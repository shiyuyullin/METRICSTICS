package backend.model;

import java.util.ArrayList;
import java.util.List;

import backend.helper.CustomMath;

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
		outputList = findMode(event.getInputs());
		
		updateObservers(event);
	}
	
	private List<Double> findMode(List<Double> inputs) {
		final CustomMath math = CustomMath.getInstance();
		List<Double> modes = new ArrayList<Double>();
		int currentStreak = 0;
		int maxStreak = 0;
		double previousNumber = 0;	//initialize to any value. just make the compiler happy.
		for(double currentNumber : inputs) {
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
				 modes = new ArrayList<Double>();
				 maxStreak = currentStreak;
			}
			//Track tied modes.
			if(currentStreak == maxStreak) {
				modes.add(currentNumber);
			}
			
			previousNumber = currentNumber;
		}
		
		return modes;
	}
}
