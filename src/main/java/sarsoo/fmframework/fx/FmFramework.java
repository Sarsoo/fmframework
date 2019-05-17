package sarsoo.fmframework.fx;

import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sarsoo.fmframework.cache.AlbumCache;
import sarsoo.fmframework.cache.StaticCache;
import sarsoo.fmframework.cache.TrackCache;
import sarsoo.fmframework.cache.puller.AlbumPuller;
import sarsoo.fmframework.cache.puller.ArtistPuller;
import sarsoo.fmframework.cache.puller.ArtistTagPuller;
import sarsoo.fmframework.cache.puller.CachedArtistTagPuller;
import sarsoo.fmframework.cache.puller.TrackPuller;
import sarsoo.fmframework.config.Config;
import sarsoo.fmframework.config.ConfigPersister;
import sarsoo.fmframework.fm.FmUserNetwork;
import sarsoo.fmframework.fx.controller.RootController;
import sarsoo.fmframework.fx.service.SaveConfigService;
import sarsoo.fmframework.log.Logger;
import sarsoo.fmframework.log.entry.LogEntry;
import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.music.Artist;
import sarsoo.fmframework.music.Track;
import sarsoo.fmframework.util.FMObjList;

public class FmFramework extends Application {

	private static Stage stage;
	private Scene rootScene;

	private static Config config;

	private static StaticCache<FMObjList, String> tagPool = null;

	private static StaticCache<Track, Track> trackPool = null;
	private static StaticCache<Album, Album> albumPool = null;
	private static StaticCache<Artist, Artist> artistPool = null;

	private static RootController control;

	@Override
	public void start(Stage stage) throws Exception {
		FmFramework.stage = stage;

		initConfig();
		initCaches();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("ui/RootPane.fxml"));

//		Parent root = FXMLLoader.load(getClass().getResource("ui/main.fxml"));
		Parent root = (Parent) loader.load();

		Scene scene = new Scene(root, 1000, 800);

		rootScene = scene;
//		scene.getStylesheets().add("styles/style.css");

		control = (RootController) loader.getController();
//		(new Thread(new TagCaller())).start();
		stage.setMinHeight(800);
		stage.setMinWidth(960);
		stage.setTitle("fmframework");
		stage.setScene(scene);
		stage.show();

	}

	private void initConfig() {
		ConfigPersister persist = new ConfigPersister();

		config = persist.readConfig(".fm/");

		if (config != null) {

			if (!(new File(".fm/").isFile())) {
				new File(".fm/").mkdir();
			}

			SaveConfigService saveConfig = new SaveConfigService(".fm/", config);
			saveConfig.start();
		} else {
			Logger.getLog().log(new LogEntry("load config").addArg("null config returned"));
		}
	}

	private void initCaches() {

		
		artistPool = new StaticCache<>(
				new ArtistPuller(new FmUserNetwork(config.getValue("api_key"), config.getValue("username"))));
		albumPool = new AlbumCache<>(
				new AlbumPuller(new FmUserNetwork(config.getValue("api_key"), config.getValue("username"))),
				artistPool);
		trackPool = new TrackCache<>(
				new TrackPuller(new FmUserNetwork(config.getValue("api_key"), config.getValue("username"))), 
				albumPool,
				artistPool);
		
		tagPool = new StaticCache<>(
				new CachedArtistTagPuller(new FmUserNetwork(config.getValue("api_key"), config.getValue("username")), artistPool));

	}

	public static StaticCache<FMObjList, String> getTagPool() {
		return tagPool;
	}

	public static StaticCache<Track, Track> getTrackPool() {
		return trackPool;
	}

	public static StaticCache<Album, Album> getAlbumPool() {
		return albumPool;
	}

	public static StaticCache<Artist, Artist> getArtistPool() {
		return artistPool;
	}

	public static Config getSessionConfig() {
		return config;
	}

	public static void main(String[] args) {
		Runtime.getRuntime().addShutdownHook(new Thread(() -> (new ConfigPersister()).saveConfig(".fm/", config)));
		launch(args);
	}

	public static RootController getController() {
		return control;
	}

	public static Stage getStage() {
		return stage;
	}
}
