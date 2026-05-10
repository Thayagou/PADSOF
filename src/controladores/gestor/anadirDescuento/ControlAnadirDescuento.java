package controladores.gestor.anadirDescuento;

import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import modelo.venta.descuentos.CondicionDescuento;
import modelo.venta.descuentos.Descontable;
import controladores.ControladorPantalla;
import controladores.gestor.ControlInicioGestor;
import modelo.exceptions.*;
import modelo.sistema.Tienda;
import modelo.usuario.Gestor;
import modelo.venta.productos.Categoria;
import modelo.venta.productos.Producto;
import vistas.common.app.TiendaFrame;
import vistas.common.assets.VentanaMensaje;
import vistas.common.components.PanelSeleccion;
import vistas.gestor.anadirDescuento.VentanaAnadirDescuento;

/**
 * Clase controladora de la vista correspondiente a añadir un nuevo descuento a la tienda
 */
public class ControlAnadirDescuento implements ControlGestionSeleccion<Descontable>, ControladorPantalla{
	
	/** Tienda sobre la que se actúa y muestran datos. */
	private Tienda tienda;
	
	/** Gestor de la tienda sobre la que estamos actuando. */
	private Gestor gestor;
	
	/** Vista que muestra el controlador por pantalla. */
	private VentanaAnadirDescuento vista;
	
	/** Tipo de descontable actualmente, productos o categorías */
	private String tipoActual;
	
	/** Elemento descontable sobre el que se va a aplicar el descuento */
	private Descontable descontado;
	
	/** Panel del descontado, lo guardamos para hacer toggleSelection de manera eficiente */
	private PanelSeleccion panelDescontado;
	
	/** En el caso de que la compensación sea de Regalo, el producto a ser regalado */
	private Producto regalo;
	
	/** Lista de controladores que nos permite no tener que volver a crear los paneles de los productos */
	private List<ControlPanelProductoSeleccion> listaControlesPanelesProductos;
	
	/** Lista de controladores que nos permite no tener que volver a crear los paneles de las categorías */
	private List<ControlPanelCategoriaSeleccion> listaControlesPanelesCategorias;
	
	
	/**
	 * Instancia un nuevo Controlador, que crea la vista de añadir descuento y los paneles de Producto/ Categoría.
	 *
	 * @param tienda Tienda sobre la que se actúa y muestran datos.
	 * @param gestor Gestor de la tienda sobre la que estamos actuando.
	 */
	public ControlAnadirDescuento(Tienda tienda, Gestor gestor) {
		this.tienda = tienda;
		vista = new VentanaAnadirDescuento();
		tipoActual = vista.getOpcionSeleccionadaDescontado();
		vista.setControlador(this);
		
		cargarDescontados();
		
		TiendaFrame.getInstance().navegarA(this);
		
    }
	
	/**
	 * Dependiendo del tipo de descontado carga las categorías o productos a descontar
	 */
	private void cargarDescontados() {
		
		if (tipoActual.equals(VentanaAnadirDescuento.TIPO_CATEGORIA)) anadirCategorias();
		else if (tipoActual.equals(VentanaAnadirDescuento.TIPO_PRODUCTO)) anadirProductos();
		
	}
	
	/**
	 * Añade los productos de la tienda, y, si ya están cargados los controladores, simplemente los enseña
	 */
	private void anadirProductos() {
		vista.vaciarDescontados();
		
		if (listaControlesPanelesProductos != null) {
			for (ControlPanelProductoSeleccion control : listaControlesPanelesProductos) {
				vista.anadirDisplay(control.getPanel());
			}
		} else {
			Producto[] catalogo = tienda.getAlmacen().getProductosCoincidentes("");
			listaControlesPanelesProductos = new ArrayList<>();
			
			for (Producto p: catalogo) {
				if (p.tieneDescuento()) continue;
				ControlPanelProductoSeleccion control = new ControlPanelProductoSeleccion(tienda, p, "Descontado", "Descontar", this, vista);
				listaControlesPanelesProductos.add(control);
				if (this.descontado != null && this.descontado.equals(p)) {
					this.panelDescontado = control.getPanel();
					this.panelDescontado.toggleCheckBox();
				}
			}
		}
		
		vista.revalidate();
		vista.repaint();
	}
	
	/**
	 * Añade las categorías de la tienda, y, si ya están cargados los controladores, simplemente los enseña
	 */
	private void anadirCategorias() {
		vista.vaciarDescontados();
		
		if (listaControlesPanelesCategorias != null) {
			for (ControlPanelCategoriaSeleccion control : listaControlesPanelesCategorias) {
				vista.anadirDisplay(control.getPanel());
			}
		} else {
			Categoria[] categorias = tienda.getAlmacen().getCategorias();
			listaControlesPanelesCategorias = new ArrayList<>();
			
			for (Categoria c: categorias) {
				if (c.tieneDescuento()) continue;
				ControlPanelCategoriaSeleccion control = new ControlPanelCategoriaSeleccion(tienda, c, this, vista);
				listaControlesPanelesCategorias.add(control);
				if (this.descontado != null && this.descontado.equals(c)) {
					this.panelDescontado = control.getPanel();
					this.panelDescontado.toggleCheckBox();
				}
				
			}
		}
		
		vista.revalidate();
		vista.repaint();
	}
	
	/**
	 * Establece el elemeto seleccionado a descontar
	 *
	 * @param elem Elemento de la interfaz Descontable a descontar
	 * @param panel Panel correspondiente a dicho elemento
	 * @param seleccionado Determina si está o no seleccionado
	 */
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
	
	/**
	 * Cambia el tipo de descontable que se está mostrando en pantalla a partir del que está seleccionado en la vista
	 */
	private void cambiarTipoDescontado() {
		String tipoNuevo = vista.getOpcionSeleccionadaDescontado();
		if (tipoNuevo.equals(tipoActual)) return;
		
		tipoActual = tipoNuevo;
		
		if (tipoActual.equals(VentanaAnadirDescuento.TIPO_CATEGORIA)) anadirCategorias();
		else if (tipoActual.equals(VentanaAnadirDescuento.TIPO_PRODUCTO)) anadirProductos();
	}
	
	/**
	 * Cambia el tipo de condición de descuento que se está mostrando en pantalla a partir del que está seleccionado en la vista
	 */
	private void cambiarTipoCondicion() {
		vista.setVisibilidadCantidad(false);
		vista.setVisibilidadVolumen(false);

		String condicion = vista.getOpcionSeleccionadaCondicion();
		
		if (condicion.equals(VentanaAnadirDescuento.COND_CANTIDAD)) vista.setVisibilidadCantidad(true);
		if (condicion.equals(VentanaAnadirDescuento.COND_VOLUMEN)) vista.setVisibilidadVolumen(true);
		
	}
	
	/**
	 * cambiarTipoCompensacion.
	 */
	private void cambiarTipoCompensacion() {
		vista.setVisibilidadRegalo(false);
		vista.setVisibilidadDinero(false);
		vista.setVisibilidadPorcentaje(false);

		String condicion = vista.getOpcionSeleccionadaCompensacion();
		
		if (condicion.equals(VentanaAnadirDescuento.COMP_DINERO)) vista.setVisibilidadDinero(true);
		if (condicion.equals(VentanaAnadirDescuento.COMP_PORCENTAJE)) vista.setVisibilidadPorcentaje(true);
		if (condicion.equals(VentanaAnadirDescuento.COMP_REGALO)) vista.setVisibilidadRegalo(true);
	}
	
	/**
	 * A partir de los datos introducidos en la vista se añade un nuevo descuento, y, en el caso de que no se hayan introducido los datos correctamente, manda un mensaje de error
	 */
	private void computarDescuento() {
		
		if (descontado == null) {
			new VentanaMensaje("Debes seleccionar un producto o categoría a descontar", VentanaMensaje.ERROR);
			return;
		}
		
		double valorMinimo = -1;
		CondicionDescuento condicion = null;
		
		switch (vista.getOpcionSeleccionadaCondicion())	{
			case VentanaAnadirDescuento.COND_CANTIDAD -> {
				condicion = CondicionDescuento.CANTIDAD;
				valorMinimo = vista.getValorMinCantidad();
			}
			case VentanaAnadirDescuento.COND_VOLUMEN -> {
				condicion = CondicionDescuento.VOLUMEN;
				valorMinimo = vista.getValorMinVolumen();
			}
			case VentanaAnadirDescuento.COND_SIN -> {
				condicion = CondicionDescuento.SIN_CONDICION;
			}
		}
		
		LocalDateTime fechaInicio = null, fechaFin = null;
		
		try {	
			fechaInicio = vista.getFechaInicio();
			fechaFin = vista.getFechaFin();
		} catch(DateTimeParseException e) {
			new VentanaMensaje("Formato inválido de fecha. Correcto dd/mm/yyyy HH:MM", VentanaMensaje.ERROR);
			return;
		}
		
		if (!TiendaFrame.getConfirmacionUsuario("Estás seguro de que desea añadir el descuento?")) return;
		
		try {
			switch (vista.getOpcionSeleccionadaCompensacion() ) {
				case VentanaAnadirDescuento.COMP_DINERO -> {
					double compDinero = vista.getCompensacionDinero();
					tienda.getAlmacen().anadirDescuentoDinero(descontado, valorMinimo, fechaInicio, fechaFin, condicion, compDinero);
				}
				case VentanaAnadirDescuento.COMP_PORCENTAJE -> {
					double compPorcentaje = vista.getCompensacionPorcentaje();
					tienda.getAlmacen().anadirDescuentoPorcentaje(descontado, valorMinimo, fechaInicio, fechaFin, condicion, compPorcentaje);
				}
				case VentanaAnadirDescuento.COMP_REGALO -> {
					if (regalo == null) {
						new VentanaMensaje("Debes seleccionar un producto como regalo");
						return;
					}
					tienda.getAlmacen().anadirDescuentoRegalo(descontado, valorMinimo, fechaInicio, fechaFin, condicion, regalo);
				}
			}
		} catch (DoubleDiscountException e) {
			new VentanaMensaje(e.getMessage(), VentanaMensaje.ERROR);
			return;
		} catch (InvalidArgumentException iae) {
			new VentanaMensaje(iae.getMessage(), VentanaMensaje.ERROR);
			return;
		}
		
		new VentanaMensaje("Se ha añadido correctamente el descuento a la tienda");
		
		/* Se asegura que se vuelven a cargar */
		listaControlesPanelesProductos = null;
		listaControlesPanelesCategorias = null;
		
		cargarDescontados();
		
		
	}
	
	/**
	 * Método que maneja todas las posibles acciones realizadas sobre la vista que maneja el controlador
	 * 
	 * Recibe valores de entrada de las vistas, actúa sobre el modelo para obtener la respuesta y actualiza las ventanas correspondientes.
	 *
	 * @param e Evento de acción lanzado por un componente Swing
	 */
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

	/**
	 * seleccionRegalo.
	 */
	public void seleccionRegalo() {
		ControlSeleccionarRegalo control = new ControlSeleccionarRegalo(tienda, vista);
		regalo = control.getRegalo();
		
		
	}

	/**
	 * Getter de la vista que controla este controlador.
	 *
	 * @return JPanel de la vista
	 */
	@Override
	public JPanel getVista() {
		return vista;
	}

	/**
	 * Getter de la información que se muestra al consultar la ayuda.
	 *
	 * @return the explicacion
	 */
	@Override
	public String getExplicacion() {
		return "En esta pantalla se permite añadir un nuevo descuento a un producto o categoría de la tienda especificando las condiciones para aplicarse, la compensación recibida y el periodo de tiempo sobre el que estará activo";
	}

}
