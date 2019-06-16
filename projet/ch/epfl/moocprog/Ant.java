package ch.epfl.moocprog;

import ch.epfl.moocprog.app.Context;
import ch.epfl.moocprog.config.Config;
import ch.epfl.moocprog.utils.Time;
import ch.epfl.moocprog.utils.Vec2d;

public abstract class Ant extends Animal {

	private Uid antillId;
	private ToricPosition lastPos;
	private AntRotationProbabilityModel probModel;
	
	public Ant(ToricPosition tp, int hp, Time ls, Uid aId) {
		super(tp, hp, ls);
		antillId = aId;
		lastPos = getPosition();
		probModel = new PheromoneRotationProbabilityModel();
		// TODO Auto-generated constructor stub
	}
	public Ant(ToricPosition tp, int hp, Time ls, Uid aId, AntRotationProbabilityModel aPm) {
		super(tp, hp, ls);
		antillId = aId;
		lastPos = getPosition();
		probModel = aPm;
		// TODO Auto-generated constructor stub
	}

	public void setLastPos(ToricPosition lastPos) {
		this.lastPos = lastPos;
	}

	public final Uid getAnthillId(){
		return this.antillId;
	}

	private void spreadPheromones(AntEnvironmentView env){
		double density = Context.getConfig().getDouble(Config.ANT_PHEROMONE_DENSITY);
		double minBound = Math.min(Context.getConfig().getInt(Config.WORLD_HEIGHT), Context.getConfig().getInt(Config.WORLD_WIDTH));
		double distance = lastPos.toricDistance(getPosition());
		if(distance > minBound/5){
			lastPos = getPosition();
			distance = 0;
		}
		int nbInstances = (int)(distance *  density);
		double pheromoneQuantity = Context.getConfig().getDouble(Config.ANT_PHEROMONE_ENERGY);
		Vec2d vector = lastPos.toricVector(getPosition());
		Vec2d pas = new Vec2d(vector.getX()/nbInstances, vector.getY()/nbInstances);
		while(nbInstances > 0){
			lastPos = lastPos.add(pas);
			env.addPheromone(new Pheromone(lastPos, pheromoneQuantity));
			nbInstances--;
		}
	}

	@Override
	protected final RotationProbability computeRotationProbsDispatch(AnimalEnvironmentView env){
		return env.selectComputeRotationProbsDispatch(this);
	}
	final void afterMoveAnt(AntEnvironmentView env, Time dt){
		spreadPheromones(env);
	}
	protected final RotationProbability computeRotationProbs(AntEnvironmentView env) {
		return probModel.computeRotationProbs(computeDefaultRotationProbs(), getPosition(), getDirection(), env);
	}
	@Override
	public boolean isEnemy(Animal other) {
		return !this.isDead() && !other.isDead() && other.isEnemyDispatch(this) ;
	}

	@Override
	protected boolean isEnemyDispatch(Termite other) {
		return true;
	}

	@Override
	protected boolean isEnemyDispatch(Ant other) {
		return !other.antillId.equals(this.antillId);
	}

	protected void afterMoveDispatch(AnimalEnvironmentView env, Time dt){
		env.selectAfterMoveDispatch(this, dt);
	}
}
