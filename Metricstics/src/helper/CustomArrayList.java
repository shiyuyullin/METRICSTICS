package helper;

/**
 * Tries to replicate an ArrayList (Specification limits its use).
 * @param <E> class of element of list
 */
public class CustomArrayList<E> {
	private E[] list;
	private int size;
	/**
	 * Default Constructor.
	 * Default list size of 10.
	 */
	public CustomArrayList() {
		this(10);
	}
	/**
	 * Parameterized Constructor, sets the initial capacity of the list.
	 * @param initialCapacity an int
	 */
	@SuppressWarnings("unchecked")
	public CustomArrayList(int initialCapacity) {
		list = (E[]) new Object[initialCapacity];
		size = 0;
	}
	
	/**
	 * Add an element to the list.
	 * @param element element to add
	 */
	public void add(E element) {
		//expand the capacity when full.
		if(size >= list.length) {
			expandCapacity();
		}
		list[size++] = element;
	}
	
	/**
	 * Get the size of the list, the number of elements in the list.
	 * @return an int
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * Increase the size of the internal array.
	 */
	private void expandCapacity() {
		//create bigger array.
		@SuppressWarnings("unchecked")
		final E[] newList = (E[]) new Object[list.length*2];
		//transfer elements from old to new.
		for(int i = 0; i < list.length; i++) {
			newList[i] = list[i];
		}
		//overwrite the old.
		list = newList;
	}
}
