package com.cab404.calc2.base;

import com.cab404.calc2.exceptions.NodeTypeIrresolvableException;
import com.cab404.calc2.nodes.Node;
import com.cab404.calc2.nodes.parse.NodeDefinition;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * Core launcher
 *
 * @author cab404
 */
public class Calculation implements Cloneable {

	//	VariableProvider variables;
	public NodeFactory nodeFactory;
	public List<Node> algorithm;

	/**
	 * Like algorithm.set, but returns new node
	 */
	public Node set(int index, Node node) {
		algorithm.set(index, node);
		return node;
	}

	/**
	 * Like algorithm.add, but returns new node
	 */
	public Node add(int index, Node node) {
		algorithm.add(index, node);
		return node;
	}

	public void calculate() {
		HashSet<Node> processed = new HashSet<>();
		List<Node> priorities = new ArrayList<>();

		/* Preparing priority list */
		priorities.addAll(algorithm);

		int iter = 0;

		/* Resolving all nodes */
		while (!priorities.isEmpty()) {
			Collections.sort(priorities);
			Node node = priorities.remove(0);

			if (!processed.contains(node)) {
				if (algorithm.contains(node)) {
					Node new_node = node.resolve(this, algorithm.indexOf(node));

					if (new_node != null)
						priorities.add(new_node);

				} else
					iter++;
			}

		}

	}

	private Calculation(Calculation base, int start, int end) {
		nodeFactory = base.nodeFactory;
		algorithm = base.algorithm.subList(start, end);
	}

	private void onNestedCreation(Calculation nested) {
//		try {
//			NamedNodeDefinition def = (NamedNodeDefinition) nodeFactory.getNodeDefinition('n', 'a');


//		} catch (NodeTypeIrresolvableException e) {
//			throw new RuntimeException("Cannot get named node definition", e);
//		}
	}

	/**
	 * Calculates given part in separate calculation, then inserts resolution into current calculation.
	 */
	public void calculateSandboxed(int startIndex, int endIndex) {
		Calculation calculation = new Calculation(this, startIndex, endIndex);
		calculation.calculate();
	}


	public Calculation(Calculation base) {
		nodeFactory = base.nodeFactory;
		base.onNestedCreation(this);
	}

	public Calculation(NodeFactory nodeFactory) {
		this.nodeFactory = nodeFactory;
		this.algorithm = new ArrayList<>();
	}


	/**
	 * Converts expression into a list of nodes.
	 */
	public void prepare(CharSequence exp)
			throws ParseException {
		algorithm.clear();

		final int length = exp.length();

		NodeDefinition current_def = null;
		StringBuilder buffer = new StringBuilder();

		for (int i = 0; i < length; i++) {
			char current_char = exp.charAt(i);

			if (current_def == null) {
				/*
				 * Trying to find a compatible node
				 */
				try {
					current_def = nodeFactory.getNodeDefenition(current_char);
					buffer.append(current_char);
				} catch (NodeTypeIrresolvableException ignored) {}

			} else {

				/*
				 * If current node is still compatible, continuing to append to it.
				 */
				if (current_def.compatibleAsBody(current_char))
					buffer.append(current_char);

				/*
				 * Otherwise, trying to find a compatible definition.
				 *
				 * If fail, clearing buffers and repeating cycle with no definition.
				 *
				 */
				else {
					try {
						current_def = nodeFactory.getNodeDefinition(buffer.charAt(0), current_char);
					} catch (NodeTypeIrresolvableException e) {
						algorithm.add(current_def.instance(buffer));
						buffer = new StringBuilder();
						current_def = null;
						i--;
					}
				}

			}

		}

		if (buffer.length() > 0 && current_def != null)
			algorithm.add(current_def.instance(buffer));

	}


	@Override public Object clone()
	throws CloneNotSupportedException {
		Calculation clone = new Calculation(nodeFactory);
		for (Node node : algorithm)
			clone.algorithm.add((Node) node.clone());
		return clone;
	}
}
