package ru.miem.hse.pitv.activity;

/**
 *
 * @param <T>
 */
public interface BaseView<T extends BasePresenter> {
	/**
	 *
	 * @param presenter
	 */
	void setPresenter(T presenter);
}
