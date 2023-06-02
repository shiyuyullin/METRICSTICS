package model;

import helper.CustomArrayList;
import helper.CustomMath;

/**
 * Computation carries the mode.
 */
public class ComputeMode extends ComputeObserver {
	/**
	 * Update the mode value.
	 * Assumes a sorted input list.
	 */
	@Override
	public void update(Event event) {
		outputArray = CustomArrayList.toPrimitiveDoubleArray(findMode(event.getInputs()));
		
		this.event = event;
		updateObservers();
	}
	
	private CustomArrayList<Double> findMode(double[] inputs) {
		final CustomMath math = CustomMath.getInstance();
		CustomArrayList<Double> modes = new CustomArrayList<Double>();
		int currentStreak = 0;
		int maxStreak = 0;
		double currentNumber;
		double previousNumber = inputs[inputs.length - 1];
		for(int currentIndex = 0; currentIndex < inputs.length; currentIndex++, previousNumber = currentNumber) {
			//alias
			currentNumber = inputs[currentIndex];
			
			//Check against the previous currentNumber to know whether the streak has ended.
//			if(currentNumber != previousNumber) {
//				currentStreak = 0;
//			}
//			currentStreak++;
			//Branchless alternative.
			final int isOnNextNumber = math.oneIfGreaterThanElseZero(currentNumber, previousNumber);
			//Reset streak count on next number.
			currentStreak *= 1^isOnNextNumber;
			currentStreak++;
			
			//Reset modes when not the most frequent.
			//Update the max streak count.
//			maxStreak = math.maximum(maxStreak, currentStreak);
			if(currentStreak > maxStreak) {
				 modes = new CustomArrayList<Double>();
				 maxStreak = currentStreak;
			}
			//Track tied modes.
			if(currentStreak == maxStreak) {
				modes.add(currentNumber);
			}
		}
		
		return modes;
	}

}
