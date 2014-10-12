package com.cab404.calc2.nodes.parse;

import com.cab404.calc2.nodes.generated.ParseableNode;

/**
 * @author cab404
 */
public class BooleanNode extends ParseableNode {
	boolean value = false;


	/**
	 * Default node constructor
	 *
	 * @param stat Statement from expression
	 */
	public BooleanNode(CharSequence stat) {
		super(stat);
	}



}
