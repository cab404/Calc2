package com.cab404.calc2.nodes;

import com.cab404.calc2.base.Calculation;

/**
 * @author cab404
 */
public abstract class Node implements Comparable<Node> {
	/**
	 * Returns new node, or null, if there's no such.
	 */
	public Node resolve(Calculation context, int index) {return null;}

	public int priority() { return 0; }

	@Override public int compareTo(Node node) {
		return priority() - node.priority();
	}

}
