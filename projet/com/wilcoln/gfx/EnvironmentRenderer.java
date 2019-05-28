package com.wilcoln.gfx;

import com.wilcoln.Animal;
import com.wilcoln.Anthill;
import com.wilcoln.Food;
import com.wilcoln.Pheromone;

public interface EnvironmentRenderer {
    void clear();

    void renderAnimal(Animal animal);

    void renderPheromone(Pheromone pheromone);

    void renderAnthill(Anthill anthill);

    void renderFood(Food food);
}
