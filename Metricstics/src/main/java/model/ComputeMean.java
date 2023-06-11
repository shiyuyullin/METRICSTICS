package model;

import java.util.List;

/**
 * Computation carries the mean.
 */
public class ComputeMean extends ComputeObserver {
	/**
	 * Update the mean value.
	 * Assumes a sorted input list.
	 */
	@Override
	public void update(Event event) {
		final List<Double> inputs = event.getInputs();
		final double zero = 0;
		final double sum = inputs.stream()
				.reduce(zero, Double::sum);
		outputValue = sum/inputs.size();
		
		final Event meanEvent = new Event.EventBuilder(event).setMean(outputValue).build();
		updateObservers(meanEvent);
	}
}
