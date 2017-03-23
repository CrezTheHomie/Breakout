import java.awt.geom.*;
import java.awt.*;


public class Brick extends ColorShape {
		private int xPos = 0;
		private int yPos = 0;
		private int width =Parameters.BRICK_WIDTH;
		private int height = Parameters.BRICK_HEIGHT;
		private Rectangle2D.Double brick = new Rectangle2D.Double(xPos, yPos, width, height);

		// constructor
		public Brick(int x, int y, int w, int h) {
			super(new Rectangle2D.Double(x, y, w, h));
			
			//set brick x, y, width, and height
			xPos = x;
			yPos =y;
			width =w;
			height =h;
			brick = (Rectangle2D.Double)super.shape;
			// update shape
			brick.setRect(xPos, yPos, width, height);
			
		}

		public int getX() {	
			return xPos;
		}

		public int getY() {
			return yPos;
		}

		public void setY(int y){
			yPos = y;
		}

		public void setX(int x){
			xPos = x;
		}

		public Rectangle2D.Double getShape() {
			return brick;
		}
		@Override
		public  void paint(Graphics2D brush) {
		brush.setColor(Color.BLACK);
		brush.draw(brick);
		brush.setColor(Color.GREEN);
		brush.fill(brick);
		}

		// make the super bricks blue to differentiate them
		public  void paintS(Graphics2D brush) {
		brush.setColor(Color.BLACK);
		brush.draw(brick);
		brush.setColor(Color.BLUE);
		brush.fill(brick);
		}
	}
