package GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Manager.Manager;

public class MazeBuilder extends JFrame{
	
	Manager m;
	MazeView mv;
	JPanel sizePanel, submitPanel;
	
	public MazeBuilder(Manager m){
		this.m = m;
		buildPanels();
		this.setLayout(new BorderLayout());
		this.add(sizePanel, BorderLayout.NORTH);
		this.add(submitPanel, BorderLayout.SOUTH);
		this.add(mv, BorderLayout.CENTER);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(600,400);
		this.setVisible(true);
		mv.setSize(5);
	}

	private void buildPanels() {
		mv = new MazeView(m);
		sizePanel = new JPanel();
		submitPanel = new JPanel();
		sizePanel.setLayout(new GridLayout(1,4));
		submitPanel.setLayout(new GridLayout(1,3));
		buildSizePanel();
		buildSubmitPanel();
	}
	
	private void buildSizePanel(){
		JLabel size = new JLabel("Grid Size");
		JRadioButton five, ten, twenty;
		five = new JRadioButton("5",true);
		ten = new JRadioButton("10");
		twenty = new JRadioButton("20");
		setChangeListener(five, 5);
		setChangeListener(ten, 10);
		setChangeListener(twenty, 20);
		ButtonGroup bg = new ButtonGroup();
		bg.add(five);
		bg.add(ten);
		bg.add(twenty);
		sizePanel.add(size);
		sizePanel.add(five);
		sizePanel.add(ten);
		sizePanel.add(twenty);
	}
	
	private void buildSubmitPanel(){
		final JTextField name = new JTextField();
		JLabel nameLabel = new JLabel("Name:");
		JButton submit = new JButton("Save Maze");
		submit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(name.getText().trim().equals("")){
					System.out.println("You need to enter a name.");
				}
				else{
					writeLevel(name.getText().trim().replaceAll(" ", "_"));
				}
			}
		});
		submitPanel.add(nameLabel);
		submitPanel.add(name);
		submitPanel.add(submit);
	}
	
	private void setChangeListener(AbstractButton ab, int size){
		final int s = size;
		ab.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				AbstractButton temp = (AbstractButton)e.getSource();
				if(temp.isSelected()){
					mv.setSize(s);
				}
			}
		});
	}
	
	public void setCurrentMazePiece(ImageIcon img){
		mv.setCurrentMazePiece(img);
	}
	
	private void writeLevel(String name){
		String output = mv.getLevelText();
		if (output!=null) {
			String fileName = "src/inc/" + name + ".dat";
			try {
				FileWriter fw = new FileWriter(fileName);
				PrintWriter out = new PrintWriter(fw);
				out.println(output);
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
