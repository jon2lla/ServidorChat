package main;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class InputListenerClt extends Thread {

	JTextArea textArea = null;
	JTextField texto = null;
	ObjectInputStream oentrada = null;
	JButton botonEnviar = null;

	public InputListenerClt(JTextArea textArea, JTextField texto, ObjectInputStream oentrada, JButton botonEnviar) {
		this.textArea = textArea;
		this.texto = texto;
		this.oentrada = oentrada;
		this.botonEnviar = botonEnviar;
	}

	public void run() {
		Datos datos = new Datos();
		datos.setContenido("");
		
		while (!datos.getContenido().equals("*")) {
			try {
				datos = (Datos) oentrada.readObject();
				if(!datos.getContenido().equals("*"))
					textArea.append(datos.getContenido());
				else
					textArea.append(" #SERVIDOR DESCONECTADO\n");			
			} catch (ClassNotFoundException e) {
				System.out.println(" !ERROR: Input Listener Cliente -> ClassNotFoundException\n");
			} catch (IOException e) {
				System.out.println(" !ERROR: Input Listener Cliente -> IOException");
			}
		}
		botonEnviar.setEnabled(false);
	}
}
