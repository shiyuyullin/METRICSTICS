package helper;

/**
 * Miscellaneous helper functions related to math operations.
 */
public class CustomMath {
	private static CustomMath singleton = new CustomMath();
	
	private CustomMath() {
		
	}
	
	/**
	 * Singleton instance.
	 * @return customMath instance
	 */
	public static CustomMath getInstance() {
		return singleton;
	}

	/**
	 * Get the absolute value.
	 * @param a value
	 * @return a if a is positive, otherwise -a.
	 */
	public int absoluteValue(int a) {
		//(a>>30|1) is -1 when a is negative and +1 when a is positive.
		//To emphasize, the leftmost bit is the sign bit, and -1 in binary is 0xffffffff and 1 in binary is 0x00000001.
		return (a>>30|1) * a;
	}
	
	/**
	 * Get the absolute value.
	 * @param a value
	 * @return a if a is positive, otherwise -a.
	 */
	public double absoluteValue(double a) {
		//Mask out the sign bit.
		final long excludeSignBitMask = 0x7fffffffffffffffl;
		return Double.longBitsToDouble(Double.doubleToRawLongBits(a) & excludeSignBitMask);
	}
	
	/**
	 * Compare two doubles: give 1 if the first is greater than the second; else, give 0.
	 * Branchless.
	 * @param a first double
	 * @param b second double
	 * @return 1 if a>b, otherwise 0.
	 */
	public int oneIfGreaterThanElseZero(double a, double b) {
		return signBit(b-a);
	}
	/**
	 * Get the sign bit of a double.
	 * Branchless.
	 * @param a a double
	 * @return 1 if a is negative, otherwise 0.
	 */
	public int signBit(double a) {
		//binary representation's leftmost bit is 1 if negative else 0. 
		//unsigned bit shift the sign bit over to the rightmost bit.
		// -unsigned to always replace all bits except the rightmost with 0.
		// -the sign bit is shifted to the rightmost bit.
		// -thus, the result of the bit shift is 1 if negative prior else 0.
		//The difference of two numbers is negative if the minuend is greater else positive.
		return (int) (Double.doubleToLongBits(a)>>>(Long.SIZE-1));
	}
	
	/**
	 * Find the maximum of two doubles. 
	 * Branchless.
	 * @param a first double
	 * @param b second double
	 * @return the maximum of a and b
	 */
	public double maximum(double a, double b) {
		final long bitsOfA = Double.doubleToLongBits(a);
		final long bitsOfB = Double.doubleToLongBits(b);
		//The difference of two numbers is negative if the maximum is the minuend, otherwise the maximum is the subtrahend.
		//signed bit shift the sign bit -> bit mask is either 0xffffffffffffffff if negative or 0x0000000000000000 if positive.
		//Note that a^b^a = b and 0^a = a. So apply the aforementioned bit mask to a^b.
		return Double.longBitsToDouble(Double.doubleToLongBits(a-b)>>(Long.SIZE-1) & (bitsOfA^bitsOfB)^bitsOfA);
	}
	/**
	 * Find the maximum of two integers. 
	 * @param a first integer
	 * @param b second integer
	 * @return the maximum of a and b
	 */
	public int maximum(int a, int b) {
		//The difference of two numbers is negative if the maximum is the minuend, otherwise the maximum is the subtrahend.
		//signed bit shift the sign bit -> bit mask is either 0xffffffff if negative or 0x00000000 if positive.
		//Note that a^b^a = b and 0^a = a. So apply the aforementioned bit mask to a^b.
		return (a-b)>>(Integer.SIZE-1) & (a^b)^a;
	}
	
	/**
	 * Find an approximated square root.
	 * @param a a double
	 * @return the square root
	 */
	public double squareRoot(double a) {
		final double defaultAccuracyThreshold = Double.MIN_NORMAL;
		return squareRoot(a, defaultAccuracyThreshold);
	}
	/**
	 * Find an approximated square root. Up to a computational accuracy threshold to the squared value.
	 * (Since squared doubles aren't as precise, the threshold is not representative of significant figures.)
	 * @param a a double
	 * @param threshold accuracy threshold
	 * @return the square root
	 */
	public double squareRoot(double a, double threshold) {
		//Solve numerically by following Babylonian method/Heron's method, equivalent to Newton's method.
		//Set an initial guess.
		double guess = a/2;
		double previousGuess=a;
		//Refine the guess iteratively until a threshold of accuracy is met.
		// Or until the guess loops on itself.
		while(absoluteValue(a - guess*guess) >= threshold && guess != previousGuess) {
			previousGuess = guess;
			guess = (guess+a/guess)/2;
		}
		return guess;
	}
}
