package com.kitsunesoftware.fractalmusic.motif;

import java.util.Random;

import com.kitsunesoftware.fractalmusic.value_source.BrownianSource;
import com.kitsunesoftware.fractalmusic.value_source.PerlinSource;

public class Motif {
	
	/** Styles of Motif */
	public static final int WHITE_SPECTRUM = 0,
							BROWNIAN = 1,
							PERLIN = 2;
	
	public int noteLikeValues[];
	int currentPosition;
	
	/** Creates a random motif with the specified random source, style (see Motif class constants) length, and range of note-like values */
	public Motif(Random random, int style, int motifLength, int motifRange) {
		noteLikeValues = new int[motifLength];
		BrownianSource brownianSource = null;
		if (style==BROWNIAN) brownianSource = new BrownianSource(new Random(random.nextLong()));
		PerlinSource perlinSource = null;
		if (style==PERLIN) {
			int coefficientCount = (int)(Math.log(motifLength)/Math.log(2));
			perlinSource = new PerlinSource(new Random(random.nextLong()), coefficientCount); 
		}
		for (int i=0; i<motifLength; ++i) {
			switch (style) {
			case WHITE_SPECTRUM: noteLikeValues[i] = random.nextInt(motifRange); break;
			case BROWNIAN: noteLikeValues[i] = brownianSource.nextInt(motifRange); break;
			case PERLIN: noteLikeValues[i] = perlinSource.nextInt(motifRange); break;
			}
		}
	}
	
	public int getNthNoteLikeValue(int noteIndex) {
		return noteLikeValues[noteIndex%noteLikeValues.length];
	}
	
	public int getCurrentNoteLikeValue() {
		return getNthNoteLikeValue(currentPosition);
	}
	
	public void moveForwardWithinMotif() {
		++currentPosition;
	}
	
	public void resetMotif() {
		currentPosition = 0;
	}
	
	public boolean motifHasFinished() {
		return currentPosition>=noteLikeValues.length;
	}
	
	public String toString() {
		String result = null;
		for (int i=0; i<noteLikeValues.length; ++i) {
			if (result==null) result = ""; else result += ",";
			result += noteLikeValues[i];
		}
		return result;
	}
}
