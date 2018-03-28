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
import sarsoo.fmframework.util.GetObject;
import sarsoo.fmframework.util.Reference;

public class MainMenu extends JFrame{

	JButton getAlbum = new JButton("Get Album");
	JButton viewTDE = new JButton("View TDE");
	JButton viewBPHQ = new JButton("View BPHQ");
	JButton viewLastTrack = new JButton("View Last Track");
	
	public MainMenu() {
		super("fmframework");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(2,2));
		setSize(300, 300);
		setResizable(false);

		getAlbum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GetObject.getAlbum().view();;
			}
		});
		viewTDE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Reference.getTDE().view();
			}
		});
		viewBPHQ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Reference.getBPHQ().view();
			}
		});
		viewLastTrack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GetObject.getLastTrack().view();
			}
		});
		
		add(viewLastTrack);
		add(getAlbum);
		add(viewTDE);
		add(viewBPHQ);
	}
}
