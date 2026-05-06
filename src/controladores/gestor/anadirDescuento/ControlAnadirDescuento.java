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
		tipoActual = vista.getOpcionSeleccionadaDescontado();
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
		String tipoNuevo = vista.getOpcionSeleccionadaDescontado();
		if (tipoNuevo.equals(tipoActual)) return;
		
		tipoActual = tipoNuevo;
		
		if (tipoActual.equals(VentanaAnadirDescuento.TIPO_CATEGORIA)) anadirCategorias();
		else if (tipoActual.equals(VentanaAnadirDescuento.TIPO_PRODUCTO)) anadirProductos();
	}
	
	private void cambiarTipoCondicion() {
		vista.setVisibilidadCantidad(false);
		vista.setVisibilidadVolumen(false);

		String condicion = vista.getOpcionSeleccionadaCondicion();
		
		if (condicion.equals(VentanaAnadirDescuento.COND_CANTIDAD)) vista.setVisibilidadCantidad(true);
		if (condicion.equals(VentanaAnadirDescuento.COND_VOLUMEN)) vista.setVisibilidadVolumen(true);
		
	}
	
	private void cambiarTipoCompensacion() {
		vista.setVisibilidadRegalo(false);
		vista.setVisibilidadDinero(false);
		vista.setVisibilidadPorcentaje(false);

		String condicion = vista.getOpcionSeleccionadaCompensacion();
		
		if (condicion.equals(VentanaAnadirDescuento.COMP_DINERO)) vista.setVisibilidadDinero(true);
		if (condicion.equals(VentanaAnadirDescuento.COMP_PORCENTAJE)) vista.setVisibilidadPorcentaje(true);
		if (condicion.equals(VentanaAnadirDescuento.COMP_REGALO)) vista.setVisibilidadRegalo(true);
	}
	
	private void computarDescuento() {
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case VentanaAnadirDescuento.CAMBIO_TIPO_DESCONTADO_ACTION -> cambiarTipoDescontado();
		case VentanaAnadirDescuento.CAMBIO_CONDICION_ACTION -> cambiarTipoCondicion();
		case VentanaAnadirDescuento.CAMBIO_COMPENSACION_ACTION -> cambiarTipoCompensacion();
		
		case VentanaAnadirDescuento.CANCELAR_ACTION -> new ControlInicioGestor(tienda, gestor);
		case VentanaAnadirDescuento.CONFIRMAR_ACTION-> computarDescuento();
		case VentanaAnadirDescuento.COMP_REGALO -> seleccionRegalo();
		
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
