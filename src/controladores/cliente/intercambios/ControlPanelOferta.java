package controladores.cliente.intercambios;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.SwingUtilities;

import controladores.cliente.intercambios.pantallas.ControlVerMisOfertas;
import controladores.cliente.intercambios.pantallas.ControlVerOferta;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.wallapop.ArticuloSegundaMano;
import modelo.wallapop.EstadoIntercambio;
import modelo.wallapop.Intercambio;
import vistas.common.app.TiendaFrame;
import vistas.common.assets.VentanaMensaje;
import vistas.common.displays.VentanaConDisplay;
import vistas.cliente.intercambios.*;

/**
 * Controlador del panel de visualización de una oferta de intercambio.
 */
public class ControlPanelOferta implements ActionListener {

	/** Campo tienda. Referencia al modelo de la tienda. */
	Tienda tienda;
	
	/** Campo cliente. Cliente registrado que visualiza la oferta. */
	ClienteRegistrado cliente;
	
	/** Campo intercambio. Intercambio asociado a este panel. */
	Intercambio intercambio;
	
	/** Campo vista. Contenedor donde se muestra el panel de oferta. */
	VentanaConDisplay<PanelOferta> vista;
	
	/** Campo panel. Panel de oferta asociado a este controlador. */
	PanelOferta panel;
	
	/** Campo controlador. Controlador padre para refrescar la lista tras las acciones. */
	ControlVerMisOfertas controlador;

	/** Constante clickAction. Comando de acción para el clic sobre el panel. */
	private static final String clickAction = "clic";
	
	/** Constante acceptAction. Comando de acción para el botón de aceptar oferta. */
	private static final String acceptAction = "Aceptar";
	
	/** Constante rejectAction. Comando de acción para el botón de rechazar oferta. */
	private static final String rejectAction = "Rechazar";
	
	/** Constante cancelAction. Comando de acción para el botón de cancelar oferta. */
	private static final String cancelAction = "Cancelar";

	/** Constante emisorPfp. Ruta de la imagen de perfil por defecto del emisor. */
	private static final String emisorPfp = "pfp.png";
	
	/** Campo aceptada. Indica si la oferta ya ha sido aceptada. */
	private boolean aceptada = false;

	/**
	 * Instancia un nuevo Objeto ControlPanelOferta.
	 *
	 * @param tienda Referencia al modelo de la tienda.
	 * @param cliente Cliente registrado que visualiza la oferta.
	 * @param intercambio Intercambio asociado a este panel.
	 * @param vista Contenedor donde se añadirá el panel de oferta.
	 * @param controlador Controlador padre para refrescar la lista tras las acciones.
	 */
	public ControlPanelOferta(Tienda tienda, ClienteRegistrado cliente, Intercambio intercambio,
			VentanaConDisplay<PanelOferta> vista, ControlVerMisOfertas controlador) {
		this.tienda = tienda;
		this.cliente = cliente;
		this.intercambio = intercambio;
		this.vista = vista;
		this.controlador = controlador;

		String nombreEmisor = intercambio.getEmisor().getDueno().getNombre();
		String nombreReceptor = intercambio.getReceptor().getDueno().getNombre();
		String imagenEmisor = emisorPfp;
		String fotoArticulo = intercambio.getOfrecidos()[0].getImage();

		ArrayList<String> arrayOfrecidos = new ArrayList<>();
		for (ArticuloSegundaMano a : intercambio.getOfrecidos())
			arrayOfrecidos.add(a.getNombre());
		String[] articulosOfrecidos = arrayOfrecidos.toArray(new String[0]);

		ArrayList<String> arraySolicitados = new ArrayList<>();
		for (ArticuloSegundaMano a : intercambio.getSolicitados())
			arraySolicitados.add(a.getNombre());
		String[] articulosSolicitados = arraySolicitados.toArray(new String[0]);
		
		/* Crear el panel de la oferta */
		if(intercambio.getEstado().equals(EstadoIntercambio.ACEPTADO)) {
			aceptada = true;
			if(intercambio.getEmisor().getDueno().equals(cliente)) {
				panel = new PanelOferta(null, imagenEmisor, nombreReceptor, fotoArticulo, articulosSolicitados, articulosOfrecidos, clickAction);
			}
			else panel = new PanelOferta(nombreEmisor, imagenEmisor, null, fotoArticulo, articulosSolicitados, articulosOfrecidos, clickAction);
		} else {
			if(intercambio.getEmisor().getDueno().equals(cliente)) {
				panel = new PanelOferta(nombreReceptor, imagenEmisor, fotoArticulo, articulosSolicitados, articulosOfrecidos,
						clickAction, cancelAction);
			}
			else panel = new PanelOferta(nombreEmisor, imagenEmisor, fotoArticulo, articulosSolicitados, articulosOfrecidos,
					clickAction, acceptAction, rejectAction);
		}
		panel.setControlador(this);

		vista.anadirDisplay(panel);
	}

	/**
	 * actionPerformed.
	 * Gestiona las acciones de aceptar, rechazar o cancelar la oferta, o ver sus detalles.
	 *
	 * @param e Evento de acción recibido.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(aceptada) return;
		switch (e.getActionCommand()) {
		case clickAction:
			SwingUtilities.invokeLater(() -> new ControlVerOferta(tienda, cliente, intercambio));
			break;
		case acceptAction:
			if(TiendaFrame.getConfirmacionUsuario("¿Quieres aceptar la oferta de " + intercambio.getEmisor().getDueno().getNombre() + "? Tus objetos involucrados desaparecerán de tu cartera." )) {
				try {
					tienda.aceptarIntercambio(cliente, intercambio);
					controlador.refrescar();
				} catch (Exception ex) {
					new VentanaMensaje(ex.getMessage(), VentanaMensaje.ERROR);
				}
			}
			break;
		case rejectAction:
			if(TiendaFrame.getConfirmacionUsuario("¿Quieres rechazar la oferta de " + intercambio.getEmisor().getDueno().getNombre() + "?" )) {
				try {
					tienda.rechazarIntercambio(cliente, intercambio);
					controlador.refrescar();
				} catch (Exception ex) {
					new VentanaMensaje(ex.getMessage(), VentanaMensaje.ERROR);
				}
			}
			break;
		case cancelAction:
			if(TiendaFrame.getConfirmacionUsuario("¿Quieres cancelar la oferta?")) {
				try {
					tienda.cancelarIntercambio(cliente, intercambio);
					controlador.refrescar();
				} catch (Exception e1) {
					new VentanaMensaje(e1.getMessage(), VentanaMensaje.ERROR);
				}
			}
			break;
		}
	}

}