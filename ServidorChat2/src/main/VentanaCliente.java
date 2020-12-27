package main;

import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class VentanaCliente extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	Socket socket = null;
	String nick;
	static JTextField mensaje = new JTextField();
	private JScrollPane scrollpane1;
	static JTextArea textarea1;
	JButton botonEnviar = new JButton("Enviar");
	JButton botonSalir = new JButton("Salir");
	Cliente cliente  = null;

	public VentanaCliente(Socket s, String nombre) throws IOException {
		super(" CONEXION DEL CLIENTE CHAT: " + nombre);
		setResizable(false);
		setAlwaysOnTop(true);
		getContentPane().setLayout(null);

		mensaje.setBounds(10, 10, 400, 30);
		getContentPane().add(mensaje); 

		textarea1 = new JTextArea();
		scrollpane1 = new JScrollPane(textarea1);
		scrollpane1.setBounds(10, 50, 400, 300);
		getContentPane().add(scrollpane1);

		botonEnviar.setBounds(420, 10, 100, 30);
		getContentPane().add(botonEnviar);
		botonSalir.setBounds(420, 50, 100, 30);
		getContentPane().add(botonSalir);

		textarea1.setEditable(false);
		botonEnviar.addActionListener(this);
		botonSalir.addActionListener(this);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		socket = s;
		this.nick = nombre.toUpperCase();
		
		cliente = new Cliente();		
		socket = new Socket(cliente.getHost(),cliente.getPuerto());
		
		cliente = new Cliente(socket,textarea1, mensaje,botonEnviar);
		cliente.enviarMensaje(" # " + nick +" se ha conectado\n");
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == botonEnviar) {
			if (mensaje.getText().trim().length() == 0)
				return;
			String texto =" - " + nick + ": " + mensaje.getText();
			cliente.enviarMensaje(texto+"\n");
			mensaje.setText("");
		}
		if (e.getSource() == botonSalir) { // SE PULSA BOTON SALIR
			String texto = " # " + nick +" se ha desconectado\n" ;
			cliente.enviarMensaje(texto);
			cliente.enviarMensaje("*");
			System.out.println(" #CLIENTE DESCONECTADO\n");
			System.exit(0);
		}		
	}
}




