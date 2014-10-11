package com.cab404.calc2;

import java.util.ArrayList;
import java.util.List;

/**
 * Braaaaackeeeeets!
 *
 * @author cab404
 */
public class NestedCalculationNode extends Node {
	Calculation nested;

	public NestedCalculationNode(Calculation base, int id) {
		List<Node> sublist = new ArrayList<>();
		int bracketCheck = 0;

		List<Node> exp = base.algorithm;

		for (int i = id; i < exp.size(); i++) {
			Node node = exp.get(i);

			if (node instanceof ControlNode) {
				if (((ControlNode) node).val == '(')
					bracketCheck++;
				if (((ControlNode) node).val == ')')
					bracketCheck--;
				if (((ControlNode) node).val == ';')
					throw new RuntimeException("Brackets wrong!");
			}

			if (bracketCheck == 0) {

				for (int j = id; j <= i; j++)
					sublist.add(exp.remove(id));
				sublist.remove(0);
				sublist.remove(sublist.size() - 1);

				nested = new Calculation(base);
				nested.algorithm = sublist;

				nested.calculate();

				return;
			}

			if (bracketCheck < 0)
				throw new RuntimeException("Brackets wrong!");

		}

		throw new RuntimeException("Brackets wrong!");

	}

	/**
	 * Deleting unnecessary brackets
	 */
	@Override public void resolve(Calculation context, int index) {
		/* Resolve me! */
		while (true) {
			if (nested.algorithm.size() == 1) {
				context.algorithm.set(index, nested.algorithm.get(0));
				if (nested.algorithm.get(0) instanceof NestedCalculationNode) {
					nested.algorithm = ((NestedCalculationNode) nested.algorithm.get(0)).nested.algorithm;
				} else
					break;
			}
		}
	}

	@Override public String toString() {
		return nested.algorithm.toString();
	}

	@Override public int priority() {
		return -9001;
	}
}
