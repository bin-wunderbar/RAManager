package bll;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BEmployeeTest {

	@Test
	void testGetCommission_cp1() 
	{
		double expectedResult = 0.0;
		
		double realValue = BEmployee.getCommission ("ABCD", "ABCD");
		assertEquals (expectedResult, realValue, 0.1);
	}
	@Test
	void testGetCommission_cp2() 
	{
		double expectedResult = 0.0;
		
		double realValue = BEmployee.getCommission ("ABCD", "19");
		assertEquals (expectedResult, realValue, 0.1);
	}
	@Test
	void testGetCommission_cp3() 
	{
		double expectedResult = 0.0;
		
		double realValue = BEmployee.getCommission ("49", "ABCD");
		assertEquals (expectedResult, realValue, 0.1);
	}
	@Test
	void testGetCommission_cp4() 
	{
		double expectedResult = 0.0;
		
		double realValue = BEmployee.getCommission ("49", "19");
		assertEquals (expectedResult, realValue, 0.1);
	}
	@Test
	void testGetCommission_cp5() 
	{
		double expectedResult = 50.0;
		
		double realValue = BEmployee.getCommission ("49", "20");
		assertEquals (expectedResult, realValue, 0.1);
	}
	@Test
	void testGetCommission_cp6() 
	{
		double expectedResult = 100.0;
		
		double realValue = BEmployee.getCommission ("50", "19");
		assertEquals (expectedResult, realValue, 0.1);
	}
	@Test
	void testGetCommission_cp7() 
	{
		double expectedResult = 150.0;
		
		double realValue = BEmployee.getCommission ("50", "20");
		assertEquals (expectedResult, realValue, 0.1);
	}
	@Test
	void testGetCommission_cp8() 
	{
		double expectedResult = 150.0;
		
		double realValue = BEmployee.getCommission ("51", "20");
		assertEquals (expectedResult, realValue, 0.1);
	}
	@Test
	void testGetCommission_cp9() 
	{
		double expectedResult = 150.0;
		
		double realValue = BEmployee.getCommission ("51", "21");
		assertEquals (expectedResult, realValue, 0.1);
	}

}
