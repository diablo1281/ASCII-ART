package pl.edu.pwr.pp;

public class ImageConverter {

	/**
	 * Znaki odpowiadające kolejnym poziomom odcieni szarości - od czarnego (0)
	 * do białego (255).
	 */
	public static String INTENSITY_2_ASCII = "@%#*+=-:. ";

	/**
	 * Metoda zwraca znak odpowiadający danemu odcieniowi szarości. Odcienie
	 * szarości mogą przyjmować wartości z zakresu [0,255]. Zakres jest dzielony
	 * na równe przedziały, liczba przedziałów jest równa ilości znaków w
	 * {@value #INTENSITY_2_ASCII}. Zwracany znak jest znakiem dla przedziału,
	 * do którego należy zadany odcień szarości.
	 * 
	 * 
	 * @param intensity
	 *            odcień szarości w zakresie od 0 do 255
	 * @return znak odpowiadający zadanemu odcieniowi szarości
	 */
	public static char IntToAscii(int intensity) {
		char sign = 0;

		if(intensity >= 0 && intensity <= 25)
			sign = INTENSITY_2_ASCII.charAt(0);
		else if(intensity >= 26 && intensity <= 51)
			sign = INTENSITY_2_ASCII.charAt(1);
		else if(intensity >= 52 && intensity <= 76)
			sign = INTENSITY_2_ASCII.charAt(2);
		else if(intensity >= 77 && intensity <= 102)
			sign = INTENSITY_2_ASCII.charAt(3);
		else if(intensity >= 103 && intensity <= 127)
			sign = INTENSITY_2_ASCII.charAt(4);
		else if(intensity >= 128 && intensity <= 153)
			sign = INTENSITY_2_ASCII.charAt(5);
		else if(intensity >= 154 && intensity <= 179)
			sign = INTENSITY_2_ASCII.charAt(6);
		else if(intensity >= 180 && intensity <= 204)
			sign = INTENSITY_2_ASCII.charAt(7);
		else if(intensity >= 205 && intensity <= 230)
			sign = INTENSITY_2_ASCII.charAt(8);
		else if(intensity >= 231 && intensity <= 255)
			sign = INTENSITY_2_ASCII.charAt(9);
		
		return sign;
	}

	/**
	 * Metoda zwraca dwuwymiarową tablicę znaków ASCII mając dwuwymiarową
	 * tablicę odcieni szarości. Metoda iteruje po elementach tablicy odcieni
	 * szarości i dla każdego elementu wywołuje {@ref #intensityToAscii(int)}.
	 * 
	 * @param intensities
	 *            tablica odcieni szarości obrazu
	 * @return tablica znaków ASCII
	 */
	public static char[][] intensitiesToAscii(int[][] intensities) {
		
		int rows = intensities.length;
		int columns = intensities[0].length;
		
		char[][] ascii;
		
		ascii = new char[rows][];

		for (int i = 0; i < rows; i++) {
			ascii[i] = new char[columns];
		}
		
		for(int y = 0; y < rows; y++)
			for(int x = 0; x < columns; x++)				
				ascii[y][x] = IntToAscii(intensities[y][x]);

		
		return ascii;
	}

}
