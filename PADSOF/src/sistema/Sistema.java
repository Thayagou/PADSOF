package sistema;

import java.io.Serializable;
import java.time.*;
import java.util.*;

public class Sistema implements Serializable {
	private static final long serialVersionUID = 1L;
	private static Sistema instancia;
	private Duration tiempoCaducaCarrito;
	private Duration tiempoCaducaOferta;
	private double precioValoracion;
	private double ponderacionCategoria;
	private double ponderacionUdsCompra = 0;
	private double ponderacionPrecioCompra = 0;
	private double ponderacionValoracionesProducto = 0;
	private double ponderacionProductoRecomendado = 0;
	private double ponderacionBusqueda = 0;
	private int numProdsRecomendados = 0;
	private Set<ParametroRecomendacion> parametros = new HashSet<>();

	private Sistema(Duration tiempoCarrito, Duration tiempoOferta, double precioValoracion, ParametroRecomendacion... parametrosIniciales) {
		this.tiempoCaducaCarrito = tiempoCarrito;
		this.tiempoCaducaOferta = tiempoOferta;
		this.precioValoracion = precioValoracion;

		for(ParametroRecomendacion p : parametrosIniciales) {
			this.parametros.add(p);
		}
	}
	
	public static Sistema getInstancia() {
		if (Sistema.instancia == null)
			Sistema.instancia = new Sistema(Duration.ofDays(3), Duration.ofDays(3), 20.0, ParametroRecomendacion.MAS_VENDIDO);
		
		return Sistema.instancia;
	}

	public Duration getTiempoCaducaCarrito() {
		return tiempoCaducaCarrito;
	}

	public boolean setTiempoCaducaCarrito(Duration tiempoCaducaCarrito) {
		this.tiempoCaducaCarrito = tiempoCaducaCarrito;
		return true;
	}

	public Duration getTiempoCaducaOferta() {
		return tiempoCaducaOferta;
	}

	public boolean setTiempoCaducaOferta(Duration tiempoCaducaOferta) {
		this.tiempoCaducaOferta = tiempoCaducaOferta;
		return true;
	}

	public double getPrecioValoracion() {
		return precioValoracion;
	}

	public double getPonderacionCategoria() {
		return ponderacionCategoria;
	}

	public double getPonderacionUdsCompra() {
		return ponderacionUdsCompra;
	}
	
	public double getPonderacionPrecioCompra() {
		return ponderacionPrecioCompra;
	}

	public double getPonderacionBusqueda() {
		return ponderacionBusqueda;
	}
	
	public int getNumProductosRecomendados() {
		return numProdsRecomendados;
	}

	public double getPonderacionValoracionesProducto() {
		return ponderacionValoracionesProducto;
	}

	public double getPonderacionProductoRecomendado() {
		return ponderacionProductoRecomendado;
	}

	public boolean setPrecioValoracion(double precioValoracion) {
		if(precioValoracion < 0)
			return false;
		this.precioValoracion = precioValoracion;
		return true;
	}

	public Set<ParametroRecomendacion> getParametros() {
		return parametros;
	}

	public boolean gestionarParametro(ParametroRecomendacion p, boolean activo) {
		if(activo)
			return this.parametros.add(p);
		else {
			if(parametros.size() > 1) {
				return this.parametros.remove(p);
			} else
				return false;
		} 
	}
	
	public boolean pagoTarjeta() {
		return Math.random() < 1;
	}
	
	

}
