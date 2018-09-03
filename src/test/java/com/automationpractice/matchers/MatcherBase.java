package com.automationpractice.matchers;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

import com.automationpractice.tests.Override;

public class MatcherBase {

	// ------------------------------
	// Custom matcher implementation
	public static Matcher<String> length(Matcher<? super Integer> matcher) {
		return new FeatureMatcher<String, Integer>(matcher, "a String of length that", "length") {
			@Override
			protected Integer featureValueOf(String actual) {
				return actual.length();
			}
		};

	}

}
