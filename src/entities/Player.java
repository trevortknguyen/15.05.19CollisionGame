package entities;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFWvidmode.height;
import static org.lwjgl.glfw.GLFWvidmode.width;

import java.nio.ByteBuffer;

import input.Input;
import input.MousePosition;
import engineTester.Main;

public class Player extends Square {
	/*
	 * creates a varaible that stoes the speed
	 */
	float speedFactor = 75.0f;
	/*
	 *Constructs the player object with the location as varaibles 
	 */
	public Player(float x, float y) {
		super(x, y, 10);
	}
	/*
	 * Updates the location of the player object to the location of the cursor
	 */
	public void update() {
		ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());  //Set the mouse cursor to your current mouse location
        int h = height(vidmode);
		yPos =  h - MousePosition.getY();
		xPos = MousePosition.getX();
	}
}
