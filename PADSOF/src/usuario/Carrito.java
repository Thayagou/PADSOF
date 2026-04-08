/**
 * Este paquete recoje las subclases de usuario y otras clases relacionadas
 */
package usuario;

import java.util.*;
import java.io.Serializable;
import java.time.*;
import venta.descuentos.*;
import venta.productos.*;
import exceptions.*;
import sistema.Reloj;
import sistema.*;

/**
 * Clase que representa un carrito de compra de un cliente
 */
public class Carrito implements Serializable, Caducable {
	private static final long serialVersionUID = 1L;
	/**Fecha de caducidad del carrito*/
	private LocalDateTime fechaCaducidad;
	/**Mapa de items que están en el carrito*/
	private Map<Producto, StockExterno> items = new HashMap<Producto, StockExterno>();
	/**Mapa de regalos que se han añadido al carrito*/
	private Map<Producto, StockExterno> regalos = new HashMap<Producto, StockExterno>();
	/**Cliente propietario del carrito*/
	private CarritoCaducadoObserver cliente;
	/**Tienda en la que se encuentra el carrito*/
	private CarritoCaducadoObserver tienda;
	
	/**
	 * Creador de la clase carrito
	 * @param cliente Cliente al que pertenece el carrito
	 * @param tienda CarritoCaducadoObserver que es la tienda
	 */
	public Carrito(CarritoCaducadoObserver cliente, CarritoCaducadoObserver tienda) {
		this.cliente = cliente;
		this.tienda = tienda;
		fechaCaducidad = LocalDateTime.MAX;
		/*
		calcularFechaCaducidad();
		GestorCaducidad.getInstancia().registrar(this);
		*/
	}
	
	/**
	 * Método para obtener la lista de items no regalo del carrito
	 * @return Array con la lista de stocks del carrito
	 */
	public StockExterno[] getItems() {
		return items.values().toArray(new StockExterno[0]);
	}
	
	/**
	 * Método para obtener la lista de regalos del carrito
	 * @return Array con los regalos del carrito
	 */
	public StockExterno[] getRegalos() {
		return regalos.values().toArray(new StockExterno[0]);
	}
	
	/**
	 * Método para obtener los items y regalos del carrito en un solo array
	 * @return Array con todo el contenido del carrito
	 */
	public StockExterno[] getContenido() {
		StockExterno[] items = getItems();
		StockExterno[] regalos = getRegalos();
		StockExterno[] merge = new StockExterno[items.length + regalos.length];
		
		for(int i=0; i<items.length; i++) {
			merge[i] = items[i];
		}
		for(int i=0; i<regalos.length; i++) {
			merge[i+items.length] = regalos[i];
		}
		return merge;
	}
	
	@Override
	public LocalDateTime getFechaCaducidad() {
		return fechaCaducidad;
	}

	@Override
	public void caducar() {
		tienda.carritoCaducado(this);
		cliente.carritoCaducado(this);
		vaciarCarrito();
	}
	
	/**
	 * Metodo para calcular la fecha en que caducará el carrito
	 */
	public void calcularFechaCaducidad() {
		GestorCaducidad.getInstancia().desregistrar(this);
		fechaCaducidad = Reloj.now().plus(Sistema.getInstancia().getTiempoCaducaCarrito());
		GestorCaducidad.getInstancia().registrar(this);
	}

	/**
	 * Metodo para vaciar el carrito de la compra
	 */
	public void vaciarCarrito() {
		items.clear();
		regalos.clear();
		GestorCaducidad.getInstancia().desregistrar(this);
	}
	
	/**
	 * Metodo para añadir un regalo a la lista de regalos
	 * @param p Producto que se quiere añadir como regalo.
	 * @throws InvalidArgumentException si se pasa un producto null
	 */
	private void anadirRegalo(Producto p) throws InvalidArgumentException {
		if(p == null) throw new InvalidArgumentException("No se puede añadir un regalo null");
		
		if(!regalos.containsKey(p)) {
			regalos.put(p,  new StockExterno(p, 1, 0));
		} else {
			regalos.get(p).incrementarStock();
		}
		calcularFechaCaducidad();
	}
	
	/**
	 * Metodo para añadir un producto al carrito
	 * @param p Producto que se quiere añadir al carrito
	 * @throws InvalidArgumentException si se pasa un producto null
	 */
	public void anadirProducto(Producto p) throws InvalidArgumentException {
		if(p == null) throw new InvalidArgumentException("No se puede añadir un producto null");
		
		if(!items.containsKey(p)) {
			items.put(p,  new StockExterno(p, 1));
		} else {
			items.get(p).incrementarStock();
		}
		calcularFechaCaducidad();
	}
	
	/**
	 * Metodo para quitar un producto del carrito
	 * @param p Producto del cual se quiere quitar una unidad del carrito.
	 */
	public void quitarProducto(Producto p) {
		if(items.containsKey(p)) {
			items.get(p).reducirStock();
			if(!items.get(p).disponible()) {
				items.remove(p);
			}
			if(items.isEmpty()) {
				vaciarCarrito();
			}
		}
	}
	
	/**
	 * Método para calcular el carrito de la compra y todos sus datos.
	 * @return Precio total del carrito.
	 */
	public double calcularCarrito() {
		double pTotal = 0;
		
		calcularDescuentos();
		for(StockExterno s : items.values()) {
			pTotal += s.getPrecioTotal();
		}
		return pTotal;
	}
	
	/**
	 * Calcula el precio base del carrito, es decir la suma de precio de los productos sin descuento.
	 * @return Precio base del carrito
	 */
	private double calcularPrecioBase() {
		double suma = 0;
		for(StockExterno s: items.values()) {
			suma += s.getProducto().getPrecio()*s.getUdsEnStock();
		}
		return suma;
	}
	
	/**
	 * Hace todas las operaciones que requieran los descuentos del carrito.
	 * Calcula todos los precios con descuento, guardándolos en el precio final de StockExterno para
	 * poder después almacenar el precio correcto en las estadísticas.
	 * También añade los regalos necesarios al carrito.
	 */
	private void calcularDescuentos() {
		try {
			regalos.clear();
			double pBase = calcularPrecioBase();
			
			/*Primero inicializamos todos los precios finales a los precios del producto*/
			for(StockExterno s : items.values()) {
				s.setPrecioUnitarioFinal(s.getProducto().getPrecio());
			}
			
			/*Creamos un HashMap con los productos por descuento*/
			HashMap<Descuento, ArrayList<StockExterno>> productosPorDescuento = new HashMap<Descuento, ArrayList<StockExterno>>();
			for(StockExterno s : items.values()) {
				if(s.getProducto().tieneDescuento()) {
					productosPorDescuento.computeIfAbsent(s.getProducto().getDescuento(), k -> new ArrayList<>()).add(s);
				}
			}
			
			/*Calculamos los precios descontados y los regalos que añadir*/
			for(Map.Entry<Descuento, ArrayList<StockExterno>> entry : productosPorDescuento.entrySet()) {
				Descuento d = entry.getKey();
				ArrayList<StockExterno> productos = entry.getValue();
				int numUds = 0;
				
				/*Tomamos el total de unidades de los productos con ese descuento*/
				for(StockExterno s : productos) {
					numUds += s.getUdsEnStock();
				}
				
				Producto regalo = d.getRegalo(numUds, pBase);
				
				if(regalo != null) {
					anadirRegalo(regalo);
				}
				
				/*Asignamos el precio final con descuento a cada Stock*/
				for(StockExterno s : productos) {
					double precio = s.getProducto().getPrecio();
					s.setPrecioUnitarioFinal(d.getPrecioDescontado(numUds, pBase, precio));
				}
			}	
		} catch(InvalidArgumentException e) {
			throw new RuntimeException("Objeto con estado inválido encontrado al calcular los descuentos", e);
		}
	}
	
	/**
	 * Método para imprimir un carrito
	 */
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("Carrito:\n");
		s.append("\n Precio total: "+calcularCarrito());
		s.append("\n Items del carrito:\n");
		for(StockExterno st : items.values()) {
			s.append("  " + st.toString() + "\n");
		}
		s.append("\n Regalos:\n");
		for(StockExterno st : regalos.values()) {
			s.append("  " + st.toString() + "\n");
		}
		
		return s.toString();
	}
}
