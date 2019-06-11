package com.wilcoln;

import com.wilcoln.app.Context;
import com.wilcoln.config.Config;
import com.wilcoln.utils.Time;

public final class Termite extends Animal {

	public Termite(ToricPosition tp) {
		super(tp, Context.getConfig().getInt(Config.TERMITE_HP), Context.getConfig().getTime(Config.TERMITE_LIFESPAN));
	}

	@Override
	public void accept(AnimalVisitor visitor, RenderingMedia s) {
		visitor.visit(this,s);

	}

	@Override
	public void specificBehaviorDispatch(AnimalEnvironmentView env, Time dt) {
		move(dt);
	}

	@Override
	public double getSpeed() {
		return Context.getConfig().getDouble(Config.TERMITE_SPEED);
	}

}
