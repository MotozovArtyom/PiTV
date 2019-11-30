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

	private TextView videoName;

	private TextView viedoUrl;

	public static ControlRootFragment getInstance() {
		ControlRootFragment fragment = new ControlRootFragment();
		return fragment;
	}

	public static ControlRootFragment getInstance(ControlContract.Presenter presenter) {
		ControlRootFragment fragment = new ControlRootFragment();
		fragment.setPresenter(presenter);
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.control_root_fragment, container, false);
		playButton = view.findViewById(R.id.controlPlayButton);
		pauseButton = view.findViewById(R.id.controlPauseButton);
		forwardButton = view.findViewById(R.id.controlForwardButton);
		rewindButton = view.findViewById(R.id.controlRewindButton);
		stopButton = view.findViewById(R.id.controlStopButton);
		volumeDownButton = view.findViewById(R.id.controlVolumeDown);
		volumeUpButton = view.findViewById(R.id.controlVolumeUp);
		videoName = view.findViewById(R.id.videoName);
		viedoUrl = view.findViewById(R.id.videoUrl);
		return view;
	}

	@Override
	public void setPresenter(ControlContract.Presenter presenter) {
		this.controlPresenter = presenter;
	}
}
