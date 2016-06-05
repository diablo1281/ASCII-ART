package pl.edu.pwr.pp;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import java.nio.charset.StandardCharsets;
import static java.nio.file.StandardOpenOption.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;

public class ImageFileWriter {

	public enum SaveType {
		Txt("Plik tekstowy"), Img("Plik graficzny");

		SaveType(String name) {
			try {
				Field fieldName = getClass().getSuperclass().getDeclaredField("name");
				fieldName.setAccessible(true);
				fieldName.set(this, name);
				fieldName.setAccessible(false);
			} catch (Exception e) {
			}
		}
	}

	public void saveToTxtFile(char[][] ascii, String fileName) {
		Path path = Paths.get(Paths.get("").toAbsolutePath().toString() + "/results/" + fileName);

		if (!Files.exists(path)) {
			try {
				Files.createFile(path);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, WRITE, CREATE,
				TRUNCATE_EXISTING)) {

			int rows = ascii.length;
			int columns = ascii[0].length;

			for (int y = 0; y < rows; y++) {
				for (int x = 0; x < columns; x++)
					writer.write(ascii[y][x] + "\t");

				writer.write("\n");
			}

			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveToTxtFile(char[][] ascii, Path path) {
		if (!Files.exists(path)) {
			try {
				Files.createFile(path);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		System.out.println("Saving ASCII...");

		try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, WRITE, CREATE,
				TRUNCATE_EXISTING)) {

			int rows = ascii.length;
			int columns = ascii[0].length;

			for (int y = 0; y < rows; y++) {
				for (int x = 0; x < columns; x++)
					writer.write(ascii[y][x] + "");

				writer.write("\n");
			}

			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveToImgFile(char[][] ascii, Path path) {
		if (!Files.exists(path)) {
			try {
				Files.createFile(path);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		try {
			System.out.println("Converting ASCII to image...");

			BufferedImage image = new BufferedImage(ascii[0].length, ascii.length, BufferedImage.TYPE_BYTE_GRAY);
			Graphics g = image.createGraphics();
			g.setFont(new Font("SanSerif", Font.PLAIN, 12));
			FontMetrics fm = g.getFontMetrics();
			int height = fm.getHeight();
			int width = fm.charWidth('@');
			int offset = fm.getAscent();

			image = new BufferedImage(ascii[0].length * width, ascii.length * height, BufferedImage.TYPE_BYTE_GRAY);
			g = image.getGraphics();

			BufferedImage background = ImageIO.read(ClassLoader.getSystemResource("background.jpg"));
			g.drawImage(background, 0, 0, ascii[0].length * width, ascii.length * height, null);

			g.setFont(new Font("SanSerif", Font.PLAIN, 12));
			g.setColor(Color.black);
			String line;
			for (int y = 0; y < ascii.length; y++)
				for (int x = 0; x < ascii[y].length; x++) {
					line = String.valueOf(ascii[y][x]);
					g.drawString(line, x * width, y * height + offset);
				}
			g.dispose();

			System.out.println("Saving image...");

			ImageIO.write(image, "jpg", path.toFile());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
