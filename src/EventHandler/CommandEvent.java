package EventHandler;

import java.util.EventObject;

public class CommandEvent extends EventObject{
	
	String command;

	public CommandEvent(Object arg0, String command) {
		super(arg0);
		this.command=command;
	}
	
	public String getCommand(){
		return command;
	}

}
