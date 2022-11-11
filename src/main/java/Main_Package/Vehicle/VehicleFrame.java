package Main_Package.Vehicle;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class VehicleFrame extends JPanel {
    private Vehicle vehicleObject;
    private VehicleCoordinates vehicle_location = new VehicleCoordinates();
    // static car object
    private int lastX = 0;

    public VehicleFrame(Vehicle vehicle)
    {
        this.vehicleObject = vehicle;
        setFocusable(true);
        setBackground(Color.BLACK);
    }
    // getters and setters


    public Vehicle getVehicleObject() {
        return vehicleObject;
    }

    public void setVehicleObject(Vehicle vehicleObject) {
        this.vehicleObject = vehicleObject;
    }

    public VehicleCoordinates getVehicle_location() {
        return vehicle_location;
    }

    public void setVehicle_location(VehicleCoordinates vehicle_location) {
        this.vehicle_location = vehicle_location;
    }

    private BufferedImage car_image;
    {
        try{
            // read the car image file
            this.car_image = ImageIO.read(new File("C:/Users/User/Desktop/My Courses/Honours/Year_Project/Github_repos/Graph_visualization/src/main/java/Main_Package/car2.png"));
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);
        draw(graphics);
    }

    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(800, 800);
    }

    public void draw(Graphics graphics)
    {
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.drawImage(this.getBackgroundImage(), 0, 0, null);

        // used to rotate the vehicle
        rotateVehicle(graphics2D);

        // displaying the sensors
        for(Sensor s :  this.getVehicle_location().getSensors())
        {
            s.drawSensors(this.getBackgroundImage(), graphics2D);
        }
        // to display the speed of the car in top left corner
        graphics2D.setColor(Color.WHITE);
        graphics2D.setFont(graphics2D.getFont().deriveFont(20F));
        graphics2D.drawString("Speed" + round(this.getVehicle_location().getCurrent_speed()*100, 3)+ "%", 10, 25);


        // for the object
  /*      int w = getWidth();
        int h = getHeight();

        int trainW = 100;
        int trainH = 10;
        int trainSpeed = 3;

        int x = lastX + trainSpeed;

        if (x > w + trainW) {
            x = -trainW;
        }

        graphics2D.setColor(Color.BLACK);
        graphics2D.fillRect(x, h/2 + trainH, trainW, trainH);

        lastX = x;*/

    }

    public void rotateVehicle(Graphics2D graphics2D)
    {
        AffineTransform affTransform = new AffineTransform();
        affTransform.rotate(Math.toRadians(this.vehicle_location.getVehicle_angle()), this.vehicle_location.getX_coordinate(), this.vehicle_location.getY_coordinate());
        AffineTransform oldTransform = graphics2D.getTransform();
        graphics2D.transform(affTransform);
        graphics2D.drawImage(this.car_image, (int) this.vehicle_location.getX_coordinate()- this.car_image.getWidth(null)/2,
                (int) this.vehicle_location.getY_coordinate()- this.car_image.getHeight(null)/2, null);
        graphics2D.setTransform(oldTransform);

    }

    public double round(double value, int places)
    {
        return new BigDecimal(value).setScale(places, RoundingMode.HALF_UP).doubleValue();
    }

    public BufferedImage getBackgroundImage()
    {
        return this.vehicleObject.getBackgroundImage();
    }

    public BufferedImage getCar_image() {
        return car_image;
    }

    public void setCar_image(BufferedImage car_image) {
        this.car_image = car_image;
    }
}
