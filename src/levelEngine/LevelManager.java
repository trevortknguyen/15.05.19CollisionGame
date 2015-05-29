package levelEngine;

import java.util.Stack;

import engineTester.TimeManager;

public class LevelManager {
	private Stack<Integer> levelStack;
	
	public static final int LEVEL_SQUARE = 0x1;
	public static final int SWAP_BACKGROUND = 0x2;
	
	private long lastTime;
	private int count = 1;
	
	/**
	 * Creates a LevelManager object using a default constructor,
	 * however there should only be one LevelManager object per
	 * instance of a game.
	 */
	public LevelManager() {
		levelStack = new Stack<>();  
		
		for (int i = 0; i < 50; i++)
			levelStack.push(LEVEL_SQUARE);
		
		lastTime = TimeManager.getTime();
	}
	
	/**
	 * Called in the main class. After a certain amount of time,
	 * the LevelManager will push an order to add more
	 * objects to the screen.
	 */
	public void update() {                                     //calls methods to add in more cube objects
		if (TimeManager.getTime() - lastTime > count * 1000) {
			for (int i = 0; i < 5; i++)
			levelStack.push(LEVEL_SQUARE);
			levelStack.push(SWAP_BACKGROUND);
			
			lastTime = TimeManager.getTime();
			count++;
		}
	}
	
	/**
	 * 
	 * @return The next order from the stack.
	 */
	public int getNextEntity() {
		return levelStack.pop();
	}
	/**
	 * returns true or false depending on if there is another object to be added
	 */
	public boolean hasNext() {
		return !levelStack.isEmpty();
	}
}
