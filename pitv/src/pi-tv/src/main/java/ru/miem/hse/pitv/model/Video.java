package ru.miem.hse.pitv.model;

public class Video {

	public String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Video{" +
				"url='" + url + '\'' +
				'}';
	}
}
