package com.kitsunesoftware.fractalmusic.gui;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class MidiFileFilter extends FileFilter {
	
	public boolean accept(File f) {
		if (f.getName().endsWith(".mid")) return true;
		if (f.getName().endsWith(".midi")) return true;
		if (f.isDirectory()) return true;
		return false;
	}
	
	public String getDescription() {
		return "Midi music files";
	}

}
