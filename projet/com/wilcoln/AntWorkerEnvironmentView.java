package com.wilcoln;

public interface AntWorkerEnvironmentView extends AntEnvironmentView{
	Food getClosestFoodForAnt(AntWorker antWorker);
	boolean dropFood(AntWorker antWorker);
}
