package com.kitsunesoftware.fractalmusic.chord_renderers;

import java.util.Random;

public class ChordRendererBiasedComplexRandomChord extends ChordRenderer {
	
	Random random;
	
	int stateIndex;
	double chordProbabilities[];
	
	public ChordRendererBiasedComplexRandomChord(Random random_in) {
		random = random_in;
		stateIndex = 0;
		chordProbabilities = new double[]{
				// Source data (my sole opinion, where a=good, l=bad):
//				aaa
//				aaaaaaaaa
//				aaallaaaa aaaaaaala aaaaaaaaa
//				aaa
//				llaaaaall
//				lllllllla aaaaaaaaa allaalala
//				aaa
//				aaaaaaaaa
//				aaallllll ????????? ?????????
				1, 1, 1,	// Two notes
				0, 0, 1, 1, 1, 1, 1, 0, 0,	// Three notes
				0, 0, 0, 0, 0, 0, 0, 0, 1,	1, 1, 1, 1, 1, 1, 1, 0, 1,	1, 0, 0, 1, 1, 0, 1, 0, 1	// Four notes
		};
		chooseNewChordIndex();
	}
	
	private void chooseNewChordIndex() {
		double totalProbability = 0;
		for (int i=0; i<chordProbabilities.length; ++i) totalProbability += chordProbabilities[i];
		double chosenValue = random.nextDouble() * totalProbability;
		for (int i=0; i<chordProbabilities.length; ++i) {
			if (chosenValue<chordProbabilities[i]) {
				stateIndex = i;
				return;
			}
			else {
				chosenValue -= chordProbabilities[i];
			}
		}
		// Shouldn't get here, but I suppose rounding errors might lead to it, and in that case we want to choose the last index:
		stateIndex = chordProbabilities.length - 1;
	}
	
	public int[] baseNoteCollectionBuilder(int primeBaseNote) {
		int stepSizeCount = 3;
		if (stateIndex<3) stepSizeCount = 1;
		else if (stateIndex<(3+9)) stepSizeCount = 2;
		int stepSizes[] = new int[stepSizeCount];
		int result[] = new int[stepSizes.length];
		
		int relativeState  = stateIndex; // In the case of two notes, the step size is equal to stateIndex index
		if (stepSizeCount==2) relativeState  = stateIndex - 3;
		else if (stepSizeCount==3) relativeState  = stateIndex - (3+9);
		chooseNewChordIndex();
		
		for (int i=0; i<stepSizes.length; ++i) {
			int stepSizeExtra = relativeState %3;
			relativeState /= 3;
			stepSizes[i] = 2+stepSizeExtra;
			result[i] = primeBaseNote;
			for (int j=0; j<i; ++j) {
				result[i] += stepSizes[j];
			}
		}
		return result;
	}
	
}
