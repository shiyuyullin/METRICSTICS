package model;

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
		final double[] inputs = event.getInputs();
		final int lastIndex = inputs.length - 1;
		outputValue = inputs[lastIndex];
		
		this.event = event;
		updateObservers();
	}

}
