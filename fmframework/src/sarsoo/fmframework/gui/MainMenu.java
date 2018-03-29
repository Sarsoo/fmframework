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
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.music.Artist;
import sarsoo.fmframework.music.Track;
import sarsoo.fmframework.net.Network;
import sarsoo.fmframework.util.FMObjList;
import sarsoo.fmframework.util.GetObject;
import sarsoo.fmframework.util.Reference;

public class MainMenu extends JFrame {

	JButton getAlbum = new JButton("Get Album");
	JButton getArtist = new JButton("Get Artist");
	JButton viewLastTrack = new JButton("View Last Track");

	public MainMenu() {
		super("fmframework");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(5, 5));
		setSize(500, 500);
		setResizable(false);

		getAlbum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Album album = GetObject.getAlbum();
				if (album != null) {
					album.view();
				} else {
					JOptionPane.showMessageDialog(null, "No Album Found", "Album Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		getArtist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Artist artist = GetObject.getArtist();
				if (artist != null) {
					artist.view();
				} else {
					JOptionPane.showMessageDialog(null, "No Artist Found", "Artist Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		viewLastTrack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Track track = GetObject.getLastTrack();
				if (track != null) {
					track.view();
				} else {
					JOptionPane.showMessageDialog(null, "No Track Found", "Track Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		add(viewLastTrack);
		add(getAlbum);
		add(getArtist);
		
		Reference.initGroupsList();
		ArrayList<FMObjList> groups = Reference.getGroups();
		int counter;
		for (counter = 0; counter < groups.size(); counter++) {
			FMObjList group = groups.get(counter);
			JButton view = new JButton("View " + group.getGroupName());
			view.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					group.view();
				}
			});
			add(view);
		}
	}
}
