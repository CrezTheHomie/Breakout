import java.awt.*;
import java.awt.geom.*;
import java.util.Random;

public class BrickConfiguration {
	
	//location and size variables
	private static final int xStart = 0;
	private static final int yStart = 0;
	private static final int numCols = 10;
	private static final int numRows = 8;
	private static final int brickHeight =Parameters.BRICK_HEIGHT;
	private static final int brickWidth = Parameters.BRICK_WIDTH;


	// 2D array containing brick objects
	private static Brick[][] bricks = new Brick[numCols][numRows];

	// 2D array telling us whether the brick should be painted (has it been hit?)
	private static boolean[][] paintBricks = new boolean[numCols][numRows];
	
	private static boolean[][] superBrick = new boolean[numCols][numRows];
	// constructor
	public BrickConfiguration() {
		
		// create new bricks and store them in bricks array
		for (int i = 0; i < numCols; i++) {
			for (int j = 0; j < numRows; j++) {
				// initialize paintBricks[i][j]
				paintBricks[i][j] = true;
				// make 1/25 bricks into SUPER bricks
				int r = (int)(15*Math.random());
				if (r == 1) {
					superBrick[i][j] = true;
				}
				// initialize bricks[i][j]
				bricks[i][j] = new Brick((xStart + i*(Parameters.BRICK_WIDTH + Parameters.BRICK_SEP)),
						(yStart + j*(Parameters.BRICK_HEIGHT + Parameters.BRICK_SEP)),
						Parameters.BRICK_WIDTH,
						Parameters.BRICK_HEIGHT);
			}
		}
	}

	// paint the bricks array to the screen
	public void paint(Graphics2D brush) {
		for (int i = 0; i < numCols; i++) {
			for (int j = 0; j < numRows; j++) {
				// determine if brick should be painted
				// if so, call paintBrick()
				if (paintBricks[i][j] != false) {
					//if a brick is a super brick it gets its own paint method. So special.
					if (superBrick[i][j] == true){
						paintSuperBrick(bricks[i][j],brush);
					}else {
						paintBrick(bricks[i][j], brush);
					}
				}
			}
		}
	}

	// paint an individual brick
	public void paintBrick(Brick brick, Graphics2D brush) {
		// call brick's paint method
		brick.paint(brush);
	}

	public void paintSuperBrick(Brick brick, Graphics2D brush) {
		// call brick's paint method
		brick.paintS(brush);
	}

	public void removeBrick(int row, int col) {
		// update paintBricks array for destroyed brick
		paintBricks[col][row] = false;
	}

	public boolean wasitsuper(int row, int col) {
		return superBrick[col][row];
	}

	public static Brick getBrick(int row, int col) {
		return bricks[col][row];
	}

	public boolean exists(int row, int col) {
		return paintBricks[col][row];
	}

	public int getRows() {
		return numRows;
	}

	public int getCols() {
		return numCols;
	}

}