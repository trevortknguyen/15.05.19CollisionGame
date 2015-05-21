package entities;

import static org.lwjgl.glfw.GLFW.*;
import input.Input;

public class Player extends Square {

	float speedFactor = 75.0f;
	
	public Player(float x, float y) {
		super(x, y, 5);
	}
	
	public void update() {
		int i = 0, j = 0;
    	if (Input.keys[GLFW_KEY_W]) j += 1;
    	if (Input.keys[GLFW_KEY_S]) j -= 1;
    	if (Input.keys[GLFW_KEY_D]) i += 1;
    	if (Input.keys[GLFW_KEY_A]) i -= 1;
    	
    	if (j != 0)
    		super.setxVelocity((float) (i * Math.sqrt(2) / 2) * speedFactor);
    	else
    		super.setxVelocity(i * speedFactor);
    	
    	if (i != 0)
    		super.setyVelocity((float) (j * Math.sqrt(2) / 2) * speedFactor);
    	else
    		super.setyVelocity(j * speedFactor);
    	
		super.update();
	}
}
