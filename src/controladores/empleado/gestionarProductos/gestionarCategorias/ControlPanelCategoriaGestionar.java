package controladores.empleado.gestionarProductos.gestionarCategorias;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.exceptions.InvalidArgumentException;
import modelo.exceptions.InvalidPermitException;
import modelo.sistema.Tienda;
import modelo.usuario.Usuario;
import modelo.venta.productos.Categoria;
import vistas.common.app.TiendaFrame;
import vistas.common.assets.VentanaMensaje;
import vistas.common.displays.PanelCategoria;
import vistas.common.displays.VentanaConDisplay;
import vistas.empleado.gestionarProductos.gestionarCategorias.PanelCategoriaGestionarCategoria;

public class ControlPanelCategoriaGestionar implements ActionListener {
	private final Categoria categoria;
	private final Usuario usuario;
	private final Tienda tienda;
	private PanelCategoriaGestionarCategoria panel;
	private final ControlGestionarCategorias padre;
	
	public ControlPanelCategoriaGestionar(Tienda tienda, Usuario usuario, Categoria categoria, VentanaConDisplay<? super PanelCategoria> vista, ControlGestionarCategorias padre) {
		this.tienda = tienda;
		this.usuario = usuario;
		this.categoria = categoria;
		this.padre = padre;
		
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
		if(TiendaFrame.getConfirmacionUsuario("¿Estás seguro de que deseas borrar esta categoría?")) {
			try {
				tienda.getAlmacen().eliminarCategoria(usuario, categoria);
			} catch (InvalidArgumentException | InvalidPermitException e) {
				new VentanaMensaje(e.getMessage(), 1);
				return;
			}
			padre.mostrar();
			new VentanaMensaje("La categoría se ha borrado correctamente");
		}
	}

	private void intentarModificar() {
		String nuevoNombre = panel.getNombreCategoria();
		if(nuevoNombre.equals("Nombre") || nuevoNombre.length() < 1) {
			new VentanaMensaje("Introduza un nuevo nombre válido para la categoría", 1);
			return;
		}
		
		if(TiendaFrame.getConfirmacionUsuario("¿Estás seguro de que deseas modificar esta categoría?")) {
			try {
				tienda.getAlmacen().modificarCategoria(usuario, categoria, nuevoNombre);
			} catch (InvalidArgumentException | InvalidPermitException e) {
				new VentanaMensaje(e.getMessage(), 1);
				return;
			}
			padre.mostrar();
			new VentanaMensaje("La categoría se ha modificado correctamente");
		}
	}
	
}
