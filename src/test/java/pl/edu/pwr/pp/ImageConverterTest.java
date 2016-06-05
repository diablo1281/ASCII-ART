package pl.edu.pwr.pp;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pl.edu.pwr.pp.ImageConverter.ConvertType;
import pl.edu.pwr.pp.ImageConverter.ScaleType;

public class ImageConverterTest {
	private BufferedImage image;

	@Before
	public void setUp() {
		try {
			image = ImageIO.read(ClassLoader.getSystemResource("Image_to_test.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void shouldResizeImageWithPreserveRatio() {
		BufferedImage result = ImageConverter.resizeImage(image, 2 * image.getWidth());

		Assert.assertThat(result.getHeight(), Matchers.is(Matchers.equalTo(2 * image.getHeight())));
	}

	@Test
	public void shouldResizeImageWithPreserveRatioUsingEnum() {
		BufferedImage result = ImageConverter.resizeImage(image, ScaleType.Signs_160);

		Assert.assertThat(result.getWidth(), Matchers.is(Matchers.equalTo(160)));
	}

	@Test
	public void shouldConvertToAsciiLow() {
		String fileName = "test_image_low.txt";
		char[][] test_ascii = new char[300][400];
		try (BufferedReader reader = Files
				.newBufferedReader(Paths.get(ClassLoader.getSystemResource(fileName).toURI()))) {

			for (int y = 0; y < test_ascii.length; y++) {
				String line = reader.readLine();
				for (int x = 0; x < test_ascii[0].length; x++)
					test_ascii[y][x] = line.charAt(x);
			}

			reader.close();
		} catch (IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		char[][] func_ascii = ImageConverter.intensitiesToAscii(
				ImageConverter.writeIntensities(ImageConverter.convertFromRGBToGrey(image)), ConvertType.Low);

		for (int y = 0; y < func_ascii.length; y++)
			for (int x = 0; x < func_ascii[y].length; x++)
				assertThat(func_ascii[y][x], is(equalTo(test_ascii[y][x])));

	}

	@Test
	public void shouldConvertToAsciiLowNegative() {
		String fileName = "test_image_low_neg.txt";
		char[][] test_ascii = new char[300][400];
		try (BufferedReader reader = Files
				.newBufferedReader(Paths.get(ClassLoader.getSystemResource(fileName).toURI()))) {

			for (int y = 0; y < test_ascii.length; y++) {
				String line = reader.readLine();
				for (int x = 0; x < test_ascii[0].length; x++)
					test_ascii[y][x] = line.charAt(x);
			}

			reader.close();
		} catch (IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		char[][] func_ascii = ImageConverter.intensitiesToAscii(
				ImageConverter.writeIntensities(ImageConverter.convertFromRGBToGrey(image)), ConvertType.LowNegative);

		for (int y = 0; y < func_ascii.length; y++)
			for (int x = 0; x < func_ascii[y].length; x++)
				assertThat(func_ascii[y][x], is(equalTo(test_ascii[y][x])));

	}

	@Test
	public void shouldConvertToAsciiHigh() {
		String fileName = "test_image_high.txt";
		char[][] test_ascii = new char[300][400];
		try (BufferedReader reader = Files
				.newBufferedReader(Paths.get(ClassLoader.getSystemResource(fileName).toURI()))) {

			for (int y = 0; y < test_ascii.length; y++) {
				String line = reader.readLine();
				for (int x = 0; x < test_ascii[0].length; x++)
					test_ascii[y][x] = line.charAt(x);
			}

			reader.close();
		} catch (IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		char[][] func_ascii = ImageConverter.intensitiesToAscii(
				ImageConverter.writeIntensities(ImageConverter.convertFromRGBToGrey(image)), ConvertType.High);

		for (int y = 0; y < func_ascii.length; y++)
			for (int x = 0; x < func_ascii[y].length; x++)
				assertThat(func_ascii[y][x], is(equalTo(test_ascii[y][x])));

	}

	@Test
	public void shouldConvertToAsciiHighNegative() {
		String fileName = "test_image_high_neg.txt";
		char[][] test_ascii = new char[300][400];
		try (BufferedReader reader = Files
				.newBufferedReader(Paths.get(ClassLoader.getSystemResource(fileName).toURI()))) {

			for (int y = 0; y < test_ascii.length; y++) {
				String line = reader.readLine();
				for (int x = 0; x < test_ascii[0].length; x++)
					test_ascii[y][x] = line.charAt(x);
			}

			reader.close();
		} catch (IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		char[][] func_ascii = ImageConverter.intensitiesToAscii(
				ImageConverter.writeIntensities(ImageConverter.convertFromRGBToGrey(image)), ConvertType.HighNegative);

		for (int y = 0; y < func_ascii.length; y++)
			for (int x = 0; x < func_ascii[y].length; x++)
				assertThat(func_ascii[y][x], is(equalTo(test_ascii[y][x])));

	}

	@Test
	public void shouldCheckConvertTypeToString() {
		assertThat(ConvertType.High.toString(), is(equalTo("Wysoka")));
	}

	@Test
	public void shouldCheckScaleTypeToString() {
		assertThat(ScaleType.Signs_160.toString(), is(equalTo("160 znaków")));
	}
}
