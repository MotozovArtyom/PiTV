package ru.miem.hse.pitv.activity.control;

import ru.miem.hse.pitv.activity.BasePresenter;
import ru.miem.hse.pitv.activity.BaseView;

public interface ControlContract {
	interface View extends BaseView<Presenter> {
		void updateVideoName(String title);
	}

	interface Presenter extends BasePresenter {

		void setView(View view);

		void sendVolumeUp();

		void sendVolumeDown();

		void sendStop();

		void sendRewind();

		void sendPlay();

		void sendPause();

		void sendForward();

		void getVideoName(String url);
	}
}
