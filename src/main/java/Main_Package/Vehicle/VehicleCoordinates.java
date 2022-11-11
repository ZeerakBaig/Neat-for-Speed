package Main_Package.Vehicle;

import Main_Package.GUI.UIVariables;
import org.graphstream.util.parser.Token;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class VehicleCoordinates {
    private double X_coordinate;
    private double Y_coordinate;
    private float vehicle_angle;
    private List<Sensor> sensors = new ArrayList<>();

    private final static double X_COORDINATE =480;//480;//283;//283;
    private final static double Y_COORDINATE =340;//340;//50;//90;
    private final static double CAR_SPEED = 10;
    private double current_speed = 4;
    private int num_sensors = 9;

    // constructor
    public VehicleCoordinates(double X_coordinate, double Y_coordinate, float vehicle_angle)
    {
        this.X_coordinate = X_coordinate;
        this.Y_coordinate= Y_coordinate;
        String coordinates = readFile("C:/Users/User/Desktop/My Courses/Honours/Year_Project/Github_repos/Graph_visualization/coordinates.txt");
        StringTokenizer tokenizer = new StringTokenizer(coordinates," ");
        int xcord = Integer.parseInt(tokenizer.nextToken());
        int ycoord = Integer.parseInt(tokenizer.nextToken());
        this.X_coordinate = xcord;
        this.Y_coordinate = ycoord;

        if(UIVariables.customTrack == false)
        {
            this.X_coordinate = 480;
            this.Y_coordinate = 340;
        }
        // adding all th sensors
        // starting angle is going to be 0 with the default coordinates
        this.sensors.add(new Sensor(0, this));
        //we can have as many sensors as we want
        //initially we are going to set it to 9
        for(int x = 1;x < num_sensors ; x++)
        {
            this.sensors.add(new Sensor(x*num_sensors, this));
            this.sensors.add(new Sensor(-x*num_sensors, this));
        }
    }

    // default constructor
    public VehicleCoordinates()
    {
        this(X_COORDINATE, Y_COORDINATE,0);
    }

    // getters and setters

    public double getX_coordinate() {
        return X_coordinate;
    }

    public void setX_coordinate(double x_coordinate) {
        X_coordinate = x_coordinate;
    }

    public double getY_coordinate() {
        return Y_coordinate;
    }

    public void setY_coordinate(double y_coordinate) {
        Y_coordinate = y_coordinate;
    }

    public float getVehicle_angle() {
        return vehicle_angle;
    }

    public void setVehicle_angle(float vehicle_angle) {
        this.vehicle_angle = vehicle_angle;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }

    public double getCurrent_speed() {
        // get a scaled car speed
        return current_speed/CAR_SPEED;
    }

    public void setCurrent_speed(double current_speed) {
        this.current_speed = current_speed;
    }
    // function to check if the car is on the finish line
    public boolean isFinish(BufferedImage simulation_grid)
    {
        if(simulation_grid.getRGB((int) this.X_coordinate, (int) this.Y_coordinate) ==Color.RED.getRGB())
        {
            return true;
        }
        if(simulation_grid.getRGB((int) this.X_coordinate, (int) this.Y_coordinate) ==Color.BLACK.getRGB())
        {
            // increase the hit count
            VehicleTraining.hitCount++;
        }
        return false;
        //return simulation_grid.getRGB((int) this.X_coordinate, (int) this.Y_coordinate) ==Color.RED.getRGB();
    }


    // function to check if the car is still alive
    public boolean carIsAlive(BufferedImage simulation_grid)
    {
        // if the colour is the same as the road or the finish line then the car is alive

        return simulation_grid.getRGB((int) this.X_coordinate, (int) this.Y_coordinate)== Sensor.ROAD_COLOUR.getRGB()||
                simulation_grid.getRGB((int) this.X_coordinate, (int) this.Y_coordinate) == Color.RED.getRGB();/*||
                simulation_grid.getRGB((int) this.X_coordinate, (int) this.Y_coordinate)== Sensor.ROAD_LINE_COLOUR.getRGB();*/
    }

    // function to manipulate the coordinates as well as the speed ofthe car
    public void tick(double gas_percentage,boolean right_touched, boolean left_touched)
    {
        if(right_touched)
        {
            // increase the angle if steering right
            this.vehicle_angle += 3.5;
        }
        if(left_touched)
        {
            // degrease the vehicle angle if stearing left
            this.vehicle_angle -= 3.5;
        }
        if(gas_percentage > 1 ||gas_percentage < 0)
        {
            System.out.println("Gas percentage should be between 0 and 1");
        }

        // variable to keep track of the gas change
        double gas_change = (gas_percentage-0.5) * 0.3D;
        // add the change in speed to the current speed
        this.current_speed += gas_change;

        // we kept the initial speed to 40 percent
        if(this.current_speed < 4)
        {
            this.current_speed = 4;
        }

        // CAR_SPEED is the maximum car speed
        if( this. current_speed > CAR_SPEED)
        {
            this.current_speed = CAR_SPEED;
        }


        // updating the x and the y coordinates
        updateVehicleCoordinates();
       /* double newX = this.current_speed * Math.cos(Math.toRadians(this.vehicle_angle));
        double newY = this.current_speed * Math.sin(Math.toRadians(this.vehicle_angle));

        this.X_coordinate += newX;
        this.Y_coordinate += newY;*/
    }

    public void updateVehicleCoordinates()
    {
        double newX = this.current_speed * Math.cos(Math.toRadians(this.vehicle_angle));
        double newY = this.current_speed * Math.sin(Math.toRadians(this.vehicle_angle));

        this.X_coordinate += newX;
        this.Y_coordinate += newY;
    }

    // function to give a penalty if an obstacle is hit (currently busy)
    public boolean hit(BufferedImage simulation_grid)
    {
        return simulation_grid.getRGB((int) this.X_coordinate, (int) this.Y_coordinate) ==Color.BLACK.getRGB();

    }

    // function to read the coordinates of the car
    public String readFile(String path){
        String line = "";
        try{
            FileReader fr = new FileReader(path);

            // Declaring loop variable
            int i;
            // Holds true till there is nothing to read
            while ((i = fr.read()) != -1)
            {
                // Print all the content of a file
                line += (char)i;
                //System.out.print((char)i);
            }
            return line;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }


}
