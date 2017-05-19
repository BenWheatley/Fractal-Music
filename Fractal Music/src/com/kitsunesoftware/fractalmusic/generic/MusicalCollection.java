package com.kitsunesoftware.fractalmusic.generic;

public abstract class MusicalCollection {
	
	/**
	 * Override this to return the correct class of object; example code should be something like:
	 * 
	 *<pre>public Motif chooseItemFromCollection() {
	 *    return (Motif)internalChooseItemFromCollection();
	 *}</pre>
	 */
	public abstract Object chooseItemFromCollection();

}
