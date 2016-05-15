package pl.edu.pwr.pp;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ImageFileReaderTest {
	
	ImageFileReader imageReader;
	
	@Before
	public void setUp() {
		imageReader = new ImageFileReader();
	}

	@Test
	public void shouldReadSequenceFrom0To255GivenTestImage() {
		// given
		String fileName = "testImage.pgm";
		// when
		int[][] intensities = null;
		try {
			intensities = imageReader.readPgmFile(fileName);
		} catch (URISyntaxException e) {
			Assert.fail("Should read the file");
		}
		// then
		int counter = 0;
		for (int[] row : intensities) {
			for (int intensity : row) {
				assertThat(intensity, is(equalTo(counter++)));
			}
		}
	}
	
	@Test
	public void shouldThrowExceptionWhenFileDontExist() {
		// given
		String fileName = "nonexistent.pgm";
		try {
			// when
			imageReader.readPgmFile(fileName);
			// then
			Assert.fail("Should throw exception");
		} catch (Exception e) {
			assertThat(e, is(instanceOf(NullPointerException.class)));
		}
		
	}
}
