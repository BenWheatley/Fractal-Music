package com.kitsunesoftware.fractalmusic;

public class FractalMusicConstants {
	
	/** Processes for creating music */
	public static final int PLAY_MOTIF_FILL_GAPS_WITH_LOOPING = 0;
	
	/** Octave designations: The note middle C (A.K.A. C4) exists in OCTAVE_4 */
	public static final int OCTAVE_0 = -4,
							OCTAVE_1 = -3,
							OCTAVE_2 = -2,
							OCTAVE_3 = -1,
							OCTAVE_4 = 0,
							OCTAVE_5 = 1,
							OCTAVE_6 = 2,
							OCTAVE_7 = 3,
							OCTAVE_8 = 4, // Highest note on an 88-key keyboard according to Wikipedia
							OCTAVE_9 = 5; // I think MIDI reaches this level, but it's silly to actually use it
	
	public static final int TICKS_PER_BEAT = 256;
	
//	/** Reserved channels */
//	public static final int MIDI_CHANNEL_MELODY = 0,
//							MIDI_CHANNEL_DRUMS = 1,
//							
//							RESERVED_MIDI_CHANNEL_COUNT = 1;
	
	/** Message types */
	public static final int META_MESSAGE_TEMPO = 0x51;
	
	/** Error strings */
	public static final String INSTRUMENT_NOT_FOUND = "Instrument not found";
	
	/** MIDI note values for OCTAVE_4 */
	public final static int C	= 60,
							CS	= 61,
							DF  = 61, // == C#
							D	= 62,
							DS	= 63,
							EF  = 63, // == D#
							E	= 64,
							ES	= 65, // == F
							F	= 65,
							FS	= 66,
							FSS = 67, // == G
							G	= 67,
							GS	= 68,
							AF  = 68, // == G#
							A	= 69,
							AS	= 70,
							BF  = 70, // == A#
							B	= 71,
							BS	= 72; // == C (of the next octave up)
}
