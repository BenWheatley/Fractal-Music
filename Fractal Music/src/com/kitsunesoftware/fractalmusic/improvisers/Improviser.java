package com.kitsunesoftware.fractalmusic.improvisers;

import java.util.Random;

import com.kitsunesoftware.fractalmusic.motif.Motif;

public class Improviser {
	
	Random random;
	
	public Improviser(Random randomArg) {
		random = randomArg;
	}
	
	public int getNoteLikeValueForBeat(Motif m, int beatIndex) {
		return m.noteLikeValues[random.nextInt(m.noteLikeValues.length)];
	}
	
}
