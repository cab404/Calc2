package com.cab404.calc2;

import com.cab404.calc2.base.Calculation;
import com.cab404.calc2.base.NodeFactory;
import com.cab404.calc2.impl.BasicFunctions;
import com.cab404.calc2.nodes.generated.ControlNode;
import com.cab404.calc2.nodes.generated.NumberNode;
import com.cab404.calc2.plugins.functions.FunctionProvider;
import com.cab404.calc2.plugins.NameResolver;
import com.cab404.calc2.plugins.VariableProvider;

import java.io.*;
import java.text.ParseException;

/**
 * @author cab404
 */
public class Main implements Runnable {

	@Override public void run() {
		NodeFactory factory = new NodeFactory();
		factory.register(ControlNode.DEFINITION);
		factory.register(NumberNode.DEFINITION);

		NameResolver def = new NameResolver();

		FunctionProvider fp = new FunctionProvider();

		fp.register(BasicFunctions.SUM);
		fp.register(BasicFunctions.SUB);
		fp.register(BasicFunctions.MUL);
		fp.register(BasicFunctions.DIV);
		fp.register(BasicFunctions.RES);
		fp.register(BasicFunctions.EQ);

		VariableProvider vars = new VariableProvider();
		def.add(fp);
		def.add(vars);

		factory.register(def);

		Calculation calculation = new Calculation(factory);

		try {


			File test = new File("testprogram.calc");

			StringBuilder builder = new StringBuilder();

			try {
				BufferedReader reader = new BufferedReader(new FileReader(test));
				String read = null;
				while ((read = reader.readLine()) != null)
					builder.append(read);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

			calculation.prepare(builder);


			vars.getVariable("x").set(new NumberNode(1));
			vars.getVariable("y").set(new NumberNode(2));

			Calculation clone = (Calculation) calculation.clone();
			clone.calculate();
			System.out.println(clone.algorithm);

			vars.getVariable("x").set(new NumberNode(3));
			vars.getVariable("y").set(new NumberNode(4));
			calculation.calculate();
			System.out.println(calculation.algorithm);

		} catch (ParseException | CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}


	public static void main(String[] args) {
		new Main().run();
	}

}
