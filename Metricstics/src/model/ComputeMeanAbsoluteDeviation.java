package model;

import java.util.List;

import helper.CustomMath;

/**
 * Computation carries the mean absolute deviation.
 */
public class ComputeMeanAbsoluteDeviation extends ComputeObserver {
	/**
	 * Update the mean absolute deviation value.
	 * Assumes a sorted input list.
	 */
	@Override
	public void update(Event event) {
		final List<Double> inputs = event.getInputs();
		final double       mean   = event.getMean()  ;
		final double zero = 0;
		final double sum = inputs.stream()
				.map(input -> input-mean)
				.map(CustomMath.getInstance()::absoluteValue)
				.reduce(zero, Double::sum);
		outputValue = sum/inputs.size();
		
		updateObservers(event);
	}
}
