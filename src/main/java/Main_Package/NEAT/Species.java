package Main_Package.NEAT;

import Main_Package.Configuration;
import Main_Package.NEAT.Genome.Genome;
import Main_Package.NEAT.Genome.Genome.GenomeSorter;

import java.util.*;

// species are a collection of genomes that are closely related
// fitness of a genome depends on the amount of genomes in a specie
public class Species {
    public static int num_species = 0;
    private final int ID = num_species++;
    private Genome representative;
    private final Set<Genome> members = new HashSet<>();
    private double highest_fitness = 0;
    // number of failed generations
    private double failed_generations = 0;

    public Species(Genome genome)
    {
        // we are going to pass a representative to the specie class
        this.representative = genome;
        representative.setSpecies(this);
    }

    //checking if the genome is compatible with a specie group
    public boolean isCompatible(Genome genome){
        return Genome.distance(this.representative, genome) <= genome.getEvolution().getConfiguration(Configuration.compatibility_threshold);
    }

    // function to remove a genome
    public void remove(Genome g)
    {
        this.members.remove(g);
    }
    // updating the representative of a specie set
    public void update()
    {
        // set a random representative from the set of members (cars)
        this.setRepresentative(Random.random(this.getMembers()));
    }


    // function to get average fitness
    public double getAverageFitness()
    {
        double total_fitness = 0;
        double counter = 0;
        for(Genome g : this.members){
            total_fitness += g.getFitness();
            counter++;
        }
        double average_fitness = total_fitness/counter;
        return average_fitness;
    }
    // function to get the best performing list of genome
    // we sort in desceding order therefore the highest fitness comes first
    public List<Genome> getBestPerforming()
    {
        List<Genome> best_performing = new ArrayList<>();
        // get all the genomes and add to a list
        for(Genome genome  : this.members)
        {
            best_performing.add(genome);
        }
        // sort by decending order so that the highest is in front
        Collections.sort(best_performing, new GenomeSorter());
        return best_performing;
    }



    // getters and setters
    public static int getNum_species() {
        return num_species;
    }

    public static void setNum_species(int num_species) {
        Species.num_species = num_species;
    }

    public int getID() {
        return ID;
    }

    public Genome getRepresentative() {
        return representative;
    }

    public void setRepresentative(Genome representative) {
        this.representative = representative;
    }

    public Set<Genome> getMembers() {
        return members;
    }

    public double getHighest_fitness() {
        return highest_fitness;
    }

    public void setHighest_fitness(double highest_fitness) {
        this.highest_fitness = highest_fitness;
        this.failed_generations = 0;
    }

    public double getFailed_generations() {
        return failed_generations;
    }

    public void setFailed_generations(double failed_generations) {
        this.failed_generations = failed_generations;
    }
}
