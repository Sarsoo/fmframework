package sarsoo.fmframework.jframe;

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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.music.FMObj;
import sarsoo.fmframework.music.Track;
import sarsoo.fmframework.music.Wiki;
import sarsoo.fmframework.net.Network;

public class WikiView extends JFrame {
	JTextArea contentLabel = new JTextArea();

	public WikiView(Wiki wiki, String name) {
		super(name);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(1,1));
		setSize(500, 700);
		contentLabel.setText(wiki.getContent()+ "\n\n" + wiki.getDate());
		
		contentLabel.setLineWrap(true);
//		contentLabel.setText("<html>" + wiki.getContent() + "<br><br>" + wiki.getDate() + "</html>");
		
		JScrollPane scroll = new JScrollPane(contentLabel);
		add(scroll);

//		add(contentLabel);
//		pack();
	}
}
