package com.wilcoln;

import com.wilcoln.utils.Time;

public abstract class Ant extends Animal {

	private Uid antillId;
	
	public Ant(ToricPosition tp, int hp, Time ls, Uid aId) {
		super(tp, hp, ls);
		antillId = aId;
		// TODO Auto-generated constructor stub
	}
	public final Uid getAnthillId(){
		return this.antillId;
	}
	

}
