package FileIO;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JOptionPane;

import Manager.Manager;

public class MapReader {
	Manager m;
	File f;
	Scanner s;
	String level;
	int[][] levelArray;
	int levelNum;
	int[] start;
	int[] finish;
	
	public MapReader(String fileName, Manager m){
		this.m = m;
		try {
			f = new File(fileName);
			s = new Scanner(f);
			getLevel(1);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getLevel(int level){
		s.close();
		try{
			s = new Scanner(f);
			String l = s.nextLine();
			for(int i=1;i<level;i++)
				l = s.nextLine();
			this.level = l;
			levelNum = level;
			levelToArray();
		}
		catch(FileNotFoundException e){
			JOptionPane.showMessageDialog(null, "Not a valid level");
		}
	}
	
	private void levelToArray(){
		Scanner mr = new Scanner(level);
		int w = mr.nextInt();
		int h = mr.nextInt();
		int s[] = {mr.nextInt(), mr.nextInt()};
		int f[] = {mr.nextInt(), mr.nextInt()};
		start = s;
		finish = f;
		levelArray = new int[w][h];
		for(int i=0;i<w;i++){
			for(int j=0;j<h;j++){
				levelArray[i][j] = mr.nextInt();
			}
		}
	}
	
	public void nextLevel(){
		try{
			level = s.nextLine();
			levelNum++;
			levelToArray();
		}
		catch(Exception ex){
			int choice = JOptionPane.showConfirmDialog(null, "There are no more levels. Exit?", "Mazes Completed!", JOptionPane.OK_CANCEL_OPTION);
			if(choice==JOptionPane.OK_OPTION){
				System.exit(0);
			}
		}
	}
	
	public int getLevelNum(){
		return levelNum;
	}
	
	public String getLevelData(){
		return level;
	}
	
	public int[][] getLevelArray(){
		return levelArray;
	}
	
	public int getStartX(){
		return start[0];
	}
	
	public int getStartY(){
		return start[1];
	}
	
	public int getFinishX(){
		return finish[0];
	}
	
	public int getFinishY(){
		return finish[1];
	}
	
	public int getYDir(){
		if(levelArray[start[0]][start[1]] == 12)
			return 1;
		if(levelArray[start[0]][start[1]] == 14)
			return -1;
		return 0;
	}
	
	public int getXDir(){
		if(levelArray[start[0]][start[1]] == 11)
			return 1;
		if(levelArray[start[0]][start[1]] == 13)
			return -1;
		return 0;
	}
		
}
