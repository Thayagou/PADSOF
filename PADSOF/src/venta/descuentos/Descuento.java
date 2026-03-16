package venta.descuentos;

import java.time.*;

import sistema.AsignadorId;
import venta.productos.Producto;

public abstract class Descuento {
	private final long id;
	private double valorMin;
	private LocalDateTime inicio;
	private LocalDateTime fin;
	private boolean finalizado;
	private CondicionDescuento condicion;
	
	/**
	 * Creador de la clase descuento
	 * @param valorMin Valor minimo para poder aplicar el descuento (de unidades o volumen de compra)
	 * @param inicio Fecha y hora de inicio del descuento
	 * @param fin Fecha y hora de finalizacion del descuento
	 * @param condicion Tipo de condicion del descuento
	 */
	public Descuento(double valorMin, LocalDateTime inicio, LocalDateTime fin, CondicionDescuento condicion) {
		this.id = AsignadorId.getInstancia().siguienteId();
		this.valorMin = valorMin;
		this.inicio = inicio;
		this.fin = fin;
		this.condicion = condicion;
		this.finalizado = false;
	}
	
	/**
	 * Método para calcular un precio descontado sobre el precio unitario de un producto
	 * @param numUds Número de unidades para comprobar si se cumple la condicion de cantidad
	 * @param volumen Volumen de compra para ver si se cumple la condicion de volumen de compra
	 * @param precio Precio unitario sobre el cual se quiere calcular el descuento
	 * @return Precio de una sola unidad de producto tras aplicar el descuento
	 */
	public double getPrecioDescontado(int numUds, double volumen, double precio) {
		return precio;
	}
	
	/**
	 * Método para obtener el regalo proporcionado por el descuento
	 * @param numUds Número de unidades para comprobar si se cumple la condicion de cantidad
	 * @param volumen Volumen de compra para ver si se cumple la condicion de volumen de compra
	 * @return Regalo del descuento o null si no cumple las condiciones o no es descuento de regalo
	 */
	public Producto getRegalo(int numUds, double volumen) {
		return null;
	}
	
	/**
	 * Método para marcar un descuento como finalizado
	 */
	public void finalizarDescuento() {
		this.finalizado = true;
	}
	
	/**
	 * Método para comprobar si un descuento está vigente.
	 * A la vez, si está caducado lo marca como finalizado, aplicando una actualización en diferido
	 * @return true si está vigente, false si no
	 */
	public boolean isVigente() {
	    if (finalizado) return false;
	    LocalDateTime ahora = LocalDateTime.now();
	    if(ahora.isAfter(inicio)) return isCaducado();
	    else {
	    	return false;
	    }
	}
	
	/**
	 * Método para comprobar si un descuento ha caducado.
	 * @return true si el descuento está caducado, false si no
	 */
	public boolean isCaducado() {
		if(LocalDateTime.now().isBefore(fin)) return true;
		else {
			finalizado = true;
			return false;
		}
	}

	/**
	 * Getter de la id del descuento
	 * @return id del descuento
	 */
	public long getId() {
		return id;
	}

	/**
	 * Getter del valor minimo del descuento
	 * @return Valor minimo del descuento
	 */
	public double getValorMin() {
		return valorMin;
	}

	/**
	 * Getter de la fecha de inicio del descuento
	 * @return Fecha y hora de inicio del descuento
	 */
	public LocalDateTime getInicio() {
		return inicio;
	}

	/**
	 * Getter de la fecha de finalizacion del descuento
	 * @return Fecha y hora de finalizacion del descuento
	 */
	public LocalDateTime getFin() {
		return fin;
	}

	/**
	 * Getter del tipo de condicion del descuento
	 * @return Tipo de condicion del descuento
	 */
	public CondicionDescuento getCondicion() {
		return condicion;
	}

	/**
	 * Método para comprobar si se cumplen las condiciones del descuento
	 * @param numUds Numero de unidades para comprobar la condicion de cantidad
	 * @param volumen Volumen de compra para comprobar la condicion de volumen
	 * @return true si cumple las condiciones, false si no
	 */
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
	
	/**
	 * Método para comprobar si dos descuentos son iguales, usando su id
	 */
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;
	    Descuento descuento = (Descuento) o;
	    return id == descuento.id;
	}

	/**
	 * Método para obtener el código hash de un descuento, usando su id
	 */
	@Override
	public int hashCode() {
	    return Long.hashCode(id);
	}
	
	/**
	 * Método para imprimir un descuento
	 */
	@Override
	public String toString() {
	    return String.format("Descuento{id=%d, condicion=%s, inicio=%s, fin=%s, vigente=%s}",
	    		id, condicion, inicio.toString(), fin.toString(), isVigente());
	}
}
