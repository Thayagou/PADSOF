package estadistica;

public class StatsMensualCliente extends StatsMensual {
	private int articulosIntercambiados = 0;
	private double recaudacionValoraciones = 0;
	
	public StatsMensualCliente() {
		super();
	}
	
	public boolean incrementarValoracion(double precio) {
		if (precio < 0) return false;
		recaudacionValoraciones += precio;
		
		return true;
	}
	
	public boolean incrementarIntercambio(int numArticulos) {
		if (numArticulos < 0) return false;
		articulosIntercambiados += numArticulos;
		
		return true;
	}
}
