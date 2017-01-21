package org.problemchimp.banker.task;

import java.util.regex.Pattern;

/**
 * Identify the type of file from its text. Currently Yorkshire Bank and
 * Smile are supported.
 * 
 * @author Heather McCartney
 *
 */
public class Identify {
	
	public enum Type {
		SMILE(Pattern.compile("Smile Current Account")), 
		YORKSHIRE_BANK(Pattern.compile("Yorkshire Bank")), 
		UNKNOWN(null);
		
		Pattern pattern;
		
		Type(Pattern pattern) {
			this.pattern = pattern;
		}
	}

	public Type run(Iterable<String> lines) {
		for (String line : lines) {
			for (Type t : Type.values()) {
				if (t.pattern != null && t.pattern.matcher(line).find()) {
					return t;
				}
			}
		}
		return Type.UNKNOWN;
	}
}
