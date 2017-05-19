package com.kitsunesoftware.fractalmusic.improvisers;

import com.kitsunesoftware.fractalmusic.generic.WeightedRandomCollection;

public class ImproviserCollection extends WeightedRandomCollection {
	
	private Improviser[] improvisers;
	private int srcArrayIndex;
	private int itemCount;
	
	public ImproviserCollection(Improviser ... improvisersArg) {
		improvisers = improvisersArg;
		replaceAllItemsInList((Object[])improvisersArg);
	}
	
	public Improviser chooseItemFromCollection() {
		return (Improviser)internalChooseItemFromCollection();
	}
	
	public void replaceAllItemsInList(Object... newItems) {
		improvisers = (Improviser[]) newItems;
		itemCount = improvisers.length;
		srcArrayIndex = 0;
		rebuildAllItems(itemCount);
	}
	
	public Improviser buildItem() {
		Improviser result = improvisers[srcArrayIndex%improvisers.length];
		srcArrayIndex++;
		return result;
	}

}
