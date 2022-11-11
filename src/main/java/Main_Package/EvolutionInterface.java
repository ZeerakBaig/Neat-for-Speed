package Main_Package;

// general interface used to get and set the configuration items
// interface has method declarations for input and output sizes of the network
// interface also has a trainTofitness function which trains a population to desired fitness

import Main_Package.NEAT.Evolution;
import Main_Package.NEAT.Genome.Genome;

import java.util.List;

public interface EvolutionInterface {

    // static function that instantiates a new evolution instance
    //Evolution class handles all the configuration items as well as fitness calculation
    public static EvolutionInterface newInstance(int input_size, int output_size, ActivationFunction activation_function,
    FitnessCalculator fitness_calculator )
    {
        return new Evolution(input_size, output_size, activation_function, fitness_calculator);
    }

    public int getInputSize();

    public int getOutputSize();

    public double getConfiguration(Configuration configuration);

    public void setConfiguration(Configuration config, double value);

    public void trainToFitness(int populationSize, double targetFitness);

    public List<Genome> getBestGenome();

    public FitnessCalculator getFitnessCalculator();

    public ActivationFunction getActivationFunction();


}
