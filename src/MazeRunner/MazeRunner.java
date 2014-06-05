package MazeRunner;

public class MazeRunner {
	
	int x;
	int y;
	int xDir;
	int yDir;
	
	public MazeRunner(int x, int y, int xDir, int yDir){
		this.x = x;
		this.y = y;
		this.xDir = xDir;
		this.yDir = yDir;
	}
	
	public void move(){
		x+=xDir;
		y+=yDir;
		System.out.println(x+" "+y);
	}
	
	public void aboutFace(){
		xDir*=-1;
		yDir*=-1;
	}
	
	public void turnLeft(){
		if(xDir==0){
			xDir+=yDir;
			yDir=0;
		}
		else{
			yDir-=xDir;
			xDir=0;
		}
	}
	
	public void say(String s){
		System.out.println("Saying: "+s);
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getXDir(){
		return xDir;
	}
	
	public int getYDir(){
		return yDir;
	}

}
