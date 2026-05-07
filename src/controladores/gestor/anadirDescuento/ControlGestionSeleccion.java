package controladores.gestor.anadirDescuento;

import java.awt.event.ActionListener;

import vistas.common.components.PanelSeleccion;

public interface ControlGestionSeleccion<T> {
	public void setSeleccionado(T elem, PanelSeleccion panel, boolean seleccionado);
}
