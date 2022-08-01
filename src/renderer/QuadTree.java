package renderer;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class QuadTree extends Canvas{

	public Center center;
	public Dimension dimension;
	QuadTree[] leafs = null;
	QuadTree northWest = null;
	QuadTree northEast = null;
	QuadTree southWest = null;
	QuadTree southEast = null;
	static int NodeCapacity = 4;
	static int maxLeafs = 4;
	Circle[] objects;
	//região delimitada
	AABB boudary;
	Graphics g;
	
	
	public QuadTree(Center center, Dimension dimension) {
		this.center = center;
		this.dimension = dimension;
		boudary = new AABB(
				center.x-dimension.width/2, 
				center.y-dimension.height/2,
				center.x+dimension.width/2,
				center.y+dimension.height/2
				);
		this.g = g;
		subdivide();
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.darkGray);
		g.drawRoundRect(center.x -dimension.width/2,  center.y-dimension.height/2, dimension.width, dimension.height, 0, 0);
		drawLeafs(g);
	}
	
	public void insert(Circle circle) {
		if(objects.length < NodeCapacity) {
			objects[objects.length+1] = circle;
		
		} else {
			subdivide();
		}
	}
	
	private void subdivide() {
		northWest = new QuadTree(new Center(center.x/2, center.y/2), new Dimension(dimension.width/2, dimension.height/2));
		northEast = new QuadTree(new Center(center.x + center.x/2, center.y/2), new Dimension(dimension.width/2, dimension.height/2));
		southWest = new QuadTree(new Center(center.x/2, center.y+center.y/2), new Dimension(dimension.width/2, dimension.height/2));
		southEast = new QuadTree(new Center(center.x+center.x/2, center.y+center.y/2), new Dimension(dimension.width/2, dimension.height/2));
	}
	
	private void drawLeafs(Graphics g) {
		if(northWest != null) {
			northWest.draw(g);
			northEast.draw(g);
			southWest.draw(g);
			southEast.draw(g);
		}
	}
	
}
