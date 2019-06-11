package ch.epfl.moocprog;

public final class Food extends Positionable{
	private double quantity;
	
	public Food(ToricPosition position, double quantity) {
		this.setPosition(position);
		this.quantity = quantity > 0 ? quantity: 0;
	}
	public double getQuantity() {
		return quantity;
	}
	public double takeQuantity(double quantity) throws IllegalArgumentException{
		if(quantity < 0)
			throw new IllegalArgumentException();
		double taken = this.quantity > quantity ? quantity: this.quantity;
		this.quantity -= taken;
		return taken;
	}
	public String toString() {
		return this.getPosition().toString() + "\n" +  String.format("Quantity : %.2f", this.quantity);
	}

}
