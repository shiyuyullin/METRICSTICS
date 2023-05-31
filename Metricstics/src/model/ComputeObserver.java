package model;

import helper.CustomArrayList;

/**
 * Observer pattern. Propagate changes through a tree via a chain of update calls.
 */
public abstract class ComputeObserver {
	private final CustomArrayList<ComputeObserver> observers;
	protected Event event;
	protected double outputValue;
	
	public ComputeObserver() {
		observers = new CustomArrayList<ComputeObserver>();
	}
	
	/**
	 * Add to list of observers.
	 * @param observer an observer to add
	 */
	public void addOberserver(ComputeObserver observer) {
		observers.add(observer);
	}
	
	/**
	 * Update all observers in list.
	 */
	public void updateObservers() {
		for(int i = observers.getSize() - 1; i >= 0; i--) {
			observers.get(i).update(event);
		}
	}
	
	/**
	 * Get the associate output value to this computer.
	 * @return a double as output
	 */
	public double getOutputValue() {
		return outputValue;
	}
	
	/**
	 * Get updated by an event. Subclass will decide what to do with the event.
	 * @param event updating event
	 */
	public abstract void update(Event event);
}
