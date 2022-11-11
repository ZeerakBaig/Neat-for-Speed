package Main_Package;

import Main_Package.NEAT.Genome.Gene;
import Main_Package.NEAT.Genome.Genome;

import java.util.HashMap;
import java.util.Map;

public class BackTraceTask {

    private  ActivationFunction activation_function;
    private  Genome genome;

    // hashmap for node input values
    private Map<Integer, Double> node_inputs = new HashMap<>();

    // constructor
    public BackTraceTask(ActivationFunction activation_function, double[] node_inputs, Genome genome)
    {
        this.activation_function = activation_function;
        //this.node_inputs = node_inputs;
        this.genome = genome;

        if(genome.getInput_nodes().size() != node_inputs.length)
        {
            System.out.println("input sizes are not equal");
        }
        // index to access an element from the double array of inputs
        int x = 0;
        for(int input : this.genome.getInput_nodes())
        {
            // adding the inputs to the map
            this.node_inputs.put(input, node_inputs[x++]);
        }

    }

    // function to get the output
    private double getNetworkOutput(Map<Integer, Double> nodeMap, int node)
    {
        // get the value of the node from the map
        Double value = nodeMap.get(node);
        if(value != null)
        {
            return value;
        }
        // for storing the sum of the inputs
        double input_sum = 0;

        // loop through all the genes
        for(Gene gene : this.genome.getGenes())
        {
            // we only calculate for the genes that are enabled
            if(gene.getNodeTo() == node && gene.isEnabled())
            {
                // check if it is an input node
                if(this.genome.isInputNode(gene.getNodeFrom()))
                {
                    // add to the input sum by taking the double value and * with the genes weight
                    input_sum += this.node_inputs.get(gene.getNodeFrom()) * gene.getWeight();
                }else
                {
                    // we would have to go further into the network using a recursive call
                    input_sum += this.getNetworkOutput(nodeMap, gene.getNodeFrom()) *gene.getWeight();
                }
            }
        }
        // finally we apply the activation function on our weighted input sum
        double activatedValue = this.activation_function.activate(input_sum);
        nodeMap.put(node, activatedValue);
        return activatedValue;
    }


    // function to calculate the final output of a genome or the network
    public double[] calculateNetworkOutput()
    {
        Map<Integer, Double> nodeMap = new HashMap<>();
        int x = 0;
        double[] output = new double[this.genome.getOutput_nodes().size()];
        for(int out : this.genome.getOutput_nodes())
        {
            output[x++] = this.getNetworkOutput(nodeMap, out);
        }
        return output;
    }



}
