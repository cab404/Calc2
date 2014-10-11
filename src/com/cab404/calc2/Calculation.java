package com.cab404.calc2;

import java.text.ParseException;
import java.util.*;

/**
 * Core launcher
 *
 * @author cab404
 */
public class Calculation {

	final Map<String, Object> services;
	final NodeFactory nodeFactory;
	List<Node> algorithm;

	public void calculate() {
		HashSet<Node> processed = new HashSet<>();

		processing:
		while (true) {
			processed.addAll(algorithm);

			/* Preparing priority list */
			List<Node> priorities = new ArrayList<>(algorithm.size());
			for (Node node : algorithm)
				priorities.add(node);

			Collections.sort(priorities, NodeComparator.INSTANCE);

			/* Resolving all nodes */
			for (Node node : priorities)
				if (algorithm.contains(node))
					node.resolve(this, algorithm.indexOf(node));

			/* Checking for new unprocessed nodes */
			for (Node node : algorithm)
				if (!processed.contains(node))
					continue processing;

			break;
		}

	}

	private Calculation(Calculation base, int start, int end) {
		algorithm = base.algorithm.subList(start, end);
		nodeFactory = base.nodeFactory;
		services = base.services;
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
		services = base.services;
	}

	public Calculation(NodeFactory nodeFactory) {
		this.nodeFactory = nodeFactory;
		this.algorithm = new ArrayList<>();
		this.services = new HashMap<>();
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
						current_def = nodeFactory.getNodeDefenition(buffer.charAt(0), current_char);
					} catch (NodeTypeIrresolvableException e) {
						algorithm.add(current_def.instatinate(buffer));
						buffer = new StringBuilder();
						current_def = null;
						i--;
					}
				}

			}

		}

		if (buffer.length() > 0 && current_def != null)
			algorithm.add(current_def.instatinate(buffer));

	}


}
