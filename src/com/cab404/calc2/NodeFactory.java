package com.cab404.calc2;

import java.util.ArrayList;

/**
 * @author cab404
 */
public class NodeFactory {

	ArrayList<NodeDefinition> registeredDefenitions = new ArrayList<>();

	public void register(NodeDefinition def) {
		registeredDefenitions.add(def);
	}

	public NodeDefinition getNodeDefenition(char start)
	throws NodeTypeIrresolvableException {
		for (NodeDefinition def : registeredDefenitions)
			if (def.compatibleAsStart(start))
				return def;
		throw new NodeTypeIrresolvableException();
	}

	public NodeDefinition getNodeDefenition(char start, char body)
	throws NodeTypeIrresolvableException {
		for (NodeDefinition def : registeredDefenitions)
			if (def.compatibleAsStart(start) && def.compatibleAsBody(body))
				return def;
		throw new NodeTypeIrresolvableException();
	}

}
