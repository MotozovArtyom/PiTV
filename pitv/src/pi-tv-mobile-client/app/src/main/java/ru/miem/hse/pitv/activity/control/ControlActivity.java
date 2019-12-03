package ru.miem.hse.pitv.activity.control;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import ru.miem.hse.pitv.R;
import ru.miem.hse.pitv.domain.Command;
import ru.miem.hse.pitv.domain.CommandBuilder;
import ru.miem.hse.pitv.domain.CommandType;
import ru.miem.hse.pitv.domain.Video;
import ru.miem.hse.pitv.server.SendCommandTask;

public class ControlActivity extends AppCompatActivity {

	private static final String TAG = ControlActivity.class.getName();

	private ControlContract.Presenter controlPresenter;

	private ControlRootFragment controlRootFragment;

	private ExecutorService threadPool = Executors.newFixedThreadPool(1);

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.control_activity);

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

		FragmentManager fragmentManager = getSupportFragmentManager();
		controlRootFragment = (ControlRootFragment)fragmentManager.findFragmentById(R.id.controlRootFragmentContainer);
		if (controlRootFragment == null) {
			controlRootFragment = ControlRootFragment.getInstance(intent.getStringExtra(Intent.EXTRA_TEXT));
			fragmentManager.beginTransaction()
					.add(R.id.controlRootFragmentContainer, controlRootFragment)
					.commit();
		}
		controlPresenter = new ControlPresenter();

		controlRootFragment.setPresenter(controlPresenter);
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

		threadPool.submit(new SendCommandTask(command));
//		try {
//			task.get();
//		} catch (ExecutionException | InterruptedException e) {
//			Log.e(TAG, "Error while executing task", e);
//		}
	}
}
