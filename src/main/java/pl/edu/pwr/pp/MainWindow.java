package pl.edu.pwr.pp;

import java.awt.*;
import java.awt.image.*;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;

import javax.swing.filechooser.FileNameExtensionFilter;

import pl.edu.pwr.pp.ImageConverter.ConvertType;
import pl.edu.pwr.pp.ImageConverter.ScaleType;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainWindow {

	private JFrame frmAsciiart;
	private String path;
	private String filename;
	private boolean path_is_url;
	private Path save_path;
	private int[][] intensities;
	private BufferedImage image;
	private Path last_path;
	private ConvertType conversion_type;
	private ScaleType scale_type;
	private int custom_width;

	private ConvertType[] option1_list = { ConvertType.Low, ConvertType.LowNegativ, ConvertType.High,
			ConvertType.HighNegative };
	private ScaleType[] option2_list = { ScaleType.Signs_80, ScaleType.Signs_160, ScaleType.Screen_width,
			ScaleType.Not_scaled, ScaleType.Custom };

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmAsciiart.setVisible(true);
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
		last_path = null;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */

	private void initialize() {
		frmAsciiart = new JFrame();
		frmAsciiart.setTitle("ASCII-ART");
		try {
			frmAsciiart.setIconImage(ImageIO.read(ClassLoader.getSystemResource("aplication_icon.png")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		frmAsciiart.getContentPane().setForeground(Color.WHITE);
		frmAsciiart.getContentPane().setBackground(Color.DARK_GRAY);
		frmAsciiart.setResizable(false);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.LIGHT_GRAY);
		GroupLayout groupLayout = new GroupLayout(frmAsciiart.getContentPane());
		groupLayout
				.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup().addContainerGap()
								.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 773, Short.MAX_VALUE)
								.addContainerGap()));
		groupLayout
				.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup().addContainerGap()
								.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 618, Short.MAX_VALUE)
								.addContainerGap()));
		panel_1.setLayout(null);

		JLabel lblLoadedImage = new JLabel("No preview");
		lblLoadedImage.setVerticalAlignment(SwingConstants.CENTER);
		lblLoadedImage.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoadedImage.setBackground(Color.RED);
		lblLoadedImage.setBounds(10, 11, 829, 586);

		panel_1.add(lblLoadedImage);

		JLabel lblImageName = new JLabel("...Image name...");
		lblImageName.setVerticalAlignment(SwingConstants.CENTER);
		lblImageName.setHorizontalAlignment(SwingConstants.CENTER);
		lblImageName.setBounds(10, 608, 829, 23);
		panel_1.add(lblImageName);
		frmAsciiart.getContentPane().setLayout(groupLayout);
		frmAsciiart.setBounds(100, 100, 875, 718);
		frmAsciiart.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frmAsciiart.setJMenuBar(menuBar);

		JButton btnLoadImage = new JButton("Wczytaj obraz");
		menuBar.add(btnLoadImage);

		DefaultComboBoxModel<ConvertType> comboConvertTypeModel = new DefaultComboBoxModel<ConvertType>(option1_list);
		JComboBox<ConvertType> comboBoxOption1 = new JComboBox<ConvertType>(comboConvertTypeModel);
		comboBoxOption1.addActionListener(e -> {
			conversion_type = (ConvertType) comboBoxOption1.getSelectedItem();
		});
		comboBoxOption1.setSelectedIndex(0);
		conversion_type = (ConvertType) comboBoxOption1.getSelectedItem();
		menuBar.add(comboBoxOption1);

		JSpinner spinCustomWidth = new JSpinner();
		spinCustomWidth.setPreferredSize(new Dimension(80, 20));
		spinCustomWidth.setMinimumSize(new Dimension(60, 20));
		spinCustomWidth.setModel(new SpinnerNumberModel(new Integer(80), new Integer(1), null, new Integer(1)));

		DefaultComboBoxModel<ScaleType> comboScaleTypeModel = new DefaultComboBoxModel<ScaleType>(option2_list);
		JComboBox<ScaleType> comboBoxOption2 = new JComboBox<ScaleType>(comboScaleTypeModel);
		comboBoxOption2.addActionListener(e -> {
			scale_type = (ScaleType) comboBoxOption2.getSelectedItem();
			if (scale_type == ScaleType.Custom)
				spinCustomWidth.setEnabled(true);
			else
				spinCustomWidth.setEnabled(false);
		});
		comboBoxOption2.setSelectedIndex(0);
		scale_type = (ScaleType) comboBoxOption2.getSelectedItem();
		menuBar.add(comboBoxOption2);

		spinCustomWidth.addChangeListener(e -> {
			custom_width = (int) spinCustomWidth.getValue();
		});
		spinCustomWidth.setEnabled(false);
		custom_width = (int) spinCustomWidth.getValue();
		menuBar.add(spinCustomWidth);

		JButton btnSaveImage = new JButton("Zapisz obraz");
		btnSaveImage.setEnabled(false);
		menuBar.add(btnSaveImage);

		JButton btnFunction1 = new JButton("(Reserve)");
		btnFunction1.setEnabled(false);
		menuBar.add(btnFunction1);

		JButton btnFunction2 = new JButton("(Reserve)");
		btnFunction2.setEnabled(false);
		menuBar.add(btnFunction2);

		// Zapisz obraz
		btnSaveImage.addActionListener(e -> {
			if (SaveFile())
				JOptionPane.showMessageDialog(frmAsciiart, "Plik został zpisany w: \n" + save_path, "Zapis pomyślny",
						JOptionPane.INFORMATION_MESSAGE);
			else
				JOptionPane.showMessageDialog(frmAsciiart, "Plik nie został zapisany", "Zapis anulowany",
						JOptionPane.INFORMATION_MESSAGE);
		});

		// Wczytaj obraz
		btnLoadImage.addActionListener(e -> {
			if (LoadFile()) {
				System.out.println("Loading...");
				if (!path_is_url)
					displayImageFromFile();
				else
					displayImageFromURL();

				ImageIcon icon = new ImageIcon(
						ImageConverter.resizeImage(image, lblLoadedImage.getWidth(), lblLoadedImage.getHeight()));
				lblLoadedImage.setIcon(icon);
				lblLoadedImage.setText("");
				lblImageName.setText(filename);

				btnSaveImage.setEnabled(true);
			}
		});
	}

	public boolean LoadFile() {
		try {
			LoadFileWindow dialog = new LoadFileWindow(last_path);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setResizable(false);
			dialog.setVisible(true);

			path = dialog.getPath();
			path_is_url = dialog.getPathType();
			last_path = dialog.getLastPath();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (path == null)
			return false;

		return true;
	}

	public boolean SaveFile() {
		while (true) {
			JFileChooser chooser = new JFileChooser();

			if (last_path != null)
				chooser.setCurrentDirectory(last_path.toFile());

			chooser.setFileFilter(new FileNameExtensionFilter("ASCII_ART (*.nfo)", "nfo"));
			chooser.addChoosableFileFilter(new FileNameExtensionFilter("Text file (*.txt)", "txt"));
			chooser.setAcceptAllFileFilterUsed(false);

			int return_val = chooser.showSaveDialog(frmAsciiart);
			if (return_val == JFileChooser.APPROVE_OPTION) {
				String tmp = chooser.getSelectedFile().getPath();
				String extension = "." + ((FileNameExtensionFilter) (chooser.getFileFilter())).getExtensions()[0];
				if (!tmp.endsWith(extension))
					tmp += extension;

				save_path = Paths.get(tmp);
				last_path = chooser.getCurrentDirectory().toPath();

				if (Files.exists(save_path)) {
					int answer = JOptionPane.showConfirmDialog(frmAsciiart,
							"Plik o podanej nazwie istnieje.\nCzy chcesz nadpisać plik?",
							"Ostrzeżenie przed nadpisaniem", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					switch (answer) {
					case JOptionPane.YES_OPTION:
						break;

					case JOptionPane.NO_OPTION:
						continue;

					case JOptionPane.CLOSED_OPTION:
						return false;
					}
				}

				ImageFileWriter imageWriter = new ImageFileWriter();
				BufferedImage img_to_save;

				if (image.getType() != BufferedImage.TYPE_BYTE_GRAY)
					img_to_save = ImageConverter.convertFromRGBToGrey(image);
				else
					img_to_save = image;

				if (scale_type != ScaleType.Custom)
					img_to_save = ImageConverter.resizeImage(img_to_save, scale_type);
				else
					img_to_save = ImageConverter.resizeImage(img_to_save, custom_width);

				intensities = ImageConverter.writeIntensities(img_to_save);

				char[][] ascii = ImageConverter.intensitiesToAscii(intensities, conversion_type);
				imageWriter.saveToTxtFile(ascii, save_path);

				return true;
			}

			return false;
		}
	}

	public boolean displayImageFromFile() {
		boolean type = false;

		Path file_path = Paths.get(path);
		if (file_path.toString().endsWith(".pgm")) {
			ImageFileReader imageReader = new ImageFileReader();
			intensities = imageReader.readPgmFile(file_path);

			if (intensities == null) {
				System.out.println("Błąd wczytania obrazu!");
				JOptionPane.showMessageDialog(frmAsciiart, "Błąd odczytu pliku PGM!", "Błąd",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}

			image = ImageConverter.pgmToImage(intensities);

			type = true;
		} else {
			try {
				image = ImageIO.read(file_path.toFile());
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			type = false;
		}

		filename = file_path.getFileName().toString();

		return type;
	}

	public boolean displayImageFromURL() {
		boolean type = false;

		ImageFileReader imageReader = new ImageFileReader();
		URL url = null;
		try {
			url = new URL(path);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
			url = null;
		}
		if (url != null) {
			if (url.getFile().endsWith(".pgm")) {
				intensities = imageReader.readPgmFile(url);

				if (intensities == null) {
					System.out.println("Błąd wczytania obrazu!");
					JOptionPane.showMessageDialog(frmAsciiart, "Błąd odczytu pliku PGM!", "Błąd",
							JOptionPane.ERROR_MESSAGE);
					return false;
				}

				image = ImageConverter.pgmToImage(intensities);

				type = true;
			} else {
				try {
					image = ImageIO.read(url);
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				type = false;
			}

			filename = url.toString().substring(url.toString().lastIndexOf('/') + 1, url.toString().length());
		}

		return type;
	}
}
