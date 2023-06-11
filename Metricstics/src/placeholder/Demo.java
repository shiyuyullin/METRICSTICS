package placeholder;

import java.util.List;
import java.util.stream.Collectors;

import model.ComputeEmpty;
import model.ComputeMaximum;
import model.ComputeMean;
import model.ComputeMeanAbsoluteDeviation;
import model.ComputeMedian;
import model.ComputeMinimum;
import model.ComputeMode;
import model.ComputeObserver;
import model.ComputeStandardDeviation;
import model.Event;

/**
 * Demonstrates Compute cases.
 */
public class Demo {
	/**
	 * Displays statistical numbers based on hard-coded input.
	 * @param args unused
	 */
	public static void main(String[] args) {
		//Get the input. Pretend it gets sorted in ascending order.
		List<Double> inputs = List.of(123.45, 234.56, 234.56, 345.45, 345.67, 345.67);
		final Event inputEvent = new Event.EventBuilder().setInputs(inputs).build();
		
		//Initialize the modules.
		ComputeObserver head    = new ComputeEmpty();
		ComputeObserver minimum = new ComputeMinimum();
		ComputeObserver maximum = new ComputeMaximum();
		ComputeObserver mode    = new ComputeMode();
		ComputeObserver median  = new ComputeMedian();
		ComputeObserver mean    = new ComputeMean();
		ComputeObserver mad     = new ComputeMeanAbsoluteDeviation();
		ComputeObserver stdev   = new ComputeStandardDeviation();
		
		//Attach Observer chain.
		head.addObserver(minimum);
		head.addObserver(maximum);
		head.addObserver(mode   );
		head.addObserver(median );
		head.addObserver(mean   );
		mean.addObserver(mad    );
		mean.addObserver(stdev  );
		
		//Deliver input.
		head.update(inputEvent);
		
		//Retrieve/Display output.
		System.out.println("min :" + minimum.getOutputValue());
		System.out.println("max :" + maximum.getOutputValue());
		System.out.println("mode:" + mode.getOutputList().stream().map(String::valueOf).collect(Collectors.joining(", ")));
		System.out.println("med :" + median .getOutputValue());
		System.out.println("mean:" + mean   .getOutputValue());
		System.out.println("mad :" + mad    .getOutputValue());
		System.out.println("std :" + stdev  .getOutputValue());
	}
}
