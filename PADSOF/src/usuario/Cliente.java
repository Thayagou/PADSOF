package usuario;

public class Cliente extends Usuario {

	public Cliente(String nombre, String contrasena) {
		super(nombre, contrasena);
	}
	
	public boolean tienePermiso(Permiso p) {
		return false;
	}

}
