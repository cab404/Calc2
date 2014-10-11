package com.cab404.calc2;

import java.util.HashMap;

/**
 * @author cab404
 */
public class VariableService {
	private HashMap<String, VariableNode> variables;

	public VariableNode getVariable(String name) {
		return variables.get(name);
	}

}
