/**
 * Este paquete recoje las subclases de usuario y otras clases relacionadas
 */
package usuario;

import java.io.Serializable;

/**
 * Clase abstracta que representa los usuarios de la tienda
 */
public abstract class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	private String nombre;
	protected String contrasena;

	/**
	 * Constructor de un usuario
	 * @param nombre Nombre del usuario
	 * @param contrasena COntraseña del usuario
	 * @throws IllegalArgumentException
	 */
	public Usuario(String nombre, String contrasena) 
		throws IllegalArgumentException {
		if(nombre == null || contrasena == null) throw new NullPointerException("Null arguments for creating new user");
		this.nombre = nombre;
		this.contrasena = contrasena;
	}
	
	/**
	 * Devuelve el nombre del usuario
	 * @return String con el nombre
	 */
	public String getNombre() {
		return this.nombre;
	}
	
	/**
	 * Devuelve la contraseña del usuario
	 * @return String con la contraseña
	 */
	public String getContrasena() {
		return this.contrasena;
	}
	
	/**
	 * Devuelve la información del usuario
	 * @return String con la información 
	 */
	@Override
	public String toString() {
		return "Nombre: " + nombre +
				"\nContraseña: " + contrasena;
	}

	public abstract boolean tienePermiso(Permiso productos);
}
