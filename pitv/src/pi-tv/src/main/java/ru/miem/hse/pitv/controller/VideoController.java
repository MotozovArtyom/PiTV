package ru.miem.hse.pitv.controller;

import java.util.Objects;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.miem.hse.pitv.model.VideoModel;

@Singleton
public class VideoController {

	private static final Logger log = LoggerFactory.getLogger(VideoController.class);

	private VideoModel videoModel;

	@Inject
	public VideoController(VideoModel videoModel) {
		Objects.requireNonNull(videoModel);

		this.videoModel = videoModel;

		videoModel.getCurrentVideo().addListener((observable, oldValue, newValue) -> {
		});

	}

	public void interrupt() {

	}
}
