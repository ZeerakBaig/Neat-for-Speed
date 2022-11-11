package Main_Package.NEAT.Genome;

// connection gene class which forms a connection between two nodes
public class ConnectionGene {
    // the two node genes that the connection gene forms a link between
    private int nodeFrom;
    private int nodeTo;

    // for display purpose currently not done
    private NodeGene displayNodeFrom;
    private NodeGene displayNodeTo;




    // constructor for connection gene
    public ConnectionGene(int nodeFrom, int nodeTo) {
        this.nodeFrom = nodeFrom;
        this.nodeTo = nodeTo;

    }

    // getters and setters
    public int getNodeFrom() {
        return nodeFrom;
    }

    public void setNodeFrom(int nodeFrom) {
        this.nodeFrom = nodeFrom;
    }

    public int getNodeTo() {
        return nodeTo;
    }

    public void setNodeTo(int nodeTo) {
        this.nodeTo = nodeTo;
    }

    // function to check of the current connection gene is equal to another connection gene object
    // function is used in array utility class to check if the list of two connection genes are equal
    // the function is then used in the manage duplicate function (setting same innovation for duplicate genes)
    @Override
    public boolean equals(Object object)
    {
        if(object == null)
        {
            return false;
        }
        if(this == object)
        {
            // if the objects are equal
            return true;
        }
        // comparing the runtime class of the object
        if(getClass() != object.getClass())
        {
            return false;
        }
        // get the connection that is to be compared
        ConnectionGene otherConnectionGene = (ConnectionGene) object;
        if(nodeFrom != otherConnectionGene.nodeFrom)
        {
            return false;
        }
        if(nodeTo != otherConnectionGene.nodeTo)
        {
            return false;
        }
        return true;
    }

/*    public NodeGene getDisplayNodeFrom() {
        return displayNodeFrom;
    }

    public void setDisplayNodeFrom(NodeGene displayNodeFrom) {
        this.displayNodeFrom = displayNodeFrom;
    }

    public NodeGene getDisplayNodeTo() {
        return displayNodeTo;
    }

    public void setDisplayNodeTo(NodeGene displayNodeTo) {
        this.displayNodeTo = displayNodeTo;
    }*/

    // function to generate a hashcode for the connection gene
    @Override
    public int hashCode()
    {
        //random chosed value of a prime number to generate a hashcode
        final int primeNumber = 31;
        int res = 1;
        res = primeNumber * res + nodeFrom;
        res = primeNumber * res + nodeTo;
        return res;
    }


}
