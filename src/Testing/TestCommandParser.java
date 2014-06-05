package Testing;

import junit.framework.TestCase;
import Manager.Manager;
import MazeRunner.CommandParser;


public class TestCommandParser extends TestCase {
	
	CommandParser cp;
	
	protected void setUp(){
		cp = new CommandParser(new Manager());
	}
	
	public void testProcess(){
		assertEquals(cp.process(""),-1);
		assertEquals(cp.getStatusMsg(),"No Method Called");
		assertEquals(cp.process("say()"),-1);
		assertEquals(cp.getStatusMsg(),"Semicolon Expected");
		assertEquals(cp.process("say;"),-1);
		assertEquals(cp.getStatusMsg(),"Parentheses Expected");
		assertEquals(cp.process("say(;"),-1);
		assertEquals(cp.getStatusMsg(),"Parentheses Expected");
		assertEquals(cp.process("say(\");"),-1);
		assertEquals(cp.getStatusMsg(),"Quotations Expected for String Parameter");
		assertEquals(cp.process("say(\"\");"),0);
		assertEquals(cp.getStatusMsg(),"");
		assertEquals(cp.process("say(\"hello\");"),0);
		assertEquals(cp.getStatusMsg(),"hello");
		assertEquals(cp.process("move();"),1);
		assertEquals(cp.process("move(7);"),-1);
		assertEquals(cp.getStatusMsg(),"Too many parameters for method - move");
		assertEquals(cp.process("turnLeft();"),2);
		assertEquals(cp.process("turnLeft(7);"),-1);
		assertEquals(cp.getStatusMsg(),"Too many parameters for method - turnLeft");
		assertEquals(cp.process("aboutFace();"),3);
		assertEquals(cp.process("aboutFace(7);"),-1);
		assertEquals(cp.getStatusMsg(),"Too many parameters for method - aboutFace");
		assertEquals(cp.process("turnRight();"),4);
		assertEquals(cp.process("turnRight(7);"),-1);
		assertEquals(cp.getStatusMsg(),"Too many parameters for method - turnRight");
	}

}
