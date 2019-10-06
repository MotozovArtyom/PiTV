package ru.miem.hse.pitv.controller;

import java.util.Objects;
import javax.inject.Inject;
import javax.inject.Singleton;

import ru.miem.hse.pitv.App;
import ru.miem.hse.pitv.model.VideoModel;

@Singleton
public class SceneController {

	private VideoModel videoModel;

	@Inject
	public SceneController(VideoModel videoModel) {
		Objects.requireNonNull(videoModel);

		this.videoModel = videoModel;
	}

	/**
	 * Navigate view part of application
	 */
	public void navigate() {
		App.navigate("fxml/video.fxml");
	}
}
