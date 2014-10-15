package com.cab404.calc2.impl;

import com.cab404.calc2.nodes.generated.NestedCalculationNode;
import com.cab404.calc2.plugins.functions.PluginFunction;
import com.cab404.calc2.nodes.Era;
import com.cab404.calc2.nodes.Node;
import com.cab404.calc2.nodes.generated.NumberNode;
import com.cab404.calc2.nodes.parse.VariableNode;

/**
 * @author cab404
 */
public class BasicFunctions {

	public static final PluginFunction

			SUM = new PluginFunction() {
		@Override public String getName() {
			return "+";
		}
		@Override public Node calculatePrefix(Node single) {
			return single;
		}
		@Override public Node calculate(Node first, Node second) {
			double sum = ((NumberNode) first).value + ((NumberNode) second).value;
			return new NumberNode(sum);
		}

		@Override public Node calculatePostfix(Node single) {
			throw new UnsupportedOperationException();
		}
		@Override public int priority() {
			return Era.FUNCTIONAL_ERA + 200;
		}
	},
			SUB = new PluginFunction() {
				@Override public String getName() {
					return "-";
				}
				@Override public Node calculatePrefix(Node single) {
					return new NumberNode(-((NumberNode) single).value);
				}
				@Override public Node calculate(Node first, Node second) {
					double sum = ((NumberNode) first).value - ((NumberNode) second).value;
					return new NumberNode(sum);
				}

				@Override public Node calculatePostfix(Node single) {
					throw new UnsupportedOperationException();
				}
				@Override public int priority() {
					return Era.FUNCTIONAL_ERA + 200;
				}

			},
			DIV = new PluginFunction() {
				@Override public String getName() {
					return "/";
				}
				@Override public Node calculatePrefix(Node single) {
					throw new UnsupportedOperationException();
				}
				@Override public Node calculate(Node first, Node second) {
					double sum = ((NumberNode) first).value / ((NumberNode) second).value;
					return new NumberNode(sum);
				}

				@Override public Node calculatePostfix(Node single) {
					throw new UnsupportedOperationException();
				}
				@Override public int priority() {
					return Era.FUNCTIONAL_ERA + 100;
				}

			},

	MUL = new PluginFunction() {
		@Override public String getName() {
			return "*";
		}
		@Override public Node calculatePrefix(Node single) {
			throw new UnsupportedOperationException();
		}
		@Override public Node calculate(Node first, Node second) {
			double sum = ((NumberNode) first).value * ((NumberNode) second).value;
			return new NumberNode(sum);
		}

		@Override public Node calculatePostfix(Node single) {
			throw new UnsupportedOperationException();
		}
		@Override public int priority() {
			return Era.FUNCTIONAL_ERA + 100;
		}

	},
			EQ = new PluginFunction() {
				@Override public String getName() {
					return "=";
				}
				@Override public Node calculatePrefix(Node single) {
					throw new UnsupportedOperationException();
				}
				@Override public Node calculate(Node first, Node second) {
					((VariableNode) first).replacement = second;
					return second;
				}

				@Override public Node calculatePostfix(Node single) {
					throw new UnsupportedOperationException();
				}
				@Override public int priority() {
					return Era.VARIABLE_ERA + 2;
				}

			},
			RES = new PluginFunction() {
				@Override public String getName() {
					return "res";
				}
				@Override public Node calculatePrefix(Node single) {
					return calculatePostfix(single);
				}
				@Override public Node calculate(Node first, Node second) {
					throw new UnsupportedOperationException();
				}

				@Override public Node calculatePostfix(Node single) {
					if (single instanceof NestedCalculationNode) {
						NestedCalculationNode calc = (NestedCalculationNode) single;
						return calc.nested.algorithm.get(calc.nested.algorithm.size() - 1);
					}
					return single;
				}

				/** Right after nested */
				@Override public int priority() {
					return Era.FOLDING_ERA + 20;
				}

			};

}
