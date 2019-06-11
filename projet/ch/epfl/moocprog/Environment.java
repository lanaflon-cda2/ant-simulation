package ch.epfl.moocprog;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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
	
	public Environment() {
		foods = new LinkedList<Food>();
		animals = new LinkedList<Animal>();
		anthills = new LinkedList<Anthill>();
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
		List<ToricPosition> listPositions = new LinkedList<ToricPosition>();
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


}