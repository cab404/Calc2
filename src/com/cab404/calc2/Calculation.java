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
	List<Node> algorythm;

	public void calculate() {
		HashSet<Node> processed = new HashSet<>();

		processing:
		while (true) {
			System.out.println(algorythm);

			processed.addAll(algorythm);

			/* Preparing priority list */
			List<Node> priorities = new ArrayList<>(algorythm.size());
			for (Node node : algorythm)
				priorities.add(node);

			Collections.sort(priorities, NodeComparator.INSTANCE);

			/* Resolving all nodes */
			for (Node node : priorities)
				if (algorythm.contains(node))
					node.resolve(this, algorythm.indexOf(node));

			/* Checking for new unprocessed nodes */
			for (Node node : algorythm)
				if (!processed.contains(node))
					continue processing;

			System.out.println(algorythm);
			break;
		}

	}

	private Calculation(Calculation base, int start, int end) {
		algorythm = base.algorythm.subList(start, end);
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

	public Calculation(NodeFactory nodeFactory) {
		this.nodeFactory = nodeFactory;
		this.algorythm = new ArrayList<>();
		this.services = new HashMap<>();
	}


	/**
	 * Converts expression into a list of nodes.
	 */
	public void prepare(CharSequence exp)
			throws ParseException {
		algorythm.clear();

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
						algorythm.add(current_def.instatinate(buffer));
						buffer = new StringBuilder();
						current_def = null;
						i--;
					}
				}

			}

		}

		if (buffer.length() > 0 && current_def != null)
			algorythm.add(current_def.instatinate(buffer));

	}


}
