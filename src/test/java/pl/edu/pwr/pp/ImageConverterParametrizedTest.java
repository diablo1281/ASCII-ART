package pl.edu.pwr.pp;

import java.util.Arrays;
import java.util.Collection;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ImageConverterParametrizedTest {

	@Parameters
	public static Collection<Object[]> data() {
		// @formatter:off
		return Arrays.asList(new Object[][] {
			{0, '@'}, {12, '@'}, {25, '@'}, 
			{26, '%'}, {34, '%'}, {51, '%'},
			{52, '#'}, {66, '#'}, {76, '#'},
			{77, '*'}, {89, '*'}, {102, '*'},
			{103, '+'}, {115, '+'}, {127, '+'},
			{128, '='}, {142, '='}, {153,'='},
			{154,'-'}, {162,'-'}, {179,'-'},
			{180, ':'}, {192, ':'}, {204, ':'},
			{205, '.'}, {215, '.'}, {230, '.'},
			{231, ' '}, {245, ' '}, {255, ' '}
		});
		// @formatter:on
	}

	private int input;
	private char expected;

	public ImageConverterParametrizedTest(int input, char expected) {
		this.input = input;
		this.expected = expected;
	}

	@Test
	public void shouldReturnRightCharacterForIntensity() {
		MatcherAssert.assertThat(ImageConverter.IntToAsciiLow(this.input),
				Matchers.is(Matchers.equalTo(this.expected)));

	}
}
