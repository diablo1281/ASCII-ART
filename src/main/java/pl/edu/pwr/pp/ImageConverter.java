package pl.edu.pwr.pp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
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
		return INTENSITY_2_ASCII_10.charAt((INTENSITY_2_ASCII_10.length() - 1) - (int)((double) intensity / (double)((double) 256 / (double) INTENSITY_2_ASCII_10.length())));
	}
	
	public static char IntToAsciiHigh(int intensity) {
		return INTENSITY_2_ASCII_70.charAt((INTENSITY_2_ASCII_70.length() - 1) - (int)((double) intensity / (double)((double) 256 / (double) INTENSITY_2_ASCII_70.length())));
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
	 * 			  określa zakres konwersji LOW - 10 znaków, HIGH - 70 znaków
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
	
	public static BufferedImage convertFromRGBToGrey(BufferedImage image)
	{
		BufferedImage gray_image = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		WritableRaster raster = gray_image.getRaster();
		
		for(int y = 0; y < image.getHeight(); y++)
			for(int x = 0; x < image.getWidth(); x++)
			{
				Color color = new Color(image.getRGB(x, y));
				raster.setSample(x, y, 0, (int)((0.2989 * color.getRed()) + (0.5870 * color.getGreen()) + (0.1140 * color.getBlue())));
			}
		
		return gray_image;
	}

	public static BufferedImage resizeImage(BufferedImage image, int width, int height)
	{
        double thumbRatio = (double) width / (double) height;
        int imageWidth = image.getWidth(null);
        int imageHeight = image.getHeight(null);
        double aspectRatio = (double) imageWidth / (double) imageHeight;
        if (thumbRatio < aspectRatio) {
            height = (int) (width / aspectRatio);
        } else {
            width = (int) (height * aspectRatio);
        }
        
        BufferedImage resizeedImage = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
        Graphics2D g2d = (Graphics2D) resizeedImage.createGraphics();
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g2d.drawImage(image, 0, 0, width, height, null);
        g2d.dispose();
        
        return resizeedImage;
	}
	
	public static BufferedImage resizeImage(BufferedImage image, ScaleType scale)
	{
		BufferedImage result = null;
		int heigth = image.getHeight();
		int width = image.getWidth();
		double org_ratio = (double) heigth /  (double) width;
		
		switch (scale) {
		case Signs_80:
			width = 80;
			heigth = (int)(org_ratio * (double) width);
			break;
			
		case Signs_160:
			width = 160;
			heigth = (int)(org_ratio * (double) width);
			break;

		case Screen_width:
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			width = (int) screenSize.getWidth();
			heigth = (int)(org_ratio * (double) width);
			break;	
		
		case Not_scaled:
			return image;
			
		default:
			break;
		}
		
		result = resizeImage(image, width, heigth);

		return result;
	}
	
	public static int[][] writeIntensities(BufferedImage image)
	{
		int[][] intensities = null;
		intensities = new int[image.getHeight()][];

		for (int i = 0; i < image.getHeight(); i++) {
			intensities[i] = new int[image.getWidth()];
		}

		for(int y = 0; y < image.getHeight(); y++)
			for(int x = 0; x < image.getWidth(); x++)
			{
				Color color = new Color(image.getRGB(x, y));
				intensities[y][x] = color.getRed();
			}
		
		return intensities;
	}
}
