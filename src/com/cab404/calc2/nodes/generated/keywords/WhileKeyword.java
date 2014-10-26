package com.cab404.calc2.nodes.generated.keywords;

import com.cab404.calc2.base.Calculation;
import com.cab404.calc2.nodes.Era;
import com.cab404.calc2.nodes.Node;
import com.cab404.calc2.nodes.generated.NestedCalculationNode;
import com.cab404.calc2.nodes.generated.NumberNode;
import com.cab404.calc2.nodes.parse.BooleanNode;

/**
 * @author cab404
 */
public class WhileKeyword extends KeywordNode {

	public WhileKeyword(CharSequence val) {super(val);}

	@Override public Node resolve(Calculation context, int index) {
		NestedCalculationNode condition = (NestedCalculationNode) context.algorithm.get(index + 1);
		NestedCalculationNode calculate = (NestedCalculationNode) context.algorithm.get(index + 2);

		try {

			while (true) {
				NestedCalculationNode cloned = (NestedCalculationNode) condition.clone();
				cloned.nested.calculate();

				Node node = cloned.nested.algorithm.get(condition.nested.algorithm.size() - 2);

				if (node instanceof BooleanNode)
					if (((BooleanNode) node).value)
						((NestedCalculationNode) calculate.clone()).nested.calculate();
					else break;

				if (node instanceof NumberNode)
					if (((NumberNode) node).value != 0)
						((NestedCalculationNode) calculate.clone()).nested.calculate();
					else break;

			}


		} catch (CloneNotSupportedException e) {
			throw new RuntimeException("Can't clone!", e);
		}
		context.algorithm.remove(index);
		context.algorithm.remove(condition);
		context.algorithm.remove(calculate);
		return null;
	}

	@Override public int priority() {
		return Era.CONTROL_ERA;
	}
}
