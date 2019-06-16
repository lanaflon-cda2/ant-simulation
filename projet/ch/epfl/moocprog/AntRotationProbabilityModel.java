package ch.epfl.moocprog;

import ch.epfl.moocprog.AntEnvironmentView;
import ch.epfl.moocprog.RotationProbability;
import ch.epfl.moocprog.ToricPosition;

public interface AntRotationProbabilityModel{
    RotationProbability computeRotationProbs(RotationProbability movementMatrix, ToricPosition position, double directionAngle, AntEnvironmentView env);

}
