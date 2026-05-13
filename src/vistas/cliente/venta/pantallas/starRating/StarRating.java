package vistas.cliente.venta.pantallas.starRating;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

/**
 * Componente de selección de puntuación mediante 5 estrellas.
 */
public class StarRating extends javax.swing.JPanel {
	
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Campo star1. Primera estrella del selector. */
	private Star star1;
	
	/** Campo star2. Segunda estrella del selector. */
	private Star star2;
	
	/** Campo star3. Tercera estrella del selector. */
	private Star star3;
	
	/** Campo star4. Cuarta estrella del selector. */
	private Star star4;
	
	/** Campo star5. Quinta estrella del selector. */
	private Star star5;
	
	/** Campo events. Lista de eventos a ejecutar cuando se selecciona una puntuación. */
	private List<EventStarRating> events = new ArrayList<>();
    
    /** Campo star. Número de estrellas seleccionadas (1-5). */
    private int star;

    /**
     * Obtiene Star.
     *
     * @return valor de Star, número de estrellas seleccionadas (1-5).
     */
    public int getStar() {
        return star;
    }

    /**
     * Establece Star.
     *
     * @param star nuevo valor, número de estrellas a seleccionar (1-5).
     */
    public void setStar(int star) {
        this.star = star;
        if (star == 1) {
            star1ActionPerformed(null);
        } else if (star == 2) {
            star2ActionPerformed(null);
        } else if (star == 3) {
            star3ActionPerformed(null);
        } else if (star == 4) {
            star4ActionPerformed(null);
        } else {
            star5ActionPerformed(null);
        }
    }

    /**
     * Instancia un nuevo Objeto StarRating.
     */
    public StarRating() {
        initComponents();
        init();
    }

    /**
     * Inicializa la apariencia del componente.
     */
    private void init() {
        setOpaque(false);
        setBackground(new Color(204, 204, 204));
        setForeground(new Color(238, 236, 0));
    }

    /**
     * Inicializa los componentes del selector de estrellas.
     */
    private void initComponents() {

        star1 = new Star();
        star2 = new Star();
        star3 = new Star();
        star4 = new Star();
        star5 = new Star();

        setLayout(new java.awt.GridLayout(1, 5));

        star1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                star1ActionPerformed(evt);
            }
        });
        add(star1);

        star2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                star2ActionPerformed(evt);
            }
        });
        add(star2);

        star3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                star3ActionPerformed(evt);
            }
        });
        add(star3);

        star4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                star4ActionPerformed(evt);
            }
        });
        add(star4);

        star5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                star5ActionPerformed(evt);
            }
        });
        add(star5);
    }

    /**
     * star1ActionPerformed.
     * Selecciona solo la primera estrella.
     *
     * @param evt Evento de acción recibido.
     */
    private void star1ActionPerformed(java.awt.event.ActionEvent evt) {
        star1.setSelected(true);
        star2.setSelected(false);
        star3.setSelected(false);
        star4.setSelected(false);
        star5.setSelected(false);
        star = 1;
        runEvent();
    }

    /**
     * star2ActionPerformed.
     * Selecciona las dos primeras estrellas.
     *
     * @param evt Evento de acción recibido.
     */
    private void star2ActionPerformed(java.awt.event.ActionEvent evt) {
        star1.setSelected(true);
        star2.setSelected(true);
        star3.setSelected(false);
        star4.setSelected(false);
        star5.setSelected(false);
        star = 2;
        runEvent();
    }

    /**
     * star3ActionPerformed.
     * Selecciona las tres primeras estrellas.
     *
     * @param evt Evento de acción recibido.
     */
    private void star3ActionPerformed(java.awt.event.ActionEvent evt) {
        star1.setSelected(true);
        star2.setSelected(true);
        star3.setSelected(true);
        star4.setSelected(false);
        star5.setSelected(false);
        star = 3;
        runEvent();
    }

    /**
     * star4ActionPerformed.
     * Selecciona las cuatro primeras estrellas.
     *
     * @param evt Evento de acción recibido.
     */
    private void star4ActionPerformed(java.awt.event.ActionEvent evt) {
        star1.setSelected(true);
        star2.setSelected(true);
        star3.setSelected(true);
        star4.setSelected(true);
        star5.setSelected(false);
        star = 4;
        runEvent();
    }

    /**
     * star5ActionPerformed.
     * Selecciona las cinco estrellas.
     *
     * @param evt Evento de acción recibido.
     */
    private void star5ActionPerformed(java.awt.event.ActionEvent evt) {
        star1.setSelected(true);
        star2.setSelected(true);
        star3.setSelected(true);
        star4.setSelected(true);
        star5.setSelected(true);
        star = 5;
        runEvent();
    }

    /**
     * Establece Background.
     *
     * @param color nuevo valor, color de fondo para el panel y las estrellas.
     */
    @Override
    public void setBackground(Color color) {
        super.setBackground(color);
        for (Component com : getComponents()) {
            com.setBackground(color);
        }
    }

    /**
     * Establece Foreground.
     *
     * @param color nuevo valor, color de las estrellas cuando no están seleccionadas.
     */
    @Override
    public void setForeground(Color color) {
        super.setForeground(color);
        for (Component com : getComponents()) {
            com.setForeground(color);
        }
    }

    /**
     * addEventStarRating.
     *
     * @param event evento a ejecutar cuando se selecciona una puntuación.
     */
    public void addEventStarRating(EventStarRating event) {
        events.add(event);
    }

    /**
     * runEvent.
     * Ejecuta todos los eventos registrados con la puntuación seleccionada.
     */
    private void runEvent() {
        for (EventStarRating event : events) {
            event.selected(star);
        }
    }
}