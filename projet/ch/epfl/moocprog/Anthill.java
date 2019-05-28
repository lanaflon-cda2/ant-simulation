package ch.epfl.moocprog;
import static ch.epfl.moocprog.app.Context.*;
import static ch.epfl.moocprog.config.Config.*;

import ch.epfl.moocprog.utils.Utils;

public final class Anthill extends Positionable {
	
	private double foodQuantity;
	private Uid id;
	private double WorkerOutProb;
	
	public Anthill(ToricPosition tp) {
		super(tp);
		foodQuantity = 0;
		WorkerOutProb = getConfig().getDouble(ANTHILL_WORKER_PROB_DEFAULT);
		id = Uid.createUid();
	}
	public double getFoodQuantity() {
		return foodQuantity;
	}
	public void setFoodQuantity(double foodQuantity) {
		this.foodQuantity = foodQuantity;
	}
	public void dropFood(double toDrop) {
		Utils.require(toDrop >= 0);
		foodQuantity += toDrop;
	}
	public Uid getAnthillId() {
		return id;
	}
	public String toString() {
		return super.toString() + "\nQuantity : " + foodQuantity;
	}
}
