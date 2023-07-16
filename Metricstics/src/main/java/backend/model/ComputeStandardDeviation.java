package backend.model;

import java.util.List;

import backend.helper.CustomMath;

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
		final List<Double> inputs = event.getInputs();
		final double       mean   = event.getMean();
		//Mathematical definition.
		final double zero = 0;
		final double sumOfSquaredDeviation = inputs.stream()
				.map(input -> input - mean)
				.map(deviation -> deviation * deviation)
				.reduce(zero, Double::sum);
		outputValue = CustomMath.getInstance().squareRoot(sumOfSquaredDeviation/inputs.size());
		
		updateObservers(event);
	}
}
