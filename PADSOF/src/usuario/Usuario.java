/**
 * Este paquete recoje las subclases de usuario y otras clases relacionadas
 */
package usuario;

import java.io.Serializable;
import exceptions.*;

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
	 * @throws InvalidArgumentException Se lanza si los argumentos son inválidos
	 */
	public Usuario(String nombre, String contrasena) throws InvalidArgumentException {
		if(nombre == null || contrasena == null) throw new InvalidArgumentException("Null arguments for creating new user", "crear usuario");
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

	/**
	 * Método abastracto que devuelve si un usuario tiene cierto permiso
	 * @param permiso Permiso sobre el que se pregunta
	 * @return true si tiene el permiso, false en caso contrario
	 */
	public abstract boolean tienePermiso(Permiso permiso);
}
