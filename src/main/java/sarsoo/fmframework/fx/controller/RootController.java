package sarsoo.fmframework.fx.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import sarsoo.fmframework.cache.TagPool;
import sarsoo.fmframework.file.ListPersister;
import sarsoo.fmframework.fm.FmUserNetwork;
import sarsoo.fmframework.fx.TextAreaConsole;
import sarsoo.fmframework.fx.chart.GenrePieChartTitledPane;
import sarsoo.fmframework.fx.chart.PieChartTitledPane;
import sarsoo.fmframework.fx.service.GetLastTrackService;
import sarsoo.fmframework.fx.service.GetScrobbleCountService;
import sarsoo.fmframework.fx.service.GetTagMenuItemsService;
import sarsoo.fmframework.fx.service.GetTagsService;
import sarsoo.fmframework.fx.service.ScrobbleCount;
import sarsoo.fmframework.fx.tab.AlbumTab;
import sarsoo.fmframework.fx.tab.ArtistTab;
import sarsoo.fmframework.fx.tab.ConsoleTab;
import sarsoo.fmframework.fx.tab.FMObjListEditTab;
import sarsoo.fmframework.fx.tab.FMObjListTab;
import sarsoo.fmframework.fx.tab.ScrobbleChartTab;
import sarsoo.fmframework.fx.tab.TrackTab;
import sarsoo.fmframework.log.Log;
import sarsoo.fmframework.log.Logger;
import sarsoo.fmframework.log.entry.InfoEntry;
import sarsoo.fmframework.log.entry.LogEntry;
import sarsoo.fmframework.fx.FmFramework;
import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.music.Artist;
import sarsoo.fmframework.music.Tag;
import sarsoo.fmframework.music.Track;
import sarsoo.fmframework.net.Key;
import sarsoo.fmframework.util.FMObjList;
import sarsoo.fmframework.util.Reference;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;

import javafx.concurrent.*;
import javafx.application.Platform;

import org.json.*;

public class RootController {

	@FXML
	public void initialize() {
		Logger.setLog(new Log(TextAreaConsole.getInstance(), false));
		refresh();
	}

	public void refresh() {
		labelStatsUsername.setText(Reference.getUserName());
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
				try {
					TrackTab tab = new TrackTab(((Track) t.getSource().getValue()));

					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							addTab(tab);
						}

					});
				} catch (IOException e) {
					e.printStackTrace();
				}
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
							}

						});

					}
				});

				getTagMenuItems.start();

			}
		});

		getTags.start();

	}

	public void refreshPieChartMenu() {

		try {
			if (new File("./piechart.json").isFile()) {
				File file = new File("./piechart.json");

				BufferedReader br = new BufferedReader(new FileReader(file));
				StringBuilder sb = new StringBuilder();
				String jsonLine = br.readLine();
				while (jsonLine != null) {
					sb.append(jsonLine);
					jsonLine = br.readLine();
				}

				br.close();

				String jsonString = sb.toString();

				JSONObject rootParsedJsonObj = new JSONObject(jsonString);

				JSONArray hierarchiesJsonArray = rootParsedJsonObj.getJSONObject("genrehierarchy")
						.getJSONArray("genres");

				if (hierarchiesJsonArray.length() > 0) {
					menuPieChart.setVisible(true);
				}

				int counter;
				for (counter = 0; counter < hierarchiesJsonArray.length(); counter++) {
					JSONObject hierarchyJsonObj = (JSONObject) hierarchiesJsonArray.get(counter);
//					JSONArray hierarchyTagsJsonArray = hierarchyJsonObj.getJSONArray("tags");
//					ArrayList<String> hierarchyTagNameList = new ArrayList<String>();

					String hierarchyName = hierarchyJsonObj.getString("name");
					JSONArray hierarchyTagsJsonArray = hierarchyJsonObj.getJSONArray("tags");
					ArrayList<String> hierarchyTagNameList = new ArrayList<String>();

					int i;
					for (i = 0; i < hierarchyTagsJsonArray.length(); i++) {
						hierarchyTagNameList.add(hierarchyTagsJsonArray.getString(i));
//						allTags.add(hierarchyTagsJsonArray.getString(i));
					}

//					paneList.add(new GenrePieChartTitledPane(hierarchyName, hierarchyTagNameList));

					MenuItem item = new MenuItem(hierarchyName);

					item.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent e) {

							// TAG ITEM HANDLER SERVICE
							Service<Void> service = new Service<Void>() {
								@Override
								protected Task<Void> createTask() {
									return new Task<Void>() {
										@Override
										protected Void call() throws Exception {

											GenrePieChartTitledPane pane = new GenrePieChartTitledPane(hierarchyName,
													hierarchyTagNameList);

//											final CountDownLatch latch = new CountDownLatch(1);
											Platform.runLater(new Runnable() {
												@Override
												public void run() {
													try {
														accordionCharts.getPanes().add(pane);
													} finally {
//														latch.countDown();
													}
												}
											});
//											latch.await();
											// Keep with the background work
											return null;
										}
									};
								}
							};
							service.start();
						}
					});

					menuPieChart.getItems().add(item);
				}

			}
		} catch (IOException e) {

		}

	}

	public void refreshPieCharts(File file) {

		Logger.getLog().log(new LogEntry("refreshPieCharts"));

		Service<Void> service = new Service<Void>() {
			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					@Override
					protected Void call() throws Exception {

						String jsonString = null;
						if (file != null) {

							BufferedReader br = new BufferedReader(new FileReader(file));
							StringBuilder sb = new StringBuilder();
							String jsonLine = br.readLine();
							while (jsonLine != null) {
								sb.append(jsonLine);
								jsonLine = br.readLine();
							}

							br.close();

							jsonString = sb.toString();
							Logger.getLog().logInfo(new InfoEntry("refreshPieCharts").addArg("json read"));
						}

						JSONObject rootParsedJsonObj = new JSONObject(jsonString);

						JSONArray hierarchiesJsonArray = rootParsedJsonObj.getJSONObject("genrehierarchy")
								.getJSONArray("genres");
						JSONObject pieJson = rootParsedJsonObj.getJSONObject("pie");

						Logger.getLog().logInfo(new InfoEntry("refreshPieCharts").addArg("arrays parsed"));

						int counter;
						ArrayList<TitledPane> paneList = new ArrayList<TitledPane>();

						ArrayList<String> allTags = new ArrayList<String>();

						for (counter = 0; counter < hierarchiesJsonArray.length(); counter++) {
							JSONObject hierarchyJsonObj = (JSONObject) hierarchiesJsonArray.get(counter);
							JSONArray hierarchyTagsJsonArray = hierarchyJsonObj.getJSONArray("tags");
							ArrayList<String> hierarchyTagNameList = new ArrayList<String>();

							String hierarchyName = hierarchyJsonObj.getString("name");

							int i;
							for (i = 0; i < hierarchyTagsJsonArray.length(); i++) {
								hierarchyTagNameList.add(hierarchyTagsJsonArray.getString(i));
								allTags.add(hierarchyTagsJsonArray.getString(i));
							}

							paneList.add(new GenrePieChartTitledPane(hierarchyName, hierarchyTagNameList));
						}

						JSONArray totalPieTags = pieJson.getJSONArray("tags");
						int i;
						for (i = 0; i < totalPieTags.length(); i++) {
							allTags.add((totalPieTags).getString(i));
						}
						paneList.add(new PieChartTitledPane("total", allTags));

//						final CountDownLatch latch = new CountDownLatch(1);
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								try {
									accordionCharts.getPanes().clear();
									int i;
									for (i = 0; i < paneList.size(); i++) {
										accordionCharts.getPanes().add(paneList.get(i));
									}
								} finally {
//									latch.countDown();
								}
							}
						});
//						latch.await();
						return null;
					}
				};
			}
		};
		service.start();
	}

	public void addTab(Tab tab) {
		tabPane.getTabs().add(tab);
		SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();

		selectionModel.select(tab);
	}

	@FXML
	protected void handleKeyShortcut(KeyEvent event) throws IOException {

		if (event.getCode() == KeyCode.F5) {
			refresh();
		}
	}

	@FXML
	protected void handleChangeUsername(ActionEvent event) throws IOException {
		Service<Void> service = new Service<Void>() {
			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					@Override
					protected Void call() throws Exception {

						String username = JOptionPane.showInputDialog("enter username:");
						if (username != null) {
							Reference.setUserName(username);

							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									refresh();
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
	protected void handleCurrentTrack(ActionEvent event) throws IOException {
		addLastTrackTab();
	}

	@FXML
	protected void handleRefreshPieChart(ActionEvent event) throws IOException {

		File file = null;
		String path = null;
		if (new File("./piechart.json").isFile()) {
			file = new File("./piechart.json");
		} else {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("open pie chart json");
			fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
			file = fileChooser.showOpenDialog(FmFramework.getStage());
		}

		refreshPieCharts(file);

	}

	@FXML
	protected void handleCreateList(ActionEvent event) {

		try {
			Tab tab = new FMObjListEditTab();
			addTab(tab);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	protected void handleScrobble(ActionEvent event) throws IOException {
//		Album album = sarsoo.fmframework.jframe.Getter.getAlbum();
//		if (album != null) {
//			Track track = sarsoo.fmframework.jframe.Getter.getTrack(album);
//
//		}
	}

	@FXML
	protected void handleOpenConsole(ActionEvent event) {
		addTab(new ConsoleTab());
	}

	@FXML
	protected void handleScrobbleChart(ActionEvent event) {
		try {
			addTab(new ScrobbleChartTab());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
			try {
				addTab(new FMObjListEditTab(list));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	protected void handleOpenEdit(ActionEvent event) {
		addTab(new ConsoleTab());
	}

	private ArrayList<Tag> tags;

	@FXML
	private Label labelStatsScrobblesToday;

	@FXML
	private Label labelStatsUsername;

	@FXML
	private Label labelStatsScrobblesTotal;
//
//	@FXML
//	private PieChart pieChartGenres;

	@FXML
	private TabPane tabPane;

	@FXML
	private Menu menuTag;

	@FXML
	private Menu menuPieChart;

	@FXML
	private Menu menuChart;
//
//	@FXML
//	private PieChart pieChartRap;
//
//	@FXML
//	private PieChart pieChartRapTotal;
//	
//	@FXML
//	private PieChart pieChartRock;
//
//	@FXML
//	private PieChart pieChartRockTotal;

	@FXML
	private Accordion accordionCharts;

	@FXML
	private TitledPane titledPaneGenres;

	@FXML
	private TitledPane titledPaneRap;

	@FXML
	private StackPane stackViewGenres;

}
