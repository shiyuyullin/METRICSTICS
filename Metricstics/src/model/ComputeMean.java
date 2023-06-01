package model;

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
		final double[] inputs = event.getInputs();
		double sum = 0;
		for(double input : inputs) {
			sum += input;
		}
		outputValue = sum/inputs.length;
		
		this.event = new Event.EventBuilder(event).setMean(outputValue).build();
		updateObservers();
	}

}
