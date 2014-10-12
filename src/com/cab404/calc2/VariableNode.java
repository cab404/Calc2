package com.cab404.calc2;

/**
 * @author cab404
 */
public class VariableNode extends Node {
	Node replacement = this;

	@Override public Node resolve(Calculation context, int index) {
		return context.set(index, replacement);
	}

	@Override public int priority() {
		return Era.VARIABLE_ERA + 100;
	}

	@Override public String toString() {
		return (replacement != this ? "var(" + replacement + ")" : "not-resolved");
	}

}
