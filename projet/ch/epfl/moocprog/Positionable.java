package ch.epfl.moocprog;

public class Positionable {
	private ToricPosition position;
	
	public Positionable() {
		position = new ToricPosition();
	}
	
	public Positionable(ToricPosition tp) {
		position = new ToricPosition(tp.toVec2d());
	}
	public ToricPosition getPosition() {
		return position;
	}
	protected final void setPosition(ToricPosition tp) {
		position = new ToricPosition(tp.toVec2d());
	}
	public String toString() {
		return position.toString();
	}

}
