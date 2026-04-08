package sistema;

import java.io.IOException;
import java.io.ObjectInputStream;
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
	/** Sistema es una clase Singleton por lo que tiene una instancia Static */
	private static Sistema instancia;
	/** Tiempo que tarda en caducarse un carrito desde que se le añade un último artículo */
	private Duration tiempoCaducaCarrito;
	/** Tiempo que tarda en caducarse una oferta de intercambio desde que esta es realizada */
	private Duration tiempoCaducaOferta;
	/** Precio que debe pagar un cliente por la solicitud de una valoración */
	private double precioValoracion;
	/** Ponderación que tiene dentro del vector de producto el hecho de pertenecer a una categoría */
	private double ponderacionCategoria = 1;
	/** Ponderación que tienen las unidades de productos comprados. Se utiliza a la hora de actualizar el vector de recomendación del cliente tras una compra */
	private double ponderacionUdsCompra = 1;
	/** Ponderación que tiene el precio pagado por un producto. Se utiliza a la hora de actualizar el vector de recomendación del cliente tras una compra */
	private double ponderacionPrecioCompra = 1;
	/** Ponderación que tiene la media de puntuación de un producto a la hora de calcular su valor de compatibilidad con un determinado usuario */
	private double ponderacionValoracionesProducto = 1;
	/** Ponderación que tiene la compatibilidad entre usuario y producto en el rango [0,1] a la hora de calcular su valor de compatibilidad con un determinado usuario */
	private double ponderacionProductoRecomendado = 1; 
	/** Ponderación que tiene la búsqueda por categorías sobre el vector de intereses del usuaro */
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
	 * Restaura la instancia singleton de Sistema desde el stream
	 * @param ois Stream de entrada con la instancia serializada
	 * @throws ClassNotFoundException si la clase no se encuentra
	 * @throws IOException si ocurre un error de lectura
	 */
	public static void asignarInstancia(ObjectInputStream ois) throws ClassNotFoundException, IOException {
		Sistema.instancia = (Sistema) ois.readObject();
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
	 */
	public void setPrecioValoracion(Gestor gestor, double precioValoracion) throws InvalidArgumentException {
		if(gestor == null || precioValoracion < 0) throw new InvalidArgumentException("Argumentos inválidos", "setPrecioValoracion");
		this.precioValoracion = precioValoracion;
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
