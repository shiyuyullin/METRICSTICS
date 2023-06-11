package model;

import java.util.List;

/**
 * Computation carries the median.
 */
public class ComputeMedian extends ComputeObserver {
	/**
	 * Update the median value.
	 * Assumes a sorted input list.
	 */
	@Override
	public void update(Event event) {
		final List<Double> inputs = event.getInputs();
		final int inputSize = inputs.size();
		//integer divide to floor automatically.
		final int firstMiddleIndex = (inputSize-1)/2;
		final int secondMiddleIndex = inputSize/2;
		//pick mean of both middles. On odd case, pick mean of two of itself.
		outputValue = (inputs.get(firstMiddleIndex)+inputs.get(secondMiddleIndex))/2;
		
		updateObservers(event);
	}

}
