package com.cab404.calc2;

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
