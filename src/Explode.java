import java.awt.*;

public class Explode {
	private int x;
	private int y;

	private int[] d = {2,7,12,17,22,27,32,25,20,15,0};
	private int count = 0;
	
	private boolean live = true;
	private TankWarClient twc;
	
	public Explode(int x, int y, TankWarClient twc) {
		this.x = x;
		this.y = y;
		this.twc = twc;
	}
	
	public void draw(Graphics g){
		if(count == d.length){
			twc.explodeList.remove(this);
			return;
		}
		Color c = g.getColor();
		g.setColor(Color.ORANGE);
		g.fillOval(x, y, d[count], d[count]);
		g.setColor(c);
		count++;
	}
}
