package com.kitsunesoftware.fractalmusic.motif;

import java.util.HashMap;
import java.util.Map;

public class MotifCollectionWithForm extends MotifCollection {
	
	int form[];
	int currentFormIndex;
	
	public MotifCollectionWithForm(String formArg, Motif ... motifsArg) {
		motifs = motifsArg;
		setFormWithString(formArg);
		currentFormIndex = 0;
	}
	
	private void setFormWithString(String formArg) {
		Map<Character, Integer> map = new HashMap<Character, Integer>();
		String formElements[] = formArg.split("\\W");
		int value = 0;
		form = new int[formArg.length()];
		for (int i=0; i<formElements.length; ++i) {
			Character charAtIndex = formElements[i].charAt(0);
			if ( ! map.containsKey(charAtIndex)) {
				map.put(charAtIndex, value);
				value++;
			}
			int resultValue = map.get(charAtIndex);
			form[i] = resultValue;
		}
	}
	
	public Motif chooseItemFromCollection() {
		Motif result = motifs[form[currentFormIndex]];
		currentFormIndex++;
		currentFormIndex %= form.length;
		return result;
	}
}