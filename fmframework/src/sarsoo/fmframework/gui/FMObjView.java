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

import sarsoo.fmframework.music.FMObj;
import sarsoo.fmframework.net.Network;
import sarsoo.fmframework.util.Maths;
import sarsoo.fmframework.util.Reference;

public class FMObjView extends JFrame {
	JPanel buttons = new JPanel();

	JLabel name = new JLabel();
	JLabel listeners = new JLabel();
	JLabel playCount = new JLabel();
	JLabel userPlayCount = new JLabel();
	JLabel timePlayRatio = new JLabel();

	JButton viewWiki = new JButton("View Wiki");
	JButton open = new JButton("View Online");
	JButton musicBrainz = new JButton("Open MusicBrainz");

	public FMObjView(FMObj obj) {
		super(obj.toString());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(6, 1));
		setSize(300, 300);
		// setResizable(false);

		buttons.setLayout(new FlowLayout());

		buttons.add(open);
		if (obj.getWiki() != null)
			buttons.add(viewWiki);
		if (obj.getMbid() != null)
			buttons.add(musicBrainz);

		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
		Font title = new Font("Arial", Font.BOLD, 24);
		Font sub = new Font("Arial", Font.PLAIN, 20);

		name.setText(obj.getName());
		name.setHorizontalAlignment(SwingConstants.CENTER);
		name.setFont(title);

		listeners.setText(numberFormat.format(obj.getListeners()) + " Listeners");
		listeners.setHorizontalAlignment(SwingConstants.CENTER);
		playCount.setText(numberFormat.format(obj.getPlayCount()) + " Total Scrobbles");
		playCount.setHorizontalAlignment(SwingConstants.CENTER);
		userPlayCount.setText(numberFormat.format(obj.getUserPlayCount())
				+ String.format(" Scrobbles (%.2f%%)", Maths.getPercentListening(obj, Reference.getUserName())));
		userPlayCount.setHorizontalAlignment(SwingConstants.CENTER);
		userPlayCount.setFont(sub);

		double ratio = obj.getTimeListenRatio();

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
		timePlayRatio.setFont(sub);

		viewWiki.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				obj.getWiki().view(obj.getName());
			}
		});
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Network.openURL(obj.getUrl());
			}
		});
		musicBrainz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Network.openURL(obj.getMusicBrainzURL());
				;
			}
		});
		add(name);
		add(userPlayCount);
		add(timePlayRatio);
		add(listeners);
		add(playCount);
		// add(info);
		add(buttons);

		pack();
	}

}
