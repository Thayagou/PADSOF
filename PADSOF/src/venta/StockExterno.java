package venta;

public class StockExterno extends Stock {
	private double precioFinal;
	
	public StockExterno(Producto p, int uds, double precio) {
		super(p, uds);
		this.precioFinal = precio;
	}

	public double getPrecioFinal() {
		return precioFinal;
	}

	public void setPrecioFinal(double precioFinal) {
		this.precioFinal = precioFinal;
	}
	
}
