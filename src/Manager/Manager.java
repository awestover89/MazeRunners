package Manager;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

import FileIO.MapReader;
import GUI.MazeBuilder;
import GUI.MazeControlWindow;
import MazeRunner.MazeRunner;

public class Manager {
	
	MapReader mr;
	MazeControlWindow mcw;
	MazeRunner runner;
	MazeBuilder mb;
	
	public Manager(){
		mr = new MapReader("src/inc/levels.dat", this);
		runner = new MazeRunner(mr.getStartX(), mr.getStartY(), mr.getXDir(), mr.getYDir());
		mcw = new MazeControlWindow(this);
		mcw.startLevel(mr.getLevelArray());
		//mb = new MazeBuilder(this);
	}
	
	public MazeRunner getRunner(){
		return runner;
	}
	
	public int getFinishX(){
		return mr.getFinishX();
	}
	
	public int getFinishY(){
		return mr.getFinishY();
	}
	
	public boolean isMoveLegal(){
		int x = runner.getX();
		int y = runner.getY();
		return mcw.isMoveLegal(x, y, x+runner.getXDir(), y+runner.getYDir());
	}

	public void levelFinished(){
		mr.nextLevel();
		mcw.dispose();
		runner = new MazeRunner(mr.getStartX(), mr.getStartY(), mr.getXDir(), mr.getYDir());
		mcw = new MazeControlWindow(this);
		int levelData[][] = mr.getLevelArray();
		System.out.println(mr.getStartX()+" "+mr.getStartY());
		mcw.startLevel(levelData);
	}
	
	public void setCurrentMazePiece(ImageIcon img){
		mb.setCurrentMazePiece(img);
	}
	
	public void prevLevel(){
		mr.getLevel(mr.getLevelNum()-1);
		mcw.dispose();
		mcw = new MazeControlWindow(this);
		int levelData[][] = mr.getLevelArray();
		System.out.println(mr.getStartX()+" "+mr.getStartY());
		runner = new MazeRunner(mr.getStartX(), mr.getStartY(), mr.getXDir(), mr.getYDir());
		mcw.startLevel(levelData);
	}
	
	public void loadCustomMaze(){
		JFileChooser chooser = new JFileChooser("src/inc"); 
	    int returnVal = chooser.showOpenDialog(null);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	    	mr = new MapReader(chooser.getSelectedFile().getAbsolutePath(), this);
	    	mcw.dispose();
	    	mcw = new MazeControlWindow(this);
	    	int levelData[][] = mr.getLevelArray();
	    	runner = new MazeRunner(mr.getStartX(), mr.getStartY(), mr.getXDir(), mr.getYDir());
			mcw.startLevel(levelData);
	    }
	}
	
	public void openBuilder(){
		mb = new MazeBuilder(this);
	}
	
}
