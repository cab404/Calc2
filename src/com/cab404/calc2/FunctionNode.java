package com.cab404.calc2;

/**
 * @author cab404
 */
public class FunctionNode extends Node {
	PluginFunction fun;

	public FunctionNode(PluginFunction fun) {
		this.fun = fun;
	}

	@Override public void resolve(Calculation context, int index) {
		int size = context.algorithm.size();
		if (size == 1) return;

		if (index == 0) {
			Node node = context.algorithm.remove(index + 1);
			context.algorithm.set(index, fun.calculatePrefix(node));
			return;
		}

		if (index == size - 1) {
			Node node = context.algorithm.remove(index - 1);
			context.algorithm.set(index - 1, fun.calculatePostfix(node));
			return;
		}

		Node second = context.algorithm.remove(index + 1);
		Node first = context.algorithm.remove(index - 1);
		context.algorithm.set(index - 1, fun.calculate(first, second));
	}
}
