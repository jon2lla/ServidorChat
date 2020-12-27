package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.awt.event.ActionEvent;

public class VentanaIntro extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaIntro frame = new VentanaIntro();
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
	public VentanaIntro() {
		setResizable(false);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 272, 187);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		textField = new JTextField();
		textField.setBounds(44, 47, 149, 19);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Introduce tu nick:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(44, 22, 128, 25);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setForeground(Color.RED);
		lblNewLabel_1.setBounds(44, 77, 165, 19);
		contentPane.add(lblNewLabel_1);
		lblNewLabel_1.setVisible(false);
		
		JButton btnNewButton = new JButton("Aceptar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblNewLabel_1.setVisible(false);
				if(!textField.getText().equals(""))
				{
					
					Socket s = null;
					VentanaCliente cl;
					try {
						cl = new VentanaCliente(s,textField.getText());
						cl.setBounds(0, 0, 540, 400);
						cl.setVisible(true);
						setVisible(false);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						lblNewLabel_1.setVisible(true);
						lblNewLabel_1.setText("Error al conectarse");
					}
				
				}
				else
				{
					lblNewLabel_1.setVisible(true);
					lblNewLabel_1.setText("Debe introducir un nick");
				}
					
			}
		});
		btnNewButton.setBounds(108, 95, 85, 21);
		contentPane.add(btnNewButton);
		
		
		
		
		
	}
}
