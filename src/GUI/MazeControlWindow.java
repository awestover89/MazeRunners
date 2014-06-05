package GUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import Manager.Manager;

public class MazeControlWindow extends JFrame{
	
	LevelDisplay ld;
	CommandInputGUI ci;
	Manager m;
	
	public MazeControlWindow(Manager m){
		this.m = m;
		this.setSize(400,450);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		ld = new LevelDisplay(m);
		ci = new CommandInputGUI("mazeRunner", m, ld);
		this.add(ci,BorderLayout.NORTH);
		this.add(ld,BorderLayout.CENTER);
		buildMenu();
		this.setVisible(true);
	}
	
	public void startLevel(int[][] levelData){
		ld.buildLevel(levelData);
	}
	
	public boolean isMoveLegal(int x1, int y1, int x2, int y2){
		return ld.isMoveLegal(x1,y1,x2,y2);
	}
	
	private void buildMenu(){
		JMenuBar menubar = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenuItem prev = new JMenuItem("Previous Maze");
		prev.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				m.prevLevel();
			}	
		});
		JMenuItem next = new JMenuItem("Next Maze");
		next.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				m.levelFinished();
			}	
		});
		JMenuItem custLoad = new JMenuItem("Load Custom Maze");
		custLoad.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				m.loadCustomMaze();
			}	
		});
		JMenuItem custCreate = new JMenuItem("Build a Maze");
		custCreate.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				m.openBuilder();
			}	
		});
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}	
		});
		menubar.add(file);
		file.add(prev);
		file.add(next);
		file.add(custLoad);
		file.add(custCreate);
		file.add(exit);
		this.setJMenuBar(menubar);
	}

}
