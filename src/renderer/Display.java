package renderer;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;

public class Display extends Canvas implements Runnable {
	private static final long serialVerionUID = 1L;
	private Thread thread;
	private JFrame frame;
	private static String title = "Scene Renderer";
	private static final int WIDTH = 800;
	private static final int HEIGHT = 800;
	private static boolean running = false;
	Circle[] circles;
	List<Circle> effects = new ArrayList<Circle>();
	int objects = 6;
	int maxRadius = 10;
	QuadTree quadTree;
	Dimension dimension;
	
	
	Random random = new Random();

	public Display() {
		this.frame = new JFrame();
		dimension = new Dimension(WIDTH, HEIGHT);
		this.setPreferredSize(dimension);
	}
	
	public static void main(String[] args) {
		Display display = new Display();
		display.frame.setTitle(title);
		display.frame.add(display);
		display.frame.pack();
		display.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		display.frame.setResizable(true);	
		display.frame.setVisible(true);	
		display.start();
	
	}
	
	public synchronized void start() {
		running = true;
		this.thread = new Thread(this, "Display");
		this.thread.start();
		circles = new Circle[objects*objects];
		int count = 0;

		for(int i = 0; i < objects; i++ ) {
			for(int j = 0; j < objects; j++ ) {
				int xoffset = 30;
				int yoffset = 30;
				circles[count] = new Circle(xoffset+i*xoffset, yoffset+j*yoffset, 
						random.nextFloat(3), random.nextFloat(3), maxRadius, WIDTH);
				count++;
			}
		}
		
		quadTree = new QuadTree(new Center(WIDTH/2, HEIGHT/2), new Dimension(WIDTH, HEIGHT));

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
				frames = 0;
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
		update(g);

		for(Circle c : circles) {
			c.drawCircle(g);
		}
		
		if(effects != null) {
			for(Circle circle : effects) {
				circle.drawCircle(g);
			}

		}

		
		generateSquare(g, Color.black, 10, 10, WIDTH-20, HEIGHT-20);
		quadTree.draw(g);
		g.dispose();
		bs.show();
		
	}
	
	private void update() {
		for(Circle circle : circles) {
			circle.move();
		}
		
		CircleCollisionDetection();
		
	}

	private void generateSquare(Graphics g, Color color, int x, int y, int width, int height) {
		g.setColor(color);
		g.drawRoundRect(x, y, width, height, 0, 0);
	}
	
	
	private void CircleCollisionDetection() {
		for(Circle circle : circles) {
			for(Circle oCircle : circles) {
				if(circle != oCircle) {
					double dx = Math.abs((double)circle.x - (double)oCircle.x);
					double dy = Math.abs((double)circle.y - (double)oCircle.y);
					float distance = (float)Math.sqrt(dx*dx+dy*dy);

					if(distance < circle.radius+circle.radius) {
						float diferenceX = oCircle.x - circle.x;
						float diferenceY = oCircle.y - circle.y;
						circle.velx = -diferenceX/maxRadius;
						circle.vely = -diferenceY/maxRadius;

						Circle effect = new Circle((circle.x+oCircle.x)/2, (circle.y+oCircle.y)/2, 0,0,2, WIDTH);
						effect.SetColor(Color.red);
						effects.add(effect);
						
				}
				
				}
			}
		}
	}

}
