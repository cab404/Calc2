package com.cab404.calc2.nodes.parse;

import com.cab404.calc2.base.Calculation;
import com.cab404.calc2.plugins.functions.PluginFunction;
import com.cab404.calc2.nodes.Node;

/**
 * @author cab404
 */
public class FunctionNode extends Node {
	PluginFunction fun;

	public FunctionNode(PluginFunction fun) {
		this.fun = fun;
	}

	@Override public Node resolve(Calculation context, int index) {
		int size = context.algorithm.size();
		if (size == 1) return null;

		if (index == 0) {
			Node node = context.algorithm.remove(index + 1);
			Node calculated = fun.calculatePrefix(node);
			context.algorithm.set(index, calculated);
			return calculated;
		}

		if (index == size - 1) {
			Node node = context.algorithm.remove(index - 1);
			Node calculated = fun.calculatePostfix(node);
			context.algorithm.set(index - 1, calculated);
			return calculated;
		}

		Node second = context.algorithm.remove(index + 1);
		Node first = context.algorithm.remove(index - 1);
		Node calculated = fun.calculate(first, second);
		context.algorithm.set(index - 1, calculated);
		return calculated;
	}

	@Override public int priority() {
		return fun.priority();
	}

	@Override public String toString() {
		return fun.getName();
	}

	@Override public Object clone()
	throws CloneNotSupportedException {
		return new FunctionNode(fun);
	}
}
