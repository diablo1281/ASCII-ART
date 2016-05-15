package pl.edu.pwr.pp;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageFileReader {

	/**
	 * Metoda czyta plik pgm i zwraca tablicę odcieni szarości.
	 * @param fileName nazwa pliku pgm
	 * @return tablica odcieni szarości odczytanych z pliku
	 * @throws URISyntaxException jeżeli plik nie istnieje
	 */
	public int[][] readPgmFile(String fileName) throws URISyntaxException {
		int columns = 0;
		int rows = 0;
		int[][] intensities = null;
		
		Path path = this.getPathToFile(fileName);
		
		try (BufferedReader reader = Files.newBufferedReader(path)) {
			// w kolejnych liniach kodu można / należy skorzystać z metody
			String header = reader.readLine();

			// pierwsza linijka pliku pgm powinna zawierać P2
			if(!header.equals("P2"))
				return null;
			
			// druga linijka pliku pgm powinna zawierać komentarz rozpoczynający
			// się od #
			header = reader.readLine();
			if(header.charAt(0) != '#')
				return null;
			
			// trzecia linijka pliku pgm powinna zawierać dwie liczby - liczbę
			// kolumn i liczbę wierszy (w tej kolejności). Te wartości należy
			// przypisać do zmiennych columns i rows.
			String[] numbers = reader.readLine().split(" ");
			columns = Integer.parseInt(numbers[0]);
			rows = Integer.parseInt(numbers[1]);
			
			// czwarta linijka pliku pgm powinna zawierać 255 - najwyższą
			// wartość odcienia szarości w pliku
			if(Integer.parseInt(reader.readLine()) > 255)
				return null;
			
			
			// inicjalizacja tablicy na odcienie szarości
			intensities = new int[rows][];

			for (int i = 0; i < rows; i++) {
				intensities[i] = new int[columns];
			}

			// kolejne linijki pliku pgm zawierają odcienie szarości kolejnych
			// pikseli rozdzielone spacjami
			String line = null;
			int currentRow = 0;
			int currentColumn = 0;
			while ((line = reader.readLine()) != null) {
				String[] elements = line.split(" ");
				for (int i = 0; i < elements.length; i++) {
					intensities[currentRow][currentColumn] = Integer.parseInt(elements[i]);
					// currentRow i currentColumn są na początku równe zero.
					// Należy je odpowiednio zwiększać, pamiętając o tym, żeby
					// nie wyjść poza zakres tablicy. Plik pgm może mieć w
					// wierszu dowolną ilość liczb, niekoniecznie równą liczbie
					currentColumn++;
					
					if(currentColumn >= columns)
					{
						currentColumn = 0;
						currentRow++;
					}
					
					if(currentRow >= rows)
					{
						return intensities;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return intensities;
	}
	
	private Path getPathToFile(String fileName) throws URISyntaxException {
		URI uri = ClassLoader.getSystemResource(fileName).toURI();
		return Paths.get(uri);
	}
	
}
