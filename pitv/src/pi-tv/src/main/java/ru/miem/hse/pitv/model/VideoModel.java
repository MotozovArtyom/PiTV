package ru.miem.hse.pitv.model;

import java.util.Arrays;
import java.util.List;
import javax.inject.Singleton;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class VideoModel {
	private static final Logger log = LoggerFactory.getLogger(VideoModel.class);


	public ObjectProperty<Video> currentVideo = new SimpleObjectProperty<>(null);

	public ObjectProperty<Video> getCurrentVideo() {
		return currentVideo;
	}

	public void setCurrentVideo(Video currentVideo) {
		log.debug("Current video updated: {}", currentVideo);
		this.currentVideo.set(currentVideo);
	}

	public static String processUrl(String url) {
		String processedUrl = null;
		StringBuilder builder = new StringBuilder();
		if (url.contains(ResourceType.YOUTUBE.domains.get(1)) ||
				url.contains(ResourceType.YOUTUBE.domains.get(0))) {
			String[] splitedUrl = url.split("/");
			String ejectUrl = splitedUrl[splitedUrl.length - 1];
			processedUrl = "https://www.youtube.com/embed/" + ejectUrl;
		} else {
			processedUrl = url;
		}
		return processedUrl;
	}

	public enum ResourceType {
		YOUTUBE("youtube", "youtu.be"),
		ETC;

		private List<String> domains;

		ResourceType(String... domains) {
			this.domains = Arrays.asList(domains);
		}

		public List<String> getDomains() {
			return domains;
		}
	}
}
