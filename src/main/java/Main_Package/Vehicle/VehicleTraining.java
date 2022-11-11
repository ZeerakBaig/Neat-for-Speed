package Main_Package.Vehicle;


import Main_Package.*;
import Main_Package.GUI.DisplayGenome;
import Main_Package.GUI.UIVariables;
import Main_Package.NEAT.Genome.Gene;
import Main_Package.NEAT.Genome.Genome;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.spriteManager.SpriteManager;
import org.graphstream.ui.swing.SwingGraphRenderer;
import org.graphstream.ui.swing_viewer.SwingViewer;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerPipe;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Scanner;


public class VehicleTraining {
    private Vehicle vehicle;
    private VehicleFrame vehicle_frame;
    private BufferedImage simulation_grid;
    private TrainCanvas trainCanvas;
    public static boolean blnContinue = false;

    int inputSize = 0;
    int outputSize = 0;

    // currently busy with hit counts
    public static int hitCount = 0;

    // configuration variables



    // Inputs are the number of sensors(In our case we have 18 sensors)
    // when a sensor hits something the input is 0 else its 1
    // Output types include 0.0-0.3 turn left, 0.3-0.7 go straight, 0.7-1.0 turn right


    public VehicleTraining(Vehicle vehicle, BufferedImage simulation_grid)
    {
        // the actual background image
        this.simulation_grid = simulation_grid;
        // the vehicle itself
        this.vehicle = vehicle;
        // currently, in works because even after reaching the max fitness the vehicle is going for another round unnecessarily
        // ask the user for input
        // adding the vehicle frame which comprises car image and the location
        // we want to display he frame at each location
        setVehicleSimulationGrid(vehicle);

    }

    public void setTitle(String title)
    {
        this.vehicle.setTitle(title);
    }

    // method to start the vehicle training
    // inputs are going to be the number of sensors
    // if a sensor hits something the value is 0 else its 1
    public void start() throws InterruptedException {
        //ConfigureParameters();
        //Thread.sleep(20000);
            EvolutionInterface instance = EvolutionInterface.newInstance(new VehicleCoordinates().getSensors().size() + 1, 2,
                    new SigmoidActivationFunction(), new FitnessCalculator() {
                        @Override
                        public double getFitness(NetworkCalculations networkCalculations) {
                            VehicleCoordinates vehicle_location = new VehicleCoordinates();

                            long ticksLived = 0;

                            while (vehicle_location.carIsAlive(simulation_grid) && !vehicle_location.isFinish(simulation_grid)) {
                                // run the vehicle while it is alive
                                boolean right_touched = false;
                                boolean left_touched = false;

                                // get all the inputs from the sensors
                                double[] inputs = new double[vehicle_location.getSensors().size() + 1];
                                for (int x = 0; x < vehicle_location.getSensors().size(); x++) {
                                    // create new sensors with those inputs
                                    Sensor sensor = vehicle_location.getSensors().get(x);
                                    // get the available distance from sensors to the edge of the roads
                                    double length = sensor.getAvailableDistance(simulation_grid);
                                    if (length > 200) {
                                        length = 200;
                                    }
                                    // inputs are the distances from the non road objects
                                    inputs[x] = length / 200D;
                                }
                                inputSize = inputs.length;
                                // last element is going to be the speed of the vehicle
                                inputs[inputs.length - 1] = vehicle_location.getCurrent_speed();

                                // calculate the output this will give us the new speed also
                                double[] answer = networkCalculations.calculate(inputs);

                                // first output is the action(steer left, right, or go straight)
                                double output = answer[0];
                                // second output is the speed of the car
                                double speed = answer[1];

                                if (output >= 0 && output <= 0.3) {
                                    // steer left
                                    left_touched = true;
                                }
                                if (output >= 0.7 && output <= 1) {
                                    //steer right
                                    right_touched = true;
                                }

                                // update the coordinates
                                vehicle_location.tick(speed, right_touched, left_touched);
                                // keeping count of the number of frames basically that actually were legal
                                ticksLived++;
                            }

                            // first fitness is for actually making it rest is for speed
                            double fitness = 0;
                            // seconds lived will be less if the car made it in less time
                            double seconds_lived = ticksLived / 30D;// 30 ticks per second
                            //seconds_lived += hitCount;
                            // if statement to check if the car is continuously stuck and not reaching finish
                            // 45 is an arbitary threshhold chosen
                            if (seconds_lived > 45) {
                                System.out.println("ERROR ran over 45 ticks");
                            }
                            if (vehicle_location.isFinish(simulation_grid)) {
                                // the lesser the seconds lived without crashing the higher the fitness
                                fitness = (45 - seconds_lived);
                            }

                            return fitness * fitness;

                        }


                        // generation
                        private int generation = 1;

                        @Override
                        public void generationEnded(NetworkCalculations bestPerforming) {
                            // we need to show how car is performing in each generation
                            this.generation++;
                            if (this.getFitness(bestPerforming) != bestPerforming.getFitness()) {
                                throw new AssertionError();
                            }
                      /*  if(interval != 0 && !(this.generation % interval==0))
                        {
                            // return nothing
                            return;
                        }*/

                            setTitle("NEAT Vehicle - Generation " + this.generation + " - Fitness " + bestPerforming.getFitness());
                            // set the vehicle coordinates to default initially
                            vehicle_frame.setVehicle_location(new VehicleCoordinates());
                            while (true) {
                                try {
                                    // putting this current thread to sleep for a some time so that we can view the cars motion
                                    Thread.sleep((long) (1000D / 30D)); // because we actually want to see how the car is moving else it will just display it on finish line
                                    // similar code to the get fitness but this time we just use it to display the car
                                    boolean right_touched = false;
                                    boolean left_touched = false;
                                    double[] inputs = new double[vehicle_frame.getVehicle_location().getSensors().size() + 1];
                                    // getting the inputs from the sensors(distance) and putting them in an array of inputs
                                    for (int x = 0; x < vehicle_frame.getVehicle_location().getSensors().size(); x++) {
                                        Sensor sensor = vehicle_frame.getVehicle_location().getSensors().get(x);
                                        double length = sensor.getAvailableDistance(simulation_grid);
                                        // in case distance is too large we just set a threshold
                                        if (length > 200) {
                                            length = 200;
                                        }
                                        // we add the scaled distance to each of out inputs, these values are for each sensor
                                        inputs[x] = length / 200D;
                                    }
                                    inputs[inputs.length - 1] = vehicle_frame.getVehicle_location().getCurrent_speed();
                                    // get the outputs from the best performing genome
                                    // we only simulate the best performing vehicle for now
                                    double[] answer = bestPerforming.calculate(inputs);
                                    outputSize = answer.length;
                                    // we will have 2 outputs one for the action and second for the speed
                                    double output = answer[0];
                                    double speed = answer[1];

                                    if (output >= 0 && output <= 0.3) {
                                        // steer left
                                        left_touched = true;
                                    }
                                    if (output >= 0.7 && output <= 1) {
                                        // steer right
                                        right_touched = true;
                                    }
                                    // pass the values to the tick function to calculate the new coordinates
                                    vehicle_frame.getVehicle_location().tick(speed, right_touched, left_touched);

                                    if (!vehicle_frame.getVehicle_location().carIsAlive(vehicle_frame.getBackgroundImage())) {
                                        // restart the process after setting the location so that calculations take place from new coordinates
                                        vehicle_frame.setVehicle_location(new VehicleCoordinates());
                                        continue;
                                    }
                                    if (vehicle_frame.getVehicle_location().isFinish(vehicle_frame.getBackgroundImage())) {
                                        break;
                                    }
                                    // we re displaying the vehicle at each valid coordinate
                                    vehicle.repaint();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }

                        }

                    });

            // allow the user to input the parameters
            configureVehicleParameters(instance);
            //ConfigureParameters(instance);

            instance.trainToFitness(1000, 1250);
            //instance.trainToFitness(1000, 1500);
            // show the generation fitness graph***********************************************
            setupGraph(instance);


        System.out.println( "bye bye" );


    }

    protected void sleep( long ms ) {
        try {
            Thread.sleep( ms );
        } catch (InterruptedException e) { e.printStackTrace(); }
    }

    // Viewer Listener Interface
    public void buttonReleased( String id ) {}

    // Data
    private String styleSheet =
            "graph {"+
                    "	canvas-color: white;"+
                    "		fill-mode: gradient-radial;"+
                    "		fill-color: white, #EEEEEE;"+
                    "		padding: 60px;"+
                    "	}"+
                    "node {"+
                    "	shape: circle;"+
                    "	size: 20px;"+
                    "	fill-mode: plain;"+
                    "	fill-color: #CCC;"+
                    "	stroke-mode: plain;"+
                    "	stroke-color: black;"+
                    "	stroke-width: 1px;"+
                    "}"+
                    "node:clicked {"+
                    "	stroke-mode: plain;"+
                    "	stroke-color: red;"+
                    "}"+
                    "node:selected {"+
                    "	stroke-mode: plain;"+
                    "	stroke-color: blue;"+
                    "}"+
                    "node#A {"+
                    "	size-mode: dyn-size;"+
                    "	size: 10px;"+
                    "}"+
                    "node#D {"+
                    "	shape: box;"+
                    "	size-mode: fit;"+
                    "	padding: 5px;"+
                    "}"+
                    "edge {"+
                    "	shape: blob;"+
                    "	size: 1px;"+
                    "	fill-color: grey;"+
                    "	fill-mode: plain;"+
                    "	arrow-shape: arrow;"+
                    "	arrow-size: 10px, 3px;"+
                    "}"+
                    "edge#BC {"+
                    "	size-mode: dyn-size;"+
                    "	size: 1px;"+
                    "}"+
                    "sprite {"+
                    "	shape: circle;"+
                    "	fill-color: #FCC;"+
                    "	stroke-mode: plain;"+
                    "	stroke-color: black;"+
                    "}"+
                    "sprite:selected {"+
                    "	stroke-color: red;"+
                    "}"+
                    "sprite#S1 {"+
                    "	size-mode: dyn-size;"+
                    "}";
    public void mouseOver(String id){

    }

    public void mouseLeft(String id){
    }

    // to round up the speed value
    public double round(double value, int places)
    {
        return new BigDecimal(value).setScale(places, RoundingMode.HALF_UP).doubleValue();
    }


    // displaying the graph

    public void setupGraph(EvolutionInterface instance)
    {
        System.out.println("Max value of double is: " + Double.MAX_VALUE);
        List<Genome> genome_List = instance.getBestGenome();
        Genome bestOne = genome_List.get(0);
        for(Gene g : bestOne.getGenes())
        {
            System.out.println("Number of genes: " +g.getInnovation_number() + "INput size" + inputSize +
                    "Output Size " + outputSize);
        }
        List<Integer> intInputNodes = bestOne.getInput_nodes();
        List<Integer> intOutputNodes = bestOne.getOutput_nodes();
        List<Integer> intMiddleNodes = bestOne.getHidden_nodes();

        for(int x : intInputNodes)
        {
            System.out.println("Input nodes are : " + x + "\n");
        }

        for(int y : intOutputNodes)
        {
            System.out.println("output nodes are : " + y + "\n");
        }

        for(int y : intMiddleNodes)
        {
            System.out.println("Middle nodes are : " + y + "\n");
        }


        DisplayGenome displayGenome = new DisplayGenome(bestOne, inputSize,outputSize);
        for(int x = 0 ; x < displayGenome.getMiddleNodes().size(); x++)
        {
            System.out.println("Why are you not displaying" + x);
        }
        //new Frame(displayGenome);

        System.setProperty("org.graphstream.ui", "swing");


        boolean loop = true ;
            MultiGraph graph = new MultiGraph("Test Size");

            Viewer viewer = new SwingViewer( graph, SwingViewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD );
            ViewerPipe pipeIn = viewer.newViewerPipe();
            viewer.addView( "view1", new SwingGraphRenderer() );

            pipeIn.addAttributeSink( graph );
            pipeIn.pump();;

            graph.setAttribute( "ui.stylesheet", styleSheet );
            graph.setAttribute( "ui.antialias" );
            graph.setAttribute( "ui.quality" );

            for(int x : intInputNodes)
            {
                graph.addNode(String.valueOf(x));
            }
            for(int x : intMiddleNodes)
            {
                graph.addNode(String.valueOf(x));
            }
            for(int x : intOutputNodes)
            {
                graph.addNode(String.valueOf(x));
            }

            for(Gene g : displayGenome.getGeneList())
            {
                graph.addEdge(String.valueOf(g.getNodeFrom())+ String.valueOf(g.getNodeTo()),
                        String.valueOf(g.getNodeFrom()), String.valueOf(g.getNodeTo())).setAttribute("ui.label", g.getWeight());
            }

            for(Node n : graph)
            {
                if(intInputNodes.contains(Integer.parseInt(n.toString())))
                {
                    // they are the input nodes
                    n.setAttribute("xyz", new double[] { 0.1, Math.random(), Math.random() });

                }else if(n.getId().equals("19")||n.getId().equals("20"))
                {
                    n.setAttribute("xyz", new double[] { 0.9, Math.random(), Math.random() });
                }else
                {
                    n.setAttribute("xyz", new double[] { Math.random(), Math.random(), Math.random() });
                }


                n.setAttribute("ui.label", n.getId());

            }



            SpriteManager sm = new SpriteManager( graph );

            double size = 20f;
            double sizeInc = 1f;

            while( loop ) {
                pipeIn.pump();
                sleep( 40 );

                size += sizeInc;

                if( size > 50 ) {
                    sizeInc = -1f; size = 50f;
                } else if( size < 20 ) {
                    sizeInc = 1f; size = 20f;
                }
            }
    }

    public static void configureParameters()
    {
        Frame frame = new JFrame("Student Registration");
        frame.setSize(350, 200);
        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);
        frame.setVisible(true);
        blnContinue = true;
    }

    private static void placeComponents(JPanel panel)
    {
        panel.setLayout(null);
        JLabel userLabel1 = new JLabel("First Name");
        userLabel1.setBounds(10,20,80,25);
        panel.add(userLabel1);
        JLabel userLabel2 = new JLabel("Last Name");
        userLabel2.setBounds(10,50,100,25);
        panel.add(userLabel2);
        JLabel userLabel3 = new JLabel("Email ID");
        userLabel3.setBounds(10,80,80,25);
        panel.add(userLabel3);
        JLabel userLabel4 = new JLabel("Phone No.");
        userLabel4.setBounds(10,110,80,25);
        panel.add(userLabel4);
        JTextField fname = new JTextField(20);
        fname.setBounds(100,50,165,25);
        panel.add(fname);
        JTextField lName = new JTextField(20);
        lName.setBounds(100,20,165,25);
        panel.add(lName);
        JTextField email = new JTextField(20);
        email.setBounds(100,80,165,25);
        panel.add(email);
        JTextField phNO = new JTextField(20);
        phNO.setBounds(100,110,165,25);
        panel.add(phNO);
        JButton loginButton = new JButton("Register");
        loginButton.setBounds(10, 160, 80, 25);
        panel.add(loginButton);
        JButton clearButton = new JButton("clear");
        clearButton.setBounds(100, 160, 80, 25);
        panel.add(clearButton);
    }

    // function to set the frame for the vehicle
    public void setVehicleSimulationGrid(Vehicle vehicle)
    {
        vehicle.add(this.vehicle_frame = new VehicleFrame(VehicleTraining.this.vehicle));
        //testing
        vehicle.pack();
        vehicle.setLocationRelativeTo(null);
        vehicle.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vehicle.setVisible(true);
        vehicle.setTitle("Neat Autonomous vehicle");
        vehicle.setResizable(false);

    }

    // function to configure vehicle parameters
    public void configureVehicleParameters(EvolutionInterface instance)
    {
        /*instance.setConfiguration(Configuration.GENE_DISABLE_PROBABILITY, 0.75);
        instance.setConfiguration(Configuration.weight_mutation_probability, 0.7);
        instance.setConfiguration(Configuration.weight_random_mutation_probability, 0.10);
        instance.setConfiguration(Configuration.mutate_weight_max_disturbance, 0.1);

        instance.setConfiguration(Configuration.mutate_new_connection_probability, 0.03);
        instance.setConfiguration(Configuration.mutate_new_node_probability, 0.3);

        instance.setConfiguration(Configuration.distance_excess_weight, 1.0);
        instance.setConfiguration(Configuration.distance_disjoint_weight, 1.0);
        instance.setConfiguration(Configuration.distance_weights_weight, 0.4);

        instance.setConfiguration(Configuration.compatibility_threshold, 0.8); // the bigger the less species
        instance.setConfiguration(Configuration.weight_mutation_probability_random, 3); // -2.0 - 2.0

        instance.setConfiguration(Configuration.generation_elimination_percentage, 0.85);
        instance.setConfiguration(Configuration.crossover_probability, 0.75);*/


        instance.setConfiguration(Configuration.GENE_DISABLE_PROBABILITY, UIVariables.GENE_DISABLE_PROBABILITY);
        instance.setConfiguration(Configuration.weight_mutation_probability, UIVariables.weight_mutation_probability);
        instance.setConfiguration(Configuration.weight_random_mutation_probability, UIVariables.weight_random_mutation_probability);
        instance.setConfiguration(Configuration.mutate_weight_max_disturbance, UIVariables.mutate_weight_max_disturbance);

        instance.setConfiguration(Configuration.mutate_new_connection_probability, UIVariables.mutate_new_connection_probability);
        instance.setConfiguration(Configuration.mutate_new_node_probability, UIVariables.mutate_new_node_probability);

        instance.setConfiguration(Configuration.distance_excess_weight, UIVariables.distance_excess_weight);
        instance.setConfiguration(Configuration.distance_disjoint_weight, UIVariables.distance_disjoint_weight);
        instance.setConfiguration(Configuration.distance_weights_weight, UIVariables.distance_weights_weight);

        instance.setConfiguration(Configuration.compatibility_threshold, UIVariables.compatibility_threshold); // the bigger the less species
        instance.setConfiguration(Configuration.weight_mutation_probability_random, UIVariables.weight_mutation_probability_random); // -2.0 - 2.0

        instance.setConfiguration(Configuration.generation_elimination_percentage, UIVariables.generation_elimination_percentage);
        instance.setConfiguration(Configuration.crossover_probability, UIVariables.crossover_probability);
    }








}
