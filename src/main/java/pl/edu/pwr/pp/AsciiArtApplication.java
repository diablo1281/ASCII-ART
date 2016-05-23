package pl.edu.pwr.pp;

import java.net.URISyntaxException;

import pl.edu.pwr.pp.ImageConverter.ConvertType;

public class AsciiArtApplication {

	public static void main(String[] args) {
		
		System.out.println("Program start...");
		System.out.println();

		
		String[] images = new String[]{"Marilyn_Monroe", "Mona_Lisa", "Sierpinski_Triangle"};
		String pgmExtension = ".pgm";
		String txtExtension = ".txt";
		
		ImageFileReader imageFileReader = new ImageFileReader(); 
		ImageFileWriter imageFileWriter = new ImageFileWriter();
		
		for (String imageName : images) {
			try {
				// przeczytaj plik pgm
				System.out.println("Odczyt pliku: " + imageName);
				int[][] intensities = imageFileReader.readPgmFile(imageName + pgmExtension);
				// przekształć odcienie szarości do znaków ASCII
				System.out.println("Konwersja pliku: " + imageName);
				char[][] ascii = ImageConverter.intensitiesToAscii(intensities, ConvertType.Low);
				// zapisz ASCII art do pliku tekstowego
				System.out.println("Zapis pliku: " + imageName);
				imageFileWriter.saveToTxtFile(ascii, imageName + txtExtension);
				
				System.out.println();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			} 
		}
		
		System.out.println("DONE");
	}
}
