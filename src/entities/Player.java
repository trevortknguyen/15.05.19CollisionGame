package entities;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFWvidmode.height;
import static org.lwjgl.glfw.GLFWvidmode.width;

import java.nio.ByteBuffer;

import input.Input;
import input.MousePosition;
import engineTester.Main;

public class Player extends Square {

	float speedFactor = 75.0f;
	
	public Player(float x, float y) {
		super(x, y, 10);
	}
	
	public void update() {
		ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        int h = height(vidmode);
		yPos =  h - MousePosition.getY();
		xPos = MousePosition.getX();
	}
}
