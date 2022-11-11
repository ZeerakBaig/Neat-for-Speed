import Main_Package.*;
import Main_Package.DrawEnvironment.DrawArea;
import Main_Package.GUI.DisplayGenome;
import Main_Package.GUI.Frame;
import Main_Package.GUI.GenerationFitnessGraph;
import Main_Package.GUI.UIVariables;
import Main_Package.NEAT.Genome.Gene;
import Main_Package.NEAT.Genome.Genome;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.graphicGraph.stylesheet.StyleConstants;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;
import org.graphstream.ui.swing.SwingGraphRenderer;
import org.graphstream.ui.swing_viewer.SwingViewer;
import org.graphstream.ui.swing_viewer.ViewPanel;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerPipe;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Main implements Runnable {
    public static void main(String[] args)
    {
        new Main().run();

    }
    private final double[][] inputs = new double[4][];
    {
        // first element is bias
        inputs[0] = new double[] { 1, 0, 0 };
        inputs[1] = new double[] { 1, 1, 1 };
        inputs[2] = new double[] { 1, 0, 1 };
        inputs[3] = new double[] { 1, 1, 0 };
    }

    private final double[][] outputs = new double[4][];
    {
        outputs[0] = new double[] { 0 };
        outputs[1] = new double[] { 0 };
        outputs[2] = new double[] { 1 };
        outputs[3] = new double[] { 1 };
    }

    @Override
    public void run() {
        int inputSize = this.inputs[0].length;
        int outputSize = this.outputs[0].length;

        EvolutionInterface instance = EvolutionInterface.newInstance(inputSize, outputSize,
                new SigmoidActivationFunction(), new FitnessCalculator() {
                    @Override
                    public double getFitness(NetworkCalculations networkCalculations) {
                        double off = 0;
                        for (int i = 0; i < 4; i++) {
                            double[] in = inputs[i];

                            // the actual value
                            double expectedOut = outputs[i][0];
                            // calculated value
                            double actualOut = networkCalculations.calculate(in)[0];

                            off += Math.abs(actualOut - expectedOut);
                        }
                        // subtract from 4 and square to increase fitness proportionally
                        double fitness = 4 - off;

                        if (fitness < 0)
                            fitness = 0;

                        return fitness * fitness;
                    }
                });

        // automate the parameter exploration
        configureParameters(instance);

        instance.trainToFitness(1000, 10);


        // Testing stuff*****************************8
        System.out.println("Max value of double is: " + Double.MAX_VALUE);
        List<Genome> genome_List = instance.getBestGenome();
        Genome bestOne = genome_List.get(0);
        for(Gene g : bestOne.getGenes())
        {
            System.out.println("Number of genes: " +g.getInnovation_number());
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
            /*if(displayGenome.getInputNodes().contains(g.getNodeFrom()) || displayGenome.getInputNodes().contains(g.getNodeTo()))
            {
                graph.addEdge("I"+String.valueOf(g.getNodeFrom())+ "I" + String.valueOf(g.getNodeTo()),
                        String.valueOf(g.getNodeFrom()), String.valueOf(g.getNodeTo()));
            }else if(displayGenome.getMiddleNodes().contains(g.getNodeFrom()) || displayGenome.getMiddleNodes().contains(g.getNodeTo()))
            {
                graph.addEdge("M"+String.valueOf(g.getNodeFrom())+ "M"+String.valueOf(g.getNodeTo()),
                        String.valueOf(g.getNodeFrom()), String.valueOf(g.getNodeTo()));
            }else
            {
                graph.addEdge("O"+String.valueOf(g.getNodeFrom())+ "O"+String.valueOf(g.getNodeTo()),
                        String.valueOf(g.getNodeFrom()), String.valueOf(g.getNodeTo()));
            }*/
            graph.addEdge(String.valueOf(g.getNodeFrom())+ String.valueOf(g.getNodeTo()),
                    String.valueOf(g.getNodeFrom()), String.valueOf(g.getNodeTo())).setAttribute("ui.label" ,g.getWeight());


        }

        for(Node n : graph)
        {
            if(n.getId().equals("1")|| n.getId().equals("2") || n.getId().equals("3"))
            {
                // they are the input nodes
                n.setAttribute("xyz", new double[] { 0.1, Math.random(), Math.random() });

            }else if(n.getId().equals("4"))
            {
                n.setAttribute("xyz", new double[] { 0.9, Math.random(), Math.random() });
            }else
            {
                n.setAttribute("xyz", new double[] { 0.5, Math.random(), Math.random() });
            }


            n.setAttribute("ui.label", n.getId());

        }

        // adding the graph to a drawing area






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


        // show the generation fitness graph***********************************************



        System.out.println( "bye bye" );
       // System.exit(0);





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

    public void configureParameters(EvolutionInterface instance)
    {
        instance.setConfiguration(Configuration.GENE_DISABLE_PROBABILITY, 0.75);
        instance.setConfiguration(Configuration.weight_mutation_probability, 0.7);
        instance.setConfiguration(Configuration.weight_random_mutation_probability, 0.10);
        instance.setConfiguration(Configuration.mutate_weight_max_disturbance, 0.1);

        instance.setConfiguration(Configuration.mutate_new_connection_probability, 0.03);
        instance.setConfiguration(Configuration.mutate_new_node_probability, 0.003);

        // C1 C2 and C3
        instance.setConfiguration(Configuration.distance_excess_weight, 1.0);
        instance.setConfiguration(Configuration.distance_disjoint_weight, 1.0);
        instance.setConfiguration(Configuration.distance_weights_weight, 0.4);

        // compatibility threshold for the distance function
        instance.setConfiguration(Configuration.compatibility_threshold, 1.25); // the bigger the less species
        instance.setConfiguration(Configuration.weight_mutation_probability_random, 3); // -2.0 - 2.0

        // for stagnation
        instance.setConfiguration(Configuration.generation_elimination_percentage, 0.85);
        instance.setConfiguration(Configuration.crossover_probability, 0.75);
    }
}
