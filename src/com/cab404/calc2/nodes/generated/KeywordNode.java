package com.cab404.calc2.nodes.generated;

import com.cab404.calc2.nodes.Node;

import java.lang.reflect.InvocationTargetException;

/**
 * @author cab404
 */
public abstract class KeywordNode extends Node {
	public KeywordNode() {}

	@Override public Object clone()
	throws CloneNotSupportedException {
		try {
			return getClass().getConstructor().newInstance();
		} catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			throw new CloneNotSupportedException();
		}
	}
}
