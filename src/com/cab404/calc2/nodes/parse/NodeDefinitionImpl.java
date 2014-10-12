package com.cab404.calc2.nodes.parse;

import com.cab404.calc2.exceptions.CannotResolveNodeException;
import com.cab404.calc2.nodes.Node;
import com.cab404.calc2.util.CharField;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author cab404
 */
public class NodeDefinitionImpl implements NodeDefinition {
	private final Class<? extends Node> nodeClass;
	private final CharField allowedStart, allowedBody;
	private final Constructor<? extends Node> constructor;

	public NodeDefinitionImpl(Class<? extends Node> nodeClass, CharField allowedStart, CharField allowedBody) {
		this.nodeClass = nodeClass;
		this.allowedStart = allowedStart;
		this.allowedBody = allowedBody;

		try {
			constructor = nodeClass.getConstructor(CharSequence.class);
		} catch (NoSuchMethodException e) {
			throw new CannotResolveNodeException("No constructor Node(CharSequence) found");
		}
	}

	@Override
	public boolean compatibleAsStart(char ch) {
		return allowedStart.contains(ch);
	}

	@Override
	public boolean compatibleAsBody(char ch) {
		return allowedBody.contains(ch);
	}

	@Override
	public Node instance(CharSequence body) {
		try {
			return constructor.newInstance(body);
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException("Cannot instatinate node of class " + nodeClass, e);
		}
	}

}
