package ru.miem.hse.pitv;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.miem.hse.pitv.controller.SceneController;
import ru.miem.hse.pitv.controller.VideoController;
import ru.miem.hse.pitv.server.Server;
import ru.miem.hse.pitv.util.AppConfig;
import ru.miem.hse.pitv.util.ThreadPools;

public class App extends Application {

	public static final Logger log = LoggerFactory.getLogger(App.class.getName());

	public static final Path USER_HOME = Paths.get(System.getProperty("user.home"));

	public static final Path APP_DIR = Paths.get(System.getProperty("user.home"), ".PiTV");

	public static final String PROPERTIES_FILE = "app.properties";

	public static final Injector injector = Guice.createInjector(new DefaultModule());

	private static final Map<String, Parent> NODES_CACHE = new HashMap<>();

	private static Scene MAIN_SCENE;

	private final VideoController videoController;

	private final SceneController sceneController;

	private final AppConfig appConfig;

	private final Server server;

	public App() {
		videoController = injector.getInstance(VideoController.class);
		sceneController = injector.getInstance(SceneController.class);
		appConfig = injector.getInstance(AppConfig.class);
		server = injector.getInstance(Server.class);
	}

	public static void main(String[] args) throws IOException {
		log.info("Application started");
		setupDefaultProperties();
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		log.info("App version: {}", appConfig.getAppVersion());

		MAIN_SCENE = new Scene(App.load("fxml/video.fxml"));

		primaryStage.setScene(MAIN_SCENE);
		primaryStage.setMaximized(true);
		primaryStage.setFullScreen(true);
		primaryStage.setTitle("PiTV " + appConfig.getAppVersion());
		primaryStage.setResizable(false);

		Platform.setImplicitExit(false);
		sceneController.navigate();

		ThreadPools.defaultPool().submit(server);

		primaryStage.setOnCloseRequest((e) -> {
			shutdown();
		});
		primaryStage.show();
	}

	private static void setupDefaultProperties() throws IOException {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream is = null;
		if (loader != null) {
			is = loader.getResourceAsStream(App.PROPERTIES_FILE);
		}
		if (is == null) {
			is = new FileInputStream(App.PROPERTIES_FILE);
		}
		Path targetPropertiesPath = Paths.get(App.APP_DIR.toString(), App.PROPERTIES_FILE);
		boolean shouldCreateFile = false;
		if (!Files.exists(targetPropertiesPath)) {
			shouldCreateFile = true;
		} else {
			String versionLine;
			try (BufferedReader reader =
					     new BufferedReader(new InputStreamReader(Files.newInputStream(targetPropertiesPath)))) {
				versionLine = reader.readLine();
				String propertiesVersionInFile = versionLine.substring(1).trim();
				String propertiesVersion = System.getProperty("app.version");
				if (!propertiesVersion.equals(propertiesVersionInFile)) {
					shouldCreateFile = true;
				}
			} catch (IOException e) {
				shouldCreateFile = true;
			}
		}
		if (shouldCreateFile) {
			Files.copy(is, targetPropertiesPath, StandardCopyOption.REPLACE_EXISTING);
		}
	}

	public static Parent load(String resourcePath) {
		return load(resourcePath, null);
	}

	public static Parent load(String resourcePath, Object controller) {
		Objects.requireNonNull(resourcePath);

		Parent node = null;
		if (controller == null)
			node = NODES_CACHE.get(resourcePath);
		if (node == null) {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			URL url = classLoader.getResource(resourcePath);
			if (url == null)
				throw new RuntimeException("Resource not found: " + resourcePath);

			FXMLLoader loader = injector.getInstance(FXMLLoader.class);
			// location
			loader.setLocation(url);
			// controller
			if (controller != null)
				loader.setController(controller);

			try (InputStream in = url.openStream()) {
				node = loader.load(in);
			} catch (IOException e) {
				throw new RuntimeException("Cannot load resource", e);
			}
			// cache nodes with implicit controllers
			if (controller == null)
				NODES_CACHE.put(resourcePath, node);
		}
		return node;
	}

	public static void navigate(Node next) {
		Objects.requireNonNull(next);
		if (MAIN_SCENE == null)
			return;
		Node node = MAIN_SCENE.lookup("#container");
		if (node instanceof VBox) {
			VBox parent = (VBox)node;
			parent.getChildren().setAll(next);
		}
	}

	public static void navigate(String resourcePath) {
		navigate(load(resourcePath));
	}

	private void shutdown() {
		log.info("Application shutdown");
		videoController.interrupt();

		ThreadPools.defaultPool().shutdown();

		Platform.exit();
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
