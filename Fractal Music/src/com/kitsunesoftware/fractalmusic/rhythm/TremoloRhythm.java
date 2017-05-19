package com.kitsunesoftware.fractalmusic.rhythm;

import static com.kitsunesoftware.fractalmusic.FractalMusicConstants.TICKS_PER_BEAT;

import java.util.Random;

public class TremoloRhythm extends Rhythm {
	
	public static final int BEAT_FRACTION = 4;
	
	public TremoloRhythm(Random randomArg, int minimumLengthArg) {
		super(randomArg, minimumLengthArg);
	}
	
	public int twiddleLengthInBeatsFunction() {
		return (minimumLength*BEAT_FRACTION)-minimumLength; // Total length = minimumLength*BEAT_FRACTION; twiddle is length beyond minimumLength
	}
	
	public long durationFunction(int noteIndex) {
		return TICKS_PER_BEAT/BEAT_FRACTION;
	}
	
	public long relativeNoteDelayInTicksFunction(int noteIndex) {
		return TICKS_PER_BEAT/BEAT_FRACTION;
	}
	
}
