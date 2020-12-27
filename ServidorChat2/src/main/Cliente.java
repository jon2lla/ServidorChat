package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Cliente {
	private int puerto = 44444;
	private String host = "localhost";
	Socket socket = null;
	JTextArea textArea = null;
	JTextField texto = null;
	ObjectOutputStream fsalida = null;
	JButton botonEnviar = null;

	public Cliente() {}

	public Cliente(Socket socket, JTextArea textArea, JTextField texto, JButton botonEnviar) throws IOException {
		this.socket = socket;
		this.textArea = textArea;
		this.texto = texto;
		this.botonEnviar = botonEnviar;
		ObjectInputStream fentrada = null;

		fsalida = new ObjectOutputStream(socket.getOutputStream());
		fentrada = new ObjectInputStream(socket.getInputStream());

		InputListenerClt hilo = new InputListenerClt(this.textArea, this.texto, fentrada, this.botonEnviar);
		hilo.start();
		System.out.println("\n #CLIENTE CONECTADO\n");
	}

	public void enviarMensaje(String mensaje) {
		Datos datos = new Datos();
		datos.setContenido(mensaje);
		try {
			if (botonEnviar.isEnabled())
				fsalida.writeObject(datos);
		} catch (IOException e) {
			System.out.println(" !CONEXION PERDIDA\n");
		}
	}

	public int getPuerto() {
		return puerto;
	}

	public String getHost() {
		return host;
	}

}
