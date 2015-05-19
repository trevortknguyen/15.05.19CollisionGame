package input;

import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.*;

public class Input extends GLFWKeyCallback {

	public static boolean[] keys = new boolean[65535];
	
	public static int lastEventKey;
	public static int lastEventAction;
	
	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		keys[key] = action != GLFW_RELEASE;
		lastEventKey = key;
		lastEventAction = action;
	}
	
	public static void resetKeys() {
		lastEventKey = GLFW_KEY_UNKNOWN;
		lastEventAction = GLFW_KEY_UNKNOWN;
	}
}
