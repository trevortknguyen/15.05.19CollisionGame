package input;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

public class MousePosition extends GLFWCursorPosCallback {
	private static float x, y;
	@Override
	public void invoke(long window, double xpos, double ypos) {
		x = (float) xpos;
		y = (float) ypos;
	}

	public static float getX() {
		return x;
	}
	
	public static float getY() {
		return y;
	}
	
}
