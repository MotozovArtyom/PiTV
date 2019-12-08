package ru.miem.hse.pitv;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.miem.hse.pitv.util.AppConfig;


public class SsdpClient {

	private static final Logger log = LoggerFactory.getLogger(SsdpClient.class);

	private static final Injector injector = Guice.createInjector(new App.DefaultModule());

	private AppConfig appConfig;

	public SsdpClient() {
		appConfig = injector.getInstance(AppConfig.class);
	}

	public static void main(String[] args) throws UnknownHostException {
		new SsdpClient().sendSsdpPacket();
	}

	private void sendSsdpPacket() throws UnknownHostException {
		InetAddress inetAddress = InetAddress.getByName(appConfig.getPitvDiscoveryHost());
		int port = appConfig.getPitvServerPort();
		byte ttl = (byte)1;

		byte[] data = "Here's some multicast data\r\n".getBytes();
		DatagramPacket dp = new DatagramPacket(data, data.length, inetAddress, port);

		try (MulticastSocket ms = new MulticastSocket()) {
			ms.setTimeToLive(ttl);
			ms.joinGroup(inetAddress);
			for (int i = 1; i < 10; i++) {
				log.info("Send {} packet", i);
				ms.send(dp);
			}
			ms.leaveGroup(inetAddress);
		} catch (IOException ex) {
			log.error("Exception in SSDP client", ex);
		}
	}
}
