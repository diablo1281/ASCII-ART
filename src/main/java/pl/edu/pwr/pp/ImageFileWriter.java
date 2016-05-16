package pl.edu.pwr.pp;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

import static java.nio.file.StandardOpenOption.*;

public class ImageFileWriter {

	public void saveToTxtFile(char[][] ascii, String fileName) {
		// np. korzystając z java.io.PrintWriter
		// TODO Wasz kod
		
		Path path = Paths.get(Paths.get("").toAbsolutePath().toString() + "/results/" + fileName);
		
		if(!Files.exists(path))
		{
			try {
				Files.createFile(path);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, WRITE, CREATE, TRUNCATE_EXISTING)){
			
			int rows = ascii.length;
			int columns = ascii[0].length;
			
			for(int y = 0; y < rows; y++)
			{
				for(int x = 0; x < columns; x++)
					writer.write(ascii[y][x] + "\t");
				
				writer.write("\n");
			}
			
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
