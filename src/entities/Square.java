package entities;

import static org.lwjgl.opengl.GL11.*;
import renderEngine.Color;

public class Square extends RegularPolygon {
	Color color;
	
	float apothem;
	
	public Square(float xPos, float yPos, float radius) {
		super(xPos, yPos, radius,
				radius * (float) Math.sqrt(2) / 2);
		apothem = super.getRadius() * (float) Math.sqrt(2) / 2;
		color = new Color((float) Math.random(), (float) Math.random(), (float) Math.random(), 1.0f);
	}
	
	public void render() {
        glColor4f(color.r, color.g, color.b, 1.0f);
		glBegin(GL_POLYGON);
			glVertex2f(super.getXPos() - apothem, super.getYPos() - apothem);
			glVertex2f(super.getXPos() + apothem, super.getYPos() - apothem);
			glVertex2f(super.getXPos() + apothem, super.getYPos() + apothem);
			glVertex2f(super.getXPos() - apothem, super.getYPos() + apothem);
		glEnd();
	}
}
