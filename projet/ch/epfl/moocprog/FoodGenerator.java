package ch.epfl.moocprog;

import static ch.epfl.moocprog.app.Context.*;
import static ch.epfl.moocprog.config.Config.*;
import  ch.epfl.moocprog.random.*;
import ch.epfl.moocprog.utils.Time;

public final class FoodGenerator {
	private Time time;
	
	public FoodGenerator() {
		time = Time.ZERO;
	}
	
	public void update(FoodGeneratorEnvironmentView env, Time dt) {
		time = time.plus(dt);
		double width = getConfig().getInt(WORLD_WIDTH);
		double height = getConfig().getInt(WORLD_HEIGHT); 
		double qty_min = getConfig().getDouble(NEW_FOOD_QUANTITY_MIN);
		double qty_max = getConfig().getDouble(NEW_FOOD_QUANTITY_MAX);
		Time timeDelay = getConfig().getTime(FOOD_GENERATOR_DELAY);
		while(time.compareTo(timeDelay) >= 0) {
			time = time.minus(timeDelay);
			double foodQuantity = UniformDistribution.getValue(qty_min, qty_max);
			double x = NormalDistribution.getValue( width / 2.0, width * width / 16.0);
			double y = NormalDistribution.getValue( height / 2.0, height * height / 16.0);
			ToricPosition foodPosition = new ToricPosition(x, y);
			env.addFood(new Food(foodPosition, foodQuantity));
		}
	}

}
