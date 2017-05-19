package com.kitsunesoftware.fractalmusic.improvisers;

import com.kitsunesoftware.fractalmusic.motif.Motif;

public class CopyImproviser extends Improviser {
	
	public CopyImproviser() {
		super(null);
	}
	
	public int getNoteLikeValueForBeat(Motif m, int beatIndex) {
		return m.getCurrentNoteLikeValue();
	}
	
}
