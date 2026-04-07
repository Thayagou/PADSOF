package sistema;

import java.io.Serializable;
import java.time.*;
import java.util.*;

import exceptions.InvalidArgumentException;
import usuario.Gestor;

/**
 * Esta clase representa el sistema que gestiona diferentes parámetros de la
 * tienda
 */
public class Sistema implements Serializable {
	private static final long serialVersionUID = 1L;
	/** Instancia única del sistema (singleton) */
	private static Sistema instancia;
	/** Tiempo de caducidad del carrito */
	private Duration tiempoCaducaCarrito;
	/** Tiempo de caducidad de las ofertas */
	private Duration tiempoCaducaOferta;
	/** Precio de solicitar una valoración */
	private double precioValoracion;
	/** Ponderación de la categoría de productos */
	private double ponderacionCategoria = 1;
	/** Ponderación basada en unidades compradas */
	private double ponderacionUdsCompra = 1;
	/** Ponderación basada en el precio de compra */
	private double ponderacionPrecioCompra = 1;
	/** Ponderación basada en las valoraciones de productos */
	private double ponderacionValoracionesProducto = 1;
	/** Ponderación de productos recomendados */
	private double ponderacionProductoRecomendado = 1;
	/** Ponderación basada en la búsqueda del usuario */
	private double ponderacionBusqueda = 1;
	/** Número de productos recomendados al usuario */
	private int numProdsRecomendados = 10;
	/** Conjunto de parámetros del sistema activos */
	private Set<ParametroSistema> parametros = new HashSet<>();


	/**
	 * Constructor de la clase
	 * @param tiempoCarrito       Tiempo de caducidad de un carrito
	 * @param tiempoOferta        Tiempo de caducidad de una oferta
	 * @param precioValoracion    Precio que cuesta solicitar una valoración
	 * @param parametrosIniciales Parámetros iniciales del sistema de recomendación
	 */
	private Sistema(Duration tiempoCarrito, Duration tiempoOferta, double precioValoracion,
			ParametroSistema... parametrosIniciales) {
		this.tiempoCaducaCarrito = tiempoCarrito;
		this.tiempoCaducaOferta = tiempoOferta;
		this.precioValoracion = precioValoracion;

		for (ParametroSistema p : parametrosIniciales) {
			this.parametros.add(p);
		}
	}

	/**
	 * Devuelve la instancia del sistema
	 * @return La instancia del sistema
	 */
	public static Sistema getInstancia() {
		if (Sistema.instancia == null)
			Sistema.instancia = new Sistema(Duration.ofDays(3), Duration.ofDays(3), 20.0, ParametroSistema.values());

		return Sistema.instancia;
	}

	/**
	 * Devuelve el tiempo de caducidad del carrito
	 * @return tiempo de caducidad del carrito
	 */
	public Duration getTiempoCaducaCarrito() {
		return tiempoCaducaCarrito;
	}

	/**
	 * Cambia el tiempo de caducidad del carrito
	 * @param tiempoCaducaCarrito Nuevo tiempo de caducidad del carrito
	 * @return true siempre
	 */
	public boolean setTiempoCaducaCarrito(Duration tiempoCaducaCarrito) {
		this.tiempoCaducaCarrito = tiempoCaducaCarrito;
		return true;
	}

	/**
	 * Devuelve el tiempo de caducidad de las ofertas
	 * @return tiempo de caducidad de las ofertas
	 */
	public Duration getTiempoCaducaOferta() {
		return tiempoCaducaOferta;
	}

	/**
	 * Cambia el tiempo de caducidad de las ofertas
	 * @param tiempoCaducaOferta Nuevo tiempo de caducidad de las ofertas
	 * @return true siempre
	 */
	public boolean setTiempoCaducaOferta(Duration tiempoCaducaOferta) {
		this.tiempoCaducaOferta = tiempoCaducaOferta;
		return true;
	}

	/**
	 * Devuelve el precio de solicitar una valoración
	 * @return precio de solicitar una valoración
	 */
	public double getPrecioValoracion() {
		return precioValoracion;
	}

	/**
	 * Cambia el precio de solicitar una valoración
	 * @param precioValoracion Nuevo precio de solicitar una valoración
	 * @return true si se cambia correctamente
	 */
	public boolean setPrecioValoracion(double precioValoracion) {
		if (precioValoracion < 0)
			return false;
		this.precioValoracion = precioValoracion;
		return true;
	}

	/**
	 * Devuelve la ponderación de categoría
	 * @return ponderación de categoría
	 */
	public double getPonderacionCategoria() {
		return ponderacionCategoria;
	}

	/**
	 * Devuelve la ponderación de unidades compradas
	 * @return ponderación de unidades
	 */
	public double getPonderacionUdsCompra() {
		return ponderacionUdsCompra;
	}

	/**
	 * Devuelve la ponderación del precio de compra
	 * @return ponderación del precio
	 */
	public double getPonderacionPrecioCompra() {
		return ponderacionPrecioCompra;
	}

	/**
	 * Devuelve la ponderación de búsqueda
	 * @return ponderación de búsqueda
	 */
	public double getPonderacionBusqueda() {
		return ponderacionBusqueda;
	}

	/**
	 * Devuelve el número de productos recomendados
	 * @return número de productos
	 */
	public int getNumProductosRecomendados() {
		return numProdsRecomendados;
	}

	/**
	 * Devuelve la ponderación de valoraciones
	 * @return ponderación de valoraciones
	 */
	public double getPonderacionValoracionesProducto() {
		return ponderacionValoracionesProducto;
	}

	/**
	 * Devuelve la ponderación de productos recomendados
	 * @return ponderación de recomendación
	 */
	public double getPonderacionProductoRecomendado() {
		return ponderacionProductoRecomendado;
	}

	/**
	 * Cambia la ponderación de categoría
	 * @param gestor Gestor que realiza el cambio
	 * @param ponderacionCategoria nueva ponderación
	 * @throws InvalidArgumentException si es negativa
	 */
	public void setPonderacionCategoria(Gestor gestor, double ponderacionCategoria) throws InvalidArgumentException {
		if (ponderacionCategoria < 0)
			throw new InvalidArgumentException("La ponderación tiene que ser positiva",
					"setter de la ponderación de Categoría");
		this.ponderacionCategoria = ponderacionCategoria;
	}

	/**
	 * Cambia la ponderación de unidades compradas
	 * @param gestor Gestor que realiza el cambio
	 * @param ponderacionUdsCompra nueva ponderación
	 * @throws InvalidArgumentException si es negativa
	 */
	public void setPonderacionUdsCompra(Gestor gestor, double ponderacionUdsCompra) throws InvalidArgumentException {
		if (ponderacionUdsCompra < 0)
			throw new InvalidArgumentException("La ponderación tiene que ser positiva",
					"setter de la ponderación de las unidades compradas");
		this.ponderacionUdsCompra = ponderacionUdsCompra;
	}

	/**
	 * Cambia la ponderación del precio de compra
	 * @param gestor Gestor que realiza el cambio
	 * @param ponderacionPrecioCompra nueva ponderación
	 * @throws InvalidArgumentException si es negativa
	 */
	public void setPonderacionPrecioCompra(Gestor gestor, double ponderacionPrecioCompra)
			throws InvalidArgumentException {
		if (ponderacionPrecioCompra < 0)
			throw new InvalidArgumentException("La ponderación tiene que ser positiva",
					"setter de la ponderación del precio de la compra");
		this.ponderacionPrecioCompra = ponderacionPrecioCompra;
	}

	/**
	 * Cambia la ponderación de valoraciones
	 * @param gestor Gestor que realiza el cambio
	 * @param ponderacionValoracionesProducto nueva ponderación
	 * @throws InvalidArgumentException si es negativa
	 */
	public void setPonderacionValoracionesProducto(Gestor gestor, double ponderacionValoracionesProducto)
			throws InvalidArgumentException {
		if (ponderacionValoracionesProducto < 0)
			throw new InvalidArgumentException("La ponderación tiene que ser positiva",
					"setter de la ponderación de las valoraciones del producto");
		this.ponderacionValoracionesProducto = ponderacionValoracionesProducto;
	}

	/**
	 * Cambia la ponderación de recomendación
	 * @param gestor Gestor que realiza el cambio
	 * @param ponderacionProductoRecomendado nueva ponderación
	 * @throws InvalidArgumentException si es negativa
	 */
	public void setPonderacionProductoRecomendado(Gestor gestor, double ponderacionProductoRecomendado)
			throws InvalidArgumentException {
		if (ponderacionProductoRecomendado < 0)
			throw new InvalidArgumentException("La ponderación tiene que ser positiva",
					"setter de la ponderación de la semejanza de producto");
		this.ponderacionProductoRecomendado = ponderacionProductoRecomendado;
	}

	/**
	 * Cambia la ponderación de búsqueda
	 * @param gestor Gestor que realiza el cambio
	 * @param ponderacionBusqueda nueva ponderación
	 * @throws InvalidArgumentException si es negativa
	 */
	public void setPonderacionBusqueda(Gestor gestor, double ponderacionBusqueda) throws InvalidArgumentException {
		if (ponderacionBusqueda < 0)
			throw new InvalidArgumentException("La ponderación tiene que ser positiva",
					"setter de la ponderación de la búsqueda");
		this.ponderacionBusqueda = ponderacionBusqueda;
	}

	/**
	 * Cambia el número de productos recomendados
	 * @param gestor Gestor que realiza el cambio
	 * @param numProdsRecomendados nuevo número
	 * @throws InvalidArgumentException si es negativo
	 */
	public void setNumProdsRecomendados(Gestor gestor, int numProdsRecomendados) throws InvalidArgumentException {
		if (numProdsRecomendados < 0)
			throw new InvalidArgumentException("El número de productos recomendados tiene que ser positivo",
					"setter del número de productos recomendados ");
		this.numProdsRecomendados = numProdsRecomendados;
	}

	/**
	 * Devuelve los parámetros activos
	 * @return conjunto de parámetros
	 */
	public Set<ParametroSistema> getParametros() {
		return parametros;
	}

	/**
	 * Activa o desactiva un parámetro
	 * @param p Parámetro
	 * @param activo true para activar
	 * @return true si se modifica
	 */
	public boolean gestionarParametro(ParametroSistema p, boolean activo) {
		if (activo)
			return this.parametros.add(p);
		else {
			if (parametros.size() > 1) {
				return this.parametros.remove(p);
			} else
				return false;
		}
	}

	/**
	 * Simula un pago con tarjeta
	 * @return true si el pago es correcto
	 */
	public boolean pagoTarjeta() {
		return Math.random() < 1;
	}
}
