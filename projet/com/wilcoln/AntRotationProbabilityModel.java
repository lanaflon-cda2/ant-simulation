package com.wilcoln;

public interface AntRotationProbabilityModel{
    RotationProbability computeRotationProbs(RotationProbability movementMatrix, ToricPosition position, double directionAngle, AntEnvironmentView env);

}
