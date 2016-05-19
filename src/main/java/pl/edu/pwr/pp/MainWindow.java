package pl.edu.pwr.pp;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.imageio.ImageIO;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import net.miginfocom.swing.MigLayout;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.net.URISyntaxException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainWindow {

	private JFrame frame;
	private String path;
	private boolean path_is_url;

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
		path_is_url = false;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setForeground(Color.WHITE);
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		
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
		btnLoadImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoadFile();
				System.out.println(path + " " + path_is_url);
			}
		});
		menuBar.add(btnLoadImage);
		
		
		JCheckBox chckbxOption1 = new JCheckBox("(Reserved)");
		chckbxOption1.setEnabled(false);
		menuBar.add(chckbxOption1);
		
		JCheckBox chckbxOption2 = new JCheckBox("(Reserved)");
		chckbxOption2.setEnabled(false);
		menuBar.add(chckbxOption2);
		
		//Zapisz obraz
		JButton btnSaveImage = new JButton("Zapisz obraz");
		btnSaveImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SaveFile();
			}
		});
		menuBar.add(btnSaveImage);
		
		JButton btnFunction1 = new JButton("(Reserve)");
		btnFunction1.setEnabled(false);
		menuBar.add(btnFunction1);
		
		JButton btnFunction2 = new JButton("(Reserve)");
		btnFunction2.setEnabled(false);
		menuBar.add(btnFunction2);
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
		
		chooser.addChoosableFileFilter(new FileNameExtensionFilter("Images (*.pgm, *.jpg, *.png)", "pgm", "jpg", "png"));
		
		int return_val = chooser.showSaveDialog(frame);
		if(return_val == chooser.APPROVE_OPTION)
		{
			File f = chooser.getSelectedFile();
			System.out.println(f.getAbsolutePath());
		}
		return true;
	}
}
