import java.awt.geom.*;
import java.awt.*;


public class Ball extends ColorShape {


	private static int xPos = Parameters.BALL_START_X;
	private static int yPos = Parameters.BALL_START_Y;
	private int xSpeed;
	private int ySpeed;
	private static int height = Parameters.BALL_HEIGHT;
	private static int width = Parameters.BALL_WIDTH;
	private static final Ellipse2D.Double ball = new Ellipse2D.Double(xPos, yPos, width, height);

	public Ball() {
		super(ball);

		super.setFillColor(Color.RED);
		super.setBorderColor(Color.RED);
		xSpeed = Parameters.BALL_SPEED_X;
		ySpeed = Parameters.BALL_SPEED_Y;
	}

	public void move() {
		if (yPos <=0) {
			ySpeed = -1*ySpeed;
		}

		if (xPos <= 0 || xPos >= Parameters.GAME_WIDTH-10) {
			xSpeed = -1*xSpeed;
		}
		xPos += xSpeed;
		yPos += ySpeed;

		ball.setFrame(xPos,yPos,width,height);
	}
	public void setXspeed(int newSpeed) {
		xSpeed = newSpeed;
	}

	public void setYspeed(int newSpeed) {
		ySpeed = newSpeed;
	}

	public int getX() {
		return xPos;
	}

	public int getY() {
		return yPos;
	}

	public void setY(int y) {
		yPos = y;
	}

	public void setX(int x) {
		xPos = x;
	}

	public int getWidth() {
		return width;
	}

	public void setHeight(int h) {
		height = h;
	}

	public void setWidth(int w) {
		width = w;
	}

	public void bigBall() {
		width += 5;
		height += 5;
	}

	public void smallBall() {
		width -= 3;
		height -= 3;
	}

	public int getXspeed() {
		return xSpeed;
	}

	public int getYspeed() {
		return ySpeed;
	}

	public int getHeight() {
		return height;
	}

	public static Ellipse2D.Double getShape() {
		return ball;
	}

	public void paint(Graphics2D brush) {
		brush.setColor(Color.BLACK);
		brush.draw(ball);
		brush.setColor(Color.RED);
		brush.fill(ball);
	}
}