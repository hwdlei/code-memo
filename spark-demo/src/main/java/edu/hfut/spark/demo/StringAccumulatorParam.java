package edu.hfut.spark.demo;

import org.apache.spark.AccumulatorParam;

public class StringAccumulatorParam implements AccumulatorParam<String> {

	@Override
	public String addInPlace(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return arg0 + arg1;
	}

	@Override
	public String zero(String arg0) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String addAccumulator(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return arg0 + arg1;
	}

}
