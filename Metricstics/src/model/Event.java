package model;

import java.util.List;

/**
 * The Event class carries details that observers send each other as attributes.
 */
public class Event {
	private final List<Double> inputs;
	private final double   mean;
	private Event(EventBuilder eventBuilder) {
		inputs = eventBuilder.inputs;
		mean   = eventBuilder.mean  ;
	}
	
	/**
	 * Gives the input associated to this event.
	 * @return a double List
	 */
	public List<Double> getInputs() {
		return inputs;
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
	 * Alternative is having a constructor instead of a Builder, but that's not scalable, since the number of arguments could change.
	 */
	public static class EventBuilder {
		private List<Double> inputs;
		private double mean;
		
		/**
		 * Default constructor.
		 */
		public EventBuilder() {
			
		}
		/**
		 * Copy constructor.
		 * @param event event to copy from
		 */
		public EventBuilder(Event event) {
			inputs = event.inputs;
			mean   = event.mean  ;
		}
		
		/**
		 * Add additional inputs to the end. Returns itself for method chaining.
		 * @param input a double
		 * @return this eventBuilder
		 */
		public EventBuilder addInput(double input) {
			inputs.add(input);
			return this;
		}
		
		/**
		 * Overwrites the inputs to the given array. Returns itself for method chaining.
		 * @param inputs array of doubles
		 * @return this eventBuilder
		 */
		public EventBuilder setInputs(List<Double> inputs) {
			this.inputs = inputs;
			return this;
		}

		/**
		 * Sets the mean value of the event. Returns itself for method chaining.
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
