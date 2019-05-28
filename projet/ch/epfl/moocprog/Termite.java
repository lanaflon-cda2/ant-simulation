package ch.epfl.moocprog;

import static ch.epfl.moocprog.app.Context.*;
import static ch.epfl.moocprog.config.Config.*;

public final class Termite extends Animal {

	public Termite(ToricPosition tp) {
		super(tp, getConfig().getInt(TERMITE_HP), getConfig().getTime(TERMITE_LIFESPAN));
	}

	@Override
	public void accept(AnimalVisitor visitor, RenderingMedia s) {
		// TODO Auto-generated method stub

	}

	@Override
	public double getSpeed() {
		return getConfig().getDouble(TERMITE_SPEED);
	}

}
