package controladores.empleado.gestionarProductos.anadirProductos;

import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import modelo.sistema.Tienda;
import modelo.usuario.Usuario;
import modelo.venta.productos.Categoria;
import modelo.venta.productos.caracteristicas.CaracteristicasComic;
import modelo.venta.productos.caracteristicas.CaracteristicasFigura;
import modelo.venta.productos.caracteristicas.CaracteristicasJuego;
import vistas.common.TiendaFrame;
import vistas.empleado.gestionarProductos.anadirProductos.VentanaAnadirProductos;

public class ControlAnadirProductos implements ControladorPantalla {
	private VentanaAnadirProductos vista;
	
	public ControlAnadirProductos(Tienda tienda, Usuario usuario) {
		List<String> categorias = new LinkedList<>();
		for(Categoria c : tienda.getAlmacen().getCategorias()) {
			categorias.add(c.getNombre());
		}
		
		String[] tiposProductos = { "Comic", "Juego", "Figura" };
		String[] espComic = new CaracteristicasComic(null, null, 0, null).getNombresCaracteristicas();
		String[] espJuego = new CaracteristicasJuego(0, null, null).getNombresCaracteristicas();
		String[] espFigura = new CaracteristicasFigura(null, null, null).getNombresCaracteristicas();
		
		this.vista = new VentanaAnadirProductos(categorias.toArray(new String[0]), tiposProductos, espComic, espJuego, espFigura);
		
		new ControlPanelCargarFichero(tienda, usuario, vista);
		vista.setControlador(this);
		
		TiendaFrame.getInstance().navegarA(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

	@Override
	public JPanel getVista() {
		return vista;
	}
}
