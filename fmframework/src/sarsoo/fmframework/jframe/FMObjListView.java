package sarsoo.fmframework.jframe;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.music.FMObj;
import sarsoo.fmframework.net.Network;
import sarsoo.fmframework.util.FMObjList;
import sarsoo.fmframework.util.Getter;
import sarsoo.fmframework.util.Maths;
import sarsoo.fmframework.util.Reference;

public class FMObjListView extends JFrame {

	public FMObjListView(FMObjList objects, String title) {
		super(title);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		setLayout(new GridLayout(objects.size() + 2, 0));
//		setResizable(false);
		// createMenu();
		
		int limit = 20;
		
		if(objects.size() > limit) {
			setSize(600, 800);
		}
		
		JPanel container = new JPanel();
		container.setLayout(new GridLayout(objects.size() + 2, 0));
		
		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
		Font font = new Font("Arial", Font.PLAIN, 20);
		Font header = new Font("Arial", Font.BOLD, 16);
		
		JPanel headerLabels = new JPanel();
//		headerLabels.setFont(header);
		headerLabels.setLayout(new GridLayout(1, 4));
		
		
		JLabel headerName = new JLabel("name");
		headerName.setHorizontalAlignment(SwingConstants.CENTER);
		headerName.setFont(header);
		
		JLabel headerUser = new JLabel("user");
		headerUser.setHorizontalAlignment(SwingConstants.CENTER);
		headerUser.setFont(header);
		
		JLabel headerTotal = new JLabel("total");
		headerTotal.setHorizontalAlignment(SwingConstants.CENTER);
		headerTotal.setFont(header);
		
		headerLabels.add(headerName);
		headerLabels.add(headerUser);
		headerLabels.add(headerTotal);
		headerLabels.add(new JLabel(""));
//		headerLabels.add(new JLabel(""));

		container.add(headerLabels);

		Collections.sort(objects);
		Collections.reverse(objects);
		
		int counter;
		for (counter = 0; counter < objects.size(); counter++) {
			FMObj fmObj = objects.get(counter);
			JLabel artistName = new JLabel(fmObj.getName());
			artistName.setHorizontalAlignment(SwingConstants.CENTER);
			artistName.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					fmObj.view();
				}
			});

			int playCountString = fmObj.getUserPlayCount();

			JLabel userPlays;
			if (playCountString == 0)
				userPlays = new JLabel("0");
			else
				userPlays = new JLabel(Integer.toString(fmObj.getUserPlayCount()));
			userPlays.setHorizontalAlignment(SwingConstants.CENTER);
			
			JLabel plays = new JLabel(numberFormat.format(fmObj.getPlayCount()));
			plays.setHorizontalAlignment(SwingConstants.CENTER);
			
			JButton openExternal = new JButton("Open Online");
			openExternal.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Network.openURL(fmObj.getUrl());
				}
			});
//			JButton openInternal = new JButton("Open " + fmObj.getClass().getSimpleName());
//			openInternal.addActionListener(new ActionListener() {
//				public void actionPerformed(ActionEvent arg0) {
//					fmObj.view();
//				}
//			});
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(1, 4));
			panel.add(artistName);
			panel.add(userPlays);
			panel.add(plays);
//			panel.add(openInternal);
			panel.add(openExternal);

			container.add(panel);
		}

		JPanel info = new JPanel();
		info.setLayout(new GridLayout(1, 2));

		JLabel totalScrobbles = new JLabel(numberFormat.format(objects.getTotalUserScrobbles()) + " total plays");
		totalScrobbles.setHorizontalAlignment(SwingConstants.CENTER);
		totalScrobbles.setFont(font);
		info.add(totalScrobbles);

		double percent = Maths.getPercentListening(objects, Reference.getUserName());
		// if (percent > 1) {
		JLabel percentLabel = new JLabel();
		percentLabel.setHorizontalAlignment(SwingConstants.CENTER);
		percentLabel.setText(String.format("%.2f%%", percent));
		percentLabel.setFont(font);
		info.add(percentLabel);

		// }

		container.add(info);
		
		JScrollPane scroll = new JScrollPane(container);
		add(scroll);
		
		if(objects.size() <= limit) {
			pack();
		}
		
	}

	private void createMenu() {
		JMenuBar menuBar = new JMenuBar();

		JMenu editMenu = new JMenu("Edit");

		JMenu addMenu = new JMenu("Add");

		// create menu items
		JMenuItem addAlbum = new JMenuItem("Album");
		addAlbum.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Album album = Getter.getAlbum();
				if (album != null) {

				}
			}
		});

		JMenuItem addTrack = new JMenuItem("Track");

		JMenuItem addArtist = new JMenuItem("Artist");

		addMenu.add(addAlbum);
		addMenu.add(addTrack);
		addMenu.add(addArtist);

		editMenu.add(addMenu);
		menuBar.add(editMenu);

		setJMenuBar(menuBar);
	}
}
