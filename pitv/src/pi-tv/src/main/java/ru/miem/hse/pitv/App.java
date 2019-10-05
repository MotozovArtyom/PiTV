package ru.miem.hse.pitv;

import javafx.application.Application;
import javafx.stage.Stage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App extends Application {
	public static final Logger log = LoggerFactory.getLogger(App.class.getName());

	public static void main(String[] args) {
		log.info("Application started");
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

	}
}
