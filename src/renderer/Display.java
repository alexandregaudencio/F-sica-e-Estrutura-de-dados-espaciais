package renderer;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Display extends Canvas implements Runnable {
	private static final long serialVerionUID = 1L;
	private Thread thread;
	private JFrame frame;
	private static String title = "Scene Renderer";
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	private static boolean running = false;
	
	public Display() {
		this.frame = new JFrame();
		Dimension szie = new Dimension(WIDTH, HEIGHT);
		this.setPreferredSize(szie);
	}
	
	public static void main(String[] args) {
		Display display = new Display();
		display.frame.setTitle(title);
		display.frame.add(display);
		display.frame.pack();
		display.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		display.frame.setResizable(false);	
		display.frame.setVisible(true);	
		display.start();
	}
	
	public synchronized void start() {
		running = true;
		this.thread = new Thread(this, "Display");
		this.thread.start();
	}
	
	public synchronized void stop() {
		running = false;
		try {
			this.thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	public void run() {
		long lastTime = System.nanoTime();
		long timer =  System.currentTimeMillis();
		final double ns = 1000000000.0/60;
		double delta = 0;
		int frames = 0;
		
		
		
				
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				update();
				delta--;
			}
			render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				this.frame.setTitle(title + " | "+frames + " fps");
			}

			
			
		}
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		//g.setColor(Color.black);
		//g.fillRect(0, 0, WIDTH, HEIGHT);
		//
		//g.setColor(Color.cyan);
		//g.fillRect(10, 10, 200, 200);
		
		//generateSquare(g, Color.yellow, 300, 300, 300, 300);
		
		generateCircle(g, Color.blue, 400, 300, 20, 20);
		generateSquare(g, Color.black, 10, 10, WIDTH-20, HEIGHT-20);
		g.dispose();
		bs.show();
		
	}
	
	private void update() {
		
	}

	private void generateSquare(Graphics g, Color color, int x, int y, int width, int height) {
		g.setColor(color);
		g.drawRoundRect(x, y, width, height, 0, 0);
	}
	
	
	private void generateCircle(Graphics g, Color color, int x, int y, int width, int height)
	{
		g.setColor(color);
		g.fillOval(x, y, width, height);
		
	}
}
