package model;

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
		final double[] inputs = event.getInputs();
		//integer divide to floor automatically.
		final int firstMiddleIndex = (inputs.length-1)/2;
		final int secondMiddleIndex = inputs.length/2;
		//pick mean of both middles. On odd case, pick mean of two of itself.
		outputValue = (inputs[firstMiddleIndex]+inputs[secondMiddleIndex])/2;
		
		this.event = event;
		updateObservers();
	}

}
