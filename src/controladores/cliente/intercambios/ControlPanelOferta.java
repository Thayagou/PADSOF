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
import modelo.wallapop.Intercambio;
import vistas.common.app.TiendaFrame;
import vistas.common.assets.VentanaMensaje;
import vistas.common.displays.VentanaConDisplay;
import vistas.cliente.intercambios.*;

public class ControlPanelOferta implements ActionListener {

	Tienda tienda;
	ClienteRegistrado cliente;
	Intercambio intercambio;
	VentanaConDisplay<PanelOferta> vista;
	PanelOferta panel;
	ControlVerMisOfertas controlador;

	private static final String clickAction = "clic";
	private static final String acceptAction = "Aceptar";
	private static final String rejectAction = "Rechazar";
	private static final String cancelAction = "Cancelar";

	private static final String emisorPfp = "pfp.png";

	public ControlPanelOferta(Tienda tienda, ClienteRegistrado cliente, Intercambio intercambio,
			VentanaConDisplay<PanelOferta> vista, ControlVerMisOfertas controlador) {
		this.tienda = tienda;
		this.cliente = cliente;
		this.intercambio = intercambio;
		this.vista = vista;
		this.controlador = controlador;

		String nombreEmisor = intercambio.getEmisor().getDueno().getNombre();
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

		if(intercambio.getEmisor().getDueno().equals(cliente)) {
			panel = new PanelOferta(nombreEmisor, imagenEmisor, fotoArticulo, articulosSolicitados, articulosOfrecidos,
					clickAction, cancelAction);
		}
		else panel = new PanelOferta(nombreEmisor, imagenEmisor, fotoArticulo, articulosSolicitados, articulosOfrecidos,
				clickAction, acceptAction, rejectAction);
		panel.setControlador(this);

		vista.anadirDisplay(panel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case clickAction:
			SwingUtilities.invokeLater(() -> new ControlVerOferta(tienda, cliente, intercambio));
			break;
		case acceptAction:
			if(TiendaFrame.getConfirmacionUsuario("¿Quieres aceptar la oferta de " + intercambio.getEmisor().getDueno().getNombre() + "?" )) {
				try {
					cliente.getCartera().aceptarIntercambio(intercambio);
					controlador.refrescar();
				} catch (Exception ex) {
					new VentanaMensaje(ex.getMessage());
				}
			}
			break;
		case rejectAction:
			if(TiendaFrame.getConfirmacionUsuario("¿Quieres rechazar la oferta de " + intercambio.getEmisor().getDueno().getNombre() + "?" )) {
				try {
					cliente.getCartera().rechazarIntercambio(intercambio);
					controlador.refrescar();
				} catch (Exception ex) {
					new VentanaMensaje(ex.getMessage());
				}
			}
			break;
		case cancelAction:
			if(TiendaFrame.getConfirmacionUsuario("¿Quieres cancelar la oferta?")) {
				try {
					cliente.getCartera().cancelarIntercambio(intercambio);
					controlador.refrescar();
				} catch (Exception e1) {
					new VentanaMensaje(e1.getMessage());
				}
			}
			break;
		}
	}

}
