package ru.miem.hse.pitv.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.EventListener;
import java.util.EventObject;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.miem.hse.pitv.util.AppConfig;
import ru.miem.hse.pitv.util.Preconditions;

public class ServiceDiscovery implements Runnable {

	public static final String PITV_SERVICE_TYPE = "pitv:mobile";

	public static final String PITV_SERVER_SERVICE_TYPE = "pitv:server";

	private static final Logger log = LoggerFactory.getLogger(ServiceDiscovery.class.getName());

	private final List<EventListener> listeners = new CopyOnWriteArrayList<>();

	private final AppConfig appConfig;

	private MulticastSocket socket;

	@Inject
	public ServiceDiscovery(AppConfig appConfig) throws IOException {
		this.appConfig = appConfig;

	}

	@Override
	public void run() {
		try {
			socket = new MulticastSocket(this.appConfig.getPitvServerPort());
			socket.setReuseAddress(true);
			socket.joinGroup(InetAddress.getByName(this.appConfig.getPitvDiscoveryHost()));


			while (!Thread.currentThread().isInterrupted()) {
				byte[] buffer = new byte[8192];
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				socket.receive(packet);
				String s = new String(packet.getData(), "ISO_8859_1");
				log.info(">>>>>>>>>>>>>>>>>>>>\nReceived new ssdp packet: \n>>>>>>>>>>>>>>>>>>>>\n{}", s);
			}
		} catch (IOException e) {
			log.error("Exception in service discovery:", e);
		}
	}


	public void addListener(EventListener listener) {
		Objects.requireNonNull(listener);

		listeners.add(listener);
	}

	public void removeListener(EventListener listener) {
		Objects.requireNonNull(listener);

		listeners.remove(listener);
	}

	public interface Listener {
		void serviceAlive(SsdpEvent event);

		void serviceUpdate(SsdpEvent event);

		void serviceByebye(SsdpEvent event);
	}

	public static class SsdpEvent extends EventObject {

		private final SsdpService service;

		/**
		 * Constructs a prototypical Event.
		 *
		 * @param source the object on which the Event initially occurred
		 * @throws IllegalArgumentException if source is null
		 */
		public SsdpEvent(Object source, SsdpService service) {
			super(source);

			Preconditions.checkNotNull(service);
			this.service = service;
		}

		public SsdpService getService() {
			return service;
		}
	}
}
