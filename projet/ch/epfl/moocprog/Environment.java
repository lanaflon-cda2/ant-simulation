package ch.epfl.moocprog;

import java.util.*;

import ch.epfl.moocprog.app.Context;
import ch.epfl.moocprog.config.Config;
import ch.epfl.moocprog.gfx.EnvironmentRenderer;
import ch.epfl.moocprog.utils.Time;
import ch.epfl.moocprog.utils.Utils;

public final class Environment implements FoodGeneratorEnvironmentView, AnimalEnvironmentView, AnthillEnvironmentView, AntEnvironmentView, AntWorkerEnvironmentView {

	private FoodGenerator foodGenerator;
	private List<Food> foods;
	private List<Animal> animals;
	private List<Anthill> anthills;
	private List<Pheromone> pheromones;
	
	public Environment() {
		foods = new LinkedList<>();
		animals = new LinkedList<>();
		anthills = new LinkedList<>();
		pheromones = new LinkedList<>();
		foodGenerator = new FoodGenerator();
	}
	@Override
	public void addFood(Food food) throws IllegalArgumentException{
		Utils.requireNonNull(food);
		foods.add(food);
		
	}
	public void addAnimal(Animal animal) throws IllegalArgumentException {
		Utils.requireNonNull(animal);
		animals.add(animal);
	}
	public void addAnthill(Anthill anthill) {
		Utils.requireNonNull(anthill);
		anthills.add(anthill);
	}
	
	public List<Double> getFoodQuantities(){
		List<Double> foodQuantities = new LinkedList<Double>();
		for (Food food: foods) {
			foodQuantities.add(food.getQuantity());
		}
		return foodQuantities;
	}
	
	public void update(Time dt) {
		foodGenerator.update(this, dt);
		for(Pheromone pheromone: pheromones)
			pheromone.update(dt);
		pheromones.removeIf(Pheromone::isNegligible);
		//Parcours mise à jour et suppression d'animaux
		Iterator<Animal> AIterator = animals.iterator();
		while(AIterator.hasNext()) {
			Animal animal = AIterator.next();
			if(animal.isDead())
				AIterator.remove();
			else
				animal.update(this, dt);
		}
		//Parcours mise à jour fourmillières
		for (Anthill anthill : anthills) {
			anthill.update(this, dt);
		}
		//Supprime les nourritures épuisées
		foods.removeIf(food -> food.getQuantity() <= 0);
	}
	
	public void renderEntities(EnvironmentRenderer environmentRenderer) {
		foods.forEach(environmentRenderer::renderFood);
		animals.forEach(environmentRenderer::renderAnimal);
		anthills.forEach(environmentRenderer::renderAnthill);
	}
	public int getWidth() {
		return Context.getConfig().getInt(Config.WORLD_WIDTH);
	}
	public int getHeight() {
		return Context.getConfig().getInt(Config.WORLD_HEIGHT);
	}
	public List<ToricPosition> getAnimalsPosition(){
		List<ToricPosition> listPositions = new LinkedList<>();
		for(Animal a: animals) {
			listPositions.add(a.getPosition());
		}
		return listPositions;
	}
	@Override
	public void addAnt(Ant ant) throws IllegalArgumentException {
		addAnimal(ant);
	}
	@Override
	public Food getClosestFoodForAnt(AntWorker antWorker) {
		Utils.requireNonNull(antWorker);
		Food closestFood = Utils.closestFromPoint(antWorker, foods);
		if(closestFood !=null && antWorker.getPosition().toricDistance(closestFood.getPosition()) <= Context.getConfig().getDouble(Config.ANT_MAX_PERCEPTION_DISTANCE))
			return closestFood;
		else
			return null;
	}
	@Override
	public boolean dropFood(AntWorker antWorker) {
		Utils.requireNonNull(antWorker);
		Anthill anthill = null;
		for (Anthill value : anthills) {
			anthill = value;
			if (anthill.getAnthillId().equals(antWorker.getAnthillId()))
				break;
		}
		return anthill == null ? false : antWorker.getPosition().toricDistance(anthill.getPosition()) <= Context.getConfig().getDouble(Config.ANT_MAX_PERCEPTION_DISTANCE);
	}

	@Override
	public void selectSpecificBehaviorDispatch(AntWorker antWorker, Time dt) {
		antWorker.seekForFood(this, dt);
	}

	@Override
	public void selectSpecificBehaviorDispatch(AntSoldier antSoldier, Time dt) {
		antSoldier.seekForEnemies(this, dt);
	}


	@Override
	public void addPheromone(Pheromone pheromone) throws IllegalArgumentException{
		Utils.requireNonNull(pheromone);
		pheromones.add(pheromone);
	}

	private static double normalizedAngle(double angle){
		while(angle < 0)
			angle += 2*Math.PI;
		while(angle > 2*Math.PI)
			angle -= 2*Math.PI;
		return angle;
	}
	private static double closestAngleFrom(double angle, double target){
		double diff = angle - target;
		diff = normalizedAngle(diff);
		return Math.min(diff, 2*Math.PI - diff);
	}
	@Override
	public double[] getPheromoneQuantitiesPerIntervalForAnt(ToricPosition position, double directionAngleRad, double[] angles) {
		double[] pheromoneQuantities = new double[angles.length];
		for(Pheromone pheromone: pheromones){
			if(!pheromone.isNegligible())
				if(position.toricDistance(pheromone.getPosition()) <= Context.getConfig().getDouble(Config.ANT_SMELL_MAX_DISTANCE)){
					double beta = position.toricVector(pheromone.getPosition()).angle() - directionAngleRad;
					int min_index = 0;
					for(int i = 0; i < angles.length; i++){
						if(closestAngleFrom(angles[min_index], beta) > closestAngleFrom(angles[i], beta))
							min_index = i;
					}
					pheromoneQuantities[min_index] += pheromone.getQuantity();
				}
		}
		return pheromoneQuantities;
	}

	public List<Double> getPheromonesQuantities(){
			List<Double> pheromonesQuantities = new LinkedList<>();
			for(Pheromone pheromone: pheromones){
				pheromonesQuantities.add(pheromone.getQuantity());
			}
			return pheromonesQuantities;
	}
}
