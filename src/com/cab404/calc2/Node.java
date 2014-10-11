package com.cab404.calc2;

/**
 * @author cab404
 */
public abstract class Node {
	public void resolve(Calculation context, int index) {}

	public int priority() { return 0; }

}
