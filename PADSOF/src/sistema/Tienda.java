package sistema;

import java.util.*;

import estadistica.Historial;
import usuario.*;

public class Tienda {
	private Almacen almacen;
	private Historial registro;
	private Map<String, Usuario> usuarios;
	

	public Tienda() {
		almacen = new Almacen();
		registro = new Historial();
		usuarios = new HashMap<>();
		usuarios.put("GESTOR", new Gestor("GESTOR", "GESTOR123"));
	}
	
	public Usuario getGestor() {
		return usuarios.get("GESTOR");
	}
	
	public boolean registrarse(String nombre, String contrasena, String confirmarContrasena) {
		if(!contrasena.equals(confirmarContrasena))
			return false;
		if(usuarios.containsKey(nombre))
			return false;
		usuarios.put(nombre, new ClienteRegistrado(nombre, contrasena));
		return true;
	}
	
	public Usuario iniciarSesion(String nombre, String contrasena) {
		if(usuarios.get(nombre).getContrasena().equals(contrasena))
			return usuarios.get(nombre);
		return null;
	}
}
