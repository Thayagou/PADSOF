package usuario;

public abstract class Usuario {
	private String nombre;
	protected String contrasena;

	public Usuario(String nombre, String contrasena) {
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
}
