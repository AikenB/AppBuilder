import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;

import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;

// import javafx.application.Application;
// import javafx.scene.Scene;
// import javafx.scene.control.Button;
// import javafx.scene.control.Label;
// import javafx.scene.image.Image;
// import javafx.scene.image.ImageView;
// import javafx.scene.layout.Pane;
// import javafx.scene.paint.Color;
// import javafx.scene.text.Font;
// import javafx.stage.Stage;

import java.awt.event.ActionListener; // For button click events
import java.awt.event.ActionEvent;   // For handling action events
import java.awt.event.ComponentListener; // For listening to component events
import java.awt.event.KeyEvent;
import java.awt.event.ComponentAdapter;  // For overriding specific component events
import java.awt.event.ComponentEvent;    // For handling component resize events

import java.awt.Color;       // For setting background colors
import java.awt.Font;        // For customizing fonts
import java.awt.Graphics;    // For custom painting in `paintComponent()`
import java.awt.Dimension;   // For specifying preferred/minimum sizes
import java.awt.Desktop;     // For opening URLs or files

import java.io.File;         // For handling file operations
import javax.swing.JFileChooser; // For file selection dialogs

import javax.swing.Timer; // For creating animations or timed events

import java.net.URI;         // For handling URIs
import java.net.URISyntaxException; // For handling URI syntax errors

import java.sql.Connection;      // For database connections
import java.sql.DriverManager;   // For managing database drivers
import java.sql.Statement;       // For executing SQL statements
import java.util.List;
import java.sql.ResultSet;       // For handling query results

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;



public class Testing{

    JFrame frame = new JFrame("Testing GUI");
    // Create a panel to hold components
    JPanel panel = new JPanel();
    // Add a label
    JLabel label = new JLabel("Welcome to my application!");
    
    
    ComponentBuilder builder = new ComponentBuilder(frame, panel);
    // Add a button
    //JButton button = new JButton("Click Me");
    
    
    
    public Testing(){


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        panel.setLayout(null);
        
        frame.setSize(1000, 700);
        frame.setVisible(true);
        
        panel.add(label);
        
        //JButton button = creator.createButton("Click Me",150,70);
        //JButton button2 = creator.createButton("Click Me Too", 100, 100, 200, 50);
        
        JButton button = builder.createButton("button3", 0, 400, 200, 50);
        builder.customizeComponent(button, Color.GREEN, Color.BLACK, new Font("Arial", Font.BOLD, 12));
        //builder.createImage("D:\\Chrome\\tungtungsahor.jpg", button, 0, 0, 1, 1);
        builder.createImage("src/Images/tungtungsahor.jpg", button, 0.125, 0.125, 0.75, 0.75);
        DesktopController desktop = new DesktopController();
        builder.setWindowIcon("src/Images/amogus.jpg");
        frame.setTitle("Experiment");
        JLabel label = builder.createLabel("Testing", 0, 0, 100, 50);
        JComponent image = builder.createImage("src/Images/amogus.jpg", 0, 0, 100, 100);
        ComponentMover mover = new ComponentMover(image);
        ComponentMover mover2 = new ComponentMover(label);
        mover.enableBoundaryLimits(true, 100, 100);
        mover2.enableBoundaryLimits(true, 100, 150);

        JScrollPane scrollPane = builder.createScrollableArea(300, 50 , 100, 100, button);
        JSlider slider = builder.createVerticalSlider(0, 100, 50, 300, 50, 200);
        
        Interactor.addButtonAction(button, ()->{
            builder.scaleComponentCoordinates(image, 20, 20);
            
        });

        Interactor.addKeyHoldAction(panel, KeyEvent.VK_A, ()->{
            mover.moveComponent_X(-10);
            mover2.moveComponent_X(-10);
           
        }, ()->{
            // mover.moveComponent_X(0);
            // mover2.moveComponent_X(0);
        });
        Interactor.addKeyHoldAction(panel, KeyEvent.VK_D, ()->{
            mover.moveComponent_X(10);
            mover2.moveComponent_X(10);
           
        }, ()->{
            // mover.moveComponent_X(0);
            // mover2.moveComponent_X(0);
        });
        Interactor.addKeyHoldAction(panel, KeyEvent.VK_W, ()->{
            mover.moveComponent_Y(10);
            mover2.moveComponent_Y(10);
           
        }, ()->{
            // mover.moveComponent_Y(0);
            // mover2.moveComponent_Y(0);
        });
        Interactor.addKeyHoldAction(panel, KeyEvent.VK_S, ()->{
            mover.moveComponent_Y(-10);
            mover2.moveComponent_Y(-10);
           
        }, () -> {
            // mover.moveComponent_Y(0);
            // mover2.moveComponent_Y(0);
        });
        Interactor.addKeyAction(panel, KeyEvent.VK_SPACE, ()->{
            builder.followComponent(button, image);
        });


        // Interactor.addKeyReleaseAction(panel, KeyEvent.VK_C, ()->{
            
            
        // });
        


        
        

        // Add a ComponentListener to adjust the button's position dynamically
        
        
        
    

    }

}

