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
		//add element and increment size.
		list[size++] = element;
	}

	/**
	 * Get all elements in list as array.
	 * @return array of elements
	 */
	public E[] getAll() {
		//create sized array.
		@SuppressWarnings("unchecked")
		final E[] newList = (E[]) new Object[size];
		//transfer elements.
		for(int i = 0; i < size; i++) {
			newList[i] = list[i];
		}
		return newList;
	}
	/**
	 * Get element at given index
	 * @param index int for index to retrieve
	 * @return an element
	 */
	public E get(int index) {
		return list[index];
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
	
	/**
	 * Convert a CustomArrayList\<Double\> into a double[].
	 * @param customArrayDouble CustomArrayList\<Double\> to convert
	 * @return a double[] with the same values as passed in the argument
	 */
	public static double[] toPrimitiveDoubleArray(CustomArrayList<Double> customArrayDouble) {
		final Object[] innerArray = customArrayDouble.getAll();
		final double[] convertedArray = new double[customArrayDouble.size];
		int valuesLeft = convertedArray.length;
		//copy over all elements.
		while(valuesLeft-- > 0) {
			convertedArray[valuesLeft] = (double) innerArray[valuesLeft];
		}
		
		return convertedArray;
	}
}
