package zx.soft.utils.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloWorld {

	final Logger logger = LoggerFactory.getLogger(HelloWorld.class);
	Integer t = 10;
	Integer oldT = 9;

	public void setTemperature(Integer temperature) {

		oldT = t;
		t = temperature;

		logger.debug("Temperature set to {}. Old temperature was {}.", t, oldT);

		if (temperature.intValue() > 50) {
			logger.info("Temperature has risen above 50 degrees.");
		}

	}

	public static void main(String[] args) {
		//		// print internal state
		//		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		//		StatusPrinter.print(lc);
		HelloWorld hello = new HelloWorld();
		hello.setTemperature(51);
	}

}