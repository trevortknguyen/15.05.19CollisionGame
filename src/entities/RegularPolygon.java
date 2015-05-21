package entities;

public abstract class RegularPolygon extends Entity {
	private float radius;
	private float apothem;
	
	public RegularPolygon(float xPos, float yPos, float radius, float apothem) {
		super(xPos, yPos);
		this.radius = radius;
	}
	
	public boolean isCollidingWith(Entity other) {
		RegularPolygon rp = (RegularPolygon) other;
		float deltaX = this.getXPos() - other.getXPos();
		float deltaY = this.getYPos() - other.getYPos();
		float distance = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
		if (distance > this.getRadius() + rp.getRadius()) {
			return false;
		} else {
			// TODO: multiple calculations to check if overlaps
			return true;
		}
		
	}
	
	public float getRadius() {
		return radius;
	}
}
