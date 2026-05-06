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
import vistas.cliente.intercambios.pantallas.VentanaOfertaIntercambio;
import vistas.common.PanelArticulo;

public class ControlPanelArticuloEnOferta implements ActionListener {

	private PanelArticulo panel;
	private Tienda tienda;
	private ClienteRegistrado cliente;
	private ArticuloSegundaMano articulo;
	
	private static final String FOTO_ARTICULO_DF = "articuloDefault.png";
	
	private static final String actionName = "clic";

	public ControlPanelArticuloEnOferta(Tienda tienda, ClienteRegistrado cliente, ArticuloSegundaMano articulo,
			VentanaOfertaIntercambio vista) {
		this.cliente = cliente;
		this.tienda = tienda;
		this.articulo = articulo;
		
		ArrayList<String> cats = new ArrayList<>();
		for (Categoria c : articulo.getCategorias()) {
			cats.add(c.getNombre());
		}
		String[] categorias = cats.toArray(new String[0]);

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
		
		String nombre = articulo.getNombre();
		String descripcion = articulo.getDescripcion();
		String interesadoEn = articulo.getInteresadoEn();

		this.panel = new PanelArticulo(nombre, foto, descripcion, interesadoEn, estimacion, estado, actionName, categorias);
		panel.setControlador(this);
		
		if(this.cliente.equals(articulo.getPropietario())) {
			vista.anadirMio(panel);
		} else {
			vista.anadirSuyo(panel);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case actionName:
			SwingUtilities.invokeLater(() -> new ControlInfoArticulo(tienda, cliente, articulo));
			break;
		}
	}
}
