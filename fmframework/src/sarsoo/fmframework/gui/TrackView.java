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
import sarsoo.fmframework.music.Track;
import sarsoo.fmframework.net.Network;

public class TrackView extends JFrame{
	JPanel info = new JPanel();
	JPanel buttons = new JPanel();
	JPanel buttons2 = new JPanel();
	JLabel name = new JLabel();
	JLabel album = new JLabel();
	JLabel artist = new JLabel();
	JLabel listeners = new JLabel();
	JLabel playCount = new JLabel();
	JLabel userPlayCount = new JLabel();
	JButton open = new JButton("View Online");
	JButton viewArtist = new JButton("View Artist");
	JButton viewAlbum = new JButton("View Album");
	JButton musicBrainz = new JButton("Open MusicBrainz");
	
	public TrackView(Track track) {
		super(track.getName());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(8,0));
		setSize(300, 300);
		setResizable(false);
		info.setLayout(new GridLayout());
		buttons.setLayout(new FlowLayout());
		buttons2.setLayout(new FlowLayout());
		
		buttons.add(open);
		buttons.add(musicBrainz);
		buttons2.add(viewArtist);
		buttons2.add(viewAlbum);
		
		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
		
		name.setText(track.getName());
		album.setText(track.getAlbum().getName());
		artist.setText(track.getArtist().getName());
		listeners.setText(numberFormat.format(track.getListeners()) + " Listeners");
		playCount.setText(numberFormat.format(track.getPlayCount()) + " Scrobbles");
		userPlayCount.setText(numberFormat.format(track.getUserPlayCount()) + " Your Scrobbles");
		
		
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Network.openURL(track.getUrl());
			}
		});
		viewArtist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				track.getArtist().view();
			}
		});
		musicBrainz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Network.openURL(track.getMusicBrainzURL());;
			}
		});
		viewAlbum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				track.getAlbum().view();
			}
		});
		add(name);
		add(album);
		add(artist);
		add(listeners);
		add(playCount);
		add(userPlayCount);
		add(buttons);
		add(buttons2);
	}
}
