package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Manager.Manager;
import MazeRunner.CommandParser;

public class CommandInputGUI extends JPanel{
	
	Manager m;
	JLabel nameLabel;
	JTextField cInput;
	JButton submit;
	CommandParser cp;

	public CommandInputGUI(String name, Manager m, LevelDisplay ld){
		this.m = m;
		this.setSize(400,100);
		JLabel nameLabel = new JLabel(name+".");
		cInput = new JTextField(15);
		submit = new JButton("Submit");
		cp = new CommandParser(m);
		addListeners();
		this.add(nameLabel);
		this.add(cInput);
		this.add(submit);
		cp.addCommandListener(ld);
	}
	
	private void addListeners(){
		cInput.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				cp.process(cInput.getText());
				System.out.println(cp.getStatusMsg());
			}
		});
		submit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				cp.process(cInput.getText());
				System.out.println(cp.getStatusMsg());
			}
		});
	}

}