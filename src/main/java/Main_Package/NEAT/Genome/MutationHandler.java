package Main_Package.NEAT.Genome;

// class handles all the types of mutations
// we are keeping 3 mutations for now, Node mutation( adding a new node)
// adding a new connection between 2 existing nodes
// changing the weight of a connection

import Main_Package.Configuration;
import Main_Package.NEAT.Random;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// class that implements all the mutation methods for a genome
public class MutationHandler {

    //genome that is going to be mutated
    private final Genome genome;

    public MutationHandler(Genome genome)
    {
        this.genome = genome;
    }

    // mutate method with all the three different types of mutations
    public void mutate()
    {
        // checking against normal configuration items
        if(Random.success(this.genome.getEvolution().getConfiguration(Configuration.mutate_new_node_probability)))
        {
            mutateNode();
        }
        // adding a new link with a random weight between two existing nodes by first looking for unconnected nodes
        if(Random.success(this.genome.getEvolution().getConfiguration(Configuration.mutate_new_connection_probability)))
        {
            mutateConnection();
        }
        // changing the existing weight values (weight shift mutation)
        if(Random.success(this.genome.getEvolution().getConfiguration(Configuration.weight_mutation_probability)))
        {
            mutateWeight();
        }


    }

    // function to get all the inputs to the node or gets all the node froms
    public List<Integer> getInputs(Genome genome, int node)
    {
        List<Integer> nodesFrom = new ArrayList<>();
        for(Gene gene : genome.getGenes())
        {
            // check if it is a to node
            if(gene.getNodeTo() == node)
            {
                // given that it is a to node then get the from node and add
                nodesFrom.add(gene.getNodeFrom());
            }
        }
        return nodesFrom;
    }

    // function to check if a connection is recurring,specifically used for the new connection added
    public boolean isRecurring(ConnectionGene connectionWith)
    {
        // clone the genome so that we dont affect the genes of the genome
        Genome temp_genome = this.genome.clone();
        if(connectionWith != null)
        {
            Gene gene = new Gene(temp_genome.getHighestInnovation()+1, 0, true, connectionWith.getNodeFrom(), connectionWith.getNodeTo());
            temp_genome.addGene(gene, null, null);
        }
        boolean recurrent = false;
        for(int hiddenNode : temp_genome.getHidden_nodes())
        {
            if(isRecurring(new ArrayList<>(), temp_genome, hiddenNode))
            {
                recurrent = true;
            }
        }
        return recurrent;

    }

    //function to check if a node is recurring in a list of nodes in a genome
    public boolean isRecurring(List<Integer> pathList, Genome genome, int node)
    {
        if(pathList.contains(node))
        {
            return true;
        }
        pathList.add(node);

        boolean recurrent = false;
        for(int nodeFrom : this.getInputs(genome, node))
        {
            if(!genome.isInputNode(nodeFrom))
            {
                if(this.isRecurring(pathList, genome, nodeFrom))
                {
                    recurrent = true;
                }
            }
        }
        return recurrent;
    }

    // functions for different kinds of mutation
    // function to mutate or add a node
    public void mutateNode()
    {
        // get a random gene from a list of genes inside the genome
        // we will choose a random gene for mutation
        Gene random_gene = Random.random(new ArrayList<>(this.genome.getGenes()));
        // disable the gene
        random_gene.setEnabled(false);

        // get the to and from node of the random gene
        int nodeGeneFrom = random_gene.getNodeFrom();
        int nodeGeneTo= random_gene.getNodeTo();
        // get the next innovation number basically an increment to current innovation number
        this.genome.getEvolution().getNextInnovationNumber();

        // for displaying the middle node
        NodeGene middleNode = new NodeGene();

        // setting the new innovation number (we will simply add 1 to thw best node)
        // best node is the last node
        int newInnovationNode = this.genome.getBestNode() +1;
        // create a new connection gene from the node from to the new node without changing the parents
        this.genome.addGene(new Gene(this.genome.getEvolution().getNextInnovationNumber(),1D, true, nodeGeneFrom, newInnovationNode), null,null);


        // creating a new connection gene from the new node to the nodeTo (existing node to the right)
        //O------------------------O-------------------------O
        this.genome.addGene(new Gene(this.genome.getEvolution().getNextInnovationNumber(), random_gene.getWeight(), true, newInnovationNode, nodeGeneTo), null,null);

        // adding the middle node
    /*    middleNode.setX_coordinate(0.5);
        middleNode.setY_coordinate(0.5);
        this.genome.getMiddleNodes().add(middleNode);*/

       /* this.genome.getGene(newInnovationNode).setX_coordinate(this.genome.getGene(nodeGeneFrom).getX_coordinate()+this.genome.getGene(nodeGeneTo).getX_coordinate());
        this.genome.getGene(newInnovationNode).setY_coordinate(this.genome.getGene(nodeGeneFrom).getY_coordinate()+this.genome.getGene(nodeGeneTo).getY_coordinate());
*/
    }

    //function to mutate a connection gene( adding a new connection)
    public void mutateConnection()
    {
        try
        {
            // we pick a random connection instead of looping through all the connections
            Collection<? extends ConnectionGene> current_connections = this.genome.getAllConnectionGenes();
            int numAttempts = 0;
            //we will try for 40 turns to see if the connection is new or not
            ConnectionGene new_connection = null;
            do{
                {
                    if (numAttempts++ > 40) {
                        throw new MutationFailedException("New connection could not be created");
                    }
                }

                int nodeFrom =Random.random(this.genome.getNodes(true, true, false));
                List<Integer> leftOverNodes = this.genome.getNodes(false, true, true);
                leftOverNodes.remove((Object) nodeFrom);
                if(leftOverNodes.isEmpty())
                {
                    continue;
                }

                int nodeTo = Random.random(leftOverNodes);
                new_connection = new ConnectionGene(nodeFrom, nodeTo);

            }while (new_connection == null || new_connection.getNodeFrom() == new_connection.getNodeTo() || current_connections.contains(new_connection)|| isRecurring(new_connection));

            // add the new gene to the genome
            genome.addGene(new Gene(this.genome.getEvolution().getNextInnovationNumber(), Random.random(-1,1), true, new_connection.getNodeFrom(), new_connection.getNodeTo()), null, null);

        }catch(MutationFailedException e)
        {
            //System.out.println("Something went wrong with new link mutation");
        }
    }

    // function to mutate existing weight
    public void mutateWeight()
    {
        if(Random.success(this.genome.getEvolution().getConfiguration(Configuration.weight_random_mutation_probability)))
        {
            // assign a new random value
            for(Gene gene : this.genome.getGenes())
            {
                double range =this.genome.getEvolution().getConfiguration(Configuration.weight_mutation_probability_random);
                // setting a random weight for the gene
                gene.setWeight(Random.random(-range, range));
            }
        }else
        {
            for(Gene gene : this.genome.getGenes())
            {
                double disturbance = this.genome.getEvolution().getConfiguration(Configuration.mutate_weight_max_disturbance);
                double uniform = Random.random(-disturbance, disturbance);
                gene.setWeight(gene.getWeight() + uniform);
            }
        }
    }





}
