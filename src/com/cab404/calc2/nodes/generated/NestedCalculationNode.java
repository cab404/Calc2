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
	public final Calculation nested;

	public NestedCalculationNode(Calculation base, int id) {
		List<Node> sublist = new ArrayList<>();
		int bracketCheck = 0;

		List<Node> exp = base.algorithm;
		List<Integer> splits = new ArrayList<>();

		for (int i = id; i < exp.size(); i++) {
			Node node = exp.get(i);

			if (node instanceof ControlNode) {
				if (((ControlNode) node).val == '(')
					bracketCheck++;
				if (((ControlNode) node).val == ')')
					bracketCheck--;
				if (((ControlNode) node).val == ';' && bracketCheck == 1)
					splits.add(i - 1);

			}

			if (bracketCheck == 0) {
				for (int j = id; j <= i; j++)
					sublist.add(exp.remove(id));

				sublist.remove(0);
				sublist.remove(sublist.size() - 1);

				nested = new Calculation(base);
				nested.algorithm = sublist;

				/* Adding trailing ';', if there's no. */
				Node last_node = nested.algorithm.get(nested.algorithm.size() - 1);
				if (!(last_node instanceof ControlNode && ((ControlNode) last_node).val == ';'))
					nested.algorithm.add(new ControlNode(";"));

				/* Calculating lines */
				int last = 0;
				int level = 0;
				Set<ControlNode> calculated_lines = new HashSet<>();
				for (int k = 0; k < nested.algorithm.size(); k++) {
					if (nested.algorithm.get(k) instanceof ControlNode) {
						ControlNode cast = ((ControlNode) nested.algorithm.get(k));

						if (cast.val == '(') level++;
						if (cast.val == ')') level--;
						if ((cast.val == ';' && level == 0)) {
							if (calculated_lines.contains(cast)) {
								last = k + 1;
							} else {
								calculated_lines.add(cast);

								nested.calculateSandboxed(last, k);

								last = k = 0;
							}
						} else if (k == nested.algorithm.size() - 1) {
							nested.calculateSandboxed(last, k + 1);
						}

					}
				}

				/* Removing all results */
				for (Node nd : calculated_lines)
					nested.algorithm.remove(nd);

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
	@Override public Node resolve(Calculation context, int index) {
		/* Resolve me! */
		for (; ; )
			if (nested.algorithm.size() == 1) {
				Node single = nested.algorithm.get(0);

				if (single instanceof NestedCalculationNode)
					nested.algorithm = ((NestedCalculationNode) single).nested.algorithm;
				else
					return context.set(index, single);

			} else
				return null;
	}

	@Override public String toString() {
		return nested.algorithm.toString();
	}

	@Override public int priority() {
		return Era.VARIABLE_ERA;
	}
}
