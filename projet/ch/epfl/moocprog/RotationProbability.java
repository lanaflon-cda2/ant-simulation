package ch.epfl.moocprog;

import ch.epfl.moocprog.utils.Utils;

public class RotationProbability {
	private double [] radians;
	private double [] probs;
	public RotationProbability(double radians[], double probs[]) {
		Utils.requireNonNull(radians);
		Utils.requireNonNull(probs);
		Utils.require(radians.length == probs.length);
		
		this.radians = new double[radians.length];
		this.probs = new double[probs.length];
		
		this.radians = radians.clone();
		this.probs = probs.clone();
	}
	public double[] getAngles() {
		return this.radians.clone();
	}
	public double[] getProbabilities() {
		return this.probs.clone();
	}

}
