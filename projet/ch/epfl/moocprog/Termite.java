package ch.epfl.moocprog;

import ch.epfl.moocprog.app.Context;
import ch.epfl.moocprog.config.Config;
import ch.epfl.moocprog.utils.Time;

public final class Termite extends Animal {

	public Termite(ToricPosition tp) {
		super(tp, Context.getConfig().getInt(Config.TERMITE_HP), Context.getConfig().getTime(Config.TERMITE_LIFESPAN));
	}

	void seekForEnemies(AnimalEnvironmentView env, Time dt){
		move(env, dt);
		fight(env, dt);
	}

	@Override
	public void accept(AnimalVisitor visitor, RenderingMedia s) {
		visitor.visit(this,s);

	}

	@Override
	public void specificBehaviorDispatch(AnimalEnvironmentView env, Time dt) {
		env.selectSpecificBehaviorDispatch(this, dt);
	}

	@Override
	protected RotationProbability computeRotationProbsDispatch(AnimalEnvironmentView env) {
		return env.selectComputeRotationProbsDispatch(this);
	}


	final void afterMoveTermite(AntEnvironmentView env, Time dt){

	}
	protected final RotationProbability computeRotationProbs(AntEnvironmentView env) {
		return this.computeDefaultRotationProbs();
	}
	@Override
	protected void afterMoveDispatch(AnimalEnvironmentView env, Time dt){
		env.selectAfterMoveDispatch(this, dt);
	}

	@Override
	public boolean isEnemy(Animal other) {
		return !this.isDead() && !other.isDead() && other.isEnemyDispatch(this) ;
	}

	@Override
	protected boolean isEnemyDispatch(Termite other) {
		return false;
	}

	@Override
	protected boolean isEnemyDispatch(Ant other) {
		return true;
	}

	@Override
	public int getMinAttackStrength() {
		return Context.getConfig().getInt(Config.TERMITE_MIN_STRENGTH);
	}

	@Override
	public int getMaxAttackStrength() {
		return Context.getConfig().getInt(Config.TERMITE_MAX_STRENGTH);
	}

	@Override
	public Time getMaxAttackDuration() {
		return Context.getConfig().getTime(Config.TERMITE_ATTACK_DURATION);
	}

	@Override
	public double getSpeed() {
		return Context.getConfig().getDouble(Config.TERMITE_SPEED);
	}

}
