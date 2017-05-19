package com.kitsunesoftware.fractalmusic.generic;

import java.util.Random;
import java.util.Vector;

import com.kitsunesoftware.fractalmusic.probability_function.ProbabilityFunction;

public abstract class WeightedRandomCollection extends MusicalCollection {

	/** The seed for the random source */
	private long randomSeed;
	/** The random source */
	protected Random random;
	
	/** A collection of items with associated relative probabilities. */
	public Vector<ItemWithProbability> itemsWithProbabilities;
	
	protected ProbabilityFunction probabilityFunction;
	
	public WeightedRandomCollection() {
		random = new Random();
		probabilityFunction = new ExponentialDecayProbabilityFunction(1.3);
	}
	
	public void setSeed(long seed) {	randomSeed = seed; resetRandomSource();	}
	public void resetRandomSource() {	random.setSeed(randomSeed);	}
	
	/** Lists all the items in this collection, without revealing their probabilities. Default implementation caches result. */
	private Vector<Object> resultOfListAllItems;
	public Vector<Object> listAllItems() {
		if (resultOfListAllItems==null) {
			resultOfListAllItems = new Vector<Object>();
			for (ItemWithProbability item : itemsWithProbabilities) {
				resultOfListAllItems.add( item.item );
			}
		}
		return resultOfListAllItems;
	}
	
	/**
	 * @return	returns a weighted-randomly selected item from this collection;
	 * do not allow this to face users directly as it returns objects of class Object!
	 * Instead, override the chooseItemFromCollection method to return the correct class
	 */
	protected Object internalChooseItemFromCollection() {
		double totalProbability = 0;
		final int LOOP_MAX = itemsWithProbabilities.size();
		for (int i=0; i<LOOP_MAX; ++i) {
			ItemWithProbability mwp = itemsWithProbabilities.elementAt(i);
			totalProbability += mwp.probability;
		}
		double chosenValue = random.nextDouble() * totalProbability;
		for (int i=0; i<LOOP_MAX; ++i) {
			ItemWithProbability mwp = itemsWithProbabilities.elementAt(i);
			if (chosenValue<mwp.probability) {
				return mwp.item;
			}
			else {
				chosenValue -= mwp.probability;
			}
		}
		// Shouldn't get here, but I suppose rounding errors might lead to it, and in that case we want to choose the last index:
		return itemsWithProbabilities.lastElement().item;
	}
	
	public abstract void replaceAllItemsInList(Object ... newItems);
	
	public abstract Object buildItem();
	
	public void rebuildAllItems(int newItemCount) {
		resetRandomSource();
		itemsWithProbabilities = new Vector<ItemWithProbability>();
		for (int i=0; i<newItemCount; ++i) {
			ItemWithProbability item = new ItemWithProbability(buildItem(), probabilityFunction.probability(i));
			itemsWithProbabilities.add(item);
		}
	}
	
	public class ItemWithProbability {
		public Object item;
		public double probability;
		public ItemWithProbability(Object item_in, double probability_in) {
			item = item_in;
			probability = probability_in;
		}
	}
}
