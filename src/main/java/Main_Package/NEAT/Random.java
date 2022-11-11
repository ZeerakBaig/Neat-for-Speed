package Main_Package.NEAT;

import java.util.List;
import java.util.Set;

public class Random {

    public static final java.util.Random random = new java.util.Random();
    public static java.util.Random getRandom()
    {
        return random;
    }

    // function that returns a random object froma given set
    public static <T> T random(Set<T> set)
    {
        if(set.size() ==0)
        {
            System.out.println("Given set is empty");
        }
        int setSize = set.size();
        int item = getRandom().nextInt(setSize);
        int x = 0;
        for(T t : set){
            if(x== item)
            {
                return t;
            }
            x++;
        }
        throw new AssertionError();
    }

    // function to get a random element from an array
    public static<T> T random(T[] arrayInput)
    {
        if(arrayInput.length ==0)
        {
            System.out.println("array is empty");
        }
        return arrayInput[getRandom().nextInt(arrayInput.length)];
    }

    // function to get a random object from a given list
    public static <T> T random(List<T> list)
    {
        if(list.size() ==0) {
            System.out.println("List cannot be empty");
        }
        return list.get(getRandom().nextInt(list.size()));
    }

    // function that returns a random double value between two values
    public static double random(double x, double y)
    {
        if(x >= y)
        {
            System.out.println("x is min and cannot be greater than y");
        }
        return x + (y- x) *getRandom().nextDouble();
    }

    // function that picks a random number x between the value of 0 and 1
    // the smaller the probability, the more unlikely that the method will return true
    public static boolean success(double probability)
    {
        return getRandom().nextDouble() <= probability;
    }
}
