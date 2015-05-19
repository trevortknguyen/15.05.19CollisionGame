package entities;

public abstract class RegularPolygon extends Entity {
	private float radius;
	
	public RegularPolygon(float xPos, float yPos, float radius) {
		super(xPos, yPos);
		this.radius = radius;
	}
	
	public float getRadius() {
		return radius;
	}
}
