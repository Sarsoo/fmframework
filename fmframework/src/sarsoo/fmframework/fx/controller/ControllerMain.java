package sarsoo.fmframework.fx.controller;

import java.io.File;
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
import javafx.stage.FileChooser;
import sarsoo.fmframework.file.ListPersister;
import sarsoo.fmframework.fx.AlbumTab;
import sarsoo.fmframework.fx.ArtistTab;
import sarsoo.fmframework.fx.ConsoleTab;
import sarsoo.fmframework.fx.FMObjListEditTab;
import sarsoo.fmframework.fx.TextAreaConsole;
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
import javafx.scene.paint.Color;

public class ControllerMain {

	@FXML
	public void initialize() {
		Reference.setUserName("sarsoo");

		Reference.setVerbose(TextAreaConsole.getInstance());

		refresh();
	}

	public void refresh() {

		Service<Void> service = new Service<Void>() {
			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					@Override
					protected Void call() throws Exception {
						NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);

						String scrobblesToday = numberFormat.format(Getter.getScrobblesToday(Reference.getUserName()));
						String scrobbles = numberFormat.format(Getter.getScrobbles(Reference.getUserName()));

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

									// refreshPieCharts();
									refreshTagMenu();

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

	public void refreshTagMenu() {
		tags = Getter.getUserTags(Reference.getUserName());

		Collections.sort(tags);

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

									FMObjListTab tab = new FMObjListTab(
											Getter.getUserTag(Reference.getUserName(), name));

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
						FMObjList soulFunk = Getter.getUserTag(Reference.getUserName(), "soulfunk");
						FMObjList punk = Getter.getUserTag(Reference.getUserName(), "punk");

						int rapTotal = rap.getTotalUserScrobbles() + classicRap.getTotalUserScrobbles()
								+ grime.getTotalUserScrobbles();

						ObservableList<PieChart.Data> rapData = FXCollections
								.observableArrayList(
										new PieChart.Data(
												String.format("rap %d%%",
														(int) rap.getTotalUserScrobbles() * 100 / rapTotal),
												rap.getTotalUserScrobbles()),
										new PieChart.Data(
												String.format("classic rap %d%%",
														(int) classicRap.getTotalUserScrobbles() * 100 / rapTotal),
												classicRap.getTotalUserScrobbles()),
										new PieChart.Data(
												String.format("grime %d%%",
														(int) grime.getTotalUserScrobbles() * 100 / rapTotal),
												grime.getTotalUserScrobbles()));

						Collections.sort(rapData, new Comparator<PieChart.Data>() {

							@Override
							public int compare(Data arg0, Data arg1) {
								return (int) (arg1.getPieValue() - arg0.getPieValue());
							}
						});

						int other = total - rapTotal;

						ObservableList<PieChart.Data> rapTotalData = FXCollections
								.observableArrayList(
										new PieChart.Data(
												String.format("rap %d%%",
														(int) rap.getTotalUserScrobbles() * 100 / total),
												rap.getTotalUserScrobbles()),
										new PieChart.Data(
												String.format("classic rap %d%%",
														(int) classicRap.getTotalUserScrobbles() * 100 / total),
												classicRap.getTotalUserScrobbles()),
										new PieChart.Data(
												String.format("grime %d%%",
														(int) grime.getTotalUserScrobbles() * 100 / total),
												grime.getTotalUserScrobbles()));

						Collections.sort(rapTotalData, new Comparator<PieChart.Data>() {

							@Override
							public int compare(Data arg0, Data arg1) {
								return (int) (arg1.getPieValue() - arg0.getPieValue());
							}
						});

						rapTotalData
								.add(new PieChart.Data(String.format("other %d%%", (int) other * 100 / total), other));

						int rockTotal = rock.getTotalUserScrobbles() + classicRock.getTotalUserScrobbles()
								+ indie.getTotalUserScrobbles() + popPunk.getTotalUserScrobbles()
								+ punk.getTotalUserScrobbles();

						ObservableList<PieChart.Data> rockData = FXCollections
								.observableArrayList(
										new PieChart.Data(
												String.format("rock %d%%",
														(int) rock.getTotalUserScrobbles() * 100 / rockTotal),
												rock.getTotalUserScrobbles()),
										new PieChart.Data(
												String.format("classic rock %d%%",
														(int) classicRock.getTotalUserScrobbles() * 100 / rockTotal),
												classicRock.getTotalUserScrobbles()),
										new PieChart.Data(
												String.format("indie %d%%",
														(int) indie.getTotalUserScrobbles() * 100 / rockTotal),
												indie.getTotalUserScrobbles()),
										new PieChart.Data(
												String.format("pop punk %d%%",
														(int) popPunk.getTotalUserScrobbles() * 100 / rockTotal),
												popPunk.getTotalUserScrobbles()),
										new PieChart.Data(
												String.format("punk %d%%",
														(int) punk.getTotalUserScrobbles() * 100 / rockTotal),
												punk.getTotalUserScrobbles()));

						Collections.sort(rockData, new Comparator<PieChart.Data>() {

							@Override
							public int compare(Data arg0, Data arg1) {
								return (int) (arg1.getPieValue() - arg0.getPieValue());
							}
						});

						int rockOther = total - rockTotal;

						ObservableList<PieChart.Data> rockTotalData = FXCollections
								.observableArrayList(
										new PieChart.Data(
												String.format("rock %d%%",
														(int) rock.getTotalUserScrobbles() * 100 / total),
												rock.getTotalUserScrobbles()),
										new PieChart.Data(
												String.format("classic rock %d%%",
														(int) classicRock.getTotalUserScrobbles() * 100 / total),
												classicRock.getTotalUserScrobbles()),
										new PieChart.Data(
												String.format("indie %d%%",
														(int) indie.getTotalUserScrobbles() * 100 / total),
												indie.getTotalUserScrobbles()),
										new PieChart.Data(
												String.format("pop punk %d%%",
														(int) popPunk.getTotalUserScrobbles() * 100 / total),
												popPunk.getTotalUserScrobbles()),
										new PieChart.Data(
												String.format("punk %d%%",
														(int) punk.getTotalUserScrobbles() * 100 / total),
												punk.getTotalUserScrobbles()));

						Collections.sort(rockTotalData, new Comparator<PieChart.Data>() {

							@Override
							public int compare(Data arg0, Data arg1) {
								return (int) (arg1.getPieValue() - arg0.getPieValue());
							}
						});

						rockTotalData.add(new PieChart.Data(String.format("other %d%%", (int) rockOther * 100 / total),
								rockOther));

						int totalOther = total - rap.getTotalUserScrobbles() - classicRap.getTotalUserScrobbles()
								- grime.getTotalUserScrobbles() - classicRock.getTotalUserScrobbles()
								- popPunk.getTotalUserScrobbles() - electronic.getTotalUserScrobbles()
								- metal.getTotalUserScrobbles() - indie.getTotalUserScrobbles()
								- rock.getTotalUserScrobbles() - jazz.getTotalUserScrobbles()
								- blues.getTotalUserScrobbles() - core.getTotalUserScrobbles()
								- rnb.getTotalUserScrobbles() - soulFunk.getTotalUserScrobbles()
								- punk.getTotalUserScrobbles();

						ObservableList<PieChart.Data> genreData = FXCollections
								.observableArrayList(
										new PieChart.Data(
												String.format("rap %d%%",
														(int) rap.getTotalUserScrobbles() * 100 / total),
												rap.getTotalUserScrobbles()),
										new PieChart.Data(
												String.format("classic rap %d%%",
														(int) classicRap.getTotalUserScrobbles() * 100 / total),
												classicRap.getTotalUserScrobbles()),
										new PieChart.Data(
												String.format("grime %d%%",
														(int) grime.getTotalUserScrobbles() * 100 / total),
												grime.getTotalUserScrobbles()),

										new PieChart.Data(
												String.format("classic rock %d%%",
														(int) classicRock.getTotalUserScrobbles() * 100 / total),
												classicRock.getTotalUserScrobbles()),
										new PieChart.Data(
												String.format("pop punk %d%%",
														(int) popPunk.getTotalUserScrobbles() * 100 / total),
												popPunk.getTotalUserScrobbles()),
										new PieChart.Data(
												String.format("electronic %d%%",
														(int) electronic.getTotalUserScrobbles() * 100 / total),
												electronic.getTotalUserScrobbles()),
										new PieChart.Data(
												String.format("metal %d%%",
														(int) metal.getTotalUserScrobbles() * 100 / total),
												metal.getTotalUserScrobbles()),
										new PieChart.Data(
												String.format("indie %d%%",
														(int) indie.getTotalUserScrobbles() * 100 / total),
												indie.getTotalUserScrobbles()),
										new PieChart.Data(
												String.format("rock %d%%",
														(int) rock.getTotalUserScrobbles() * 100 / total),
												rock.getTotalUserScrobbles()),
										new PieChart.Data(
												String.format("jazz %d%%",
														(int) jazz.getTotalUserScrobbles() * 100 / total),
												jazz.getTotalUserScrobbles()),
										new PieChart.Data(
												String.format("blues %d%%",
														(int) blues.getTotalUserScrobbles() * 100 / total),
												blues.getTotalUserScrobbles()),
										new PieChart.Data(
												String.format("core %d%%",
														(int) core.getTotalUserScrobbles() * 100 / total),
												core.getTotalUserScrobbles()),
										new PieChart.Data(
												String.format("soul/funk %d%%",
														(int) soulFunk.getTotalUserScrobbles() * 100 / total),
												soulFunk.getTotalUserScrobbles()),
										new PieChart.Data(
												String.format("rnb %d%%",
														(int) rnb.getTotalUserScrobbles() * 100 / total),
												rnb.getTotalUserScrobbles()),
										new PieChart.Data(
												String.format("punk %d%%",
														(int) punk.getTotalUserScrobbles() * 100 / total),
												punk.getTotalUserScrobbles()));

						Collections.sort(genreData, new Comparator<PieChart.Data>() {

							@Override
							public int compare(Data arg0, Data arg1) {
								return (int) (arg1.getPieValue() - arg0.getPieValue());
							}
						});

						genreData.add(new PieChart.Data(String.format("other %d%%", (int) totalOther * 100 / total),
								totalOther));

						final CountDownLatch latch = new CountDownLatch(1);
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								try {
									pieChartRap.setData(rapData);

									pieChartRapTotal.setData(rapTotalData);
									
									pieChartRock.setData(rockData);

									pieChartRockTotal.setData(rockTotalData);

									pieChartGenres.setData(genreData);

									accordionCharts.setExpandedPane(titledPaneGenres);
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
	protected void handleKeyShortcut(KeyEvent event) throws IOException {

		if (event.getCode() == KeyCode.F5) {
			refresh();
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
	protected void handleCurrentTrack(ActionEvent event) throws IOException {
		Service<Void> service = new Service<Void>() {
			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					@Override
					protected Void call() throws Exception {

						Track track = Getter.getLastTrack();

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
	protected void handleRefreshPieChart(ActionEvent event) throws IOException {

		refreshPieCharts();

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
		Album album = sarsoo.fmframework.jframe.Getter.getAlbum();
		if (album != null) {
			Track track = sarsoo.fmframework.jframe.Getter.getTrack(album);

		}
	}

	@FXML
	protected void handleOpenConsole(ActionEvent event) {
		addTab(new ConsoleTab());
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

	@FXML
	private PieChart pieChartGenres;

	@FXML
	private TabPane tabPane;

	@FXML
	private Menu menuTag;

	@FXML
	private PieChart pieChartRap;

	@FXML
	private PieChart pieChartRapTotal;
	
	@FXML
	private PieChart pieChartRock;

	@FXML
	private PieChart pieChartRockTotal;

	@FXML
	private Accordion accordionCharts;

	@FXML
	private TitledPane titledPaneGenres;

	@FXML
	private TitledPane titledPaneRap;

	@FXML
	private StackPane stackViewGenres;

}
