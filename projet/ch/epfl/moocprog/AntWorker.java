package ch.epfl.moocprog;

import static ch.epfl.moocprog.app.Context.getConfig;
import static ch.epfl.moocprog.config.Config.*;

public class AntWorker extends Ant {

	private double foodQuantity = 0;

	public AntWorker(ToricPosition tp, Uid aId) {
		super(tp, getConfig().getInt(ANT_WORKER_HP), getConfig().getTime(ANT_WORKER_LIFESPAN), aId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(AnimalVisitor visitor, RenderingMedia s) {
		visitor.visit(this, s);

	}

	@Override
	public double getSpeed() {
		return getConfig().getDouble(ANT_WORKER_SPEED);
	}
	public double getFoodQuantity() {
		return this.foodQuantity;
	}
	public String toString() {
		return super.toString() + "\nQuantity : " + foodQuantity;
	}

}
