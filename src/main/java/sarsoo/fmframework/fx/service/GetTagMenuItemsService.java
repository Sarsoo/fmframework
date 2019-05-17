package sarsoo.fmframework.fx.service;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import sarsoo.fmframework.fx.FmFramework;
import sarsoo.fmframework.fx.tab.FMObjListTab;
import sarsoo.fmframework.log.Logger;
import sarsoo.fmframework.log.entry.ErrorEntry;
import sarsoo.fmframework.music.Tag;

public class GetTagMenuItemsService extends Service<ArrayList<MenuItem>> {

	private ArrayList<Tag> tags;

	public GetTagMenuItemsService(ArrayList<Tag> tags) {
		super();
		this.tags = tags;
	}

	@Override
	protected Task<ArrayList<MenuItem>> createTask() {
		return new Task<ArrayList<MenuItem>>() {

			@Override
			protected ArrayList<MenuItem> call() throws Exception {

				ArrayList<MenuItem> items = new ArrayList<MenuItem>();

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

											FMObjListTab tab = new FMObjListTab(FmFramework.getTagPool().get(name));

											Platform.runLater(new Runnable() {
												@Override
												public void run() {
													FmFramework.getController().addTab(tab);
												}
											});
											return null;
										}
									};
								}
							};
							service.start();
						}
					});

					items.add(item);
				}

				return items;
			}

		};
	}

	@Override
	protected void failed() {
		super.failed();
		
		Logger.getLog().logError(new ErrorEntry("failed to get tag menu items"));
		
	}

}
