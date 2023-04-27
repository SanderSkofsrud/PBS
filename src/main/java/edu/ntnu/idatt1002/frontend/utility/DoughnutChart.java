package edu.ntnu.idatt1002.frontend.utility;

import com.sun.javafx.charts.Legend;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.Objects;

/**
 * A class that creates a doughnut chart.
 * The doughnut chart is a pie chart with a hole in the middle.
 *
 * @author Emil J., Vegard J., Sander S. and Elias T.
 * @version 1.1 - 26.04.2023
 */
public class DoughnutChart extends PieChart {
  /**
   * The inner circle of the doughnut chart.
   */
  private final Circle innerCircle;
  private final String stylesheet;

  /**
   * Constructs a new doughnut chart.
   *
   * @param pieData the data to be displayed in the chart
   * @param style   the style
   */
  public DoughnutChart(ObservableList<Data> pieData, String style) {
    super(pieData);

    stylesheet = style;
    if (Objects.equals(stylesheet, "Darkmode")) {
      innerCircle = new Circle();
      innerCircle.setFill(Paint.valueOf("#3b3b3b"));
      innerCircle.setStroke(Color.WHITE);
      innerCircle.setStrokeWidth(1);
    } else {
      innerCircle = new Circle();
      innerCircle.setFill(Color.WHITESMOKE);
      innerCircle.setStroke(Color.WHITE);
      innerCircle.setStrokeWidth(1);
    }
  }

  /*
   * This method is called whenever the layout of the chart is updated.
   */
  @Override
  protected void layoutChartChildren(double top, double left, double contentWidth,
                                     double contentHeight) {
    super.layoutChartChildren(top, left, contentWidth, contentHeight);

    addInnerCircleIfNotPresent();
    updateInnerCircleLayout();
    changeLabelColor();
  }
  /*
   * This method is called whenever the data of the chart is updated.
   * It is used to add the inner circle to the chart if it is not already present.
   */

  private void addInnerCircleIfNotPresent() {
    if (!getData().isEmpty()) {
      Node pie = getData().get(0).getNode();
      if (pie.getParent() instanceof Pane parent && (!parent.getChildren().contains(innerCircle))) {
        parent.getChildren().add(innerCircle);
      }
    }
  }

  /*
   * This method is called whenever the data of the chart is updated.
   * It is used to update the layout of the inner circle when the data is updated.
   */
  private void updateInnerCircleLayout() {
    double minX = Double.MAX_VALUE;
    double minY = Double.MAX_VALUE;
    double maxX = Double.MIN_VALUE;
    double maxY = Double.MIN_VALUE;
    for (PieChart.Data data : getData()) {
      Node node = data.getNode();

      Bounds bounds = node.getBoundsInParent();
      if (bounds.getMinX() < minX) {
        minX = bounds.getMinX();
      }
      if (bounds.getMinY() < minY) {
        minY = bounds.getMinY();
      }
      if (bounds.getMaxX() > maxX) {
        maxX = bounds.getMaxX();
      }
      if (bounds.getMaxY() > maxY) {
        maxY = bounds.getMaxY();
      }
    }

    innerCircle.setCenterX(minX + (maxX - minX) / 2);
    innerCircle.setCenterY(minY + (maxY - minY) / 2);

    innerCircle.setRadius((maxX - minX) / 4);
  }

  /*
   * This method is called whenever the data of the chart is updated.
   * It is used to change the color of the labels of the chart.
   */
  private void changeLabelColor() {
    for (Data data : getData()) {
      data.nameProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
        for (Node node : lookupAll(".chart-pie-label")) {
          if (node instanceof Text textNode && !(node.getParent() instanceof Legend)) {
            textNode.getStyleClass().add("chart-pie-label");
          }
        }
      }));
    }
  }

}