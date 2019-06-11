package com.wilcoln;
import static com.wilcoln.app.Context.*;
import static com.wilcoln.config.Config.*;

import com.wilcoln.random.NormalDistribution;
import com.wilcoln.random.UniformDistribution;
import com.wilcoln.utils.Time;
import com.wilcoln.utils.Utils;

public final class Anthill extends Positionable implements AnthillEnvironmentView {
	
	private double foodQuantity;
	private Uid id;
	private double WorkerOutProb;
	private Time time;
	
	public Anthill(ToricPosition tp) {
		super(tp);
		foodQuantity = 0;
		WorkerOutProb = getConfig().getDouble(ANTHILL_WORKER_PROB_DEFAULT);
		id = Uid.createUid();
		time =  Time.ZERO;
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

	@Override
	public void addAnt(Ant ant) throws IllegalArgumentException {

	}
	void update(AnthillEnvironmentView env, Time dt) {
		time = time.plus(dt);
		Time timeDelay = getConfig().getTime(ANTHILL_SPAWN_DELAY);
		Ant ant;
		while(time.compareTo(timeDelay) >= 0) {
			time = time.minus(timeDelay);
			double value = UniformDistribution.getValue(0, 1);
			if(value <= getConfig().getDouble(ANTHILL_WORKER_PROB_DEFAULT))
				ant = new AntWorker(getPosition(), Uid.createUid());
			else
				ant = new AntSoldier(getPosition(), Uid.createUid());
			env.addAnt(ant);
		}
	}
}
