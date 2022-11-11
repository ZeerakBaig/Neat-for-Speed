package Main_Package.Vehicle;

import Main_Package.DrawEnvironment.DrawArea;
import Main_Package.DrawEnvironment.SwingPaint;
import Main_Package.EvolutionInterface;
import Main_Package.GUI.UIVariables;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class Vehicle extends JFrame implements Runnable {
    public static boolean blnContinue = false;
    // by default  the custom track will be set to false
    public static boolean blnCustomTrack = true;
    static JButton clearBtn;
    static JButton blackBtn;
    static JButton blueBtn;
    static JButton greenBtn;
    static JButton redBtn;
    static JButton magentaBtn;
    static JButton saveBtn;
    static DrawArea drawArea;


    public static void main(String[] args) throws InterruptedException {
        startApplicationPane();
        Thread.sleep(10000);
        if(blnCustomTrack==true)
        {
            showPaint();
            Thread.sleep(15000);
        }

        ConfigureParameters();
        Thread.sleep(15000);

        Vehicle vehicle = new Vehicle();
        vehicle.run();
    }

    private BufferedImage background;

    {
        try {
            if (blnCustomTrack == true) {
                UIVariables.customTrack = true;
                this.background = ImageIO.read(new File("C:/Users/User/Desktop/My Courses/Honours/Year_Project/image.png"));
            } else {
                UIVariables.xCoordinate = 480;
                UIVariables.yCoordinate = 340;
                this.background = ImageIO.read(new File("C:/Users/User/Desktop/My Courses/Honours/Year_Project/Github_repos/Graph_visualization/src/main/java/Main_Package/Intersection_new15.png"));
            }
            //this.background = ImageIO.read(new File("C:/Users/User/Desktop/My Courses/Honours/Year_Project/Github_repos/Graph_visualization/src/main/java/Main_Package/Intersection_new15.png"));
            //this.background = ImageIO.read(new File("C:/Users/User/Desktop/My Courses/Honours/Year_Project/image.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getBackgroundImage() {
        return background;
    }


    @Override
    public void run() {
        try {
            new VehicleTraining(this, getBackgroundImage()).start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public static void ConfigureParameters() {
        JFrame jframe = new JFrame();
        jframe.setLayout(null);

        jframe.setSize(800, 800);



        //creating the input boxes
        JTextField jtext_GENE_DISABLE_PROBABILITY = new JTextField(String.valueOf(UIVariables.GENE_DISABLE_PROBABILITY));
        JTextField jtext_weight_mutation_probability = new JTextField(String.valueOf(UIVariables.weight_mutation_probability));
        JTextField jtext_weight_random_mutation_probability = new JTextField(String.valueOf(UIVariables.weight_random_mutation_probability));
        JTextField jtext_mutate_weight_max_disturbance = new JTextField(String.valueOf(UIVariables.mutate_weight_max_disturbance));
        JTextField jtext_mutate_new_connection_probability = new JTextField(String.valueOf(UIVariables.mutate_new_connection_probability));
        JTextField jtext_mutate_new_node_probability = new JTextField(String.valueOf(UIVariables.mutate_new_node_probability));
        JTextField jtext_distance_excess_weight = new JTextField(String.valueOf(UIVariables.distance_excess_weight));
        JTextField jtext_distance_disjoint_weight = new JTextField(String.valueOf(UIVariables.distance_disjoint_weight));
        JTextField jtext_distance_weights_weight = new JTextField(String.valueOf(UIVariables.distance_weights_weight));
        JTextField jtext_compatibility_threshold = new JTextField(String.valueOf(UIVariables.compatibility_threshold));
        JTextField jtext_weight_mutation_probability_random = new JTextField(String.valueOf(UIVariables.weight_mutation_probability_random));
        JTextField jtext_generation_elimination_percentage = new JTextField(String.valueOf(UIVariables.generation_elimination_percentage));
        JTextField jtext_crossover_probability = new JTextField(String.valueOf(UIVariables.crossover_probability));

        // setting the labels
        JLabel jLabel_GENE_DISABLE_PROBABILITY = new JLabel("Gene_Disable_Probability");
        JLabel jLabel_weight_mutation_probability = new JLabel("weight_mutation_probability");
        JLabel jLabel_weight_random_mutation_probability = new JLabel("weight_random_mutation_probability");
        JLabel jLabel_mutate_weight_max_disturbance = new JLabel("mutate_weight_max_disturbance");
        JLabel jLabel_mutate_new_connection_probability = new JLabel("mutate_new_connection_probability");
        JLabel jLabel_mutate_new_node_probability = new JLabel("mutate_new_node_probability");
        JLabel jLabel_distance_excess_weight = new JLabel("distance_excess_weight");
        JLabel jLabel_distance_disjoint_weight = new JLabel("distance_disjoint_weight");
        JLabel jLabel_distance_weights_weight = new JLabel("distance_weights_weight");
        JLabel jLabel_compatibility_threshold = new JLabel("compatibility_threshold");
        JLabel jLabel_weight_mutation_probability_random = new JLabel("weight_mutation_probability_random");
        JLabel jLabel_generation_elimination_percentage = new JLabel("generation_elimination_percentage");
        JLabel jLabel_crossover_probability = new JLabel("crossover_probability");

        jLabel_GENE_DISABLE_PROBABILITY.setBounds(80, 30, 300, 30);
        jLabel_weight_mutation_probability.setBounds(80, 70, 300, 30);
        jLabel_weight_random_mutation_probability.setBounds(80, 110, 300, 30);
        jLabel_mutate_weight_max_disturbance.setBounds(80, 150, 300, 30);
        jLabel_mutate_new_connection_probability.setBounds(80, 190, 300, 30);
        jLabel_mutate_new_node_probability.setBounds(80, 230, 300, 30);
        jLabel_distance_excess_weight.setBounds(80, 270, 300, 30);
        jLabel_distance_disjoint_weight.setBounds(80, 310, 300, 30);
        jLabel_distance_weights_weight.setBounds(80, 350, 300, 30);
        jLabel_compatibility_threshold.setBounds(80, 390, 300, 30);
        jLabel_weight_mutation_probability_random.setBounds(80, 430, 300, 30);
        jLabel_generation_elimination_percentage.setBounds(80, 470, 300, 30);
        jLabel_crossover_probability.setBounds(80, 510, 300, 30);

        // setting the boundsfor texts
        jtext_GENE_DISABLE_PROBABILITY.setBounds(350, 30, 300, 30);
        jtext_weight_mutation_probability.setBounds(350, 70, 300, 30);
        jtext_weight_random_mutation_probability.setBounds(350, 110, 300, 30);
        jtext_mutate_weight_max_disturbance.setBounds(350, 150, 300, 30);
        jtext_mutate_new_connection_probability.setBounds(350, 190, 300, 30);
        jtext_mutate_new_node_probability.setBounds(350, 230, 300, 30);
        jtext_distance_excess_weight.setBounds(350, 270, 300, 30);
        jtext_distance_disjoint_weight.setBounds(350, 310, 300, 30);
        jtext_distance_weights_weight.setBounds(350, 350, 300, 30);
        jtext_compatibility_threshold.setBounds(350, 390, 300, 30);
        jtext_weight_mutation_probability_random.setBounds(350, 430, 300, 30);
        jtext_generation_elimination_percentage.setBounds(350, 470, 300, 30);
        jtext_crossover_probability.setBounds(350, 510, 300, 30);


        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setTitle("NEAT Parameters");
        jframe.add(jLabel_GENE_DISABLE_PROBABILITY);
        jframe.add(jLabel_weight_mutation_probability);
        jframe.add(jLabel_weight_random_mutation_probability);
        jframe.add(jLabel_mutate_weight_max_disturbance);
        jframe.add(jLabel_mutate_new_connection_probability);
        jframe.add(jLabel_mutate_new_node_probability);
        jframe.add(jLabel_distance_excess_weight);
        jframe.add(jLabel_distance_disjoint_weight);
        jframe.add(jLabel_distance_weights_weight);
        jframe.add(jLabel_compatibility_threshold);
        jframe.add(jLabel_weight_mutation_probability_random);
        jframe.add(jLabel_generation_elimination_percentage);
        jframe.add(jLabel_crossover_probability);

        jframe.add(jtext_GENE_DISABLE_PROBABILITY);
        jframe.add(jtext_weight_mutation_probability);
        jframe.add(jtext_weight_random_mutation_probability);
        jframe.add(jtext_mutate_weight_max_disturbance);
        jframe.add(jtext_mutate_new_connection_probability);
        jframe.add(jtext_mutate_new_node_probability);
        jframe.add(jtext_distance_excess_weight);
        jframe.add(jtext_distance_disjoint_weight);
        jframe.add(jtext_distance_weights_weight);
        jframe.add(jtext_compatibility_threshold);
        jframe.add(jtext_weight_mutation_probability_random);
        jframe.add(jtext_generation_elimination_percentage);
        jframe.add(jtext_crossover_probability);


        JButton jButton = new JButton("Start Training");
        jButton.setBounds(80, 540, 300, 30);
        jframe.add(jButton);

        jframe.setVisible(true);


        // adding the on click listener
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UIVariables.GENE_DISABLE_PROBABILITY = Double.parseDouble(jtext_GENE_DISABLE_PROBABILITY.getText());
                UIVariables.weight_mutation_probability = Double.parseDouble(jtext_weight_mutation_probability.getText());
                UIVariables.weight_random_mutation_probability = Double.parseDouble(jtext_weight_random_mutation_probability.getText());
                UIVariables.mutate_weight_max_disturbance = Double.parseDouble(jtext_mutate_weight_max_disturbance.getText());
                UIVariables.mutate_new_connection_probability = Double.parseDouble(jtext_mutate_new_connection_probability.getText());
                UIVariables.mutate_new_node_probability = Double.parseDouble(jtext_mutate_new_node_probability.getText());
                UIVariables.distance_excess_weight = Double.parseDouble(jtext_distance_excess_weight.getText());
                UIVariables.distance_disjoint_weight = Double.parseDouble(jtext_distance_disjoint_weight.getText());
                UIVariables.distance_weights_weight = Double.parseDouble(jtext_distance_weights_weight.getText());
                UIVariables.compatibility_threshold = Double.parseDouble(jtext_compatibility_threshold.getText());
                UIVariables.weight_mutation_probability_random = Double.parseDouble(jtext_weight_mutation_probability_random.getText());
                UIVariables.generation_elimination_percentage = Double.parseDouble(jtext_generation_elimination_percentage.getText());
                UIVariables.crossover_probability = Double.parseDouble(jtext_crossover_probability.getText());

                jframe.dispose();

            }
        });
    }

    // function to display the starting point
    public static void startApplicationPane() {
        JFrame frame = new JFrame("Home Page");
        final ImageIcon icon = new ImageIcon("C:/Users/User/Desktop/My Courses/Honours/Year_Project/Github_repos/Graph_visualization/NeatBackground2.png");
        JButton jButton = new JButton("Default Simulation");
        JButton jButton2 = new JButton("Customize Simulation");
        JLabel jLabel = new JLabel("NEAT for Speed");
        JLabel jLabel2 = new JLabel("<html>"+"Biologically Inspired Navigation" +"<br />" + "Control" +"</html>");
        JLabel jLabel3 = new JLabel("<html>"+"Welcome to autonomous vehicle navigation" +"<br />"+
                "simulation using a biologically inspired algorithm." +"<br />"+
                "Start off by choosing a default simulation." + "<br />" +
                "If you are crafty then try customizing your " +"<br />"+
                "simulation environment"+"</html>");
        jLabel.setForeground(Color.BLACK);
        jLabel2.setForeground(Color.darkGray);
        jLabel3.setForeground(Color.black);
        Font font = new Font("Courier", Font. BOLD,30);
        Font font2 = new Font("Courier", Font.BOLD, 20);
        Font font3 = new Font("Courier",Font.TYPE1_FONT, 10);
        jLabel2.setFont(font2);
        jLabel3.setFont(font3);
        jLabel.setFont(font);
        jLabel.setBounds(100,150,500,30);
        jLabel2.setBounds(100, 200, 500,50 );
        jLabel3.setBounds(100,260,300,80);
        jButton.setBounds(100, 350, 300, 30);
        jButton2.setBounds(100, 400, 300, 30);
        jButton.setBackground(Color.decode("#ffc000"));
        jButton2.setBackground(Color.decode("#a1d1f9"));
        jButton.setForeground(Color.white);
        jButton2.setForeground(Color.white);
        jButton.setOpaque(true);
        jButton2.setOpaque(true);
        frame.add(jButton);
        frame.add(jButton2);
        frame.add(jLabel);
        frame.add(jLabel2);
        frame.add(jLabel3);
        JTextArea text = new JTextArea() {
            Image img = icon.getImage();

            // instance initializer
            {
                setOpaque(false);
            }

            public void paintComponent(Graphics graphics) {
                graphics.drawImage(img, 0, 0, this);
                super.paintComponent(graphics);
            }
        };
        JScrollPane pane = new JScrollPane(text);
        Container content = frame.getContentPane();
        content.add(pane, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(3);
        frame.setSize(1100, 600);
        frame.setVisible(true);

        // default simulation
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                blnCustomTrack = false;
                frame.dispose();
            }
        });
        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                blnCustomTrack = true;
                frame.dispose();
            }
        });

    }


    // function to allow user to draw
    public static void showPaint() {
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
                } else if (e.getSource() == saveBtn) {
                    //saving the image in a normal image file
                    saveImage(drawArea);
                    writeCoordinates("C:/Users/User/Desktop/My Courses/Honours/Year_Project/Github_repos/Graph_visualization/coordinates.txt");
                    readFile("C:/Users/User/Desktop/My Courses/Honours/Year_Project/Github_repos/Graph_visualization/coordinates.txt");
                    frame.dispose();
                }
            }
        };

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

    public static void saveImage(DrawArea drawingArea) {
        JFileChooser chooseDirec = new JFileChooser();
        chooseDirec.setFileSelectionMode(JFileChooser.FILES_ONLY);
        //chooseDirec.showSaveDialog(this);
        File file = chooseDirec.getSelectedFile();
        file = new File(file + ".png");

        BufferedImage image = new BufferedImage(
                drawingArea.getWidth(), drawingArea.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        drawingArea.paint(g2);
        try {
            ImageIO.write(image, "png", new File("C:/Users/User/Desktop/My Courses/Honours/Year_Project/image.png"));
        } catch (Exception e) {
        }
    }

    // function to write coordinates to a file
    public static void writeCoordinates(String filePath) {
        try {
            FileWriter myWriter = new FileWriter(filePath);
            myWriter.write(UIVariables.xCoordinate + " " + UIVariables.yCoordinate);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readFile(String path) {
        try {
            FileReader fr = new FileReader(path);

            // Declaring loop variable
            int i;
            // Holds true till there is nothing to read
            while ((i = fr.read()) != -1)

                // Print all the content of a file
                System.out.print((char) i);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}


// class to set background of the panel
class ImagePanel extends JComponent {
    private Image image;

    public ImagePanel(Image image) {
        this.image = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
}

//class to round a button
class RoundButton extends JButton {

    public RoundButton(String label) {
        super(label);

        //Enlarge button to make a circle rather than an oval.
        Dimension size = getPreferredSize();
        size.width = size.height = Math.max(size.width, size.height);
        setPreferredSize(size);

        //This call causes the JButton *not* to paint the background
        //and allows us to paint a round background instead.
        setContentAreaFilled(false);
    }

    //Paint the round background and label.
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed())
            //You might want to make the highlight color
            //a property of the RoundButton class.
            g.setColor(Color.lightGray);
        else
            g.setColor(getBackground());
        g.fillOval(0, 0, 300 , 40);
        //This call will paint label and focus rectangle.
        super.paintComponent(g);
    }

    //Paint the border of the button using a simple stroke.
    protected void paintBorder(Graphics g) {
        g.setColor(getForeground());
        g.drawOval(0, 0, 10, 10);
    }
}
