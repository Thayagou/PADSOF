package estadistica;

import usuario.ClienteRegistrado;
import venta.productos.*;
import java.util.*;

import sistema.Sistema;

/**
 * Clase StatsUsuario que nos permite almacenar la infromación referente a los usuarios
 */
public class StatsUsuario {
	ClienteRegistrado cliente;
	double totalGastado = 0;
	long udsCompradas = 0;
	long udsIntercambiadas = 0;
	Map<Categoria, Double> intereses = new HashMap<>();
	
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
			intereses.putIfAbsent(c, 0.0);
		
			intereses.put(c, intereses.get(c) + peso);
		}
	}
	
	public void actualizarCompra(Map<Categoria, Double> vector) {
		//double peso = Sistema.getInstancia().getPonderacionCompra();
		double peso = 0;
		for (Categoria c : vector.keySet()) {			
			intereses.merge(c, vector.get(c) * peso, (a,b)->a+b);
		}
	}
	
	public Map<Categoria, Double> getVectorIntereses() {
		return Collections.unmodifiableMap(intereses);
	}

}
