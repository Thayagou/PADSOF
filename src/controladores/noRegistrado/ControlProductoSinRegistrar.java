package controladores.noRegistrado;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.SwingUtilities;

import modelo.sistema.Tienda;
import modelo.venta.productos.*;
import vistas.common.*;
import vistas.noRegistrado.VentanaProductoSinRegistrar;

public class ControlProductoSinRegistrar implements ActionListener {

	private Tienda tienda;
	private VentanaProductoSinRegistrar vista;

	public ControlProductoSinRegistrar(Tienda tienda, Producto producto) {
		this.tienda = tienda;
		ArrayList<String> categorias = new ArrayList<>();
		for(Categoria c : producto.getCategorias()) {
			categorias.add(c.getNombre());
		}
		this.vista = new VentanaProductoSinRegistrar(producto.getNombre(), 
				producto.getDescripcion(), producto.getPuntuacionMedia(), 
				producto.getPrecio(), categorias.toArray(new String[0]));
		for(Resena r : producto.getResenas()) {
			vista.anadirPanelResena(r.getPuntuacion(), r.getComentario(), r.getUsuario().getNombre());
		}
		
		TiendaFrame.getInstance().setVistaActual(vista);
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
}
