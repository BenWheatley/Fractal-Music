package com.kitsunesoftware.fractalmusic;

import javax.sound.midi.Track;

import com.kitsunesoftware.fractalmusic.chord_renderers.ChordRenderer;
import com.kitsunesoftware.fractalmusic.improvisers.Improviser;
import com.kitsunesoftware.fractalmusic.motif.Motif;
import com.kitsunesoftware.fractalmusic.note_sequence_generators.SimpleNoteSequenceGenerator;
import com.kitsunesoftware.fractalmusic.rhythm.Rhythm;

public class VoiceRenderer {
	
	public Track track;
		
	public int[] volumeRuns;
	public int volumeRunIndex;
	public int volumeRunCountdown;
	public int currentVolume;
	
	public int currentOctave;
	
	public int currentTransposition;
	
	public Motif currentMotif;
	
	public ChordRenderer currentChordRenderer;
	
	public Rhythm currentRhythm;
	
	public Improviser currentImproviser;
	
	public SimpleNoteSequenceGenerator noteSource;
	
}
