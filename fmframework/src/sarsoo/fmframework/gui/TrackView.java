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

import sarsoo.fmframework.music.Track;
import sarsoo.fmframework.net.Network;

public class TrackView extends JFrame {
	JPanel info = new JPanel();
	JPanel nameInfo = new JPanel();
	JPanel scrobbleInfo = new JPanel();
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
	JButton viewWiki = new JButton("View Wiki");
	JButton musicBrainz = new JButton("Open MusicBrainz");
	JButton genius = new JButton("Open Genius");

	public TrackView(Track track) {
		super(track.getName());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(3, 1));
//		 setSize(300, 300);
//		 setResizable(false);

		info.setLayout(new GridLayout(1,2));
		nameInfo.setLayout(new GridLayout(3,1));
		scrobbleInfo.setLayout(new GridLayout(3,1));
		buttons.setLayout(new FlowLayout());
		buttons2.setLayout(new FlowLayout());

		buttons.add(open);
		if (track.getMbid() != null) 
			buttons.add(musicBrainz);
		
		buttons2.add(viewArtist);
		if (track.getAlbum() != null)
			buttons2.add(viewAlbum);
		if (track.getWiki() != null)
			buttons2.add(viewWiki);
//		if (track.getArtist() != null)
			buttons.add(genius);

		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);

		name.setText(track.getName());
		if(track.getAlbum() != null)
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
		viewWiki.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				track.getWiki().view(track.getName());
			}
		});
		viewArtist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				track.getArtist().view();
			}
		});
		musicBrainz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Network.openURL(track.getMusicBrainzURL());
			}
		});
		viewAlbum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				track.getAlbum().view();
			}
		});
		genius.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Network.openURL(track.getLyricsURL());
			}
		});

		nameInfo.add(name);
		if(track.getAlbum() != null)
			nameInfo.add(album);
		nameInfo.add(artist);
		scrobbleInfo.add(listeners);
		scrobbleInfo.add(playCount);
		scrobbleInfo.add(userPlayCount);
		
		info.add(nameInfo);
		info.add(scrobbleInfo);
		
		add(info);
		add(buttons);
		add(buttons2);
		pack();
	}
}
