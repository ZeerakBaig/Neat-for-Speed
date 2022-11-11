package Main_Package.DrawEnvironment;

import Main_Package.GUI.UIVariables;
import Main_Package.Vehicle.Vehicle;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;

public class SwingPaint {

    JButton clearBtn, blackBtn, blueBtn, greenBtn, redBtn, magentaBtn, saveBtn;
    DrawArea drawArea;
    Vehicle vehicle = new Vehicle();
    ActionListener actionListener = new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == clearBtn) {
                drawArea.clear();
            } else if (e.getSource() == blackBtn) {
                drawArea.black();
            } else if (e.getSource() == blueBtn) {
                drawArea.blue();
            } else if (e.getSource() == greenBtn) {
                drawArea.green();
            } else if (e.getSource() == redBtn) {
                drawArea.red();
            } else if (e.getSource() == magentaBtn) {
                drawArea.magenta();
            }else if(e.getSource() == saveBtn)
            {
                //saving the image in a normal image file
                saveImage(drawArea);
                writeCoordinates("C:/Users/User/Desktop/My Courses/Honours/Year_Project/Github_repos/Graph_visualization/coordinates.txt");
                readFile("C:/Users/User/Desktop/My Courses/Honours/Year_Project/Github_repos/Graph_visualization/coordinates.txt");
            }
        }
    };

    public static void main(String[] args) {
        new SwingPaint().show();
    }

    public void show() {
        // create main frame
        JFrame frame = new JFrame("Swing Paint");
        Container content = frame.getContentPane();
        // set layout on content pane
        content.setLayout(new BorderLayout());
        // create draw area
        drawArea = new DrawArea();

        // add to content pane
        content.add(drawArea, BorderLayout.CENTER);

        // create controls to apply colors and call clear feature
        JPanel controls = new JPanel();

        clearBtn = new JButton("Clear");
        clearBtn.addActionListener(actionListener);
        blackBtn = new JButton("Black");
        blackBtn.addActionListener(actionListener);
        blueBtn = new JButton("Blue");
        blueBtn.addActionListener(actionListener);
        greenBtn = new JButton("Green");
        greenBtn.addActionListener(actionListener);
        redBtn = new JButton("Red");
        redBtn.addActionListener(actionListener);
        magentaBtn = new JButton("Magenta");
        magentaBtn.addActionListener(actionListener);

        saveBtn = new JButton("Save");
        saveBtn.addActionListener(actionListener);


        // add to panel
        controls.add(greenBtn);
        controls.add(blueBtn);
        controls.add(blackBtn);
        controls.add(redBtn);
        controls.add(magentaBtn);
        controls.add(clearBtn);
        controls.add(saveBtn);

        // add to content pane
        content.add(controls, BorderLayout.NORTH);

        frame.setSize(800, 800);
        // can close frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // show the swing paint result
        frame.setVisible(true);

    }

    public void saveImage(DrawArea drawingArea)
    {
        JFileChooser chooseDirec = new JFileChooser();
        chooseDirec.setFileSelectionMode(JFileChooser.FILES_ONLY);
        //chooseDirec.showSaveDialog(this);
        File file = chooseDirec.getSelectedFile();
        file = new File(file+".png");

        BufferedImage image=new BufferedImage(
                drawingArea.getWidth(), drawingArea.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g2=(Graphics2D)image.getGraphics();
        drawingArea.paint(g2);
        try
        {
            ImageIO.write(image, "png", new File("C:/Users/User/Desktop/My Courses/Honours/Year_Project/image.png"));
        }
        catch (Exception e)

        {
        }
    }

    // function to write coordinates to a file
    public void writeCoordinates(String filePath)
    {
        try{
            FileWriter myWriter = new FileWriter(filePath);
            myWriter.write(UIVariables.xCoordinate +" "+ UIVariables.yCoordinate);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFile(String path){
        try{
            FileReader fr = new FileReader(path);

            // Declaring loop variable
            int i;
            // Holds true till there is nothing to read
            while ((i = fr.read()) != -1)

                // Print all the content of a file
                //System.out.println("this is the problem");
                System.out.print((char)i);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}