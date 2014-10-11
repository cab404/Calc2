package com.cab404.calc2;

/**
 * @author cab404
 */
public class VariableNode extends Node {
	Node replacement = this;

	@Override public void resolve(Calculation context, int index) {
		context.algorithm.set(index, replacement);
	}

	@Override public int priority() {
		return -9001;
	}

	@Override public String toString() {
		return super.toString() + (replacement != this ? "(" + replacement + ")" : "");
	}

}
