package ru.miem.hse.pitv.domain;

public class Command {

	private CommandType commandType;

	private Video video;

	public Command() {
	}

	public CommandType getCommandType() {
		return commandType;
	}

	public void setCommandType(CommandType commandType) {
		this.commandType = commandType;
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}
}
