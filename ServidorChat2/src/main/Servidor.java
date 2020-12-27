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
	protected static int MAX_CONEXIONES = 3;
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

			System.out.println("\n **SERVIDOR INICIADO**\n");
			Socket socket = new Socket();
			
			texto.setText("Conexiones actuales: " + GestorConexiones.getInstance().getNumUsuarios());

			while (continuar) {
				Thread.sleep(200);
				if (GestorConexiones.getInstance().getNumUsuarios() < Servidor.MAX_CONEXIONES)
					textArea.append(" Esperando conexiones... \n");
				else
					textArea.append(" Servidor lleno\n");
				socket = servidor.accept();
				if (GestorConexiones.getInstance().getNumUsuarios() < MAX_CONEXIONES) {
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
			System.out.println(" **SERVIDOR TERMINADO**");
		} catch (IOException e) {
			System.out.println(" **SERVIDOR CERRADO**");
			System.exit(0);
		} catch (InterruptedException e) {
			
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
