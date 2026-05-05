package controladores.cliente.intercambios;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import controladores.cliente.intercambios.pantallas.ControlInfoArticulo;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.venta.productos.Categoria;
import modelo.wallapop.ArticuloSegundaMano;
import vistas.cliente.intercambios.PanelArticuloEnCartera;
import vistas.common.*;

public class ControlPanelArticulo implements ActionListener {

	private PanelArticuloEnCartera panel;
	private ArticuloSegundaMano articulo;
	private Tienda tienda;
	private ClienteRegistrado cliente;
	@SuppressWarnings("unused")
	private VentanaConDisplay<PanelArticuloEnCartera> vista;

	private final String FOTO_PERFIL = "pfp.png";

	private static final String actionName = "clic";

	public ControlPanelArticulo(Tienda tienda, ClienteRegistrado cliente, ArticuloSegundaMano articulo,
			VentanaConDisplay<PanelArticuloEnCartera> vista) {
		ClienteRegistrado dueno = articulo.getDueno().getDueno();
		this.articulo = articulo;
		this.tienda = tienda;
		this.vista = vista;
		this.cliente = cliente;

		ArrayList<String> categorias = new ArrayList<>();
		for (Categoria c : articulo.getCategorias()) {
			categorias.add(c.getNombre());
		}

		panel = new PanelArticuloEnCartera(dueno.getNombre(), FOTO_PERFIL, articulo.getNombre(),
				articulo.getDescripcion(), articulo.getInteresadoEn(), articulo.getValoracion().getPrecioEstimado(),
				articulo.getValoracion().getEstadoFisico().name(), actionName, categorias.toArray(new String[0]));
		vista.anadirDisplay(panel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().endsWith(actionName)) {
			SwingUtilities.invokeLater(() -> new ControlInfoArticulo(tienda, cliente, articulo));
		}

	}

}
