package com.kitsunesoftware.fractalmusic;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.*;

public class MidiPlayer {
	public Sequence sequence;
	public Sequencer sequencer;
	public Synthesizer synthesizer;
	
	/** Loads a midi file from disk; throws any exceptions from loading the file 
	 * @throws IOException 
	 * @throws InvalidMidiDataException 
	 * @throws MidiUnavailableException */
	public MidiPlayer(File file) throws InvalidMidiDataException, IOException, MidiUnavailableException {
		sequence = MidiSystem.getSequence(file);
		sequencer = MidiSystem.getSequencer();
		synthesizer = MidiSystem.getSynthesizer();
		sequencer.open();
		sequencer.setSequence(sequence);
		sequencer.getTransmitter().setReceiver(synthesizer.getReceiver());
		synthesizer.open();
	}
	
	/** Loads a midi file from a sequence; throws any exceptions raised
	 * @throws MidiUnavailableException 
	 * @throws InvalidMidiDataException */
	public MidiPlayer(Sequence sequence_in) throws MidiUnavailableException, InvalidMidiDataException {
		sequence = sequence_in;
		sequencer = MidiSystem.getSequencer();
		synthesizer = MidiSystem.getSynthesizer();
		sequencer.open();
		sequencer.setSequence(sequence);
		sequencer.getTransmitter().setReceiver(synthesizer.getReceiver());
		synthesizer.open();
	}
	
	public void play_looping() {
		sequencer.setTickPosition(0);
		sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
		sequencer.start();
	}
	
	public void play() {
		sequencer.setTickPosition(0);
		sequencer.setLoopCount(0);
		sequencer.start();
	}
	
	public void stop() {
		MidiChannel mc[] = synthesizer.getChannels();
		for (int i=mc.length-1; i>=0; --i) mc[i].allSoundOff();
		sequencer.stop();
	}
	
	public boolean is_playing() {
		return sequencer.isRunning();
	}
}
