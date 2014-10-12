package com.cab404.calc2.plugins.functions;

import com.cab404.calc2.nodes.Node;

/**
 * @author cab404
 */
public interface PluginFunction {

	public String getName();

	public Node calculatePrefix(Node single);
	public Node calculate(Node first, Node second);
	public Node calculatePostfix(Node single);
	public int priority();

}
