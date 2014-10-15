package com.cab404.calc2.plugins;

import com.cab404.calc2.nodes.Node;
import com.cab404.calc2.nodes.parse.VariableNode;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * @author cab404
 */
public class VariableProvider extends NodeForNameProvider {
	public static final String NAME = "VariableProvider";

	private HashMap<String, VariableNode> variables = new HashMap<>();

	public VariableProvider() {
	}

	/**
	 * Hides everything ontop
	 */
	public VariableProvider(VariableProvider base) {
		this.variables = new HashMap<>(base.variables);
	}

	public VariableNode getVariable(String name) {
		if (!variables.containsKey(name))
			variables.put(name, new VariableNode());
		return variables.get(name);
	}

	@Override public Node nodeForName(String name) {
		return getVariable(name);
	}

	@Override public int priority() {
		return 90000;
	}


	@Override public NodeForNameProvider nested() {
		VariableProvider nested = new VariableProvider();
		nested.variables = new LinkedHashMap<>(variables);
		return nested;
	}
}
