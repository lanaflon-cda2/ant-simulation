package com.wilcoln;

import com.wilcoln.app.Context;
import com.wilcoln.config.Config;
import com.wilcoln.utils.Time;

public final class AntWorker extends Ant{

	private double foodQuantity = 0;

	public AntWorker(ToricPosition tp, Uid aId) {
		super(tp, Context.getConfig().getInt(Config.ANT_WORKER_HP), Context.getConfig().getTime(Config.ANT_WORKER_LIFESPAN), aId);
	}

	@Override
	public void accept(AnimalVisitor visitor, RenderingMedia s) {
		visitor.visit(this, s);
	}

	@Override
	public double getSpeed() {
		return Context.getConfig().getDouble(Config.ANT_WORKER_SPEED);
	}
	public double getFoodQuantity() {
		return this.foodQuantity;
	}
	void seekForFood(AntWorkerEnvironmentView env, Time dt){
		move(dt);
	}
	public String toString() {
		return super.toString() + "\nQuantity : " + foodQuantity;
	}

	@Override
	public void specificBehaviorDispatch(AnimalEnvironmentView env, Time dt) {
		// A ce moment là, on sait que l'on à affaire à un AntWorker.
		// Grâce à l'appel suivant, on informe AnimalEnvironmentView de notre type !
		env.selectSpecificBehaviorDispatch(this, dt);
	}
}
