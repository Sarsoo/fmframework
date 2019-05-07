package sarsoo.fmframework.fx.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import sarsoo.fmframework.fx.FmFramework;
import sarsoo.fmframework.fx.chart.GenrePieChartTitledPane;
import sarsoo.fmframework.fx.chart.PieChartTitledPane;
import sarsoo.fmframework.log.Logger;
import sarsoo.fmframework.log.entry.InfoEntry;
import sarsoo.fmframework.log.entry.LogEntry;

public class GenrePieChartPaneController {

	@FXML
	BorderPane borderPane;

	@FXML
	Accordion accordionCharts;

	@FXML
	ToolBar toolbar;

	@FXML
	Button buttonLoadAll;

	@FXML
	Button buttonLoad;

	@FXML
	ChoiceBox choiceBox;

	ArrayList<GenreHierarchy> genreHierarchies = new ArrayList<GenreHierarchy>();

	@FXML
	public void initialize() {
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
//					menuPieChart.setVisible(true);
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

					choiceBox.getItems().add(new GenreHierarchy(hierarchyName, hierarchyTagNameList));
//					paneList.add(new GenrePieChartTitledPane(hierarchyName, hierarchyTagNameList));
				}

			}
		} catch (IOException e) {

		}
	}

	@FXML
	protected void handleLoad(ActionEvent event) {
		GenreHierarchy hier = (GenreHierarchy) choiceBox.getValue();

		if (hier != null) {
			Service<Void> service = new Service<Void>() {
				@Override
				protected Task<Void> createTask() {
					return new Task<Void>() {
						@Override
						protected Void call() throws Exception {

							GenrePieChartTitledPane pane = new GenrePieChartTitledPane(hier.getName(),
									hier.getTagNames());

							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									accordionCharts.getPanes().add(pane);
								}
							});
							return null;
						}
					};
				}
			};
			service.start();
		}
	}

	@FXML
	protected void handleLoadAll(ActionEvent event) {

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

}

class GenreHierarchy {

	private String name;
	private ArrayList<String> tagNames;

	public GenreHierarchy(String name, ArrayList<String> tagNames) {
		this.name = name;
		this.tagNames = tagNames;
	}

	public String getName() {
		return name;
	}

	public ArrayList<String> getTagNames() {
		return tagNames;
	}

	@Override
	public String toString() {
		return name;
	}

}
