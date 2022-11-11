package Main_Package.NEAT;

import Main_Package.ActivationFunction;
import Main_Package.Configuration;
import Main_Package.EvolutionInterface;
import Main_Package.FitnessCalculator;
import Main_Package.NEAT.Genome.Gene;
import Main_Package.NEAT.Genome.Genome;

import java.util.*;

public class Evolution implements EvolutionInterface {

    // amount of input nodes
    private final int input_size;
    // list of the best genomes, this is for elitism
    public List<Genome> bestGenomeList = new ArrayList<>();

    // amount of output nodes
    private final int output_size;

    // Map of configuration items
    private final Map<Configuration, Double> configuration = new HashMap<Configuration, Double>() {
        {
            for (Configuration config : Configuration.values()) {
                // add the default configuration items with values to the hashmap
                this.put(config, config.getDefaultConfig());
            }
        }
    };

    private FitnessCalculator fitness_calculator;
    private int current_innovation_number = 1;
    private final ActivationFunction activation_function;
    private final PopulationManager population_manager = new PopulationManager(this);


    //Constructor for out evolution class
    public Evolution(int input_size, int output_size, ActivationFunction activation_function, FitnessCalculator fitness_calculator) {
        this.input_size = input_size;
        this.output_size = output_size;
        this.activation_function = activation_function;
        this.fitness_calculator = fitness_calculator;
    }

    // normal getters and setters
    public int getNextInnovationNumber()
    {
        int next_innovation_number = current_innovation_number++;
        return next_innovation_number;
    }

    public PopulationManager getPopulation_manager() {
        return population_manager;
    }

    // implementing all the interface methods
    @Override
    public int getInputSize() {
        return input_size;
    }

    @Override
    public int getOutputSize() {
        return output_size;
    }

    @Override
    public double getConfiguration(Configuration configuration) {
        return this.configuration.get(configuration);
    }

    @Override
    public void setConfiguration(Configuration config, double value) {
        this.configuration.put(config, value);
    }

    @Override
    public void trainToFitness(int populationSize, double targetFitness) {
        this.population_manager.initialize(populationSize);
        while(true)
        {
            this.population_manager.newGeneration();
            Genome bestGenome = this.population_manager.getLatest_fitness();
            this.getFitnessCalculator().generationEnded(bestGenome);

            if(bestGenome.getFitness() >= targetFitness)
            {
                Set<Integer> hidden_nodes = new HashSet<>();
                int enabled_connections = 0;

                populateBestGenomeNodes(bestGenome, hidden_nodes,enabled_connections);

                System.out.println("Final Solution with a fitness of " + bestGenome.getFitness() + " in generation " + this.population_manager.getCurrent_generation());
                System.out.println("The system had " + hidden_nodes.size() + " hidden units and " + enabled_connections + " enabled connections");
                for (Gene gene : bestGenome.getGenes()) {
                    System.out.println("    " + gene.toString());
                }
                bestGenomeList.add(bestGenome);
                return;
            }
        }

    }

    //function to get the nodes(including hidden for the best genome
    public void populateBestGenomeNodes(Genome bestGenome, Set<Integer> hidden_nodes, int enabled_connections)
    {
        for(Gene g : bestGenome.getGenes())
        {
            if(g.isEnabled())
            {
                enabled_connections++;
            }

            {
                int node = g.getNodeFrom();
                if(!bestGenome.isInputNode(node) &&  !bestGenome.isOutputNode(node))
                {
                    if(!hidden_nodes.contains(node))
                    {
                        hidden_nodes.add(node);
                    }
                }
            }

            {
                int node = g.getNodeTo();
                if(!bestGenome.isInputNode(node)  && !bestGenome.isOutputNode(node))
                {
                    if(!hidden_nodes.contains(node))
                    {
                        hidden_nodes.add(node);
                    }
                }
            }
        }
    }

    @Override
    public FitnessCalculator getFitnessCalculator() {
        return this.fitness_calculator;
    }

    @Override
    public ActivationFunction getActivationFunction() {
        return this.activation_function;
    }

    // function to get the best genome
    @Override
    public List<Genome> getBestGenome(){

        return  bestGenomeList;
    }




}

