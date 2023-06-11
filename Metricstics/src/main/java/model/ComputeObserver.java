package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Observer pattern. Propagate changes through a tree via a chain of update calls.
 * Can hold a value and list of values as output of a computation.
 */
public abstract class ComputeObserver {
	private final List<ComputeObserver> observers;
	protected double outputValue;
	protected List<Double> outputList;
	
	public ComputeObserver() {
		observers = new ArrayList<ComputeObserver>();
	}
	
	/**
	 * Add to list of observers.
	 * @param observer an observer to add
	 */
	public void addObserver(ComputeObserver observer) {
		observers.add(observer);
	}
	
	/**
	 * Update all observers in list.
	 * @param event event to propagate
	 */
	public void updateObservers(Event event) {
		observers.forEach(observer -> observer.update(event));
	}
	
	/**
	 * Get the associated output value to this computer.
	 * @return a double as output
	 */
	public double getOutputValue() {
		return outputValue;
	}
	
	/**
	 * Get the associated output list to this computer.
	 * @return an double list as output
	 */
	public List<Double> getOutputList() {
		return outputList;
	}
	
	/**
	 * Get updated by an event. Subclass will decide what to do with the event.
	 * @param event updating event
	 */
	public abstract void update(Event event);
}
