package sarsoo.fmframework.gui;

import java.awt.FlowLayout;
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

public class FMObjView extends JFrame{
	JPanel buttons = new JPanel();
	
	JLabel name = new JLabel();
	JLabel listeners = new JLabel();
	JLabel playCount = new JLabel();
	JLabel userPlayCount = new JLabel();
	
	JButton viewWiki = new JButton("View Wiki");
	JButton open = new JButton("View Online");
	JButton musicBrainz = new JButton("Open MusicBrainz");
	
	public FMObjView(FMObj obj) {
		super(obj.toString());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(5,0));
		setSize(300, 300);
//		setResizable(false);
		
		buttons.setLayout(new FlowLayout());

		buttons.add(open);
		if (obj.getWiki() != null)
			buttons.add(viewWiki);
		if(obj.getMbid() != null)
			buttons.add(musicBrainz);
		
		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
		
		name.setText(obj.getName());
		name.setHorizontalTextPosition(JLabel.CENTER);
		listeners.setText(numberFormat.format(obj.getListeners()) + " Listeners");
		playCount.setText(numberFormat.format(obj.getPlayCount()) + " Scrobbles");
		userPlayCount.setText(numberFormat.format(obj.getUserPlayCount()) + " Your Scrobbles");
		
		viewWiki.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				obj.getWiki().view(obj.getName());
			}
		});
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Network.openURL(obj.getUrl());
			}
		});
		musicBrainz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Network.openURL(obj.getMusicBrainzURL());;
			}
		});
		add(name);
		add(listeners);
		add(playCount);
		add(userPlayCount);
//		add(info);
		add(buttons);
		
		pack();
	}
	
	
}
