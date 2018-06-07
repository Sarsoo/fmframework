package sarsoo.fmframework.jframe;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.music.Artist;
import sarsoo.fmframework.music.Track;
import sarsoo.fmframework.net.Network;
import sarsoo.fmframework.util.Getter;
import sarsoo.fmframework.util.Reference;

public class MainMenu extends JFrame {

	JButton getAlbum = new JButton("Get Album");
	JButton getArtist = new JButton("Get Artist");
	JButton viewLastTrack = new JButton("View Last Track");
	JButton viewList = new JButton("View List");
	JButton viewTag = new JButton("View Tags");
	JButton today = new JButton();

	public MainMenu() {
		super("fmframework - " + Reference.getUserName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(3, 2));
		setSize(300, 300);
		setResizable(false);

		getAlbum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Album album = sarsoo.fmframework.jframe.Getter.getAlbum();
				if (album != null) {
					album.view();
				} else {
					JOptionPane.showMessageDialog(null, "No Album Found", "Album Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		getArtist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Artist artist = sarsoo.fmframework.jframe.Getter.getArtist();
				if (artist != null) {
					artist.view();
				} else {
					JOptionPane.showMessageDialog(null, "No Artist Found", "Artist Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		viewLastTrack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Track track = Getter.getLastTrack();
				if (track != null) {
					track.view();
				} else {
					JOptionPane.showMessageDialog(null, "No Track Found", "Track Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		viewList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				RefListsView view = new RefListsView();
				view.setVisible(true);
			}
		});
		viewTag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TagMenuView view = new TagMenuView();
				view.setVisible(true);
			}
		});
		today.setText("Today: " + Integer.toString(Getter.getScrobblesToday(Reference.getUserName())));
		today.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Network.openURL(String.format("https://www.last.fm/user/%s/library?date_preset=LAST_30_DAYS", Reference.getUserName()));
				today.setText("Today: " + Integer.toString(Getter.getScrobblesToday(Reference.getUserName())));
			}
		});
		add(viewLastTrack);
		add(today);
		add(viewTag);
		add(viewList);
		add(getArtist);
		add(getAlbum);	
		
	}
}
