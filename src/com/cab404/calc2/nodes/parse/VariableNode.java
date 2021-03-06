package com.cab404.calc2.nodes.parse;

import com.cab404.calc2.base.Calculation;
import com.cab404.calc2.nodes.Era;
import com.cab404.calc2.nodes.Node;

/**
 * @author cab404
 */
public class VariableNode extends Node {
	private Node replacement = this;

	@Override public Node resolve(Calculation context, int index) {
		if (replacement == this)
			throw new RuntimeException("Node is not defined yet!");
		return context.set(index, replacement);
	}

	public void set(Node node) {
		if (node instanceof VariableNode)
			replacement = ((VariableNode) node).replacement;
		else
			replacement = node;
	}


	public Node get() {
		return replacement;
	}

	@Override public int priority() {
		return Era.VARIABLE_ERA + 100;
	}

	@Override public String toString() {
		return (replacement != this ? "var(" + replacement + ")" : "not-resolved");
	}

	@Override public Object clone()
	throws CloneNotSupportedException {
		return this;
	}
}
