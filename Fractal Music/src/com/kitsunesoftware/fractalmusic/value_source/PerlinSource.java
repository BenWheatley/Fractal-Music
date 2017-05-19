package com.kitsunesoftware.fractalmusic.value_source;

import java.util.Random;

public class PerlinSource implements IntegerSource {
	private Random random;
	int coefficientCount;
	private int position;
	private double lowCoefficients[], highCoefficients[];
	
	public PerlinSource(Random randomArg, int coefficientCount_in) {
		random = randomArg;
		coefficientCount = coefficientCount_in;
		position = 0;
		lowCoefficients = new double[coefficientCount];
		highCoefficients = new double[coefficientCount];
		for (int i=0; i<coefficientCount; ++i) {
			lowCoefficients[i] = random.nextDouble();
			highCoefficients[i] = random.nextDouble();
		}
	}
	
	public int nextInt(int range) {
		int result = (int)(range*nextDouble());
		return result;
	}
	
	public double nextDouble() {
		double result = 0;
		
		// Calculate Perlin value for current state
		for (int i=0; i<coefficientCount; ++i) {
			double wavelength = Math.pow(2, i);
			double intensity = wavelength / Math.pow(2, coefficientCount);
			double positionInWave = (position%wavelength)/wavelength;
			double waveComponent = (positionInWave*highCoefficients[i]) + ((1-positionInWave)*lowCoefficients[i]);
			result += intensity*waveComponent;
		}
		// Update state
		++position;
		for (int i=0; i<coefficientCount; ++i) {
			int wavelength = 1<<i;
			if ( (position%wavelength)==0 ) {
				lowCoefficients[i] = highCoefficients[i];
				highCoefficients[i] = random.nextDouble();
			}
		}
		
		return result;
	}
}
