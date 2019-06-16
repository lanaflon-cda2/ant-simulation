package com.wilcoln;

import com.wilcoln.app.Context;
import com.wilcoln.config.Config;
import com.wilcoln.utils.Time;

public final class Pheromone extends Positionable {
	private double quantity;

	public Pheromone(ToricPosition tp) {
		super(tp);
		// TODO Auto-generated constructor stub
	}
	public Pheromone(ToricPosition tp, double quantity){
		super(tp);
		this.quantity  = quantity;

	}
	public boolean isNegligible(){
		return quantity < Context.getConfig().getDouble(Config.PHEROMONE_THRESHOLD);
	}
	public void update(Time dt){
		if(!isNegligible())
			quantity -= dt.toSeconds()*Context.getConfig().getDouble(Config.PHEROMONE_EVAPORATION_RATE);
	}

	public double getQuantity() {
		return quantity;
	}
}
