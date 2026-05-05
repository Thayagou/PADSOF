package controladores.cliente.intercambios;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import controladores.cliente.intercambios.pantallas.ControlInfoArticulo;
import controladores.cliente.intercambios.pantallas.ControlVentanaPagoValoracion;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.venta.productos.Categoria;
import modelo.wallapop.ArticuloSegundaMano;
import vistas.cliente.intercambios.PanelArticuloEnCartera;
import vistas.common.*;

public class ControlPanelArticuloEnCartera implements ActionListener {

	private PanelArticulo panel;
	private ArticuloSegundaMano articulo;
	private Tienda tienda;
	private ClienteRegistrado cliente;
	@SuppressWarnings("unused")
	private VentanaConDisplay<PanelArticulo> vista;

	private final String FOTO_PERFIL = "pfp.png";

	private static final String actionName = "clic";

	public ControlPanelArticuloEnCartera(Tienda tienda, ClienteRegistrado cliente, ArticuloSegundaMano articulo,
			VentanaConDisplay<PanelArticulo> vista) {
		ClienteRegistrado dueno = articulo.getDueno().getDueno();
		this.articulo = articulo;
		this.tienda = tienda;
		this.vista = vista;
		this.cliente = cliente;

		ArrayList<String> categorias = new ArrayList<>();
		for (Categoria c : articulo.getCategorias()) {
			categorias.add(c.getNombre());
		}

		if (articulo.getValoracion() == null) {

		}

		String estado;
		double estimacion;
		if (articulo.getValoracion() != null) {
			switch (articulo.getValoracion().getEstadoFisico()) {
			case PERFECTO:
				estado = "Perfecto";
				break;
			case MUY_BUENO:
				estado = "Muy bueno";
				break;
			case USO_LIGERO:
				estado = "Uso ligero";
				break;
			case USO_EVIDENTE:
				estado = "Uso evidente";
				break;
			case MUY_USADO:
				estado = "Muy usado";
				break;
			case DANADO:
				estado = "Dañado";
				break;
			case PENDIENTE:
				estado = "Pendiente de valoración";
				break;
			default:
				estado = "Error";
				break;
			}
			estimacion = articulo.getValoracion().getPrecioEstimado();
		} else {
			estado = "Sin valorar";
			estimacion = -1;
		}

		panel = new PanelArticuloEnCartera(dueno.getNombre(), FOTO_PERFIL, articulo.getNombre(),
				articulo.getDescripcion(), articulo.getInteresadoEn(), estimacion, estado, actionName,
				categorias.toArray(new String[0]));
		panel.setControlador(this);
		
		vista.anadirDisplay(panel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case actionName:
			SwingUtilities.invokeLater(() -> new ControlInfoArticulo(tienda, cliente, articulo));
			break;
		case "Solicitar valoracion":
			SwingUtilities.invokeLater(() -> new ControlVentanaPagoValoracion(tienda, cliente, articulo));
			break;

		}
		if (e.getActionCommand().endsWith(actionName)) {
		}

	}

}
