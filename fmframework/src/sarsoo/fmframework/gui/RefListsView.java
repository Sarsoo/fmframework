package sarsoo.fmframework.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import sarsoo.fmframework.util.FMObjList;
import sarsoo.fmframework.util.Reference;

public class RefListsView extends JFrame {

	public RefListsView() {
		super("fmframework");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(5, 5));
		setSize(500, 500);
		setResizable(false);

		
		Reference.initGroupsList();
		ArrayList<FMObjList> groups = Reference.getGroups();
		int counter;
		for (counter = 0; counter < groups.size(); counter++) {
			FMObjList group = groups.get(counter);
			JButton view = new JButton("View " + group.getGroupName());
			view.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					group.view();
				}
			});
			add(view);
		}
	}
}
