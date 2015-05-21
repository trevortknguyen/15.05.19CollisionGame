package engineTester;

public class TimeManager {
	private static long lastFrame;
	private static long delta;
	
	public TimeManager() {
		initialize();
	}
	
	private void initialize() {
		lastFrame = getTime();
	}
	
	public static long getTime() {
		return System.currentTimeMillis();
	}
	
	public static long newFrame() {
		long currentFrame = getTime();
		delta = currentFrame - lastFrame;
		lastFrame = getTime();
		return delta;
	}
	
	public static float getDelta() {
		return delta / 1000.0f;
	}
}
