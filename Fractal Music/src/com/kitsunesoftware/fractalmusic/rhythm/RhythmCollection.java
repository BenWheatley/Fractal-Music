package com.kitsunesoftware.fractalmusic.rhythm;

import com.kitsunesoftware.fractalmusic.generic.WeightedRandomCollection;

public class RhythmCollection extends WeightedRandomCollection {
	
	Rhythm rhythms[];
	private int srcArrayIndex;
	private int itemCount;
	
	public RhythmCollection(Rhythm ... rhythmsArg) {
		super();
		replaceAllItemsInList((Object[])rhythmsArg);
	}
	
	public Rhythm chooseItemFromCollection() {
		return (Rhythm)internalChooseItemFromCollection();
	}
	
	public void replaceAllItemsInList(Object... newItems) {
		rhythms = (Rhythm[]) newItems;
		itemCount = rhythms.length;
		srcArrayIndex = 0;
		rebuildAllItems(itemCount);
	}
	
	public Rhythm buildItem() {
		Rhythm result = rhythms[srcArrayIndex%rhythms.length];
		srcArrayIndex++;
		return result;
	}
	
}
