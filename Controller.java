package sample;

import javafx.fxml.FXML;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import java.io.IOException;


/**
 * Класс для обработки событий элементов главного окна.
 */

public class Controller {
    private Alert alert;
    @FXML
    private TextField textField1;

    @FXML
    private TextArea amountEdges;
    @FXML
    public TextArea result;
    @FXML
    private TextArea textGraph;

    @FXML
    public TextArea graphAlg;
    @FXML
    private TextArea graphV;


    private static int k;


    public static Graph P = new Graph();
    private GraphController graphWindow;
    public static boolean Setting = true;

    /**
     * Функция обработки события "Генерация случайного графа".
     * Проверяет правильное заполнение полей "Количество вершин" и "Количество ребер".
     * Обрабатывает нажатие клавиши "Сгенерировать граф".
     */
    @FXML
    public void generateGraph() {
        if (amountVertex.getText() == null || amountVertex.getText().length() == 0) {
            error("Введите количество вершин");
            int x = Integer.parseInt(amountVertex.getText());
        } else {
            if (amountEdges.getText() == null || amountEdges.getText().length() == 0) {
                error("Введите количество ребер");
                int y = Integer.parseInt(amountEdges.getText());
            }
            try {
                int x = Integer.parseInt(amountVertex.getText());
                int y = Integer.parseInt(amountEdges.getText());
                if (x > 30 || x < 0 || y > 10 || y < 0) {
                    error("Граф может быть не отображен.");
                } else {
                    P.list.clear();
                    P.ways.clear();
                    P.road.clear();
                    k = 0;
                    P.n = x;
                    P.m = y;
                    P.V=-1;
                    textGraph.clear();
                    P.input_generation();
                    for (int i = 0; i < P.list.size(); i++) {
                        textGraph.appendText("Путь из вершины " + (P.list.elementAt(i).from + 1) + " в вершину " + (P.list.elementAt(i).to + 1) + ": " + P.list.elementAt(i).l + "\n");
                    }
                    Stage stageWindow = new Stage();
                    //try {
                        //FXMLDocumentController(stageWindow);
                        //graphWindow.arT(4);
                    //} catch (IOException e) {
                       // e.printStackTrace();
                    //}
                }
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
                error("Введено некорректное значение в одно из полей! Пожалуста, вводите только цифры.");
            }
        }

    }

    /**
     * Функция для вывода ошибок при заполнении полей.
     *
     * @param s строка, которая сообщает, какая ошибка допущена при заполнении полей.
     */
    public void error(String s) {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Некорректный ввод");
        alert.setHeaderText(s);
        alert.showAndWait();
    }

    /**
     * Функция запуска алгоритма поиска гратчайших путей графа.
     */
    public void beginAlgorithm() {
        k = 1;
        P.search_algorithm(this);
        P.output_ways(this);
    }

    /**
     * Функция обработки нажатия клавиши "Начать работу алгоритма".
     */
    @FXML
    public void workBegin() {
        if (P.n == 0) {
            error("Граф не сгенерирован");
        } else {
            if (textField1.getText() == null || textField1.getText().length() == 0) {
                error("Введите вершину");
                int m = Integer.parseInt(amountEdges.getText());
                P.v = m;
            } else {
                try {
                    int m = Integer.parseInt(textField1.getText());
                    P.v = m;
                    if (m > P.n || m <= 0)
                        error("Вершина задана неверно");
                    else beginAlgorithm();
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    error("Введено некорректное значение в одно из полей! Пожалуста, вводите только цифры.");
                }
            }
        }
    }


    /**
     * Функция обработки нажатия клавиши "Считать граф из файла".
     *
     * @throws IOException
     */
    @FXML
    public void fileGeneration ()  {
        k = 0;
        P.list.clear();
        P.ways.clear();
        P.road.clear();
        P.V=-1;
        textGraph.clear();
        P.input_file();
        for (int i = 0; i < P.list.size(); i++) {
            textGraph.appendText("Путь из вершины " + (P.list.elementAt(i).from + 1) + " в вершину " + (P.list.elementAt(i).to + 1) + ": " + P.list.elementAt(i).l + "\n");
        }
        Stage stageWindow = new Stage();
        try {
            FXMLDocumentController(stageWindow);
            //graphWindow.arT(4);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Функция вызова дочернего окна для отрисовки графа.
     */
    public void FXMLDocumentController(Stage stageWindow) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("GUI_prototype.fxml"));
        stageWindow.setTitle("Граф");
        Scene scene = new Scene(root);
        stageWindow.setScene(scene);
        stageWindow.show();
    }

    /**
     * Функция отображения компонентов главной панели при нажатии клавиши "Визуализация работы алгоритма".
     */
    @FXML
    public void nextMenu() {
        if (k == 0)
            error("Алгоритм не начал свою работу");
        else {
            next.setVisible(false);
            text3.setVisible(false);
            text4.setVisible(false);
            text5.setVisible(false);
            text6.setVisible(false);
            amountEdges.setVisible(false);
            amountVertex.setVisible(false);
            textField1.setVisible(false);
            begAlg.setVisible(false);
            generateGraph.setVisible(false);
            fileGraph.setVisible(false);
            back.setVisible(true);
            graphWay.setVisible(true);
            vertex.setVisible(true);
            graphAlg.setVisible(true);
            graphV.setVisible(true);
            Setting = false;
        }
    }

    /**
     * Функция возврата в пердыдущее меню при визуализаци алгоритма.
     */
    @FXML
    public void backMenu() {
        next.setVisible(true);
        text3.setVisible(true);
        text4.setVisible(true);
        text5.setVisible(true);
        text6.setVisible(true);
        amountEdges.setVisible(true);
        amountVertex.setVisible(true);
        textField1.setVisible(true);
        begAlg.setVisible(true);
        generateGraph.setVisible(true);
        fileGraph.setVisible(true);
        back.setVisible(false);
        graphWay.setVisible(false);
        vertex.setVisible(false);
        graphAlg.setVisible(false);
        graphV.setVisible(false);
        Setting = true;
    }

    /**
     * Функция вывода кратчайшего пути до заданной вершины.
     */
    @FXML
    public void getWay() {
        if (graphV.getText() == null || graphV.getText().length() == 0) {
            error("Введите количество вершин");
            int x = Integer.parseInt(graphV.getText());
        } else {

            try {
                int x = Integer.parseInt(graphV.getText());
                x--;
                P.V = x;
                if (x + 1 > P.n || x + 1 < 0 || x == (P.v)) {

                    error("Граф может быть не отображен.");
                } else {
                    Stage stageWindow = new Stage();
                    try {
                        FXMLDocumentController(stageWindow);
                        //graphWindow.arT(4);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
                error("Введено некорректное значение в одно из полей! Пожалуста, вводите только цифры.");
            }
        }
    }
}
