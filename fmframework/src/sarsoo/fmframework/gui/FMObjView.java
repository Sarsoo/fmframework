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
	JPanel info = new JPanel();
	JPanel buttons = new JPanel();
	JLabel name = new JLabel();
	JLabel listeners = new JLabel();
	JLabel playCount = new JLabel();
	JLabel userPlayCount = new JLabel();
	JButton open = new JButton("View Online");
	JButton musicBeanz = new JButton("Open MusicBeanz");
	
	public FMObjView(FMObj obj) {
		super(obj.toString());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(5,0));
		setSize(300, 300);
		setResizable(false);
		info.setLayout(new GridLayout());
		buttons.setLayout(new FlowLayout());
//		info.add(name);
//		info.add(listeners);
//		info.add(playCount);
//		info.add(userPlayCount);
		buttons.add(open);
		buttons.add(musicBeanz);
		
		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
		
		name.setText(obj.getName());
		name.setHorizontalTextPosition(JLabel.CENTER);
		listeners.setText(numberFormat.format(obj.getListeners()) + " Listeners");
		playCount.setText(numberFormat.format(obj.getPlayCount()) + " Scrobbles");
		userPlayCount.setText(numberFormat.format(obj.getUserPlayCount()) + " Your Scrobbles");
		
		
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Network.openURL(obj.getUrl());
			}
		});
		musicBeanz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Network.openURL(obj.getMusicBeanzURL());;
			}
		});
		add(name);
		add(listeners);
		add(playCount);
		add(userPlayCount);
//		add(info);
		add(buttons);
	}
	
	
}
