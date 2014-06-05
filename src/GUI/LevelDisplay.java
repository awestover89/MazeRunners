package GUI;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import EventHandler.CommandEvent;
import EventHandler.CommandListener;
import Manager.Manager;

public class LevelDisplay extends JPanel implements CommandListener{
	private Manager m;
	private int gridSize, runnerLoc, width, height;
	private LinkedList<JLabel> icons;
	private LinkedList<Integer> tileTypes;
	private Image runnerPic, finishPic, underRunner;
	
	public LevelDisplay(Manager m){
		this.m = m;
		this.setSize(400,400);
		int xD = m.getRunner().getXDir();
		int yD = m.getRunner().getYDir();
		if(xD==-1){
			//rotate 180 degrees
			runnerPic = rotate(180);
		}
		else if(yD==1){
			//rotate 90
			runnerPic = rotate(90);
		}
		else if(yD==-1){
			//rotate 270
			runnerPic = rotate(270);
		}
		else{
			runnerPic = rotate(0);
		}
		ImageIcon img = new ImageIcon("src/inc/img/flag.gif");
		finishPic = img.getImage();
	}
	
	public void buildLevel(int[][] levelData){
		width = levelData.length;
		height = levelData[0].length;
		int dim = Math.max(width, height);
		gridSize = setLevelSize(dim);
		icons = new LinkedList<JLabel>();
		tileTypes = new LinkedList<Integer>();
		int blankRows = (int)Math.floor((gridSize-height)/2.0);
		fillBlankRows(blankRows, gridSize);
		int leftBlanks = (int)Math.floor((gridSize-width)/2.0);
		int rightBlanks = (int)Math.ceil((gridSize-width)/2.0);
		fillLevel(leftBlanks, rightBlanks,levelData);
		blankRows = (int)Math.ceil((gridSize-height)/2.0);
		fillBlankRows(blankRows, gridSize);
		runnerLoc = m.getRunner().getY()*gridSize+m.getRunner().getX()+blankRows*gridSize+leftBlanks;
		this.validate();
	}

	private void fillLevel(int leftBlanks, int rightBlanks, int[][] levelData) {
		for(int i=0;i<levelData[0].length;i++){
			for(int j=0;j<leftBlanks;j++){
				JLabel l = new JLabel();
				tileTypes.add(0);
				ImageIcon img = new ImageIcon("src/inc/img/blank.png");
				Image imgTemp = img.getImage();
				Image newimg = imgTemp.getScaledInstance(400/gridSize, 400/gridSize, java.awt.Image.SCALE_SMOOTH);
				l.setIcon(new ImageIcon(newimg));
				this.add(l);
				icons.add(l);
			}
			for(int k=0;k<levelData.length;k++){
				JLabel l = new JLabel();
				ImageIcon img;
				tileTypes.add(levelData[k][i]);
				switch(levelData[k][i]){
				case 0:
					img = new ImageIcon("src/inc/img/blank.png");
					break;
				case 1:
					img = new ImageIcon("src/inc/img/l.png");
					break;
				case 2:
					img = new ImageIcon("src/inc/img/t.png");
					break;
				case 3:
					img = new ImageIcon("src/inc/img/r.png");
					break;
				case 4:
					img = new ImageIcon("src/inc/img/b.png");
					break;
				case 5:
					img = new ImageIcon("src/inc/img/tl.png");
					break;
				case 6:
					img = new ImageIcon("src/inc/img/tr.png");
					break;
				case 7:
					img = new ImageIcon("src/inc/img/br.png");
					break;
				case 8:
					img = new ImageIcon("src/inc/img/bl.png");
					break;
				case 9:
					img = new ImageIcon("src/inc/img/tb.png");
					break;
				case 10:
					img = new ImageIcon("src/inc/img/rl.png");
					break;
				case 11:
					img = new ImageIcon("src/inc/img/tbl.png");
					break;
				case 12:
					img = new ImageIcon("src/inc/img/trl.png");
					break;
				case 13:
					img = new ImageIcon("src/inc/img/tbr.png");
					break;
				case 14:
					img = new ImageIcon("src/inc/img/brl.png");
					break;
				default:
					img = new ImageIcon("src/inc/img/blank.png");
					break;
				}
				Image imgTemp = img.getImage();
				Image newimg = imgTemp.getScaledInstance(400/gridSize, 400/gridSize, java.awt.Image.SCALE_SMOOTH);
				if(k == m.getRunner().getX() && i == m.getRunner().getY()){
					underRunner = imgTemp;
					newimg = getCombinedImage(imgTemp, runnerPic, 3);
					runnerLoc = icons.size();
				}
				else if(k == m.getFinishX() && i == m.getFinishY()){
					newimg = getCombinedImage(imgTemp, finishPic, 5);
				}
				l.setIcon(new ImageIcon(newimg));
				this.add(l);
				icons.add(l);
			}
			for(int l=0;l<rightBlanks;l++){
				JLabel label = new JLabel();
				tileTypes.add(0);
				label.setIcon(new ImageIcon("src/inc/img/blank.png"));
				this.add(label);
				icons.add(label);
			}
		}
	}

	private Image getCombinedImage(Image img1, Image img2, int denom) {
		int w = Math.max(img2.getWidth(null), img1.getWidth(null));
		int h = Math.max(img2.getHeight(null), img1.getHeight(null));
		Image image = new BufferedImage(w, h,  BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = (Graphics2D) image.getGraphics();
		g2.drawImage(img1, 0, 0, Color.WHITE, null);
		g2.drawImage(img2, w/denom, h/denom, Color.WHITE, null);
		g2.dispose();
		return image;
	}
	
	private Image rotate(double degs){
		ImageIcon img = new ImageIcon("src/inc/img/char_male.png");
		Image temp = new BufferedImage(img.getIconWidth(), img.getIconHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = (Graphics2D) temp.getGraphics();
		g2.rotate(Math.toRadians(degs), img.getIconWidth()/2, img.getIconHeight()/2);
		g2.drawImage(img.getImage(), 0, 0, Color.WHITE, null);
		g2.dispose();
		return temp;
	}

	private void fillBlankRows(int blankRows, int gridSize) {
		for(int i=0;i<blankRows;i++){
			for(int j=0;j<gridSize;j++){
				JLabel l = new JLabel();
				tileTypes.add(0);
				ImageIcon img = new ImageIcon("src/inc/img/blank.png");
				l.setIcon(img);
				this.add(l);
				icons.add(l);
			}
		}
	}

	private int setLevelSize(int dim) {
		if(dim <= 5){
			this.setLayout(new GridLayout(5,5));
			return 5;
		}
		else if(dim <=10){
			this.setLayout(new GridLayout(10,10));
			return 10;
		}
		else{
			this.setLayout(new GridLayout(20,20));
			return 20;
		}
	}
	
	public boolean isMoveLegal(int x1, int y1, int x2, int y2){
		int blankRows = (int)Math.floor((gridSize-height)/2.0);
		int leftBlanks = (int)Math.floor((gridSize-width)/2.0);
		int loc = x1+y1*gridSize+blankRows*gridSize+leftBlanks;
		int type = tileTypes.get(loc);
		if(x2>x1 && (type <=5 || type == 8 || type == 9 || type == 11 && type != 3)){
			return true;
		}
		if(x2<x1 && (type <=4 || type == 6 || type == 7 || type == 9 || type == 13 && type != 1)){
			return true;
		}
		if(y2<y1 && (type <=4 || type == 8 || type == 7 || type == 10 || type == 14 && type != 2)){
			return true;
		}
		if(y2>y1 && (type <=6 || type == 10 || type == 12 && type != 4)){
			return true;
		}
		return false;
	}

	@Override
	public void commandGiven(CommandEvent e) {
		Class<?> c;
		try {
			c = Class.forName("MazeRunner.MazeRunner");
			String command = e.getCommand();
			String args = null;
			Class<?> argType = null;
			if(e.getCommand().indexOf(',')!=-1){
				command = e.getCommand().substring(0,e.getCommand().indexOf(','));
				args = e.getCommand().substring(e.getCommand().indexOf(',')+1);
				argType = args.getClass();
			}
			Method method;
			if(argType != null)
				method = c.getDeclaredMethod(command, argType);
			else
				method = c.getDeclaredMethod(command, null);
			int oldX = m.getRunner().getX();
			int oldY = m.getRunner().getY();
			if(argType != null)
				method.invoke(m.getRunner(), args);
			else
				method.invoke(m.getRunner(), null);
			int newX = m.getRunner().getX();
			int newY = m.getRunner().getY();
			if(newX == m.getFinishX() && newY == m.getFinishY()){
				m.levelFinished();
			}
			int xD = m.getRunner().getXDir();
			int yD = m.getRunner().getYDir();
			if(xD==-1){
				//rotate 180 degrees
				ImageIcon imgTemp = (ImageIcon) icons.get(runnerLoc).getIcon();
				icons.get(runnerLoc).setIcon(new ImageIcon(getCombinedImage(imgTemp.getImage(),rotate(180),3)));
			}
			else if(yD==1){
				//rotate 90
				ImageIcon imgTemp = (ImageIcon) icons.get(runnerLoc).getIcon();
				icons.get(runnerLoc).setIcon(new ImageIcon(getCombinedImage(imgTemp.getImage(),rotate(90),3)));
			}
			else if(yD==-1){
				//rotate 270
				ImageIcon imgTemp = (ImageIcon) icons.get(runnerLoc).getIcon();
				icons.get(runnerLoc).setIcon(new ImageIcon(getCombinedImage(imgTemp.getImage(),rotate(270),3)));
			}
			else{
				ImageIcon imgTemp = (ImageIcon) icons.get(runnerLoc).getIcon();
				icons.get(runnerLoc).setIcon(new ImageIcon(getCombinedImage(imgTemp.getImage(),rotate(0),3)));
			}
			if(oldX != newX || oldY != newY){
				icons.get(runnerLoc).setIcon(new ImageIcon(underRunner));
				int blankRows = (int)Math.floor((gridSize-height)/2.0);
				int leftBlanks = (int)Math.floor((gridSize-width)/2.0);
				runnerLoc = newY*gridSize+newX+blankRows*gridSize+leftBlanks;
				ImageIcon imgTemp = (ImageIcon) icons.get(runnerLoc).getIcon();
				if(xD==-1){
					//rotate 180 degrees
					icons.get(runnerLoc).setIcon(new ImageIcon(getCombinedImage(imgTemp.getImage(),rotate(180),3)));
				}
				else if(yD==1){
					//rotate 90
					icons.get(runnerLoc).setIcon(new ImageIcon(getCombinedImage(imgTemp.getImage(),rotate(90),3)));
				}
				else if(yD==-1){
					//rotate 270
					icons.get(runnerLoc).setIcon(new ImageIcon(getCombinedImage(imgTemp.getImage(),rotate(270),3)));
				}
				else{
					icons.get(runnerLoc).setIcon(new ImageIcon(getCombinedImage(imgTemp.getImage(),rotate(0),3)));
				}
				underRunner = imgTemp.getImage();
			}
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			e1.printStackTrace();
		}
	}
}
