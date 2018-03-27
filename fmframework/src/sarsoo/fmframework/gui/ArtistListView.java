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
import sarsoo.fmframework.music.Artist;
import sarsoo.fmframework.net.Network;
import sarsoo.fmframework.util.ArtistList;

public class ArtistListView extends JFrame{
//	JPanel info = new JPanel();
//	JPanel buttons = new JPanel();
//	JPanel buttons2 = new JPanel();
//	JLabel name = new JLabel();
//	JLabel artist = new JLabel();
//	JLabel listeners = new JLabel();
//	JLabel playCount = new JLabel();
//	JLabel userPlayCount = new JLabel();
//	JButton open = new JButton("View Online");
//	JButton viewArtist = new JButton("View Artist");
//	JButton musicBeanz = new JButton("Open MusicBeanz");
//	JButton rym = new JButton("Open RYM");
//	
	public ArtistListView(ArtistList artists, String title) {
		super(title);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(artists.size() + 2,0));
//		setSize(300, 300);
		setResizable(false);
//		info.setLayout(new GridLayout());
//		buttons.setLayout(new FlowLayout());
//		buttons2.setLayout(new FlowLayout());
//
//		buttons.add(open);
//		buttons.add(viewArtist);
//		buttons2.add(musicBeanz);
//		buttons2.add(rym);
		
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
		for(counter = 0; counter < artists.size(); counter++) {
			Artist artist = artists.get(counter);
			JLabel name = new JLabel(artist.getName());
			JLabel userPlays = new JLabel(Integer.toString(artist.getUserPlayCount()));
			JLabel plays = new JLabel(numberFormat.format(artist.getPlayCount()));
			JButton openExternal = new JButton("Open Online");
			openExternal.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Network.openURL(artist.getUrl());
				}
			});
			JButton openInternal = new JButton("Open Artist");
			openInternal.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					artist.view();
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
		JLabel totalScrobbles = new JLabel(numberFormat.format(artists.getTotalUserScrobbles()) + " Total Plays");
		add(totalScrobbles);
		pack();
		
//		name.setText(album.getName());
//		name.setHorizontalTextPosition(JLabel.CENTER);
//		artist.setText(album.getArtist().getName());
//		listeners.setText(numberFormat.format(album.getListeners()) + " Listeners");
//		playCount.setText(numberFormat.format(album.getPlayCount()) + " Scrobbles");
//		userPlayCount.setText(numberFormat.format(album.getUserPlayCount()) + " Your Scrobbles");
//		
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
//		musicBeanz.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				Network.openURL(album.getMusicBeanzURL());;
//			}
//		});
//		rym.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				Network.openURL(album.getRymURL());;
//			}
//		});
//		
//		add(name);
//		add(artist);
//		add(listeners);
//		add(playCount);
//		add(userPlayCount);
////		add(info);
//		add(buttons);
//		add(buttons2);
	}
}
