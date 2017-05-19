package com.kitsunesoftware.fractalmusic.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class PlayButton extends JButton implements ActionListener {
	private static final long serialVersionUID = -9141498411392832307L;

	public PlayButton() {
		super("Play");
		addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		MainWindow.myself.buildAndPlayMusic();
	}

}
