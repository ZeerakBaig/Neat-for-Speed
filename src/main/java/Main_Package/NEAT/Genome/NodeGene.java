package Main_Package.NEAT.Genome;

//for displaying but not currently finished
public class NodeGene {
    private double x_coordinate;
    private double y_coordinate;
    private int innovation_number;

    //default constructor to display a node *** only for display purposes
    public NodeGene()
    {

    }
    public NodeGene(int number)
    {
        this.innovation_number = number;

    }
    public NodeGene(int xCoordinate, int yCoordinate)
    {
        this.x_coordinate = xCoordinate;
        this.y_coordinate = yCoordinate;
    }

    public double getX_coordinate() {
        return x_coordinate;
    }

    public void setX_coordinate(double x_coordinate) {
        this.x_coordinate = x_coordinate;
    }

    public double getY_coordinate() {
        return y_coordinate;
    }

    public void setY_coordinate(double y_coordinate) {
        this.y_coordinate = y_coordinate;
    }

    public int getInnovation_number() {
        return innovation_number;
    }

    public void setInnovation_number(int innovation_number) {
        this.innovation_number = innovation_number;
    }
}
