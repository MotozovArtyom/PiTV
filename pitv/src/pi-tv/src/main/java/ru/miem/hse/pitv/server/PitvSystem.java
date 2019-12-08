package ru.miem.hse.pitv.server;

import java.security.SecureRandom;

public class PitvSystem {

	private static final int SYSTEM_ID = new SecureRandom().nextInt();

	private PitvSystem() {
	}

	public static int systemId() {
		return SYSTEM_ID;
	}
}
