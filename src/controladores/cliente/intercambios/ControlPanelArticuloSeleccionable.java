package controladores.cliente.intercambios;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.venta.productos.Categoria;
import modelo.wallapop.ArticuloSegundaMano;
import vistas.cliente.intercambios.pantallas.VentanaOfertaIntercambio;
import vistas.common.displays.PanelArticuloSeleccion;

public class ControlPanelArticuloSeleccionable implements ActionListener {

	private PanelArticuloSeleccion panel;
	@SuppressWarnings("unused")
	private Tienda tienda;
	private ClienteRegistrado cliente;
	@SuppressWarnings("unused")
	private ArticuloSegundaMano articulo;
	@SuppressWarnings("unused")
	private VentanaOfertaIntercambio vista;
	
	private static final String FOTO_ARTICULO_DF = "articuloDefault.png";
	
	private static final String actionName = "clic";

	public ControlPanelArticuloSeleccionable(Tienda tienda, ClienteRegistrado cliente, ArticuloSegundaMano articulo,
			VentanaOfertaIntercambio vista) {
		this.cliente = cliente;
		this.tienda = tienda;
		this.articulo = articulo;
		this.vista = vista;
		
		ArrayList<String> categorias = new ArrayList<>();
		for (Categoria c : articulo.getCategorias()) {
			categorias.add(c.getNombre());
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
		
		String foto;
		if(articulo.getImage() == null) foto = FOTO_ARTICULO_DF;
		else foto = articulo.getImage();

		this.panel = new PanelArticuloSeleccion(articulo.getNombre(), foto, articulo.getDescripcion(),
				articulo.getInteresadoEn(), estimacion, estado, actionName, categorias.toArray(new String[0]));
		panel.setControlador(this);
		
		if(this.cliente.equals(articulo.getPropietario())) {
			vista.anadirMio(panel);
		} else {
			vista.anadirSuyo(panel);
		}
	}
	
	public boolean isSelected() {
		return panel.isSelected();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case actionName:
			panel.toggleSelection();
			break;
		}
	}
}
