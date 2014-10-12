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

			calculation.functions.register(BasicFunctions.SUM);
			calculation.functions.register(BasicFunctions.SUB);
			calculation.functions.register(BasicFunctions.MUL);
			calculation.functions.register(BasicFunctions.DIV);
			calculation.functions.register(BasicFunctions.RES);
			calculation.functions.register(BasicFunctions.EQ);

			calculation.prepare("" +
					"res (" +
					"   abc = (356-((-2*42)+17-28));" +
					"   cmd = 10e0;" +
					"   cmd" +
					")");
			System.out.println("start: " + calculation.algorithm);
			calculation.calculate();
			System.out.println("final: " + calculation.algorithm);

			System.out.println(calculation.algorithm);

		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}


	public static void main(String[] args) {
		new Main().run();
	}

}
