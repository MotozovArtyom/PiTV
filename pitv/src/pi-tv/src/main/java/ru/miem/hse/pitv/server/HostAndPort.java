package ru.miem.hse.pitv.server;

import java.util.Objects;

/**
 * Encapsulate host and port information
 */
public class HostAndPort {

	private String host;

	private String port;

	public HostAndPort(String host, String port) {
		Objects.requireNonNull(host);
		Objects.requireNonNull(port);

		this.host = host;
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
}
