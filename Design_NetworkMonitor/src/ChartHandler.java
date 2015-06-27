import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Sharuru on 2015/6/26 0026.
 */
public class ChartHandler {
    protected static Queue<Double> tcpQ = new LinkedList<>();
    protected static Queue<Double> udpQ = new LinkedList<>();
    protected static int space;

    protected static void initSpeedArrayList(int space) {
        //Something like malloc in C
        ChartHandler.space = space;
        for (int i = 0; i < space; i++) {
            tcpQ.offer(0.0);
            udpQ.offer(0.0);
        }
    }

    protected static XYDataset createDataset(double newTcpSpeed, double newUdpSpeed) {
        XYSeries tcpDS = new XYSeries("TCP");
        XYSeries udpDS = new XYSeries("UDP");
        //Kick the first and add the new speed to the last
        tcpQ.poll();
        tcpQ.offer(newTcpSpeed);
        udpQ.poll();
        udpQ.offer(newUdpSpeed);
        //Copy queue for dataset uses
        Queue<Double> tcpQQ = new LinkedList<>(tcpQ);
        Queue<Double> udpQQ = new LinkedList<>(udpQ);
        //Set dataset
        for (int i = 0; i < space; i++) {
            tcpDS.add(i, tcpQQ.poll());
            udpDS.add(i, udpQQ.poll());
        }
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(tcpDS);
        dataset.addSeries(udpDS);
        return dataset;
    }
}
