package controladores.gestor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.aplicacion.Main;
import modelo.sistema.ParametroSistema;
import modelo.sistema.Sistema;
import modelo.sistema.Tienda;
import modelo.usuario.Empleado;
import modelo.usuario.Gestor;
import vistas.common.TiendaFrame;
import vistas.gestor.PanelParametroSistema;
import vistas.gestor.VentanaGestionarEmpleados;
import vistas.gestor.VentanaGestionarParametrosSistema;

public class ControlGestionarParametrosSistema implements ActionListener{
	private Tienda tienda;
	private Gestor gestor;
	private VentanaGestionarParametrosSistema vista;
	
	public ControlGestionarParametrosSistema(Tienda tienda, Gestor gestor) {
		this.tienda = tienda;
		this.gestor = gestor;
		TiendaFrame frame = TiendaFrame.getInstance();
		this.vista = new VentanaGestionarParametrosSistema();
		
		Sistema sistema = Sistema.getInstancia();
		
		PanelParametroSistema categoria = new PanelParametroSistema("  Categoría:              ", String.format("%.2f", sistema.getPonderacionCategoria()), ParametroSistema.CATEGORIA.name());
		categoria.setControlador(this);
		PanelParametroSistema precioDeCompra = new PanelParametroSistema("  Precio de compra:       ", String.format("%.2f", sistema.getPonderacionPrecioCompra()), ParametroSistema.PRECIO_COMPRA.name());
		precioDeCompra.setControlador(this);
		PanelParametroSistema valoracionesProducto = new PanelParametroSistema("  Valoraciones producto:  ", String.format("%.2f", sistema.getPonderacionValoracionesProducto()), ParametroSistema.VALORACIONES_PRODUCTO.name());
		valoracionesProducto.setControlador(this);
		PanelParametroSistema productoRecomendado = new PanelParametroSistema("  Producto recomendado:   ", String.format("%.2f", sistema.getPonderacionProductoRecomendado()), ParametroSistema.PRODUCTO_RECOMENDADO.name());
		productoRecomendado.setControlador(this);
		PanelParametroSistema busqueda = new PanelParametroSistema("  Búsqueda:               ", String.format("%.2f", sistema.getPonderacionBusqueda()), ParametroSistema.BUSQUEDA.name());
		busqueda.setControlador(this);
		PanelParametroSistema duracionCarrito = new PanelParametroSistema("  Duración carrito:       ", sistema.getTiempoCaducaCarrito().toString(), ParametroSistema.DURACION_CARRITO.name());
		duracionCarrito.setControlador(this);
		PanelParametroSistema duracionOferta = new PanelParametroSistema("  Duración ofertas:       ", sistema.getTiempoCaducaOferta().toString(), ParametroSistema.DURACION_OFERTA.name());
		duracionOferta.setControlador(this);
		PanelParametroSistema precioValoracion = new PanelParametroSistema("  Precio valoracion:       ", String.format("%.2f", sistema.getPrecioValoracion()), ParametroSistema.PRECIO_VALORACION.name());
		precioValoracion.setControlador(this);
		frame.setVistaActual(vista);
		
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch() {
		
		}
	}
	
}
