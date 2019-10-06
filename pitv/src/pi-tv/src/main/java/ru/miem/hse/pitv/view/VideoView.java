package ru.miem.hse.pitv.view;

import javax.inject.Inject;
import javax.inject.Singleton;

import javafx.fxml.FXML;
import javafx.scene.web.WebView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.miem.hse.pitv.model.VideoModel;

@Singleton
public class VideoView {

	private static final Logger log = LoggerFactory.getLogger(VideoView.class);

	@Inject
	private VideoModel videoModel;

	@FXML
	private WebView web;

	@FXML
	public void initialize() {
		web.getEngine().load("https://www.youtube.com/embed/VSPuRXkUWoU");
	}
}
