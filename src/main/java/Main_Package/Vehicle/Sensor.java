package Main_Package.Vehicle;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Sensor {
    private VehicleCoordinates vehicle_location;
    private float sensor_angle;
    public static final Color ROAD_COLOUR = new Color(255, 174, 0);//(68, 114, 196);//(255, 174, 0);//(26, 70, 132);
    public static final Color ROAD_LINE_COLOUR = new Color(45,193,213);
    public static final double STEP_SIZE = 3.0;
    public static final Color OBSTACLE_COLOUR = new Color(0,0,0);

    // constructor
    public Sensor(float angle, VehicleCoordinates vehicle_location)
    {
        this.sensor_angle = angle;
        this.vehicle_location = vehicle_location;
    }
    // getters and setters


    public VehicleCoordinates getVehicle_location() {
        return vehicle_location;
    }

    public void setVehicle_location(VehicleCoordinates vehicle_location) {
        this.vehicle_location = vehicle_location;
    }

    public float getSensor_angle() {
        return sensor_angle;
    }

    public void setSensor_angle(float sensor_angle) {
        this.sensor_angle = sensor_angle;
    }

    // function to get the available distance on the simulation environment
    public double getAvailableDistance(BufferedImage simulation_environment)
    {
        // get the x and the y coordinates
        double start_x_coordinate = this.getVehicle_location().getX_coordinate();
        double start_y_coordinate = this.getVehicle_location().getY_coordinate();


        // get the total angle based on the location of the vehicle
        double total_angle = this.vehicle_location.getVehicle_angle() + this.sensor_angle;
        // calculating the new coordinates using the step size
        double newY = STEP_SIZE* Math.sin(Math.toRadians(total_angle));
        double newX = STEP_SIZE * Math.cos(Math.toRadians(total_angle));

     /*   while(true)
        {
            // updating the coordinats
            start_x_coordinate += newX;
            start_y_coordinate += newY;

            if(simulation_environment.getRGB((int) start_x_coordinate, (int) start_y_coordinate)!= Sensor.ROAD_COLOUR.getRGB()
                   // && simulation_environment.getRGB((int) start_x_coordinate, (int) start_y_coordinate)!= Sensor.ROAD_LINE_COLOUR.getRGB()
            &&  simulation_environment.getRGB((int) start_x_coordinate, (int) start_y_coordinate) != Color.RED.getRGB())
            {
                // we are not on the road
                // we subtract the start coordinates because we off the road(need to make sure that start coordinates are always on the road)
                double distance_x = this.getVehicle_location().getX_coordinate() - start_x_coordinate;
                double distance_y = this.getVehicle_location().getY_coordinate() - start_y_coordinate;
                return Math.sqrt(distance_x * distance_x + distance_y*distance_y);
            }
        }*/
        return getAvailableDistanceHelper(simulation_environment, start_x_coordinate, start_y_coordinate, newX,newY);
    }

    // update coordinates and get the available distance helper
    public double getAvailableDistanceHelper(BufferedImage simulation_environment, double start_x_coordinate, double start_y_coordinate, double newX, double newY)
    {
        while(true)
        {
            // updating the coordinates
            start_x_coordinate += newX;
            start_y_coordinate += newY;

            if(simulation_environment.getRGB((int) start_x_coordinate, (int) start_y_coordinate)!= Sensor.ROAD_COLOUR.getRGB()
                    // && simulation_environment.getRGB((int) start_x_coordinate, (int) start_y_coordinate)!= Sensor.ROAD_LINE_COLOUR.getRGB()
                    &&  simulation_environment.getRGB((int) start_x_coordinate, (int) start_y_coordinate) != Color.RED.getRGB())
            {
                // we are not on the road
                // we subtract the start coordinates because we off the road(need to make sure that start coordinates are always on the road)
                double distance_x = this.getVehicle_location().getX_coordinate() - start_x_coordinate;
                double distance_y = this.getVehicle_location().getY_coordinate() - start_y_coordinate;
                return Math.sqrt(distance_x * distance_x + distance_y*distance_y);
            }
        }
    }



    // function to get the end Y coordinate
    public double getEndYCoordinate(BufferedImage simulation_grid)
    {
        double start_Y_coordinate = this.getVehicle_location().getY_coordinate();
        double total_angle = this.vehicle_location.getVehicle_angle() + this.sensor_angle;
        double distance_Y = getAvailableDistance(simulation_grid) * Math.sin(Math.toRadians(total_angle));
        return start_Y_coordinate + distance_Y;

    }

    //function to get the end X coordinate
    public double getEndXCoordinate(BufferedImage simulation_grid)
    {
        //similar to get END Y but we use cos function for the x coordinate
        double start_X_coordinate = this.getVehicle_location().getX_coordinate();
        double total_angle = this.vehicle_location.getVehicle_angle() + this.sensor_angle;
        double distance_X = getAvailableDistance(simulation_grid) * Math.cos(Math.toRadians(total_angle));
        return start_X_coordinate + distance_X;

    }

    // function tp draw the sensor lines
    public void drawSensors(BufferedImage simulation_environment, Graphics2D graphics2D)
    {
        graphics2D.setColor(Color.YELLOW);

        graphics2D.drawLine((int) this.getVehicle_location().getX_coordinate(), (int) this.getVehicle_location().getY_coordinate(),
                (int)this.getEndXCoordinate(simulation_environment), (int) this.getEndYCoordinate(simulation_environment));
    }
}
