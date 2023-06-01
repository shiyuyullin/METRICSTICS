package model;

/**
 * The Event class carries details that observers send each other as attributes.
 */
public class Event {
	private final double[] inputs;
	private final double   mean;
	private Event(EventBuilder eventBuilder) {
		inputs = eventBuilder.inputs;
		mean   = eventBuilder.mean  ;
	}
	
	/**
	 * Gives the input associated to this event.
	 * @return a double array
	 */
	public double[] getInputs() {
		return inputs.clone();
	}
	
	/**
	 * Gives the mean associated to this event
	 * @return a double
	 */
	public double getMean() {
		return mean;
	}
	
	/**
	 * Builder pattern for Event. 
	 * Allows attributes of Event to be read-only once finalized, but modifiable before that.
	 * Alternative is having a constructor, but that's not scalable, since the number of arguments could change.
	 */
	public static class EventBuilder {
		private double[] inputs;
		private double mean;
		private int inputCount;
		
		/**
		 * Set (or reset) the expected input size.
		 * @param size internal size of input array
		 * @return this eventBuilder
		 */
		public EventBuilder resetInputSize(int size) {
			inputs = new double[size];
			inputCount = 0;
			return this;
		}
		
		/**
		 * Add additional inputs to the end. Returns itself for method chaining.
		 * MUST call resetInputSize() first (to create the array).
		 * @param input a double
		 * @return this eventBuilder
		 */
		public EventBuilder addInput(double input) {
			inputs[inputCount++] = input;
			return this;
		}
		
		/**
		 * Overwrites the inputs to the given array.
		 * @param inputs array of doubles
		 * @return this eventBuilder
		 */
		public EventBuilder setInput(double[] inputs) {
			this.inputs = inputs;
			return this;
		}

		/**
		 * Sets the mean value of the event.
		 * @param mean a double
		 * @return this eventBuilder
		 */
		public EventBuilder setMean(double mean) {
			this.mean = mean;
			return this;
		}
		
		/**
		 * Builds the event.
		 * @return an event with the same, relevant attributes as this.
		 */
		public Event build() {
			return new Event(this);
		}
	}
}
