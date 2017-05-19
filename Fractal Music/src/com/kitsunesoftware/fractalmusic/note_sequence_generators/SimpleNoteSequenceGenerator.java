package com.kitsunesoftware.fractalmusic.note_sequence_generators;

import static com.kitsunesoftware.fractalmusic.FractalMusicConstants.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;

import com.kitsunesoftware.fractalmusic.Key;
import com.kitsunesoftware.fractalmusic.Note;
import com.kitsunesoftware.fractalmusic.chord_renderers.ChordRenderer;
import com.kitsunesoftware.fractalmusic.improvisers.Improviser;
import com.kitsunesoftware.fractalmusic.motif.Motif;
import com.kitsunesoftware.fractalmusic.rhythm.Rhythm;

public class SimpleNoteSequenceGenerator {
	
	public Random random;
	
	public SimpleNoteSequenceGenerator(Random randomArg) {
		random = randomArg;
	}
	
	/**
	 * Creates a bunch of notes. The timing of the notes is relative, not absolute.
	 * 
	 * @param motif	The Motif of this collection of notes
	 * @param improviser	The Improviser to use for notes not taken from the Motif
	 * @param chordRenderer	How to render chords
	 * @param key	What key this is in
	 * @param octave	What octave this is in
	 * @param transposition	How far to transpose notes
	 * @param rhythm	The rhythm of the notes
	 * @param barStressPattern	Which notes in the bar should be stressed
	 * @param highVolume	The volume of stressed notes
	 * @param lowVolume	The volume of unstressed notes
	 * 
	 * @return	A collection of notes. Timing starts at zero, Note.note values represents MIDI note values. 
	 */
	public Vector<Note> toMIDI(Motif motif, Improviser improviser, ChordRenderer chordRenderer, Key key, int octave, int transposition, Rhythm rhythm, boolean barStressPattern[], int highVolume, int lowVolume) {
		final int RHYTHM_PATTERN_LENGTH = rhythm.pattern.size();
		// Decide where each note's pitch is to be sourced from
		ArrayList<Boolean> notePitchIsFromMotif = new ArrayList<Boolean>();
		for (int i=0; i<RHYTHM_PATTERN_LENGTH; ++i) {
			notePitchIsFromMotif.add(i<motif.noteLikeValues.length);
		}
		Collections.shuffle(notePitchIsFromMotif, random);
		// Choose note values
		Vector<Note> result = new Vector<Note>();
		for (int beatIndex=0; beatIndex<RHYTHM_PATTERN_LENGTH; ++beatIndex) {
			// Grab the base note for this point in the beat
			Note baseNote = rhythm.pattern.elementAt(beatIndex);
			// Do we want to stress this note?
			boolean startTimeIsOnBeatBoundary = (baseNote.startTimeTicks%TICKS_PER_BEAT)==0;
			int barPosition = (int)(baseNote.startTimeTicks/TICKS_PER_BEAT);
			boolean isStressed = startTimeIsOnBeatBoundary && barStressPattern[barPosition%barStressPattern.length];
			// Set note volume
			baseNote.volume = isStressed? highVolume: lowVolume;
			// What note(s) do we want to play? This information may come from the Motif or the Improviser
			int baseNoteLikeValue;
			if (notePitchIsFromMotif.get(beatIndex)) {
				baseNoteLikeValue = motif.getCurrentNoteLikeValue();
				motif.moveForwardWithinMotif();
			}
			else baseNoteLikeValue = improviser.getNoteLikeValueForBeat(motif, beatIndex);
			// We may (or may not) want to play a chord. We can generalise chords to include single notes, so this works in both cases.
			int midiNotes[] = chordRenderer.getCurrentMIDINotes(baseNoteLikeValue, key, octave, transposition);
			for (int chordIndex=0; chordIndex<midiNotes.length; ++chordIndex) {
				Note note = new Note(baseNote);
				note.note = midiNotes[chordIndex];
				result.add(note);
			}
		}
		return result;
	}
}
