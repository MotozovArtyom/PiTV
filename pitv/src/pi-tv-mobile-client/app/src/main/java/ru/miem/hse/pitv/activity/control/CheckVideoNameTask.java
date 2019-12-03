package ru.miem.hse.pitv.activity.control;

import java.util.concurrent.Callable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class CheckVideoNameTask implements Callable<String> {

	private static final String TAG = CheckVideoNameTask.class.getName();

	private String url;

	public CheckVideoNameTask(String url) {
		this.url = url;
	}

	@Override
	public String call() throws Exception {
		Document document = Jsoup.connect(url).get();
		return document.title();
	}
}
