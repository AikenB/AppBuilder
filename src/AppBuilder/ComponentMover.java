package AppBuilder;
import java.awt.Component;
import javax.swing.SwingUtilities;

/**
     * ComponentMover is a class that allows for easy and smooth movement of components within a GUI application.
     * Since the Component class doesn't have a method for moving the component, this class provides a way to easily move a component across the X and Y axis simultaneously.
     */
public class ComponentMover {

    private Component component;
    private int xChange = 0;
    private int yChange = 0;
    
    private int xDisplacement = 0;
    private int yDisplacement = 0;
    private boolean enabled = false;
    private boolean clamped = false;
    private int limitWidth;
    private int limitHeight;

    private int getXDisplacement() {
        return xDisplacement;
    }
    private int getYDisplacement() {
        return yDisplacement;
    }

    /**
     * Creates a componentMover for the specified component. When the ComponentMover is created, it will automatically start the movement system for the component.
     * Keep in mind that when moving the component with this class, the positive y axis is upwards, which is opposite to that of the Component class.
     * @param component
     */
    public ComponentMover(Component component){
        this.component = component;
        limitWidth = component.getWidth(); // Set the default width limit for clamping
        limitHeight = component.getHeight(); // Set the default height limit for clamping
        // xDisplacement = component.getX();
        // yDisplacement = component.getY();
        enableComponentMovementSystem();
        
    }

    /**
     * Enables or disables the boundary limits for the component movement. When enabled, the component's position will be clamped within the boundaries of the parent container (ex: a panel)
     * @param enabled true to enable boundary limits, false to disable them. When enabled, the component's position will be clamped within the boundaries of the parent container (ex: a panel).
     * @param width This is the width that will be treated as the component's width. This parameter is decided by the user so they can limit the motion of the component 
     * @param height This is the height that will be treated as the component's height. This parameter is decided by the user so they can limit the motion of the component
     */
    public void enableBoundaryLimits(boolean enabled, int width, int height){
        limitWidth = width; // Set the width limit for clamping
        limitHeight = height; // Set the height limit for clamping
        clamped = enabled; // This will enable or disable the clamping of the component's position within the boundaries of the parent container.
    }

    private int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
    
    //The thread that will be run for allowing the component to move. This will keep the component at the same position until the xchange or ychange values are modified to make it move
    Thread movementThread = new Thread(() -> {
        while (enabled == true) {
            
            
            SwingUtilities.invokeLater(()->{
                int newX = component.getX() + xDisplacement;
                int newY = component.getY() - yDisplacement;
                if (clamped){
                    // Clamp the new position within the boundaries of the parent container
                    Component parent = component.getParent();
                    if (parent != null) {
                        int minX = 0;
                        int minY = 0;
                        int maxX = parent.getWidth() - limitWidth;
                        int maxY = parent.getHeight() - limitHeight;
                        newX = clamp(newX, minX, maxX);
                        newY = clamp(newY, minY, maxY);
                    }
                }
                component.setLocation(newX, newY);
                xDisplacement = 0; // Reset xDisplacement after moving
                yDisplacement = 0; // Reset yDisplacement after moving
        });
            
            
            // moveOnce = false; // Reset moveOnce after moving
            // xChange = 0; // Reset xChange after moving
            // yChange = 0; // Reset yChange after moving
            
            
            try {
                Thread.sleep(10); // Add a small delay to prevent excessive CPU usage
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    });

    /**
     * Enables the component movement system, allowing the component to be moved by changing the xChange and yChange values.
     */
    public void enableComponentMovementSystem() {
        enabled = true;
        movementThread.start();
        
    }
    /**
     * Moves the component by the specified x and y values. This will change the position of the component by the specified amount.
     * @param x the amount to move the component in the X direction
     * @param y the amount to move the component in the Y direction
     */
    public void moveComponent(int x, int y) {
        // xChange = x;
        // yChange = y;
        // moveOnce = true;
        xDisplacement += x;
        yDisplacement += y;
    }

    /**
     * Moves the component by the specified x value. This will change the position of the component by the specified amount in the X direction.
     * @param x the amount to move the component in the X direction
     */
    public void moveComponent_X(int x) {
        // xChange = x;
        // moveOnce = true;
        xDisplacement += x;
        
    }

    /**
     * Moves the component by the specified y value. This will change the position of the component by the specified amount in the Y direction.
     * @param y the amount to move the component in the Y direction
     */
    public void moveComponent_Y(int y) {
        // yChange = y;
        // moveOnce = true;
        yDisplacement += y;
    }
    /**
     * disables the component movement system. The system can be re-enabled by calling enableComponentMovementSystem().
     */
    public void stopComponentMovement() {
        enabled = false;
        movementThread.interrupt(); // Stop the movement thread
    }
}
