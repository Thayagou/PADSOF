package venta;

import java.time.*;

import sistema.AsignadorId;

public abstract class Descuento {
	private final long id;
	private double valorMin;
	private LocalDateTime inicio;
	private LocalDateTime fin;
	private boolean finalizado;
	private CondicionDescuento condicion;
	
	public Descuento(double valorMin, LocalDateTime inicio, LocalDateTime fin, CondicionDescuento condicion) {
		this.id = AsignadorId.getInstancia().siguienteId();
		this.valorMin = valorMin;
		this.inicio = inicio;
		this.fin = fin;
		this.condicion = condicion;
		this.finalizado = false;
	}
	
	public double getPrecioDescontado(int numUds, double volumen, double precio) {
		return 0.0;
	}
	
	public Producto getRegalo(int numUds, double volumen, double precio) {
		return null;
	}
	
	public void finalizarDescuento() {
		this.finalizado = true;
	}
	
	public boolean isVigente() {
	    if (finalizado) return false;
	    LocalDateTime ahora = LocalDateTime.now();
	    if(!ahora.isBefore(inicio) && !ahora.isAfter(fin)) return true;
	    else {
	    	finalizado = true;
	    	return false;
	    }
	}

	public long getId() {
		return id;
	}

	public double getValorMin() {
		return valorMin;
	}

	public LocalDateTime getInicio() {
		return inicio;
	}

	public LocalDateTime getFin() {
		return fin;
	}

	public CondicionDescuento getCondicion() {
		return condicion;
	}

	public boolean cumpleCondiciones(int numUds, double volumen) {
		if(isVigente() == false) return false;
		
		switch(condicion) {
		case SIN_CONDICION:
			return true;
		case VOLUMEN:
			return (volumen >= valorMin);
		case CANTIDAD:
			return (numUds >= valorMin);
		default:
				return false;
		}
	}
	
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;
	    Descuento descuento = (Descuento) o;
	    return id == descuento.id;
	}

	@Override
	public int hashCode() {
	    return Long.hashCode(id);
	}
	
	@Override
	public String toString() {
	    return String.format("Descuento{id=%d, condicion=%s, inicio=%s, fin=%s, vigente=%s}",
	    		id, condicion, inicio.toString(), fin.toString(), isVigente());
	}
}
