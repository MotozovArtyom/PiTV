package ru.miem.hse.pitv.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class ThreadPools {
	private static final ExecutorService defaultPool = Executors.newFixedThreadPool(1);

	public static ExecutorService defaultPool() {
		return defaultPool;
	}
}
