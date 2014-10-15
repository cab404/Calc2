package com.cab404.calc2.plugins.functions;

import com.cab404.calc2.nodes.parse.FunctionNode;
import com.cab404.calc2.nodes.Node;
import com.cab404.calc2.plugins.NodeForNameProvider;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * @author cab404
 */
public class FunctionProvider extends NodeForNameProvider {
	HashMap<String, PluginFunction> registry = new HashMap<>();

	public void register(PluginFunction fn) {
		registry.put(fn.getName(), fn);
	}

	public PluginFunction get(String name) {
		return registry.get(name);
	}

	@Override public Node nodeForName(String name) {
		if (registry.containsKey(name))
			return new FunctionNode(get(name));
		else
			return null;
	}

	@Override public int priority() {
		return 0;
	}

	@Override public NodeForNameProvider nested() {
		FunctionProvider nested = new FunctionProvider();
		nested.registry = new LinkedHashMap<>(registry);
		return nested;
	}


}
