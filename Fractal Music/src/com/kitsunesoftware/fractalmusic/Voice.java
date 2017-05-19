package com.kitsunesoftware.fractalmusic;

import java.util.Random;

import com.kitsunesoftware.fractalmusic.improvisers.ImproviserCollection;
import com.kitsunesoftware.fractalmusic.motif.MotifCollection;
import com.kitsunesoftware.fractalmusic.rhythm.RhythmCollection;
import com.kitsunesoftware.fractalmusic.value_source.IntegerSource;

public class Voice {
	
	Random random;
	
	public String instrument;
	
	public VoiceRenderer renderer;
	
	/** A collection of motifs which may be used within this composition. */
	public MotifCollection motifCollection;
	/** A collection of rhythms which may be used within this composition. */
	public RhythmCollection rhythmCollection;
	/** A collection of improviser which may be used within this composition. */
	public ImproviserCollection improviserCollection;
	/** The minimum and maximum volume of the music. 0 = silent, 127/emphasisVolumeMultiplier = maximum */
	public int volumeMinimum, volumeMaximum;
	/** The number of volume changes in this composition */
	public int volumeChangeCount;
	
	/** The upper and lower limit to the number of note-like values (that is, physical keys in a given key) by which a motif can be transposed */
	public int transpositionMinimum, transpositionMaximum;
	/** The data source for transposition */
	public IntegerSource transpositionSource;
	
	/** Emphasis volume multiplier; maximum value is volumeMaximum/127 */
	public double emphasisVolumeMultiplier;
	
	
	/**
	 * Constructor
	 * 
	 * @param randomArg
	 * @param instrumentArg
	 * @param motifCollectionArg
	 * @param rhythmCollectionArg
	 * @param keyCollectionArg
	 * @param keyChangeCountArg
	 * @param volumeMinimumArg
	 * @param volumeMaximumArg
	 * @param volumeChangeCountArg
	 * @param emphasisVolumeMultiplierArg
	 * @param transpositionMinimumArg
	 * @param transpositionMaximumArg
	 * @param transpositionSource
	 */
	public Voice(Random randomArg, String instrumentArg,
			MotifCollection motifCollectionArg, RhythmCollection rhythmCollectionArg, ImproviserCollection improviserCollectionArg,
			int volumeMinimumArg, int volumeMaximumArg, int volumeChangeCountArg,
			double emphasisVolumeMultiplierArg,
			int transpositionMinimumArg, int transpositionMaximumArg, IntegerSource transpositionSourceArg) {
		random = randomArg;
		motifCollection = motifCollectionArg;
		rhythmCollection = rhythmCollectionArg;
		improviserCollection = improviserCollectionArg;
		instrument = instrumentArg;
		volumeMinimum = volumeMinimumArg;
		volumeMaximum = volumeMaximumArg;
		volumeChangeCount = volumeChangeCountArg;
		emphasisVolumeMultiplier = emphasisVolumeMultiplierArg;
		transpositionMinimum = transpositionMinimumArg;
		transpositionMaximum = transpositionMaximumArg;
		transpositionSource = transpositionSourceArg;
	}
	
	public int chooseVolume(IntensityEnvelope intensityEnvelope, double timeFraction) {
		int volumeRange = volumeMaximum - volumeMinimum;
		return volumeMinimum + (int)(volumeRange*intensityEnvelope.getIntensityAtTime(timeFraction));
	}
}
