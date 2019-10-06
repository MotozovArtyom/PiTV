package ru.miem.hse.pitv.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.miem.hse.pitv.util.AppConfig;

/**
 *
 */
public class Server implements Runnable {

	private static final Logger log = LoggerFactory.getLogger(Server.class);

	private AppConfig appConfig;

	private Selector selector;

	private ServerSocketChannel serverSocket;

	private Integer port;

	private ByteBuffer buffer = ByteBuffer.allocate(4096); // 4KB;

	/**
	 * @throws IOException
	 */
	@Inject
	public Server(AppConfig appConfig) throws IOException {
		this.appConfig = appConfig;
		port = this.appConfig.getPitvServerPort();
		selector = Selector.open();
		serverSocket = ServerSocketChannel.open();
		serverSocket.socket().bind(new InetSocketAddress(port));
		serverSocket.configureBlocking(false);

		serverSocket.register(selector, SelectionKey.OP_ACCEPT);
	}

	/**
	 * Server process: selecting requests and sort them by OPT:
	 * OP_READ, OP_ACCEPT
	 */
	@Override
	public void run() {
		try {
			log.info("Server starting on port " + this.port);
			Iterator<SelectionKey> keyIterator;
			SelectionKey selectionKey;
			while (this.serverSocket.isOpen()) {
				selector.select();
				keyIterator = this.selector.selectedKeys().iterator();
				while (keyIterator.hasNext()) {
					selectionKey = keyIterator.next();
					keyIterator.remove();

					if (selectionKey.isValid()) {
						if (selectionKey.isAcceptable()) {
							this.handleAccept(selectionKey);
						}

						if (selectionKey.isReadable()) {
							this.handleRead(selectionKey);
						}
					}
				}
			}
		} catch (IOException e) {
			log.error("Error while receiving, handling, processing requests", e);
		} finally {
			closeSelector();
			closeServerSocket();
		}
	}

	/**
	 *
	 */
	private void closeSelector() {
		try {
			if (selector.isOpen()) {
				selector.close();
			}
		} catch (IOException e) {
			log.error("Cannot finally close selector", e);
			throw new ServerException("Cannot finally close selector", e);
		}
	}

	/**
	 *
	 */
	private void closeServerSocket() {
		try {
			if (serverSocket.isOpen()) {
				serverSocket.close();
			}
		} catch (IOException e) {
			log.error("Cannot finally close server socket", e);
			throw new ServerException("Cannot finally close socket", e);
		}
	}

	/**
	 * Handle OP_ACCEPT option in request
	 *
	 * @param selectionKey
	 * @throws IOException
	 */
	private void handleAccept(SelectionKey selectionKey) throws IOException {
		SocketChannel channel = ((ServerSocketChannel)selectionKey.channel()).accept();
		StringBuilder builder = new StringBuilder();
		builder.append(channel.socket().getInetAddress());
		builder.append(":");
		builder.append(channel.socket().getPort());
		String address = builder.toString();
		channel.configureBlocking(false);
		channel.write(ByteBuffer.wrap("HI".getBytes()));
		channel.register(selector, SelectionKey.OP_READ, address);
		log.info("Accepted connection from: {}", address);
	}

	/**
	 * Handle OP_READ option in request
	 *
	 * @param selectionKey
	 * @throws IOException
	 */
	private void handleRead(SelectionKey selectionKey) throws IOException {
		SocketChannel channel = (SocketChannel)selectionKey.channel();
		StringBuilder builder = new StringBuilder();

		buffer.clear();
		int readBytes;
		while ((readBytes = channel.read(buffer)) > 0) {
			buffer.flip();
			byte[] bytes = new byte[buffer.limit()];
			buffer.get(bytes);
			builder.append(new String(bytes));
			buffer.clear();
		}
		String message = null;
		if (readBytes < 0) {
			log.info("Read bytes less than 0. Attachments {}", selectionKey.attachment());
			log.info("Closing channel");
			channel.close();
		} else {
			// TODO: Getting JSON from mobile client;
			message = builder.toString();
		}
		log.debug("Received message: {}", message);
	}
}
