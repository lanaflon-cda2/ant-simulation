package ch.epfl.moocprog;

import ch.epfl.moocprog.app.Context;
import ch.epfl.moocprog.config.Config;
import ch.epfl.moocprog.utils.Time;
import ch.epfl.moocprog.utils.Utils;

public final class AntWorker extends Ant{

	private double foodQuantity = 0;

	public AntWorker(ToricPosition tp, Uid aId) {
		super(tp, Context.getConfig().getInt(Config.ANT_WORKER_HP), Context.getConfig().getTime(Config.ANT_WORKER_LIFESPAN), aId);
	}
	public AntWorker(ToricPosition tp, Uid aId, AntRotationProbabilityModel aPm) {
		super(tp, Context.getConfig().getInt(Config.ANT_WORKER_HP), Context.getConfig().getTime(Config.ANT_WORKER_LIFESPAN), aId, aPm);
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
		if(foodQuantity == 0){
			Food food = env.getClosestFoodForAnt(this);
			if(food != null && getPosition().toricDistance(food.getPosition()) <= Context.getConfig().getDouble(Config.ANT_MAX_PERCEPTION_DISTANCE)){
				foodQuantity += food.takeQuantity(Context.getConfig().getDouble(Config.ANT_MAX_FOOD));
				// Faire demi tour
				double nouvelAngle = getDirection() + Math.PI;
				nouvelAngle = ((2 * Math.PI) < nouvelAngle) ? (nouvelAngle - (2 * Math.PI)) : nouvelAngle;
				setDirection(nouvelAngle);
			}
		}
		foodQuantity = env.dropFood(this) ? 0 : foodQuantity;
		move(env, dt);
	}
	public String toString() {
		return super.toString() + "\nQuantity : " + foodQuantity;
	}

	@Override
	protected void specificBehaviorDispatch(AnimalEnvironmentView env, Time dt) {
		// A ce moment là, on sait que l'on à affaire à un AntWorker.
		// Grâce à l'appel suivant, on informe AnimalEnvironmentView de notre type !
		env.selectSpecificBehaviorDispatch(this, dt);
	}
	@Override
	public int getMinAttackStrength() {
		return Context.getConfig().getInt(Config.ANT_WORKER_MIN_STRENGTH);
	}

	@Override
	public int getMaxAttackStrength() {
		return Context.getConfig().getInt(Config.ANT_WORKER_MAX_STRENGTH);
	}

	@Override
	public Time getMaxAttackDuration() {
		return Context.getConfig().getTime(Config.ANT_WORKER_ATTACK_DURATION);
	}
}
