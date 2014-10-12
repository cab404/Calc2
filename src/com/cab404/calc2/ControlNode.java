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

	/* As fast as we could. But still will leave some place for others. */
	@Override public int priority() {
		return Era.CONTROL_ERA;
	}

	@Override public Node resolve(Calculation context, int index) {
		if (val == '(')
			return context.add(index, new NestedCalculationNode(context, index));
		if (val == ')')
			throw new RuntimeException("Wrong brackets!");
		return null;
	}
}
