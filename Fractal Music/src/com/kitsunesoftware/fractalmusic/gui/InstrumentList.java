package com.kitsunesoftware.fractalmusic.gui;

import javax.swing.JComboBox;

import com.kitsunesoftware.fractalmusic.FractalMusicGlobals;

public class InstrumentList extends JComboBox {
	
	private static final long serialVersionUID = -1516451723853744757L;
	
	public InstrumentList(String defaultInstrument) {
		super();
		addItem("None");
		FractalMusicGlobals.acquireCompleteListOfInstruments();
		String instrument_strings[] = new String[FractalMusicGlobals.instruments.length];
		for (int i=0; i<FractalMusicGlobals.instruments.length; ++i) instrument_strings[i] = FractalMusicGlobals.instruments[i].getName();
		java.util.Arrays.sort(instrument_strings);
		for (int i=0; i<FractalMusicGlobals.instruments.length; ++i) addItem( instrument_strings[i] );
		setSelectedItem(defaultInstrument);
		setMaximumRowCount(20);
	}
}
