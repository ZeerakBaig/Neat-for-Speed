package Main_Package.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

//gui class
public class Frame extends JFrame {
    private Panel panel;
    private DisplayGenome genome;


    public Frame(DisplayGenome genome)
    {
        this();
        setGenome(genome);
        this.repaint();

    }

    public void setGenome(DisplayGenome genome)
    {
        panel.setGenome(genome);
        this.genome = genome;
    }
    public Frame() throws HeadlessException {
        //setting the title and the dimensions of the frame
        setInitialFrame();
        //handling the characteristics of components
        handleLookAndFeels();
        //function to add a menu with relevant buttons
        setMenu();

    }

    private void setMenu() {
        JPanel menu = new JPanel();
        menu.setPreferredSize(new Dimension(1000,100));
        menu.setLayout(new GridLayout(1,5));

        JButton buttonB = new JButton("Random Weight");
        buttonB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                repaint();
            }
        });
        //menu.add(buttonB);

        JButton buttonZ = new JButton("Weight Shift");
        buttonZ.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                repaint();
            }
        });
       // menu.add(buttonZ);

        JButton buttonC = new JButton("Mutate Link");
        buttonC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                repaint();
            }
        });
        //menu.add(buttonC);

        JButton buttonD = new JButton("Mutate Node");
        buttonD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                repaint();
            }
        });
        //menu.add(buttonD);



        JButton buttonE = new JButton("Enable/Disable Link");
        buttonE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                repaint();
            }
        });
        //menu.add(buttonE);

        JButton buttonF = new JButton("Mutate All");
        buttonF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                repaint();
            }
        });
        //menu.add(buttonF);

        JButton buttonG = new JButton("Calculate");
        buttonG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                repaint();
            }
        });
        //menu.add(buttonG);
        //this.add(menu, BorderLayout.SOUTH);
        this.panel = new Panel();
        this.add(panel, BorderLayout.CENTER);
        this.setVisible(true);
    }

    private void handleLookAndFeels() {
        UIManager.LookAndFeelInfo[] myLooks = UIManager.getInstalledLookAndFeels();
        try {
            UIManager.setLookAndFeel(myLooks[3].getClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    public void setInitialFrame()
    {
        this.setDefaultCloseOperation(3);

        this.setTitle("NEAT");
        this.setMinimumSize(new Dimension(1000,700));
        this.setPreferredSize(new Dimension(1000,700));

        this.setLayout(new BorderLayout());
    }



}
