import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Tank {
	
	private int x;
	private int y;
	
	private static final int X_SPEED = 5;
	private static final int   Y_SPEED = 5;
	
	private static final int TANK_WIDTH = 20;
	private static final int TANK_HEIGHT = 20;
	
	private boolean bL = false;
	private boolean bD = false;
	private boolean bR = false;
	private boolean bU = false;
	
	private Direction tankDirection = dirArr[r.nextInt(dirArr.length-1)];
	private Direction ptDirection = dirArr[r.nextInt(dirArr.length-1)];
	
	private static Direction[] dirArr= Direction.values();
	private TankWarClient twc;
	
	private boolean ai;
	private boolean live = true;
	
	
	private int step = 10 + r.nextInt(50);
	private int stepcount = 0;
	
	private static Random r = new Random();
	
	public Tank(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public Tank(int x, int y,TankWarClient twc){
		this(x,y);
		this.twc = twc;
	}
	
	public Tank(int x, int y , TankWarClient twc,boolean ai){
		this(x,y,twc);
		this.ai = ai;
	}
	

	public boolean isAi() {
		return ai;
	}
	
	public void setLive(boolean live){
		this.live = live;
	}

	public boolean isLive() {
		return live;
	}

	public void draw(Graphics g){
		if(!live){
			twc.aiTankList.remove(this);
			return;
		}
		Color initColor = g.getColor();
		if(!ai){
			g.setColor(Color.BLACK);
		}else{
			g.setColor(Color.RED);
		}
		g.fillOval(x,y,TANK_WIDTH,TANK_WIDTH);
		g.setColor(Color.WHITE);
		switch(ptDirection){
		case L:
			g.drawLine(x + TANK_WIDTH/2,y+TANK_HEIGHT/2, x, y+TANK_HEIGHT/2);
			break;
		case LD:
			g.drawLine(x + TANK_WIDTH/2,y+TANK_HEIGHT/2, x, y+TANK_HEIGHT);
			break;
		case D:
			g.drawLine(x + TANK_WIDTH/2,y+TANK_HEIGHT/2, x+TANK_WIDTH/2, y+TANK_WIDTH);
			break;
		case RD:
			g.drawLine(x + TANK_WIDTH/2,y+TANK_HEIGHT/2, x+TANK_WIDTH, y+TANK_HEIGHT);
			break;
		case R:
			g.drawLine(x + TANK_WIDTH/2,y+TANK_HEIGHT/2, x+TANK_WIDTH, y+TANK_WIDTH/2);
			break;
		case RU:
			g.drawLine(x + TANK_WIDTH/2,y+TANK_HEIGHT/2, x+TANK_WIDTH, y);
			break;
		case U:
			g.drawLine(x + TANK_WIDTH/2,y+TANK_HEIGHT/2, x+TANK_WIDTH/2, y);
			break;
		case LU:
			g.drawLine(x + TANK_WIDTH/2,y+TANK_HEIGHT/2, x, y)
			break;
		}
		g.setColor(initColor);
		if(ai){
			nexDrection();
		}
	}
	
	public void keyPress(KeyEvent e){
		switch(e.getKeyCode()){
		case KeyEvent.VK_SPACE:
			fire();
			break;
		case KeyEvent.VK_LEFT:
			bL = true;
			break;
		case KeyEvent.VK_RIGHT:
			bR = true;
			break;
		case KeyEvent.VK_UP:
			bU = true;
			break;
		case KeyEvent.VK_DOWN:
			bD = true;
			break;
			}
		nexDrection();
		}
	
	public void keyReleased(KeyEvent e){
		switch(e.getKeyCode()){
		case KeyEvent.VK_LEFT:
			bL = false;
			break;
		case KeyEvent.VK_RIGHT:
			bR = false;
			break;
		case KeyEvent.VK_UP:
			bU = false;
			break;
		case KeyEvent.VK_DOWN:
			bD = false;
			break;
			}
	nexDrection();
	}
		
	
	
	private void nexDrection (){
		if(ai){
			if(stepcount == step){
				stepcount = 0;
				int next = r.nextInt(dirArr.length -1);
				tankDirection = dirArr[next];
			}
			stepcount++;
			int isFire = r.nextInt(50);
			if(isFire==48){
				fire();
			}
		}else{
			if (bL && !bD && !bR && !bU) {
				tankDirection = Direction.L;
			} else if (bL && bD && !bR && !bU) {
				tankDirection = Direction.LD;
			} else if (!bL && bD && !bR && !bU) {
				tankDirection = Direction.D;
			} else if (!bL && bD && bR && !bU) {
				tankDirection = Direction.RD;
			} else if (!bL && !bD && bR && !bU) {
				tankDirection = Direction.R;
			} else if (!bL && !bD && bR && bU) {
				tankDirection = Direction.RU;
			} else if (!bL && !bD && !bR && bU) {
				tankDirection = Direction.U;
			} else if (bL && !bD && !bR && bU) {
				tankDirection = Direction.LU;
			}
		}
		switch(tankDirection){
		case L:
			x -= X_SPEED;
			break;
		case LD:
			x -= X_SPEED;
			y += Y_SPEED; 
			break;
		case D:
			y += Y_SPEED;
			break;
		case RD:
			x += X_SPEED;
			y += Y_SPEED; 
			break;
		case R:
			x += X_SPEED;
			break;
		case RU:
			x += X_SPEED;
			y -= Y_SPEED;
			break;
		case U:
			y -= Y_SPEED; 
			break;
		case LU:
			x -= X_SPEED;
			y -= Y_SPEED; 
			break;
		
		}
		x = Math.abs(x)%TankWarClient.WINDOWS_WIDTH;
		y = Math.abs(y)%TankWarClient.WINDOWS_HEIGHT;
		if(tankDirection != Direction.STOP){
			ptDirection = tankDirection;
		}
	}
	
	public Rectangle getRectangle(){
		return new Rectangle(x, y, TANK_WIDTH, TANK_WIDTH);
	}
	
	
	public void fire(){
		twc.missleList.add(new Missle(x+TANK_WIDTH/2,y+TANK_HEIGHT/2,ptDirection,this.twc,ai));
	}

}
