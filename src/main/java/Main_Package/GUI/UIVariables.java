package Main_Package.GUI;

import Main_Package.Configuration;
import org.jfree.data.xy.XYCoordinate;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class UIVariables {
    public static TreeMap<Integer, Double> GenerationMap = new TreeMap<Integer, Double>();

    public static double GENE_DISABLE_PROBABILITY = 0.75;
    public static double weight_mutation_probability = 0.7;
    public static double weight_random_mutation_probability = 0.10;
    public static double mutate_weight_max_disturbance = 0.1;

    public static double mutate_new_connection_probability = 0.03;
    public static double mutate_new_node_probability = 0.3;

    public static double distance_excess_weight = 1.0;
    public static double distance_disjoint_weight = 1.0;
    public static double distance_weights_weight = 0.4;

    public static double compatibility_threshold = 0.8; // the bigger the less species
    public static double weight_mutation_probability_random = 3; // -2.0 - 2.0

    public static double generation_elimination_percentage =0.85;
    public static double crossover_probability = 0.75;

    // coordinates for vehicles
    public static int xCoordinate = 0;
    public static int yCoordinate = 0;
    public static boolean customTrack = false;

}
