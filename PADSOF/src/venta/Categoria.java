package venta;

public class Categoria {
	private String nombre;
	
	public Categoria(String nombre) {
		this.nombre = nombre;
	}
	
	@Override
	public String toString() {
		return nombre;
	}
}
