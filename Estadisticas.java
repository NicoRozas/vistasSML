/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb.jefeArea;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;

@Named(value = "EstadisticasMB")
@RequestScoped
public class Estadisticas {

    private BarChartModel barModel;
    private PieChartModel pieModel1;
    private PieChartModel pieModel2;

    @PostConstruct
    public void init() {
        createBarModels();
        createPieModels();
    }

    public PieChartModel getPieModel1() {
        return pieModel1;
    }

    public PieChartModel getPieModel2() {
        return pieModel2;
    }

    public BarChartModel getBarModel() {
        return barModel;
    }

    private void createPieModels() {
        createPieModel1();
        createPieModel2();
    }

    private void createPieModel1() {
        pieModel1 = new PieChartModel();

        pieModel1.set("Evidencia 1", 100);
        pieModel1.set("Evidencia 2", 52);
        pieModel1.set("Evidencia 3", 89);

        pieModel1.setTitle("Diciembre");
        pieModel1.setLegendPosition("w");
    }

    private void createPieModel2() {
        pieModel2 = new PieChartModel();

        pieModel2.set("Evidencia 1", 120);
        pieModel2.set("Evidencia 2", 60);
        pieModel2.set("Evidencia 3", 95);

        pieModel2.setTitle("Enero");
        pieModel2.setLegendPosition("w");
    }
    
    private BarChartModel iniciarModelo1() {
        BarChartModel model = new BarChartModel();

        ChartSeries evidencia = new ChartSeries();
        evidencia.setLabel("Evidencia 1");
        evidencia.set("Diciembre", 100);
        evidencia.set("Enero", 120);

        ChartSeries evidencia2 = new ChartSeries();
        evidencia2.setLabel("Evidencia 2");
        evidencia2.set("Diciembre", 52);
        evidencia2.set("Enero", 60);

        ChartSeries evidencia3 = new ChartSeries();
        evidencia3.setLabel("Evidencia 3");
        evidencia3.set("Diciembre", 89);
        evidencia3.set("Enero", 95);

        model.addSeries(evidencia);
        model.addSeries(evidencia2);
        model.addSeries(evidencia3);

        return model;
    }

    private void createBarModels() {
        createBarModel();
    }

    private void createBarModel() {
        barModel = iniciarModelo1();

        barModel.setTitle("Gráfico de evidencias");
        barModel.setLegendPosition("ne");

        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Evidencias");

        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Cantidad");
        yAxis.setMin(0);
        yAxis.setMax(200);
    }

}
