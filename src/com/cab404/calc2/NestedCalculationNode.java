package com.cab404.calc2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cab404
 */
public class NestedCalculationNode extends Node {
	Calculation nested;

	public NestedCalculationNode(Calculation base, int id) {
		List<Node> sublist = new ArrayList<>();
		int bracketCheck = 0;

		List<Node> exp = base.algorythm;

		for (int i = id; i < exp.size(); i++) {
			Node node = exp.get(i);

			if (node instanceof ControlNode) {
				if (((ControlNode) node).val == '(')
					bracketCheck++;
				if (((ControlNode) node).val == ')')
					bracketCheck--;
			}

			if (bracketCheck == 0) {

				for (int j = id; j <= i; j++)
					sublist.add(exp.remove(id));
				sublist.remove(0);
				sublist.remove(sublist.size() - 1);

				nested = new Calculation(base.nodeFactory);
				nested.algorythm = sublist;

				nested.calculate();

				return;
			}

			if (bracketCheck < 0)
				throw new RuntimeException("Brackets wrong!");

		}

		throw new RuntimeException("Brackets wrong!");

	}

	@Override public String toString() {
		return nested.algorythm.toString();
	}

}
