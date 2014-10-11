package com.cab404.calc2;

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

			};

}
