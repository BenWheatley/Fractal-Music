package com.kitsunesoftware.fractalmusic.chord_renderers;

import com.kitsunesoftware.fractalmusic.Key;

/**
 * Transforms a base note (in the form of an index into a key) into a chord set (array of MIDI note values)
 * The base class returns a "chord" of one note.
 * 
 * @author benwheatley
 *
 */
public class ChordRenderer {
	
	/** Octave 0 is the one containing middle C; @see FractalMusicConstants.OCTAVE_4 et. al.
	 * This creates a set of MIDI notes to be played *simultaneously*
	 * */
	public int[] getCurrentMIDINotes(int sourceNoteLikeValue, Key keyArg, int octave, int transposition) {
		int baseNote = getCurrentMIDINoteBase(sourceNoteLikeValue, transposition);
		
		int baseNoteCollection[] = baseNoteCollectionBuilder(baseNote);
		
		int result[] = new int[baseNoteCollection.length];
		int key[] = keyArg.notes;
		for (int i=0; i<result.length; ++i) {
			int noteLikeValue = baseNoteCollection[i];
			int effectiveOctave = (noteLikeValue/key.length) + octave;
			int keyIndex = noteLikeValue%key.length;
			while (keyIndex<0) {
				keyIndex += key.length;
			}
			result[i] = key[keyIndex] + 12*effectiveOctave;
		}
		
		return result;
	}
	
	public int getCurrentMIDINoteBase(int sourceNoteLikeValue, int transposition) {
		int noteLikeValue = sourceNoteLikeValue + transposition;
		return noteLikeValue;
	}
	
	public int[] baseNoteCollectionBuilder(int primeBaseNote) {
		return new int[]{
				primeBaseNote
		};
	}
}
