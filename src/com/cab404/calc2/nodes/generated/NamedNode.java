package com.cab404.calc2.nodes.generated;

import com.cab404.calc2.base.Calculation;
import com.cab404.calc2.nodes.Era;
import com.cab404.calc2.nodes.Node;
import com.cab404.calc2.plugins.NameResolver;

/**
 * Node for some expression. May be... hmm... anything.
 *
 * @author cab404
 */
public class NamedNode extends Node {

	private final CharSequence stat;
	private NameResolver resolver;


	public NamedNode(CharSequence stat, NameResolver resolver) {
		this.resolver = resolver;
		this.stat = stat;
	}

	@Override public Node resolve(Calculation context, int index) {
		String name = stat.toString();
		return context.set(index, resolver.resolve(name));

	}

	@Override public int priority() {
		return Era.CONTROL_ERA;
	}

	@Override public String toString() {
		return String.valueOf(stat);
	}
}
