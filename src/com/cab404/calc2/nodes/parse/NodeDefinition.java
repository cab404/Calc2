package com.cab404.calc2.nodes.parse;

import com.cab404.calc2.nodes.Node;

/**
 * @author cab404
 */
public interface NodeDefinition {

	public boolean compatibleAsStart(char ch);

	public boolean compatibleAsBody(char ch);

	public Node instance(CharSequence body);

	public NodeDefinition nested();

}
