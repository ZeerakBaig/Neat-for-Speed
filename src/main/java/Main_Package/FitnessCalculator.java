package Main_Package;

// abstract class for fitness calculations
public abstract class FitnessCalculator {
 public abstract double getFitness(NetworkCalculations networkCalculations);
 public void generationEnded(NetworkCalculations highestPerformance){}

}
