package input;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

/**
 * Main callback as part of GLFW to manage and get the cursor
 * position.
 * 
 * @author Thai Nguyen
 *
 */
public class MousePosition extends GLFWCursorPosCallback {
	private static float x, y;
	
	/**
	 * This is called every time the the pollEvents
	 * method is called in the main class.
	 */
	@Override
	public void invoke(long window, double xpos, double ypos) {
		x = (float) xpos;
		y = (float) ypos;
	}

	/**
	 * 
	 * @return x-position
	 */
	public static float getX() {
		return x;
	}
	
	/**
	 * 
	 * @return y-position
	 */
	public static float getY() {
		return y;
	}
	
}
