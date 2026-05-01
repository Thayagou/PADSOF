package vistas.common;

public interface VentanaConDisplay<D extends PanelDisplay> {
	public <K extends D> D anadirDisplay(K panelDisplay);
}
