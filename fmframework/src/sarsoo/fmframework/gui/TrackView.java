package sarsoo.fmframework.gui;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import sarsoo.fmframework.music.Track;
import sarsoo.fmframework.net.Network;
import sarsoo.fmframework.util.Maths;
import sarsoo.fmframework.util.Reference;

public class TrackView extends JFrame {
	// JPanel info = new JPanel();
	// JPanel nameInfo = new JPanel();
	// JPanel scrobbleInfo = new JPanel();
	JPanel buttons = new JPanel();
	JPanel buttons2 = new JPanel();

	JLabel name = new JLabel();
	JLabel album = new JLabel();
	JLabel artist = new JLabel();
	JLabel listeners = new JLabel();
	JLabel playCount = new JLabel();
	JLabel userPlayCount = new JLabel();
	JLabel timePlayRatio = new JLabel();

	JButton open = new JButton("View Online");
	JButton viewArtist = new JButton("View Artist");
	JButton viewAlbum = new JButton("View Album");
	JButton viewWiki = new JButton("View Wiki");
	JButton musicBrainz = new JButton("Open MusicBrainz");
	JButton genius = new JButton("Open Genius");

	public TrackView(Track track) {
		super(track.getName());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		if (track.getAlbum() != null) {
			setLayout(new GridLayout(8, 1));
		} else {
			setLayout(new GridLayout(7, 1));
		}
		// setSize(300, 300);
		setResizable(false);

		// info.setLayout(new GridLayout(6,1));
		// nameInfo.setLayout(new GridLayout(3,1));
		// scrobbleInfo.setLayout(new GridLayout(3,1));
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
		// if (track.getArtist() != null)
		buttons.add(genius);

		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
		Font title = new Font("Arial", Font.BOLD, 24);
		Font sub = new Font("Arial", Font.PLAIN, 20);
		Font subSub = new Font("Arial", Font.PLAIN, 16);

		name.setText(track.getName());
		name.setHorizontalAlignment(SwingConstants.CENTER);
		name.setFont(title);

		if (track.getAlbum() != null) {
			album.setText(track.getAlbum().getName());
			album.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					track.getAlbum().view();
				}
			});
		}
		album.setHorizontalAlignment(SwingConstants.CENTER);
		album.setFont(sub);

		artist.setText(track.getArtist().getName());
		artist.setHorizontalAlignment(SwingConstants.CENTER);
		artist.setFont(sub);
		
		double ratio = track.getTimeListenRatio();

		// int ratioRound = (int) Math.round(ratio);
		// int oneOverRatioRound = (int) Math.round(1/ratio);
		if (ratio > 1) {
			timePlayRatio.setText(String.format("listen every %.2f days", ratio));
		} else if (ratio == 1) {
			timePlayRatio.setText("listen every day");
		} else {
			timePlayRatio.setText(String.format("%.2f times a day", 1 / ratio));
		}
		timePlayRatio.setHorizontalAlignment(SwingConstants.CENTER);
		timePlayRatio.setFont(subSub);

		listeners.setText(numberFormat.format(track.getListeners()) + " Listeners");
		listeners.setHorizontalAlignment(SwingConstants.CENTER);
		playCount.setText(numberFormat.format(track.getPlayCount()) + " Total Scrobbles");
		playCount.setHorizontalAlignment(SwingConstants.CENTER);
		userPlayCount.setText(numberFormat.format(track.getUserPlayCount())
				+ String.format(" Scrobbles (%.3f%%)", Maths.getPercentListening(track, Reference.getUserName())));
		userPlayCount.setHorizontalAlignment(SwingConstants.CENTER);
		userPlayCount.setFont(sub);

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

		if (track.getUserPlayCount() > 0) {
			userPlayCount.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					String namePlus = track.getName().replaceAll(" ", "+");
					String artistPlus = track.getArtist().getName().replaceAll(" ", "+");
					String url = String.format("https://www.last.fm/user/%s/library/music/%s/_/%s",
							Reference.getUserName(), artistPlus, namePlus);
					Network.openURL(url);
				}
			});
		}

		artist.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				track.getArtist().view();
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

		add(name);
		if (track.getAlbum() != null)
			add(album);
		add(artist);
		add(userPlayCount);
		add(timePlayRatio);
		add(listeners);
		add(playCount);

		// info.add(nameInfo);
		// info.add(scrobbleInfo);

		// add(info);
		add(buttons);
		// add(buttons2);
		pack();
	}
}
