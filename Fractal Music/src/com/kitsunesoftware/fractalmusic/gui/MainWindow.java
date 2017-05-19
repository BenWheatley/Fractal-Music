package com.kitsunesoftware.fractalmusic.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.ItemSelectable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.JTableHeader;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import com.kitsunesoftware.fractalmusic.FractalMusic;
import com.kitsunesoftware.fractalmusic.Key;
import com.kitsunesoftware.fractalmusic.KeyCollection;
import com.kitsunesoftware.fractalmusic.MidiPlayer;
import com.kitsunesoftware.fractalmusic.Voice;
import com.kitsunesoftware.fractalmusic.generic.WeightedRandomCollection;
import com.kitsunesoftware.fractalmusic.motif.Motif;

import java.util.Random;
import java.util.Vector;

public class MainWindow extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = -633214777855531416L;

	static MainWindow myself;
	
	MidiPlayer midiPlayer;
	FractalMusic fractalMusic;
	
	JTabbedPane voicesTabbedPane;
	
	public static void main(String args[]) {
		myself = new MainWindow();
	}
	
	public MainWindow() {
		fractalMusic = new FractalMusic();
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(1, 4));
		buttons.add(new PlayButton());
		buttons.add(new LoadButton());
		buttons.add(new SaveButton());
		buttons.add(new ExportButton());
		
		add(buttons, BorderLayout.PAGE_START);
		
		JPanel contentPanel = new JPanel();
		JScrollPane scrollPane = new JScrollPane(contentPanel);
		final int ROWS = 0, COLUMNS = 2;
		contentPanel.setLayout(new GridLayout(ROWS, COLUMNS));
		addSliderField(contentPanel, "Minimum tempo (BPM):", 0, 500, fractalMusic.tempoMinimum, new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				fractalMusic.tempoMinimum = ((JSlider)e.getSource()).getValue();
			}	});
		addSliderField(contentPanel, "Maximum tempo (BPM):", 0, 500, fractalMusic.tempoMaximum, new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				fractalMusic.tempoMaximum = ((JSlider)e.getSource()).getValue();
			}	});
		addTextField(contentPanel, "Number of tempo changes:", ""+fractalMusic.tempoChangeCount, new KSTextFieldListener() {
			public void setValue(String newValue) {
				fractalMusic.tempoChangeCount = Integer.parseInt(newValue);
			}	});
//		addVariableLengthBeat(contentPanel, "Bar stress pattern:", fractalMusic.barStressPattern, new KSVariableLengthBeatListener() {
//			public void setValue(boolean newValue[]) {
//				fractalMusic.barStressPattern = newValue;
//			}	});

		// TODO: Key collection
		addTextField(contentPanel, "Key change count:", ""+fractalMusic.keyChangeCount, new KSTextFieldListener() {
			public void setValue(String newValue) {
				fractalMusic.keyChangeCount = Integer.parseInt(newValue);
			}	});
		Vector<Key> keys = new Vector<Key>();
		keys.add(Key.C_MAJOR);
		addTextField(contentPanel, "Keys:", arrayToText(fractalMusic.keys), new KSTextFieldListener() {
			public void setValue(String newValue) {
				fractalMusic.keys.replaceAllItemsInList((Object[])keysArrayFromText(newValue));
			}	});
		
		// TODO: intensity curve (anything else?)
		addTextField(contentPanel, "Length in bars:", ""+fractalMusic.lengthInBars, new KSTextFieldListener() {
			public void setValue(String newValue) {
				fractalMusic.lengthInBars = Integer.parseInt(newValue);
			}	});
		
		add(scrollPane, BorderLayout.CENTER);

		addVoices(this);
		
		pack();
		
		setVisible(true);
	}
	
	private void addVoices(JFrame container) {
		voicesTabbedPane = new JTabbedPane();
		// For each voice
		for (Voice voice : fractalMusic.voices) {
			int tabIndex = voicesTabbedPane.getTabCount();
			voicesTabbedPane.addTab(voice.instrument, voiceJComponentWithVoice(voice, tabIndex));
		}
		voicesTabbedPane.addTab("New voice", newVoiceJComponent());
		
		container.add(voicesTabbedPane, BorderLayout.PAGE_END);
	}
	
	public JComponent voiceJComponentWithVoice(final Voice voice, final int tabIndex) {
		// Setup and layout
		JPanel content = new JPanel();
		JScrollPane scrollPane = new JScrollPane(content);
		final int ROWS = 0, COLUMNS = 2;
		content.setLayout(new GridLayout(ROWS, COLUMNS));
		// Actual input fields
		
		// Instrument
		addInstrumentPopup(content, "Instrument:", voice.instrument, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				voice.instrument = (String) ((InstrumentList)e.getSource()).getSelectedItem();
				voicesTabbedPane.setTitleAt(tabIndex, voice.instrument);
			}	});
		
		addMotifCollection(voice, content);
		// TODO: Beat pattern collection
		
		// Volume
		addSliderField(content, "Maximum volume:", 0, 127, 16, voice.volumeMaximum, new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				voice.volumeMaximum = ((JSlider)e.getSource()).getValue();
			}	});
		addSliderField(content, "Minimum volume:", 0, 127, 16, voice.volumeMinimum, new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				voice.volumeMinimum = ((JSlider)e.getSource()).getValue();
			}	});
		addSliderField(content, "Emphasis volume (%):", 100, 200, 10, (int)(voice.emphasisVolumeMultiplier*100), new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				voice.emphasisVolumeMultiplier = 0.01 * ((JSlider)e.getSource()).getValue();
			}	});
		addTextField(content, "Volume change count:", ""+voice.volumeChangeCount, new KSTextFieldListener() {
			public void setValue(String newValue) {
				voice.volumeChangeCount = Integer.parseInt(newValue);
			}	});
		
		// TODO: Transposition change style (Brownian, white noise, Perlin, etc.)
		// TODO: Limit transposition to valid MIDI range
		addSliderField(content, "Minimum transposition:", -60, 60, 12, voice.transpositionMinimum, new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				voice.transpositionMinimum = ((JSlider)e.getSource()).getValue();
			}	});
		addSliderField(content, "Maximum transposition:", -60, 60, 12, voice.transpositionMaximum, new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				voice.transpositionMaximum = ((JSlider)e.getSource()).getValue();
			}	});
		
		// Return result
		return scrollPane;
	}
	
	private void addMotifCollection(Voice voice, JPanel content) {
		String[] columnNames = {"Pattern", "Probability"};
		Vector<WeightedRandomCollection.ItemWithProbability> itemsWithProbabilities = voice.motifCollection.itemsWithProbabilities;
		Object[][] data = new Object[itemsWithProbabilities.size()][];
		for (int i=0; i<itemsWithProbabilities.size(); ++i) {
			WeightedRandomCollection.ItemWithProbability itemWithProbability = itemsWithProbabilities.elementAt(i);
			Motif motif = (Motif)(itemWithProbability.item);
			Double probability = itemWithProbability.probability;
			data[i] = new Object[]{motif, probability};
		}
		JTable table = new JTable(data, columnNames);
		content.add(new JLabel("Motif collection:", JLabel.TRAILING));
		content.add(table);
	}
	
	private String arrayToText(KeyCollection keys) {
		String result = null;
		Vector<Object> keysVector = keys.listAllItems();
		for (Object o : keysVector) {
			Key key = (Key)o;
			if (result==null) result = "";
			else result += ", ";
			result += key.name;
		}
		return result;
	}
	
	private static Key[] keysArrayFromText(String newValue) {
		String splitStrings[] = newValue.split(",\\W*");
		Key result[] = new Key[splitStrings.length];
		for (int i=0; i<splitStrings.length; ++i) {
			result[i] = Key.keyByName(splitStrings[i]);
		}
		return result;
	}
	
	public JComponent newVoiceJComponent() {
		JPanel result = new JPanel();
		JButton cmdCreate = new JButton("Create new voice");
		cmdCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Voice voice = fractalMusic.defaultNewVoice();
				fractalMusic.voices.add(voice);
				int index = voicesTabbedPane.getTabCount()-1;
				voicesTabbedPane.insertTab(voice.instrument, null, voiceJComponentWithVoice(voice, index), null, index);
			}
		});
		result.add(cmdCreate);
		return result;
	}
	
	public abstract class KSTextFieldListener implements DocumentListener {
		public abstract void setValue(String newValue);
		private void commonEventHandler(DocumentEvent e) {
			Document source = e.getDocument();
			try {
				String stringVal = source.getText(0, source.getLength());
				setValue(stringVal);
			} catch (BadLocationException badLocation) {
				badLocation.printStackTrace();
			} catch (NumberFormatException badNumberString) {
				System.err.println(badNumberString);
			}
		}
		public void changedUpdate(DocumentEvent e)	{	commonEventHandler(e);	}
		public void insertUpdate(DocumentEvent e)	{	commonEventHandler(e);	}
		public void removeUpdate(DocumentEvent e)	{	commonEventHandler(e);	}
	}
	
	public void addTextField(JPanel p, String labelString, String defaultValue, KSTextFieldListener textFieldListener) {
		p.add(new JLabel(labelString, JLabel.TRAILING));
		JTextField text = new JTextField(defaultValue);
		text.getDocument().addDocumentListener(textFieldListener);
		p.add(text);
	}
	
	public void addSliderField(JPanel p, String labelString, int minimumValue, int maximumValue, int defaultValue, ChangeListener changeListener) {
		int majorTickSpacing = 50;
		addSliderField(p, labelString, minimumValue, maximumValue, majorTickSpacing, defaultValue, changeListener);
	}
	
	public void addSliderField(JPanel p, String labelString, int minimumValue, int maximumValue,
			int majorTickSpacing, int defaultValue, ChangeListener changeListener) {
		p.add(new JLabel(labelString, JLabel.TRAILING));
		JSlider slider = new JSlider(minimumValue, maximumValue, defaultValue);
		slider.setMajorTickSpacing(majorTickSpacing);
		slider.setPaintLabels(true);
		slider.setPaintTicks(true);
		slider.addChangeListener(changeListener);
		p.add(slider);
	}
	
	public void addInstrumentPopup(JPanel p, String labelString, String defaultValue, ActionListener actionListener) {
		p.add(new JLabel(labelString, JLabel.TRAILING));
		InstrumentList instrumentList = new InstrumentList(defaultValue);
		instrumentList.addActionListener(actionListener);
		p.add(instrumentList);
	}
	
	public void actionPerformed(ActionEvent arg0) {
		
	}
	
	public void buildAndPlayMusic() {
		if (midiPlayer!=null) {
			midiPlayer.stop();
		}
		try {
			midiPlayer = new MidiPlayer( fractalMusic.toMIDI() );
		} catch (MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (midiPlayer!=null) midiPlayer.play();
	}
}