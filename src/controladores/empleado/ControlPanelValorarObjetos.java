package controladores.empleado;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import modelo.sistema.Tienda;
import modelo.usuario.Empleado;
import modelo.venta.productos.Categoria;
import modelo.wallapop.ArticuloSegundaMano;
import vistas.common.VentanaMensaje;
import vistas.empleado.PanelArticuloPendienteValoracion;
import vistas.empleado.VentanaValorarObjetos;

public class ControlPanelValorarObjetos implements ActionListener {
	private ArticuloSegundaMano articulo;
	private Empleado empleado;
	private Tienda tienda;

	public ControlPanelValorarObjetos(Tienda tienda, ArticuloSegundaMano articulo, Empleado empleado,
			VentanaValorarObjetos vista) {
		this.articulo = articulo;
		List<String> categorias = new ArrayList<>();
		for (Categoria c : articulo.getCategorias()) {
			categorias.add(c.getNombre());
		}
		PanelArticuloPendienteValoracion panel = new PanelArticuloPendienteValoracion(articulo.getNombre(), articulo.getDescripcion(),
				articulo.getInteresadoEn(), -1, "Muy bueno", "Valorar artículo", categorias.toArray(new String[0]));
		vista.anadirDisplay(panel);
		panel.setControlador(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("llega aqui");
		switch (e.getActionCommand()) {
		case "Valorar artículo":
			try {
				tienda.getHistorial().valorarArticulo(empleado, articulo, 0, null);
			} catch (Exception ex) {
				new VentanaMensaje(e.toString());
			}
		}
	}
}
