package ru.miem.hse.pitv;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.miem.hse.pitv.controller.VideoController;

public class App extends Application {

	public static final Logger log = LoggerFactory.getLogger(App.class.getName());

	public static final Path USER_HOME = Paths.get(System.getProperty("user.home"));

	public static final Path APP_DIR = Paths.get(System.getProperty("user.home"), ".PiTV");

	public static final String PROPERTIES_FILE = "app.properties";

	public static final Injector injector = Guice.createInjector(new DefaultModule());

	private final VideoController videoController;

	public App() {
		videoController = injector.getInstance(VideoController.class);
	}

	public static void main(String[] args) {
		log.info("Application started");
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

	}

	static class DefaultModule extends AbstractModule {

		@Override
		protected void configure() {
			bind(FXMLLoader.class).toProvider(FXMLLoaderProvider.class);
		}
	}

	static class FXMLLoaderProvider implements Provider<FXMLLoader> {

		private final Injector injector;

		@Inject
		public FXMLLoaderProvider(Injector injector) {
			Objects.requireNonNull(injector);
			this.injector = injector;
		}

		@Override
		public FXMLLoader get() {
			FXMLLoader loader = new FXMLLoader();
			loader.setControllerFactory(injector::getInstance);
			return loader;
		}
	}
}
