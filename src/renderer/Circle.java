package renderer;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

public class Circle extends Canvas implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Color color;
	float x;
	float y;
	float radius;
	public float velx;
	public float vely;
	Thread thread;
	int circleID;
	
	public int GetWidth() {
		return (int)radius*2;
	}
	
	public int GetHeight() {
		return (int)radius*2;
	}
	
	public void SetColor(Color color) {
		this.color = color;
	}
	
	public Circle(float x, float y, float velx, float vely, float radius, int ID) {
		this.circleID = ID;
		this.x = x;
		this.y = y;
		this.velx = velx;
		this.vely = vely;
		this.radius = radius;
	    color = Color.black;	
	    
	}
	
	public void move() {
		x += velx;
		y += vely;

		if(x <= 10 || x >= 1280-GetWidth()) {
		    velx =  velx*(-1);
		}
		if(y <= 10 || y >= 720-GetHeight()) {
			vely =  vely*(-1);
		}
	}
	public synchronized void start() {
		this.thread = new Thread(this);
		this.thread.start();
	}

	
	public void drawCircle(Graphics g)
	{
		g.setColor(color);
		g.fillOval((int)x,  (int)y, GetWidth(),  GetHeight());
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	

}
