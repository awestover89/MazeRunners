package GUI;

import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import Manager.Manager;

public class MazeView extends JPanel{
	
	Manager m;
	int size;
	MazeCanvas mc;
	MazePieces mp;
	
	public MazeView(Manager m){
		this.m = m;
		size = 5;
		mc = new MazeCanvas(m);
		mp = new MazePieces(m, mc.panelSize());
		this.setLayout(new GridLayout(1,2));
		this.add(mc);
		this.add(mp);
	}
	
	public void setSize(int size){
		this.size = size;
		mc.setSize(size);
		mp.setSize(size);
	}
	
	public void setCurrentMazePiece(ImageIcon img){
		mc.setCurrentMazePiece(img);
	}

	public String getLevelText() {
		return mc.getLevelText();
	}

}
