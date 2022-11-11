package Main_Package.GUI;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class GenerationFitnessGraph extends ApplicationFrame {
    private Map<Integer, Double> generationMap = new TreeMap<>();

    public GenerationFitnessGraph(String applicationTitle , String chartTitle, TreeMap<Integer, Double> generationMap) {
        super(applicationTitle);
        this.generationMap = generationMap;
       /* this.generationMap.put(1 , 1.0);
        this.generationMap.put(2 , 3.0);
        this.generationMap.put(3 , 8.0);*/

        JFreeChart lineChart = ChartFactory.createLineChart(
                chartTitle,
                "Years","Number of Schools",
                createDataset(generationMap),
                PlotOrientation.VERTICAL,
                true,true,false);

        ChartPanel chartPanel = new ChartPanel( lineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        setContentPane( chartPanel );
    }

    private DefaultCategoryDataset createDataset( TreeMap<Integer, Double> generationMap) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
      for(Map.Entry<Integer, Double> set : generationMap.entrySet())
        {
           System.out.println(set.getKey() + set.getValue() + "\n");
           dataset.addValue(set.getValue(), "Generation", "Generation "+ set.getKey());
        }
       /* dataset.addValue( 15 , "schools" , "1970" );
        dataset.addValue( 30 , "schools" , "1980" );
        dataset.addValue( 60 , "schools" ,  "1990" );
        dataset.addValue( 120 , "schools" , "2000" );
        dataset.addValue( 240 , "schools" , "2010" );
        dataset.addValue( 300 , "schools" , "2014" );*/
        return dataset;
    }

    public static void main( String[ ] args ) {
        GenerationFitnessGraph chart = new GenerationFitnessGraph(
                "School Vs Years" ,
                "Numer of Schools vs years", UIVariables.GenerationMap);

        chart.pack( );
        RefineryUtilities.centerFrameOnScreen( chart );
        chart.setVisible( true );
    }
}
