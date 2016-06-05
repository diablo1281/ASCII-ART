package pl.edu.pwr.pp;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import pl.edu.pwr.pp.ImageConverter.ConvertType;
import pl.edu.pwr.pp.ImageFileWriter.SaveType;

import static org.junit.Assert.*;

public class ImageWriterTest {
	char[][] ascii;
	ImageFileWriter imageWriter;
	Path path;

	@Before
	public void setUp() {
		imageWriter = new ImageFileWriter();
		path = Paths.get(Paths.get("").toAbsolutePath().toString() + "/test_file");
		try {
			BufferedImage image = ImageIO.read(ClassLoader.getSystemResource("Image_to_test.jpg"));
			ascii = ImageConverter.intensitiesToAscii(ImageConverter.writeIntensities(image), ConvertType.Low);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void shouldFindTxtFileInResults() {
		imageWriter.saveToTxtFile(ascii, path);
		assertTrue(Files.exists(path));
		try {
			Files.delete(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(!Files.exists(path));
	}

	@Test
	public void shouldFindImgFileInResults() {
		imageWriter.saveToImgFile(ascii, path);
		assertTrue(Files.exists(path));
		try {
			Files.delete(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(!Files.exists(path));
	}

	@Test
	public void shouldCheckSaveTypeToString() {
		assertThat(SaveType.Txt.toString(), is(equalTo("Plik tekstowy")));
	}
}
