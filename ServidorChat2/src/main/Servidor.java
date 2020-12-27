package main;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Servidor extends Thread {

	int PUERTO = 44444;
	int MAXIMO_CONEXIONES = 3;
	JTextArea textArea = null;
	JTextField texto = null;
	boolean continuar = true;
	ServerSocket servidor = null;

	public Servidor(JTextArea textArea, JTextField texto) {
		this.texto = texto;
		this.textArea = textArea;
		texto.setText("0");
	}

	public void run() {

		try {
			servidor = new ServerSocket(PUERTO);

			System.out.println("Servidor iniciado...");
			Socket socket = new Socket();
			
			texto.setText("Conexiones actuales: " + GestorConexiones.getInstance().getNumUsuarios());
			while (continuar) {
				socket = servidor.accept();
				if (GestorConexiones.getInstance().getNumUsuarios() < MAXIMO_CONEXIONES) {
					InputListenerSrv hilo = new InputListenerSrv(socket, textArea, texto);
					hilo.start();
					GestorConexiones.getInstance().registrarConexion(hilo);
					System.out.println(" #CONEXION " + hilo.getIdConexion() + " -> Conectado\n");
					texto.setText("Conexiones actuales: " + GestorConexiones.getInstance().getNumUsuarios());
				}
				else
					socket.close();
				
			
				
			}

			socket.close();
			
			System.out.println("Servidor terminado");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Servidor cerrado");
			System.exit(0);
		}
		

	}

	public void desconectar() {
		continuar = false;
		try {
			GestorConexiones.getInstance().mensajeDeDifusion("*");			
			servidor.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
