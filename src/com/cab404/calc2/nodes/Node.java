package com.cab404.calc2.nodes;

import com.cab404.calc2.base.Calculation;

/**
 * @author cab404
 */
public abstract class Node implements Comparable<Node>, Cloneable {
	/**
	 * Returns new node, or null, if there's nothing added.
	 */
	public Node resolve(Calculation context, int index) {return null;}

	public int priority() { return 0; }

	@Override public int compareTo(Node node) {
		return priority() - node.priority();
	}

	public abstract Object clone()
	throws CloneNotSupportedException;

}
