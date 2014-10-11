package com.cab404.calc2;

import java.util.HashMap;

/**
 * @author cab404
 */
public class VariableProvider {
	public static final String NAME = "VariableProvider";

	private HashMap<String, VariableNode> variables = new HashMap<>();

	public VariableNode getVariable(String name) {
		if (!variables.containsKey(name))
			variables.put(name, new VariableNode());
		return variables.get(name);
	}

}
