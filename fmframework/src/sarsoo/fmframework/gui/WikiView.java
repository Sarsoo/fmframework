package sarsoo.fmframework.gui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.music.FMObj;
import sarsoo.fmframework.music.Track;
import sarsoo.fmframework.music.Wiki;
import sarsoo.fmframework.net.Network;

public class WikiView extends JFrame {
//	JPanel date = new JPanel();
//	JPanel content = new JPanel();
//	JPanel trackListPanel = new JPanel();

//	JLabel dateLabel = new JLabel();
	JLabel contentLabel = new JLabel();
//	JLabel listeners = new JLabel();
//	JLabel playCount = new JLabel();
//	JLabel userPlayCount = new JLabel();
//
//	JButton open = new JButton("View Online");
//	JButton viewArtist = new JButton("View Artist");
//	JButton viewWiki = new JButton("View Wiki");
//	JButton musicBrainz = new JButton("Open MusicBrainz");
//	JButton rym = new JButton("Open RYM");

	public WikiView(Wiki wiki, String name) {
		super(name);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(1,1));
		setSize(500, 700);
//		setResizable(false);

//		buttons.setLayout(new FlowLayout());
//		buttons2.setLayout(new FlowLayout());
////		System.out.println(album.getName());
////		if (album.getTrackList() != null)
////			buttons2.setLayout(new GridLayout(album.getTrackList().size(), 1));
//
//		buttons.add(open);
//		buttons.add(viewArtist);
//		if (album.getWiki() != null)
//			buttons2.add(viewWiki);
//		if (album.getMbid() != null)
//			buttons2.add(musicBrainz);
//		buttons2.add(rym);
//
//		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
//
//		dateLabel.setText(wiki.getDate());
		contentLabel.setText("<html>" + wiki.getContent() + "<br><br>" + wiki.getDate() + "</html>");

//		artist.setText(album.getArtist().getName());
//		listeners.setText(numberFormat.format(album.getListeners()) + " Listeners");
//		playCount.setText(numberFormat.format(album.getPlayCount()) + " Scrobbles");
//		userPlayCount.setText(numberFormat.format(album.getUserPlayCount()) + " Your Scrobbles");
//
//		open.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				Network.openURL(album.getUrl());
//			}
//		});
//		viewArtist.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				album.getArtist().view();
//			}
//		});
//		viewWiki.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				album.getWiki().view();
//			}
//		});
//		musicBrainz.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				Network.openURL(album.getMusicBrainzURL());
//				;
//			}
//		});
//		rym.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				Network.openURL(album.getRymURL());
//				;
//			}
//		});

//		if (album.getTrackList() != null) {
//			int counter;
//			ArrayList<Track> trackList = album.getTrackList();
//			for (counter = 0; counter < trackList.size(); counter++) {
//				Track track = trackList.get(counter);
//				JLabel name = new JLabel(track.getName());
//
//				int playCountString = track.getUserPlayCount();
//
//				JLabel userPlays;
//				if (playCountString == 0)
//					userPlays = new JLabel("0");
//				else
//					userPlays = new JLabel(Integer.toString(track.getUserPlayCount()));
//
//				JLabel plays = new JLabel(numberFormat.format(track.getPlayCount()));
//				JButton openExternal = new JButton("Open Online");
//				openExternal.addActionListener(new ActionListener() {
//					public void actionPerformed(ActionEvent arg0) {
//						Network.openURL(track.getUrl());
//					}
//				});
//				JButton openInternal = new JButton("Open " + track.getClass().getSimpleName());
//				openInternal.addActionListener(new ActionListener() {
//					public void actionPerformed(ActionEvent arg0) {
//						track.view();
//					}
//				});
//				JPanel panel = new JPanel();
//				panel.setLayout(new GridLayout(1, 5));
//				panel.add(name);
//				panel.add(userPlays);
//				panel.add(plays);
//				panel.add(openInternal);
//				panel.add(openExternal);
//
//				trackListPanel.add(panel);
//			}
//		}

//		add(name);
//		add(artist);
//		add(listeners);
//		add(playCount);
//		add(userPlayCount);
//
//		add(trackListPanel);
//		JPanel datePanel = new JPanel();
//		datePanel.setLayout(new FlowLayout());
//		datePanel.add(dateLabel);
		
//		add(datePanel);
		add(contentLabel);
//		pack();
	}
}
