package estadistica;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import usuario.ClienteRegistrado;

public class StatsUsuario {
	ClienteRegistrado cliente;
	List<StatsMensualCliente> estadisticas = new ArrayList<>();
	
	public StatsUsuario(ClienteRegistrado cliente) {
		this.cliente = cliente;
	}
	
	public boolean actualizarUltimaVenta(int udsCompradas, double precio) {
		if (udsCompradas < 0 || precio < 0) return false;
		
		if (estadisticas.getLast().getMes().equals(YearMonth.now()) == false) {
			estadisticas.add(new StatsMensualCliente());
		}
		
		estadisticas.getLast().incrementar(udsCompradas, precio);
		
		return true;
	}
	
	public boolean actualizarUltimoIntercambio(int uds) {
		if (uds < 0) return false;
		
		if (estadisticas.getLast().getMes().equals(YearMonth.now()) == false) 
			estadisticas.add(new StatsMensualCliente());
		
		return estadisticas.getLast().incrementarIntercambio(uds);
	}
	
	public boolean actualizarUltimaValoracion(double precio) {
		if (precio < 0) return false;
		
		if (estadisticas.getLast().getMes().equals(YearMonth.now()) == false) 
			estadisticas.add(new StatsMensualCliente());
		
		return estadisticas.getLast().incrementarValoracion(precio);
	}
	
	

}
