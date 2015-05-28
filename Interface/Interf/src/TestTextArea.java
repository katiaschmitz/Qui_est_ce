import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
public class TestTextArea extends JFrame{     
     JScrollPane scrollPane;     
     JTextArea textArea;     
     ImageIcon image;     
     public TestTextArea()     {          
          JPanel panel = new JPanel();          
          setContentPane( panel );          
          panel.setLayout( new BorderLayout() );   
          this.setPreferredSize(new Dimension(1100,550));

          image = new ImageIcon("fond.jpg");          
          textArea = new JTextArea(20, 60 )          {               
               public void paintComponent(Graphics g)               {                    
                    Point p = scrollPane.getViewport().getViewPosition();                    
                    g.drawImage(image.getImage(), p.x, p.y, null);                    
                    super.paintComponent(g);               
               }          
          };          
          textArea.setOpaque(false);          
          textArea.setFont( new Font("monospaced", Font.PLAIN, 12) );          
          scrollPane = new JScrollPane( textArea );          
          panel.add( scrollPane );     
     }     
     public static void main(String[] args)     {          
          TestTextArea frame = new TestTextArea();          
          frame.setDefaultCloseOperation( EXIT_ON_CLOSE );          
          frame.pack();          
          frame.setVisible(true);     
     }
} 