package ch.epfl.moocprog;

import ch.epfl.moocprog.app.Context;
import ch.epfl.moocprog.config.Config;
import ch.epfl.moocprog.random.UniformDistribution;
import ch.epfl.moocprog.utils.Time;
import ch.epfl.moocprog.utils.Utils;
import ch.epfl.moocprog.utils.Vec2d;

public abstract class Animal extends Positionable{
	private double angle;
	private int hitpoints;
	private Time lifespan;
	private Time rotationDelay;
	
	public Animal(ToricPosition tp, int hp, Time ls) {
		this.setPosition(tp);
		this.hitpoints  = hp;
		this.lifespan = ls;
		this.angle =  UniformDistribution.getValue(0, 2*Math.PI);
		this.rotationDelay = Time.ZERO;
	}
	
	public final double getDirection() {
		return this.angle;
	}
	public abstract double getSpeed();
	public abstract void accept(AnimalVisitor visitor, RenderingMedia s);
	protected abstract void specificBehaviorDispatch(AnimalEnvironmentView env, Time dt);
	public String toString() {
		return this.getPosition().toString() + "\nSpeed : " + getSpeed() + "\nHitPoints : " + hitpoints + "\nLifeSpan : " + lifespan.toSeconds();
	}
	public final void setDirection(double angle) {
		this.angle = angle;
	}
	public final boolean isDead() {
		return !(this.hitpoints > 0 && this.lifespan.isPositive());
	}
	public final  int getHitpoints() {
		return this.hitpoints;
	}
	public final Time getLifespan() {
		return this.lifespan;
	}
	public final void update(AnimalEnvironmentView env, Time dt) {
		double lifespanDecreaseFactor = Context.getConfig().getDouble(Config.ANIMAL_LIFESPAN_DECREASE_FACTOR);
		this.lifespan = this.lifespan.minus(dt.times(lifespanDecreaseFactor));
		if(lifespan.isPositive()) {
			this.specificBehaviorDispatch(env, dt);
		}
	}
	protected final void move(Time dt) {
		Time timeDelay = Context.getConfig().getTime(Config.ANIMAL_NEXT_ROTATION_DELAY);
		rotationDelay = rotationDelay.plus(dt);
		while(rotationDelay.compareTo(timeDelay) >= 0) {
			rotationDelay = rotationDelay.minus(timeDelay);
			rotate();
		}
		Vec2d deplacement = Vec2d.fromAngle(angle).scalarProduct(dt.toSeconds()*getSpeed());
		this.setPosition(this.getPosition().add(deplacement));
	}
	private void rotate() {
		RotationProbability rp = computeRotationProbs();
		double angle = Utils.pickValue(rp.getAngles(), rp.getProbabilities());
		this.angle += angle;
	}
	protected RotationProbability computeRotationProbs() {
		double angles[] = { -180, -100, -55, -25, -10, 0, 10, 25, 55, 100, 180};
		for(int i = 0; i < angles.length; i++)
			angles[i] = Math.toRadians(angles[i]);
		double probs[] = {0.0000, 0.0000, 0.0005, 0.0010, 0.0050, 0.9870, 0.0050, 0.0010, 0.0005, 0.0000, 0.0000};
		return new RotationProbability(angles, probs);
	}
}
