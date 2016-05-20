package pl.edu.pwr.pp;

import java.net.*;
import java.nio.file.Path;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JRadioButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoadFileWindow extends JDialog {

	/**
	 * 
	 */
	//Dodane aby usunąć warning
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private final JPanel contentPanel = new JPanel();
	private JTextField txtLocalAddress;
	private JTextField txtUrlAddress;
	private String path;
	private boolean path_is_url;
	private Path last_path;
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		try {
			LoadFileWindow dialog = new LoadFileWindow();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * Create the dialog.
	 */
	public LoadFileWindow(Path given_path) {
		path = null;
		path_is_url = false;
		last_path = given_path;
		
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setTitle("Wczytaj plik z obrazem");
		setAlwaysOnTop(true);
		setBounds(100, 100, 589, 234);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.PINK);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		
		JLabel lblWczytaj = new JLabel("Wczytaj plik");
		lblWczytaj.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblWczytaj = new GridBagConstraints();
		gbc_lblWczytaj.anchor = GridBagConstraints.WEST;
		gbc_lblWczytaj.insets = new Insets(0, 0, 5, 5);
		gbc_lblWczytaj.gridx = 0;
		gbc_lblWczytaj.gridy = 0;
		contentPanel.add(lblWczytaj, gbc_lblWczytaj);
	
	
		JRadioButton rdbtnFromFile = new JRadioButton("z dysku lokalnego");
		rdbtnFromFile.setBackground(Color.PINK);
		rdbtnFromFile.setSelected(true);
		rdbtnFromFile.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_rdbtnFromFile = new GridBagConstraints();
		gbc_rdbtnFromFile.anchor = GridBagConstraints.WEST;
		gbc_rdbtnFromFile.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnFromFile.gridx = 0;
		gbc_rdbtnFromFile.gridy = 1;
		contentPanel.add(rdbtnFromFile, gbc_rdbtnFromFile);
	
	
		txtLocalAddress = new JTextField();
		txtLocalAddress.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				rdbtnFromFile.setSelected(true);
			}
		});
		txtLocalAddress.setBackground(Color.CYAN);
		txtLocalAddress.setEditable(false);
		GridBagConstraints gbc_txtLocalAddress = new GridBagConstraints();
		gbc_txtLocalAddress.insets = new Insets(0, 0, 5, 5);
		gbc_txtLocalAddress.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLocalAddress.gridx = 0;
		gbc_txtLocalAddress.gridy = 2;
		contentPanel.add(txtLocalAddress, gbc_txtLocalAddress);
		txtLocalAddress.setColumns(10);
	
	
		JButton btnBrowser = new JButton("Wybierz plik");
		btnBrowser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				Component source =(Component) e.getSource();
				
				if(last_path != null)
					chooser.setCurrentDirectory(last_path.toFile());
				
				chooser.addChoosableFileFilter(new FileNameExtensionFilter("PGM images (*.pgm)", "pgm"));
				chooser.addChoosableFileFilter(new FileNameExtensionFilter("All other images (*.jpg, *.png, *.bmp, *.gif)", "jpg", "png", "bmp", "gif"));
				chooser.setAcceptAllFileFilterUsed(false);
				
				int return_val = chooser.showOpenDialog(source.getParent());
				if(return_val == JFileChooser.APPROVE_OPTION)
				{
					File f = chooser.getSelectedFile();
					txtLocalAddress.setText(f.getAbsolutePath());
					rdbtnFromFile.setSelected(true);
					last_path = chooser.getCurrentDirectory().toPath();
				}
				
			}
		});
		btnBrowser.setToolTipText("Wybierz plik zdjęcia do załadowania");
		GridBagConstraints gbc_btnBrowser = new GridBagConstraints();
		gbc_btnBrowser.insets = new Insets(0, 0, 5, 0);
		gbc_btnBrowser.gridx = 1;
		gbc_btnBrowser.gridy = 2;
		contentPanel.add(btnBrowser, gbc_btnBrowser);
	
	
		JRadioButton rdbtnLoadURL = new JRadioButton("z adresu URL");
		rdbtnLoadURL.setBackground(Color.PINK);
		GridBagConstraints gbc_rdbtnLoadURL = new GridBagConstraints();
		gbc_rdbtnLoadURL.anchor = GridBagConstraints.WEST;
		gbc_rdbtnLoadURL.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnLoadURL.gridx = 0;
		gbc_rdbtnLoadURL.gridy = 4;
		contentPanel.add(rdbtnLoadURL, gbc_rdbtnLoadURL);
		
		
		txtUrlAddress = new JTextField();
		txtUrlAddress.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				rdbtnLoadURL.setSelected(true);
			}
		});
		txtUrlAddress.setBackground(Color.CYAN);
		txtUrlAddress.setToolTipText("Wprowadz adres URL obrazka");
		GridBagConstraints gbc_txtUrlAddress = new GridBagConstraints();
		gbc_txtUrlAddress.insets = new Insets(0, 0, 0, 5);
		gbc_txtUrlAddress.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtUrlAddress.gridx = 0;
		gbc_txtUrlAddress.gridy = 5;
		contentPanel.add(txtUrlAddress, gbc_txtUrlAddress);
		txtUrlAddress.setColumns(10);
		
		
		JPanel buttonPane = new JPanel();
		buttonPane.setBackground(Color.PINK);
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdbtnFromFile.isSelected())
				{
					path = txtLocalAddress.getText();
					path_is_url = false;
				}
				else if(rdbtnLoadURL.isSelected())
				{
					int code = -1;
					try {
						URL url = new URL(txtUrlAddress.getText());
						HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
				        urlConn.connect();
				        code = urlConn.getResponseCode();
				    } catch (IOException ex) {
				        ex.printStackTrace();
				    }
					
					if(code == HttpURLConnection.HTTP_OK)
					{
						path = txtUrlAddress.getText();
						path_is_url = true;
					}
					else
					{
						Component source =(Component) e.getSource();
						JOptionPane.showMessageDialog(source.getParent(), "Podany adres URL jest nieprawidłowy!", "Błąd", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				dispose();
			}
		});
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
			
	
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
			
		ButtonGroup radioBtnGroup = new ButtonGroup();
		radioBtnGroup.add(rdbtnFromFile);
		radioBtnGroup.add(rdbtnLoadURL);
	}
	
	public String getPath()
	{
		return path;
	}
	
	public Path getLastPath()
	{
		return last_path;
	}
	
	public boolean getPathType()
	{
		return path_is_url;
	}
}
