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
			calculation.prepare("356 + - (745 + 12, 677) + (((gjkawd-11.11e11 ddas)))");
			calculation.calculate();

			System.out.println(calculation.algorythm);

		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}


	public static void main(String[] args) {
		new Main().run();
	}

}
