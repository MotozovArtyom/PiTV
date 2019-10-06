package ru.miem.hse.pitv.activity.control;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import ru.miem.hse.pitv.R;

public class ControlActivity extends AppCompatActivity {

	private ControlContract.Presenter controlPresenter;

	private ControlRootFragment controlRootFragment;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.control_activity);

		FragmentManager fragmentManager = getSupportFragmentManager();
		controlRootFragment = (ControlRootFragment)fragmentManager.findFragmentById(R.id.controlRootFragmentContainer);
		if (controlRootFragment != null) {
			controlRootFragment = ControlRootFragment.getInstance();
			fragmentManager.beginTransaction()
					.add(R.id.controlRootFragmentContainer, controlRootFragment)
					.commit();
		}
		controlPresenter = new ControlPresenter();

		controlRootFragment.setPresenter(controlPresenter);
	}
}
