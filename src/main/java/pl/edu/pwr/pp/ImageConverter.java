package pl.edu.pwr.pp;

import java.awt.image.BufferedImage;
import java.lang.reflect.Field;

public class ImageConverter {

	public enum ConvertType {
		Low("Niska"), 
		High("Wysoka");
		
		ConvertType(String name) {
	        try {
	            Field fieldName = getClass().getSuperclass().getDeclaredField("name");
	            fieldName.setAccessible(true);
	            fieldName.set(this, name);
	            fieldName.setAccessible(false);
	        } catch (Exception e) {}
	    }
	}
	
	public enum ScaleType {
		Signs_80("80 znaków"), 
		Signs_160("160 znaków"),
		Screen_width("Szerokość ekranu"),
		Not_scaled("Oryginał (bez skalowania)");
		
		ScaleType(String name) {
	        try {
	            Field fieldName = getClass().getSuperclass().getDeclaredField("name");
	            fieldName.setAccessible(true);
	            fieldName.set(this, name);
	            fieldName.setAccessible(false);
	        } catch (Exception e) {}
	    }
	}
	/**
	 * Znaki odpowiadające kolejnym poziomom odcieni szarości - od czarnego (0)
	 * do białego (255).
	 */
	private static String INTENSITY_2_ASCII_10 = "@%#*+=-:. ";
	private static String INTENSITY_2_ASCII_70 = "$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/\\|()1{}[]?-_+~<>i!lI;:,\"^`'. ";

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
	public static char IntToAsciiLow(int intensity) {
		return INTENSITY_2_ASCII_10.charAt((int)(intensity / 25.6));
	}
	
	public static char IntToAsciiHigh(int intensity) {
		return INTENSITY_2_ASCII_70.charAt((int)(intensity / 3.6571));
	}

	/**
	 * Metoda zwraca dwuwymiarową tablicę znaków ASCII mając dwuwymiarową
	 * tablicę odcieni szarości. Metoda iteruje po elementach tablicy odcieni
	 * szarości i dla każdego elementu wywołuje {@ref #intensityToAscii(int)}.
	 * 
	 * @param intensities
	 *            tablica odcieni szarości obrazu
	 *            
	 * @param type
	 * 			  określa zakres konwersji 0 - LOW, 1 - HIGH
	 * @return tablica znaków ASCII
	 */
	public static char[][] intensitiesToAscii(int[][] intensities, ConvertType type) {
		
		int rows = intensities.length;
		int columns = intensities[0].length;
		
		char[][] ascii;
		
		ascii = new char[rows][];

		for (int i = 0; i < rows; i++) {
			ascii[i] = new char[columns];
		}
		
		for(int y = 0; y < rows; y++)
			for(int x = 0; x < columns; x++)
			{
				if(type == ConvertType.Low)
					ascii[y][x] = IntToAsciiLow(intensities[y][x]);
				else
					ascii[y][x] = IntToAsciiHigh(intensities[y][x]);
			}

		
		return ascii;
	}
	
	/*public BufferedImage convertFromRGBToGrey(BufferedImage image)
	{
		BufferedImage gray_image = new BufferdImage();
		
		return gray_image;
	}
*/
}
