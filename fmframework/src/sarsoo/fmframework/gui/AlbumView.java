package sarsoo.fmframework.gui;

import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import sarsoo.fmframework.music.Album;

public class AlbumView extends JFrame{
	JPanel info = new JPanel();
	JPanel buttons = new JPanel();
	JLabel name = new JLabel();
	JLabel listeners = new JLabel();
	JLabel playCount = new JLabel();
	JLabel userPlayCount = new JLabel();
	JButton open = new JButton("View Online");
	
	public AlbumView(Album album) {
		super(album.toString());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(5,0));
		setSize(300, 200);
		setResizable(false);
		info.setLayout(new GridLayout());
		buttons.setLayout(new FlowLayout());
//		info.add(name);
//		info.add(listeners);
//		info.add(playCount);
//		info.add(userPlayCount);
		buttons.add(open);
		
		name.setText(album.getName());
		name.setHorizontalTextPosition(JLabel.CENTER);
		listeners.setText(album.getListeners() + " Listeners");
		playCount.setText(album.getPlayCount() + " Scrobbles");
		userPlayCount.setText(album.getUserPlayCount() + " Your Scrobbles");
		
		
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					  Desktop desktop = java.awt.Desktop.getDesktop();
					  URI oURL = new URI(album.getUrl());
					  desktop.browse(oURL);
					} catch (Exception e) {
					  e.printStackTrace();
					}
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
