package ru.miem.hse.pitv.controller;

import javax.inject.Singleton;

import ru.miem.hse.pitv.App;

@Singleton
public class SceneController {

	/**
	 * Navigate view part of application
	 */
	public void navigate() {
		App.navigate("fxml/video.fxml");
	}
}
