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
					ch != ')' &&
					ch != '(' &&
					ch != ';' &&
					ch != ',';
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

	@Override public void resolve(Calculation context, int index) {
		super.resolve(context, index);
		String name = stat.toString();

		FunctionProvider provider = (FunctionProvider) context.services.get(FunctionProvider.NAME);
		PluginFunction function = provider.get(name);

		if (function != null) {
			context.algorithm.set(index, new FunctionNode(function));
		} else {
			VariableProvider vars = (VariableProvider) context.services.get(VariableProvider.NAME);
			context.algorithm.set(index, vars.getVariable(name));
		}

	}

	@Override public String toString() {
		return String.valueOf(stat);
	}
}
