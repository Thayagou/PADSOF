package estadistica;

import usuario.ClienteRegistrado;
import venta.productos.*;

import java.io.Serializable;
import java.util.*;

import sistema.Sistema;

/**
 * Clase StatsUsuario que nos permite almacenar la infromación referente a los usuarios
 */
public class StatsUsuario implements Serializable {
	private static final long serialVersionUID = 1L;
	ClienteRegistrado cliente;
	double totalGastado = 0;
	long udsCompradas = 0;
	long udsIntercambiadas = 0;
	Map<Categoria, Double> intereses = new HashMap<>();
	double norma = 0;
	
	public StatsUsuario(ClienteRegistrado cliente) {
		this.cliente = cliente;
	}
	
	public boolean actualizarUltimaVenta(int udsCompradas, double precio) {
		if (udsCompradas < 0 || precio < 0) return false;
		
		totalGastado += precio;
		this.udsCompradas += udsCompradas;
		
		return true;
	}
	
	public boolean actualizarUltimoIntercambio(int udsIntercambiadas) {
		if (udsIntercambiadas < 0) return false;
		
		this.udsIntercambiadas+= udsIntercambiadas; 
		
		return true;
	}
	
	public boolean actualizarUltimaValoracion(double precioValoracion) {
		if (precioValoracion < 0) return false;
		
		totalGastado += precioValoracion;
		
		return true;
	}
	
	public double getGastoTotal() {
		return totalGastado;
	}
	
	public void actualizarBusqueda(Categoria...categorias) {
		double peso = Sistema.getInstancia().getPonderacionBusqueda();
		for (Categoria c: categorias) {
			intereses.merge(c, peso, (a,b)->a+b);
		}
		
		norma = Math.sqrt(intereses.values().stream().mapToDouble(a->a*a).sum());
	}
	
	public void actualizarCompra(Map<Categoria, Double> vector) {
		for (Categoria c : vector.keySet()) {			
			intereses.merge(c, vector.get(c), (a,b)->a+b);
		}
		
		norma = Math.sqrt(intereses.values().stream().mapToDouble(a->a*a).sum());
	}
	
	public Map<Categoria, Double> getVectorIntereses() {
		return Collections.unmodifiableMap(intereses);
	}
	
	public double getNorma() {
		return norma;
	}
	
	public double getCompatibilidad(Map<Categoria, Double> v1, double n1) {
		double prodEscalar = 0; 

		for (Categoria c: intereses.keySet()) {
			if (v1.containsKey(c) == false) continue;
			
			prodEscalar += intereses.get(c)*v1.get(c);
		}
		
		prodEscalar /= (n1*norma);
		
		return prodEscalar;
	}

}
