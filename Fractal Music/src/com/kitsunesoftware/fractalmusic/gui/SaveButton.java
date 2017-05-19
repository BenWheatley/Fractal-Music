package com.kitsunesoftware.fractalmusic.gui;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class SaveButton extends JButton implements ActionListener {
	public SaveButton() {
		super("Save");
		addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
//		JFileChooser fc = new JFileChooser(MainWindow.last_file);
//		fc.setFileFilter(new KitsuneMarkovMusicFile());
//		int response = fc.showSaveDialog(MainWindow.myself);
//		if (response==JFileChooser.APPROVE_OPTION) {
//			if (fc.getSelectedFile()!=null) MainWindow.last_file=fc.getSelectedFile();
//			try {
//				MarkovMidiFile.save(fc.getSelectedFile(), MainWindow.myself.markov_midi);
//			} catch (IOException exception) {
//				JOptionPane.showMessageDialog(MainWindow.myself, "Error: "+exception);
//			} 
//		}
//		if (MainWindow.last_file!=null) {
//			MainWindow.setTitle();
//		}
	}
	
}
