package ch.epfl.moocprog;

import ch.epfl.moocprog.app.Context;
import ch.epfl.moocprog.config.Config;
import ch.epfl.moocprog.utils.Time;
import ch.epfl.moocprog.utils.Vec2d;

public abstract class Ant extends Animal {

	private Uid antillId;
	private ToricPosition lastPos;
	
	public Ant(ToricPosition tp, int hp, Time ls, Uid aId) {
		super(tp, hp, ls);
		antillId = aId;
		lastPos = getPosition();
		// TODO Auto-generated constructor stub
	}
	public final Uid getAnthillId(){
		return this.antillId;
	}

	private void spreadPheromones(AntEnvironmentView env){
		double density = Context.getConfig().getDouble(Config.ANT_PHEROMONE_DENSITY);
		double distance = lastPos.toricDistance(getPosition());
		int nbInstances = (int)(distance *  density);
		double pheromoneQuantity = Context.getConfig().getDouble(Config.ANT_PHEROMONE_ENERGY);
		Vec2d vector = lastPos.toricVector(getPosition());
		Vec2d pas = new Vec2d(vector.getX()/density, vector.getY()/density);
		ToricPosition position = lastPos;
		while(position.toricDistance(getPosition()) > 0){
			env.addPheromone(new Pheromone(position, pheromoneQuantity));
			position = position.add(pas);
		}
		lastPos = getPosition();
	}

}
