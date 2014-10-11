package com.cab404.calc2;

/**
 * Node for some expression. May be... hmm... anything.
 *
 * @author cab404
 */
public class NamedNode extends ParseableNode {

	private static final CharField BODY = new CharField() {
		@Override public boolean contains(char ch) {
			return !Character.isDigit(ch) && !Character.isWhitespace(ch) &&
					ch != ')' && ch != '(' && ch != ',';
		}
	};

	public static final NodeDefinition
			DEFINITION = new NodeDefinition(
			NamedNode.class,
			BODY,
			BODY
	);
	private final CharSequence stat;


	/**
	 * Default node constructor
	 *
	 * @param stat Statement from expression
	 */
	public NamedNode(CharSequence stat) {
		super(stat);
		this.stat = stat;
	}

	@Override public String toString() {
		return String.valueOf(stat);
	}
}
