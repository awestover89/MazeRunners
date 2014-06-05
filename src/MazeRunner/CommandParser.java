package MazeRunner;

import java.util.ArrayList;
import java.util.Iterator;

import EventHandler.CommandEvent;
import EventHandler.CommandListener;
import Manager.Manager;

public class CommandParser{

	public final int ERROR = -1;
	public final int SAY = 0;
	public final int MOVE = 1;
	public final int TURN_LEFT = 2;
	public final int ABOUT_FACE = 3;
	public final int TURN_RIGHT = 4;
	
	private ArrayList<CommandListener> _listeners = new ArrayList<CommandListener>();
	
	private String statusMsg;
	
	Manager m;

	public CommandParser(Manager m){
		this.m = m;
		statusMsg = "";
	}
	
    public synchronized void addCommandListener( CommandListener l ) {
        _listeners.add( l );
    }
    
    public synchronized void removeCommandListener( CommandListener l ) {
        _listeners.remove( l );
    }

    private synchronized void fireEvent(String command) {
        CommandEvent c = new CommandEvent( this, command );
        Iterator listeners = _listeners.iterator();
        while( listeners.hasNext() ) {
            ( (CommandListener) listeners.next() ).commandGiven( c );
        }
    }
	
	public int process(String command){
		statusMsg = "";
		if(command.trim().length()==0){
			statusMsg = "No Method Called";
			return -1;
		}
		if(command.charAt(command.length()-1)!=';'){
			statusMsg = "Semicolon Expected";
			return -1;
		}
		if(!(command.indexOf("(")!=-1 && command.charAt(command.length()-2)==(')'))){
			statusMsg = "Parentheses Expected";
			return -1;
		}
		if(command.length() > 3 && command.substring(0,3).equals("say")){
			if(!(command.charAt(4)==('\"') && command.charAt(command.length()-3)==('\"') && command.length()-3!=4)){
				statusMsg = "Quotations Expected for String Parameter";
				return -1;
			}
			statusMsg = command.substring(5,command.length()-3);
			fireEvent("say,"+statusMsg);
			return 0;
		}
		if(command.length() > 4 && command.substring(0,4).equals("move")){
			if(command.length()==7){
				if(m.isMoveLegal()){
					fireEvent("move");
					return 1;
				}
				statusMsg = "That is not a legal move";
				return -1;
			}
			statusMsg = "Too many parameters for method - move";
			return -1;
		}
		if(command.length() > 8 && command.substring(0,8).equals("turnLeft")){
			if(command.length()==11){
				fireEvent("turnLeft");
				return 2;
			}
			statusMsg = "Too many parameters for method - turnLeft";
			return -1;
		}
		/*if(command.length() > 9 && command.substring(0,9).equals("aboutFace")){
			if(command.length()==12){
				fireEvent("aboutFace");
				return 3;
			}
			statusMsg = "Too many parameters for method - aboutFace";
			return -1;
		}
		if(command.length() > 9 && command.substring(0,9).equals("turnRight")){
			if(command.length()==12){
				fireEvent("turnRight");
				return 4;
			}
			statusMsg = "Too many parameters for method - turnRight";
			return -1;
		}*/
		statusMsg = "Unknown Command - "+command;
		return -1;
	}
	
	public String getStatusMsg(){
		return statusMsg;
	}
}