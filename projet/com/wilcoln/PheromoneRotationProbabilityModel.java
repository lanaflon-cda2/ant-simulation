package com.wilcoln;

import com.wilcoln.app.Context;
import com.wilcoln.config.Config;

public class PheromoneRotationProbabilityModel implements  AntRotationProbabilityModel {
    @Override
    public RotationProbability computeRotationProbs(RotationProbability movementMatrix, ToricPosition position, double directionAngle, AntEnvironmentView env) {

        double[] pheromoneQuantities = env.getPheromoneQuantitiesPerIntervalForAnt(position,directionAngle, movementMatrix.getAngles());
        double sum = 0;
        for(int i = 0; i< movementMatrix.getProbabilities().length; i++){
            movementMatrix.getProbabilities()[i] = movementMatrix.getProbabilities()[i]*Math.pow(detection(pheromoneQuantities[i]),Context.getConfig().getInt(Config.ALPHA));
            sum += movementMatrix.getProbabilities()[i];
        }
        for(int i = 0; i < movementMatrix.getProbabilities().length; i++){
            movementMatrix.getProbabilities()[i] /= sum;
        }

        return movementMatrix;
    }
    double detection(double x){
        return 1.0 / ( 1.0 + Math.exp( - Context.getConfig().getDouble(Config.BETA_D) * (x -  Context.getConfig().getDouble(Config.Q_ZERO)) ));
    }
}
