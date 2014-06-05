package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Manager.Manager;

public class MazeCanvas extends JPanel{
	
	Manager m;
	int size;
	LinkedList<JLabel> maze;
	ImageIcon currentPiece, startImg, finishImg;
	int startX, startY, finishX, finishY;
	
	public MazeCanvas(Manager m){
		this.m = m;
		this.size = 5;
		currentPiece = new ImageIcon("src/inc/img/blank.jpg");
		this.setLayout(new GridLayout(size, size));
		maze = new LinkedList<JLabel>();
		startX = -1;
		startY = -1;
		finishX = -1;
		finishY = -1;
	}
	
	public void setSize(int size){
		((GridLayout) this.getLayout()).setRows(size);
		((GridLayout) this.getLayout()).setColumns(size);
		fillGrid(this.size, size);
		this.size = size;
		this.validate();
	}
	
	private void fillGrid(int oldSize, int newSize){
		LinkedList<JLabel> tempList = (LinkedList<JLabel>) maze.clone();
		maze.clear();
		this.removeAll();
		int width = this.getWidth();
		int height = this.getHeight();
		for(int i=0;i<newSize;i++){
			for(int j=0;j<newSize;j++){
				JLabel l = new JLabel();
				ImageIcon img;
				if(tempList.size()==0){
					img = new ImageIcon("src/inc/img/blank.png");
					img.setDescription("blank");
				}
				else{
					if(i<oldSize && j<oldSize){
						int loc = i*oldSize+j;
						img = (ImageIcon)tempList.get(loc).getIcon();
					}
					else{
						img = new ImageIcon("src/inc/img/blank.png");
						img.setDescription("blank");
					}
				}
				Image imgTemp = img.getImage();
				Image newimg = imgTemp.getScaledInstance(width/newSize, height/newSize, java.awt.Image.SCALE_SMOOTH);
				l.setIcon(new ImageIcon(newimg));
				((ImageIcon) l.getIcon()).setDescription(img.getDescription());
				setMouseListener(l);
				this.add(l);
				maze.add(l);
			}
		}
	}
	
	private void setMouseListener(Component c){
		final Component temp = c;
		c.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {	
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				if(currentPiece.getDescription().equals("start")){
					if(startX!=-1){
						maze.get(startX+(startY*size)).setIcon(startImg);
					}
					setStart(temp.getX(), temp.getY());
					startImg = (ImageIcon) ((JLabel) temp).getIcon();
					String descrip = ((ImageIcon) ((JLabel) temp).getIcon()).getDescription();
					ImageIcon tmp = new ImageIcon(getCombinedImage(((ImageIcon)((JLabel) temp).getIcon()).getImage(),currentPiece.getImage(),size*2));
					tmp.setDescription(descrip);
					((JLabel) temp).setIcon(tmp);
				}
				else if(currentPiece.getDescription().equals("flag")){
					if(finishX!=-1){
						maze.get(finishX+(finishY*size)).setIcon(finishImg);
					}
					setFinish(temp.getX(), temp.getY());
					finishImg = (ImageIcon) ((JLabel) temp).getIcon();
					String descrip = ((ImageIcon) ((JLabel) temp).getIcon()).getDescription();
					ImageIcon tmp = new ImageIcon(getCombinedImage(((ImageIcon)((JLabel) temp).getIcon()).getImage(),currentPiece.getImage(),size*2));
					tmp.setDescription(descrip);
					((JLabel) temp).setIcon(tmp);
				}
				else{
					((JLabel) temp).setIcon(currentPiece);
				}
			}
		});
	}
	
	private Image getCombinedImage(Image img1, Image img2, int denom) {
		int w = Math.max(img2.getWidth(null), img1.getWidth(null));
		int h = Math.max(img2.getHeight(null), img1.getHeight(null));
		Image image = new BufferedImage(w, h,  BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = (Graphics2D) image.getGraphics();
		g2.drawImage(img1, 0, 0, Color.WHITE, null);
		g2.drawImage(img2, w/denom, h/denom, (int)(w*0.8), (int)(h*0.8), null);
		g2.dispose();
		return image;
	}
	
	private void setFinish(float x, float y){
		int width = this.getWidth();
		int height = this.getHeight();
		int xIndex = (int) (x/(width/size));
		int yIndex = (int) (y/(height/size));
		finishX = xIndex;
		finishY = yIndex;
	}
	
	private void setStart(float x, float y){
		int width = this.getWidth();
		int height = this.getHeight();
		int xIndex = (int) (x/(width/size));
		int yIndex = (int) (y/(height/size));
		startX = xIndex;
		startY = yIndex;
	}
	
	public int panelSize(){
		return size;
	}
	
	public void setCurrentMazePiece(ImageIcon img){
		int width = this.getWidth();
		int height = this.getHeight();
		Image imgTemp = img.getImage();
		Image newimg = imgTemp.getScaledInstance(width/size, height/size, java.awt.Image.SCALE_SMOOTH);
		currentPiece = new ImageIcon(newimg);
		currentPiece.setDescription(img.getDescription());
	}

	public String getLevelText() {
		if(startX==-1 || startY==-1){
			System.out.println("You must specify a start.");
			return null;
		}
		if(finishX==-1 || finishY==-1){
			System.out.println("You must specify a finish.");
			return null;
		}
		String s = size+" "+size+" "+startX+" "+startY+" "+finishX+" "+finishY;
		String col[] = new String[size];
		for(int j=0;j<size;j++){
			col[j] = "";
		}
		//the problem here is that this goes left to right, top to bottom, we want top to bottom, left to right
		int i = 0;
		for(JLabel j:maze){
			col[i%size]+=" "+descripToNum(((ImageIcon)j.getIcon()).getDescription());
			i++;
		}
		for(int j=0;j<size;j++){
			s+=col[j];
		}
		return s;
	}
	
	private String descripToNum(String descrip){
		if(descrip == null || descrip.equals("blank"))
			return "0";
		if(descrip.equals("tl"))
			return "5";
		if(descrip.equals("tr"))
			return "6";
		if(descrip.equals("br"))
			return "7";
		if(descrip.equals("bl"))
			return "8";
		if(descrip.equals("tb"))
			return "9";
		if(descrip.equals("rl"))
			return "10";
		if(descrip.equals("tbl"))
			return "11";
		if(descrip.equals("trl"))
			return "12";
		if(descrip.equals("tbr"))
			return "13";
		if(descrip.equals("brl"))
			return "14";
		return "DESCRIP"+descrip;
	}

}
