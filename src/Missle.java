import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

public class Missle {
	
	private int x;
	private int y;
	
	private static final int MISSLE_WIDTH = 5;
	private static final int MISSLE_HEIGHT =5;
	private static final int MISSLE_SPEED = 10;
	
	private Direction direction;
	private TankWarClient twc;
	
	private boolean ai;
	private boolean live = true;

	public Missle(int x, int y, Direction direction) {
		this.x = x;
		this.y = y;
		this.direction = direction;
	}
	
	public Missle(int x,int y, Direction direction,TankWarClient twc,boolean ai){
		this(x,y,direction);
		this.twc = twc;
		this.ai =ai;
	}
	
	public void draw(Graphics g){
		if(!live){
			twc.missleList.remove(this);
			return;
		}
		Color c = g.getColor();
		g.fillOval(x, y,MISSLE_WIDTH, MISSLE_HEIGHT);
		switch(direction){
		case L:
			g.fillOval(x, y, MISSLE_WIDTH, MISSLE_HEIGHT);
			x -= MISSLE_SPEED;
			break;
		case LD:
			g.fillOval(x, y, MISSLE_WIDTH, MISSLE_HEIGHT);
			x -= MISSLE_SPEED;
			y += MISSLE_SPEED;
			break;
		case D:
			g.fillOval(x, y, MISSLE_WIDTH, MISSLE_HEIGHT);
			y += MISSLE_SPEED;
			break;
		case RD:
			g.fillOval(x, y, MISSLE_WIDTH, MISSLE_HEIGHT);
			x += MISSLE_SPEED;
			y += MISSLE_SPEED;
			break;
		case R:
			g.fillOval(x, y, MISSLE_WIDTH, MISSLE_HEIGHT);
			x += MISSLE_SPEED;
			break;
		case RU:
			g.fillOval(x, y, MISSLE_WIDTH, MISSLE_HEIGHT);
			x += MISSLE_SPEED;
			y += MISSLE_SPEED;
			break;
		case U:
			g.fillOval(x, y, MISSLE_WIDTH, MISSLE_HEIGHT);
			y -= MISSLE_SPEED;
			break;
		case LU:
			g.fillOval(x, y, MISSLE_WIDTH, MISSLE_HEIGHT);
			x -= MISSLE_SPEED;
			y -= MISSLE_SPEED;
			break;
		}
		g.setColor(c);
		if(x<0||x>TankWarClient.WINDOWS_WIDTH || y<0 || y>TankWarClient.WINDOWS_HEIGHT){
			twc.missleList.remove(this);
		}
		hitTank(twc.aiTankList);
		hit(twc.myTank);
	}
	
	public Rectangle getRectangle(){
		return new Rectangle(x, y, MISSLE_WIDTH, MISSLE_WIDTH);
	}
	
	public void hit(Tank aiTank){
		if(live && getRectangle().intersects(aiTank.getRectangle()) && aiTank.isLive() && ai!= aiTank.isAi()){
			aiTank.setLive(false);
			twc.explodeList.add(new Explode(x ,y,twc));
			live = false;
		}
	}
	
	public void hitTank(List<Tank> tankList){
		for(int i=0; i<tankList.size();i++){
			hit(tankList.get(i));
		}
	}
}
