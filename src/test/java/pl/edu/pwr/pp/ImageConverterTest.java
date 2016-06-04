package pl.edu.pwr.pp;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

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
}
