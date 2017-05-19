package com.kitsunesoftware.fractalmusic;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;

public class FractalMusicGlobals {
	
	/** The instruments in the synthesiser */
	public static Instrument instruments[];
	
	public static void acquireCompleteListOfInstruments() {
		if (instruments!=null) return;
		try {
			Synthesizer synthesizer = MidiSystem.getSynthesizer();
			synthesizer.open();
			Soundbank bank = synthesizer.getDefaultSoundbank();
			if (bank==null) System.err.println("Default soundbank not available!");
			synthesizer.loadAllInstruments(bank);
			instruments = synthesizer.getLoadedInstruments();
		} catch (MidiUnavailableException e) {
			// TODO Auto-generated catch block
			System.err.println("Unexpectedly, a MidiUnavailableException was thrown while trying to get information about the available instruments.");
			e.printStackTrace();
		}
	}
}
