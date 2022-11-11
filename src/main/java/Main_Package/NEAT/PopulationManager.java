package Main_Package.NEAT;

import Main_Package.Configuration;
import Main_Package.GUI.UIVariables;
import Main_Package.NEAT.Genome.Gene;
import Main_Package.NEAT.Genome.Genome;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

// class to manage the population related functions
public class PopulationManager{


    // variables to store visualisation data************************************
    private ArrayList<Double> averageFitnessList = new ArrayList<>();
    public ArrayList<Integer> generationList = new ArrayList<>();
    public ArrayList<Integer> activeSpecies = new ArrayList<>();
    public ArrayList<Integer> specieSizeList = new ArrayList<>();
    public ArrayList<Integer> bestGenomeList = new ArrayList<>();
    public ArrayList<Double> genomeFitnessList = new ArrayList<>();
    public ArrayList<Integer> genomeSpecieID = new ArrayList<>();
    public ArrayList<Integer> genomeSpecieMembers = new ArrayList<>();
    // for average fitness for each specie visualization only
    private ArrayList<Integer> specieIDList = new ArrayList<>();
    private ArrayList<Double> averageSpecieFitnessList = new ArrayList<>();
    private ArrayList<Integer> stagnationPerSpecie = new ArrayList<>();
    // variables to store visualisation data************************************

    private int current_generation = 1;
    private final Evolution evolution;
    private final Population current_population;
    private int population_size = 500;
    private Genome latest_fitness;

    public PopulationManager(Evolution evolution)
    {
        this.evolution = evolution;
        this.current_population = new Population(this.evolution);
    }

    // getters and setters
    public int getCurrent_generation() {
        return current_generation;
    }


    // function to create a new generation
    public void newGeneration()
    {
        // increase the number of generation by one because we adding a new generation
        this.current_generation++;
        // we start by calling the getBestPerforming for every specie
        // this results in calculate fitness to be executed
        Map<Species, List<Genome>> best_performing = new HashMap<>();
        for(Species sps: this.getSpecies())
        {
            best_performing.put(sps, sps.getBestPerforming());
        }

        // calculate the total average fitness of the species
        double sum = 0;
        for(Species sps : this.getSpecies())
        {
            sum += sps.getAverageFitness();
        }
        // visualization per specie average fitness

        // eliminating the worst performing genomes from every specie
        // declaring a map of specie and genome to be eliminated
        HashMap<Species, Genome> toVipe = new HashMap<>();
        // get the iterator for the species
        Iterator<Species> itSps = this.getSpecies().iterator();

        //stagnation
        stagnate(itSps,best_performing, sum);

            // helper function to get the updated size of the specie members and populating arraylists for visualisation
        newGenHelper();


        if(this.getSpecies().isEmpty())
        {
            System.out.println("All the species died");
        }

        int population_size = 0;
        // declaring a hashmap for old genomes of a specie
        Map<Species, Set<Genome>> old_members = new HashMap<>();
        for(Species sps : this.getSpecies())
        {
            old_members.put(sps, new HashSet<>(sps.getMembers()));
            sps.getMembers().clear();
            Genome vipe = toVipe.get(sps);
            if(vipe != null)
            {
                sps.getMembers().add(vipe);
                population_size++;
            }
        }

        // fill the population with new offsprings
        fillNewOffsprings(population_size, old_members);

        Iterator<Species> iteratorSpecies = this.getSpecies().iterator();
        //iterate through all the species
        while(iteratorSpecies.hasNext())
        {
            Species sps = iteratorSpecies.next();
            // remove the species if it is empty
            if(sps.getMembers().isEmpty())
            {
                iteratorSpecies.remove();
            }
        }

        for(Species  sps : this.getSpecies())
        {
            sps.update();
        }

        // finally display the new population
        // displays the best genome and adds the details to a csv file for visualisations
        displayAndAddData();
/*
        // for visualization
        bestGenomeList.add(this.latest_fitness.getInnovationID());
        genomeFitnessList.add(this.latest_fitness.getFitness());
        genomeSpecieID.add(this.latest_fitness.getSpecies().getID());
        genomeSpecieMembers.add(this.latest_fitness.getSpecies().getMembers().size());





        // adding the generation and fitness to a hashmap
        UIVariables.GenerationMap.put(this.current_generation, this.latest_fitness.getFitness());

        // write all the data to the csv file
        writeToCSV("C:/Users/User/Desktop/My Courses/Honours/Year_Project/Github_repos/Graph_visualization/GenomeData.csv");*/


    }

    // function to get the initial genome for initialization
    public Genome initial()
    {
        Integer[] inputs = new Integer[this.getEvolution().getInputSize()];
        for(int x = 0 ; x < inputs.length; x++)
        {
            inputs[x] = x + 1;
        }
        Integer[] outputs = new Integer[this.getEvolution().getOutputSize()];
        for(int y = 0 ; y < outputs.length; y++)
        {
            outputs[y] = inputs.length +y +1;
        }

        double distance = this.getEvolution().getConfiguration(Configuration.weight_mutation_probability_random);

        Genome genome = new Genome(this.getEvolution(),null, inputs, outputs);
        for(int i = 1 ; i <= this.getEvolution().getInputSize(); i++)
        {
            for(int j = 1 ; j <= this.getEvolution().getOutputSize() ; j++)
            {
                genome.addGene(new Gene(this.getEvolution().getNextInnovationNumber(), Random.random(-distance, distance), true, i, this.getEvolution().getInputSize()+j), null, null);

            }
        }
        // adding the coordinates *********************************************
  /*      for(int x = 1 ; x <= this.getEvolution().getInputSize(); x++)
        {
            genome.getGene(x).setX_coordinate(0.1);
            genome.getGene(x).setY_coordinate((x+1)/(double)(this.getEvolution().getInputSize()+1));

        }
        for(int x = 1 ; x <= this.getEvolution().getOutputSize(); x++)
        {
            genome.getGene(x).setX_coordinate(0.9);
            genome.getGene(x).setY_coordinate((x+1)/(double)(this.getEvolution().getInputSize()+1));

        }*/

        return genome;

    }


    //only called for the first generation
    public void initialize(int populationSize)
    {
       // initializing a generation with given population
        this.population_size = populationSize;
        if(this.current_generation != 1)
        {
            System.out.println("The initialize method should only be called for first generation");
        }

        // get the initial genome
        Genome initialGenome = initial();
        //adds the clone of the genome to current population
        addGenomeClone(initialGenome);

    }

    public void addGenomeClone(Genome initialGenome)
    {
        for(int x = 0 ; x < this.getPopulation_size(); x++)
        {
            // new genome with random weights
            //get a clone of the initial genome
            Genome genome = initialGenome.clone();
            // cloning the genes
            for(Gene gene : genome.getGenes())
            {
                double distance = this.getEvolution().getConfiguration(Configuration.weight_mutation_probability_random);
                gene.setWeight(Random.random(-distance, distance));
            }
            this.getEvolution().getPopulation_manager().getCurrent_population().addGenome(genome);
        }

    }

    // function for stagnation of certain genomes from the species
    public void stagnate(Iterator<Species> itSps,  Map<Species, List<Genome>> best_performing, double sum)
    {
        while(itSps.hasNext())
        {
            Species species = itSps.next();
            //initially the stagnation count will be 0
            int totalStagnationCount = 0;


            // eliminate the worst performing genome for every specie
            // get the best performing genome of the current specie
            List<Genome> bestGenome = best_performing.get(species);
            if(bestGenome == null)
            {
                // best performing genome cannot be null
                throw new AssertionError();
            }
            double toRemove = Math.ceil(bestGenome.size() * this.getEvolution().getConfiguration(Configuration.generation_elimination_percentage));
            int start = (int) (Math.floor(bestGenome.size()-toRemove)+1);
            for(int x = start ; x < bestGenome.size(); x++)
            {
                Genome badPerforming = bestGenome.get(x);
                totalStagnationCount++;
                species.remove(badPerforming);
            }
            specieIDList.add(species.getID());
            stagnationPerSpecie.add(totalStagnationCount);
            averageSpecieFitnessList.add(species.getAverageFitness());

            // remove all the species whos fitness has not reached maximum for 15 generations
            species.setFailed_generations(species.getFailed_generations() +1);

            if(species.getFailed_generations() > 15)
            {
                System.out.println("Specie was eliminated because it did not reach max fitness for 15 generations");
                itSps.remove();
                continue;
            }

            // remove all the species that dont get breeding spots in the next generation
            double total_size = this.getPopulation_size();
            double allowableBreeds = Math.floor(species.getAverageFitness()/ sum * total_size) -1.0;

            if(allowableBreeds < 1)
            {
                itSps.remove();
                continue;
            }

        }
    }

    // function to fill the new generation with new offsprings
    public void fillNewOffsprings(int population_size, Map<Species, Set<Genome>> old_members)
    {
        while(population_size < this.population_size)
        {
            Species randomSpecies = Random.random(this.getSpecies());
            // get random genomes from the species set
            Set<Genome> oldMembers = old_members.get(randomSpecies);

            if(oldMembers != null)
            {
                if(Random.success(this.getEvolution().getConfiguration(Configuration.crossover_probability)))
                {
                    // perform cross over then
                    // get 2 random parents from the old member set
                    Genome parentA = Random.random(oldMembers);
                    Genome parentB = Random.random(oldMembers);

                    Genome.crossOver(parentA, parentB);
                }else
                {
                    // else just clone them without crossover
                    Genome g = Random.random(oldMembers).clone();
                    g.mutate();
                    randomSpecies.getMembers().add(g);
                }
                population_size++;
            }
        }

    }

    // function to display the best genome details and adding data for visualisation
    public void displayAndAddData()
    {
        this.latest_fitness = this.current_population.getBestPerformingGenome();
        System.out.println("Best performing genome ID : " +  this.latest_fitness.getInnovationID() + " Fitness of: " + this.latest_fitness.getFitness()+
                " Was part of specie" + this.latest_fitness.getSpecies().getID() + " Members in specie"+ this.latest_fitness.getSpecies().getMembers().size());
        //System.out.println(this.latest_fitness.toString());

        int specieCount = 0;
        int totalFitness = 0;
        double average = 0.0;
        for(Species s : this.current_population.getSpecieSet())
        {
            totalFitness += s.getAverageFitness();
            specieCount++;

        }
        average = totalFitness/specieCount;
        //System.out.println("average for species is" + average);
        averageFitnessList.add(average);

        // for visualization
        bestGenomeList.add(this.latest_fitness.getInnovationID());
        genomeFitnessList.add(this.latest_fitness.getFitness());
        genomeSpecieID.add(this.latest_fitness.getSpecies().getID());
        genomeSpecieMembers.add(this.latest_fitness.getSpecies().getMembers().size());


        // adding the generation and fitness to a hashmap
        UIVariables.GenerationMap.put(this.current_generation, this.latest_fitness.getFitness());

        // write all the data to the csv file
        writeToCSV("C:/Users/User/Desktop/My Courses/Honours/Year_Project/Github_repos/Graph_visualization/GenomeData.csv");
        writeToCSVSpecie("C:/Users/User/Desktop/My Courses/Honours/Year_Project/Github_repos/Graph_visualization/SpecieData.csv");

    }



    public void setCurrent_generation(int current_generation) {
        this.current_generation = current_generation;
    }

    public Evolution getEvolution() {
        return evolution;
    }

    public Population getCurrent_population() {
        return current_population;
    }

    public int getPopulation_size() {
        return population_size;
    }

    public void setPopulation_size(int population_size) {
        this.population_size = population_size;
    }

    public Genome getLatest_fitness() {
        return latest_fitness;
    }

    public void setLatest_fitness(Genome latest_fitness) {
        this.latest_fitness = latest_fitness;
    }

    // function to get a list of species in current population
    public List<Species> getSpecies()
    {
        return this.current_population.getSpecieSet();
    }

    public void writeToCSV( String filepath)
    {
        File file = new File(filepath);
        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);

            // adding header to csv
            String[] header = { "Generation_ID", "Active_Species", "Specie_Size", "Best_Genome_ID", "Genome_Fitness",
            "Genome_SpecieID", "Genome_Specie_Members", "Generation Average Fitness", "Generation Stagnation"};
            writer.writeNext(header);

            for(int x = 0 ; x < this.generationList.size(); x++)
            {
                String[] data1 = {String.valueOf(this.generationList.get(x)), String.valueOf(this.activeSpecies.get(x)),
                String.valueOf(this.specieSizeList.get(x)), String.valueOf(this.bestGenomeList.get(x)),
                String.valueOf(this.genomeFitnessList.get(x)), String.valueOf(this.genomeSpecieID.get(x)),
                String.valueOf(this.genomeSpecieMembers.get(x)), String.valueOf(averageFitnessList.get(x)), String.valueOf(stagnationPerSpecie.get(x))};
             /*   Integer[] data2 = {this.generationList.get(x), this.activeSpecies.get(x),
                        this.specieSizeList.get(x), this.bestGenomeList.get(x),
                        Integer.parseInt(String.valueOf(this.genomeFitnessList.get(x))), this.genomeSpecieID.get(x),
                        this.genomeSpecieMembers.get(x)};*/
                writer.writeNext(data1);
            }

            // add data to csv

            // closing writer connection
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    // function to add specie specific data to a csv file
    public void writeToCSVSpecie(String filePath)
    {
        File file = new File(filePath);
        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);

            // adding header to csv
            String[] header = {"Specie ID", "Average Fitness", "Stagnation"};
            writer.writeNext(header);

            // add data to csv
            for(int x = 0 ; x < this.specieIDList.size(); x++)
            {
                String[] data1 = {"Specie " + String.valueOf(this.specieIDList.get(x)), String.valueOf(this.averageSpecieFitnessList.get(x)),
                String.valueOf(this.stagnationPerSpecie.get(x))};
                writer.writeNext(data1);
            }

            // closing writer connection
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }


    public void newGenHelper()
    {
        int size = 0;
        for(Species sps : this.getSpecies())
        {
            size += sps.getMembers().size();
        }
        System.out.println("Generation " + this.current_generation + "... with  " + this.getSpecies().size() + " species active (with a total size of " + size + ").");
        // for visualisation
        generationList.add(this.current_generation);
        activeSpecies.add(this.getSpecies().size());
        specieSizeList.add(size);
    }

}
