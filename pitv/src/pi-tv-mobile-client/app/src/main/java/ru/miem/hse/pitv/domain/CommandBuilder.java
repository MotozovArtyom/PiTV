package ru.miem.hse.pitv.domain;

public class CommandBuilder {
	private Video video;

	private CommandType commandType;

	public CommandBuilder setVideo(Video video) {
		this.video = video;
		return this;
	}

	public CommandBuilder setCommandType(CommandType commandType) {
		this.commandType = commandType;
		return this;
	}

	public Command build() {
		Command command = new Command();
		command.setCommandType(commandType);
		command.setVideo(video);
		return command;
	}
}
