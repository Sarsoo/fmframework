package sarsoo.fmframework.jframe;

import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import sarsoo.fmframework.music.Wiki;

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
