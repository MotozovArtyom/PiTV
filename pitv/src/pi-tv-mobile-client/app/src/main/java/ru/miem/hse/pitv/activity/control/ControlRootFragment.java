package ru.miem.hse.pitv.activity.control;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ru.miem.hse.pitv.R;

public class ControlRootFragment extends Fragment implements ControlContract.View {

	private static final String TAG = ControlRootFragment.class.getName();

	private ControlContract.Presenter controlPresenter;

	private ImageView playButton;

	private ImageView pauseButton;

	private ImageView forwardButton;

	private ImageView rewindButton;

	private ImageView stopButton;

	private ImageView volumeDownButton;

	private ImageView volumeUpButton;

	private TextView videoNameLabel;

	private TextView videoUrlLabel;

	private String targetUrl;

	public static ControlRootFragment getInstance(String url) {
		ControlRootFragment fragment = new ControlRootFragment();
		fragment.targetUrl = url;
		return fragment;
	}

	public static ControlRootFragment getInstance(String url, ControlContract.Presenter presenter) {
		ControlRootFragment fragment = new ControlRootFragment();
		fragment.targetUrl = url;
		fragment.setPresenter(presenter);
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.control_root_fragment, container, false);

		playButton = view.findViewById(R.id.controlPlayButton);
		playButton.setOnClickListener(newOnPlayButtonClickListener());

		pauseButton = view.findViewById(R.id.controlPauseButton);
		pauseButton.setOnClickListener(newOnPauseButtonClickListener());

		forwardButton = view.findViewById(R.id.controlForwardButton);
		forwardButton.setOnClickListener(newOnForwardButtonClickListener());

		rewindButton = view.findViewById(R.id.controlRewindButton);
		rewindButton.setOnClickListener(newOnRewindButtonClickListener());

		stopButton = view.findViewById(R.id.controlStopButton);
		stopButton.setOnClickListener(newOnStopButtonClickListener());

		volumeDownButton = view.findViewById(R.id.controlVolumeDown);
		volumeDownButton.setOnClickListener(newOnVolumeDownButtonClickListener());

		volumeUpButton = view.findViewById(R.id.controlVolumeUp);
		volumeUpButton.setOnClickListener(newOnVolumeUpButtonClickListener());

		videoNameLabel = view.findViewById(R.id.videoName);
		videoUrlLabel = view.findViewById(R.id.videoUrl);
		videoUrlLabel.setText(targetUrl);
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		controlPresenter.getVideoName(targetUrl);
	}

	private View.OnClickListener newOnVolumeUpButtonClickListener() {
		return v -> controlPresenter.sendVolumeUp();
	}

	private View.OnClickListener newOnVolumeDownButtonClickListener() {
		return v -> controlPresenter.sendVolumeDown();
	}

	private View.OnClickListener newOnStopButtonClickListener() {
		return v -> controlPresenter.sendStop();
	}

	private View.OnClickListener newOnRewindButtonClickListener() {
		return v -> controlPresenter.sendRewind();
	}

	private View.OnClickListener newOnPlayButtonClickListener() {
		return v -> controlPresenter.sendPlay();
	}

	private View.OnClickListener newOnPauseButtonClickListener() {
		return v -> controlPresenter.sendPause();
	}

	private View.OnClickListener newOnForwardButtonClickListener() {
		return v -> controlPresenter.sendForward();
	}

	@Override
	public void setPresenter(ControlContract.Presenter presenter) {
		this.controlPresenter = presenter;
		presenter.setView(this);
	}

	@Override
	public void updateVideoName(String title) {
		videoNameLabel.setText(title);
	}
}
