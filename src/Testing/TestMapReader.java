package Testing;

import junit.framework.TestCase;
import FileIO.MapReader;
import Manager.Manager;

public class TestMapReader
    extends TestCase {
	
	MapReader mr;
	
	protected void setUp(){
		mr = new MapReader("src/inc/levels.dat", new Manager());
	}

    public void testReadLevel() {
        assertEquals(mr.getLevelNum(),1);
        assertEquals(mr.getLevelData(),"5 1 0 0 4 0 11 9 9 9 13");
    }
    
    public void testLevelArray(){
    	int[][] array = mr.getLevelArray();
    	assertEquals(array.length, 5);
    	assertEquals(array[0].length, 1);
    	assertEquals(array[0][0], 11);
    	assertEquals(array[1][0], 9);
    	assertEquals(array[2][0], 9);
    	assertEquals(array[3][0], 9);
    	assertEquals(array[4][0], 13);
    }
    
    public void testNextLevel(){
    	mr.nextLevel();
    	assertEquals(mr.getLevelNum(),2);
        assertEquals(mr.getLevelData(),"3 3 0 0 2 2 12 10 8 0 0 9 0 0 13");
        int[][] array = mr.getLevelArray();
    	assertEquals(array.length, 3);
    	assertEquals(array[0].length, 3);
    	assertEquals(array[0][0], 12);
    	assertEquals(array[0][1], 10);
    	assertEquals(array[0][2], 8);
    	assertEquals(array[1][0], 0);
    	assertEquals(array[1][1], 0);
    	assertEquals(array[1][2], 9);
    	assertEquals(array[2][0], 0);
    	assertEquals(array[2][1], 0);
    	assertEquals(array[2][2], 13);
    }
    
    public static void main(String[] args) {
        junit.textui.TestRunner.run(
            TestMapReader.class);
    }
}
