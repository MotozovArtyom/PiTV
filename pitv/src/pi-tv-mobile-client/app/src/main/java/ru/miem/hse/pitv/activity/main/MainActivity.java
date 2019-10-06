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

	private ExecutorService threadPool = Executors.newFixedThreadPool(1);

	/**
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		String action = intent.getAction();
		String type = intent.getType();

		Log.i(TAG, "Receive action " + action);
		Log.i(TAG, "Receive type " + type);

		if (Intent.ACTION_SEND.equals(action) && type != null) {
			if (type.equals("text/plain")) {
				handleTextIntent(intent);
			}
		}

		setContentView(R.layout.main_activity);
	}

	/**
	 * @param intent
	 */
	private void handleTextIntent(Intent intent) {
		String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
		Log.i(TAG, "Shared text = " + sharedText);
		Video video = new Video();
		video.setUrl(sharedText);

		Command command = new CommandBuilder()
				.setVideo(video)
				.setCommandType(CommandType.PLAY)
				.build();

		Future<?> task = threadPool.submit(new SendCommandTask(command));
		try {
			task.get();
		} catch (ExecutionException | InterruptedException e) {
			Log.e(TAG, "Error while executing task", e);
		}
	}
}
