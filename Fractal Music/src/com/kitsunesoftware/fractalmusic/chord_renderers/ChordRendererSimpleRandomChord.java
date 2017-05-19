package com.kitsunesoftware.fractalmusic.chord_renderers;

import java.util.Random;

public class ChordRendererSimpleRandomChord extends ChordRenderer {
	
	Random random;
	
	public ChordRendererSimpleRandomChord(Random random_in) {
		random = random_in;
	}
	
	public int[] baseNoteCollectionBuilder(int primeBaseNote) {
		int step1, step2;
		step1 = 2+random.nextInt(1);
		step2 = 2+random.nextInt(1);
		return new int[]{
				primeBaseNote, primeBaseNote+step1, primeBaseNote+step1+step2
		};
	}
}
