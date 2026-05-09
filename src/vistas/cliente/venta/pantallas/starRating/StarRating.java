package vistas.cliente.venta.pantallas.starRating;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

/**
 * Tipo: Class StarRating.
 */
public class StarRating extends javax.swing.JPanel {
	
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Campo star1. */
	private Star star1;
	
	/** Campo star2. */
	private Star star2;
	
	/** Campo star3. */
	private Star star3;
	
	/** Campo star4. */
	private Star star4;
	
	/** Campo star5. */
	private Star star5;
	
	/** Campo events. */
	private List<EventStarRating> events = new ArrayList<>();
    
    /** Campo star. */
    private int star;

    /**
     * Obtiene Star.
     *
     * @return valor de Star
     */
    public int getStar() {
        return star;
    }

    /**
     * Establece Star.
     *
     * @param star nuevo valor
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
     * init.
     */
    private void init() {
        setOpaque(false);
        setBackground(new Color(204, 204, 204));
        setForeground(new Color(238, 236, 0));
    }

    /**
     * initComponents.
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
    }// </editor-fold>//GEN-END:initComponents

    /**
     * star1ActionPerformed.
     *
     * @param evt parámetro evt
     */
    private void star1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_star1ActionPerformed
        star1.setSelected(true);
        star2.setSelected(false);
        star3.setSelected(false);
        star4.setSelected(false);
        star5.setSelected(false);
        star = 1;
        runEvent();
    }//GEN-LAST:event_star1ActionPerformed

    /**
     * star2ActionPerformed.
     *
     * @param evt parámetro evt
     */
    private void star2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_star2ActionPerformed
        star1.setSelected(true);
        star2.setSelected(true);
        star3.setSelected(false);
        star4.setSelected(false);
        star5.setSelected(false);
        star = 2;
        runEvent();
    }//GEN-LAST:event_star2ActionPerformed

    /**
     * star3ActionPerformed.
     *
     * @param evt parámetro evt
     */
    private void star3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_star3ActionPerformed
        star1.setSelected(true);
        star2.setSelected(true);
        star3.setSelected(true);
        star4.setSelected(false);
        star5.setSelected(false);
        star = 3;
        runEvent();
    }//GEN-LAST:event_star3ActionPerformed

    /**
     * star4ActionPerformed.
     *
     * @param evt parámetro evt
     */
    private void star4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_star4ActionPerformed
        star1.setSelected(true);
        star2.setSelected(true);
        star3.setSelected(true);
        star4.setSelected(true);
        star5.setSelected(false);
        star = 4;
        runEvent();
    }//GEN-LAST:event_star4ActionPerformed

    /**
     * star5ActionPerformed.
     *
     * @param evt parámetro evt
     */
    private void star5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_star5ActionPerformed
        star1.setSelected(true);
        star2.setSelected(true);
        star3.setSelected(true);
        star4.setSelected(true);
        star5.setSelected(true);
        star = 5;
        runEvent();
    }//GEN-LAST:event_star5ActionPerformed

    /**
     * Establece Background.
     *
     * @param color nuevo valor
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
     * @param color nuevo valor
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
     * @param event parámetro event
     */
    public void addEventStarRating(EventStarRating event) {
        events.add(event);
    }

    /**
     * runEvent.
     */
    private void runEvent() {
        for (EventStarRating event : events) {
            event.selected(star);
        }
    }
}
