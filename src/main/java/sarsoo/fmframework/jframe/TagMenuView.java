package sarsoo.fmframework.jframe;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;

import sarsoo.fmframework.music.Tag;
import sarsoo.fmframework.util.FMObjList;
import sarsoo.fmframework.util.Getter;
import sarsoo.fmframework.util.Reference;

public class TagMenuView extends JFrame {

	public TagMenuView() {
		super("View Tags");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		ArrayList<Tag> tags = Getter.getUserTags(Reference.getUserName());
		
		setLayout(new GridLayout(4, 4));
		setSize(700, 700);
		setResizable(false);
		
		int counter;
		for(counter = 0; counter < tags.size(); counter++) {
			Tag tag = tags.get(counter);
			
			JButton view = new JButton(tag.getName());
			view.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					FMObjList list = Getter.getUserTag(Reference.getUserName(), tag.getName());
					list.view();
				}
			});
			add(view);
		}
	}
}
