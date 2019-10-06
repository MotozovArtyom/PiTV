package ru.miem.hse.pitv.controller;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.miem.hse.pitv.model.VideoModel;

@Singleton
public class VideoController {

	@Inject
	private VideoModel videoModel;

	public void interrupt() {

	}
}
