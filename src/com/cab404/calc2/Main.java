package com.cab404.calc2;

import java.text.ParseException;

/**
 * @author cab404
 */
public class Main implements Runnable {

	@Override public void run() {
		NodeFactory factory = new NodeFactory();
		factory.register(ControlNode.DEFINITION);
		factory.register(NumberNode.DEFINITION);
		factory.register(NamedNode.DEFINITION);

		try {

			Calculation calculation = new Calculation(factory);

			FunctionProvider fun = new FunctionProvider();

			fun.register(BasicFunctions.SUM);
			fun.register(BasicFunctions.SUB);
			fun.register(BasicFunctions.MUL);
			fun.register(BasicFunctions.DIV);
			fun.register(BasicFunctions.EQ);

			calculation.services.put(FunctionProvider.NAME, fun);
			calculation.services.put(VariableProvider.NAME, new VariableProvider());

			calculation.prepare("asdf = (356 -(-2))");
//			calculation.prepare("356 + sqrt(745 + 12, 677) + (alpha <- 11.11e11, nemesis)");
			calculation.calculate();
			calculation.prepare("asdf * asdf");
			calculation.calculate();

			System.out.println(calculation.algorithm);

		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}


	public static void main(String[] args) {
		new Main().run();
	}

}
