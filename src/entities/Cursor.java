package entities;

public class Cursor extends Entity {

	public Cursor() {
		
	}
	
	public Cursor(float xPos, float yPos) {
		super(xPos, yPos);
	}
	
	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isCollidingWith(Entity other) {
		RegularPolygon rp = (RegularPolygon) other;
		float deltaX = this.getXPos() - other.getXPos();
		float deltaY = this.getYPos() - other.getYPos();
		float distance = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
		if (distance > rp.getRadius()) {
			return false;
		} else {
			return true;
		}
	}

}
