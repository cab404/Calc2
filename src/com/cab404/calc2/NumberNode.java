package com.cab404.calc2;

/**
 * @author cab404
 */
public class NumberNode extends ParseableNode {

	public static final NodeDefinition DEFINITION =
			new NodeDefinition(
					NumberNode.class,
					new CharField() {
						@Override public boolean contains(char ch) {return Character.isDigit(ch);}
					},
					new CharField() {
						@Override public boolean contains(char ch) {return Character.isDigit(ch) || ch == '.' || ch == 'e'; }
					}
			);


	double value;

	public NumberNode(CharSequence stat)
	throws NumberFormatException {
		super(stat);
		value = Double.parseDouble(stat.toString());
	}


	@Override public void resolve(Calculation context, int index) {
		super.resolve(context, index);
	}

	public static class Body implements CharField {
		@Override public boolean contains(char ch) {
			return Character.isDigit(ch) || ch == '.' || ch == 'e';
		}
	}

	@Override public String toString() {
		return String.valueOf(value);
	}
}
