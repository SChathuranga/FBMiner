package com.genesislab.views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.HeadlessException;

import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.genesislab.seleniumbase.CoreBase;
import com.genesislab.seleniumbase.DBConnector;
import com.genesislab.seleniumbase.DBOperations;
import com.genesislab.seleniumbase.FetchAtOnce;
import com.genesislab.seleniumbase.User;

import javax.swing.JTextField;
import javax.swing.UIManager;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import javax.swing.JCheckBox;

@SuppressWarnings("serial")
public class SeekerUI extends JFrame {

	private JPanel contentPane;
	private JTextField txtCSVFilePath;
	private JTextField txtIDsToFetch;
	private JTextField txtFBID;
	public static URL checkedUrl;
	public static URL resetUrl;
	public static URL settingsUrl;
	public String csvFilePath;
	
	static Connection connection = null;
	DBOperations dbops = new DBOperations();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			checkedUrl = SeekerUI.class.getResource("/checked.png");
			resetUrl = SeekerUI.class.getResource("/synchronize.png");
			settingsUrl = SeekerUI.class.getResource("/settings.png");
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SeekerUI frame = new SeekerUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SeekerUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 524, 400);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		try
		{
			//connecting to database
			connection = DBConnector.dbConnector();
			if(connection.isValid(0))
				System.out.println("Database Connection Established");
			else
				System.out.println("Database Connection Failed");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		JLabel lblDevelopedByGenesislab = new JLabel("Developed by GenesisLab Inc.");
		lblDevelopedByGenesislab.setForeground(Color.LIGHT_GRAY);
		lblDevelopedByGenesislab.setFont(new Font("Ubuntu", Font.PLAIN, 10));
		lblDevelopedByGenesislab.setBounds(180, 347, 147, 14);
		contentPane.add(lblDevelopedByGenesislab);
		
		JLabel lblDataFetcher = new JLabel("Data Fetcher");
		lblDataFetcher.setHorizontalAlignment(SwingConstants.CENTER);
		lblDataFetcher.setForeground(Color.WHITE);
		lblDataFetcher.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblDataFetcher.setBounds(160, 13, 204, 40);
		contentPane.add(lblDataFetcher);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		panel.setForeground(Color.WHITE);
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(10, 72, 246, 278);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblSuccessMark = new JLabel("");
		lblSuccessMark.setVisible(false);
		lblSuccessMark.setIcon(new ImageIcon(checkedUrl));
		//lblNewLabel.setIcon(new ImageIcon("E:\\GenesisLab\\Seeker\\Seeker\\resource\\checked.png"));
		lblSuccessMark.setBounds(208, 51, 21, 30);
		panel.add(lblSuccessMark);
		
		JLabel lblUploadCsvFile = new JLabel("Select CSV file containing FB IDs :");
		lblUploadCsvFile.setFont(new Font("Ubuntu", Font.PLAIN, 12));
		lblUploadCsvFile.setForeground(Color.WHITE);
		lblUploadCsvFile.setBounds(12, 55, 213, 23);
		panel.add(lblUploadCsvFile);
		
		JButton btnSelect = new JButton("Select");
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter csvFileFilter = new FileNameExtensionFilter("CSV File", "csv");
				fileChooser.setFileFilter(csvFileFilter);
				fileChooser.setDialogTitle("Choose CSV containing FB IDs");
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				if(fileChooser.showOpenDialog(btnSelect) == JFileChooser.APPROVE_OPTION)
				{
					File file = fileChooser.getSelectedFile();
					csvFilePath = file.getAbsolutePath();
					txtCSVFilePath.setText(csvFilePath);
					lblSuccessMark.setVisible(true);
				}
				else
					JOptionPane.showMessageDialog(null, "No CSV file selected", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		
		JLabel lblImportSuccessful = new JLabel("Import Successful!");
		lblImportSuccessful.setVisible(false);
		lblImportSuccessful.setHorizontalAlignment(SwingConstants.LEFT);
		lblImportSuccessful.setIcon(new ImageIcon(checkedUrl));
		//lblImportSuccessful.setIcon(new ImageIcon("E:\\GenesisLab\\Seeker\\Seeker\\Resources\\checked.png"));
		lblImportSuccessful.setForeground(Color.WHITE);
		lblImportSuccessful.setFont(new Font("Ubuntu", Font.PLAIN, 12));
		lblImportSuccessful.setBounds(53, 156, 139, 23);
		panel.add(lblImportSuccessful);
		
		
		
		JLabel lblNumberOfFbIds = new JLabel("Number of FB IDs Uploaded:");
		lblNumberOfFbIds.setVisible(false);
		lblNumberOfFbIds.setFont(new Font("Ubuntu", Font.PLAIN, 12));
		lblNumberOfFbIds.setForeground(Color.WHITE);
		lblNumberOfFbIds.setBounds(28, 190, 164, 14);
		panel.add(lblNumberOfFbIds);
		
		JLabel lblUploadedAmount = new JLabel("100");
		lblUploadedAmount.setHorizontalAlignment(SwingConstants.CENTER);
		lblUploadedAmount.setVisible(false);
		lblUploadedAmount.setFont(new Font("Ubuntu", Font.PLAIN, 12));
		lblUploadedAmount.setForeground(Color.WHITE);
		lblUploadedAmount.setBounds(183, 190, 46, 14);
		panel.add(lblUploadedAmount);
		
		JLabel lblFailedUploads = new JLabel("Failed Uploads:");
		lblFailedUploads.setFont(new Font("Ubuntu", Font.PLAIN, 12));
		lblFailedUploads.setVisible(false);
		lblFailedUploads.setForeground(Color.WHITE);
		lblFailedUploads.setBounds(28, 215, 144, 14);
		panel.add(lblFailedUploads);
		
		JLabel lblDuplicateUploads = new JLabel("Duplicate Uploads:");
		lblDuplicateUploads.setVisible(false);
		lblDuplicateUploads.setForeground(Color.WHITE);
		lblDuplicateUploads.setFont(new Font("Ubuntu", Font.PLAIN, 12));
		lblDuplicateUploads.setBounds(28, 240, 164, 14);
		panel.add(lblDuplicateUploads);
		
		JLabel lblFailedAmount = new JLabel("5");
		lblFailedAmount.setVisible(false);
		lblFailedAmount.setHorizontalAlignment(SwingConstants.CENTER);
		lblFailedAmount.setFont(new Font("Ubuntu", Font.PLAIN, 12));
		lblFailedAmount.setForeground(Color.WHITE);
		lblFailedAmount.setBounds(183, 215, 46, 14);
		panel.add(lblFailedAmount);
		
		JLabel lblDuplicatesAmount = new JLabel("5");
		lblDuplicatesAmount.setVisible(false);
		lblDuplicatesAmount.setHorizontalAlignment(SwingConstants.CENTER);
		lblDuplicatesAmount.setForeground(Color.WHITE);
		lblDuplicatesAmount.setFont(new Font("Ubuntu", Font.PLAIN, 12));
		lblDuplicatesAmount.setBounds(183, 241, 46, 14);
		panel.add(lblDuplicatesAmount);
		btnSelect.setFont(new Font("Ubuntu", Font.PLAIN, 12));
		btnSelect.setBounds(151, 83, 78, 23);
		panel.add(btnSelect);
		
		JButton btnUpload = new JButton("Upload CSV Data!");
		btnUpload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int duplicatesList=0, failureList=0;
				ArrayList<String> fbIDsList = new ArrayList<>();
				
				if(txtCSVFilePath.getText().equals(""))
					JOptionPane.showMessageDialog(null, "Select a CSV file please!", "Error", JOptionPane.ERROR_MESSAGE);
				else
				{
					try 
					{
						BufferedReader bReader = new BufferedReader(new FileReader(txtCSVFilePath.getText()));
						String line = ""; 
				        while ((line = bReader.readLine()) != null) 
				        {
				            if(line != null)
				            {
				            	String fbid = line.trim();
				            	System.out.println(fbid);
				            	
				            	fbIDsList.add(fbid);
				            }
				        }
				        bReader.close();
				        
				        ArrayList<String> DBList = dbops.getFBIDListfromDB(connection);
				        
				        for(int i=0; i<DBList.size();i++)
				        {
				        	if(fbIDsList.contains(DBList.get(i)))
				        	{
				        		fbIDsList.remove(DBList.get(i));
				        		duplicatesList++;
				        	}
				        }
				        
				        
				        lblImportSuccessful.setVisible(true);
				        lblNumberOfFbIds.setVisible(true);
				        lblUploadedAmount.setText(Integer.toString(fbIDsList.size()));
				        lblUploadedAmount.setVisible(true);
				        lblFailedUploads.setVisible(true);
				        lblFailedAmount.setText(Integer.toString(failureList));
				        lblFailedAmount.setVisible(true);
				        lblDuplicateUploads.setVisible(true);
				        lblDuplicatesAmount.setText(Integer.toString(duplicatesList));
				        lblDuplicatesAmount.setVisible(true);
				        
				        if(dbops.exportCSVData(connection, fbIDsList))
				        	JOptionPane.showMessageDialog(null, "Exporting data successful", "Sucess", JOptionPane.INFORMATION_MESSAGE);
				        else
				        	JOptionPane.showMessageDialog(null, "Exporting data failed", "Error", JOptionPane.ERROR_MESSAGE);
				        
				        
					} 
					catch (IOException | HeadlessException | SQLException e2) 
					{
						e2.printStackTrace();
					}
				}

			}
		});
		btnUpload.setFont(new Font("Ubuntu", Font.PLAIN, 12));
		btnUpload.setBounds(12, 111, 217, 23);
		btnUpload.setBorder(new EmptyBorder(0, 0, 0, 0));
		panel.add(btnUpload);
		
		txtCSVFilePath = new JTextField();
		txtCSVFilePath.setEditable(false);
		txtCSVFilePath.setBounds(12, 83, 139, 23);
		txtCSVFilePath.setBorder(new EmptyBorder(0, 0, 0, 0));
		panel.add(txtCSVFilePath);
		txtCSVFilePath.setColumns(10);
		
		JLabel lblFbIdUpload = new JLabel("FB ID Upload");
		lblFbIdUpload.setForeground(Color.WHITE);
		lblFbIdUpload.setFont(new Font("Ubuntu", Font.BOLD, 18));
		lblFbIdUpload.setBounds(10, 11, 117, 23);
		panel.add(lblFbIdUpload);
		
		JLabel lblRestUpload = new JLabel("");
		lblRestUpload.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//code here
				txtCSVFilePath.setText(null);
				lblImportSuccessful.setVisible(false);
		        lblNumberOfFbIds.setVisible(false);
		        lblUploadedAmount.setVisible(false);
		        lblSuccessMark.setVisible(false);
		        lblFailedUploads.setVisible(false);
		        lblFailedAmount.setVisible(false);
		        lblDuplicateUploads.setVisible(false);
		        lblDuplicatesAmount.setVisible(false);
			}
		});
		lblRestUpload.setToolTipText("Reset Upload");
		lblRestUpload.setIcon(new ImageIcon(resetUrl));
		lblRestUpload.setBounds(194, -1, 42, 46);
		panel.add(lblRestUpload);
		
		JLabel lblLoading = new JLabel("");
		lblLoading.setVisible(false);
		lblLoading.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoading.setIcon(new ImageIcon("/media/schathuranga/My Stuff/GenesisLab/Freelancing/FBMiner/resources/spinner.gif"));
		lblLoading.setBounds(88, 13, 60, 40);
		contentPane.add(lblLoading);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.DARK_GRAY);
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setBounds(252, 72, 246, 278);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNoOfIds = new JLabel("No of IDs to be fetched :");
		lblNoOfIds.setFont(new Font("Ubuntu", Font.PLAIN, 12));
		lblNoOfIds.setForeground(Color.WHITE);
		lblNoOfIds.setBounds(18, 68, 145, 14);
		panel_1.add(lblNoOfIds);
		
		txtIDsToFetch = new JTextField();
		txtIDsToFetch.setHorizontalAlignment(SwingConstants.CENTER);
		txtIDsToFetch.setBounds(162, 66, 62, 20);
		txtIDsToFetch.setBorder(new EmptyBorder(0, 0, 0, 0));
		panel_1.add(txtIDsToFetch);
		txtIDsToFetch.setColumns(10);
		
		JButton btnFetch = new JButton("Fetch");
		btnFetch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//write validation here
				lblLoading.setVisible(true);
				
				int count=0;
				int dbCount = dbops.validateNumberOfRecords(connection);
				if(txtIDsToFetch.getText().equals(""))
				{
					System.out.println("Enter number of IDs to fetch please!");
					JOptionPane.showMessageDialog(null, "Enter number of IDs to fetch please!", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					try
					{
						int inputCount = Integer.parseInt(txtIDsToFetch.getText());
						if(inputCount > dbCount)
						{
							JOptionPane.showMessageDialog(null, "There are no that amount of IDs to be fetched!", "Error", JOptionPane.ERROR_MESSAGE);
							System.out.println("There are no that amount of IDs to be fetched!");
						}
						else
						{
							//start fetching from here onwards
							String accountUsername = dbops.getAccountUserName(connection);
							String accountPassword = dbops.getAccountPassword(connection);
							String amount = (txtIDsToFetch.getText());
							ResultSet fbIDList = dbops.fetchIDs(connection, amount);
							String lastID="";
							while (fbIDList.next())
							{
								String fbID = fbIDList.getString(1);	
								FetchAtOnce fetch = new FetchAtOnce(fbID, accountUsername, accountPassword);
								fetch.start();	
								count++;
								if(count==Integer.parseInt(amount))
								{
									lastID=fbID;
								}
							}
							
							TimeUnit.SECONDS.sleep(20);
							while(dbops.checkIDAvailability(connection, lastID))
							{
								TimeUnit.SECONDS.sleep(20);
							}
							JOptionPane.showMessageDialog(null, "Fetching Completed", "Success", JOptionPane.INFORMATION_MESSAGE);
							TimeUnit.SECONDS.sleep(3);
							lblLoading.setVisible(false);
						}

					}
					catch(Exception parsing)
					{
						parsing.printStackTrace();
					}
					//JOptionPane.showMessageDialog(null, count + " Facebook user datas Fetched", "Operation Successful!", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		JButton btnFetchAll = new JButton("Fetch All");
		btnFetchAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				//looping fetch
				lblLoading.setVisible(true);
				int count=0;
				int dbCount = dbops.validateNumberOfRecords(connection);
				int inputCount = Integer.parseInt(txtIDsToFetch.getText());
				if(txtIDsToFetch.getText().equals(""))
				{
					System.out.println("Enter number of IDs to fetch please!");
					JOptionPane.showMessageDialog(null, "Enter number of IDs to fetch at a time please!", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else if(inputCount > dbCount)
				{
					JOptionPane.showMessageDialog(null, "There are no that amount of IDs to be fetched!", "Error", JOptionPane.ERROR_MESSAGE);
					System.out.println("There are no that amount of IDs to be fetched!");
				}
				else
				{
					String accountUsername = dbops.getAccountUserName(connection);
					String accountPassword = dbops.getAccountPassword(connection);
					String amount = (txtIDsToFetch.getText());
					String lastID="";
					//starts here
					while(dbCount!=0 && dbCount>inputCount)
					{
						try
						{
							ResultSet fbIDList = dbops.fetchIDs(connection, amount);
							
							while (fbIDList.next())
							{
								String fbID = fbIDList.getString(1);	
								FetchAtOnce fetch = new FetchAtOnce(fbID, accountUsername, accountPassword);
								fetch.start();	
								count++;
								if(count==Integer.parseInt(amount))
								{
									lastID=fbID;
								}
							}
							
							TimeUnit.SECONDS.sleep(20);
							while(dbops.checkIDAvailability(connection, lastID))
							{
								TimeUnit.SECONDS.sleep(20);
							}
							System.out.println("########## Fetching one set COMPLETED! ############");
							TimeUnit.SECONDS.sleep(3);
							lblLoading.setVisible(false);
							dbCount = dbops.validateNumberOfRecords(connection);
						}
						catch(Exception exx)
						{
							exx.printStackTrace();
						}
						//ends here
					}
					System.out.println("################## FETCHING ALL SETS COMPLETED SUCCESSFULLY! ############");
				}	
			}
		});
		btnFetchAll.setFont(new Font("Ubuntu", Font.PLAIN, 12));
		btnFetchAll.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnFetchAll.setBounds(28, 98, 99, 23);
		panel_1.add(btnFetchAll);
		btnFetch.setFont(new Font("Ubuntu", Font.PLAIN, 12));
		btnFetch.setBounds(129, 98, 99, 23);
		btnFetch.setBorder(new EmptyBorder(0, 0, 0, 0));
		panel_1.add(btnFetch);
		
		JLabel lblEnterFbId = new JLabel("Enter FB ID :");
		lblEnterFbId.setFont(new Font("Ubuntu", Font.PLAIN, 12));
		lblEnterFbId.setForeground(Color.WHITE);
		lblEnterFbId.setBounds(18, 207, 89, 14);
		panel_1.add(lblEnterFbId);
		
		txtFBID = new JTextField();
		txtFBID.setHorizontalAlignment(SwingConstants.CENTER);
		txtFBID.setBounds(94, 204, 134, 20);
		txtFBID.setBorder(new EmptyBorder(0, 0, 0, 0));
		panel_1.add(txtFBID);
		txtFBID.setColumns(10);
		
		JButton btnFetchSingle = new JButton("Fetch");
		btnFetchSingle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//empty field validation here
				if(txtFBID.getText().equals(""))
					JOptionPane.showMessageDialog(null, "Enter a FB ID please!", "Error", JOptionPane.ERROR_MESSAGE);
				else if(dbops.checkAvailabilityOnUserData(connection, txtFBID.getText()))
				{
					JOptionPane.showMessageDialog(null, "Details already exist in the Database", "Error", JOptionPane.INFORMATION_MESSAGE);
				}
				else
				{
					//code here
					CoreBase executeTest = new CoreBase();
					try 
					{
						lblLoading.setVisible(true);
						String facebookUsername = dbops.getAccountUserName(connection);
						String facebookPassword = dbops.getAccountPassword(connection);
						User entity = executeTest.Facebook_Login(txtFBID.getText(), facebookUsername, facebookPassword, connection);
						if(dbops.insertUserData(connection, entity))
							JOptionPane.showMessageDialog(null, "Data saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
						else
							JOptionPane.showMessageDialog(null, "Operation failed!", "Error", JOptionPane.ERROR_MESSAGE);
					} 
					catch (InterruptedException e) 
					{
						e.printStackTrace();
					}
					lblLoading.setVisible(false);
				}
			}
		});
		btnFetchSingle.setFont(new Font("Ubuntu", Font.PLAIN, 12));
		btnFetchSingle.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnFetchSingle.setBounds(129, 244, 99, 23);
		panel_1.add(btnFetchSingle);
		
		JLabel lblSingleFetch = new JLabel("Single Fetch");
		lblSingleFetch.setFont(new Font("Ubuntu", Font.BOLD, 18));
		lblSingleFetch.setForeground(Color.WHITE);
		lblSingleFetch.setBounds(18, 159, 117, 23);
		panel_1.add(lblSingleFetch);
		
		JLabel lblBulkFetch = new JLabel("Bulk Fetch");
		lblBulkFetch.setForeground(Color.WHITE);
		lblBulkFetch.setFont(new Font("Ubuntu", Font.BOLD, 18));
		lblBulkFetch.setBounds(18, 20, 117, 23);
		panel_1.add(lblBulkFetch);
		
		JLabel lblResetBulk = new JLabel("");
		lblResetBulk.setToolTipText("Reset Criterias");
		lblResetBulk.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				txtIDsToFetch.setText(null);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				txtIDsToFetch.setText(null);
			}
		});
		lblResetBulk.setIcon(new ImageIcon(resetUrl));
		lblResetBulk.setBounds(194, 11, 42, 46);
		panel_1.add(lblResetBulk);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setToolTipText("Reset Criterias");
		lblNewLabel_1.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				txtFBID.setText(null);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setIcon(new ImageIcon(resetUrl));
		lblNewLabel_1.setBounds(190, 154, 46, 39);
		panel_1.add(lblNewLabel_1);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//code
				Settings.main(null);
			}
		});
		lblNewLabel.setToolTipText("Settings");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setIcon(new ImageIcon(settingsUrl));
		lblNewLabel.setBounds(462, 13, 36, 40);
		contentPane.add(lblNewLabel);
	}
}
