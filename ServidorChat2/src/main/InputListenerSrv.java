package main;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JTextArea;
import javax.swing.JTextField;

public class InputListenerSrv extends Thread {
	static int ID_CLIENTE = 0;
	private final String idConexion;
	private Socket cliente;
	JTextArea textArea = null;
	JTextField textoF = null;
	ObjectOutputStream osalida = null;
	ObjectInputStream oentrada = null;

	public InputListenerSrv(Socket cliente, JTextArea textArea, JTextField texto) throws IOException {
		ID_CLIENTE++;
		this.idConexion = String.valueOf(ID_CLIENTE);
		this.cliente = cliente;
		this.textArea = textArea;
		this.textoF = texto;
		this.osalida = new ObjectOutputStream(cliente.getOutputStream());
		this.oentrada = new ObjectInputStream(cliente.getInputStream());
	}

	public void run() {
		Datos datos = new Datos();
		datos.setContenido("");
		while (!datos.getContenido().equals("*")) {
			try {
				datos = (Datos) oentrada.readObject();
				if (!datos.getContenido().equals("*")) {
				textArea.append(datos.getContenido());
				GestorConexiones.getInstance().mensajeDeDifusion(datos.getContenido());

				}
				else
				{
					GestorConexiones.getInstance().cerrarConexion(idConexion);									
				}
			} catch (ClassNotFoundException e) {
				System.out.println(" !ERROR: Input Listener Servidor -> ClassNotFoundException");
			} catch (IOException e) {
				System.out.println(" !ERROR: Input Listener Servidor -> IOException");
			}

		}
		this.textoF.setText("Conexiones actuales: " + GestorConexiones.getInstance().getNumUsuarios());			
		System.out.println(" #CONEXION " + idConexion + " -> Desconectado\n");
	}

	public String getIdConexion() {
		return idConexion;
	}

	public void cerrarConexion() throws IOException {
		if(cliente != null)
			cliente.close();
	}

	public void enviarMensaje(String mensaje) throws IOException {
		Datos datos = new Datos();
		datos.setContenido(mensaje);
		osalida.writeObject(datos);
	}

}
