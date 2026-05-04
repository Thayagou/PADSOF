package controladores.cliente.intercambios;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.venta.productos.Categoria;
import modelo.wallapop.ArticuloSegundaMano;
import vistas.common.*;

public class ControlPanelArticulo implements ActionListener {

	private PanelArticulo panel;
	private ArticuloSegundaMano articulo;
	private Tienda tienda;
	private ClienteRegistrado cliente;
	private VentanaConDisplay<PanelArticulo> vista;

	private static final String actionName = "clic";

	public ControlPanelArticulo(Tienda tienda, ClienteRegistrado cliente, ArticuloSegundaMano articulo,
			VentanaConDisplay<PanelArticulo> vista) {
		this.cliente = cliente;
		this.articulo = articulo;
		this.tienda = tienda;
		this.vista = vista;

		ArrayList<String> categorias = new ArrayList<>();
		for (Categoria c : articulo.getCategorias()) {
			categorias.add(c.getNombre());
		}

		panel = new PanelArticulo(articulo.getNombre(), articulo.getDescripcion(), articulo.getInteresadoEn(),
				articulo.getValoracion().getPrecioEstimado(), articulo.getValoracion().getEstadoFisico().name(), actionName,
				categorias.toArray(new String[0]));
		vista.anadirDisplay(panel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().endsWith(actionName)) {

		}

	}

}
