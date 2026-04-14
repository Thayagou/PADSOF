package aplicacion;

import java.util.concurrent.TimeUnit;

import exceptions.*;
import sistema.*;
import gui.*;

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
		
		javax.swing.SwingUtilities.invokeLater(() -> {
	        new VentanaInicioSinRegistrar(tienda);
	    });
		
	}
}
