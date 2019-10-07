package ru.miem.hse.pitv.model;

public class Command {

	private Video video;

	private CommandType commandType;

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public CommandType getCommandType() {
		return commandType;
	}

	public void setCommandType(CommandType commandType) {
		this.commandType = commandType;
	}

	@Override
	public String toString() {
		return "Command{" +
				"video=" + video +
				", commandType=" + commandType +
				'}';
	}
}
