package com.kitsunesoftware.fractalmusic;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class Note {
	public int note;
	public int volume;
	public long startTimeTicks, durationTicks;
	
	public Note() {}
	public Note(Note src) {
		note = src.note;
		volume = src.volume;
		startTimeTicks = src.startTimeTicks;
		durationTicks = src.durationTicks;
	}
	
	public void renderToTrack(Track track, int channel) throws InvalidMidiDataException {
		ShortMessage msg = new ShortMessage();
		msg.setMessage(ShortMessage.NOTE_ON, channel, note, volume);
		track.add(new MidiEvent(msg, startTimeTicks));
		msg = new ShortMessage();
		msg.setMessage(ShortMessage.NOTE_OFF, channel, note, 0);
		track.add(new MidiEvent(msg, startTimeTicks+durationTicks));
	}
}
