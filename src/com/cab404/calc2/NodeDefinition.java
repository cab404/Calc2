package com.cab404.calc2;

import java.lang.reflect.InvocationTargetException;

/**
 * @author cab404
 */
public class NodeDefinition {
	private Class<? extends Node> nodeClass;
	private CharField allowedStart, allowedBody;


	public NodeDefinition(Class<? extends Node> nodeClass, CharField allowedStart, CharField allowedBody) {
		this.nodeClass = nodeClass;
		this.allowedStart = allowedStart;
		this.allowedBody = allowedBody;

		try {
			nodeClass.getConstructor(CharSequence.class);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("Node has no Class(String) constructor.", e);
		}

	}

	public boolean compatibleAsStart(char ch) {
		return allowedStart.contains(ch);
	}

	public boolean compatibleAsBody(char ch) {
		return allowedBody.contains(ch);
	}

	public Node instatinate(CharSequence body) {
		try {
			return nodeClass.getConstructor(CharSequence.class).newInstance(body);
		} catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException("Cannot instatinate node of class " + nodeClass, e);
		}
	}

}
