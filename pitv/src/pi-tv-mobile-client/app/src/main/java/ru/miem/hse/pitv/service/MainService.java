package ru.miem.hse.pitv.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class MainService extends IntentService {
	private static final String TAG = MainService.class.getName();

	/**
	 * Creates an IntentService.  Invoked by your subclass's constructor.
	 */
	public MainService() {
		super("PiTvService");
	}

	/**
	 * Creates an IntentService.  Invoked by your subclass's constructor.
	 *
	 * @param name Used to name the worker thread, important only for debugging.
	 */
	public MainService(String name) {
		super(name);
	}

	/**
	 *
	 */
	@Override
	public void onCreate() {
		super.onCreate();
//		Intent intent = getIN
	}

	/**
	 * @param intent
	 * @return
	 */
	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	/**
	 * @param intent
	 */
	@Override
	protected void onHandleIntent(@Nullable Intent intent) {
		Log.i(TAG, "Got intent");
	}
}
