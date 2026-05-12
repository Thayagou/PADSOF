package modelo.aplicacion;

import java.util.concurrent.TimeUnit;
import javax.swing.SwingUtilities;

import controladores.noRegistrado.ControlInicioSinRegistrar;
import modelo.exceptions.*;
import modelo.sistema.*;

/**
 * Ejecutable principal de la aplicación
 */
public class GuiExe {
	/** Modelo de la tienda sobre el que e actúa */
	private static Tienda tienda;
	/** Nombre del fichero donde se carga y guarda la tienda */
	private static String filename = "tienda.dat";

	/**
	 * Constructor privado para no inicializar la clase
	 */
	private GuiExe() {}
	
	/**
	 * Método ejecutable
	 * @param args ARgumentos de entrada
	 */
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
	
	/**
	 * Guarda el estado de la tienda al cerrar la ventana
	 */
	public static void guardarTienda() {
		tienda.guardarTienda(filename);
	}
}
