package pl.edu.pwr.pp;

import java.awt.*;
import java.awt.image.*;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;

import javax.swing.filechooser.FileNameExtensionFilter;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainWindow {

	private JFrame frame;
	private String path;
	private String filename;
	private boolean path_is_url;
	private Path save_path;
	private int[][] intensities;
	private BufferedImage image;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		path = null;
		filename = null;
		path_is_url = false;
		intensities = null;
		image = null;
		save_path = null;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setForeground(Color.WHITE);
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		frame.setResizable(false);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.LIGHT_GRAY);
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 773, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 618, Short.MAX_VALUE)
					.addContainerGap())
		);
		panel_1.setLayout(null);
		
		JLabel lblLoadedImage = new JLabel("No preview");
		lblLoadedImage.setVerticalAlignment(SwingConstants.CENTER);
		lblLoadedImage.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoadedImage.setBackground(Color.RED);
		lblLoadedImage.setBounds(10, 11, 753, 558);
		
		panel_1.add(lblLoadedImage);
		
		JLabel lblImageName = new JLabel("...Image name...");
		lblImageName.setVerticalAlignment(SwingConstants.CENTER);
		lblImageName.setHorizontalAlignment(SwingConstants.CENTER);
		lblImageName.setBounds(10, 580, 753, 23);
		panel_1.add(lblImageName);
		frame.getContentPane().setLayout(groupLayout);
		frame.setBounds(100, 100, 809, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		//Wczytaj obraz
		JButton btnLoadImage = new JButton("Wczytaj obraz");
		menuBar.add(btnLoadImage);
		
		
		JCheckBox chckbxOption1 = new JCheckBox("(Reserved)");
		chckbxOption1.setEnabled(false);
		menuBar.add(chckbxOption1);
		
		JCheckBox chckbxOption2 = new JCheckBox("(Reserved)");
		chckbxOption2.setEnabled(false);
		menuBar.add(chckbxOption2);
		
		//Zapisz obraz
		JButton btnSaveImage = new JButton("Zapisz obraz");
		btnSaveImage.setEnabled(false);
		btnSaveImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(SaveFile())
				{
					JOptionPane.showMessageDialog(frame, "Plik został zpisany w: \n" + save_path, "Zapis pomyślny", JOptionPane.INFORMATION_MESSAGE);	
				}
				else
					JOptionPane.showMessageDialog(frame, "Błąd zapisu pliku!", "Błąd", JOptionPane.ERROR_MESSAGE);
			}
		});
		menuBar.add(btnSaveImage);
		
		JButton btnFunction1 = new JButton("(Reserve)");
		btnFunction1.setEnabled(false);
		menuBar.add(btnFunction1);
		
		JButton btnFunction2 = new JButton("(Reserve)");
		btnFunction2.setEnabled(false);
		menuBar.add(btnFunction2);
		
		
		btnLoadImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(LoadFile())
				{				
					if(path_is_url == false)
					{
						Path file_path = Paths.get(path);
						if(file_path.toString().endsWith(".pgm"))
						{
							ImageFileReader imageReader = new ImageFileReader();
							intensities = imageReader.readPgmFile(file_path);
							
							if(intensities == null)
							{
								System.out.println("Błąd wczytania obrazu!");
								JOptionPane.showMessageDialog(frame, "Błąd odczytu pliku PGM!", "Błąd", JOptionPane.ERROR_MESSAGE);
								return;
							}
							
							image = pgmToImage(intensities);
							btnSaveImage.setEnabled(true);
						}
						else
						{
							try {
								image = ImageIO.read(file_path.toFile());
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							btnSaveImage.setEnabled(false);
						}
						
						filename = file_path.getFileName().toString();
					}
					else if (path_is_url == true)
					{
						ImageFileReader imageReader = new ImageFileReader();
						URL url = null;
						try {
							url = new URL(path);
						} catch (MalformedURLException e1) {
							e1.printStackTrace();
							url = null;
						}
						if(url != null)
						{
							if(url.getFile().endsWith(".pgm"))
							{
								intensities = imageReader.readPgmFile(url);
								
								if(intensities == null)
								{
									System.out.println("Błąd wczytania obrazu!");
									JOptionPane.showMessageDialog(frame, "Błąd odczytu pliku PGM!", "Błąd", JOptionPane.ERROR_MESSAGE);
									return;
								}
								
								image = pgmToImage(intensities);
								btnSaveImage.setEnabled(true);
							}
							else
							{
								try {
									image = ImageIO.read(url);
								} catch (IOException e1) {
									e1.printStackTrace();
								}
								btnSaveImage.setEnabled(false);
							}
							
							filename = url.toString().substring( url.toString().lastIndexOf('/')+1, url.toString().length());
						}
						
					}
					
					ImageIcon icon = new ImageIcon(image);
					lblLoadedImage.setIcon(icon);
					lblLoadedImage.setText("");
					lblImageName.setText(filename);
				}
			}
		});
	}
	
	public boolean LoadFile()
	{
		try {
			LoadFileWindow dialog = new LoadFileWindow();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setResizable(false);
			dialog.setVisible(true);
			
			path = dialog.getPath();
			path_is_url = dialog.getPathType();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(path == null)
			return false;
		
		return true;
	}
	
	public boolean SaveFile()
	{
		JFileChooser chooser = new JFileChooser();
		
		chooser.addChoosableFileFilter(new FileNameExtensionFilter("ASCI-ART (*.txt)", "txt"));
		chooser.setAcceptAllFileFilterUsed(false);
		
		int return_val = chooser.showSaveDialog(frame);
		if(return_val == JFileChooser.APPROVE_OPTION)
		{
			if(intensities != null)
			{
				ImageFileWriter imageWriter = new ImageFileWriter();
				String tmp = chooser.getSelectedFile().getPath();
				if(tmp.substring(tmp.length() - 4, tmp.length()) != ".txt")
					tmp += ".txt";
				save_path = Paths.get(tmp);
				char[][] ascii = ImageConverter.intensitiesToAscii(intensities);
				
				imageWriter.saveToTxtFile(ascii, save_path);
			}
			else

				return false;
			
		}
		return true;
	}
	
	public BufferedImage pgmToImage(int intensities[][])
	{
		int rows = intensities.length;
		int columns = intensities[0].length;
		
		BufferedImage image = new BufferedImage(columns, rows,
			     BufferedImage.TYPE_BYTE_GRAY);
		WritableRaster raster = image.getRaster();
		
		for (int y = 0; y < rows; y++)
			for (int x = 0; (x < columns); x++)
				raster.setSample(x, y, 0, intensities[y][x]);
		
		return image;
	}
}
