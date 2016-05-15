package pl.edu.pwr.pp;

import java.net.URISyntaxException;

public class AsciiArtApplication {

	public static void main(String[] args) {
		
		String[] images = new String[]{"Marilyn_Monroe", "Mona_Lisa", "Sierpinski_Triangle"};
		String pgmExtension = ".pgm";
		String txtExtension = ".txt";
		
		ImageFileReader imageFileReader = new ImageFileReader(); 
		ImageFileWriter imageFileWriter = new ImageFileWriter();
		
		for (String imageName : images) {
			try {
				// przeczytaj plik pgm
				int[][] intensities = imageFileReader.readPgmFile(imageName + pgmExtension);
				// przekształć odcienie szarości do znaków ASCII
				char[][] ascii = ImageConverter.intensitiesToAscii(intensities);
				// zapisz ASCII art do pliku tekstowego
				imageFileWriter.saveToTxtFile(ascii, imageName + txtExtension);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			} 
		}
	}
}
