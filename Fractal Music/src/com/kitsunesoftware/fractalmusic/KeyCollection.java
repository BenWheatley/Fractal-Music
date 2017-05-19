package com.kitsunesoftware.fractalmusic;

import com.kitsunesoftware.fractalmusic.generic.WeightedRandomCollection;
import com.kitsunesoftware.fractalmusic.probability_function.ProbabilityFunction;

public class KeyCollection extends WeightedRandomCollection {
	
	Key keyTypes[];
	
	public KeyCollection(ProbabilityFunction probabilityFunctionArg, Key ... keyTypesArg) {
		super();
		probabilityFunction = probabilityFunctionArg;
		keyTypes = keyTypesArg;
		rebuildAllItems(keyTypes.length);
	}
	
	public Key chooseItemFromCollection() {
		return (Key)internalChooseItemFromCollection();
	}
	
	public Key buildItem() {
		return keyTypes[itemsWithProbabilities.size()];
	}
	
	public void replaceAllItemsInList(Object ... newItems) {
		itemsWithProbabilities.removeAllElements();
		keyTypes = new Key[newItems.length];
		for (int i=0; i<newItems.length; ++i) {
			keyTypes[i] = (Key)(newItems[i]);
		}
		rebuildAllItems(keyTypes.length);
	}
}
