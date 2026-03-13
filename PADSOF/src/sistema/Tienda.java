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
	
	public Gestor getGestor() {
		return (Gestor)usuarios.get("GESTOR");
	}
	
	public Usuario getUsuario(String nombre) {
		return usuarios.get(nombre);
	}
	
	public Empleado getEmpleado(String nombre) {
		Usuario usr = usuarios.get(nombre);
		if(usr instanceof Empleado) {
			return (Empleado)usr;
		}
		return null;
	}
	
	public ClienteRegistrado getCliente(String nombre) {
		Usuario usr = usuarios.get(nombre);
		if(usr instanceof ClienteRegistrado) {
			return (ClienteRegistrado)usr;
		}
		return null;
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
		if(!usuarios.containsKey(nombre)) return null;
		if(usuarios.get(nombre).getContrasena().equals(contrasena))
			return usuarios.get(nombre);
		return null;
	}
	
	public boolean darDeAltaEmpleado(String nombre, String contrasena, Permiso...permisos) {
		if(usuarios.containsKey(nombre)) return false;
		usuarios.put(nombre, new Empleado(nombre, contrasena, permisos));
		return true;
	}
	
	public boolean darDeBajaEmpleado(String nombre) {
		if(getEmpleado(nombre) == null) return false;
		usuarios.remove(nombre);
		return true;
	}
}
