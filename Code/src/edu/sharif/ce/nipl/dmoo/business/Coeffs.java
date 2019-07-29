package edu.sharif.ce.nipl.dmoo.business;

import com.fuzzylite.Engine;
import com.fuzzylite.FuzzyLite;
import com.fuzzylite.Op;
import com.fuzzylite.rule.Rule;
import com.fuzzylite.rule.RuleBlock;
import com.fuzzylite.term.Triangle;
import com.fuzzylite.variable.InputVariable;
import com.fuzzylite.variable.OutputVariable;

public class Coeffs {

	public static int calSMP(int last_change , int current_iter , int max_iter){

		double result = 5;
		double t = 0;
		double et = 0;
		double kasret=0;
		double MRmin = 4;
		double MRmax = 6;
		double temp=0;
		if(last_change != 0)
			//result -= 2 * ((double)(current_iter - last_change) / (max_iter));
			t = 100 * ((double) 1 / 5) * ((double)(current_iter - last_change) / 25);
		et = Math.pow(Math.E, t);
		kasret = (double)( 1 / et);
		temp = (double)(1) / (double)( 1 - Math.log10(kasret));
		result = MRmin - (( MRmin - MRmax) * temp);


		//System.out.println((int)result);
		return (int) result;
	}

	
		
	
	public static double calCDC(double prevCDCValue , double hvrValue, double changeValue){
		Engine engine = new Engine();
		engine.setName("simple-CDC");

		InputVariable prevCDC = new InputVariable();
		prevCDC.setName("PREVCDC");
		prevCDC.setRange(0.1, 1.000);
		prevCDC.addTerm(new Triangle("LOW", 0.1, 0.2, 0.3));
		prevCDC.addTerm(new Triangle("MEDIUM", 0.25, 0.45, 0.75));
		prevCDC.addTerm(new Triangle("HIGH", 0.70, 0.85, 1.000));
		engine.addInputVariable(prevCDC);

		InputVariable HVR = new InputVariable();
		HVR.setName("HVR");
		HVR.setRange(0.9, 1);
		HVR.addTerm(new Triangle("LOW", 0.900, 0.920, 0.950));
		HVR.addTerm(new Triangle("MEDIUM", 0.940, 0.950,0.998 ));
		HVR.addTerm(new Triangle("HIGH", 0.990, 0.9999, 1));
		engine.addInputVariable(HVR);
		
		InputVariable changeVal = new InputVariable();
		changeVal.setName("ChangeVal");
		changeVal.setRange(-1, 1);
		changeVal.addTerm(new Triangle("LOW", -1, 0, 0.15));
		changeVal.addTerm(new Triangle("MEDIUM", 0.1, 0.3, 0.6));
		changeVal.addTerm(new Triangle("HIGH", 0.5, 0.8, 1));
		engine.addInputVariable(changeVal);


		OutputVariable nextCDC = new OutputVariable();
		nextCDC.setName("NEXTCDC");
		nextCDC.setRange(0, 1.000);
		nextCDC.addTerm(new Triangle("LOW", 0.1, 0.2, 0.3));
		nextCDC.addTerm(new Triangle("MEDIUM", 0.25, 0.45, 0.75));
		nextCDC.addTerm(new Triangle("HIGH", 0.70, 0.85, 1.000));
		nextCDC.setDefaultValue(0.8);
		engine.addOutputVariable(nextCDC);

		RuleBlock ruleBlock = new RuleBlock();
		
		ruleBlock.addRule(Rule.parse("if HVR is LOW and ChangeVal is LOW and PREVCDC is LOW then NEXTCDC is MEDIUM", engine));
		ruleBlock.addRule(Rule.parse("if HVR is LOW and ChangeVal is LOW and PREVCDC is MEDIUM then NEXTCDC is HIGH", engine));
		ruleBlock.addRule(Rule.parse("if HVR is LOW and ChangeVal is LOW and PREVCDC is HIGH then NEXTCDC is HIGH", engine));
		ruleBlock.addRule(Rule.parse("if HVR is LOW and ChangeVal is MEDIUM and PREVCDC is LOW then NEXTCDC is MEDIUM", engine));
		ruleBlock.addRule(Rule.parse("if HVR is LOW and ChangeVal is MEDIUM and PREVCDC is MEDIUM then NEXTCDC is MEDIUM", engine));
		ruleBlock.addRule(Rule.parse("if HVR is LOW and ChangeVal is MEDIUM and PREVCDC is HIGH then NEXTCDC is MEDIUM", engine));
		ruleBlock.addRule(Rule.parse("if HVR is LOW and ChangeVal is HIGH and PREVCDC is LOW then NEXTCDC is MEDIUM", engine));
		ruleBlock.addRule(Rule.parse("if HVR is LOW and ChangeVal is HIGH and PREVCDC is MEDIUM then NEXTCDC is HIGH", engine));
		ruleBlock.addRule(Rule.parse("if HVR is LOW and ChangeVal is HIGH and PREVCDC is HIGH then NEXTCDC is HIGH", engine));

		ruleBlock.addRule(Rule.parse("if HVR is MEDIUM and ChangeVal is LOW and PREVCDC is LOW then NEXTCDC is MEDIUM", engine));
		ruleBlock.addRule(Rule.parse("if HVR is MEDIUM and ChangeVal is LOW and PREVCDC is MEDIUM then NEXTCDC is MEDIUM", engine));
		ruleBlock.addRule(Rule.parse("if HVR is MEDIUM and ChangeVal is LOW and PREVCDC is HIGH then NEXTCDC is HIGH", engine));
		ruleBlock.addRule(Rule.parse("if HVR is MEDIUM and ChangeVal is MEDIUM and PREVCDC is LOW then NEXTCDC is MEDIUM", engine));
		ruleBlock.addRule(Rule.parse("if HVR is MEDIUM and ChangeVal is MEDIUM and PREVCDC is MEDIUM then NEXTCDC is MEDIUM", engine));
		ruleBlock.addRule(Rule.parse("if HVR is MEDIUM and ChangeVal is MEDIUM and PREVCDC is HIGH then NEXTCDC is HIGH", engine));
		ruleBlock.addRule(Rule.parse("if HVR is MEDIUM and ChangeVal is HIGH and PREVCDC is LOW then NEXTCDC is MEDIUM", engine));
		ruleBlock.addRule(Rule.parse("if HVR is MEDIUM and ChangeVal is HIGH and PREVCDC is MEDIUM then NEXTCDC is HIGH", engine));
		ruleBlock.addRule(Rule.parse("if HVR is MEDIUM and ChangeVal is HIGH and PREVCDC is HIGH then NEXTCDC is HIGH", engine));
		
		ruleBlock.addRule(Rule.parse("if HVR is HIGH and ChangeVal is LOW and PREVCDC is LOW then NEXTCDC is LOW", engine));
		ruleBlock.addRule(Rule.parse("if HVR is HIGH and ChangeVal is LOW and PREVCDC is MEDIUM then NEXTCDC is MEDIUM", engine));
		ruleBlock.addRule(Rule.parse("if HVR is HIGH and ChangeVal is LOW and PREVCDC is HIGH then NEXTCDC is HIGH", engine));
		ruleBlock.addRule(Rule.parse("if HVR is HIGH and ChangeVal is MEDIUM and PREVCDC is LOW then NEXTCDC is LOW", engine));
		ruleBlock.addRule(Rule.parse("if HVR is HIGH and ChangeVal is MEDIUM and PREVCDC is MEDIUM then NEXTCDC is MEDIUM", engine));
		ruleBlock.addRule(Rule.parse("if HVR is HIGH and ChangeVal is MEDIUM and PREVCDC is HIGH then NEXTCDC is HIGH", engine));
		ruleBlock.addRule(Rule.parse("if HVR is HIGH and ChangeVal is HIGH and PREVCDC is LOW then NEXTCDC is HIGH", engine));
		ruleBlock.addRule(Rule.parse("if HVR is HIGH and ChangeVal is HIGH and PREVCDC is MEDIUM then NEXTCDC is HIGH", engine));
		ruleBlock.addRule(Rule.parse("if HVR is HIGH and ChangeVal is HIGH and PREVCDC is HIGH then NEXTCDC is HIGH", engine));
		
		engine.addRuleBlock(ruleBlock);

		engine.configure("Minimum", "Maximum", "Minimum", "Maximum", "Centroid");

		StringBuilder status = new StringBuilder();
		if (!engine.isReady(status)) {
			throw new RuntimeException("Engine not ready. "
					+ "The following errors were encountered:\n" + status.toString());
		}


		//Define the inputs
		prevCDC.setInputValue(prevCDCValue);
		HVR.setInputValue(hvrValue);
		changeVal.setInputValue(changeValue);
		engine.process();

		return nextCDC.getOutputValue();
		

	}

	public static double calMR(double MR , int last_change , int current_iter , int max_iter){
		double result = MR;
		double t = 0;
		double et = 0;
		double kasret=0;
		double MRmin = 0.94;
		double MRmax = 0.99;
		double temp=0;
		if(last_change != 0)
			//result -= 2 * ((double)(current_iter - last_change) / (max_iter));
			t = 100 * ((double) 1 / 5) * ((double)(current_iter - last_change) / 25);
		et = Math.pow(Math.E, t);
		kasret = (double)( 1 / et);
		temp = (double)(1) / (double)( 1 - Math.log10(kasret));
		result = MRmin - (( MRmin - MRmax) * temp);


		//System.out.println(result);
		return result;
		//return 0.98;
	}

	public
	static double calC(int last_change , int current_iter , int max_iter){
		//return C - (double)(max_iter - iter) / (2 * max_iter);
		double result = 2;
		double t = 0;
		double et = 0;
		double kasret=0;
		double MRmin = 2;
		double MRmax = 1.5;
		double temp=0;
		if(last_change != 0)
			//result -= 2 * ((double)(current_iter - last_change) / (max_iter));
			t = 100 * ((double) 1 / 5) * ((double)(current_iter - last_change) / 25);
		et = Math.pow(Math.E, t);
		kasret = (double)( 1 / et);
		temp = (double)(1) / (double)( 1 - Math.log10(kasret));
		result = MRmin - (( MRmin - MRmax) * temp);


		//System.out.println(result);
		return result;
		//return 0.98;
	}

	public static double calW(int last_change , int current_iter , int max_iter){
		//return W + (double)(max_iter - iter) / (2 * max_iter);
		double result = 0.7;
		double t = 0;
		double et = 0;
		double kasret=0;
		double MRmin = 0.3;
		double MRmax = 0.8;
		double temp=0;
		if(last_change != 0)
			//result -= 2 * ((double)(current_iter - last_change) / (max_iter));
			t = 100 * ((double) 1 / 5) * ((double)(current_iter - last_change) / 25);
		et = Math.pow(Math.E, t);
		kasret = (double)( 1 / et);
		temp = (double)(1) / (double)( 1 - Math.log10(kasret));
		result = MRmin - (( MRmin - MRmax) * temp);


		//System.out.println(result);
		return result;
		//return 0.98;
	}


}
