package AppBuilder;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Cursor;

import javax.sound.sampled.*;
import javax.swing.JOptionPane;
import java.io.IOException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The DesktopController class provides methods to interact with the computer itself, such as opening files, URLs, and playing sounds. 
 * It also includes methods for simulating keyboard and mouse actions. 
 */
public class DesktopController {
    Component component;
    Robot robot; 
    private static ExecutorService executor = Executors.newSingleThreadExecutor();

    
    /**
     * Resets the executor service to a new single-thread executor. This will clear the queue of tasks in the executor
     * IF YOUR CODE INVOLVES AUTOMATION WITH YOUR COMPUTER THEN RUN THIS METHOD BEFORE OR BY THE TIME YOUR PROGRAM ENDS. THIS WILL PREVENT YOUR COMPUTER TO CONTINUE PERFORMING ACTIONS AFTER YOUR PROGRAM ENDS
     */
    public void resetExecutor() {
        executor.submit(() -> {
            executor.shutdown(); // Shut down the current executor
            executor = Executors.newSingleThreadExecutor(); // Create a new executor
        });
    }

    /**
     * Creates a DesktopController object with a given component
     * @param component The component to be used for displaying messages from the object. This just makes messages from the object to be positioned relative the component.
     */
    public DesktopController(Component component) {
        this.component = component;

        try {
            this.robot = new Robot(); // Initialize Robot here
        } catch (AWTException ex) {
            ex.printStackTrace();
            
        }
        
       
    }
    public DesktopController() {
        component = null;

        try {
            this.robot = new Robot(); // Initialize Robot here
        } catch (AWTException ex) {
            ex.printStackTrace();
            
        }
    }

    // #region Desktop/File Methods

    /**
     * Opens a URL in the default web browser.
     * @param url The URL to open
     */
    public void openURL(String url){
        try {
                // URL to open
                URI uri = new URI(url);
        
                // Open the URL in the default browser
                if (Desktop.isDesktopSupported()) {
                    Desktop desktop = Desktop.getDesktop();
                    desktop.browse(uri);
                } else {
                    JOptionPane.showMessageDialog(component, "Desktop not supported");
                    
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(component, "URL cannot be accessed");
            }
    }

    /**
     * Performs a Google search with the given input and opens the results in the default web browser.
     * @param input The input to be searched on google (ex: Java programming tutorial)
     */

    public void google(String input) {
    try {
        // Encode the query for use in a URL
        String encodedQuery = URLEncoder.encode(input, StandardCharsets.UTF_8.toString());

        // Construct the search URL (Google search in this case)
        String searchURL = "https://www.google.com/search?q=" + encodedQuery;

        // Open the search URL in the default browser
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            desktop.browse(new URI(searchURL));
            
        } else {
            JOptionPane.showMessageDialog(component, "Desktop is not supported on this system.");
        }
    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(component, "Error performing search: " + ex.getMessage());
    }
}
    /**
     * Opens a file using the default application associated with the file type.
     * @param filePath The path to the file to be opened.
     */
    public void openFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                if (Desktop.isDesktopSupported()) {
                    Desktop desktop = Desktop.getDesktop();
                    desktop.open(file);
                } else {
                    JOptionPane.showMessageDialog(component, "Desktop not supported");
                }
            } else {
                JOptionPane.showMessageDialog(component, "File does not exist: " + filePath);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(component, "Error opening file: " + ex.getMessage());
        }
    }
    /**
     * Edits a given file using the default application associated with the file type.
     * @param filePath The path to the file to be edited.
     */
    public void editFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists() && file.canWrite()) {
                if (Desktop.isDesktopSupported()) {
                    Desktop desktop = Desktop.getDesktop();
                    desktop.edit(file);
                } else {
                    JOptionPane.showMessageDialog(component, "Desktop not supported");
                }
            } else {
                JOptionPane.showMessageDialog(component, "File does not exist or cannot be edited: " + filePath);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(component, "Error opening file: " + ex.getMessage());
        }
    }  
    
    /**
     * Plays a sound from the specified file path. This only supports .wav files and will not work with other audio formats.
     * @param filePath The path to the sound file to be played. This should be a .wav file.
     */
    public void playSound(String filePath) {
        try {
            // Create a File object for the sound file
            File soundFile = new File(filePath);

            // Check if the file exists
            if (!soundFile.exists()) {
                JOptionPane.showMessageDialog(component, "Sound file does not exist: " + filePath);
                return;
            }
            
            // Get an audio input stream from the file
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            // Get a sound clip resource
            Clip clip = AudioSystem.getClip();
            // Open the audio stream in the clip
            clip.open(audioStream);
            // Play the sound
            clip.start();

            // Optional: Wait for the clip to finish playing
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                }
            });

        } catch (UnsupportedAudioFileException ex) {
            JOptionPane.showMessageDialog(component, "Unsupported audio file: " + ex.getMessage());
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            JOptionPane.showMessageDialog(component, "Audio line unavailable: " + ex.getMessage());
            ex.printStackTrace();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(component, "Error reading audio file: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Copies the given text to the system clipboard.
     * @param text The text to be copied to the clipboard.
     */
    public void copyToClipboard(String text) {
        try { //gets the system's clipboard and puts the text inside
            Toolkit.getDefaultToolkit()
                   .getSystemClipboard()
                   .setContents(new StringSelection(text), null);
            
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(component, "Error copying to clipboard: " + ex.getMessage());
        }
    }

    // #region Mouse Methods

    /**
     * Resets the cursor of the given JFrame to the default cursor.
     * @param frame The JFrame whose cursor is to be reset.
     */
    public void resetToDefaultCursor(JFrame frame){
        frame.setCursor(Cursor.getDefaultCursor());
    }

    /**
     * Creates a custom cursor from an image file. This will create a Cursor object which can be used to set the cursor of a JFrame or any other component.
     * @param imagePath The path to the image file to be used as the cursor.
     * @param name The name of the cursor. This is used to identify the cursor in the system and can be any string you choose.
     * @return A Cursor object created from the specified image.
     */
    public Cursor createCustomCursor(String imagePath, String name) {
        Image cursorImage = Toolkit.getDefaultToolkit().getImage(imagePath);

        Image scaledImage = cursorImage.getScaledInstance(32,32, Image.SCALE_SMOOTH);
        return Toolkit.getDefaultToolkit().createCustomCursor(scaledImage, new Point(0,0), name);
    }

    /**
    * Moves the mouse pointer to the specified screen coordinates.
    * @param x The x-coordinate on the screen
    * @param y The y-coordinate on the screen
     */
    public void moveMouse(int x, int y) {
        executor.submit(() -> {
            try {
                robot.mouseMove(x, y); // Move the mouse to the specified coordinates
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    /**
    * Simulates a mouse click at the current mouse pointer location.
    * 
    * (InputEvent.BUTTON1_DOWN_MASK: Left mouse button,
    * InputEvent.BUTTON2_DOWN_MASK: Middle mouse button,
    * InputEvent.BUTTON3_DOWN_MASK: Right mouse button)
    *
    * @param button The mouse button to click. See https://docs.oracle.com/javase/8/docs/api/java/awt/event/InputEvent.html
    *               
    */
    public void clickMouse(int button , int delayMS) {
        executor.submit(() -> {
            try {
                robot.mousePress(button); // Press the specified mouse button
                robot.mouseRelease(button); // Release the specified mouse button
                Thread.sleep(delayMS);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    /**
     * Holds down a mouse button at the current mouse pointer location.
     * 
     * (InputEvent.BUTTON1_DOWN_MASK: Left mouse button,
     * InputEvent.BUTTON2_DOWN_MASK: Middle mouse button,
     * InputEvent.BUTTON3_DOWN_MASK: Right mouse button)
     * 
     * @param button The mouse button to click. See https://docs.oracle.com/javase/8/docs/api/java/awt/event/InputEvent.html
     */
    public void holdMouseClick(int button) {
        executor.submit(()->{
            
            robot.mousePress(button);
        });
        
    }

    /**
     * Releases a mouse button at the current mouse pointer location.
     * 
     * (InputEvent.BUTTON1_DOWN_MASK: Left mouse button,
     * InputEvent.BUTTON2_DOWN_MASK: Middle mouse button,
     * InputEvent.BUTTON3_DOWN_MASK: Right mouse button)
     * 
     * @param button The mouse button to click. See https://docs.oracle.com/javase/8/docs/api/java/awt/event/InputEvent.html
     */
    public void releaseMouseClick(int button) {
        executor.submit(()->{
            
            robot.mouseRelease(button);
        });
        
    }

    /**
    * Simulates scrolling the mouse wheel.
    * @param notches The number of notches to scroll. Positive values scroll down, negative values scroll up.
    */
    public void scrollMouseWheel(int notches) {
        executor.submit(() -> {
            try {
                robot.mouseWheel(notches); // Scroll the mouse wheel by the specified number of notches
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    

    // #region Keyboard Methods

    /**
     * Simulates pressing a sequence of keys on the keyboard.
     * @param keySequence A list of key codes representing the order of keys to press. See https://docs.oracle.com/javase/8/docs/api/java/awt/event/KeyEvent.html to find the keycode for any key
     * @param delayMS The delay in milliseconds between each key press
     */
    public void pressKeys(List<Integer> keySequence, int delayMS) {
            
        executor.submit(() -> {
            try {
                for (int keycode : keySequence) {
                    robot.keyPress(keycode); // Press the key
                    Thread.sleep(100);       // Hold the key briefly
                    robot.keyRelease(keycode); // Release the key
                    Thread.sleep(delayMS);  // Wait before pressing the next key
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });
    }

    /**
     * Simulates pressing a key once on the keyboard
     * @param keycode The keycode representing the key on the keyboard to be pressed. See https://docs.oracle.com/javase/8/docs/api/java/awt/event/KeyEvent.html to find the keycode for any key
     * @param delayMS The delay in milliseconds after the key is pressed
     */
    public void pressKey(int keycode, int delayMS) {
        
        executor.submit(() -> {
            try {
                robot.keyPress(keycode);
                robot.keyRelease(keycode);
                Thread.sleep(delayMS); 
                
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });

    }

    /**
     * holds down a key for a specified amount of time. Use this method only for typing situations that involve type keys (ex: a,b,1,2). 
     * This actually just spams the key extremely fast (20ms intervals) for the specified time. This is done because on the computer when you are holding a key it repeadedly sends a signal that the key is pressed down. When typing, it only types the key each time it recieves the signal that it is pressed down.
     * If this method doesn't work properly for your scenario, then try holdKey().
     * @param keycode The keycode representing the key on the keyboard to be held down. See https://docs.oracle.com/javase/8/docs/api/java/awt/event/KeyEvent.html to find the keycode for any key
     * @param holdTime The time in seconds to hold the key down (limit is 60 seconds)
     */
    public void holdKey_Type(int keycode, double holdTime) {
        executor.submit(() -> {
            try {
                int timeMS = (int) (holdTime * 1000); // Convert seconds to milliseconds
                int elapsedTime = 0;

                while (elapsedTime < timeMS) {
                    robot.keyPress(keycode); // Press the key
                    Thread.sleep(10); // Hold the key briefly
                    robot.keyRelease(keycode); // Release the key
                    Thread.sleep(10); // Wait for the remaining interval
                    elapsedTime += 20; // Increment elapsed time
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });
    }

    /**
     * holds down a key for a specified amount of time. This method can be used in scenarios that do not involve typing. 
     * If this method doesn't work properly for your scenario, then try holdKey_Type().
     * @param keycode The keycode representing the key on the keyboard to be held down. See https://docs.oracle.com/javase/8/docs/api/java/awt/event/KeyEvent.html to find the keycode for any key
     * @param holdTime The time in seconds to hold the key down (limit is 60 seconds)
     */
    public void holdKey(int keycode, double holdTime) {
        executor.submit(() -> {
            try {
                int timeMS = (int) (holdTime * 1000); // Convert seconds to milliseconds
                robot.keyPress(keycode);
                Thread.sleep(timeMS); 
                robot.keyRelease(keycode); // Release the key

                
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });
    }

    /**
     * Holds down a key until it is released. This method is useful for scenarios where you want to keep a key pressed down without specifying a hold time.
     * @param keycode The keycode representing the key on the keyboard to be held down. See https://docs.oracle.com/javase/8/docs/api/java/awt/event/KeyEvent.html to find the keycode for any key
     */
    public void holdKey(int keycode) {
        executor.submit(()->{
            
            robot.keyPress(keycode);
        });
        
    }
    /**
     * Releases a key that was previously pressed down. This method is useful for scenarios where you want to release a key after holding it down.
     * @param keycode The keycode representing the key on the keyboard to be released. See https://docs.oracle.com/javase/8/docs/api/java/awt/event/KeyEvent.html to find the keycode for any key
     */
    public void releaseKey(int keycode) {
        executor.submit(()->{
            try {
                robot.keyRelease(keycode);
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            
        });
        

    }
}
