package Main_Package.NEAT;

import Main_Package.NEAT.Genome.ConnectionGene;

import java.util.List;

public class ArrayUtility {

    //function to check if a list of connection genes is equal to another list of connection genes
    public static boolean equals(List<ConnectionGene> connectionA, List<ConnectionGene> connectionB)
    {
        if(connectionA.size() != connectionB.size())
        {
            // means that the connection lists are not the same
            return false;
        }
        for(int x = 0 ; x <  connectionA.size(); x++)
        {
            if(!connectionA.get(x).equals(connectionB.get(x)))
            {
                return false;
            }
        }
        return true;
    }




}
