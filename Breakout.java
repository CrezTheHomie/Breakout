import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.util.Random;



public class Breakout {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(Parameters.GAME_WIDTH,Parameters.GAME_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Breakout!");
		frame.setResizable(false);
		GamePanel game = new GamePanel();
		frame.add(game);
		frame.setVisible(true);
	}

	public static class GamePanel extends JPanel {
		//instance variables
		Paddle paddle;
		Timer timer;
		Ball ball;
		BrickConfiguration bconfig;
		int score;
		Timer scoretimer;
		Boolean isDead;
		int lives;
		
		public GamePanel() {
			//constructors
			super();
			initializeGameObjects();
			addKeyListener(new PaddleMover());
			setFocusable(true);

			
		}

		@Override
		public void paintComponent(Graphics g) {
			g.setColor(Color.WHITE);
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D)g;
			// this is a game over boolean
			if (isDead) {
				g2.setColor(Color.BLACK);
				g2.setFont(new Font("Helvetica", Font.PLAIN, 30));
				g2.drawString("Game Over!", 220, 200);
				g2.drawString("Your final score was " + score, 100, 250);
				timer.stop();
			} else {
				paddle.paint(g2);
				ball.paint(g2);
				bconfig.paint(g2);
				g2.setColor(Color.BLACK);
				g2.setFont(new Font("Helvetica", Font.PLAIN, 15));
				g2.drawString("Score: " + score, 30,350);
				g2.drawString("Lives: " + lives, 31,335); 
			}
		}
		public void initializeGameObjects() {
			// instantiate ball, paddle, and brick configuration

			// set up timer to run GameMotion() every 10ms
			score = 0;
			lives =3;
			isDead = false;
			paddle = new Paddle();
			ball = new Ball();
			bconfig = new BrickConfiguration();
			timer = new Timer(10, new GameMotion());
			timer.start();
		}
		//methods for gamepanel like keylistener and such

		private class PaddleMover implements KeyListener {
			public void keyPressed(KeyEvent evt) {
				int key = evt.getKeyCode();
				if (key == KeyEvent.VK_RIGHT) {
					paddle.moveR();
					}
			 	else if (key == KeyEvent.VK_LEFT) {
					paddle.moveL();
					}
			}
			public void keyReleased(KeyEvent evt) {
				int key = evt.getKeyCode();
				if ((key == KeyEvent.VK_LEFT) || (key == KeyEvent.VK_RIGHT)) {
					paddle.moveS();
				}
			}
				
			public void keyTyped(KeyEvent evt) {}
		}
		
		public void checkForHit() {
			int ySpeed = ball.getYspeed();
			int xSpeed = ball.getXspeed();
			if (ball.getShape().intersects(paddle.getShape())) {
				int leftSide = paddle.getX();
				int middleLeft = paddle.getX() + (int)(paddle.getWidth()/3);
				int middleRight = paddle.getX() + (int)(2*paddle.getWidth()/3);
				int rightSide = paddle.getX() + paddle.getWidth();
				

				if ((ball.getX() >= leftSide) && (ball.getX() < middleLeft)) {
					if (ball.getXspeed() <= 0) {
						ball.setXspeed(xSpeed - 1);
						ball.setYspeed(-1*ySpeed);
					}
					if (ball.getXspeed() >=0) {
						ball.setXspeed(-1*xSpeed + 1);
						ball.setYspeed(-1*ySpeed);
					}
				}
				if ((ball.getX() >= middleLeft) && (ball.getX() <= middleRight)) {
					if (ball.getXspeed() <=0) {
						ball.setXspeed(xSpeed - 1);
						ball.setYspeed(-1*ySpeed);
					}
					if (ball.getXspeed() >=0) {
						ball.setXspeed(xSpeed + 1);
						ball.setYspeed(-1*ySpeed);
					}
				}
				if ((ball.getX() > middleRight) && (ball.getX() <= rightSide)) {
					if (ball.getXspeed() >=0) {
						ball.setXspeed(xSpeed + 1);
						ball.setYspeed(-1*ySpeed);
					}
					if (ball.getXspeed() <=0) {
						ball.setXspeed(-1*xSpeed - 1);
						ball.setYspeed(-1*ySpeed);
					}
				}
			}
			for (int i = 0; i < bconfig.getRows(); i++) {
				for (int j = 0; j < bconfig.getCols(); j++) {
					if (bconfig.exists(i,j)) {
						if (ball.getShape().intersects(bconfig.getBrick(i,j).getShape())) {
							Point ballLeft = new Point((int)ball.getShape().getX(), (int)(ball.getShape().getY() + ball.getShape().getHeight()/2));
							Point ballRight = new Point((int)(ball.getShape().getX() + ball.getShape().getWidth()), (int)(ball.getShape().getY() + ball.getShape().getHeight()/2));
							Point ballTop = new Point((int)(ball.getShape().getX() + ball.getShape().getWidth()/2), (int)ball.getShape().getY());
							Point ballBottom = new Point((int)(ball.getShape().getX() + ball.getShape().getWidth()/2), (int)(ball.getShape().getY() + ball.getShape().getHeight()));
							if (bconfig.getBrick(i,j).getShape().contains(ballLeft)) {
								// change ball speed
								ball.setYspeed(-1*ySpeed);
							}
							else if(bconfig.getBrick(i,j).getShape().contains(ballRight)) {
								// change ball speed
								ball.setYspeed(-1*ySpeed);
							}
							if (bconfig.getBrick(i,j).getShape().contains(ballTop)) {
								// change ball speed
								ball.setYspeed(-1*ySpeed);
							}
							else if (bconfig.getBrick(i,j).getShape().contains(ballBottom)) {
								// change ball speed
								ball.setYspeed(-1*ySpeed);
							}
							// remove brick
							// if a brick is a super brick special things happen
							if (bconfig.wasitsuper(i,j) == true) {
								bconfig.removeBrick(i,j);
								score += 200;
								// randomly determine which power up you get from a super brick
								int r = (int)(5*Math.random());
								if (r <= 3) {
									paddle.bigWidth();
								}
								if (r == 4) {
									paddle.smallWidth();
								}
								if ( r <= 7 && r>= 5) {
									ball.bigBall();
								}
								if (r >= 8 && r<= 9) {
									ball.smallBall();
								}
							} else {
								bconfig.removeBrick(i,j);
								score += 100;
							}
						}
					}
				}
			}
			// ball reset and life increment
			if (ball.getY() == Parameters.GAME_HEIGHT) {
				lives -= 1;
				ball.setY(Parameters.BALL_START_Y);
				ball.setX(Parameters.BALL_START_X);
				ball.setXspeed(Parameters.BALL_SPEED_X);
				ball.setYspeed(Parameters.BALL_SPEED_Y);
				ball.setWidth(Parameters.BALL_WIDTH);
				ball.setHeight(Parameters.BALL_HEIGHT);
				if (lives == 0){
					isDead = true;
				}
			}
		}

		private class GameMotion implements ActionListener {
			public GameMotion() {

			}

			public void actionPerformed(ActionEvent evt) {
				//move ball automatically
				ball.move();

				//move paddle according to key press
				paddle.move();

				//check if the ball hits the paddle or a brick
				checkForHit();
				
				//call repaint
				repaint();
			}
		}	
	}
}