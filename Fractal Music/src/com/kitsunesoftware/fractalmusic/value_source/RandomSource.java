package com.kitsunesoftware.fractalmusic.value_source;

import java.util.Random;

public class RandomSource implements IntegerSource {
	Random random;
	
	public RandomSource(Random randomArg) {
		random = randomArg;
	}
	
	public int nextInt(int range) {
		return random.nextInt(range);
	}
}
