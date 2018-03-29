package sarsoo.fmframework.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import sarsoo.fmframework.music.FMObj;
import sarsoo.fmframework.net.Network;
import sarsoo.fmframework.util.FMObjList;

public class FMObjListView extends JFrame{

	public FMObjListView(FMObjList objects, String title) {
		super(title);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(objects.size() + 2,0));
		setResizable(false);
		
		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
		
		JPanel headerLabels = new JPanel();
		headerLabels.setLayout(new GridLayout(1, 5));
		headerLabels.add(new JLabel("Name"));
		headerLabels.add(new JLabel("User Scrobbles"));
		headerLabels.add(new JLabel("Total Scrobbles"));
		headerLabels.add(new JLabel(""));
		headerLabels.add(new JLabel(""));
		
		add(headerLabels);
		
		int counter;
		for(counter = 0; counter < objects.size(); counter++) {
			FMObj fmObj = objects.get(counter);
			JLabel name = new JLabel(fmObj.getName());
			
			int playCountString = fmObj.getUserPlayCount();
			
			JLabel userPlays;
			if(playCountString == 0)
				 userPlays = new JLabel("0");
			else
				userPlays = new JLabel(Integer.toString(fmObj.getUserPlayCount()));
			
			JLabel plays = new JLabel(numberFormat.format(fmObj.getPlayCount()));
			JButton openExternal = new JButton("Open Online");
			openExternal.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Network.openURL(fmObj.getUrl());
				}
			});
			JButton openInternal = new JButton("Open " + fmObj.getClass().getSimpleName());
			openInternal.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					fmObj.view();
				}
			});
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(1, 5));
			panel.add(name);
			panel.add(userPlays);
			panel.add(plays);
			panel.add(openInternal);
			panel.add(openExternal);
			
			add(panel);
		}
		JLabel totalScrobbles = new JLabel(numberFormat.format(objects.getTotalUserScrobbles()) + " Total Plays");
		add(totalScrobbles);
		pack();
	}
}
