package ru.miem.hse.pitv.view;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.inject.Singleton;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import javafx.stage.Screen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.miem.hse.pitv.App;
import ru.miem.hse.pitv.model.Video;
import ru.miem.hse.pitv.model.VideoModel;
import ru.miem.hse.pitv.util.ThreadPools;

/**
 * View class. Representing videos on desktop application
 */
@Singleton
public class VideoView {

	private static final Logger log = LoggerFactory.getLogger(VideoView.class);

	/**
	 * Model objects. Through the object view class receiving current video
	 */
	@Inject
	private VideoModel videoModel;

	/**
	 * WebView attribute. Load and rendering local and remote HTTP resources
	 */
	@FXML
	private WebView web;

	/**
	 * FX Initialize method
	 */
	@FXML
	public void initialize() {
		currentVideoChanged(videoModel.currentVideo.get());

		videoModel.getCurrentVideo().addListener((observable, oldValue, newValue) ->
				Platform.runLater(() -> currentVideoChanged(newValue)));
	}

	/**
	 * Handling new video. If video is null then WebView will render
	 * message in screen
	 * @param video
	 */
	public void currentVideoChanged(Video video) {
		if (video == null) {
			URL url = App.getLocalFileUrl("html/blank.html");
			web.getEngine().load(url.toString());
		} else {
			web.getEngine().load(videoModel.getCurrentVideo().get().getUrl());
			ThreadPools.defaultScheduler().schedule(() -> {
				log.debug("Send key event");
				try {
					;
					Robot robot = new Robot();
					robot.keyPress(KeyEvent.VK_SPACE);

					double width = Screen.getPrimary().getBounds().getWidth();
					double height = Screen.getPrimary().getBounds().getHeight();
					robot.mouseMove((int)(width / 2), (int)(height / 2));
					robot.mousePress(InputEvent.BUTTON1_MASK);
					robot.mouseRelease(InputEvent.BUTTON1_MASK);
				} catch (AWTException e) {
					e.printStackTrace();
				}
			}, 5000, TimeUnit.MILLISECONDS);
		}
	}
}
