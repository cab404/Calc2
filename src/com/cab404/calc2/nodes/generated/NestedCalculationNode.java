package com.cab404.calc2.nodes.generated;

import com.cab404.calc2.base.Calculation;
import com.cab404.calc2.nodes.Era;
import com.cab404.calc2.nodes.Node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Braaaaackeeeeets!
 *
 * @author cab404
 */
public class NestedCalculationNode extends Node {
	public Calculation nested;

	public NestedCalculationNode() {}

	/**
	 * Wraps selected brackets
	 */
	public void wrap(Calculation base, int id) {
		List<Node> sublist = new ArrayList<>();

		List<Node> exp = base.algorithm;

		int bracketCheck = 0;
		/* Cutting the thing.*/

		while (exp.size() > id) {
			Node node = exp.remove(id);
			sublist.add(node);

			if (node instanceof ControlNode) {
				if (((ControlNode) node).val == '(')
					bracketCheck++;
				if (((ControlNode) node).val == ')')
					bracketCheck--;
				if (bracketCheck < 0)
					throw new RuntimeException("Brackets wrong!");
				System.out.println(bracketCheck + ": " + sublist);
			}

			if (bracketCheck == 0) {
				/* Removing brackets */
				sublist.remove(0);
				sublist.remove(sublist.size() - 1);

				nested = new Calculation(base);
				nested.algorithm = sublist;

				/* Adding trailing ';', if there's no. */
				Node last_node = nested.algorithm.get(nested.algorithm.size() - 1);
				if (!(last_node instanceof ControlNode && ((ControlNode) last_node).val == ';'))
					nested.algorithm.add(new ControlNode(";"));

				break;
			}
		}

		if (bracketCheck != 0)
			throw new RuntimeException("Brackets wrong!");

	}

	private void calculate() {

		/* Calculating thing */
		int last = 0;
		int level = 0;
		Set<ControlNode> calculated_lines = new HashSet<>();

		for (int k = 0; k < nested.algorithm.size(); k++) {

			/* Searching thru all the ControlNodes... */
			if (nested.algorithm.get(k) instanceof ControlNode) {
				ControlNode cast = ((ControlNode) nested.algorithm.get(k));

				if (cast.val == '(') level++;
				if (cast.val == ')') level--;

				/*... for a splitter... */
				if ((cast.val == ';' && level == 0)) {

					/*... and if we already calculated that line, then just updating last node. */
					if (calculated_lines.contains(cast)) {
						last = k + 1;

					}
					/* ... and if not - calculating value and adding line splitter to calculated values */
					/* Then reseting cycle, 'cause something might added a new one. */
					else {

						nested.calculateSandboxed(last, k);

						calculated_lines.add(cast);

						last = k = 0;
					}

				}

			}
		}

		/* Removing all splitters */
		for (Node nd : calculated_lines)
			nested.algorithm.remove(nd);

	}

	@Override public Node resolve(Calculation base, int id) {
		System.out.println("ALG" + nested.algorithm);
		calculate();
		System.out.println("ALGAF" + nested.algorithm);

		/* Resolve me! */
		for (; ; )
			if (nested.algorithm.size() == 1) {
				Node single = nested.algorithm.get(0);

				if (single instanceof NestedCalculationNode)
					nested.algorithm = ((NestedCalculationNode) single).nested.algorithm;
				else
					return base.set(id, single);

			} else
				return null;
	}

	@Override public String toString() {
		return nested.algorithm.toString();
	}

	@Override public int priority() {
		return Era.FOLDING_ERA;
	}

	@Override public Object clone()
	throws CloneNotSupportedException {
		return null;
	}
}
