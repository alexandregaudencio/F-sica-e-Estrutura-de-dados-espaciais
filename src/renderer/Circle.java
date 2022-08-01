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
	float worldDimension;

	public int GetWidth() {
		return (int)radius*2;
	}
	
	public int GetHeight() {
		return (int)radius*2;
	}
	
	public void SetColor(Color color) {
		this.color = color;
	}
	
	public Circle(float x, float y, float velx, float vely, float radius, float worldDimension) {
		this.x = x;
		this.y = y;
		this.velx = velx;
		this.vely = vely;
		this.radius = radius;
	    color = Color.black;
	    this.worldDimension = worldDimension;
	    
	}
	
	public void move() {
		x += velx;
		y += vely;

		if(x <= 10 || x >= worldDimension-GetWidth()) {
		    velx =  velx*(-1);
		}
		if(y <= 10 || y >= worldDimension-GetHeight()) {
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
