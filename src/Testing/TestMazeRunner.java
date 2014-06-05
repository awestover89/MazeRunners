package Testing;


import junit.framework.TestCase;
import MazeRunner.MazeRunner;

public class TestMazeRunner extends TestCase{

	MazeRunner mr;
	
	public void setUp() throws Exception {
		mr = new MazeRunner(0,0,1,0);
	}
	
	public void testMove(){
		mr.move();
		assertEquals(mr.getX(),1);
		assertEquals(mr.getY(),0);
		mr.turnLeft();
		mr.move();
		assertEquals(mr.getX(),1);
		assertEquals(mr.getY(),1);
	}
	
	public void testTurnLeft(){
		mr.turnLeft();
		assertEquals(mr.getXDir(),0);
		assertEquals(mr.getYDir(),1);
		mr.turnLeft();
		assertEquals(mr.getXDir(),-1);
		assertEquals(mr.getYDir(),0);
		mr.turnLeft();
		assertEquals(mr.getXDir(),0);
		assertEquals(mr.getYDir(),-1);
		mr.turnLeft();
		assertEquals(mr.getXDir(),1);
		assertEquals(mr.getYDir(),0);
	}
	
	public void testAboutFace(){
		mr.aboutFace();
		assertEquals(mr.getXDir(),-1);
		mr.turnLeft();
		mr.aboutFace();
		assertEquals(mr.getYDir(),1);
	}

}
