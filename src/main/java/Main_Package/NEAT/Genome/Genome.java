package Main_Package.NEAT.Genome;

import Main_Package.BackTraceTask;
import Main_Package.Configuration;
import Main_Package.NEAT.ArrayUtility;
import Main_Package.NEAT.Evolution;
import Main_Package.NEAT.Species;
import Main_Package.NetworkCalculations;
import java.util.Map.Entry;
import Main_Package.NEAT.Random;
import java.util.*;

public class Genome implements Cloneable, NetworkCalculations {

    private static int counter = 0;
    private final int innovationID = counter++;


    // using a tree map to ensure that all the innovation numbers are in ascending order
    Map<Integer, Gene> genes =new TreeMap<Integer, Gene>();
    // genome will have a list of input nodes and a list of output nodes
    // we are keeping nodes as simple integers
    private List<Integer> output_nodes = new ArrayList<>();
    private List<Integer> input_nodes = new ArrayList<>();

    // for display currently not done
    public List<NodeGene> middleNodes = new ArrayList<>();

    // certain evolution functions apply to the genome
    private final Evolution evolution;
    private double fitness = -1;
    private Species species;

    // constructor for genome class
    public Genome(Evolution evolution, Species member, Integer[] inputNodes, Integer[] outputNodes)
    {
        this.evolution = evolution;
        this.species = member;

        // genome starts with a set of input nodes and output nodes before any mutation
        for(int i : inputNodes)
        {
            // function used to add input nodes
            this.addInputNode(i);
        }

        for(int o : outputNodes)
        {
            // function used to add output nodes
            this.addOutputNode(o);
        }
    }

    // function to get the innovation ID
    public int getInnovationID() {
        return innovationID;
    }

    public List<Integer> getOutput_nodes() {
        return output_nodes;
    }

    public List<Integer> getInput_nodes() {
        return input_nodes;
    }

    public Species getSpecies() {
        return species;
    }

    public Evolution getEvolution() {
        return evolution;
    }

    // function to get a collection of genes
    public Collection<Gene> getGenes() {
        return genes.values();
    }


    // function to get gene by innovation number
    public Gene getGene(int innovation_number)
    {
        // return a gene given an innovation number
        return this.genes.get(innovation_number);
    }


    // function to get an array of all the input nodes
    public Integer[] getInputs(){
        return this.input_nodes.toArray(new Integer[this.input_nodes.size()]);
    }

    // function to get an array of all the output nodes
    public Integer[] getOutputs()
    {
        return this.output_nodes.toArray(new Integer[this.output_nodes.size()]);
    }

    // function to check if the node is an input node
    public boolean isInputNode(int i)
    {
        return this.input_nodes.contains(i);
    }

    // function to check if a given node is an output node
    public boolean isOutputNode(int o)
    {
        return this.output_nodes.contains(o);
    }

    // function to check if it is hidden node
    public boolean isHiddenNode(int h)
    {
        // if the node is not present in input or output node array list then it is hidden
        return !this.output_nodes.contains(h) && !this.input_nodes.contains(h);
    }

    // function to get all the nodes or node genes
    public List<Integer> getAllNodes()
    {
        // nodes are just a list of integers
        List<Integer> allNodes = new ArrayList<>();
        for(Gene gene : this.getGenes())
        {
            if(!allNodes.contains(gene.getNodeFrom()))
            {
                allNodes.add(gene.getNodeFrom());
            }
            if(!allNodes.contains(gene.getNodeTo()))
            {
                allNodes.add(gene.getNodeTo());
            }
        }
        Collections.sort(allNodes);
        return allNodes;
    }

    // function to get the node with highest value
    public int getBestNode()
    {
        // get all the nodes
        List<Integer> allNodes = this.getAllNodes();
        // get the last item in the list (we ordered them in ascending order)
        int highest = allNodes.get(allNodes.size()-1);
        // return the highest
        return highest;
    }

    // function to check if the genome has a gene
    private boolean hasGene(int innovation_number)
    {
        return this.genes.containsKey(innovation_number);
    }

    // function to get the highest innovation number
    public int getHighestInnovation()
    {
        if(this.genes.isEmpty())
        {
            System.out.println("No genes are present");
        }
        // getting the iterator for Gene class
        Iterator<Gene> geneIterator = this.genes.values().iterator();
        Gene lastGene = null;
        while(geneIterator.hasNext())
        {
            lastGene = geneIterator.next();
        }
        // cannot be null
        if(lastGene == null)
        {
            throw new AssertionError();
        }

        return lastGene.getInnovation_number();


    }


    // function to add a gene
    public void addGene(Gene gene, Genome parentA, Genome parentB)
    {
        if(this.fitness != -1)
        {
            System.out.println("addGene() should be called before getFitness");
        }
        if(this.genes.containsKey(gene.getInnovation_number()))
        {
            //print the details of the gene that is already present
            System.out.println(this.toString());
            System.out.println("Gene already exists in the genome with innovation number" + gene.getInnovation_number());
        }

        //getting the clone of the gene
        gene = gene.clone();
        if(parentA != null && parentB != null)
        {
            // check if both the parents have this gene
            if(parentA.hasGene(gene.getInnovation_number()) && parentB.hasGene(gene.getInnovation_number()))
            {
                // both parents have the same gene
                // there is a possibility that the gene disabled in one of the parents is actually disabled
                boolean d1 = !parentA.getGene(gene.getInnovation_number()).isEnabled();
                boolean d2 = !parentB.getGene(gene.getInnovation_number()).isEnabled();

                // only one of them wil be disabled
                if((!d1 && d2) || (d1 && !d2))
                {
                    boolean disabled = Random.success(this.getEvolution().getConfiguration(Configuration.GENE_DISABLE_PROBABILITY));
                    gene.setEnabled(!disabled);
                }

            }

        }
        // cloning it to ensure that we have a new instance
        this.genes.put(gene.getInnovation_number(), gene);


    }





    // function to get  a list of all the hidden nodes
    public List<Integer> getHidden_nodes(){
        List<Integer> hiddenNodes = new ArrayList<>();
        for(int node : this.getAllNodes())
        {
            if(!this.isInputNode(node) && !this.isOutputNode(node))
            {
                hiddenNodes.add(node);
            }
        }
        return hiddenNodes;
    }


    // function to get a list of nodes
    public List<Integer> getNodes(boolean includeInput, boolean includeHidden, boolean includeOutput)
    {
        List<Integer> nodes = new ArrayList<>();
        for(int i : this.getAllNodes())
        {
            if(this.isInputNode(i) && !includeInput)
            {
                continue;
            }
            if(this.isHiddenNode(i) && !includeHidden)
            {
                continue;
            }
            if(this.isOutputNode(i) && !includeOutput)
            {
                continue;
            }

            // else add to the nodes list
            nodes.add(i);
        }
        return nodes;
    }


    // method to add output  nodes to the array list
    private void addOutputNode(int o) {
        if(this.fitness != -1)
        {
            System.out.println("addOutputNode() should be called before getting the fitness");
        }
        if(this.output_nodes.contains(o))
        {
            System.out.println("output node already exists");
        }
        // else add it to the arraylist of output nodes
        this.output_nodes.add(o);
    }

    // method to add input nodes to the array list
    private void addInputNode(int i) {
        if(this.fitness != -1)
        {
            System.out.println("addInputNode() should be called before getting the fitness");
        }
        if(this.input_nodes.contains(i))
        {
            System.out.println("input node already exists");
        }
        // else add the new node to the input node array list
        this.input_nodes.add(i);
    }


    // method to set the species of the genome
    public void setSpecies(Species species)
    {
        if(this.fitness != -1)
        {
            System.out.println("setSpecies() should be called before getting the fitness");
        }
        this.species = species;
    }

    // function to get all the active connection genes
    public Collection<? extends ConnectionGene> getActiveConnectionGenes()
    {
        // creating a new set of connection genes
        Set<ConnectionGene> connections = new HashSet<>();
        for(Gene gene : this.getGenes())
        {
            if(gene.isEnabled())
            {
                connections.add(new ConnectionGene(gene.getNodeFrom(), gene.getNodeTo()));
            }

        }
        return connections;

    }

    // function to get all the connection genes
    public List<ConnectionGene> getAllConnectionGenes()
    {
        List<ConnectionGene> total_connections = new ArrayList<>();
        for(Gene gene : this.getGenes())
        {
            total_connections.add(new ConnectionGene(gene.getNodeFrom(), gene.getNodeTo()));
        }
        return total_connections;
    }



    // method to clone a genome
    @Override
    public Genome clone()
    {
        // create a new genome with the input nodes and the output nodes
        Genome new_genome = new Genome(this.evolution, this.getSpecies(), this.getInputs(), this.getOutputs());
        // copying or cloning the values of the gene map
        new_genome.genes = new TreeMap<>();
        for(Entry<Integer, Gene> x : this.genes.entrySet())
        {
            new_genome.genes.put(x.getKey(), x.getValue().clone());
        }

        // creating a new arraylist of the input and the output nodes and assigning to the cloned genome
        new_genome.input_nodes = new ArrayList<>(this.input_nodes);
        new_genome.output_nodes = new ArrayList<>(this.output_nodes);
        return new_genome;

    }

    // Crossover Related methods START*******************************************************************************************
    public void manageDuplicateConnections(){
        if(this.fitness != -1){
            System.out.println("ManageDuplicates() should be called before getFitness()");
        }
        // for each specie in the specie set
        for(Species  sps: this.getEvolution().getPopulation_manager().getCurrent_population().getSpecieSet())
        {
            // for each genome in the specie group
            for(Genome genome : sps.getMembers())
            {
                // get the connections for the current genomes and the comparable genome
                List<ConnectionGene> connectionA = this.getAllConnectionGenes();
                List<ConnectionGene> connectionB = genome.getAllConnectionGenes();

                if(ArrayUtility.equals(connectionA, connectionB))
                {
                    // get the iterator of where to clone from
                    Iterator<Gene> to_clone_from = new ArrayList<>(genome.genes.values()).iterator();
                    // iterator for what to replace (replacing the current genomes genes)
                    Iterator<Gene> to_replace = new ArrayList<>(this.genes.values()).iterator();

                    //iterate through both
                    while(to_clone_from.hasNext() && to_replace.hasNext())
                    {
                        Gene geneFrom = to_clone_from.next();
                        Gene geneTo = to_replace.next();

                        // get the old innovation number
                        int old_innovation_number = geneTo.getInnovation_number();
                        int changeTo = geneFrom.getInnovation_number();

                        //remove the old innovation number
                        Gene old = this.genes.remove(old_innovation_number);
                        // set the new innovation number to the one taken from the replacement
                        old.setInnovation_number(changeTo);
                        // put the old gene back with the replacement
                        this.genes.put(old.getInnovation_number(), old);
                    }
                    if(to_clone_from.hasNext() || to_replace.hasNext())
                    {
                        throw new AssertionError();
                    }
                    return;
                }
            }
        }

    }
    // need to make sure that the calculate fitness function is called before this
    public static void crossOver(Genome genomeA, Genome genomeB){
        if(!genomeA.getSpecies().equals(genomeB.getSpecies()))
        {
            System.out.println("They do not form part of same species");
        }

        // get the fitness for both the genomes
        double genomeAFitness = genomeA.getFitness();
        double genomeBFitness = genomeB.getFitness();

        Genome dominant;
        Genome weaker;
        // check which parent is fitter (genome A will always be fitter)
        if(genomeAFitness > genomeBFitness)
        {
            dominant = genomeA;
            weaker = genomeB;
        }else
        {
            dominant = genomeB;
            weaker = genomeA;
        }

        // function to actually perform the crossover
        Genome offspring = crossOverDominant(dominant, weaker);
        genomeA.getEvolution().getPopulation_manager().getCurrent_population().addGenome(offspring);

    }

    public static Genome crossOverDominant(Genome dominant_genome, Genome other_genome)
    {
        if(dominant_genome.getGenes().isEmpty() || other_genome.getGenes().isEmpty())
        {
            System.out.println("The genomes cannot be empty while crossing over");
        }
        // need to make sure that crossover takes place between genomes of same species
        if(!dominant_genome.getSpecies().equals(other_genome.getSpecies()))
        {
            System.out.println("They do not form part of same species");
        }

        // variable to check how far the genomes matched
        int shared_length = -1;
        //not providing a condition for the for loop
        // loop will only break once the genes stop matching
        for(int x = 1;;x++)
        {
            if(x > 100000)
            {
                throw new RuntimeException();
            }
            if(dominant_genome.hasGene(x) && other_genome.hasGene(x))
            {
                shared_length = x;
            }else
            {
                break;
            }
        }

        if(shared_length == -1)
        {
            throw  new AssertionError();
        }
        // we will use the dominant genes inputs and outputs to create the new genome
        Genome new_genome = new Genome(dominant_genome.getEvolution(), null, dominant_genome.getInputs(),dominant_genome.getOutputs());
        for(int x = 1 ; x <= dominant_genome.getHighestInnovation(); x++)
        {
            // if both of them have the same genes then we randomly select
            if(dominant_genome.hasGene(x)){
            if(other_genome.hasGene(x))
            {
                // randomly select the gene either from dominant or weak
                new_genome.addGene(Random.random(new Gene[]{dominant_genome.getGene(x), other_genome.getGene(x)}), dominant_genome, other_genome);
            }else
            {
                // else we just take the dominant genome
                new_genome.addGene(dominant_genome.getGene(x), dominant_genome, other_genome);
            }
            }
        }

        // adjust the duplicates
        new_genome.manageDuplicateConnections();

        // mutate
        new_genome.mutate();

        return new_genome;

    }

    // function that calls the mutate method from the mutation handler class
    public void mutate()
    {
        MutationHandler mutateHandler = new MutationHandler(this);
        mutateHandler.mutate();
    }

    // distance function used for speciation
    public static double distance(Genome genomeA, Genome genomeB){
        // find the genome with the greater innovation number, we will always have the first genome to be higher
        // innovation numbers incremnt when new genes are added, therefore the length is the same as highest innovation
        int genomeA_length = genomeA.getHighestInnovation();
        int genomeB_length = genomeB.getHighestInnovation();

        // declaring the longest and the shortest genome
        Genome longestGenome;
        Genome shortestGenome;
        // if statement to check and set the longest gene
        if(genomeA_length > genomeB_length)
        {
            longestGenome = genomeA;
            shortestGenome = genomeB;
        } else
        {
            longestGenome = genomeB;
            shortestGenome = genomeA;
        }

        // getting the lengths again
        int longest_genome_length = longestGenome.getHighestInnovation();
        int shortest_genome_length = shortestGenome.getHighestInnovation();

        // counters for disjoint and excess genes
        double excessGenes = 0;
        double disjointGenes = 0;
        List<Double> gene_weights= new ArrayList<>();

        // loop through the longest genome
        for(int x = 1 ; x < longest_genome_length ; x++)
        {
            // get the longest and the shortest gene
            Gene la = longestGenome.getGene(x);
            Gene sa = shortestGenome.getGene(x);

            // check if there is a matching gene
            if(la != null && sa != null)
            {
                // gene is present in both the genomes
                double distance =Math.abs(la.getWeight()- sa.getWeight());
                gene_weights.add(distance);
            }

            // checking if the gene is only present in one of the parent
            if((la ==null && sa != null)  || (la != null && sa ==null))
            {
                // it is only present in one of the genome
                if(x < shortest_genome_length)
                {
                    disjointGenes++;
                }else if(x> shortest_genome_length)
                {
                    // there are more genes in the dominant genome
                    excessGenes++;
                }
            }

        }

        double total_weight = 0;
        double size = 0;
        for(double w : gene_weights)
        {
            total_weight += w;
            size++;
        }
        double averageWeightDistance = total_weight/ size;
        // get the size of the longest genome for normalization factor
        double N =longestGenome.getGenes().size();
        // according to the neat paper, if the length of the gene consist of less than 20 genes it can be set to 1
        // It normalizes the genome size
        if(N< 20)
        {
            N= 1;
        }

        double C1 = genomeA.getEvolution().getConfiguration(Configuration.distance_excess_weight);
        double C2 = genomeA.getEvolution().getConfiguration(Configuration.distance_disjoint_weight);
        double C3 = genomeA.getEvolution().getConfiguration(Configuration.distance_weights_weight);
        double finalDistance =  ((C1* excessGenes)/N) + ((C2 * disjointGenes)/N) + (C3 * averageWeightDistance);
        return finalDistance;
    }


    // function to calculate fitness
    public double calculateFitness()
    {
        this.fitness = this.evolution.getFitnessCalculator().getFitness(this);
        int x = 0;
        // setting the current fitness to the highest fitness of the species
        if(this.fitness >this.getSpecies().getHighest_fitness())
        {
            this.getSpecies().setHighest_fitness(this.fitness);
        }
        return this.fitness;
    }
    // implementing the get fitness function from the interface
    public double getFitness()
    {
        if(this.fitness == -1)
        {
            return calculateFitness();
        }
        return this.fitness;
    }

    // Crossover Related methods END*******************************************************************************************

    // method to get all the middle nodes for display


    public List<NodeGene> getMiddleNodes() {
        return middleNodes;
    }

    @Override
    public double[] calculate(double[] networkInput) {
        return  new BackTraceTask(this.evolution.getActivationFunction(),networkInput , this).calculateNetworkOutput();
    }

    // method to print the details of a gene
    @Override
    public String toString()
    {
        StringBuilder geneString = new StringBuilder();
        for(Entry<Integer, Gene> gen : this.genes.entrySet())
        {
            Gene gene = gen.getValue();
            geneString.append("[ " + gen.getKey() + "=" + gene.getInnovation_number() + " , " + gene.getNodeFrom() + " , " + gene.getNodeTo() + " , " + gene.getWeight() + " " + gene.isEnabled() + " ] ");
        }
        return geneString.toString();
    }

    // class for sorting the genomes in a descending order
    public static class GenomeSorter implements Comparator<Genome>{

        @Override
        public int compare(Genome o1, Genome o2) {
            double fitness1 = o1.getFitness();
            double fitness2 = o2.getFitness();

            if(fitness1>fitness2)
            {
                return -1;
            }
            if(fitness1 < fitness2)
            {
                return 1;
            }
            return 0;
        }
    }

}
