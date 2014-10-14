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

		Node result = null;

		if ((result = tryBoth(context, index)) != null) {
			context.algorithm.set(index, result);
			context.algorithm.remove(index + 1);
			context.algorithm.remove(index - 1);
		} else if ((result = tryPrefix(context, index)) != null) {
			context.algorithm.set(index, result);
			context.algorithm.remove(index - 1);
		} else if ((result = tryPostfix(context, index)) != null) {
			context.algorithm.set(index, result);
			context.algorithm.remove(index + 1);
		}


		return result;
	}

	private Node tryBoth(Calculation context, int index) {
		if (index + 1 >= context.algorithm.size()) return null;
		if (index - 1 < 0) return null;

		Node second = context.algorithm.get(index + 1);
		Node first = context.algorithm.get(index - 1);

		if (!(first instanceof FunctionNode) && !(second instanceof FunctionNode))
			return fun.calculate(first, second);
		else
			return null;
	}

	private Node tryPrefix(Calculation context, int index) {
		if (index - 1 < 0) return null;
		Node first = context.algorithm.get(index - 1);

		if (!(first instanceof FunctionNode))
			return fun.calculatePrefix(first);
		else
			return null;
	}

	private Node tryPostfix(Calculation context, int index) {
		if (index + 1 >= context.algorithm.size()) return null;
		Node second = context.algorithm.get(index + 1);

		if (!(second instanceof FunctionNode))
			return fun.calculatePostfix(second);
		else
			return null;
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
