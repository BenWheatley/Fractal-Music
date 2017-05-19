package com.kitsunesoftware.fractalmusic.chord_renderers;

import java.util.Random;

public class ChordRendererComplexRandomChord extends ChordRenderer {
	
	Random random;
	
	public ChordRendererComplexRandomChord(Random random_in) {
		random = random_in;
	}
	
	public int[] baseNoteCollectionBuilder(int primeBaseNote) {
		int stepSizes[] = new int[1+random.nextInt(3)];
		int result[] = new int[stepSizes.length];
		for (int i=0; i<stepSizes.length; ++i) {
			stepSizes[i] = 2+random.nextInt(2);
			result[i] = primeBaseNote;
			for (int j=0; j<i; ++j) {
				result[i] += stepSizes[j];
			}
		}
		return result;
	}

}
