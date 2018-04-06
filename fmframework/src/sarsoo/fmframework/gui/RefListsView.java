package sarsoo.fmframework.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import sarsoo.fmframework.util.FMObjList;
import sarsoo.fmframework.util.Getter;
import sarsoo.fmframework.util.Reference;

public class RefListsView extends JFrame {

	public RefListsView() {
		super("fmframework");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(3, 2));
		setSize(300, 300);
		setResizable(false);

		
//		Reference.initGroupsList();
//		ArrayList<FMObjList> groups = Reference.getGroups();
//		int counter;
//		for (counter = 0; counter < groups.size(); counter++) {
//			FMObjList group = groups.get(counter);
//			JButton view = new JButton("View " + group.getGroupName());
//			view.addActionListener(new ActionListener() {
//				public void actionPerformed(ActionEvent arg0) {
//					group.view();
//				}
//			});
//			add(view);
//		}
		
		JButton viewTDE = new JButton("TDE");
		viewTDE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Reference.getTDE().view();
			}
		});
		add(viewTDE);
		
		JButton viewBPHQ = new JButton("BPHQ");
		viewBPHQ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Reference.getBB().view();
			}
		});
		add(viewBPHQ);
		
		JButton viewDre = new JButton("Dre");
		viewDre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Reference.getDre().view();
			}
		});
		add(viewDre);
		JButton viewWu = new JButton("Wu");
		viewWu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Reference.getWu().view();
			}
		});
		add(viewWu);
		JButton viewHopeless = new JButton("Hopeless");
		viewHopeless.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Reference.getHopeless().view();
			}
		});
		add(viewHopeless);
		JButton viewSaturation = new JButton("Saturation");
		viewSaturation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Reference.getSaturation().view();
			}
		});
		add(viewSaturation);
		
		JButton viewEmoTrio = new JButton("Emo Trio");
		viewEmoTrio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Reference.getEmoTrio().view();
			}
		});
		add(viewEmoTrio);
		
		JButton viewRockTag = new JButton("Rock Tag");
		viewRockTag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FMObjList list = Getter.getUserTag(Reference.getUserName(), "rock");
				list.view();
			}
		});
		add(viewRockTag);
		
		JButton viewRapTag = new JButton("Rap Tag");
		viewRapTag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FMObjList list = Getter.getUserTag(Reference.getUserName(), "rap");
				list.view();
			}
		});
		add(viewRapTag);
	}
}
