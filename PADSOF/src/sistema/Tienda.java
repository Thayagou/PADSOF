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
	
	/**
	 * Método para obtener el gestor de la tienda
	 * @return Gestor de la tienda
	 */
	public Gestor getGestor() {
		return (Gestor)usuarios.get("GESTOR");
	}
	
	/**
	 * Método para obtener un usuario con el nombre
	 * @param nombre Nombre del usuario
	 * @return Usuario obtenido con el nombre
	 */
	public Usuario getUsuario(String nombre) {
		return usuarios.get(nombre);
	}
	
	/**
	 * Método para obtener un empleado con su nombre
	 * @param nombre Nombre del empleado
	 * @return Empleado obtenido con ese nombre
	 */
	public Empleado getEmpleado(String nombre) {
		Usuario usr = usuarios.get(nombre);
		if(usr instanceof Empleado) {
			return (Empleado)usr;
		}
		return null;
	}
	
	/**
	 * Método para obtener un cliente con su nombre
	 * @param nombre Nombre del cliente
	 * @return Cliente obtenido con ese nombre
	 */
	public ClienteRegistrado getCliente(String nombre) {
		Usuario usr = usuarios.get(nombre);
		if(usr instanceof ClienteRegistrado) {
			return (ClienteRegistrado)usr;
		}
		return null;
	}
	
	/**
	 * Método para registrarse en la tienda como cliente
	 * @param nombre Nombre de usuario del cliente
	 * @param contrasena Contraseña de la cuenta
	 * @param confirmarContrasena Confirmacion de la contraseña
	 * @return Usuario que se creó
	 */
	public Usuario registrarse(String nombre, String contrasena, String confirmarContrasena) {
		if(!contrasena.equals(confirmarContrasena))
			return null;
		if(usuarios.containsKey(nombre))
			return null;
		usuarios.put(nombre, new ClienteRegistrado(nombre, contrasena));
		return usuarios.get(nombre);
	}
	
	/**
	 * Método para iniciar sesión en la aplicación
	 * @param nombre Nombre de usuario
	 * @param contrasena Contraseña de la cuenta
	 * @return Usuario que está registrado con ese nombre de usuario
	 */
	public Usuario iniciarSesion(String nombre, String contrasena) {
		if(!usuarios.containsKey(nombre)) return null;
		if(usuarios.get(nombre).getContrasena().equals(contrasena))
			return usuarios.get(nombre);
		return null;
	}
	
	/**
	 * Método para dar de alta a un empleado nuevo o existente
	 * @param nombre Nombre de usuario del empleado
	 * @param contrasena Contraseña del usuario si se crea uno nuevo
	 * @param permisos Permisos del empleado que se da de alta
	 * @return true si se pudo dar de alta, false si ya existía y está dado de alta
	 */
	public boolean darDeAltaEmpleado(String nombre, String contrasena, Permiso...permisos) {
		Empleado emp = getEmpleado(nombre);
		if(emp != null) {
			if(emp.estaDeAlta()) return false;
			emp.darDeAlta();
			emp.setPermisos(permisos);
			return true;
		}
		usuarios.put(nombre, new Empleado(nombre, contrasena, permisos));
		return true;
	}
	
	/**
	 * Método para dar de baja a un empleado
	 * @param nombre Nombre del empleado a dar de baja
	 * @return true si se pudo dar de baja, false si no existía el empleado
	 */
	public boolean darDeBajaEmpleado(String nombre) {
		Empleado emp = getEmpleado(nombre);
		if(emp == null) return false;
		emp.darDeBaja();
		return true;
	}
}
