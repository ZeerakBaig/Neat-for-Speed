package Main_Package.GUI;

import Main_Package.NEAT.Genome.DisplayConnection;
import Main_Package.NEAT.Genome.Gene;
import Main_Package.NEAT.Genome.Genome;

import java.util.*;

import Main_Package.NEAT.Genome.NodeGene;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

public class DisplayGenome {

    HashMap<Double, Double> finalConnections = new HashMap<>();



    private HashMap<Integer, Integer> nodePair = new HashMap<>();
    // for display purposes
    private Genome display_genome;
    // to get all the connection history of the genome
    private List<DisplayConnection> lstConnections = new ArrayList<>();
    // to collect all the nodes for the genome
    private List<Integer> inputNodes = new ArrayList<>();
    private List<Integer> middleNodes = new ArrayList<>();
    private List<Integer> outputNodes = new ArrayList<>();
    // to generate and add all the node genes
    private List<NodeGene> allNodes = new ArrayList<>();
    // map to specify whether a node is input or not
    private HashMap<String, NodeGene> nodeMap = new HashMap<>();

    // to capture all the genes in the genome
    private List<Gene> geneList = new ArrayList<>();

    public DisplayGenome(Genome genome, int input_size, int output_size)
    {
        this.display_genome = genome;
        instantiateArrayLists();
        /*System.out.println("Outputsize: "+ output_size);
        System.out.println("Outputsize: "+ input_size);*/
        int counter = 0;

        for(int x = 0 ; x < input_size; x++)
        {
            NodeGene nGene = new NodeGene();
            nGene.setX_coordinate(0.1);
            nGene.setY_coordinate((x+1)/(double)(input_size+1));
            this.allNodes.add(nGene);
            this.nodeMap.put("Input", nGene);
        }

        for(int x = 0 ; x < output_size; x++)
        {
            NodeGene nGene = new NodeGene();
            nGene.setX_coordinate(0.9);
            nGene.setY_coordinate((x+1)/(double)(output_size+1));
            this.allNodes.add(nGene);
            this.nodeMap.put("Output", nGene);
        }
        int totalMiddle = 0;
        for(int x = 0 ; x < this.middleNodes.size(); x++)
        {
            NodeGene middleGene = new NodeGene();
            middleGene.setY_coordinate((x+1)/(double)(this.middleNodes.size()+1));
            middleGene.setX_coordinate(0.5);//(Math.random()*(0.7-0.5))+0.5);
            this.allNodes.add(middleGene);
            this.nodeMap.put("Middle", middleGene);

        }

        for(Gene g : geneList)
        {
            nodePair.put(g.getNodeFrom(), g.getNodeTo());
        }






    }

    public void instantiateArrayLists()
    {
        for(Gene g : this.display_genome.getGenes())
        {
            geneList.add(g);
        }
        for(Gene g : geneList)
        {
            System.out.println("THe innovation Number: " + g.getInnovation_number());
        }

        // instantiating the arraylists
        for(int Genes : display_genome.getInput_nodes())
        {
            inputNodes.add(Genes);
        }

        for(int Genes : display_genome.getHidden_nodes())
        {
            middleNodes.add(Genes);
        }

        for(int Genes : display_genome.getOutput_nodes())
        {
            outputNodes.add(Genes);
        }

        // adding all the connections
        for(Gene g : this.display_genome.getGenes())
        {
            lstConnections.add(new DisplayConnection(g.getNodeFrom(), g.getNodeTo(),g.getWeight(), g.isEnabled()));
        }


    }

    public Genome getDisplay_genome() {
        return display_genome;
    }

    public void setDisplay_genome(Genome display_genome) {
        this.display_genome = display_genome;
    }

    public List<Integer> getInputNodes() {
        return inputNodes;
    }

    public void setInputNodes(List<Integer> inputNodes) {
        this.inputNodes = inputNodes;
    }

    public List<Integer> getMiddleNodes() {
        return middleNodes;
    }

    public void setMiddleNodes(List<Integer> middleNodes) {
        this.middleNodes = middleNodes;
    }

    public List<Integer> getOutputNodes() {
        return outputNodes;
    }

    public void setOutputNodes(List<Integer> outputNodes) {
        this.outputNodes = outputNodes;
    }

    public List<Gene> getGeneList() {
        return geneList;
    }

    public void setGeneList(List<Gene> geneList) {
        this.geneList = geneList;
    }

    public List<NodeGene> getAllNodes() {
        return allNodes;
    }

    public List<DisplayConnection> getLstConnections() {
        return lstConnections;
    }
}
