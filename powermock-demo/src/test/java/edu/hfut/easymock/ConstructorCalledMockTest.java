package edu.hfut.easymock;


import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.easymock.EasyMockSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Example of how to partial mock with actually calling a constructor
 *
 * @author Henri Tremblay
 */
public class ConstructorCalledMockTest extends EasyMockSupport {

	/**
	 * Class to test and partially mock
	 */
	public static abstract class TaxCalculator {

		private final BigDecimal[] values;

		public TaxCalculator(BigDecimal... values) {
			this.values = values;
		}

		protected abstract BigDecimal rate();

		public BigDecimal tax() {
			BigDecimal result = BigDecimal.ZERO;

			for (BigDecimal d : values) {
				result = result.add(d);
			}

			return result.multiply(rate());
		}
	}

	private TaxCalculator tc;

	@Before
	public void setUp() {
		tc = partialMockBuilder(TaxCalculator.class).withConstructor(BigDecimal[].class)
				.withArgs((Object) new BigDecimal[] { new BigDecimal("5"), new BigDecimal("15") }) // varargs are special since they are in fact arrays
				.createMock(); // no need to mock any methods, abstract ones are mocked by default
	}

	@After
	public void tearDown() {
		verifyAll();
	}

	@Test
	public void testTax() {

		expect(tc.rate()).andStubReturn(new BigDecimal("0.20"));
		replayAll();

		assertEquals(new BigDecimal("4.00"), tc.tax());
	}

	@Test
	public void testTax_ZeroRate() {

		expect(tc.rate()).andStubReturn(BigDecimal.ZERO);
		replayAll();

		assertEquals(BigDecimal.ZERO, tc.tax());
	}
}