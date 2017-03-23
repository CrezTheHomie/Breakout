import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;


public class Paddle extends ColorShape{
	
	private static int speed;
	private static int xPos = 250 ;
	private static final int height = 10;
	private static int width = 85;
	private static final int yPos = Parameters.PADDLE_START_Y;
	private static Rectangle2D.Double paddle = new Rectangle2D.Double(xPos,yPos,width,height);

	public Paddle() {
		super(paddle);
		speed = 0;
		super.setFillColor(Color.BLACK);
		super.setBorderColor(Color.BLACK);
	}

	public static void move() {
		if (xPos <=-1) {
			xPos = 0;
		}
		else if (xPos >= Parameters.GAME_WIDTH-width) {
			xPos = Parameters.GAME_WIDTH-width-1;
		}

		else {
			xPos += speed;
			paddle.setRect(xPos,yPos,width,height);
		}
	}

	public static void setSpeed(int newSpeed){
		speed = newSpeed;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getX() {
		return xPos;
	}

	public void bigWidth() {
		width += 20;
	}

	public void smallWidth() {
		width -= 20;
	}

	public static Rectangle2D.Double getShape() {
		return paddle;
	}

	public static void moveR() {
		speed = (Parameters.PADDLE_SPEED);
		
	}

	public static void moveL() {
		speed = (-1*Parameters.PADDLE_SPEED);
		
	}

	public void moveS() {
		speed = 0;
	}

	public void paint(Graphics2D brush) {
		brush.draw(paddle);
		brush.setColor(Color.BLACK);
		brush.fill(paddle);
	}

}