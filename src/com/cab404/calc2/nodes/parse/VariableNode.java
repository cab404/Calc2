package com.cab404.calc2.nodes.parse;

import com.cab404.calc2.base.Calculation;
import com.cab404.calc2.nodes.Era;
import com.cab404.calc2.nodes.Node;

/**
 * @author cab404
 */
public class VariableNode extends Node {
	public Node replacement = this;

	@Override public Node resolve(Calculation context, int index) {
		if (replacement instanceof VariableNode && replacement != this)
			return replacement.resolve(context, index);
		else
			return context.set(index, replacement);
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
