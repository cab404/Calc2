package com.cab404.calc2.nodes.generated;

import com.cab404.calc2.base.Calculation;
import com.cab404.calc2.nodes.Era;
import com.cab404.calc2.nodes.Node;
import com.cab404.calc2.nodes.parse.NodeDefinition;
import com.cab404.calc2.nodes.parse.NodeDefinitionImpl;
import com.cab404.calc2.util.CharField;

/**
 * @author cab404
 */
public class ControlNode extends ParseableNode {

	public static final NodeDefinition DEFINITION = new NodeDefinitionImpl(
			ControlNode.class,
			new CharField() {
				@Override public boolean contains(char ch) {
					switch (ch) {
						case '(':
						case ')':
						case ';':
						case ',':
							return true;
						default:
							return false;
					}
				}
			},
			new CharField() {
				@Override public boolean contains(char ch) {
					return false;
				}
			}
	);

	public final char val;

	/**
	 * Default node constructor
	 *
	 * @param stat Statement from expression
	 */
	public ControlNode(CharSequence stat) {
		super(stat);
		val = stat.charAt(0);
	}

	public ControlNode(char val) {
		super(null);
		this.val = val;
	}

	@Override public String toString() {
		return "c'" + val + "'";
	}

	/* As fast as we could. But still will leave some place for others. */
	@Override public int priority() {
		return Era.CONTROL_ERA;
	}

	@Override public Object clone()
	throws CloneNotSupportedException {
		return new ControlNode(val);
	}

	@Override public Node resolve(Calculation context, int index) {
		if (val == '(') {
			NestedCalculationNode calc = new NestedCalculationNode();
			calc.wrap(context, index);
			return context.add(index, calc);
		}
		if (val == ')')
			throw new RuntimeException("Brackets wrong!");
		return null;
	}

}
