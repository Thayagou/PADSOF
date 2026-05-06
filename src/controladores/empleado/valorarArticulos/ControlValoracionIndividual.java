package controladores.empleado.valorarArticulos;

import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controladores.ControladorPantalla;
import modelo.exceptions.ArticuloSinValoracionException;
import modelo.exceptions.InvalidArgumentException;
import modelo.exceptions.InvalidPermitException;
import modelo.sistema.Tienda;
import modelo.usuario.Empleado;
import modelo.venta.productos.Categoria;
import modelo.wallapop.ArticuloSegundaMano;
import modelo.wallapop.EstadoFisicoArticulo;
import vistas.common.TiendaFrame;
import vistas.common.VentanaMensaje;
import vistas.empleado.valorarArticulos.VentanaValoracionIndividual;

public class ControlValoracionIndividual implements ControladorPantalla {
	private final Tienda tienda;
	private final Empleado empleado;
	private final ArticuloSegundaMano articulo;
	private final VentanaValoracionIndividual vista;

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
				articulo.getNombre(), "producto.png", categorias.toArray(new String[0]), articulo.getDescripcion(),
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
		
	private void intentarValorar() {
		double estimacion = -1;
		try {
			estimacion = Double.parseDouble(vista.getEstimacion());
		} catch (RuntimeException ex) {
			new VentanaMensaje("Introduzca una estimación de valor válida");
		}
		
		String estadoFisico = vista.getEstadoFisico();
		EstadoFisicoArticulo estado = null;
		for(EstadoFisicoArticulo es :EstadoFisicoArticulo.values()) {
			if (estadoFisico.equals(es.name())) {
				estado = es;
			}
		}
		if(estado == null) {
			new VentanaMensaje("Introduzca un estado físico válido");
		}
		
		try {
			tienda.getHistorial().valorarArticulo(empleado, articulo, estimacion, estado);
		} catch (InvalidPermitException | InvalidArgumentException | ArticuloSinValoracionException ex) {
			new VentanaMensaje(ex.getMessage());
		}
		
		new VentanaMensaje("El artículo se ha valorado correctamente");
		
		SwingUtilities.invokeLater(() -> new ControlValorarObjetos(tienda, empleado));
	}

	@Override
	public JPanel getVista() {
		return vista;
	}
	
	@Override
	public boolean puedeVolver() {
		return false;
	}

}
