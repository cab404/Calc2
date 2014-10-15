package com.cab404.calc2.plugins;

import com.cab404.calc2.exceptions.CannotResolveNodeException;
import com.cab404.calc2.nodes.Node;
import com.cab404.calc2.nodes.generated.NamedNode;
import com.cab404.calc2.nodes.parse.NodeDefinition;
import com.cab404.calc2.util.CharField;

import java.util.Set;
import java.util.TreeSet;

/**
 * Resolver for NamedNodes
 *
 * @author cab404
 */
public class NameResolver implements NodeDefinition {

	Set<NodeForNameProvider> resolvers;


	private static final CharField BODY = new CharField() {
		@Override public boolean contains(char ch) {
			return !Character.isDigit(ch) && !Character.isWhitespace(ch) &&
					ch != ')' &&
					ch != '(' &&
					ch != ';' &&
					ch != ',';
		}
	};

	public NameResolver() {
		resolvers = new TreeSet<>();
	}


	public void add(NodeForNameProvider resolver) {
		resolvers.add(resolver);
	}

	public Node resolve(String name) {
		Node resolved;
		for (NodeForNameProvider resolver : resolvers)
			if ((resolved = resolver.nodeForName(name)) != null)
				return resolved;
		throw new CannotResolveNodeException(name);
	}

	@Override public boolean compatibleAsStart(char ch) {
		return BODY.contains(ch);
	}

	@Override public boolean compatibleAsBody(char ch) {
		return BODY.contains(ch);
	}

	@Override public Node instance(CharSequence body) {
		return new NamedNode(body, this);
	}


	@Override public NodeDefinition nested() {
		NameResolver nested = new NameResolver();

		for (NodeForNameProvider provider : resolvers) {
			nested.add(provider.nested());
		}

		return nested;
	}

}