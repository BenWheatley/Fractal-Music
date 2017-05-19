package com.kitsunesoftware.fractalmusic.motif;

import com.kitsunesoftware.fractalmusic.generic.WeightedRandomCollection;

public class MotifCollection extends WeightedRandomCollection {
	
	Motif motifs[];
	private int srcArrayIndex;
	private int itemCount;
	
	public MotifCollection(Motif ... motifsArg) {
		super();
		replaceAllItemsInList((Object[])motifsArg);
	}
	
	public Motif chooseItemFromCollection() {
		return (Motif)internalChooseItemFromCollection();
	}
	
	public void replaceAllItemsInList(Object... newItems) {
		motifs = (Motif[]) newItems;
		itemCount = motifs.length;
		srcArrayIndex = 0;
		rebuildAllItems(itemCount);
	}
	
	public Motif buildItem() {
		Motif result = motifs[srcArrayIndex%motifs.length];
		srcArrayIndex++;
		return result;
	}
}
