package com.cab404.calc2.nodes.generated.keywords;

import com.cab404.calc2.nodes.Node;
import com.cab404.calc2.nodes.generated.KeywordNode;
import com.cab404.calc2.plugins.NodeForNameProvider;

import java.lang.reflect.InvocationTargetException;

/**
 * @author cab404
 */
public class KeywordProvider extends NodeForNameProvider {
	public static enum Keywords {
		_while(WhileKeyword.class),
		_do(WhileKeyword.class);

		public final Class<? extends KeywordNode> cls;
		Keywords(Class<? extends KeywordNode> cls) {this.cls = cls;}
	}

	@Override public Node nodeForName(String name) {
		Keywords kw = Keywords.valueOf("_" + name);
		if (kw != null)
			try {
				return kw.cls.getConstructor().newInstance();
			} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
				throw new RuntimeException(e);
			}
		return null;
	}

	@Override public int priority() {
		return -90000;
	}

	@Override public NodeForNameProvider nested() {
		return this;
	}

}
