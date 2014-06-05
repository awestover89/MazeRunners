package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import Manager.Manager;

public class MazePieces extends JPanel{
	
	Manager m;
	LinkedList<ImageIcon> images;
	LinkedList<Component> items;
	String fileLoc;
	int size;
	MazePieces mp;
	
	public MazePieces(Manager m, int size){
		this.m = m;
		mp = this;
		this.size = size;
		this.setLayout(new GridLayout(5,3));
		fileLoc = "src/inc/img/";
		images = new LinkedList<ImageIcon>();
		items = new LinkedList<Component>();
		populateImages();
	}
	
	private void populateImages(){
		images.add(new ImageIcon(fileLoc+"bl.png"));
		images.peekLast().setDescription("bl");
		images.add(new ImageIcon(fileLoc+"br.png"));
		images.peekLast().setDescription("br");
		images.add(new ImageIcon(fileLoc+"tl.png"));
		images.peekLast().setDescription("tl");
		images.add(new ImageIcon(fileLoc+"tr.png"));
		images.peekLast().setDescription("tr");
		images.add(new ImageIcon(fileLoc+"tb.png"));
		images.peekLast().setDescription("tb");
		images.add(new ImageIcon(fileLoc+"rl.png"));
		images.peekLast().setDescription("rl");
		images.add(new ImageIcon(fileLoc+"brl.png"));
		images.peekLast().setDescription("brl");
		images.add(new ImageIcon(fileLoc+"tbl.png"));
		images.peekLast().setDescription("tbl");
		images.add(new ImageIcon(fileLoc+"tbr.png"));
		images.peekLast().setDescription("tbr");
		images.add(new ImageIcon(fileLoc+"start.png"));
		images.peekLast().setDescription("start");
		images.add(new ImageIcon(fileLoc+"trl.png"));
		images.peekLast().setDescription("trl");
		images.add(new ImageIcon(fileLoc+"flag.gif"));
		images.peekLast().setDescription("flag");
	}
	
	private void createButtons(){
		this.removeAll();
		items.clear();
		int width = this.getWidth();
		int height = this.getHeight();
		JButton button = new JButton();
		ImageIcon img = new ImageIcon(fileLoc+"blank.png");
		Image imgTemp = img.getImage();
		Image newimg = imgTemp.getScaledInstance(width/size, height/size, java.awt.Image.SCALE_SMOOTH);
		button.setIcon(new ImageIcon(newimg));
		((ImageIcon)button.getIcon()).setDescription("blank");
		button.addActionListener(buildActionListener(button.getIcon()));
		items.add(button);
		this.add(button);
		JLabel l = new JLabel("Current:");
		l.setHorizontalAlignment(SwingConstants.CENTER);
		items.add(l);
		this.add(l);
		l = new JLabel();
		img = new ImageIcon(fileLoc+"blank.png");
		l.setBorder(new LineBorder(Color.YELLOW, 3));
		l.setHorizontalAlignment(SwingConstants.CENTER);
		imgTemp = img.getImage();
		newimg = imgTemp.getScaledInstance(width/size, height/size, java.awt.Image.SCALE_SMOOTH);
		l.setIcon(new ImageIcon(newimg));
		((ImageIcon)l.getIcon()).setDescription("blank");
		items.add(l);
		this.add(l);
		for(int i = 0;i<12;i++){
			button = new JButton();
			items.add(button);
			this.add(button);
		}
		imageButtons();
	}
	
	private ActionListener buildActionListener(Icon icon){
		final Icon pic = icon;
		return new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				((JLabel) items.get(2)).setIcon(pic);
				m.setCurrentMazePiece((ImageIcon) pic);
			}
		};
	}
	
	private void imageButtons(){
		System.out.println(items.size());
		int width = this.getWidth();
		int height = this.getHeight();
		for(int i = 3;i<15;i++){
			JButton button = (JButton)items.get(i);
			ImageIcon img = images.get(i-3);
			Image imgTemp = img.getImage();
			Image newimg = imgTemp.getScaledInstance(width/size, height/size, java.awt.Image.SCALE_SMOOTH);
			button.setIcon(new ImageIcon(newimg));
			((ImageIcon)button.getIcon()).setDescription(images.get(i-3).getDescription());
			button.addActionListener(buildActionListener(button.getIcon()));
		}
		this.validate();
	}
	
	public void setSize(int s){
		createButtons();
	}

}
