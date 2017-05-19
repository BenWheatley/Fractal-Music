package com.kitsunesoftware.fractalmusic;

import java.util.*;

import javax.sound.midi.*;

import com.kitsunesoftware.fractalmusic.chord_renderers.ChordRenderer;
import com.kitsunesoftware.fractalmusic.generic.ExponentialDecayProbabilityFunction;
import com.kitsunesoftware.fractalmusic.improvisers.*;
import com.kitsunesoftware.fractalmusic.motif.*;
import com.kitsunesoftware.fractalmusic.note_sequence_generators.SimpleNoteSequenceGenerator;
import com.kitsunesoftware.fractalmusic.rhythm.*;
import com.kitsunesoftware.fractalmusic.value_source.*;

import static com.kitsunesoftware.fractalmusic.FractalMusicConstants.*;

public class FractalMusic {

	/** Intensity envelope */
	IntensityEnvelope intensityEnvelope;
	/** The random source for this music */
	Random random;
	/** The starting seed for the random source for this music */
	long startingSeed;
	
	/** A collection of keys which may be used within this composition. */
	public KeyCollection keys;
	/** The number of key changes in this composition */
	public int keyChangeCount;

	public int[] keyRuns;
	public int keyRunIndex;
	public int keyRunCountdown;
	public Key currentKey;
	
	/** The minimum and maximum tempo of the music, measured in BPM. */
	public int tempoMinimum, tempoMaximum;
	/** The number of tempo changes in this composition */
	public int tempoChangeCount;
	/** Which beats in a bar are to be stressed; length of this array is assumed to be the length of a bar as measured in beats. */
	public boolean barStressPattern[];
	/** The length of the composition, measured in bars */
	public int lengthInBars;
	
	public Vector<Voice> voices;
	
	public FractalMusic() {
		// For all voices
		startingSeed = (long)(Math.random()*Long.MAX_VALUE);
		random = new Random(startingSeed);
		tempoMinimum = 120;
		tempoMaximum = 180;
		tempoChangeCount = 28;
		barStressPattern = new boolean[]{true, false, false};
		lengthInBars = 14*2*2;
		intensityEnvelope = IntensityEnvelope.standardIntensityEnvelope();
		
		keyChangeCount = 3;
		keys = new KeyCollection(new ExponentialDecayProbabilityFunction(1), Key.PENTATONIC);
		
		voices = new Vector<Voice>();
		// For each voice
		{
			voices.add(defaultNewVoice());
		}
	}
	
	public Voice defaultNewVoice() {
		final int baseMotifLength = barStressPattern.length;
		MotifCollection motifCollection = new MotifCollectionWithForm(
				"A A B B A A B B C C D D C C D D E E F F E E F F G G G G",
				new Motif(random, Motif.WHITE_SPECTRUM, baseMotifLength*2, 10),
				new Motif(random, Motif.WHITE_SPECTRUM, baseMotifLength*2, 5),
				new Motif(random, Motif.WHITE_SPECTRUM, baseMotifLength*2, 10),
				new Motif(random, Motif.WHITE_SPECTRUM, baseMotifLength*2, 5),
				new Motif(random, Motif.WHITE_SPECTRUM, baseMotifLength*2, 10),
				new Motif(random, Motif.WHITE_SPECTRUM, baseMotifLength*2, 5),
				new Motif(random, Motif.WHITE_SPECTRUM, baseMotifLength*2, 10)
				);
		
		int newRhythmCount = 10;
		int minimumRhythmLength = barStressPattern.length;
		Rhythm rhythms[] = new Rhythm[newRhythmCount];
		for (int i=0; i<newRhythmCount; ++i) {
			rhythms[i] = /*random.nextBoolean()?
					new TremoloRhythm(random, minimumRhythmLength):*/
					new MostBasicRhythm(random, minimumRhythmLength);
		}
		RhythmCollection beatPatternCollection = new RhythmCollection(rhythms);
		
		Improviser improvisers[] = {new CopyImproviser(), new Improviser(new Random(random.nextLong()))};
		ImproviserCollection improviserCollection = new ImproviserCollection(improvisers);
		
		String instrument = "Piano";
		
		int volumeMinimum = 30, volumeMaximum = 50, volumeChangeCount = 8;

		double emphasisVolumeMultiplier = 1.5;
		int transpositionMinimum = -18, transpositionMaximum = -6;
		IntegerSource transpositionSource = new PerlinSource(new Random(random.nextLong()), 8);
		return new Voice(	new Random(random.nextLong()),
							instrument,
							motifCollection, beatPatternCollection, improviserCollection,
							volumeMinimum, volumeMaximum, volumeChangeCount,
							emphasisVolumeMultiplier,
							transpositionMinimum, transpositionMaximum, transpositionSource
								);
	}
	
	public void setTempoRange(int newMinimum, int newMaximum) {
		tempoMinimum = newMinimum;
		tempoMaximum = newMaximum;
	}
	
	/** Gets the list of MIDI-number notes in the specified scale_data, from pitchMin to pitchMax.
	 * key will be treated as an infinitely repeating pattern (mod 12) for calculations. */
	public int[] getNotesInKeyAndRange(int[] key, int pitchMin, int pitchMax) {
		LinkedList<Integer> result_store = new LinkedList<Integer>();
		for (int i=pitchMin; i<=pitchMax; ++i) {
			for (int j=0; j<key.length; ++j) {
				if (key[j]%12 == i%12) {
					result_store.add(i);
					break; // Terminates inner loop
				}
			}
		}
		int result[] = new int[result_store.size()];
		for (int i=0; i<result.length; ++i) {
			result[i] = result_store.get(i);
		}
		return result;
	}
	
	private int[] generateRun(int changeCount) {
		if (changeCount==0) {
			return new int[]{0, lengthInBars};
		}
		// Pick changeCount+1 random numbers, divide by sum of all such numbers, multiply by lengthInBars
		changeCount += 1;
		double randomNumbers[] = new double[changeCount];
		double sum = 0; for (int i=0; i<randomNumbers.length; ++i) {
			randomNumbers[i] = random.nextDouble();
			sum += randomNumbers[i];
		}
		int result[] = new int[changeCount];
		for (int i=0; i<changeCount; ++i) {
			result[i] = (int)( (randomNumbers[i]/sum) * lengthInBars );
		}
		return result;
	}
	
	private int chooseTempo(double timeFraction) {
		int tempoRange = tempoMaximum - tempoMinimum;
		return tempoMinimum + (int)(tempoRange*intensityEnvelope.getIntensityAtTime(timeFraction));
	}
	
	private int[] chooseNotePitches(Motif currentMotif, ChordRenderer currentMotifRenderer, Key key, int octave, int transposition, int previousNotePitch, int currentProcess) {
		int results[] = currentMotifRenderer.getCurrentMIDINotes(currentMotif.getCurrentNoteLikeValue(), key, octave, transposition);
		currentMotif.moveForwardWithinMotif();
		return results;
	}
	
	private void renderNoteToTrack(Track track, int channel, int notePitch, int noteVolume, long startTimeInTicks, long durationInTicks) throws InvalidMidiDataException {
		if (notePitch<0 || notePitch>127) return;
		ShortMessage msg = new ShortMessage();
		msg.setMessage(ShortMessage.NOTE_ON, channel, notePitch, noteVolume);
		track.add(new MidiEvent(msg, startTimeInTicks));
		msg = new ShortMessage();
		msg.setMessage(ShortMessage.NOTE_OFF, channel, notePitch, 0);
		track.add(new MidiEvent(msg, startTimeInTicks+durationInTicks));
	}
	
	private Track trackBuilder(Sequence result_sequence, int channel, String instrument) throws IllegalArgumentException, InvalidMidiDataException {
		if (instrument=="None") return null;
		Track result_track = result_sequence.createTrack();
		setInstrumentForTrack(result_track, channel, instrument);
		return result_track;
	}
	
	private void addTempoChangeEvent(long timeInTicks, int newTempoBPM) throws InvalidMidiDataException {
		int tempoInMPQ = 60000000 / newTempoBPM;
		byte[] data = new byte[3];
		data[0] = (byte)((tempoInMPQ >> 16) & 0xFF);
		data[1] = (byte)((tempoInMPQ >> 8) & 0xFF);
		data[2] = (byte)(tempoInMPQ & 0xFF);
		MetaMessage message = new MetaMessage();
		message.setMessage(META_MESSAGE_TEMPO, data, data.length);
		MidiEvent event = new MidiEvent( message, timeInTicks );
		for (Voice voice : voices) {
			voice.renderer.track.add(event);
		}
	}
	
	private Instrument getInstrument(String instrument_name) {
		if (FractalMusicGlobals.instruments==null) FractalMusicGlobals.acquireCompleteListOfInstruments();
		for (int i=0; i < FractalMusicGlobals.instruments.length; i++) {
			if (FractalMusicGlobals.instruments[i].getName().compareTo(instrument_name)==0) {
				return FractalMusicGlobals.instruments[i];
			}
		}
		return null;
	}
	
	private void setInstrumentForTrack(Track track, int channel, String instrumentName) throws IllegalArgumentException, InvalidMidiDataException {
		Instrument instrument = getInstrument(instrumentName);
		if (instrument==null) throw new IllegalArgumentException(INSTRUMENT_NOT_FOUND);
		Patch instrumentPatch = instrument.getPatch();
		int instrument_value = instrumentPatch.getProgram();
		ShortMessage msg = new ShortMessage();
		msg.setMessage(ShortMessage.PROGRAM_CHANGE, channel, instrument_value, 0);
		track.add(new MidiEvent(msg, 0/*tick*/));
	}
	
	public Key chooseKey(double timeFraction) {
		return keys.chooseItemFromCollection();
	}
	
	public Sequence toMIDI() throws InvalidMidiDataException {
		Sequence result = new Sequence(Sequence.PPQ, TICKS_PER_BEAT);
		
		for (Voice voice : voices) {
			if (null==voice.renderer) voice.renderer = new VoiceRenderer();
			int channel = voices.indexOf(voice);//MIDI_CHANNEL_MELODY
			voice.renderer.track = trackBuilder(result, channel, voice.instrument);
		}
		
		random.setSeed(startingSeed);
		
		// Set tempo for ALL voices at the same time
		int tempoRuns[] = generateRun(tempoChangeCount);
		int tempoRunIndex = -1, tempoRunCountdown = 0;
		addTempoChangeEvent(0, chooseTempo(0));
		
		keys.resetRandomSource();
		currentKey = null;
		keyRuns = generateRun(keyChangeCount);
		keyRunIndex = -1;
		keyRunCountdown = 0;
		
		// Keys, volume, etc. are on a per-voice basis
		for (Voice voice : voices) {
			voice.motifCollection.resetRandomSource();
			voice.rhythmCollection.resetRandomSource();
			
			voice.renderer.volumeRuns = generateRun(voice.volumeChangeCount);
			voice.renderer.volumeRunIndex = -1;
			voice.renderer.volumeRunCountdown = 0;
			
			voice.renderer.currentVolume = 0;
			voice.renderer.currentOctave = OCTAVE_4;
			voice.renderer.currentTransposition = voice.transpositionMinimum + (voice.transpositionMaximum-voice.transpositionMinimum)/2;
			voice.renderer.currentMotif = voice.motifCollection.chooseItemFromCollection();
			voice.renderer.currentChordRenderer = new ChordRenderer();
			voice.renderer.currentRhythm = voice.rhythmCollection.chooseItemFromCollection();
			voice.renderer.noteSource = new SimpleNoteSequenceGenerator(new Random(random.nextLong()));
		}

		long currentTimeInTicks = 0;
		int currentTempo = 0;
		
		for (int barIndex=0; barIndex<lengthInBars; ++barIndex) {
			
			double timeFraction = ((double)(barIndex))/lengthInBars;
			
			if (--tempoRunCountdown<0) {
				++tempoRunIndex;
				tempoRunCountdown = tempoRuns[tempoRunIndex];
				currentTempo = chooseTempo(timeFraction);
				addTempoChangeEvent(currentTimeInTicks, currentTempo);
			}

			// See if we want to change key, iff so, to what
			if (--keyRunCountdown<0) {
				++keyRunIndex;
				keyRunCountdown = keyRuns[keyRunIndex];
				currentKey = chooseKey(timeFraction);
			}
			
			for (Voice voice : voices) {
				// See if we want to change volume, iff so, to what
				if (--voice.renderer.volumeRunCountdown<0) {
					++voice.renderer.volumeRunIndex;
					voice.renderer.volumeRunCountdown = voice.renderer.volumeRuns[voice.renderer.volumeRunIndex];
					voice.renderer.currentVolume = voice.chooseVolume(intensityEnvelope, timeFraction);
				}

				// Change transposition
				int transpositionRange = voice.transpositionMaximum - voice.transpositionMinimum;
				voice.renderer.currentTransposition = voice.transpositionMinimum + voice.transpositionSource.nextInt(transpositionRange);
				
				int lowVolume = voice.renderer.currentVolume;
				int highVolume = (int) (lowVolume * voice.emphasisVolumeMultiplier);
				if (lowVolume>127) lowVolume = 127;
				if (highVolume>127) highVolume = 127;
				
				if (voice.renderer.currentMotif.motifHasFinished()) {
					voice.renderer.currentMotif = voice.motifCollection.chooseItemFromCollection();
					voice.renderer.currentMotif.resetMotif();
				}
				// TODO: these might be expected to continue over more than one bar.
				voice.renderer.currentRhythm = voice.rhythmCollection.chooseItemFromCollection();
				voice.renderer.currentImproviser = voice.improviserCollection.chooseItemFromCollection();
				
				Vector<Note> notes = voice.renderer.noteSource.toMIDI(
						voice.renderer.currentMotif,
						voice.renderer.currentImproviser,
						voice.renderer.currentChordRenderer,
						currentKey,
						voice.renderer.currentOctave, voice.renderer.currentTransposition,
						voice.renderer.currentRhythm, barStressPattern, highVolume, lowVolume);
				int channel = voices.indexOf(voice);//MIDI_CHANNEL_MELODY
				for (Note note : notes) {
					note.startTimeTicks += currentTimeInTicks;
					renderNoteToTrack(voice.renderer.track, channel, note.note, note.volume, note.startTimeTicks, note.durationTicks);
				}
			}
			
			currentTimeInTicks += TICKS_PER_BEAT * barStressPattern.length;
			
		}
		
		return result;
	}
}
