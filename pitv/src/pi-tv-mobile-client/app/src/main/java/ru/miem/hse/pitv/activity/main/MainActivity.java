package ru.miem.hse.pitv.activity.main;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import ru.miem.hse.pitv.R;
import ru.miem.hse.pitv.domain.Command;
import ru.miem.hse.pitv.domain.CommandBuilder;
import ru.miem.hse.pitv.domain.CommandType;
import ru.miem.hse.pitv.domain.Video;
import ru.miem.hse.pitv.server.SendCommandTask;

/**
 *
 */
public class MainActivity extends AppCompatActivity {

	/**
	 *
	 */
	private static final String TAG = MainActivity.class.getName();

	/**
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main_activity);
	}
}
