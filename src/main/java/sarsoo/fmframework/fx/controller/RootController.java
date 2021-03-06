package sarsoo.fmframework.fx.controller;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import sarsoo.fmframework.config.Config;
import sarsoo.fmframework.config.ConfigPersister;
import sarsoo.fmframework.config.ConfigVariable;
import sarsoo.fmframework.config.VariableEvent;
import sarsoo.fmframework.config.VariableListener;
import sarsoo.fmframework.error.ApiCallException;
import sarsoo.fmframework.file.ListPersister;
import sarsoo.fmframework.fm.FmAuthNetwork;
import sarsoo.fmframework.fx.TextAreaConsole;
import sarsoo.fmframework.fx.service.GetLastTrackService;
import sarsoo.fmframework.fx.service.GetScrobbleCountService;
import sarsoo.fmframework.fx.service.GetTagMenuItemsService;
import sarsoo.fmframework.fx.service.GetTagsService;
import sarsoo.fmframework.fx.service.ScrobbleCount;
import sarsoo.fmframework.fx.tab.AlbumTab;
import sarsoo.fmframework.fx.tab.ArtistTab;
import sarsoo.fmframework.fx.tab.ConsoleTab;
import sarsoo.fmframework.fx.tab.FMObjListEditTab;
import sarsoo.fmframework.fx.tab.GenrePieChartTab;
import sarsoo.fmframework.fx.tab.ScrobbleChartTab;
import sarsoo.fmframework.fx.tab.ScrobbleTab;
import sarsoo.fmframework.fx.tab.TopAlbumTab;
import sarsoo.fmframework.fx.tab.TrackTab;
import sarsoo.fmframework.fx.tab.WebViewTab;
import sarsoo.fmframework.log.Log;
import sarsoo.fmframework.log.Logger;
import sarsoo.fmframework.fx.FmFramework;
import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.music.Artist;
import sarsoo.fmframework.music.Tag;
import sarsoo.fmframework.music.Track;
import sarsoo.fmframework.util.FMObjList;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javafx.concurrent.*;
import javafx.application.Platform;

public class RootController {

	@FXML
	public void initialize() {
		Logger.setLog(new Log(TextAreaConsole.getInstance()));

		Config config = FmFramework.getSessionConfig();

		if (config.getVariable("api_key") == null) {
			while (config.getVariable("api_key") == null) {
				setApiKey();
			}
		}

		if (config.getVariable("username") == null) {
			while (config.getVariable("username") == null) {
				changeUsername();
			}
		}

		new ConfigPersister().saveConfig(".fm/", config);

		FmFramework.getSessionConfig().getVariable("username").addListener(new VariableListener() {

			@Override
			public void listen(VariableEvent event) {
				refresh();
			}

		});

		refresh();
	}

	public void refresh() {
		labelStatsUsername.setText(FmFramework.getSessionConfig().getValue("username"));

		refreshScrobbleCounts();
		addLastTrackTab();
		refreshTagMenu();
	}

	public void refreshScrobbleCounts() {
		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.UK);

		GetScrobbleCountService getScrobbles = new GetScrobbleCountService();
		getScrobbles.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent t) {
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						labelStatsScrobblesToday.setText(
								numberFormat.format(((ScrobbleCount) t.getSource().getValue()).getDailyCount()));
						labelStatsScrobblesTotal.setText(
								numberFormat.format(((ScrobbleCount) t.getSource().getValue()).getTotalCount()));
					}

				});
			}
		});
		getScrobbles.start();
	}

	public void addLastTrackTab() {
		GetLastTrackService getLastTrack = new GetLastTrackService();
		getLastTrack.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent t) {
				TrackTab tab = new TrackTab(((Track) t.getSource().getValue()));

				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						addTab(tab, false);
					}

				});
			}
		});
		getLastTrack.start();
	}

	public void refreshTagMenu() {

		GetTagsService getTags = new GetTagsService();
		getTags.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent t) {

				tags = (ArrayList<Tag>) t.getSource().getValue();

				Collections.sort(tags);

				GetTagMenuItemsService getTagMenuItems = new GetTagMenuItemsService(tags);

				getTagMenuItems.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
					@Override
					public void handle(WorkerStateEvent t) {

						Platform.runLater(new Runnable() {

							@Override
							public void run() {
								menuTag.getItems().setAll((ArrayList<MenuItem>) t.getSource().getValue());
								menuTag.setDisable(false);
							}

						});

					}
				});

				getTagMenuItems.start();

			}
		});

		getTags.start();

	}
	
	public void addTab(Tab tab) {
		addTab(tab, true);
	}

	public void addTab(Tab tab, Boolean select) {
		tabPane.getTabs().add(tab);
		SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();

		if(select)
			selectionModel.select(tab);
	}

	@FXML
	protected void handleKeyShortcut(KeyEvent event) throws IOException {

		if (event.getCode() == KeyCode.F5) {
			refresh();
		}

		if (event.getCode() == KeyCode.W && event.isControlDown()) {
			closeCurrentTab();
		}

		if (event.getCode() == KeyCode.TAB && event.isControlDown()) {
			if (event.isShiftDown()) {
				tabPane.getSelectionModel().selectPrevious();
			} else {
				tabPane.getSelectionModel().selectNext();
			}
		}

	}

	@FXML
	protected void handleChangeUsername(ActionEvent event) {
		changeUsername();
	}

	@FXML
	protected void handleAuth(ActionEvent event) {
		authenticate();
	}

	public void authenticate() {
		try {

			Config config = FmFramework.getSessionConfig();

			if (config.getVariable("api_secret") != null) {

				FmAuthNetwork net = new FmAuthNetwork(config.getValue("api_key"), config.getValue("api_secret"),
						config.getValue("username"));

				String token = net.getToken();

				String url = String.format("http://www.last.fm/api/auth/?api_key=%s&token=%s",
						config.getValue("api_key"), token);

				Tab tab = new WebViewTab(url);

				tab.setOnClosed(new EventHandler<Event>() {

					@Override
					public void handle(Event arg0) {
						completeAuth(net, token);
					}
				});

				addTab(tab, true);

			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ApiCallException e) {
		}
	}

	protected void completeAuth(FmAuthNetwork net, String token) {

		String sk;
		try {
			sk = net.getSession(token);

			if (sk != null) {
				FmFramework.getSessionConfig().addVariable(new ConfigVariable("session_key", sk));
			}

		} catch (ApiCallException e) {
		}
	}

	public void changeUsername() {

		String username = JOptionPane.showInputDialog("enter username:");
		if (username != null) {
			FmFramework.getSessionConfig().addVariable(new ConfigVariable("username", username));

		}

	}

	public void setApiKey() {

		String apiKey = JOptionPane.showInputDialog("enter api key:");
		if (apiKey != null) {
			FmFramework.getSessionConfig().addVariable(new ConfigVariable("api_key", apiKey));

		}

	}

	@FXML
	protected void handleLookupAlbum(ActionEvent event) throws IOException {
		Service<Void> service = new Service<Void>() {
			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					@Override
					protected Void call() throws Exception {

						Album album = sarsoo.fmframework.jframe.Getter.getAlbum();

						if (album != null) {
							AlbumTab tab = new AlbumTab(album);

//							final CountDownLatch latch = new CountDownLatch(1);
							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									try {
										tabPane.getTabs().add(tab);
									} finally {
//										latch.countDown();
									}
								}
							});
//							latch.await();
						}
						// Keep with the background work
						return null;
					}
				};
			}
		};
		service.start();

	}

	@FXML
	protected void handleLookupArtist(ActionEvent event) throws IOException {
		Service<Void> service = new Service<Void>() {
			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					@Override
					protected Void call() throws Exception {

						Artist artist = sarsoo.fmframework.jframe.Getter.getArtist();

						if (artist != null) {
							ArtistTab tab = new ArtistTab(artist);

//							final CountDownLatch latch = new CountDownLatch(1);
							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									try {
										tabPane.getTabs().add(tab);
									} finally {
//										latch.countDown();
									}
								}
							});
//							latch.await();
						}
						return null;
					}
				};
			}
		};
		service.start();
	}

	@FXML
	protected void handleLookupTrack(ActionEvent event) throws IOException {

		Service<Void> service = new Service<Void>() {
			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					@Override
					protected Void call() throws Exception {

						Track track = sarsoo.fmframework.jframe.Getter.getTrack();

						if (track != null) {
							TrackTab tab = new TrackTab(track);

							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									tabPane.getTabs().add(tab);
								}
							});
						}
						return null;
					}
				};
			}
		};
		service.start();
	}

	@FXML
	protected void handleCurrentTrack(ActionEvent event) throws IOException {
		addLastTrackTab();
	}

	@FXML
	protected void handleCreateList(ActionEvent event) {

		Tab tab = new FMObjListEditTab();
		addTab(tab, true);

	}

	@FXML
	protected void handlePrintConfig(ActionEvent event) {
		System.out.println(FmFramework.getSessionConfig());
	}

	@FXML
	protected void handleDumpCache(ActionEvent event) {
		Log log = Logger.getLog();

		FmFramework.getTrackPool().dumpToLog(log);
		FmFramework.getAlbumPool().dumpToLog(log);
		FmFramework.getArtistPool().dumpToLog(log);
		FmFramework.getTagPool().dumpToLog(log);
	}

	@FXML
	protected void handleScrobble(ActionEvent event) throws IOException {
		addTab(new ScrobbleTab(), true);
	}

	@FXML
	protected void handleOpenConsole(ActionEvent event) {
		addTab(new ConsoleTab(), true);
	}

	@FXML
	protected void handleTopAlbum(ActionEvent event) {
		addTab(new TopAlbumTab(), true);
	}

	@FXML
	protected void handleScrobbleChart(ActionEvent event) {
		addTab(new ScrobbleChartTab(), true);

	}

	@FXML
	protected void handleListEdit(ActionEvent event) {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("open fm list");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("FMObjList", "*.fmlist"));
		File file = fileChooser.showOpenDialog(FmFramework.getStage());

		if (file != null) {

			ListPersister persist = new ListPersister();
			FMObjList list = persist.readListFromFile(file);
			list.setGroupName(file.getName());
			list.refresh();
			addTab(new FMObjListEditTab(list), true);
		}
	}

	@FXML
	protected void handleOpenEdit(ActionEvent event) {
		addTab(new ConsoleTab(), true);
	}

	public void closeCurrentTab() {
		SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();

		Tab tab = selectionModel.getSelectedItem();

		if (tab.isClosable()) {

			EventHandler<Event> handler = tab.getOnClosed();
			if (handler != null) {
				handler.handle(null);
			} else {
				tabPane.getTabs().remove(tab);
			}
		}
	}

	@FXML
	protected void handleGenrePieTab(ActionEvent event) {
		addTab(new GenrePieChartTab(), true);
	}

	private ArrayList<Tag> tags;

	@FXML
	private Label labelStatsScrobblesToday;

	@FXML
	private Label labelStatsUsername;

	@FXML
	private Label labelStatsScrobblesTotal;

	@FXML
	private TabPane tabPane;

	@FXML
	private Menu menuTag;

	@FXML
	private Menu menuPieChart;

	@FXML
	private Menu menuChart;

	@FXML
	private Accordion accordionCharts;

}
