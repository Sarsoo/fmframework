package sarsoo.fmframework.fx.controller;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.text.Text;
import sarsoo.fmframework.fx.AlbumTab;
import sarsoo.fmframework.fx.ArtistTab;
import sarsoo.fmframework.fx.FMObjListTab;
import sarsoo.fmframework.fx.FmFramework;
import sarsoo.fmframework.fx.TrackTab;
import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.music.Artist;
import sarsoo.fmframework.music.Tag;
import sarsoo.fmframework.music.Track;
import sarsoo.fmframework.util.FMObjList;
import sarsoo.fmframework.util.Getter;
import sarsoo.fmframework.util.Reference;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.chart.*;
import javafx.scene.chart.PieChart.Data;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.Parent;

import javafx.scene.layout.*;

import javafx.concurrent.*;
import javafx.application.Platform;

public class ControllerMain {

	@FXML
	private Label labelStatsScrobblesToday;

	@FXML
	private Label labelStatsUsername;

	@FXML
	private Label labelStatsScrobblesTotal;

	@FXML
	private PieChart pieChartGenres;

	@FXML
	private TabPane tabPane;

	@FXML
	public void initialize() {
		Reference.setUserName("sarsoo");

		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);

		Service<Void> service = new Service<Void>() {
			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					@Override
					protected Void call() throws Exception {

						String scrobblesToday = numberFormat.format(Getter.getScrobblesToday(Reference.getUserName()));
						String scrobbles = numberFormat.format(Getter.getScrobbles(Reference.getUserName()));
						tags = Getter.getUserTags(Reference.getUserName());

						TrackTab tab = new TrackTab(Getter.getLastTrack());
						
						final CountDownLatch latch = new CountDownLatch(1);
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								try {

									labelStatsScrobblesToday.setText(scrobblesToday);
									labelStatsUsername.setText(Reference.getUserName());
									labelStatsScrobblesTotal.setText(scrobbles);
									
									addTab(tab);

									refreshPieCharts();

									int counter;
									for (counter = 0; counter < tags.size(); counter++) {

										String name = tags.get(counter).getName().toLowerCase();

										// System.out.println(name);

										MenuItem item = new MenuItem(name);

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

																FMObjListTab tab = new FMObjListTab(Getter
																		.getUserTag(Reference.getUserName(), name));

																final CountDownLatch latch = new CountDownLatch(1);
																Platform.runLater(new Runnable() {
																	@Override
																	public void run() {
																		try {
																			tabPane.getTabs().add(tab);
																		} finally {
																			latch.countDown();
																		}
																	}
																});
																latch.await();
																// Keep with the background work
																return null;
															}
														};
													}
												};
												service.start();
											}
										});

										menuTag.getItems().add(item);
									}

								} finally {
									latch.countDown();
								}
							}
						});
						latch.await();
						// Keep with the background work
						return null;
					}
				};
			}
		};
		service.start();

	}

	public void updateGenrePieChart() {

		String[] names = { "rock", "rap", "classic rock", "pop punk", "electronic", "metal", "grime", "classic rap",
				"indie", "jazz", "blues", "core" };

		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(

				new PieChart.Data("rock", 5), new PieChart.Data("rock", 5), new PieChart.Data("rock", 5));
		pieChartGenres = new PieChart(pieChartData);

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

							final CountDownLatch latch = new CountDownLatch(1);
							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									try {
										tabPane.getTabs().add(tab);
									} finally {
										latch.countDown();
									}
								}
							});
							latch.await();
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

							final CountDownLatch latch = new CountDownLatch(1);
							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									try {
										tabPane.getTabs().add(tab);
									} finally {
										latch.countDown();
									}
								}
							});
							latch.await();
						}
						// Keep with the background work
						return null;
					}
				};
			}
		};
		service.start();

		// tabPane.getTabs().add(new ArtistTab(Getter.getArtist()));
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

							final CountDownLatch latch = new CountDownLatch(1);
							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									try {
										tabPane.getTabs().add(tab);
									} finally {
										latch.countDown();
									}
								}
							});
							latch.await();
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
	protected void handleScrobble(ActionEvent event) throws IOException {
		Album album = sarsoo.fmframework.jframe.Getter.getAlbum();
		if (album != null) {
			Track track = sarsoo.fmframework.jframe.Getter.getTrack(album);

		}
	}

	@FXML
	private Menu menuTag;

	private ArrayList<Tag> tags;

	// @FXML
	// protected void handleTagClick(ActionEvent event) throws IOException {
	// // System.out.println("clicked");
	//
	// if (tags == null) {
	// // System.out.println("list null");
	//
	// tags = Getter.getUserTags(Reference.getUserName());
	//
	// int counter;
	// for (counter = 0; counter < tags.size(); counter++) {
	//
	// String name = tags.get(counter).getName().toLowerCase();
	//
	// // System.out.println(name);
	//
	// MenuItem item = new MenuItem(name);
	//
	// item.setOnAction(new EventHandler<ActionEvent>() {
	// @Override
	// public void handle(ActionEvent e) {
	// try {
	// FMObjListTab tab = new
	// FMObjListTab(Getter.getUserTag(Reference.getUserName(), name));
	//
	// tabPane.getTabs().add(tab);
	// System.out.println("tab added");
	// } catch (IOException e1) {
	// e1.printStackTrace();
	// }
	// }
	// });
	//
	// menuTag.getItems().add(item);
	// }
	// }
	// }

	@FXML
	protected void handleKeyShortcut(KeyEvent event) throws IOException {

		if (event.getCode() == KeyCode.F5) {
			refresh();
			// System.out.println("refreshed");
		}
	}

	public void refresh() {
		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);

		Service<Void> service = new Service<Void>() {
			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					@Override
					protected Void call() throws Exception {

						String scrobblesToday = numberFormat.format(Getter.getScrobblesToday(Reference.getUserName()));
						String scrobbles = numberFormat.format(Getter.getScrobbles(Reference.getUserName()));
						tags = Getter.getUserTags(Reference.getUserName());

						final CountDownLatch latch = new CountDownLatch(1);
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								try {

									labelStatsScrobblesToday.setText(scrobblesToday);
									labelStatsUsername.setText(Reference.getUserName());
									labelStatsScrobblesTotal.setText(scrobbles);

//									refreshPieCharts();
									menuTag.getItems().clear();

									int counter;
									for (counter = 0; counter < tags.size(); counter++) {

										String name = tags.get(counter).getName().toLowerCase();

										// System.out.println(name);

										MenuItem item = new MenuItem(name);

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

																FMObjListTab tab = new FMObjListTab(Getter
																		.getUserTag(Reference.getUserName(), name));

																final CountDownLatch latch = new CountDownLatch(1);
																Platform.runLater(new Runnable() {
																	@Override
																	public void run() {
																		try {
																			tabPane.getTabs().add(tab);
																		} finally {
																			latch.countDown();
																		}
																	}
																});
																latch.await();
																// Keep with the background work
																return null;
															}
														};
													}
												};
												service.start();
											}
										});

										menuTag.getItems().add(item);
									}

								} finally {
									latch.countDown();
								}
							}
						});
						latch.await();
						// Keep with the background work
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
	private PieChart pieChartRap;

	@FXML
	private PieChart pieChartRapTotal;

	@FXML
	private Accordion accordionCharts;

	@FXML
	private TitledPane titledPaneGenres;

	@FXML
	private TitledPane titledPaneRap;

	@FXML
	protected void handleRefreshPieChart(ActionEvent event) throws IOException {

		refreshPieCharts();

	}

	public void refreshPieCharts() {
		Service<Void> service = new Service<Void>() {
			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					@Override
					protected Void call() throws Exception {
						
						int total = Getter.getScrobbles(Reference.getUserName());

						FMObjList rap = Getter.getUserTag(Reference.getUserName(), "rap");
						FMObjList classicRap = Getter.getUserTag(Reference.getUserName(), "classic rap");
						FMObjList grime = Getter.getUserTag(Reference.getUserName(), "grime");
						
						FMObjList classicRock = Getter.getUserTag(Reference.getUserName(), "classic rock");
						FMObjList popPunk = Getter.getUserTag(Reference.getUserName(), "pop punk");
						FMObjList electronic = Getter.getUserTag(Reference.getUserName(), "electronic");
						FMObjList metal = Getter.getUserTag(Reference.getUserName(), "metal");
						FMObjList indie = Getter.getUserTag(Reference.getUserName(), "indie");
						FMObjList rock = Getter.getUserTag(Reference.getUserName(), "rock");
						FMObjList jazz = Getter.getUserTag(Reference.getUserName(), "jazz");
						FMObjList blues = Getter.getUserTag(Reference.getUserName(), "blues");
						FMObjList core = Getter.getUserTag(Reference.getUserName(), "core");
						FMObjList rnb = Getter.getUserTag(Reference.getUserName(), "rnb");
						
						int rapTotal = rap.getTotalUserScrobbles() - classicRap.getTotalUserScrobbles()
								- grime.getTotalUserScrobbles();

						ObservableList<PieChart.Data> rapData = FXCollections.observableArrayList(
								new PieChart.Data("rap", rapTotal),
								new PieChart.Data("classic rap", classicRap.getTotalUserScrobbles()),
								new PieChart.Data("grime", grime.getTotalUserScrobbles()));
						
						Collections.sort(rapData, new Comparator<PieChart.Data>() {

							@Override
							public int compare(Data arg0, Data arg1) {
								return (int) (arg1.getPieValue() - arg0.getPieValue());
							}
						});
						

						int other = total - rap.getTotalUserScrobbles();

						ObservableList<PieChart.Data> rapTotalData = FXCollections.observableArrayList(
								new PieChart.Data("rap", rapTotal),
								new PieChart.Data("classic rap", classicRap.getTotalUserScrobbles()),
								new PieChart.Data("grime", grime.getTotalUserScrobbles()));
						
						Collections.sort(rapTotalData, new Comparator<PieChart.Data>() {

							@Override
							public int compare(Data arg0, Data arg1) {
								return (int) (arg1.getPieValue() - arg0.getPieValue());
							}
						});
						
						rapTotalData.add(new PieChart.Data("other", other));
						
						int totalOther = total - rap.getTotalUserScrobbles()
								- classicRock.getTotalUserScrobbles()
								- popPunk.getTotalUserScrobbles()
								- electronic.getTotalUserScrobbles()
								- metal.getTotalUserScrobbles()
								- indie.getTotalUserScrobbles()
								- rock.getTotalUserScrobbles()
								- jazz.getTotalUserScrobbles()
								- blues.getTotalUserScrobbles()
								- core.getTotalUserScrobbles()
								- rnb.getTotalUserScrobbles();
						
						ObservableList<PieChart.Data> genreData = FXCollections.observableArrayList(
								new PieChart.Data("rap", rapTotal),
								new PieChart.Data("classic rap", classicRap.getTotalUserScrobbles()),
								new PieChart.Data("grime", grime.getTotalUserScrobbles()),
								
								new PieChart.Data("classic rock", classicRock.getTotalUserScrobbles()),
								new PieChart.Data("pop punk", popPunk.getTotalUserScrobbles()),
								new PieChart.Data("electronic", electronic.getTotalUserScrobbles()),
								new PieChart.Data("metal", metal.getTotalUserScrobbles()),
								new PieChart.Data("indie", indie.getTotalUserScrobbles()),
								new PieChart.Data("rock", rock.getTotalUserScrobbles()),
								new PieChart.Data("jazz", jazz.getTotalUserScrobbles()),
								new PieChart.Data("blues", blues.getTotalUserScrobbles()),
								new PieChart.Data("core", core.getTotalUserScrobbles()),
								new PieChart.Data("rnb", rnb.getTotalUserScrobbles()));
						
						Collections.sort(genreData, new Comparator<PieChart.Data>() {

							@Override
							public int compare(Data arg0, Data arg1) {
								return (int) (arg1.getPieValue() - arg0.getPieValue());
							}
						});
						
						genreData.add(new PieChart.Data("other", totalOther));

						final CountDownLatch latch = new CountDownLatch(1);
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								try {
									pieChartRap.setData(rapData);

									pieChartRapTotal.setData(rapTotalData);
									
									pieChartGenres.setData(genreData);

									accordionCharts.setExpandedPane(titledPaneRap);
								} finally {
									latch.countDown();
								}
							}
						});
						latch.await();
						// Keep with the background work
						return null;
					}
				};
			}
		};
		service.start();
	}

}
