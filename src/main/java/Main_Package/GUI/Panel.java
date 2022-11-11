package Main_Package.GUI;

import Main_Package.NEAT.Genome.ConnectionGene;
import Main_Package.NEAT.Genome.DisplayConnection;
import Main_Package.NEAT.Genome.Gene;
import Main_Package.NEAT.Genome.NodeGene;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {
    private DisplayGenome genome;
    public Panel()
    {

    }

    public DisplayGenome getGenome() {
        return genome;
    }

    public void setGenome(DisplayGenome genome) {
        this.genome = genome;
    }

    @Override
    protected void paintComponent(Graphics g)
    {

        //making main panel to display the netork
        makeRectangle(g);

        for(DisplayConnection c : genome.getLstConnections())
        {
            paintConnection(c,(Graphics2D) g);
        }
        for(NodeGene n : this.genome.getAllNodes())
        {

            paintNode(n,(Graphics2D) g);
        }
    }

    private void paintNode(NodeGene n, Graphics2D g) {
        g.setColor(Color.gray);
        g.setStroke(new BasicStroke(3));
        g.drawOval((int)(this.getWidth() * n.getX_coordinate()) - 10,
                (int)(this.getHeight() * n.getY_coordinate()) - 10,20,20);
    }
    //function to make the main panel for the network display
    private void makeRectangle(Graphics graphics)
    {
        graphics.clearRect(0,0,10000,10000);
        graphics.setColor(Color.white);
        graphics.fillRect(0,0,10000,10000);
    }
    //function to colour the connection between 2 nodes
    private void paintConnection(DisplayConnection connectionGene, Graphics2D g) {
        //g.setColor(connectionGene.isEnabled() ? Color.green:Color.red);
        if(connectionGene.isEnabled())
        {
            g.setColor(Color.green);
        }else
        {
            g.setColor(Color.red);
        }
        g.setStroke(new BasicStroke(3));
        g.drawString(new String(connectionGene.getWeight() + "       ").substring(0,7),
                (int)((connectionGene.getnGeneTo() + connectionGene.getnGeneFrom())* 0.5 * this.getWidth()),
                (int)((connectionGene.getnGeneTo() + connectionGene.getnGeneFrom())* 0.5 * this.getHeight()) +15);
        g.drawLine((int)(this.getWidth() * connectionGene.getnGeneFrom()), (int)(this.getHeight() * connectionGene.getnGeneFrom()), (int)(this.getWidth() * connectionGene.getnGeneTo()), (int)(this.getHeight() * connectionGene.getnGeneTo()));
       /* g.drawString(new String(connectionGene.getWeight() + "       ").substring(0,7),
                (int)((connectionGene.getNodeTo().getxCoordinate() + connectionGene.getNodeFrom().getxCoordinate())* 0.5 * this.getWidth()),
                (int)((connectionGene.getNodeTo().getyCoordinate() + connectionGene.getNodeFrom().getyCoordinate())* 0.5 * this.getHeight()) +15);
        g.drawLine((int)(this.getWidth() * connectionGene.getNodeFrom().getxCoordinate()), (int)(this.getHeight() * connectionGene.getNodeFrom().getyCoordinate()), (int)(this.getWidth() * connectionGene.getNodeTo().getxCoordinate()), (int)(this.getHeight() * connectionGene.getNodeTo().getyCoordinate()));*/
    }


}
