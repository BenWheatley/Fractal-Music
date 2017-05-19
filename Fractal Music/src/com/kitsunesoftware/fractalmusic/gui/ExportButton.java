package com.kitsunesoftware.fractalmusic.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.midi.MidiSystem;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


public class ExportButton extends JButton implements ActionListener {
	
	private static final long serialVersionUID = 5822594633885712209L;

	public ExportButton() {
		super("Export to MIDI");
		addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser();
		fc.setSelectedFile(new File("/Users/ben_wheatley/Desktop/Fractal Music "+System.currentTimeMillis()+".mid"));
		fc.setFileFilter(new MidiFileFilter());
		int response = fc.showSaveDialog(MainWindow.myself);
		if (response==JFileChooser.APPROVE_OPTION) {
			try {
				int midiFileTypes[] = MidiSystem.getMidiFileTypes(MainWindow.myself.midiPlayer.sequence);
				MidiSystem.write(
						MainWindow.myself.midiPlayer.sequence,
						midiFileTypes[0],
						fc.getSelectedFile());
			} catch (IOException exception) {
				JOptionPane.showMessageDialog(MainWindow.myself, "Error: "+exception);
			}
		}
	}
}
	