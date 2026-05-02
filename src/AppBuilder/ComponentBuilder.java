/**
 * @author Aiken Bouis (CHS Studios)
 */

package AppBuilder;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicSliderUI;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import javax.swing.text.JTextComponent;

/**
 * The ComponentFactory class provides methods to create and manage GUI components such as frames, panels, labels, buttons, and images.
 * Methods in this class use a coordinate system where the origin is at the bottom left corner of the panel. This is different to the coordinate system used in Java's Component class which has its origin at the top left
 */
public class ComponentBuilder {
    private JFrame frame;
    private JPanel panel;

    /**
     * Creates an object for creating components for a given frame or panel. The panel must be added to the frame already
     * @param frame The JFrame to be used
     * @param panel The JPanel to be used
     */
    public ComponentBuilder(JFrame frame, JPanel panel){
        this.frame = frame;
        this.panel = panel;

    }
    public ComponentBuilder() {
        
    }
    /**
     * Creates an object for creating components for a given frame. The panel should be added later and must be added to the frame
     * @param frame The JFrame to be used
     */
    public ComponentBuilder(JFrame frame) {
        this.frame = frame;
        
    }

    /**
     * Sets the frame that the CompopnentBuilder object will use
     * @param frame The JPanel to be used
     */
    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    /**
     * Sets the panel that the ComponentBuilder object will use
     * @param panel The JPanel to be used
     */
    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    /**
     * Gets the x coordinate of the component on a coordinate system where the origin is at the bottom left corner of the panel
     * @param component The component
     * @return The x coordinate of the component. This will be the same as the component's x coordinate in the coordinate system used the Component class
     */
    public int getX(Component component){
        return component.getX();
    }

    /**
     * Gets the y coordinate of the component on a coordinate system where the origin is at the bottom left corner of the panel
     * @param component The component
     * @return The y coordinate of the component. This will be the height of the panel minus the component's y coordinate in the coordinate system used by the Component class
     */
    public int getY(Component component){
        return panel.getHeight() - component.getY();
    }

    /**
     * Sets the location of a component to that of another component. This will only set its location once and will not permanently make it follow the entered component.
     * @param follower The component that will follow the location of another component
     * @param followed The component that the follower will follow
     */
    public void followComponent(Component follower, Component followed){
        follower.setLocation(followed.getX(), followed.getY());
    }

    // #region Creation Methods


    
    /**
     * Paints an image onto a specific component
     * 
     * @param imagePath The path to the image file
     * @param component The omponent to paint the image on
     * @param xPercentage The x coordinate of the image as a percentage of the component's width(0 to 1), with 0 being the left side of the component and 1 being the right side
     * @param yPercentage The y coordinate of the image as a percentage of the component's height(0 to 1), with 0 being the bottom side of the component and 1 being the top side
     * @param widthPercentage The width of the image as a percentage of the component's width (0 to 1)
     * @param heightPercentage The height of the image as a percentage of the component's height (0 to 1)
     * @return The image component as a JComponent
     */
    public JComponent createImage(String imagePath, JComponent component, double xPercentage, double yPercentage, double widthPercentage, double heightPercentage) {
        component.setLayout(null); // Ensure the component can hold other elements

        JComponent imageLayer = new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image image = new ImageIcon(imagePath).getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };

        int initialComponentWidth = component.getWidth();
        int initialComponentHeight = component.getHeight();
        int initialImageX = (int) (xPercentage * initialComponentWidth);
        int initialImageY = (int) ((1 - yPercentage) * initialComponentHeight - (heightPercentage * initialComponentHeight)); // Adjust for bottom-left origin
        int initialImageWidth = (int) (widthPercentage * initialComponentWidth);
        int initialImageHeight = (int) (heightPercentage * initialComponentHeight);

        imageLayer.setBounds(initialImageX, initialImageY, initialImageWidth, initialImageHeight);
        component.add(imageLayer);
        panel.repaint();
        panel.revalidate();

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int componentWidth = component.getWidth();
                int componentHeight = component.getHeight();

                int scaledImageX = (int) (xPercentage * componentWidth);
                int scaledImageY = (int) ((1 - yPercentage) * componentHeight - (heightPercentage * componentHeight));
                int scaledImageWidth = (int) (widthPercentage * componentWidth);
                int scaledImageHeight = (int) (heightPercentage * componentHeight);

                imageLayer.setBounds(scaledImageX, scaledImageY, scaledImageWidth, scaledImageHeight);
            }
        });

        return imageLayer;
    }


    private void autoScale(Component component, int x, int y, int width, int height) {
        int initialPanelWidth = panel.getWidth();
        int initialPanelHeight = panel.getHeight();
        
        final float[] originalFontSize = {0};
        final JLabel[] label = {null}; //This is for if a label is being scaled
        if (component instanceof JLabel) {
            label[0]= (JLabel) component;
            originalFontSize[0] = label[0].getFont().getSize2D();
            
        }
    
        // Track the last known position and size
        final int[] lastX = {x};
        final int[] lastY = {y};
        final int[] lastWidth = {width};
        final int[] lastHeight = {height};
        final int[] displacementX = {0};
        final int[] displacementY = {0};
        final int[] displacementWidth = {0};
        final int[] displacementHeight = {0};
    
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                SwingUtilities.invokeLater(()-> {

                    int panelWidth = panel.getWidth();
                    int panelHeight = panel.getHeight();
    
                    // Calculate scaling ratios
                    double ratioX = (double) panelWidth / initialPanelWidth;
                    double ratioY = (double) panelHeight / initialPanelHeight;
    
                    // Calculate displacement
                    int deltaX = component.getX() - lastX[0];
                    int deltaY = component.getY() - lastY[0];
                    int deltaWidth = component.getWidth() - lastWidth[0];
                    int deltaHeight = component.getHeight() - lastHeight[0];
    
                    displacementX[0] += deltaX;
                    displacementY[0] += deltaY;
                    displacementWidth[0] += deltaWidth;
                    displacementHeight[0] += deltaHeight;
    
                    // Calculate new position and size
                    int scaledX = (int) Math.round((x + displacementX[0]) * ratioX);
                    int scaledY = (int) Math.round((y + displacementY[0]) * ratioY);
                    int scaledWidth = (int) Math.round((width + displacementWidth[0]) * ratioX);
                    int scaledHeight = (int) Math.round((height + displacementHeight[0]) * ratioY);
    
                    // Update component bounds
                    component.setBounds(scaledX, scaledY, scaledWidth, scaledHeight);
    
                    // Update last known position and size
                    lastX[0] = component.getX();
                    lastY[0] = component.getY();
                    lastWidth[0] = component.getWidth();
                    lastHeight[0] = component.getHeight();
                    panel.repaint();
                    panel.revalidate();
                
                    // Scale font size if the component is a JLabel
                    if (component instanceof JLabel) {
                        JLabel label = (JLabel) component;

                        // Calculate diagonal scaling factor using Pythagorean theorem
                        double diagonalScalingFactor = Math.sqrt(ratioX * ratioX + ratioY * ratioY);

                        // Scale the font size
                        float scaledFontSize = (float) (originalFontSize[0] * diagonalScalingFactor);
                        label.setFont(label.getFont().deriveFont(scaledFontSize));
                    }

                });
                
            }
        });
    }
    /**
     * Creates an image component onto the panel with given coordinates and dimensions
     * Note: The coordinates for the image refer to the bottom left corner of the image
     * @param imagePath The path to the image file
     * @param x The x coordinate of the image
     * @param y The y coordinate of the image
     * @param width The width of the image
     * @param height The height of the image
     * @return The image component as a JComponent
     */
    public JComponent createImage(String imagePath, int x, int y, int width, int height) {
        int adjustedY = panel.getHeight() - y - height; // Adjust y-coordinate
        JComponent imageComponent = new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image image = new ImageIcon(imagePath).getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };

        imageComponent.setBounds(x, adjustedY, width, height);
        panel.add(imageComponent);

        int initialPanelWidth = panel.getWidth();
        int initialPanelHeight = panel.getHeight();
        autoScale(imageComponent, x, y, initialPanelWidth, initialPanelHeight);
        
        return imageComponent;
    }

    /**
     * Creates a JFrame with a specified title, width, height, and visibility.
     * @param title The title of the frame
     * @param width The width of the frame
     * @param height The height of the frame
     * @param visible Whether the frame should be visible upon creation
     * @return a JFrame object
     */
    public JFrame createFrame(String title, int width, int height , boolean visible){
        JFrame newFrame = new JFrame(title);
        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newFrame.setSize(width, height);

        if (visible){
            newFrame.setVisible(true);
        } else {
            newFrame.setVisible(false);
        }
        return newFrame;
    }
    public JFrame getFrame() {
        return frame;
    }
    public JPanel getPanel() {
        return panel;
    }
    /**
     * Creates a label that is centered in the panel at all times
     * @param text The text to display in the label
     * @param width The width of the label
     * @param height The height of the label
     * @return a JLabel object
     */
    public JLabel createLabel(String text, int width, int height) {
        JLabel label = new JLabel(text);
        label.setSize(width, height);
        panel.add(label);
        centerComponent(label);

        
        frame.addComponentListener(new ComponentAdapter(){
            @Override
            public void componentResized(ComponentEvent e) {
                centerComponent(label);
            }
        });
    
        return label;
    }

    /**
     * Creates a label with custom coordinates. The label will automatically scale when the window size is adjusted.
     * Note: The coordinates for the label refer to the bottom left corner of the label
     * @param text The text to display in the label
     * @param x The x coordinate of the label
     * @param y The y coordinate of the label
     * @param width The width of the label
     * @param height The height of the label
     * @return a JLabel object
     */
    public JLabel createLabel(String text, int x, int y, int width, int height) {
        int adjustedY = panel.getHeight() - y - height; // Adjust y-coordinate
        JLabel label = new JLabel(text);
        label.setBounds(x, adjustedY, width, height);
        panel.add(label);

        
        int initialPanelWidth = panel.getWidth();
        int initialPanelHeight = panel.getHeight();
        autoScale(label, x, y, initialPanelWidth, initialPanelHeight);
        
        return label;
    }

     /**
     * Creates a button that is centered in the panel. The button will automatically scale when the window size is adjusted.
     * @param text The text to display in the button
     * @param width The width of the button
     * @param height The height of the button
     * @return a JButton object
     */
    public JButton createButton(String text, int width, int height){
        JButton button = new JButton(text);
        button.setSize(width, height);
        panel.add(button);
        centerComponent(button);

        frame.addComponentListener(new ComponentAdapter(){
            @Override
            public void componentResized(ComponentEvent e) {
                centerComponent(button);
            }
        });
    
        return button;
    }

    /**
     * Creates a button with custom coordinates. The button will automaticall scale when the window is adjusted
     * Note: The coordinates for the button refer to the bottom left corner of the button
     * @param text The text to display in the button
     * @param x The x coordinate of the button
     * @param y The y coordinate of the button
     * @param width The width of the button
     * @param height The height of the button
     * @return a JButton object
     */
    public JButton createButton(String text, int x, int y, int width, int height) {
        int adjustedY = panel.getHeight() - y - height; // Adjust y-coordinate
        JButton button = new JButton(text);
        button.setBounds(x, adjustedY, width, height);
        panel.add(button);
        int initialPanelWidth = panel.getWidth();
        int initialPanelHeight = panel.getHeight();
        autoScale(button, x, y, initialPanelWidth, initialPanelHeight);

        
        return button;
    }


    /**
    * Creates a JTextArea with customizable font, dimensions, and optional scrollbars.
    * @param text The initial text to display in the text area. The text inside can be modified later.
    * @param x The x-coordinate of the text area
    * @param y The y-coordinate of the text area
    * @param width The width of the text area
    * @param height The height of the text area
    * @return The created JTextArea or JScrollPane (if scrollable is true)
    */
    public JTextArea createTextArea(String text, int x, int y, int width, int height) {
        JTextArea textArea = new JTextArea(text);
        
        textArea.setLineWrap(true); // Enable or disable line wrapping
        textArea.setWrapStyleWord(true); // Wrap at word boundaries if wrapping is enabled
        textArea.setBounds(x, panel.getHeight() - y - height, width, height); // Adjust y-coordinate
        panel.add(textArea);
        panel.revalidate();
        panel.repaint();
        int initialPanelWidth = panel.getWidth();
        int initialPanelHeight = panel.getHeight();
        autoScale(textArea, x, y, initialPanelWidth, initialPanelHeight);

        return textArea; // Return the plain text area
    }

    /**
    * Creates a scrollable JTextArea with customizable font and dimensions.
    * @param x The x-coordinate of the text area
    * @param y The y-coordinate of the text area
    * @param width The width of the text area
    * @param height The height of the text area
    * @param component The component to be added to the JScrollPane
    * @return The JScrollPane containing the JTextArea
    */
    public JScrollPane createScrollableArea(int x, int y, int width, int height, Component component) {
        int adjustedY = panel.getHeight() - y - height; // Adjust y-coordinate
        
        JScrollPane scrollPane = new JScrollPane(component);
        scrollPane.setBounds(x, adjustedY, width, height);
        panel.add(scrollPane);
        panel.revalidate();
        panel.repaint();
        int initialPanelWidth = panel.getWidth();
        int initialPanelHeight = panel.getHeight();
        autoScale(scrollPane, x, y, initialPanelWidth, initialPanelHeight);

        return scrollPane;
    }

    /**
    * Creates a JSlider with customizable range, orientation, and tick marks.
    * @param min The minimum value of the slider
    * @param max The maximum value of the slider
    * @param x The x-coordinate of the slider
    * @param y The y-coordinate of the slider
    * @param width The width of the slider
    * @param height The height of the slider
    * @return The created JSlider
    */
    public JSlider createVerticalSlider(int min, int max, int x, int y, int width, int height) {
        JSlider slider = new JSlider(SwingConstants.VERTICAL, min, max, 0);
        slider.setBounds(x, y, width, height);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        panel.add(slider);
        panel.revalidate();
        panel.repaint();

        int initialPanelWidth = panel.getWidth();
        int initialPanelHeight = panel.getHeight();
        autoScale(slider, x, y, initialPanelWidth, initialPanelHeight);
        return slider;
    }

    /**
    * Creates a JSlider with customizable range, orientation, and tick marks.
    * @param min The minimum value of the slider
    * @param max The maximum value of the slider
    * @param x The x-coordinate of the slider
    * @param y The y-coordinate of the slider
    * @param width The width of the slider
    * @param height The height of the slider
    * @return The created JSlider
    */
    public JSlider createHorizontalSlider(int min, int max, int x, int y, int width, int height) {
        JSlider slider = new JSlider(SwingConstants.HORIZONTAL, min, max, 0);
        slider.setBounds(x, y, width, height);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        panel.add(slider);
        panel.revalidate();
        panel.repaint();

        int initialPanelWidth = panel.getWidth();
        int initialPanelHeight = panel.getHeight();
        autoScale(slider, x, y, initialPanelWidth, initialPanelHeight);
        return slider;
    }

    // #region Optimization Methods

    /**
     * Centers a given component within the panel.
     * @param component The component to be centered
     */
    public void centerComponent(JComponent component) {
        int width = component.getWidth();
        int height = component.getHeight();
        int panelWidth = panel.getWidth();
        int panelHeight = panel.getHeight();
        component.setBounds((panelWidth - width) / 2, (panelHeight - height) / 2, width, height);
    }

    /**
     * Scales the coordinates of a component based on the panel's current size.
     * @param component The component to scale
     * @param x The x coordinate in percentage (0-100) out of the panel's width
     * @param y The y coordinate in percentage (0-100) out of the panel's height
     */
    public void scaleComponentCoordinates(JComponent component, int x,int y){
        int panelWidth = panel.getWidth();
        int panelHeight = panel.getHeight();

        int scaledX = (int) ((x / 100.0) * panelWidth);
        int scaledY = (int) ((panelHeight - (y / 100.0) * panelHeight) - component.getHeight());
        component.setBounds(scaledX, scaledY, component.getWidth(), component.getHeight());
    }


    /**
     * Customizes the appearance of a JComponent.
     * @param c The component to customize
     * @param bgColor The background color to set
     * @param fgColor The foreground color to set
     * @param font The font to set
     */
    public void customizeComponent(JComponent c, Color bgColor, Color fgColor, Font font) {
            
        if (bgColor != null) {
            c.setBackground(bgColor);
        }
        if (fgColor != null) {
            c.setForeground(fgColor);
        }
        if (font != null) {
            c.setFont(font);
        }
        
    }

    /**
     * Sets the icon of the JFrame window.
     * @param imagePath The path to the image file to be used as the icon
     */
    public void setWindowIcon(String imagePath){
        try {
            // Load the image from the provided file path
            Image icon = new ImageIcon(imagePath).getImage();
    
            // Set the image as the icon for the JFrame
            frame.setIconImage(icon);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error setting window icon: " + ex.getMessage());
        }

    }

    /**
     * Enables or disables text editing in a JTextComponent object (ex: JTextArea, JTextField).
     * @param textComponent The JTextComponent object
     * @param editable true to enable text editing, false to disable it
     */
    public void enableTextEditing(JTextComponent textComponent, boolean editable) {
        textComponent.setEditable(editable);
    }

    /**
    * Clears all text from the JTextComponent
     * @param textComponent The JTextComponent to clear
    */
    public void clearText(JTextComponent textComponent) {
        textComponent.setText("");
    }

    /**
    * Retrieves the text from the JTextComponent
    * @param textComponent The JTextComponent to retrieve the text from
    * @return The current text in the JTextComponent
    */
    public String getText(JTextComponent textComponent) {
        return textComponent.getText();
    }

    /**
    * Enables or disables line wrapping in the JTextArea.
    * @param textArea The JTextArea to modify
    * @param wrap true to enable line wrapping, false to disable it
    */
    public void setLineWrapping(JTextArea textArea, boolean wrap) {
        textArea.setLineWrap(wrap);
        textArea.setWrapStyleWord(wrap); // Wrap at word boundaries if wrapping is enabled
    }

    /**
     * Sets the text of a JTextComponent (ex: JTextArea, JTextField).
     * @param textComponent The JTextComponent object to set the text for
     * @param text The text to set in the JTextComponent
     */
    public void setText(JTextComponent textComponent, String text) {
        textComponent.setText(text);
    }

    /**
    * Enables or disables focus on the component. This includes labels, buttons, text areas, etc.
    * @param component The component to modify
    * @param focusable true to enable focus, false to disable it
    */
    public void setFocusable(Component component, boolean focusable) {
        component.setFocusable(focusable);
    }

    /**
    * Limits the maximum number of characters in the JTextArea.
    * @param textArea The JTextArea to modify
    * @param maxLength The maximum number of characters allowed
     */
    public void setTextAreaLimit(JTextArea textArea, int maxLength) {
        AbstractDocument doc = (AbstractDocument) textArea.getDocument();
        doc.setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, javax.swing.text.AttributeSet attrs) throws javax.swing.text.BadLocationException {
                if (fb.getDocument().getLength() + text.length() - length <= maxLength) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
    }

    /**
    * Sets the major and minor tick spacing for the JSlider.
    * @param slider The JSlider to modify
    * @param majorTickSpacing The spacing between major ticks
    * @param minorTickSpacing The spacing between minor ticks
    */
    public void setTickSpacing(JSlider slider, int majorTickSpacing, int minorTickSpacing) {
        slider.setMajorTickSpacing(majorTickSpacing);
        slider.setMinorTickSpacing(minorTickSpacing);
        slider.setPaintTicks(true); // Ensure ticks are visible
    }

    /**
    * Retrieves the current value of the JSlider.
    * @param slider The JSlider to query
    * @return The current value of the slider
    */
    public int getSliderValue(JSlider slider) {
        return slider.getValue();
    }

    /**
    * Sets the value of the JSlider.
    * @param slider The JSlider to modify
    * @param value The value to set
    */
    public void setSliderValue(JSlider slider, int value) {
        slider.setValue(value);
    }

    /**
    * Enables or disables the JSlider.
     * @param slider The JSlider to modify
    * @param enabled true to enable the slider, false to disable it
    */
    public void setSliderEnabled(JSlider slider, boolean enabled) {
        slider.setEnabled(enabled);
    }

    /**
    * Enables or disables snapping to ticks for the JSlider.
     * @param slider The JSlider to modify
    * @param snapToTicks true to enable snapping, false to disable it
    */
    public void setSnapToTicks(JSlider slider, boolean snapToTicks) {
        slider.setSnapToTicks(snapToTicks);
    }

    /**
     * Resets the JSlider to its default value (usually the minimum value).
    * @param slider The JSlider to reset
    */
    public void resetSlider(JSlider slider) {
        slider.setValue(slider.getMinimum());
    }

    /**
    * Adds a listener to the JSlider that runs the inputted Runnable whenever the slider's value changes
    * @param slider The JSlider to attach the listener to
    * @param onValueChange A Runnable to execute when the slider's value changes. You can use a lambda expression or method reference to pass this in.
    */
    public void addSliderValueReactor(JSlider slider, Runnable onValueChange) {
        slider.addChangeListener(e -> {
            onValueChange.run();
        });
    }

    //TODO: FIX THIS METHOD SO IT WORKS
    
    /**
    * Sets the color of the slider's track.
    * @param slider The JSlider to modify
    * @param color The color to set for the track
    */
    private void setSliderTrackColor(JSlider slider, Color color) {
        slider.setUI(new BasicSliderUI(slider) {
            @Override
            public void paintTrack(Graphics g) {

                super.paintTrack(g); // Call the superclass method to draw the default track
                g.setColor(color);
                g.fillRect(trackRect.x, trackRect.y, trackRect.width, trackRect.height); // Fill the track
                
            }
        });
        slider.repaint();
        slider.revalidate();
    }

    /**
    * Sets the color of the slider's thumb.
    * This will also turn the slider thumb into a rectangle so its shape will look a bit different
     * @param slider The JSlider to modify
    * @param color The color to set for the thumb
    */
    public void setSliderThumbColor(JSlider slider, Color color) {
        slider.setUI(new BasicSliderUI(slider) {
            @Override
            public void paintThumb(Graphics g) {
                super.paintThumb(g);
                g.setColor(color);
                g.fillRect(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height); // Fill the thumb
                
                
            }
        });
        slider.repaint();
        slider.revalidate();
    }

    /**
    * Adds a label to a specific value on the JSlider.
    * @param slider The JSlider to modify
    * @param value The value on the slider where the label should be added
    * @param label The text of the label to add
    */
    public void addSliderLabel(JSlider slider, int value, String label) {
        // Get the current label table or create a new one if it doesn't exist
        Hashtable<Integer, JLabel> labelTable = (Hashtable<Integer, JLabel>) slider.getLabelTable();
        if (labelTable == null) {
            labelTable = new Hashtable<>();
        }

        // Add the new label at the specified value
        labelTable.put(value, new JLabel(label));

        // Set the updated label table 
        slider.setLabelTable(labelTable);
    }





    

}
