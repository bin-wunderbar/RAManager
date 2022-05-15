package bll;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BClientTest {

	@Test
	void testGetDiscount_cp1() {
		double expectedResult = 0.0;
		
		double realValue = BClient.getDiscount("ABCD", "ABCD");
		assertEquals (expectedResult, realValue, 0.1);
	}
	@Test
	void testGetDiscount_cp2() {
		double expectedResult = 0.0;
		
		double realValue = BClient.getDiscount("ABCD", "500");
		assertEquals (expectedResult, realValue, 0.1);
	}
	@Test
	void testGetDiscount3_cp3() {
		double expectedResult = 0.0;
		
		double realValue = BClient.getDiscount("5", "");
		assertEquals (expectedResult, realValue, 0.1);
	}
	@Test
	void testGetDiscount_cp4() {
		double expectedResult = 0.0;
		
		double realValue = BClient.getDiscount("4", "999");
		assertEquals (expectedResult, realValue, 0.1);
	}
	@Test
	void testGetDiscount_cp5() {
		double expectedResult = 2.0;
		
		double realValue = BClient.getDiscount("5", "999");
		assertEquals (expectedResult, realValue, 0.1);
	}
	@Test
	void testGetDiscount_cp6() {
		double expectedResult = 2.0;
		
		double realValue = BClient.getDiscount("6", "999");
		assertEquals (expectedResult, realValue, 0.1);
	}
	@Test
	void testGetDiscount_cp7() {
		double expectedResult = 2.0;
		
		double realValue = BClient.getDiscount("9", "999");
		assertEquals (expectedResult, realValue, 0.1);
	}
	@Test
	void testGetDiscount_cp8() {
		double expectedResult = 5.0;
		
		double realValue = BClient.getDiscount("10", "999");
		assertEquals (expectedResult, realValue, 0.1);
	}
	@Test
	void testGetDiscount_cp9() {
		double expectedResult = 5.0;
		
		double realValue = BClient.getDiscount("4", "1000");
		assertEquals (expectedResult, realValue, 0.1);
	}
	@Test
	void testGetDiscount_cp10() {
		double expectedResult = 7.0;
		
		double realValue = BClient.getDiscount("5", "1000");
		assertEquals (expectedResult, realValue, 0.1);
	}
	@Test
	void testGetDiscount_cp11() {
		double expectedResult = 7.0;
		
		double realValue = BClient.getDiscount("9", "1000");
		assertEquals (expectedResult, realValue, 0.1);
	}	@Test
	void testGetDiscount_cp12() {
		double expectedResult = 10.0;
		
		double realValue = BClient.getDiscount("10", "1000");
		assertEquals (expectedResult, realValue, 0.1);
	}
	@Test
	void testGetDiscount_cp13() {
		double expectedResult = 10.0;
		
		double realValue = BClient.getDiscount("11", "1000");
		assertEquals (expectedResult, realValue, 0.1);
	}
	@Test
	void testGetDiscount_cp14() {
		double expectedResult = 7.0;
		
		double realValue = BClient.getDiscount("5", "1001");
		assertEquals (expectedResult, realValue, 0.1);
	}

	
}
