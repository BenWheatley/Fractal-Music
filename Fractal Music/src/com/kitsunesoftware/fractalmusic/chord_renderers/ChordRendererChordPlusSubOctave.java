package com.kitsunesoftware.fractalmusic.chord_renderers;

public class ChordRendererChordPlusSubOctave extends ChordRenderer {
	
	public int[] baseNoteCollectionBuilder(int primeBaseNote) {
		return new int[]{
				primeBaseNote, primeBaseNote+2, primeBaseNote+4, primeBaseNote-7
		};
	}
}
