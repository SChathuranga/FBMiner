package com.genesislab.views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.genesislab.seleniumbase.DBConnector;
import com.genesislab.seleniumbase.DBOperations;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class Settings extends JFrame {

	private JPanel contentPane;
	private JTextField txtNewUsername;
	private JTextField txtNewPassword;
	static Connection connection = null;
	DBOperations dbops = new DBOperations();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Settings frame = new Settings();
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
	public Settings() {
		setTitle("Settings");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setForeground(Color.WHITE);
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		connection = DBConnector.dbConnector();
		try {
			if(connection.isValid(0))
				System.out.println("Database Connection Established in Settings");
			else
				System.out.println("Database Connection Failed in Settings");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		JLabel lblSettings = new JLabel("Settings");
		lblSettings.setHorizontalAlignment(SwingConstants.CENTER);
		lblSettings.setFont(new Font("Ubuntu", Font.BOLD, 18));
		lblSettings.setForeground(Color.WHITE);
		lblSettings.setBounds(174, 0, 86, 31);
		contentPane.add(lblSettings);
		
		JLabel lblUsername = new JLabel("Facebook Username:");
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setFont(new Font("Ubuntu", Font.PLAIN, 12));
		lblUsername.setBounds(34, 42, 127, 14);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Facebook Password:");
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setFont(new Font("Ubuntu", Font.PLAIN, 12));
		lblPassword.setBounds(34, 71, 127, 14);
		contentPane.add(lblPassword);
		
		JLabel lblUsernameValue = new JLabel("Facebook Username:");
		lblUsernameValue.setForeground(Color.WHITE);
		lblUsernameValue.setFont(new Font("Ubuntu", Font.PLAIN, 12));
		lblUsernameValue.setBounds(194, 43, 191, 14);
		lblUsernameValue.setText(dbops.getAccountUserName(connection));
		contentPane.add(lblUsernameValue);
		
		JLabel lblPasswordValue = new JLabel("Facebook Username:");
		lblPasswordValue.setForeground(Color.WHITE);
		lblPasswordValue.setFont(new Font("Ubuntu", Font.PLAIN, 12));
		lblPasswordValue.setBounds(194, 72, 191, 14);
		lblPasswordValue.setText(dbops.getAccountPassword(connection));
		contentPane.add(lblPasswordValue);
		
		dbops.getAccountUserName(connection);
		
		JLabel lblNewUserName = new JLabel("Enter New Username:");
		lblNewUserName.setForeground(Color.WHITE);
		lblNewUserName.setFont(new Font("Ubuntu", Font.PLAIN, 12));
		lblNewUserName.setBounds(34, 153, 127, 14);
		contentPane.add(lblNewUserName);
		
		JLabel lblEnterNewPassword = new JLabel("Enter New Password:");
		lblEnterNewPassword.setForeground(Color.WHITE);
		lblEnterNewPassword.setFont(new Font("Ubuntu", Font.PLAIN, 12));
		lblEnterNewPassword.setBounds(34, 183, 127, 14);
		contentPane.add(lblEnterNewPassword);
		
		txtNewUsername = new JTextField();
		txtNewUsername.setFont(new Font("Ubuntu", Font.PLAIN, 12));
		txtNewUsername.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtNewUsername.setBounds(194, 151, 191, 20);
		contentPane.add(txtNewUsername);
		txtNewUsername.setColumns(10);
		
		
		txtNewPassword = new JTextField();
		txtNewPassword.setFont(new Font("Ubuntu", Font.PLAIN, 12));
		txtNewPassword.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtNewPassword.setColumns(10);
		txtNewPassword.setBounds(194, 181, 191, 20);
		contentPane.add(txtNewPassword);
		
		JButton btnUpdate = new JButton("Update!");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(dbops.updateAccountDetails(connection, lblUsernameValue.getText(), txtNewUsername.getText(), txtNewPassword.getText()))
				{
					lblUsernameValue.setText(dbops.getAccountUserName(connection));
					lblPasswordValue.setText(dbops.getAccountPassword(connection));
					JOptionPane.showMessageDialog(null, "Account details updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
				}
				else
					JOptionPane.showMessageDialog(null, "Account details update Failed!", "Failed", JOptionPane.ERROR_MESSAGE);
			}
		});
		btnUpdate.setFont(new Font("Ubuntu", Font.PLAIN, 12));
		btnUpdate.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnUpdate.setBounds(296, 211, 89, 23);
		contentPane.add(btnUpdate);
		
		JLabel lblUpdateFacebookAccount = new JLabel("Change Facebook Account");
		lblUpdateFacebookAccount.setHorizontalAlignment(SwingConstants.LEFT);
		lblUpdateFacebookAccount.setForeground(Color.WHITE);
		lblUpdateFacebookAccount.setFont(new Font("Ubuntu", Font.BOLD, 14));
		lblUpdateFacebookAccount.setBounds(34, 109, 191, 31);
		contentPane.add(lblUpdateFacebookAccount);
	}
}
