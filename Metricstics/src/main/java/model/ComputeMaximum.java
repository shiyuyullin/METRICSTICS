package model;

import java.util.List;

/**
 * Computation carries the maximum.
 */
public class ComputeMaximum extends ComputeObserver {
	/**
	 * Update the maximum value.
	 * Assumes a sorted input list.
	 */
	@Override
	public void update(Event event) {
		final List<Double> inputs = event.getInputs();
		final int lastIndex = inputs.size() - 1;
		outputValue = inputs.get(lastIndex);
		
		updateObservers(event);
	}
}
