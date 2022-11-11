package Main_Package.NEAT;

import Main_Package.NEAT.Genome.Genome;

import java.util.ArrayList;
import java.util.List;

// has a configuration for the evolution purpose and a list of species
public class Population {
    private final Evolution evolution;
    private final List<Species> specieSet = new ArrayList<>();

    // constructor for the population class
    public Population(Evolution evolution)
    {
        this.evolution = evolution;
    }

    // function to get the size of the population
    public int getPopulationSize(){
        return this.evolution.getPopulation_manager().getPopulation_size();
    }

    public Evolution getEvolution() {
        return evolution;
    }

    public List<Species> getSpecieSet() {
        return specieSet;
    }

    // function to add a genome to a population
    public void addGenome(Genome genome)
    {
        // check which specie the genome belongs to using the classify function
        Species species = this.classify(genome);
        // add the genome to the specie
        species.getMembers().add(genome);
    }

    // function used to set the specie of the genome
    // if a new specie is created then we set the genome as representative
    public Species classify(Genome genome)
    {
        for(Species s : this.getSpecieSet())
        {
            if(s.isCompatible(genome))
            {
                genome.setSpecies(s);
                return s;
            }
        }
        // if the genome is not compatible with any specie then create a new specie
        Species sps = new Species(genome);
        // add the specie to the specie set
        this.getSpecieSet().add(sps);
        return sps;
    }

    // function to return the best performing genome in current population
    public Genome getBestPerformingGenome()
    {
        Genome best_performing = null;
        double best_fitness = -1;

        for(Species s : this.specieSet)
        {
            for(Genome g : s.getMembers())
            {
                if(best_performing == null || g.getFitness() > best_fitness)
                {
                    best_performing = g;
                    best_fitness = g.getFitness();
                }
            }
        }
        return best_performing;
    }



}
