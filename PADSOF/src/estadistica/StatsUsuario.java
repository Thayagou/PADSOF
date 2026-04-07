package estadistica;

import usuario.ClienteRegistrado;
import venta.productos.*;

import java.io.Serializable;
import java.util.*;

import exceptions.InvalidArgumentException;
import sistema.Sistema;

/**
 * Clase StatsUsuario que nos permite almacenar la infromación referente a los usuarios
 */
public class StatsUsuario implements Serializable {
	private static final long serialVersionUID = 1L;
	/** Cliente del cual se almacenan las estadísticas */
	ClienteRegistrado cliente;
	/** Total gastado por el usuario en la tienda */
	double totalGastado = 0;
	/** Unidades totales de productos comprados por el cliente */
	long udsCompradas = 0;
	/** Unidades totales de artículos intercambiados por el cliente */
	long udsIntercambiadas = 0;
	/** Vector de intereses del usuario en formato de Map Categoria->valor double asignado */
	Map<Categoria, Double> intereses = new HashMap<>();
	/** Norma del vector de intereses */
	double norma = 0;
	
	/**
	 * Contructor de la clase ClienteRegistrado
	 * @param cliente Cliente a almacenar
	 */
	public StatsUsuario(ClienteRegistrado cliente) {
		this.cliente = cliente;
		cliente.setEstadisticas(this);
	}
	
	/**
	 * A partir de un número de unidades intercambiadas incrementa el contador interno de los intercambios totales
	 * @param udsIntercambiadas Número de artículos que se han intercambiado
	 * @throws InvalidArgumentException  En caso de que el número de unidades sea negativo se lanza esta excepción
	 */
	public void actualizarUltimoIntercambio(int udsIntercambiadas) throws InvalidArgumentException {
		if (udsIntercambiadas < 0) throw new InvalidArgumentException("El número de unidades intercambiadas no puede ser negativo", "actualizar el último intercambio del cliente");
		
		this.udsIntercambiadas+= udsIntercambiadas; 
	}
	
	/**
	 * A partir de un gasto de valoración incrementa el contador interno del gasto total del usuario
	 * @param precioValoracion Precio pagado por la valoración de un artícul
	 * @throws InvalidArgumentException  En caso de que el número de unidades sea negativo se lanza esta excepción
	 */
	public void actualizarUltimaValoracion(double precioValoracion) throws InvalidArgumentException {
		if (precioValoracion < 0) throw new InvalidArgumentException("El número de unidades intercambiadas no puede ser negativo", "actualizar el último intercambio del cliente");
		
		
		totalGastado += precioValoracion;
	}
	
	/**
	 * A partir de una serie de categorías buscadas se actualiza el interés de un usuario
	 * @param categorias VarArgs de categorías buscadas
	 * @throws InvalidArgumentException Se lanza esta excepción en caso de que alguna de las categorías sea null
	 */
	public void actualizarVectorInteresesBusqueda(Categoria...categorias) throws InvalidArgumentException {
		for (Categoria c: categorias) {
			if (c == null) throw new InvalidArgumentException("Categoría inválida introducida", "actualizar vector de cliente a partir de búsqueda");
		}
		double peso = Sistema.getInstancia().getPonderacionBusqueda();
		for (Categoria c: categorias) {
			intereses.merge(c, peso, (a,b)->a+b);
		}
		
		norma = Math.sqrt(intereses.values().stream().mapToDouble(a->a*a).sum());
	}
	
	
	/**
	 * Tras realizar un cliente una compra actualiza el vector de intereses del cliente, las unidades de productos compradas y su gasto total en la tienda
	 * @param vector Vector resultante de la compra, obtenido a partir de una media teniendo en cuenta las ponderaciones de gastos y unidades
	 * @param udsCompradas Unidades totales compradas por el cliente
	 * @param precio Precio total de la compra y gasto del cliente
	 * @throws InvalidArgumentException En caso de que haya algún argumento inválido se lanza
	 */
	public void actualizarCompra(Map<Categoria, Double> vector, int udsCompradas, double precio) throws InvalidArgumentException {
		if (vector == null | udsCompradas < 0 || precio < 0) throw new InvalidArgumentException("Argumentos inválidos para la actualización", "actualizar estadísticas del cliente a partir de una compra");
		
		totalGastado += precio;
		this.udsCompradas += udsCompradas;
		
		for (Categoria c : vector.keySet()) {			
			intereses.merge(c, vector.get(c), (a,b)->a+b);
		}
		
		System.out.println("inteseses: "+intereses);
		System.out.println(this);
		
		norma = Math.sqrt(intereses.values().stream().mapToDouble(a->a*a).sum());
	}
	
	/**
	 * Realiza un producto escalar normalizado entre el vector del cliente y uno introduciddo, de manera que el módulo resultante reside entre 0 y 1
	 * @param vectorExt Vector de recomendación/intereses introducido
	 * @param normaExt Norma del vector introducido
	 * @return Resultado del producto escalar realizado
	 */
	public double getCompatibilidad(Map<Categoria, Double> vectorExt, double normaExt) {
		double prodEscalar = 0; 

		for (Categoria c: intereses.keySet()) {
			if (vectorExt.containsKey(c) == false) continue;
			
			prodEscalar += intereses.get(c)*vectorExt.get(c);
		}
		
		prodEscalar /= (normaExt*norma);
		
		return prodEscalar;
	}
	
	/**
	 * Obtiene el vector de intereses del cliente asociado en forma de Map
	 * @return el vector asociado
	 */
	public Map<Categoria, Double> getVectorIntereses() {
		return Collections.unmodifiableMap(intereses);
	}
	
	/**
	 * Getter de la norma del vector del cliente
	 * @return La norma del vector
	 */
	public double getNorma() {
		return norma;
	}
	
	/**
	 * Getter del gasto total del usuario
	 * @return Total gastado por el usuario
	 */
	public double getGastoTotal() {
		return totalGastado;
	}
	
	/**
	 * Getter del número de productos comprados
	 * @return long con dicho número
	 */
	public long getUdsCompradas() {
		return udsCompradas;
	}

	/**
	 * Getter del número de artículos intercambiados
	 * @return long con dicho número
	 */
	public long getUdsIntercambiadas() {
		return udsIntercambiadas;
	}

	/**
	 * Getter del cliente al que hace referencia estas estadísticas
	 * @return Referencia al cliente
	 */
	public ClienteRegistrado getCliente() {
		return cliente;
	}
	
	@Override
	public String toString() {
		return "Estadística de "+cliente.getNombre() +
				"\n  Total gastado: "+totalGastado+ "\n  Unidades compradas: "+ udsCompradas+ 
				"\n  Unidades intercambiadas: "+ udsIntercambiadas + "\n  Vector recomendación: "+ intereses; 
	}

}
