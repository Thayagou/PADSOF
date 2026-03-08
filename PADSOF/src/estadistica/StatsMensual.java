package estadistica;

import java.time.*;

public class StatsMensual {
	private YearMonth mes;
	private int unidades = 0;
	private double recaudacion = 0;
	
	public StatsMensual() {
		this.mes = YearMonth.now();
	}
	
	public boolean incrementar(int uds, double precio) {
		unidades += uds;
		recaudacion += precio;
		
		return true;
	}
	
	
	
	@Override
	public String toString() {
		return "Estadisticas mensual del " + mes + 
				"Unidades: " + unidades + 
				"Recaudacion=" + recaudacion;
	}

	public YearMonth getMes() {
		return mes;
	}
	public int getUnidades() {
		return unidades;
	}
	public double getRecaudacion() {
		return recaudacion;
	}
	
	
}
