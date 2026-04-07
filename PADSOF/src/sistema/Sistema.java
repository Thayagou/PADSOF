package sistema;

import java.io.Serializable;
import java.time.*;
import java.util.*;

import exceptions.InvalidArgumentException;
import usuario.Gestor;

public class Sistema implements Serializable {
	private static final long serialVersionUID = 1L;
	private static Sistema instancia;
	private Duration tiempoCaducaCarrito;
	private Duration tiempoCaducaOferta;
	private double precioValoracion;
	private double ponderacionCategoria = 1;
	private double ponderacionUdsCompra = 1;
	private double ponderacionPrecioCompra = 1;
	
	private double ponderacionValoracionesProducto = 1;
	private double ponderacionProductoRecomendado = 1;
	private double ponderacionBusqueda = 1;
	private int numProdsRecomendados = 10;
	private Set<ParametroSistema> parametros = new HashSet<>();

	private Sistema(Duration tiempoCarrito, Duration tiempoOferta, double precioValoracion, ParametroSistema... parametrosIniciales) {
		this.tiempoCaducaCarrito = tiempoCarrito;
		this.tiempoCaducaOferta = tiempoOferta;
		this.precioValoracion = precioValoracion;

		for(ParametroSistema p : parametrosIniciales) {
			this.parametros.add(p);
		}
	}
	
	public static Sistema getInstancia() {
		if (Sistema.instancia == null)
			Sistema.instancia = new Sistema(Duration.ofDays(3), Duration.ofDays(3), 20.0, ParametroSistema.values());
		
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

	public void setPonderacionCategoria(Gestor gestor, double ponderacionCategoria) throws InvalidArgumentException {
		if (ponderacionCategoria < 0) throw new InvalidArgumentException("La ponderación tiene que ser positiva", "setter de la ponderación de Categoría");
		this.ponderacionCategoria = ponderacionCategoria;
	}

	public void setPonderacionUdsCompra(Gestor gestor, double ponderacionUdsCompra)  throws InvalidArgumentException {
		if (ponderacionUdsCompra < 0) throw new InvalidArgumentException("La ponderación tiene que ser positiva", "setter de la ponderación de las unidades compradas");
		
		this.ponderacionUdsCompra = ponderacionUdsCompra;
	}

	public void setPonderacionPrecioCompra(Gestor gestor, double ponderacionPrecioCompra)  throws InvalidArgumentException {
		if (ponderacionPrecioCompra < 0) throw new InvalidArgumentException("La ponderación tiene que ser positiva", "setter de la ponderación del precio de la compra");
		
		this.ponderacionPrecioCompra = ponderacionPrecioCompra;
	}

	public void setPonderacionValoracionesProducto(Gestor gestor, double ponderacionValoracionesProducto)  throws InvalidArgumentException {
		if (ponderacionValoracionesProducto < 0) throw new InvalidArgumentException("La ponderación tiene que ser positiva", "setter de la ponderación de las valoraciones del producto");
		
		this.ponderacionValoracionesProducto = ponderacionValoracionesProducto;
	}

	public void setPonderacionProductoRecomendado(Gestor gestor, double ponderacionProductoRecomendado)  throws InvalidArgumentException {
		if (ponderacionProductoRecomendado < 0) throw new InvalidArgumentException("La ponderación tiene que ser positiva", "setter de la ponderación de la semejanza de producto");
		
		this.ponderacionProductoRecomendado = ponderacionProductoRecomendado;
	}

	public void setPonderacionBusqueda(Gestor gestor, double ponderacionBusqueda)  throws InvalidArgumentException {
		if (ponderacionBusqueda < 0) throw new InvalidArgumentException("La ponderación tiene que ser positiva", "setter de la ponderación de la búsqueda");
		
		this.ponderacionBusqueda = ponderacionBusqueda;
	}

	public void setNumProdsRecomendados(Gestor gestor, int numProdsRecomendados)  throws InvalidArgumentException {
		if (numProdsRecomendados < 0) throw new InvalidArgumentException("El número de productos recomendados tiene que ser positivo", "setter del número de productos recomendados ");
		
		this.numProdsRecomendados = numProdsRecomendados;
	}

	public boolean setPrecioValoracion(double precioValoracion) {
		if(precioValoracion < 0)
			return false;
		this.precioValoracion = precioValoracion;
		return true;
	}

	public Set<ParametroSistema> getParametros() {
		return parametros;
	}

	public boolean gestionarParametro(ParametroSistema p, boolean activo) {
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
