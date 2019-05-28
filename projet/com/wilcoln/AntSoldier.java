package com.wilcoln;

import static com.wilcoln.app.Context.*;
import static com.wilcoln.config.Config.*;

public final class AntSoldier extends Ant {


	public AntSoldier(ToricPosition tp, Uid aId) {
		super(tp, getConfig().getInt(ANT_SOLDIER_HP), getConfig().getTime(ANT_SOLDIER_LIFESPAN), aId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(AnimalVisitor visitor, RenderingMedia s) {
		visitor.visit(this, s);

	}

	@Override
	public double getSpeed() {
		return getConfig().getDouble(ANT_SOLDIER_SPEED);
	}

}
