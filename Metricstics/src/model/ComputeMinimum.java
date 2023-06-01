package model;

/**
 * Computation carries the minimum.
 */
public class ComputeMinimum extends ComputeObserver {
	/**
	 * Update the minimum value.
	 * Assumes a sorted input list.
	 */
	@Override
	public void update(Event event) {
		outputValue = event.getInputs()[0];

		this.event = event;
		updateObservers();
	}

}
