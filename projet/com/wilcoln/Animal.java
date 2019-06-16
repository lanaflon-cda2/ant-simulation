package com.wilcoln;

import com.wilcoln.app.Context;
import com.wilcoln.config.Config;
import com.wilcoln.random.UniformDistribution;
import com.wilcoln.utils.Time;
import com.wilcoln.utils.Utils;
import com.wilcoln.utils.Vec2d;

import java.util.List;

public abstract class Animal extends Positionable{
	public enum State {ATTACK, IDLE, ESCAPING}

	private State state;
	private double angle;
	private int hitpoints;
	private Time lifespan;
	private Time rotationDelay;
	private Time attackDuration;
	
	public Animal(ToricPosition tp, int hp, Time ls) {
		this.setPosition(tp);
		this.hitpoints  = hp;
		this.lifespan = ls;
		this.angle =  UniformDistribution.getValue(0, 2*Math.PI);
		this.rotationDelay = Time.ZERO;
		this.attackDuration = Time.ZERO;
		this.state = State.IDLE;
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
			switch (state){
				case ATTACK:
					if(canAttack())
						fight(env, dt);
					else
						setState(State.ESCAPING);
						attackDuration = Time.ZERO;
					break;
				case ESCAPING:
					escape(env, dt);
					break;
				case IDLE:
					this.specificBehaviorDispatch(env, dt);
					break;

			}
		}

	}
	public boolean canAttack(){
		return state != State.ESCAPING && attackDuration.compareTo(getMaxAttackDuration()) <= 0;
	}
	public void escape(AnimalEnvironmentView env, Time dt){
		move(env, dt);
		if(!env.isVisibleFromEnemies(this))
			setState(State.IDLE);
	}
	public void fight(AnimalEnvironmentView env, Time dt){
		List<Animal> visibleEnemies = env.getVisibleEnemiesForAnimal(this);
		if(!visibleEnemies.isEmpty()){
			Animal closestVisibleEnemy = Utils.closestFromPoint(this, visibleEnemies);
			if(state != State.ATTACK)
				this.setState(State.ATTACK);
			if(closestVisibleEnemy.state != State.ATTACK)
				this.setState(State.ATTACK);

			closestVisibleEnemy.hitpoints-= UniformDistribution.getValue(getMaxAttackStrength(), getMaxAttackStrength());
			attackDuration.plus(dt);


		}else{
			attackDuration = Time.ZERO;
			if(state == State.ATTACK)
				this.setState(State.ESCAPING);
		}


	}
	protected final void move(AnimalEnvironmentView env, Time dt) {
		Time timeDelay = Context.getConfig().getTime(Config.ANIMAL_NEXT_ROTATION_DELAY);
		rotationDelay = rotationDelay.plus(dt);
		while(rotationDelay.compareTo(timeDelay) >= 0) {
			rotationDelay = rotationDelay.minus(timeDelay);
			rotate(env);
		}
		Vec2d deplacement = Vec2d.fromAngle(angle).scalarProduct(dt.toSeconds()*getSpeed());
		this.setPosition(this.getPosition().add(deplacement));
		afterMoveDispatch(env, dt);
	}
	private void rotate(AnimalEnvironmentView env) {
		RotationProbability rp = computeRotationProbsDispatch(env);
		double angle = Utils.pickValue(rp.getAngles(), rp.getProbabilities());
		this.angle += angle;
	}
	protected final RotationProbability computeDefaultRotationProbs() {
		double angles[] = { -180, -100, -55, -25, -10, 0, 10, 25, 55, 100, 180};
		for(int i = 0; i < angles.length; i++)
			angles[i] = Math.toRadians(angles[i]);
		double probs[] = {0.0000, 0.0000, 0.0005, 0.0010, 0.0050, 0.9870, 0.0050, 0.0010, 0.0005, 0.0000, 0.0000};
		return new RotationProbability(angles, probs);
	}

	public void setState(State state){
		this.state = state;
	}
	public State getState(){
		return this.state;
	}

	protected abstract RotationProbability computeRotationProbsDispatch(AnimalEnvironmentView env);
	protected abstract void afterMoveDispatch(AnimalEnvironmentView env, Time dt);
	public abstract boolean isEnemy(Animal other);
	protected abstract boolean isEnemyDispatch(Termite other);
	protected abstract boolean isEnemyDispatch(Ant other) ;
	public abstract int getMinAttackStrength();
	public abstract int getMaxAttackStrength();
	public abstract Time getMaxAttackDuration();
}
