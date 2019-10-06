package ru.miem.hse.pitv.activity.control;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.miem.hse.pitv.R;

public class ControlRootFragment extends Fragment implements ControlContract.View {

	private ControlContract.Presenter controlPresenter;

	public static ControlRootFragment getInstance() {
		ControlRootFragment fragment = new ControlRootFragment();
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.control_root_fragment, container, false);
		return view;
	}


	@Override
	public void setPresenter(ControlContract.Presenter presenter) {

	}
}
