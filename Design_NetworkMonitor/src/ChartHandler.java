import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.ArrayList;

/**
 * Created by Sharuru on 2015/6/26 0026.
 */
public class ChartHandler {
    protected static ArrayList<Double> tcpA = new ArrayList<>();
    protected static ArrayList<Double> udpA = new ArrayList<>();
    protected static int space;

    protected static void initSpeedArrayList(int space) {
        ChartHandler.space = space;
        for (int i = 0; i < space; i++) {
            tcpA.add(i, 0.0);
            udpA.add(i, 0.0);
        }
    }

    protected static XYDataset createDataset(double newTcpSpeed, double newUdpSpeed) {
        XYSeries tcpDS = new XYSeries("TCP");
        XYSeries udpDS = new XYSeries("UDP");
        tcpA.set(space - 1, newTcpSpeed);
        udpA.set(space - 1, newUdpSpeed);
        for (int i = 0; i < space; i++) {
            tcpDS.add(i, tcpA.get(i));
            udpDS.add(i, udpA.get(i));
        }
        for (int i = 0; i < space - 1; i++) {
            tcpA.set(i, tcpA.get(i + 1));
            udpA.set(i, udpA.get(i + 1));
        }
        XYSeriesCollection XYdataset = new XYSeriesCollection();
        XYdataset.addSeries(tcpDS);
        XYdataset.addSeries(udpDS);
        return XYdataset;
    }
}
