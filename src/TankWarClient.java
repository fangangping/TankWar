import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList; 
import java.util.List;

public class TankWarClient extends Frame {
	
	private int x=0;
	private int y=0;
	
	public static final int WINDOWS_WIDTH = 800;
	public static final int WINDOWS_HEIGHT = 600;
	
	private Image offScreenImage = null;
	
	Tank myTank = new Tank(20,20,this,false);
	
	
	List<Missle> missleList = new ArrayList<>(); 
	List<Explode> explodeList = new ArrayList<>();
	List<Tank> aiTankList = new ArrayList<>();
	
	public void launchFrame(){
		this.setSize(WINDOWS_WIDTH, WINDOWS_HEIGHT);
		this.setResizable(false);
		this.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.addKeyListener(new KeyMonitor());
		addTank();
		this.setVisible(true);
		new Thread(new paintThread()).start();
	}
	
	public void paint(Graphics g){
		myTank.draw(g);
		for(int i=0;i<aiTankList.size();i++){
			aiTankList.get(i).draw(g);
		}
		for(int i=0;i< explodeList.size();i++){
			explodeList.get(i).draw(g);
		}
		for(int i=0;i< missleList.size();i++){
			missleList.get(i).draw(g);
		}
		g.drawString("missle count = " + missleList.size() , 50, 50);
		g.drawString("tank count = " + aiTankList.size() , 50, 70);
		g.drawString("explode count = " + explodeList.size() , 50, 100);

	}
	
	public void update(Graphics g){
		if(offScreenImage == null){
			offScreenImage = this.createImage(WINDOWS_WIDTH, WINDOWS_HEIGHT);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.WHITE);
		gOffScreen.fillRect(0, 0, WINDOWS_WIDTH, WINDOWS_HEIGHT);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
		//System.out.println("update");
	}
	
	public static void main(String[] args) {
		TankWarClient twc = new TankWarClient();
		twc.launchFrame();
	}
	
	private class paintThread implements Runnable{
		public void run(){
			while(true){
				try {
					Thread.sleep(20);
					repaint();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
			}
		}
	}

	private class KeyMonitor extends KeyAdapter{
		@Override
		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
			myTank.keyPress(e);
		}
	}
	
	public void addTank(){
		for(int i=0;i<10;i++){
			aiTankList.add(new Tank(20 + i*30,100,this,true));
		}
	}
	
	
}
