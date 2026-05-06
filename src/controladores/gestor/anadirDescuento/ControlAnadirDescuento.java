package controladores.gestor.anadirDescuento;

import java.awt.event.*;

import javax.swing.JPanel;

import modelo.venta.descuentos.Descontable;
import controladores.ControladorPantalla;
import controladores.gestor.ControlInicioGestor;
import modelo.sistema.Tienda;
import modelo.usuario.Gestor;
import modelo.venta.productos.Categoria;
import modelo.venta.productos.Producto;
import vistas.common.PanelMultiopcion;
import vistas.common.PanelSeleccion;
import vistas.common.TiendaFrame;
import vistas.gestor.anadirDescuento.VentanaAnadirDescuento;

public class ControlAnadirDescuento implements ControlGestionSeleccion<Descontable>, ControladorPantalla{
	private Tienda tienda;
	private Gestor gestor;
	private VentanaAnadirDescuento vista;
	private String tipoActual;
	private Descontable descontado;
	private PanelSeleccion panelDescontado;
	private Producto regalo;
	
	
	
	public ControlAnadirDescuento(Tienda tienda, Gestor gestor) {
		this.tienda = tienda;
		vista = new VentanaAnadirDescuento();
		tipoActual = vista.getOpcionSeleccionada();
		vista.setControlador(this);
		
		if (tipoActual.equals(VentanaAnadirDescuento.TIPO_CATEGORIA)) anadirCategorias();
		else if (tipoActual.equals(VentanaAnadirDescuento.TIPO_PRODUCTO)) anadirProductos();
		
		TiendaFrame.getInstance().navegarA(this);
		
    }
	
	private void anadirProductos() {
		Producto[] catalogo = tienda.getAlmacen().getProductosCoincidentes("");
	
		vista.vaciarDescontados();
		
		for (Producto p: catalogo) {
			ControlPanelProductoSeleccion control = new ControlPanelProductoSeleccion(tienda, p, "Descontado", "Descontar", this, vista);
			if (this.descontado != null && this.descontado.equals(p)) {
				this.panelDescontado = control.getPanel();
				this.panelDescontado.toggleCheckBox();
			}
		}
	}
	
	private void anadirCategorias() {
		Categoria[] categorias = tienda.getAlmacen().getCategorias();
	
		vista.vaciarDescontados();
		
		for (Categoria c: categorias) {
			ControlPanelCategoriaSeleccion control = new ControlPanelCategoriaSeleccion(tienda, c, this, vista);
			if (this.descontado != null && this.descontado.equals(c)) {
				this.panelDescontado = control.getPanel();
				this.panelDescontado.toggleCheckBox();
			}
			
		}
		
		vista.revalidate();
		vista.repaint();
	}
	
	@Override
	public void setSeleccionado(Descontable elem, PanelSeleccion panel, boolean seleccionado) {
		if (this.descontado == null) {
			if (!seleccionado) return;
			
			this.descontado = elem;
			this.panelDescontado = panel;
			this.panelDescontado.toggleCheckBox();
		}
		else if (seleccionado) {
			this.panelDescontado.toggleCheckBox();
			panel.toggleCheckBox();
			this.panelDescontado = panel;
			this.descontado = elem;
		} else {
			if (!seleccionado) {
				if (this.descontado.equals(elem)) {
					this.panelDescontado.toggleCheckBox();
					this.descontado = null;
					this.panelDescontado = null;
				}
			} else {
				if (!this.descontado.equals(elem)) {
					this.panelDescontado.toggleCheckBox();
					panel.toggleCheckBox();
					this.panelDescontado = panel;
					this.descontado = elem;
				}
	
			}
		}
		
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
		case PanelMultiopcion.CAMBIO_OPCION_ACTION -> {
			cambiarTipoDescontado();
		}
		case VentanaAnadirDescuento.CANCELAR_ACTION -> new ControlInicioGestor(tienda, gestor);
		case VentanaAnadirDescuento.CONFIRMAR_ACTION-> computarDescuento();
		case "Regalo" -> seleccionRegalo();
		
		}
		
		
	}
	
	public void seleccionRegalo() {
		ControlSeleccionarRegalo control = new ControlSeleccionarRegalo(tienda, vista);
		regalo = control.getRegalo();
		
		
	}

	@Override
	public JPanel getVista() {
		return vista;
	}

}
