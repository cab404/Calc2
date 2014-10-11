package com.cab404.calc2;

import java.util.HashMap;

/**
 * @author cab404
 */
public class FunctionProvider {
	public static final String NAME = "FunctionProvider";

	HashMap<String, PluginFunction> registry = new HashMap<>();

	public void register(PluginFunction fn) {
		registry.put(fn.getName(), fn);
	}

	public PluginFunction get(String name) {
		return registry.get(name);
	}

}
