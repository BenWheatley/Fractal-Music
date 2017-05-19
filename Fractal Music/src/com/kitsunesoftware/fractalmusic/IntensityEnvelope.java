package com.kitsunesoftware.fractalmusic;

public class IntensityEnvelope {
	
	double dataPoints[];
	
	public static IntensityEnvelope standardIntensityEnvelope() {
		IntensityEnvelope result = new IntensityEnvelope();
		for (int i=0; i<result.dataPoints.length; ++i) {
			result.dataPoints[i] = Math.pow(
					Math.sin((Math.PI*i)/result.dataPoints.length),
					2 );
		}
		return result;
	}
	
	public IntensityEnvelope() {
		dataPoints = new double[100];
	}
	
	/** Returns the intensity (range 0=lowest 1=highest) for the specified time (range 0=start, 1=end) */
	public double getIntensityAtTime(double time) {
		double floatingIndex = time*dataPoints.length;
		int previousIndex = (int)Math.floor(floatingIndex);
		double fractionalIndex = floatingIndex - previousIndex;
		double previousValue = dataPoints[previousIndex];
		double nextValue = dataPoints[previousIndex+1];
		double interpolatedValue = (previousValue*(1-fractionalIndex)) + (nextValue*fractionalIndex);
		return interpolatedValue;
	}
}
