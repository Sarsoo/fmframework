package sarsoo.fmframework.gui;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.net.Network;
import sarsoo.fmframework.util.Maths;
import sarsoo.fmframework.util.Reference;

public class AlbumView extends JFrame {
	JPanel buttons = new JPanel();
	JPanel buttons2 = new JPanel();
	JPanel trackListPanel = new JPanel();

	JLabel name = new JLabel();
	JLabel artist = new JLabel();
	JLabel listeners = new JLabel();
	JLabel playCount = new JLabel();
	JLabel userPlayCount = new JLabel();

	JButton open = new JButton("View Online");
	JButton viewArtist = new JButton("View Artist");
	JButton viewWiki = new JButton("View Wiki");
	JButton musicBrainz = new JButton("Open MusicBrainz");
	JButton rym = new JButton("Open RYM");

	public AlbumView(Album album) {
		super(album.getName());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(8, 1));
		setSize(300, 300);
		setResizable(false);

		buttons.setLayout(new FlowLayout());
		buttons2.setLayout(new FlowLayout());
//		System.out.println(album.getName());
//		if (album.getTrackList() != null)
//			buttons2.setLayout(new GridLayout(album.getTrackList().size(), 1));

		buttons2.add(open);
		buttons2.add(viewArtist);
		
		if (album.getWiki() != null)
			buttons2.add(viewWiki);
		if (album.getMbid() != null)
			buttons.add(musicBrainz);
		buttons.add(rym);

		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
		Font title = new Font("Arial", Font.BOLD, 24);
		Font sub = new Font("Arial", Font.PLAIN, 20);

		name.setText(album.getName());
		name.setHorizontalAlignment(SwingConstants.CENTER);
		name.setFont(title);

		artist.setText(album.getArtist().getName());
		artist.setHorizontalAlignment(SwingConstants.CENTER);
		artist.setFont(sub);
		
		listeners.setText(numberFormat.format(album.getListeners()) + " Listeners");
		listeners.setHorizontalAlignment(SwingConstants.CENTER);
		playCount.setText(numberFormat.format(album.getPlayCount()) + " Scrobbles");
		playCount.setHorizontalAlignment(SwingConstants.CENTER);
		
		userPlayCount.setText(numberFormat.format(album.getUserPlayCount()) + String.format(" Your Scrobbles (%.2f%%)", Maths.getPercentListening(album, Reference.getUserName())));
		userPlayCount.setHorizontalAlignment(SwingConstants.CENTER);
		userPlayCount.setFont(sub);

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
		viewWiki.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				album.getWiki().view(album.getName());
			}
		});
		musicBrainz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Network.openURL(album.getMusicBrainzURL());
				;
			}
		});
		rym.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Network.openURL(album.getRymURL());
				;
			}
		});

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

		add(name);
		add(artist);
		add(userPlayCount);
		add(listeners);
		add(playCount);

		add(trackListPanel);
		add(buttons);
		add(buttons2);
	}
}
