package com.kitsunesoftware.fractalmusic.generic;

import com.kitsunesoftware.fractalmusic.probability_function.ProbabilityFunction;

public class ExponentialDecayProbabilityFunction implements ProbabilityFunction {
	
	double base;
	
	public ExponentialDecayProbabilityFunction(double base_in) {
		base = base_in;
	}

	public double probability(double input) {
		return 1.0 / Math.pow(base, input);
	}

}
