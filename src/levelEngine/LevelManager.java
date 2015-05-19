package levelEngine;

import java.util.Stack;

import engineTester.TimeManager;

public class LevelManager {
	private Stack<Integer> levelStack;
	
	public static int LEVEL_SQUARE = 0x1;
	
	private long lastTime;
	private int count = 1;
	
	
	public LevelManager() {
		levelStack = new Stack<>();
		
		for (int i = 0; i < 5; i++)
			levelStack.push(LEVEL_SQUARE);
		
		lastTime = TimeManager.getTime();
	}
	
	public void update() {
		if (TimeManager.getTime() - lastTime > count * 1000) {
			for (int i = 0; i < 15; i++)
			levelStack.push(LEVEL_SQUARE);

			lastTime = TimeManager.getTime();
			count++;
		}
	}
		
	public int getNextEntity() {
		return levelStack.pop();
	}
	
	public boolean hasNext() {
		return !levelStack.isEmpty();
	}
}
