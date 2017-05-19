package com.kitsunesoftware.fractalmusic.rhythm;

import static com.kitsunesoftware.fractalmusic.FractalMusicConstants.TICKS_PER_BEAT;

import java.util.Random;

public class MostBasicRhythm extends Rhythm {
	
	public MostBasicRhythm(Random randomArg, int minimumLengthArg) {
		super(randomArg, minimumLengthArg);
	}
	
	public int twiddleLengthInBeatsFunction() {
		return 0;
	}
	
	public long durationFunction(int noteIndex) {
		return TICKS_PER_BEAT;
	}
	
	public long relativeNoteDelayInTicksFunction(int noteIndex) {
		return TICKS_PER_BEAT;
	}
}
