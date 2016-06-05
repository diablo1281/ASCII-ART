package pl.edu.pwr.pp;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

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

	@Test
	public void shouldReadSequenceFrom0To255GivenTestImageByPath() {
		// given
		int[][] intensities = null;
		try {
			Path path = Paths.get(ClassLoader.getSystemResource("testImage.pgm").toURI());
			intensities = imageReader.readPgmFile(path);
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
	public void shouldReturnNullForIntensities() throws MalformedURLException {
		// given
		int[][] intensities = null;
		URL url = new URL("http://www.google.pl");
		intensities = imageReader.readPgmFile(url);
		// then
		assertThat(intensities, is(equalTo(null)));
	}
}
