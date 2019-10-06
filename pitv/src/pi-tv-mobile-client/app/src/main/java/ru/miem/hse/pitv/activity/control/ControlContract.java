package ru.miem.hse.pitv.activity.control;

import ru.miem.hse.pitv.activity.BasePresenter;
import ru.miem.hse.pitv.activity.BaseView;

public interface ControlContract {
	interface View extends BaseView<Presenter> {

	}

	interface Presenter extends BasePresenter {

	}
}
