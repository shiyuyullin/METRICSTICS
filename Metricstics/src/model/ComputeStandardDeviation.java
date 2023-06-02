package model;

import helper.CustomMath;

/**
 * Computation carries the standard deviation.
 */
public class ComputeStandardDeviation extends ComputeObserver {
	/**
	 * Update the standard deviation value.
	 * Assumes a sorted input list.
	 */
	@Override
	public void update(Event event) {
		final double[] inputs = event.getInputs();
		final double   mean   = event.getMean()  ;
		//Mathematical definition.
		double sum = 0;
		for(double input : inputs) {
			final double deviation = input-mean;
			sum += deviation*deviation;
		}
		outputValue = CustomMath.getInstance().squareRoot(sum/inputs.length);
		
		this.event = event;
		updateObservers();
	}

}
