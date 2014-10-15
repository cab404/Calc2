package com.cab404.calc2.plugins;

import com.cab404.calc2.nodes.Node;

/**
 * @author cab404
 */
public abstract class NodeForNameProvider implements Comparable<NodeForNameProvider> {
	public abstract Node nodeForName(String name);
	public abstract int priority();
	public abstract NodeForNameProvider nested();

	@Override public int compareTo(NodeForNameProvider nodeForNameProvider) {
		return priority() - nodeForNameProvider.priority();
	}
}
