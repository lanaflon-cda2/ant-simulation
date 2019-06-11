package ch.epfl.moocprog;

import ch.epfl.moocprog.utils.Time;

import static ch.epfl.moocprog.app.Context.*;
import static ch.epfl.moocprog.config.Config.*;

public final class AntSoldier extends Ant{


	public AntSoldier(ToricPosition tp, Uid aId) {
		super(tp, getConfig().getInt(ANT_SOLDIER_HP), getConfig().getTime(ANT_SOLDIER_LIFESPAN), aId);
	}

	@Override
	public void accept(AnimalVisitor visitor, RenderingMedia s) {
		visitor.visit(this, s);
	}

	@Override
	public double getSpeed() {
		return getConfig().getDouble(ANT_SOLDIER_SPEED);
	}

	void seekForEnemies(AntEnvironmentView env, Time dt){
		move(dt);
	}

	@Override
	protected void specificBehaviorDispatch(AnimalEnvironmentView env, Time dt) {
		// A ce moment là, on sait que l'on à affaire à un AntWorker.
		// Grâce à l'appel suivant, on informe AnimalEnvironmentView de notre type !
		env.selectSpecificBehaviorDispatch(this, dt);
	}
}
