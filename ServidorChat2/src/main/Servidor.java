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

	ArrayList<ObjectOutputStream> lista;
	int PUERTO = 44444;
	int MAXIMO_CONEXIONES = 3;
	JTextArea textArea = null;
	JTextField texto = null;
	boolean continuar = true;
	ServerSocket servidor = null;

	public Servidor(JTextArea textArea, JTextField texto) {
		lista = new ArrayList<ObjectOutputStream>();
		this.texto = texto;
		this.textArea = textArea;
		texto.setText("0");
	}

	public void run() {

		try {
			servidor = new ServerSocket(PUERTO);

			System.out.println("Servidor iniciado...");
			Socket socket = new Socket();
			
			texto.setText("Conexiones actuales: "+ lista.size());
			while (continuar) {
				socket = servidor.accept();
				if (lista.size() < MAXIMO_CONEXIONES) {
					ObjectOutputStream osalida = new ObjectOutputStream(socket.getOutputStream());
					lista.add(osalida);
					ObjectInputStream oentrada = new ObjectInputStream(socket.getInputStream());
					texto.setText("Conexiones actuales: "+ lista.size());
					HiloRecibir hilo = new HiloRecibir(textArea, texto, osalida, oentrada, lista);
					hilo.start();
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
			for(int i = 0;i<lista.size();i++)
			{
				ObjectOutputStream os = lista.get(i);
				Datos datos =new Datos();
				datos.setContenido("*");
				os.writeObject(datos);
			}
			servidor.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
