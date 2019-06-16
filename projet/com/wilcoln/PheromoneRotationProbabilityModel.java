package com.wilcoln;

import com.wilcoln.app.Context;
import com.wilcoln.config.Config;

public class PheromoneRotationProbabilityModel implements  AntRotationProbabilityModel {
    @Override
    public RotationProbability computeRotationProbs(RotationProbability movementMatrix, ToricPosition position, double directionAngle, AntEnvironmentView env) {
        double[] angles = movementMatrix.getAngles();
        double[] probs = movementMatrix.getProbabilities();
        double[] pheromoneQuantities = env.getPheromoneQuantitiesPerIntervalForAnt(position,directionAngle, movementMatrix.getAngles());
        double sum = 0;
        for(int i = 0; i < probs.length; i++){
            probs[i] = probs[i]*Math.pow(detection(pheromoneQuantities[i]),Context.getConfig().getInt(Config.ALPHA));
            sum += probs[i];
        }
        for(int i = 0; i < probs.length; i++){
            probs[i] /= sum;
        }

        return new RotationProbability(angles, probs);
    }
    double detection(double x){
        return 1.0 / ( 1.0 + Math.exp( - Context.getConfig().getDouble(Config.BETA_D) * (x -  Context.getConfig().getDouble(Config.Q_ZERO)) ));
    }
}
