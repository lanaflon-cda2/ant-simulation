package com.wilcoln;

import com.wilcoln.app.Context;
import com.wilcoln.config.Config;
import com.wilcoln.utils.Time;

import static com.wilcoln.app.Context.*;
import static com.wilcoln.config.Config.*;

public final class AntSoldier extends Ant{


	public AntSoldier(ToricPosition tp, Uid aId) {
		super(tp, getConfig().getInt(ANT_SOLDIER_HP), getConfig().getTime(ANT_SOLDIER_LIFESPAN), aId);
	}
	public AntSoldier(ToricPosition tp, Uid aId, AntRotationProbabilityModel aPm) {
		super(tp, Context.getConfig().getInt(Config.ANT_SOLDIER_HP), Context.getConfig().getTime(Config.ANT_SOLDIER_LIFESPAN), aId, aPm);
	}
	@Override
	public void accept(AnimalVisitor visitor, RenderingMedia s) {
		visitor.visit(this, s);
	}

	@Override
	public double getSpeed() {
		return getConfig().getDouble(ANT_SOLDIER_SPEED);
	}

	void seekForEnemies(AntEnvironmentView env, Time dt){
		move(env, dt);
		fight(env, dt);
	}

	@Override
	protected void specificBehaviorDispatch(AnimalEnvironmentView env, Time dt) {
		// A ce moment là, on sait que l'on à affaire à un AntWorker.
		// Grâce à l'appel suivant, on informe AnimalEnvironmentView de notre type !
		env.selectSpecificBehaviorDispatch(this, dt);
	}
	@Override
	public int getMinAttackStrength() {
		return Context.getConfig().getInt(Config.ANT_SOLDIER_MIN_STRENGTH);
	}

	@Override
	public int getMaxAttackStrength() {
		return Context.getConfig().getInt(Config.ANT_SOLDIER_MAX_STRENGTH);
	}

	@Override
	public Time getMaxAttackDuration() {
		return Context.getConfig().getTime(Config.ANT_SOLDIER_ATTACK_DURATION);
	}
}
