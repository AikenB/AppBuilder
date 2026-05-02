/**
 * @author Aiken Bouis (CHS Studios)
 */

package AppBuilder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;



/**
 * The Interactor class provides methods to add and remove actions for inputs while the window is active
 */
public class Interactor {


    /**
     * Adds an action to a button that will be performed when the button is clicked. In order to replace the action that is performed (like changing a button binding), just call this method and put in the new action
     * @param button The button that the action is attached to.
     * @param action The runnable that will be called when the button is clicked. You can use a lambda expression or method reference to pass this in.
     */
    public static void addButtonAction(AbstractButton button, Runnable action) {
        button.addActionListener(e -> action.run());
    }

    /**
     * Removes all actions from a button.
     * @param button The button from which to remove all actions.
     */
    public static void removeButtonAction(AbstractButton button) {
        // Remove all ActionListeners from the button
        for (ActionListener listener : button.getActionListeners()) {
            button.removeActionListener(listener);
        }
    }

   /**
    * Adds an action to be performed when a certain key is pressed. In order to replace the action that is performed (like changing a button binding), just call this method and put in the new action
    * @param component The component that the action is attached to. This makes it so that whenever the window that this component is in is active, the action will be performed when the key is pressed.
    * @param keyCode The key code representing the key that will trigger the action. See https://docs.oracle.com/javase/8/docs/api/java/awt/event/KeyEvent.html to find the keycode for any key
    * @param onPress The runnable that will be called when the key is pressed. You can use a lambda expression or method reference to pass this in.
    */
    public static void addKeyAction(JComponent component, int keyCode, Runnable onPress) {
    
        // Use Key Bindings with WHEN_IN_FOCUSED_WINDOW to ensure the action works when the window is active
        InputMap inputMap = component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = component.getActionMap();

        // Generate a unique actionMapKey based on the keyCode
        String actionMapKey = "keyPressed_" + KeyEvent.getKeyText(keyCode);

        // Map the key press action
        inputMap.put(KeyStroke.getKeyStroke(keyCode, 0, false), actionMapKey);
        actionMap.put(actionMapKey, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onPress.run(); // Run the action for key press
            }
        });
    }

    /**
     * Removes the action associated with a specific key code from a component.
     * 
     *
     * @param component The component from which the key action will be removed.
     * @param keyCode   The key code representing the key whose action is to be removed. See https://docs.oracle.com/javase/8/docs/api/java/awt/event/KeyEvent.html to find the keycode for any key
     */
    public static void removeKeyAction(JComponent component, int keyCode) {
        // Get the InputMap and ActionMap for the component
        InputMap inputMap = component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = component.getActionMap();
    
        // Generate the same unique actionMapKey based on the keyCode
        String actionMapKey = "keyPressed_" + KeyEvent.getKeyText(keyCode);
    
        // Remove the key binding from the InputMap and ActionMap
        inputMap.remove(KeyStroke.getKeyStroke(keyCode, 0, false));
        actionMap.remove(actionMapKey);
    }

    /**
     * Adds an action to be performed when a certain key is released. In order to replace the action that is performed (like changing a button binding), just call this method and put in the new action
     * @param component The component that the action is attached to. This makes it so that whenever the window that this component is in is active, the action will be performed when the key is released.
     * @param keyCode The key code representing the key that will trigger the action. See https://docs.oracle.com/javase/8/docs/api/java/awt/event/KeyEvent.html to find the keycode for any key
     * @param onRelease The runnable that will be called when the key is released. You can use a lambda expression or method reference to pass this in.
     */
    public static void addKeyReleaseAction(JComponent component, int keyCode, Runnable onRelease) {
        // Use Key Bindings with WHEN_IN_FOCUSED_WINDOW to ensure the action works when the window is active
        InputMap inputMap = component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = component.getActionMap();
    
        // Generate a unique actionMapKey based on the keyCode
        String actionMapKey = "keyReleased_" + KeyEvent.getKeyText(keyCode);
    
        // Map the key release action
        inputMap.put(KeyStroke.getKeyStroke(keyCode, 0, true), actionMapKey); 
        actionMap.put(actionMapKey, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onRelease.run(); // Run the action for key release
            }
        });
    }

    /**
     * Removes the action associated with a specific key code when it is released from a component.
     * 
     * @param component The component from which the key release action will be removed.
     * @param keyCode   The key code representing the key whose release action is to be removed. See https://docs.oracle.com/javase/8/docs/api/java/awt/event/KeyEvent.html to find the keycode for any key
     */
    public static void removeKeyReleaseAction(JComponent component, int keyCode) {
        // Use Key Bindings with WHEN_IN_FOCUSED_WINDOW to ensure the action works when the window is active
        InputMap inputMap = component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = component.getActionMap();
    
        // Generate a unique actionMapKey based on the keyCode
        String actionMapKey = "keyReleased_" + KeyEvent.getKeyText(keyCode);
    
        // Remove the key binding from the InputMap and ActionMap
        inputMap.remove(KeyStroke.getKeyStroke(keyCode, 0, true));
        actionMap.remove(actionMapKey);
    }

    private static final Map<Integer, Thread> activeThreads = new HashMap<>();


    /**
     * Adds an action to be performed continuously while a certain key is held down, and stops the action when the key is released.
     * @param component The component that the action is attached to.
     * @param keyCode The key code representing the key that will trigger the action. See https://docs.oracle.com/javase/8/docs/api/java/awt/event/KeyEvent.html to find the keycode for any key
     * @param onHold The runnable that will be called repeatedly while the key is held down. You can use a lambda expression or method reference to pass this in.
     * @param onRelease The runnable that will be called when the key is released. You can use a lambda expression or method reference to pass this in.
     */

    public static void addKeyHoldAction(JComponent component, int keyCode, Runnable onHold, Runnable onRelease) {
        // Add key press action to start the loop
        addKeyAction(component, keyCode, () -> {
            if (!activeThreads.containsKey(keyCode)) {
                // Create a new thread to repeatedly execute the action
                Thread thread = new Thread(() -> {
                    while (activeThreads.containsKey(keyCode)) {
                        onHold.run(); // Execute the action
                        try {
                            Thread.sleep(10); // Add a small delay to prevent excessive CPU usage
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            break;
                        }
                    }
                });
                activeThreads.put(keyCode, thread);
                thread.start();
            }
        });
    
        // Add key release action to stop the loop
        addKeyReleaseAction(component, keyCode, () -> {
            Thread thread = activeThreads.remove(keyCode);
            if (thread != null) {
                thread.interrupt(); // Stop the thread
            }
            onRelease.run(); // Execute the release action
        });
    }
}




