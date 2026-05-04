package controladores.empleado.valorarArticulos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import modelo.sistema.Tienda;
import modelo.usuario.Empleado;
import modelo.venta.productos.Categoria;
import modelo.wallapop.ArticuloSegundaMano;
import vistas.empleado.valorarArticulos.PanelArticuloPendienteValoracion;
import vistas.empleado.valorarArticulos.VentanaValorarObjetos;

public class ControlPanelValorarObjetos implements ActionListener {
	private final ArticuloSegundaMano articulo;
	private final Empleado empleado;
	private final Tienda tienda;
	private final String ACTION_NAME = "Valorar artículo";

	public ControlPanelValorarObjetos(Tienda tienda, ArticuloSegundaMano articulo, Empleado empleado,
			VentanaValorarObjetos vista) {
		this.tienda = tienda;
		this.empleado = empleado;
		this.articulo = articulo;
		List<String> categorias = new ArrayList<>();
		for (Categoria c : articulo.getCategorias()) {
			categorias.add(c.getNombre());
		}
		
		PanelArticuloPendienteValoracion panel = new PanelArticuloPendienteValoracion(
				articulo.getNombre(), articulo.getDescripcion(),
				articulo.getInteresadoEn(), -1, "", ACTION_NAME, categorias.toArray(new String[0]));
		vista.anadirDisplay(panel);
		panel.setControlador(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case ACTION_NAME:
			SwingUtilities.invokeLater(() -> {
				new ControlValoracionIndividual(tienda, empleado, articulo);
			});
			break;
		}
	}
}
