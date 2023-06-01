package model;

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
		final double[] inputs = event.getInputs();
		final double   mean   = event.getMean()  ;
		double sum = 0;
		for(double input : inputs) {
			sum += CustomMath.getInstance().absoluteValue(input-mean);
		}
		outputValue = sum/inputs.length;
		
		this.event = event;
		updateObservers();
	}

}
