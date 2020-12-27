package main;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JTextArea;
import javax.swing.JTextField;

public class HiloRecibir extends Thread {

	JTextArea textArea = null;
	JTextField textoF = null;
	ObjectOutputStream osalida = null;
	ObjectInputStream oentrada = null;
	ArrayList<ObjectOutputStream> lista = null;

	public HiloRecibir(JTextArea textArea, JTextField texto, ObjectOutputStream osalida, ObjectInputStream oentrada,
			ArrayList<ObjectOutputStream> lista) {
		this.textArea = textArea;
		this.textoF = texto;
		this.osalida = osalida;
		this.oentrada = oentrada;
		this.lista = lista;
	}

	public void run() {


		String texto = "";
		Datos datos = new Datos();
		datos.setContenido("");
		while (!datos.getContenido().equals("*")) {
			try {
				datos = (Datos) oentrada.readObject();
				if (!datos.getContenido().equals("*")) {
				textArea.append(datos.getContenido());
					for (int i = 0; i < lista.size(); i++) {
						ObjectOutputStream os = lista.get(i);
						os.writeObject(datos);
					}
				}
				else
				{
					lista.remove(osalida);
					
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		this.textoF.setText("Conexiones actuales: "+lista.size());
		
		
		System.out.println("Termino recibir ser");

	}

}
