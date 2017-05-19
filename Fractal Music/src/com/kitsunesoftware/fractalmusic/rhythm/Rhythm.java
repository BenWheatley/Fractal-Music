package com.kitsunesoftware.fractalmusic.rhythm;

import static com.kitsunesoftware.fractalmusic.FractalMusicConstants.*;

import java.util.Random;
import java.util.Vector;

import com.kitsunesoftware.fractalmusic.Note;
import com.kitsunesoftware.fractalmusic.value_source.*;

public class Rhythm {
	
	Random random;
	IntegerSource integerSource;
	public Vector<Note> pattern;
	int minimumLength;
	
	public Rhythm(Random randomArg, int minimumLengthArg) {
		random = randomArg;
		integerSource = new PerlinSource(random, 8);
		minimumLength = minimumLengthArg;
		pattern = new Vector<Note>();
		
		int combinedLength = minimumLength + twiddleLengthInBeatsFunction();
		int nextNoteStartTimeTicks = 0;
		
		for (int i=0; i<combinedLength; ++i) {
			Note n = new Note();
			n.durationTicks = durationFunction(i);
			n.startTimeTicks = nextNoteStartTimeTicks;
			nextNoteStartTimeTicks += relativeNoteDelayInTicksFunction(i);
			pattern.add(n);
			// TODO: coerce length to fit something, but what?
		}
	}
	
	public int twiddleLengthInBeatsFunction() {
		return random.nextInt(minimumLength);
	}
	
	private long POSSIBLE_DURATIONS[] = {
			TICKS_PER_BEAT * 4,
			TICKS_PER_BEAT * 3,
			TICKS_PER_BEAT * 2,
			TICKS_PER_BEAT,
			TICKS_PER_BEAT / 2,
			TICKS_PER_BEAT / 4
	};
	public long durationFunction(int noteIndex) {
		return POSSIBLE_DURATIONS[integerSource.nextInt(POSSIBLE_DURATIONS.length)];
	}
	
	private long POSSIBLE_RELATIVE_NOTE_DELAYS[] = {
			TICKS_PER_BEAT,
			TICKS_PER_BEAT * 2
	};
	public long relativeNoteDelayInTicksFunction(int noteIndex) {
		return POSSIBLE_RELATIVE_NOTE_DELAYS[integerSource.nextInt(POSSIBLE_RELATIVE_NOTE_DELAYS.length)];
	}
}
