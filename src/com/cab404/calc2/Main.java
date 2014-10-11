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
			fun.register(BasicFunctions.RES);
			fun.register(BasicFunctions.EQ);

			calculation.services.put(FunctionProvider.NAME, fun);
			calculation.services.put(VariableProvider.NAME, new VariableProvider());

			calculation.prepare("" +
					" res (" +
					"   asdf = (356-( (-2*42) +17-28));" +
					"   asdf * asdf;" +
					")");
			System.out.println("start: " + calculation.algorithm);
			calculation.calculate();
			System.out.println("final: " + calculation.algorithm);
//			calculation.prepare("356 + sqrt(745 + 12, 677) + (alpha <- 11.11e11, nemesis)");
//			calculation.prepare("asdf * asdf");
//			calculation.calculate();
//			calculation.prepare("xna = (12,12)");
//			calculation.calculate();
//			calculation.prepare("xna");
//			calculation.calculate();

			System.out.println(calculation.algorithm);

		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}


	public static void main(String[] args) {
		new Main().run();
	}

}
