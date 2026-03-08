package venta;

import sistema.AsignadorId;

public class Stock {
	private final long id;
	private Producto producto;
	private int udsEnStock;
	
	public Stock(Producto p, int uds) {
		this.id = AsignadorId.getInstancia().siguienteId();
		this.producto = p;
		this.udsEnStock = uds;
	}
	
	public long getId() {
		return id;
	}

	public int getUdsEnStock() {
		return udsEnStock;
	}

	public void setUdsEnStock(int udsEnStock) {
		this.udsEnStock = udsEnStock;
	}
	
	public void reducirStock() {
		if(this.udsEnStock > 0)
			this.udsEnStock--;
	}
	
	public void reducirStock(int unidades) {
		if(unidades < 0)
			throw new IllegalArgumentException("No se pueden reducir unidades negativas");
		if((udsEnStock -= unidades) < 0)
			udsEnStock = 0;
	}
	
	public void incrementarStock() {
		this.udsEnStock++;
	}
	
	public void incrementarStock(int unidades) {
		if(unidades < 0)
			throw new IllegalArgumentException("No se pueden incrementar unidades negativas");
		this.udsEnStock += unidades;
	}

	public Producto getProducto() {
		return producto;
	}
	
	public boolean disponible() {
		return udsEnStock > 0;
	}
	
	public boolean disponible(int uds) {
		return udsEnStock >= uds;
	}
	
	@Override
	public int hashCode() {
	    return Long.hashCode(id);
	}
	
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;
	    Stock st = (Stock) o;
	    return id == st.id;
	}
	
	@Override
	public String toString() {
		return String.format("Stock{%s Numero de unidades=%d}", producto, udsEnStock);
	}
}
