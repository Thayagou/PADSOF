package usuario;

public class ClienteRegistrado {
	String nombre;
	
	public ClienteRegistrado(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	@Override
	public String toString() {
		return nombre;
	}
	
	
}
