package UserInterface.Panels;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

/**
 * Represents a custom JScrollPane with the horizontal scrollbar disabled. It also contains a
 * custom scrollbar.
 */
public class CustomVerticalScrollPane extends JScrollPane {
    protected Color defThumbColor = new Color(0,122,217);
    protected Color defTrackColor = new Color(186, 186, 186);

    /**
     * Constructor which loads the pane's properties and scrollbar. It also sets the view.
     * @param view the component being displayed in the scroll pane
     * @param prefSize preferred size
     * @param location location of the pane
     * @param unitIncrement scrollbar unit increment
     */
    public CustomVerticalScrollPane(Component view, Dimension prefSize, Point location, int unitIncrement) {
        super(view);
        loadProperties(prefSize, location);
        loadScrollBar(unitIncrement);
    }

    /**
     * Loads the scroll pane properties.
     * @param prefSize preferred size
     * @param location location of the pane
     */
    private void loadProperties(Dimension prefSize, Point location) {
        setPreferredSize(prefSize);
        setBounds((int)location.getX(), (int)location.getY(), (int)prefSize.getWidth(), (int)prefSize.getHeight());
        setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        setBorder(null);
    }

    /**
     * Loads the custom scrollbar.
     * @param unitIncrement scrollbar unit increment
     */
    private void loadScrollBar(int unitIncrement) {
        JScrollBar verticalScrollBar = getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(unitIncrement);
        verticalScrollBar.setUI(new BasicScrollBarUI() {

            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = defThumbColor;
                this.trackColor = defTrackColor;
            }

            /**
             * Effectively removes the decrease button on the scrollbar by calling the createZeroButton method.
             * @param orientation the orientation
             * @return an invisible JButton
             */
            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }

            /**
             * Effectively removes the increase button on the scrollbar by calling the createZeroButton method.
             * @param orientation the orientation
             * @return an invisible JButton
             */
            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }

            /**
             * Creates a JButton with no size (0 width and 0 height) to simulate a nonexistent button.
             * The button isn't visible or usable.
             * @return the invisible button
             */
            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                return button;
            }

            /**
             * Paints a custom scrollbar thumb.
             * @param g the graphics
             * @param c the component
             * @param thumbBounds the thumb bounds
             */
            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                Graphics2D g2 = (Graphics2D)g.create();
                g2.setPaint(thumbColor);
                g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10);
                g2.dispose();
            }

        });

        verticalScrollBar.addAdjustmentListener(new AdjustmentListener() {
            /**
             * Repaints the target component when the scrollbar value is changed.
             * @param e the event to be processed
             */
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                repaintTarget();
            }
        });
    }

    /**
     * Repaints the target component.
     */
    private void repaintTarget() {
        JComponent targetComponent = (JComponent)getViewport().getView();
        targetComponent.repaint();
    }
}
