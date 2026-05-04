package controladores.empleado.gestionarProductos.gestionarCategorias;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import modelo.exceptions.InvalidArgumentException;
import modelo.exceptions.InvalidPermitException;
import modelo.sistema.Tienda;
import modelo.usuario.Usuario;
import modelo.venta.productos.Categoria;
import vistas.common.PanelCategoria;
import vistas.common.VentanaConDisplay;
import vistas.common.VentanaMensaje;
import vistas.empleado.gestionarProductos.gestionarCategorias.PanelCategoriaGestionarCategoria;

public class ControlPanelCategoriaGestionar implements ActionListener {
	private final Categoria categoria;
	private final Usuario usuario;
	private final Tienda tienda;
	private PanelCategoriaGestionarCategoria panel;
	
	public ControlPanelCategoriaGestionar(Tienda tienda, Usuario usuario, Categoria categoria, VentanaConDisplay<? super PanelCategoria> vista) {
		this.tienda = tienda;
		this.usuario = usuario;
		this.categoria = categoria;
		
		panel = new PanelCategoriaGestionarCategoria(categoria.getNombre());
		panel.setControlador(this);
		
		vista.anadirDisplay(panel);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case PanelCategoriaGestionarCategoria.BORRAR_ACTION:
			intentarBorrar();
			break;
		case PanelCategoriaGestionarCategoria.CONFIRMAR_ACTION:
			intentarModificar();
			break;
		}
	}
	
	private void intentarBorrar() {
		try {
			tienda.getAlmacen().eliminarCategoria(usuario, categoria);
		} catch (InvalidArgumentException | InvalidPermitException e) {
			new VentanaMensaje(e.getMessage());
		}
		SwingUtilities.invokeLater(() -> new ControlGestionarCategorias(tienda, usuario));
		new VentanaMensaje("La categoría se ha borrado correctamente");
	}

	private void intentarModificar() {
		String nuevoNombre = panel.getNombreCategoria();
		if(nuevoNombre.equals("Nombre") || nuevoNombre.length() < 1) {
			new VentanaMensaje("Introduzca un nuevo nombre para la categoría");
			return;
		}
		
		try {
			tienda.getAlmacen().modificarCategoria(usuario, categoria, nuevoNombre);
		} catch (InvalidArgumentException | InvalidPermitException e) {
			new VentanaMensaje(e.getMessage());
		}
		SwingUtilities.invokeLater(() -> new ControlGestionarCategorias(tienda, usuario));
		new VentanaMensaje("La categoría se ha modificado correctamente");
	}
	
}
