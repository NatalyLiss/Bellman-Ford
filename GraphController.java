package sample;

/**
 * Created by helladmin on 26.06.2017.
 */


        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.scene.Scene;
        import javafx.scene.control.Alert;
        import javafx.scene.control.Button;
        import javafx.scene.control.Label;
        import javafx.scene.layout.Pane;
        import javafx.scene.paint.Color;
        import javafx.scene.shape.Circle;
        import javafx.scene.shape.Ellipse;
        import javafx.scene.shape.Line;

        import java.awt.geom.Line2D;
        import java.io.IOException;
        import java.util.ArrayList;
        import java.util.Vector;

/**
 * Класс для управления элементами дочерней панели.
 */
public class GraphController extends Controller {
    private Alert alert;
    public int counter = 0;
    @FXML
    private Pane pane1;
    static int Setting = 1;
    public boolean fordInWork = false;
    public boolean nextStep = false;
    public int relaxCounter = 0;
    public boolean last = false;
    public int cycleCounter = 0;
    public boolean cycle = false;
    final int inf = 1000000000;

    @FXML
    public static Button netButton;

    @FXML
    public static Button netButton1;

    @FXML
    public static Button buildBut;

    /**
     * Обработка нажатия клавиши "Построить".
     * Изображает заданный граф.
     */
    @FXML
    public void graphButton() {
        //изображение вершин
        for (int i = 0; i < P.n; i++) {
            Ellipse C = new Ellipse(P.vicual.elementAt(i).x, P.vicual.elementAt(i).y, 6, 9);
            C.setFill(Color.LIGHTSKYBLUE);
            pane1.getChildren().add(C);
            Label label = new Label(Integer.toString(P.vicual.elementAt(i).name));
            label.setTextFill(Color.BLUEVIOLET);
            label.setLayoutX(P.vicual.elementAt(i).x - 3);
            label.setLayoutY(P.vicual.elementAt(i).y - 9);
            pane1.getChildren().add(label);

        }
        //изображение рёбер
        for (int i = 0; i < P.m; i++) {
            Line q = new Line(P.vicual.elementAt(P.list.elementAt(i).from).x, P.vicual.elementAt(P.list.elementAt(i).from).y - 9, P.vicual.elementAt(P.list.elementAt(i).to).x, P.vicual.elementAt(P.list.elementAt(i).to).y - 9);
            q.setStrokeWidth(0.5);
            q.setFill(Color.LIGHTGREY);
            pane1.getChildren().add(q);

            //изображение стрелок
            this.arrow(P.vicual.elementAt(P.list.elementAt(i).from).x, P.vicual.elementAt(P.list.elementAt(i).to).x, P.vicual.elementAt(P.list.elementAt(i).from).y, P.vicual.elementAt(P.list.elementAt(i).to).y, Color.BLACK);
        }
        //красит в красный кратчайший путь
        if (P.V != -1) {
            if (P.ways.elementAt(P.V) == 1000000000) {

                Label label = new Label("Путь из вершины " + Integer.toString(P.v + 1) + " в вершину " + Integer.toString(P.V + 1) + ": NO\n");
                label.setTextFill(Color.RED);
                label.setLayoutX(0);
                label.setLayoutY(0);
                pane1.getChildren().add(label);
            } else {

                Vector<Integer> path = new Vector<Integer>();
                for (int cur = P.V; cur != -1; cur = P.road.elementAt(cur))
                    path.add(cur);

                for (int i = path.size() - 1; i >= 1; i--) {

                    int l = (path.elementAt(i));
                    int k = (path.elementAt(i - 1));

                    Label label1 = new Label(Integer.toString(P.ways.elementAt(P.V)));
                    label1.setTextFill(Color.RED);
                    label1.setLayoutX((P.vicual.elementAt(P.V)).x + 10);
                    label1.setLayoutY((P.vicual.elementAt(P.V).y - 12));
                    pane1.getChildren().add(label1);

                    Line q = new Line(P.vicual.elementAt(l).x, P.vicual.elementAt(l).y - 9, P.vicual.elementAt(k).x, P.vicual.elementAt(k).y - 9);
                    q.setStroke(Color.RED);
                    q.setStrokeWidth(1);
                    pane1.getChildren().add(q);


                    this.arrow(P.vicual.elementAt(l).x, P.vicual.elementAt(k).x, P.vicual.elementAt(l).y, P.vicual.elementAt(k).y, Color.RED);
                }
                path.clear();
            }
        }

    }

    /**
     * Функция отрисовки направления движения по ребру.
     *
     * @param x     координаты
     * @param x1    координаты
     * @param y     координаты
     * @param y1    координаты
     * @param color цвет линии.
     */
    void arrow(int x, int x1, int y, int y1, Color color) {
        double beta = Math.atan2((y) - (y1 - 9), x1 - x); //{ArcTan2 ищет арктангенс от x/y что бы неопределенностей не
        //  возникало(например, деление на 0)}
        double alfa = Math.PI / 10;// {угол между основной осью стрелки и рисочки в конце}
        int r1 = 10; //{длинна риски}

        int x2 = (int) Math.round(x1 - r1 * Math.cos(beta + alfa));
        int y2 = (int) Math.round((y1 - 9) + r1 * Math.sin(beta + alfa));
//g2d.drawLine(x1,y1,x2,y2);
        int x3 = (int) Math.round(x1 - r1 * Math.cos(beta - alfa));
        int y3 = (int) Math.round((y1 - 9) + r1 * Math.sin(beta - alfa));
//g2d.drawLine(x1,y1,x2,y2);
        Line q1 = new Line(x1, y1 - 9, x2, y2);
        Line q2 = new Line(x1, y1 - 9, x3, y3);
        q1.setStroke(color);
        pane1.getChildren().add(q1);
        q2.setStroke(color);
        pane1.getChildren().add(q2);
    }

    /**
     * Функция пошагового поиска кратчайших путей из заданной вершины в графе.
     * Обработка нажатия клавиши "следующий шаг".
     */

    @FXML
    public void graphPaint() {
        fordInWork = true;

        //изображение вершин
        for (int i = 0; i < P.n; i++) {
            Ellipse C = new Ellipse(P.vicual.elementAt(i).x, P.vicual.elementAt(i).y, 6, 9);
            C.setFill(Color.LIGHTSKYBLUE);
            pane1.getChildren().add(C);
            Label label = new Label(Integer.toString(P.vicual.elementAt(i).name));
            label.setTextFill(Color.BLUEVIOLET);
            label.setLayoutX(P.vicual.elementAt(i).x - 3);
            label.setLayoutY(P.vicual.elementAt(i).y - 9);
            pane1.getChildren().add(label);
        }
        //изображение рёбер
        for (int i = 0; i < P.m; i++) {
            Line q = new Line(P.vicual.elementAt(P.list.elementAt(i).from).x, P.vicual.elementAt(P.list.elementAt(i).from).y - 9, P.vicual.elementAt(P.list.elementAt(i).to).x, P.vicual.elementAt(P.list.elementAt(i).to).y - 9);
            q.setStrokeWidth(0.5);
            q.setFill(Color.BLACK);
            pane1.getChildren().add(q);

            //изображение стрелок
            this.arrow(P.vicual.elementAt(P.list.elementAt(i).from).x, P.vicual.elementAt(P.list.elementAt(i).to).x, P.vicual.elementAt(P.list.elementAt(i).from).y, P.vicual.elementAt(P.list.elementAt(i).to).y, Color.BLACK);
        }

        for (int i = 1; i < (P.n+1); i++) {//int n;//количество узлов
            P.ways.remove(1);// was i like index
            P.ways.add(inf);
            P.road.add(-1);
        }

        //P.v--;// узел из которого нужно считать пути;
        //P.V--;//узел в который ищем путь
        P.ways.set(P.v, 0);//something wrong?
        P.ways.set(P.V, inf);

    }

    @FXML
    public void nextStep(ActionEvent actionEvent) {
        if (fordInWork) {
            nextStep = true;

            cycleFord();
            if (last) {
                if (P.ways.elementAt(P.V) == inf) {
                    Label label6 = new Label("Путь из вершины " + Integer.toString(P.v + 1) + " в вершину " + Integer.toString(P.V + 1) + ": NO\n");
                    label6.setTextFill(Color.ORANGE);
                    label6.setLayoutX(0);
                    label6.setLayoutY(0);
                    pane1.getChildren().add(label6);
                    cycle = false;
                }
                Vector<Integer> path = new Vector<Integer>();
                if (P.V != -1) {
                    for (int cur = P.V; cur != -1; cur = P.road.elementAt(cur)) {
                        path.add(cur);
                    }

                    for (int j = path.size() - 1; j >= 1; j--) {

                        int l = (path.elementAt(j));
                        int k = (path.elementAt(j - 1));

                        Label label1 = new Label(Integer.toString(P.ways.elementAt(P.V)));
                        label1.setTextFill(Color.RED);
                        label1.setLayoutX((P.vicual.elementAt(P.V)).x + 10);
                        label1.setLayoutY((P.vicual.elementAt(P.V).y - 12));
                        pane1.getChildren().add(label1);


                        Line q = new Line(P.vicual.elementAt(l).x, P.vicual.elementAt(l).y - 9, P.vicual.elementAt(k).x, P.vicual.elementAt(k).y - 9);
                        q.setStroke(Color.RED);
                        q.setStrokeWidth(1);
                        pane1.getChildren().add(q);


                        this.arrow(P.vicual.elementAt(l).x, P.vicual.elementAt(k).x, P.vicual.elementAt(l).y, P.vicual.elementAt(k).y, Color.RED);
                    }
                    path.clear();
                    //}
                }
                path.clear();
                P.ways.clear();
                P.road.clear();
                nextStep = false;
                fordInWork = false;
                last = false;
                counter = 0;

                cycleCounter = 0;

            }
        }
    }

    public void cycleFord() {
        if (fordInWork && !cycle) {
            if (!last) {
                if (counter < P.m)//false!!!!!! error P.ways.size()
                    stepSearchAlgorithm();
                else {
                    relaxCounter = 0;
                    counter = 0;
                    cycleCounter++;
                    cycle = true;
                }
            }
        }
        if (fordInWork && cycle) {
            last = true;
            if (counter < P.m) {//P.ways.size()
                stepSearchAlgorithm();
                cycle = true;
                last = false;
            } else {
                if (relaxCounter != 0)
                    last = false;
                else
                    last = true;
                relaxCounter = 0;
                counter = 0;
                cycleCounter++;
                cycle = false;
            }
        }
    }

    public void stepSearchAlgorithm() {

        if (P.ways.elementAt(P.list.elementAt(counter).from) < inf) {//j=counter
            if ((P.ways.elementAt(P.list.elementAt(counter).from) + P.list.elementAt(counter).l) < P.ways.elementAt(P.list.elementAt(counter).to)) {//Зеленый - произошло ослабление.
                P.ways.set(P.list.elementAt(counter).to, (P.ways.elementAt(P.list.elementAt(counter).from) + P.list.elementAt(counter).l));
                P.road.set(P.list.elementAt(counter).to, P.list.elementAt(counter).from);

                Line q3 = new Line(P.vicual.elementAt(P.list.elementAt(counter).from).x, P.vicual.elementAt(P.list.elementAt(counter).from).y - 9, P.vicual.elementAt(P.list.elementAt(counter).to).x, P.vicual.elementAt(P.list.elementAt(counter).to).y - 9);
                q3.setStrokeWidth(1);
                q3.setStroke(Color.GREEN);
                pane1.getChildren().add(q3);
                last = false;
                relaxCounter++;
                cycle = false;
            } else {
                Line q1 = new Line(P.vicual.elementAt(P.list.elementAt(counter).from).x, P.vicual.elementAt(P.list.elementAt(counter).from).y - 9, P.vicual.elementAt(P.list.elementAt(counter).to).x, P.vicual.elementAt(P.list.elementAt(counter).to).y - 9);
                q1.setStrokeWidth(1);
                q1.setStroke(Color.BLUE);
                pane1.getChildren().add(q1);
                cycle = false;
            }
        }
        else {
            Line q3 = new Line(P.vicual.elementAt(P.list.elementAt(counter).from).x, P.vicual.elementAt(P.list.elementAt(counter).from).y - 9, P.vicual.elementAt(P.list.elementAt(counter).to).x, P.vicual.elementAt(P.list.elementAt(counter).to).y - 9);
            q3.setStrokeWidth(1);
            q3.setStroke(Color.ORANGE);
            pane1.getChildren().add(q3);
            cycle = false;
        }
            counter++;
        }
    }
