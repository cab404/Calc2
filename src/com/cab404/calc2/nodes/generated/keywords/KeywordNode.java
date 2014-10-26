package com.cab404.calc2.nodes.generated.keywords;

import com.cab404.calc2.nodes.generated.ParseableNode;

import java.lang.reflect.InvocationTargetException;

/**
 * @author cab404
 */
public abstract class KeywordNode extends ParseableNode {
	private final CharSequence val;

	public KeywordNode(CharSequence val) {super(val);
		this.val = val;
	}

	@Override public Object clone()
	throws CloneNotSupportedException {
		try {
			return getClass().getConstructor(CharSequence.class).newInstance(val);
		} catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			throw new CloneNotSupportedException();
		}
	}
}
