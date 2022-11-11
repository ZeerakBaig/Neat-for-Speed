package Main_Package;

// contains the configuration items for out neat library
public enum Configuration {

    // Probability that a child will get a disabled gene if one of the parents has a gene disabled
    GENE_DISABLE_PROBABILITY(0.75),

    // Probability that an entirely new ransom weigh is generated
    weight_random_mutation_probability(0.10),

    // Probability that gene is mutated when copied
    weight_mutation_probability(0.80),

    // Probability that a new connection gene is made as a result of mutation
    mutate_new_connection_probability(0.05),

    // Probability that a new Node will be created as a result of mutation
    mutate_new_node_probability(0.03),

    // weight will be distributed by a random value
    mutate_weight_max_disturbance(0.25),

    // C1 in distance formula
    distance_excess_weight(1.0),

    // C2 in distance function
    distance_disjoint_weight(1.0),

    //C3 in distance function
    distance_weights_weight(1.0),

    // compatibility threshold for distance function
    compatibility_threshold(0.8),

    // percentage of genomes that will be eliminated from the new generation
    generation_elimination_percentage(0.90),

    // probability that crossover will take place
    crossover_probability(0.75),

    // probability that a weight mutation will take place or not
    weight_mutation_probability_random(5.0),;



    private double defaultConfig;

    private Configuration(double defaultConfig)
    {
        this.defaultConfig = defaultConfig;
    }

    public double getDefaultConfig() {
        return defaultConfig;
    }





}
