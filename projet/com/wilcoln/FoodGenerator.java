package com.wilcoln;

import static com.wilcoln.app.Context.*;
import static com.wilcoln.config.Config.*;
import com.wilcoln.random.NormalDistribution;
import com.wilcoln.random.UniformDistribution;
import com.wilcoln.utils.Time;

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
