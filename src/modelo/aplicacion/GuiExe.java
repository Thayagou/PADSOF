package modelo.aplicacion;

import java.util.concurrent.TimeUnit;

import javax.swing.SwingUtilities;

import controladores.ControlInicioSinRegistrar;
import modelo.exceptions.*;
import modelo.sistema.*;

public class GuiExe {
	protected static Tienda tienda;
	private static String filename = "tienda.dat";

	public static void main(String[] args) {
		try {
			tienda = new Tienda();
		} catch (InvalidArgumentException e) {
			e.printStackTrace();
		}

		Tienda carga = Tienda.cargarTienda(filename);
		if (carga != null)
			tienda = carga;
		GestorCaducidad.getInstancia().iniciar(1, TimeUnit.MINUTES);
		
		SwingUtilities.invokeLater(() -> {
		    new ControlInicioSinRegistrar(tienda);
		});
	}
}
