package ru.miem.hse.pitv.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.miem.hse.pitv.App;

@Singleton
public class AppConfig {

	private static final String DEFAULT_PITV_SERVER_PORT = "8080";

	private static final Logger log = LoggerFactory.getLogger(AppConfig.class.getName());

	private static final Path CONFIG_FILE = Paths.get(App.APP_DIR.toString(), App.PROPERTIES_FILE);

	private String appVersion;

	private Integer pitvServerPort;

	/**
	 * @throws IOException
	 */
	public AppConfig() throws IOException {
		InputStream in = null;
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader != null) {
			in = classLoader.getResourceAsStream(CONFIG_FILE.toString());
		}
		if (in == null) {
			in = new FileInputStream(CONFIG_FILE.toString());
		}
		Properties readedProperties = new Properties();
		readedProperties.load(in);

		appVersion = System.getProperty("app.version");

		pitvServerPort = Integer.parseInt(readedProperties.getProperty("pitv.server.port", DEFAULT_PITV_SERVER_PORT));

	}

	/**
	 * @return App build version
	 */
	public String getAppVersion() {
		return appVersion;
	}

	/**
	 * @return PiTV port number defined in properties file (Default: 8080)
	 */
	public Integer getPitvServerPort() {
		return pitvServerPort;
	}
}
