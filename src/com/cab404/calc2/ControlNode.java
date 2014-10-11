package com.cab404.calc2;

/**
 * @author cab404
 */
public class ControlNode extends ParseableNode {

	public static final NodeDefinition DEFINITION = new NodeDefinition(
			ControlNode.class,
			new CharField() {
				@Override public boolean contains(char ch) {
					switch (ch) {
						case '(':
						case ')':
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

	char val;

	/**
	 * Default node constructor
	 *
	 * @param stat Statement from expression
	 */
	public ControlNode(CharSequence stat) {
		super(stat);
		val = stat.charAt(0);
	}

	@Override public String toString() {
		return "'" + val + "'";
	}

	@Override public int priority() {
		return -9001;
	}

	@Override public void resolve(Calculation context, int index) {
		if (val == '(')
			context.algorythm.add(index, new NestedCalculationNode(context, index));
		if (val == ')')
			throw new RuntimeException("Wrong brackets!");
	}
}
