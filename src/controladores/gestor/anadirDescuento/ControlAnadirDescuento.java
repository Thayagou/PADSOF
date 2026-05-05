package controladores.gestor.anadirDescuento;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import controladores.gestor.ControlInicioGestor;
import modelo.sistema.Tienda;
import modelo.usuario.Gestor;
import modelo.venta.productos.Categoria;
import modelo.venta.productos.Producto;
import vistas.common.PanelMultiopcion;
import vistas.common.TiendaFrame;
import vistas.gestor.anadirDescuento.VentanaAnadirDescuento;

public class ControlAnadirDescuento implements ControladorPantalla{
	private Tienda tienda;
	private Gestor gestor;
	private VentanaAnadirDescuento vista;
	private String tipoActual;
	private List<Producto> productosDescontados = new ArrayList<>();
	private List<Producto> categoriasDescontados = new ArrayList<>();;
	
	
	public ControlAnadirDescuento(Tienda tienda, Gestor gestor) {
		this.tienda = tienda;
		vista = new VentanaAnadirDescuento();
		tipoActual = vista.getOpcionSeleccionada();
		vista.setControlador(this);
		
		if (tipoActual.equals(VentanaAnadirDescuento.TIPO_CATEGORIA)) anadirCategorias();
		else if (tipoActual.equals(VentanaAnadirDescuento.TIPO_PRODUCTO)) anadirProductos();
		
		TiendaFrame.getInstance().navegarA(this);
		
    }
	
	public void anadirProductos() {
		Producto[] catalogo = tienda.getAlmacen().getProductosCoincidentes("");
	
		vista.vaciarDescontados();
		
		for (Producto p: catalogo) {
			new ControlPanelProductoSeleccion(tienda, p, vista);
		}
	}
	
	public void anadirCategorias() {
		Categoria[] categorias = tienda.getAlmacen().getCategorias();
	
		vista.vaciarDescontados();
		
		for (Categoria c: categorias) {
			new ControlPanelCategoriaSeleccion(tienda, c, vista);
		}
		
		vista.revalidate();
		vista.repaint();
	}
	
	private void cambiarTipoDescontado() {
		String tipoNuevo = vista.getOpcionSeleccionada();
		if (tipoNuevo.equals(tipoActual)) return;
		
		tipoActual = tipoNuevo;
		
		if (tipoActual.equals(VentanaAnadirDescuento.TIPO_CATEGORIA)) anadirCategorias();
		else if (tipoActual.equals(VentanaAnadirDescuento.TIPO_PRODUCTO)) anadirProductos();
	}
	
	private void computarDescuento() {

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case PanelMultiopcion.CAMBIO_OPCION_ACTION:
			cambiarTipoDescontado();
			break;
		case VentanaAnadirDescuento.CANCELAR_ACTION:
			new ControlInicioGestor(tienda, gestor);
			break;
		case VentanaAnadirDescuento.CONFIRMAR_ACTION:
			computarDescuento();
			break;
		}
		
		
	}

	@Override
	public JPanel getVista() {
		return vista;
	}

}
