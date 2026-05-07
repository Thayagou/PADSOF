package vistas.common.displays;

public interface VentanaConDisplay<D extends PanelDisplay> {
	public <K extends D> D anadirDisplay(K panelDisplay);
}
