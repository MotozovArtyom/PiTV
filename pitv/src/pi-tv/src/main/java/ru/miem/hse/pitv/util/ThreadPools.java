package ru.miem.hse.pitv.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public final class ThreadPools {
	private static final ExecutorService DEFAULT_POOL = Executors.newFixedThreadPool(1);

	private static final ScheduledExecutorService DEFAULT_SCHEDULER
			= Executors.newScheduledThreadPool(1);

	private ThreadPools() {
	}

	public static ExecutorService defaultPool() {
		return DEFAULT_POOL;
	}

	public static ScheduledExecutorService defaultScheduler() {
		return DEFAULT_SCHEDULER;
	}
}
