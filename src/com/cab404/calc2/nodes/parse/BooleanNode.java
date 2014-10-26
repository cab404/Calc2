package com.cab404.calc2.nodes.parse;

import com.cab404.calc2.nodes.generated.keywords.KeywordNode;

/**
 * @author cab404
 */
public class BooleanNode extends KeywordNode {
	public boolean value = false;


	/**
	 * Default node constructor
	 *
	 * @param stat Statement from expression
	 */
	public BooleanNode(CharSequence stat) {
		super(stat);
		value = Boolean.valueOf(stat.toString());
	}

	public BooleanNode(boolean bool) {
		super(null);
		this.value = bool;
	}

	@Override public Object clone()
	throws CloneNotSupportedException {
		return new BooleanNode(value);
	}
}
