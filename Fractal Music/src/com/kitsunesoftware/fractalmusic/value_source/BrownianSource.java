package com.kitsunesoftware.fractalmusic.value_source;

import java.util.Random;

public class BrownianSource implements IntegerSource {
	private Random random;
	int lastValue;
	
	public BrownianSource(Random randomArg) {
		random = randomArg;
		lastValue = random.nextInt();
	}
	
	public int nextInt(int range) {
		lastValue %= range;
		if (random.nextBoolean()) ++lastValue;
		else --lastValue;
		if (lastValue<0 || lastValue>=range) {
			lastValue = random.nextInt(range);
		}
		return lastValue;
	}
}
