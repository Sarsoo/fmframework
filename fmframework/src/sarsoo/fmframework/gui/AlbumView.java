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

import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.net.Network;

public class AlbumView extends JFrame{
	JPanel info = new JPanel();
	JPanel buttons = new JPanel();
	JPanel buttons2 = new JPanel();
	JLabel name = new JLabel();
	JLabel artist = new JLabel();
	JLabel listeners = new JLabel();
	JLabel playCount = new JLabel();
	JLabel userPlayCount = new JLabel();
	JButton open = new JButton("View Online");
	JButton viewArtist = new JButton("View Artist");
	JButton musicBeanz = new JButton("Open MusicBeanz");
	JButton rym = new JButton("Open RYM");
	
	public AlbumView(Album album) {
		super(album.getName());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(7,0));
		setSize(300, 300);
		setResizable(false);
		info.setLayout(new GridLayout());
		buttons.setLayout(new FlowLayout());
		buttons2.setLayout(new FlowLayout());
//		info.add(name);
//		info.add(listeners);
//		info.add(playCount);
//		info.add(userPlayCount);
		buttons.add(open);
		buttons.add(viewArtist);
		buttons2.add(musicBeanz);
		buttons2.add(rym);
		
		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
		
		name.setText(album.getName());
		name.setHorizontalTextPosition(JLabel.CENTER);
		artist.setText(album.getArtist().getName());
		listeners.setText(numberFormat.format(album.getListeners()) + " Listeners");
		playCount.setText(numberFormat.format(album.getPlayCount()) + " Scrobbles");
		userPlayCount.setText(numberFormat.format(album.getUserPlayCount()) + " Your Scrobbles");
		
		
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Network.openURL(album.getUrl());
			}
		});
		viewArtist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				album.getArtist().view();
			}
		});
		musicBeanz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Network.openURL(album.getMusicBeanzURL());;
			}
		});
		rym.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Network.openURL(album.getRymURL());;
			}
		});
		add(name);
		add(artist);
		add(listeners);
		add(playCount);
		add(userPlayCount);
//		add(info);
		add(buttons);
		add(buttons2);
	}
}
