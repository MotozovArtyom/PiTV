package ru.miem.hse.pitv.server;

import ru.miem.hse.pitv.util.Preconditions;

public class SsdpService {
	private final String type;
	private final String name;
	private final String location; // optional
	private final String identity; // optional

	public SsdpService(String type, String name, String location, String identity) {
		Preconditions.checkNotEmpty(type);
		Preconditions.checkNotEmpty(name);

		this.type = type;
		this.name = name;
		this.location = location;
		this.identity = identity;
	}

	public SsdpService(String type, String name, String location) {
		this(type, name, location, null);
	}

	public String type() {
		return type;
	}

	public String name() {
		return name;
	}

	public String location() {
		return location;
	}

	public String identity() {
		return identity;
	}
}
