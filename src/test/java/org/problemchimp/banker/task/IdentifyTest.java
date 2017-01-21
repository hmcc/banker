package org.problemchimp.banker.task;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.problemchimp.banker.task.Identify.Type;

@RunWith(JUnit4.class)
public class IdentifyTest {
	
	private Identify id = new Identify();

	/**
	 * Lines including the text "Smile Current Account" will be identified
	 * as Smile.
	 */
	@Test
	public void testSmile() {
		List<String> lines = Arrays.asList(
				"Date Description Money out Money in BalanceSummary", 
				"Smile Current Account", 
				"09 May BROUGHT FORWARD 123.45");
		Type t = id.run(lines);
		assertEquals(Type.SMILE, t);
	}
	
	/**
	 * Lines including the text "Yorkshire Bank" will be identified as
	 * Yorkshire Bank.
	 */
	@Test
	public void testYB() {
		List<String> lines = Arrays.asList(
				"REQUEST", 
				"^Yorkshire Bank", 
				"MR A N OTHER");
		Type t = id.run(lines);
		assertEquals(Type.YORKSHIRE_BANK, t);
	}
	
	/**
	 * Lines including neither "Yorkshire Bank" or "Smile Current Account"
	 * will be identified as unknown.
	 */
	@Test
	public void testUnknown() {
		List<String> lines = Arrays.asList(
				"REQUEST", 
				"Date Description Money out Money in BalanceSummary", 
				"MR A N OTHER");
		Type t = id.run(lines);
		assertEquals(Type.UNKNOWN, t);
	}
	
	/**
	 * If lines of both types are included, the line which appears first wins.
	 */
	@Test
	public void testBoth_YBFirst() {
		List<String> lines = Arrays.asList(
				"REQUEST", 
				"^Yorkshire Bank", 
				"MR A N OTHER",
				"Date Description Money out Money in BalanceSummary", 
				"Smile Current Account", 
				"09 May BROUGHT FORWARD 123.45");
		Type t = id.run(lines);
		assertEquals(Type.YORKSHIRE_BANK, t);
	}
}
