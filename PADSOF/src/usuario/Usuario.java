package usuario;

import java.io.Serializable;

public abstract class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	private String nombre;
	protected String contrasena;

	public Usuario(String nombre, String contrasena) 
		throws IllegalArgumentException {
		if(nombre == null || contrasena == null) throw new NullPointerException("Null arguments for creating new user");
		this.nombre = nombre;
		this.contrasena = contrasena;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public String getContrasena() {
		return this.contrasena;
	}
	
	@Override
	public String toString() {
		return "Nombre: " + nombre +
				"\nContraseña: " + contrasena;
	}

	public abstract boolean tienePermiso(Permiso productos);
}
