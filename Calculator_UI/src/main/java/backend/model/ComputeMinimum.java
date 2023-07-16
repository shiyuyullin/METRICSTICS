package backend.model;

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
		final int first = 0;
		outputValue = event.getInputs().get(first);
		
		updateObservers(event);
	}

}
