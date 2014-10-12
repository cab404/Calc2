package com.cab404.calc2.nodes.generated;

import com.cab404.calc2.base.Calculation;
import com.cab404.calc2.util.CharField;
import com.cab404.calc2.nodes.parse.NodeDefinition;
import com.cab404.calc2.nodes.parse.NodeDefinitionImpl;
import com.cab404.calc2.nodes.Node;

/**
 * @author cab404
 */
public class NumberNode extends ParseableNode {

	public static final NodeDefinition DEFINITION =
			new NodeDefinitionImpl(
					NumberNode.class,
					new CharField() {
						@Override public boolean contains(char ch) {return Character.isDigit(ch);}
					},
					new CharField() {
						@Override public boolean contains(char ch) {return Character.isDigit(ch) || ch == '.' || ch == 'e'; }
					}
			);


	public double value;

	public NumberNode(CharSequence stat)
	throws NumberFormatException {
		super(stat);
		value = Double.parseDouble(stat.toString());
	}

	public NumberNode(double value) {
		super(null);
		this.value = value;
	}

	@Override public Node resolve(Calculation context, int index) {
		return super.resolve(context, index);
	}

	@Override public String toString() {
		return String.valueOf(value);
	}
}
