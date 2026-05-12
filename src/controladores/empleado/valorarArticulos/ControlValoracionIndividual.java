package controladores.empleado.valorarArticulos;

import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import modelo.exceptions.ArticuloSinValoracionException;
import modelo.exceptions.InvalidArgumentException;
import modelo.exceptions.InvalidPermitException;
import modelo.sistema.Tienda;
import modelo.usuario.Empleado;
import modelo.venta.productos.Categoria;
import modelo.wallapop.ArticuloSegundaMano;
import modelo.wallapop.EstadoFisicoArticulo;
import vistas.common.app.TiendaFrame;
import vistas.common.assets.VentanaMensaje;
import vistas.empleado.valorarArticulos.VentanaValoracionIndividual;

/**
 * Esta clase representa el controlador de la ventana para hacer una valoración individual
 */
public class ControlValoracionIndividual implements ControladorPantalla {
	/** Modelo de la tienda sobre el que se actúa */
	private final Tienda tienda;
	/** Empleado que realiza la acción */
	private final Empleado empleado;
	/** Artículo que se está valorando */
	private final ArticuloSegundaMano articulo;
	/** Ventana que se muestra */
	private final VentanaValoracionIndividual vista;

	/**
	 * Cosntructor del controlador de la valoración individual
	 * @param tienda Modelo de la tienda
	 * @param empleado Empleado que realiza la acción
	 * @param articulo Artículo que se valora
	 */
	public ControlValoracionIndividual(Tienda tienda, Empleado empleado, ArticuloSegundaMano articulo) {
		this.tienda = tienda;
		this.empleado = empleado;
		this.articulo = articulo;

		List<String> categorias = new LinkedList<>();
		for (Categoria c : articulo.getCategorias()) {
			categorias.add(c.getNombre());
		}

		List<String> tiposEstado = new LinkedList<>();
		for (EstadoFisicoArticulo e : EstadoFisicoArticulo.values()) {
			if(!e.name().equals("PENDIENTE")) tiposEstado.add(e.name());
		}

		LocalDateTime fecha = articulo.getValoracion().getFechaSolicitud();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy  HH:mm");
		String fechaFormateada = fecha.format(formatter);

		this.vista = new VentanaValoracionIndividual(articulo.getPropietario().getNombre(), "pfp.png", fechaFormateada,
				articulo.getNombre(), articulo.getImage(), categorias.toArray(new String[0]), articulo.getDescripcion(),
				tiposEstado.toArray(new String[0]));
		vista.setControlador(this);
		TiendaFrame.getInstance().navegarA(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Valorar":
			intentarValorar();
		}
	}
		
	/**
	 * Acción que se ejecuta al intentar valorar un artículo
	 */
	private void intentarValorar() {
		double estimacion = -1;
		try {
			estimacion = Double.parseDouble(vista.getEstimacion());
		} catch (RuntimeException ex) {
			new VentanaMensaje("Introduzca una estimación de valor válida", 1);
			return;
		}
		
		String estadoFisico = vista.getEstadoFisico();
		EstadoFisicoArticulo estado = null;
		for(EstadoFisicoArticulo es :EstadoFisicoArticulo.values()) {
			if (estadoFisico.equals(es.name())) {
				estado = es;
			}
		}
		if(estado == null) {
			new VentanaMensaje("Introduzca un estado físico válido", 1);
			return;
		}
		
		
		if(TiendaFrame.getConfirmacionUsuario("¿Estás seguro de que deseas valorar este artículo?")) {
			try {
				tienda.getHistorial().valorarArticulo(empleado, articulo, estimacion, estado);
			} catch (InvalidPermitException | InvalidArgumentException | ArticuloSinValoracionException ex) {
				new VentanaMensaje(ex.getMessage(), 1);
				TiendaFrame.getInstance().volverAtras();
				return;
			}
			TiendaFrame.getInstance().volverAtras();
			new VentanaMensaje("El artículo se ha valorado correctamente");
		}
	}

	@Override
	public JPanel getVista() {
		return vista;
	}
	
	@Override
	public boolean puedeVolver() {
		return false;
	}

	@Override
	public String getExplicacion() {
		return "En esta ventana debes valorar el artículo seleccionado, asignandole una estimación de precio y un estado físico";
	}

}
