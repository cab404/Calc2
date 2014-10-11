package com.cab404.calc2;

import java.util.Comparator;

/**
 * @author cab404
 */
public class NodeComparator implements Comparator<Node> {
	public static final NodeComparator INSTANCE = new NodeComparator();

	@Override public int compare(Node n1, Node n2) {
		return n1.priority() - n2.priority();
	}
}
