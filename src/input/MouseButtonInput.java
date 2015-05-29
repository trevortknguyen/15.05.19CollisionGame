package input;

import org.lwjgl.glfw.GLFWMouseButtonCallback;

/**
 * Main callback to retrieve whether or not the mouse
 * buttons are being clicked.
 * @author Thai Nguyen
 *
 */
public class MouseButtonInput extends GLFWMouseButtonCallback {
	public static int lastButton;
	public static int lastAction;
	
	/**
	 * Method is called every time pollEvents 
	 * method is called in the main class.
	 */
	@Override
	public void invoke(long window, int button, int action, int mods) {
		lastButton = button;
		lastAction = action;
	}

	public static void resetMouse() {
		lastButton = -1;
		lastAction = -1;
	}
}
