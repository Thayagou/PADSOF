package vistas.gestor;

import java.awt.*;
import javax.swing.*;

import controladores.ControlBarraLateral;
import vistas.common.BarraLateral;
import vistas.common.TiendaFrame;
import vistas.herramientas.ColorPalette;

public class BarraGestor extends BarraLateral {
	private static final long serialVersionUID = 1L;

	private JButton gestionarProductosYCategorias;
	private JButton gestionarProductos;
	private JButton anadirProducto;
	private JButton gestionarExistentes;
	private JButton gestionarCategorias;
	private JButton crearPacks;
	
	private JButton anadirDescuento;
	private JButton gestionarEmpleados;
	private JButton configurarSistema;
	
	private JButton consultarEstadisticas;
	private JButton consultarStatsClientes;
	private JButton consultarStatsProductos;
	private JButton consultarStatsVentas;
	private JButton consultarStatsIntercambios;

	public BarraGestor() {
		TiendaFrame frame = TiendaFrame.getInstance();
		int distFromLeft = frame.optionBarDistFromLeft();
		int distIndented = (int) (distFromLeft * BarraLateral.PERC_INDENTED);
		int btnHeigth = frame.btnHeight();

		setBackground(ColorPalette.CARD_LIGHT.getColor());
		setPreferredSize(new Dimension(distFromLeft, 0));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		gestionarProductos = addBtn("Gestionar productos y categorías", btnHeigth, distFromLeft);
		anadirProducto = addBtn("Añadir nuevo producto", btnHeigth, distIndented);
		gestionarExistentes = addBtn("Gestionar productos existentes", btnHeigth, distIndented);
		gestionarCategorias = addBtn("Gestionar categorías", btnHeigth, distIndented);
		crearPacks = addBtn("Crear packs de productos", btnHeigth, distIndented);

		anadirDescuento = addBtn("Añadir nuevo descuento", btnHeigth, distFromLeft);
		gestionarEmpleados = addBtn("Gestionar empleados", btnHeigth, distFromLeft);
		configurarSistema = addBtn("Configurar sistema", btnHeigth, distFromLeft);
		
		consultarEstadisticas = addBtn("Consultar estadísticas", btnHeigth, distFromLeft);
		
		consultarStatsProductos = addBtn("Estadísticas productos", btnHeigth, distIndented);
		consultarStatsClientes = addBtn("Estadísticas clientes", btnHeigth, distIndented);
		consultarStatsVentas = addBtn("Estadísticas ventas", btnHeigth, distIndented);
		consultarStatsIntercambios = addBtn("Estadísticas intercambios", btnHeigth, distIndented);
		
		add(gestionarProductos);
		add(anadirProducto);
		add(gestionarExistentes);
		add(gestionarCategorias);
		add(crearPacks);
		
		add(anadirDescuento);
		add(gestionarEmpleados);
		add(configurarSistema);
		
		add(consultarEstadisticas);
		add(consultarStatsProductos);
		add(consultarStatsClientes);
		add(consultarStatsVentas);
		add(consultarStatsIntercambios);
		
		setInvisiblesEstadisticas();
		setInvisiblesGestionarProdsYCats();
	}
	
	public void setVisiblesGestionarProdsYCats() {
		setVisibles(gestionarProductos, anadirProducto, gestionarExistentes, gestionarCategorias, crearPacks);
	}
	
	public void setInvisiblesGestionarProdsYCats() {
		setInvisibles(anadirProducto, gestionarExistentes, gestionarCategorias, crearPacks);
	}
	
	public void setVisiblesEstadisticas() {
		setVisibles(consultarStatsProductos, consultarStatsClientes, consultarStatsVentas, consultarStatsIntercambios);
	}
	
	public void setInvisiblesEstadisticas() {
		setInvisibles(consultarStatsProductos, consultarStatsClientes, consultarStatsVentas, consultarStatsIntercambios);
	}

	@Override
	public void setControlador(ControlBarraLateral c) {
		gestionarProductos.addActionListener(c);
		anadirProducto.addActionListener(c);
		gestionarExistentes.addActionListener(c);
		gestionarCategorias.addActionListener(c);
		crearPacks.addActionListener(c);
		
		anadirDescuento.addActionListener(c);
		gestionarEmpleados.addActionListener(c);
		configurarSistema.addActionListener(c);
		
		consultarEstadisticas.addActionListener(c);
		consultarStatsProductos.addActionListener(c);
		consultarStatsClientes.addActionListener(c);
		consultarStatsVentas.addActionListener(c);
		consultarStatsIntercambios.addActionListener(c);
	}
}
