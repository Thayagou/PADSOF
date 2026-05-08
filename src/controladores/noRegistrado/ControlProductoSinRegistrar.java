package controladores.noRegistrado;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controladores.ControladorPantalla;
import modelo.sistema.Tienda;
import modelo.venta.productos.*;
import vistas.common.app.TiendaFrame;
import vistas.noRegistrado.VentanaProductoSinRegistrar;

public class ControlProductoSinRegistrar implements ActionListener, ControladorPantalla {

	private Tienda tienda;
	private VentanaProductoSinRegistrar vista;
	
	private static final String DF_PRODUCT_IMAGE = "producto.png";

	public ControlProductoSinRegistrar(Tienda tienda, Producto producto) {
		this.tienda = tienda;
		ArrayList<String> categorias = new ArrayList<>();
		for(Categoria c : producto.getCategorias()) {
			categorias.add(c.getNombre());
		}
		
		String imageRoute;
		if(producto.getImagen() == null || producto.getImagen().isBlank()) imageRoute = DF_PRODUCT_IMAGE;
		else imageRoute = producto.getImagen();
		
		this.vista = new VentanaProductoSinRegistrar(producto.getNombre(), 
				producto.getDescripcion(), imageRoute, producto.getPuntuacionMedia(), 
				producto.getPrecio(), producto.getCaracteristicas(), categorias.toArray(new String[0]));
		for(Resena r : producto.getResenas()) {
			vista.anadirPanelResena(r.getPuntuacion(), r.getComentario(), r.getUsuario().getNombre());
		}
		
		TiendaFrame.getInstance().navegarA(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// De momento la vista de detalle de producto no tiene botones con acción.
		// Cuando se añadan (p.ej. "Volver", "Iniciar sesión para comprar"), se
		// gestionan aquí:
		switch (e.getActionCommand()) {
		case "Volver" -> this.volver();
		case "Iniciar sesión" -> SwingUtilities.invokeLater(() -> new ControlLoginRegistro(tienda));
		}
	}

	private void volver() {
		// Vuelve a la pantalla de inicio sin registrar
		SwingUtilities.invokeLater(() -> new ControlInicioSinRegistrar(tienda));
	}

	@Override
	public JPanel getVista() {
		return vista;
	}
}
