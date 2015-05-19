package entities;

import engineTester.TimeManager;

public abstract class Entity implements Drawable {
	private float xPos, yPos;
	private float xVelocity, yVelocity;
	
	public Entity() {
		
	}
	
	public Entity(float xPos, float yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	public void update() {
		xPos += xVelocity * TimeManager.getDelta();
		yPos += yVelocity * TimeManager.getDelta();
	}
	
	public float getXPos() {
		return xPos;
	}
	
	public float getYPos() {
		return yPos;
	}

	public void setxPos(float xPos) {
		this.xPos = xPos;
	}

	public void setyPos(float yPos) {
		this.yPos = yPos;
	}
	
	public float getxVelocity() {
		return xVelocity;
	}

	public void setxVelocity(float xVelocity) {
		this.xVelocity = xVelocity;
	}

	public float getyVelocity() {
		return yVelocity;
	}

	public void setyVelocity(float yVelocity) {
		this.yVelocity = yVelocity;
	}
	
	public void reflectxVelocity() {
		xVelocity = -xVelocity;
	}
	
	public void reflectyVelocity() {
		yVelocity = -yVelocity;
	}
}
