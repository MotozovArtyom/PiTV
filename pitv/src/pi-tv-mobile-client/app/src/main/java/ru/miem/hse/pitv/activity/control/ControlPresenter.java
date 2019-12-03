package ru.miem.hse.pitv.activity.control;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import android.util.Log;

import ru.miem.hse.pitv.common.Strings;
import ru.miem.hse.pitv.domain.Command;
import ru.miem.hse.pitv.domain.CommandBuilder;
import ru.miem.hse.pitv.domain.CommandType;
import ru.miem.hse.pitv.server.SendCommandTask;

public class ControlPresenter implements ControlContract.Presenter {

	private static final String TAG = ControlPresenter.class.getName();

	private ExecutorService threadPool = Executors.newFixedThreadPool(1);

	private ControlContract.View controlView;

	@Override
	public void start() {

	}

	@Override
	public void setView(ControlContract.View view) {
		controlView = view;
	}

	@Override
	public void sendVolumeUp() {
		Command command = new CommandBuilder()
				.setVideo(null)
				.setCommandType(CommandType.VOLUME_UP)
				.build();

		Future<?> task = threadPool.submit(new SendCommandTask(command));
		try {
			task.get();
		} catch (ExecutionException | InterruptedException e) {
			Log.e(TAG, "Error while executing task", e);
		}
	}

	@Override
	public void sendVolumeDown() {
		Command command = new CommandBuilder()
				.setVideo(null)
				.setCommandType(CommandType.VOLUME_DOWN)
				.build();

		Future<?> task = threadPool.submit(new SendCommandTask(command));
		try {
			task.get();
		} catch (ExecutionException | InterruptedException e) {
			Log.e(TAG, "Error while executing task", e);
		}
	}

	@Override
	public void sendStop() {
		Command command = new CommandBuilder()
				.setVideo(null)
				.setCommandType(CommandType.STOP)
				.build();

		Future<?> task = threadPool.submit(new SendCommandTask(command));
		try {
			task.get();
		} catch (ExecutionException | InterruptedException e) {
			Log.e(TAG, "Error while executing task", e);
		}
	}

	@Override
	public void sendRewind() {
		Command command = new CommandBuilder()
				.setVideo(null)
				.setCommandType(CommandType.PREV)
				.build();

		Future<?> task = threadPool.submit(new SendCommandTask(command));
		try {
			task.get();
		} catch (ExecutionException | InterruptedException e) {
			Log.e(TAG, "Error while executing task", e);
		}
	}

	@Override
	public void sendPlay() {
		Command command = new CommandBuilder()
				.setVideo(null)
				.setCommandType(CommandType.PLAY)
				.build();

		Future<?> task = threadPool.submit(new SendCommandTask(command));
		try {
			task.get();
		} catch (ExecutionException | InterruptedException e) {
			Log.e(TAG, "Error while executing task", e);
		}
	}

	@Override
	public void sendPause() {
		Command command = new CommandBuilder()
				.setVideo(null)
				.setCommandType(CommandType.PAUSE)
				.build();

		Future<?> task = threadPool.submit(new SendCommandTask(command));
		try {
			task.get();
		} catch (ExecutionException | InterruptedException e) {
			Log.e(TAG, "Error while executing task", e);
		}
	}

	@Override
	public void sendForward() {
		Command command = new CommandBuilder()
				.setVideo(null)
				.setCommandType(CommandType.NEXT)
				.build();

		Future<?> task = threadPool.submit(new SendCommandTask(command));
		try {
			task.get();
		} catch (ExecutionException | InterruptedException e) {
			Log.e(TAG, "Error while executing task", e);
		}
	}

	@Override
	public void getVideoName(String url) {
		Future<String> task = threadPool.submit(new CheckVideoNameTask(url));
		String title;
		try {
			title = task.get();
		} catch (ExecutionException | InterruptedException e) {
			Log.w("Cannot get video name", e);
			title = Strings.empty();
		}

		controlView.updateVideoName(title);
	}
}
