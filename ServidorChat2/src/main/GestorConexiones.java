package main;

import java.io.IOException;
import java.util.*;

public class GestorConexiones {
    
	private Map<String, InputListenerSrv> conexiones = new Hashtable<String, InputListenerSrv>();
    
    private GestorConexiones() {}
    
    public static GestorConexiones getInstance() {
        return Holder.INSTANCE;
    }
    
    private static class Holder {
        private static final GestorConexiones INSTANCE = new GestorConexiones();
    }

    private Object readResolve()  {
        return Holder.INSTANCE;
    }
    
    public void registrarConexion(InputListenerSrv conexion){
    	conexiones.put(conexion.getIdConexion(), conexion);
    }
    
    public InputListenerSrv recuperarConexion(String idConexion) {
    	return conexiones.get(idConexion);
    }
    
    public void cerrarConexion(String idConexion) throws IOException{
    	InputListenerSrv conexion = conexiones.get(idConexion);
        if (conexion != null) {
            conexiones.remove(idConexion);
        }
    }
    
    public void cerrarConexiones() throws IOException{
    	for (Map.Entry<String, InputListenerSrv> entrada : conexiones.entrySet()){
    		if (entrada.getValue() != null) {
    			entrada.getValue().cerrarConexion();
            }        
    	}     
    }
    
    public void mensajeDeDifusion(String mensaje) throws IOException{
        for (Map.Entry<String, InputListenerSrv> entrada : conexiones.entrySet()){
        	entrada.getValue().enviarMensaje(mensaje);            
        }
    }  
    
    public Map<String, InputListenerSrv> getConexiones(){
    	return conexiones;
    }
    
    public int getNumUsuarios() {
		return conexiones.size();
	}
}