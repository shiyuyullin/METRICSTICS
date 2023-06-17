package model;

/**
 * Congregates multiple observers under one. Does nothing else.
 * Should be used as a root, or not at all.
 */
public class ComputeEmpty extends ComputeObserver {
	/**
	 * Propagate event to further observers.
	 */
	@Override
	public void update(Event event) {
		updateObservers(event);
	}
}
