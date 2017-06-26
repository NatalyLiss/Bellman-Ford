package sample;

/**
 * Created by helladmin on 26.06.2017.
 */

        import java.io.File;
        import java.io.FileNotFoundException;
        import java.util.Random;
        import java.util.Scanner;
        import java.util.Vector;

/**
 * Класс для хранения графа и отображения действий над ним.
 */
public class Graph implements Graph_Interface {
    /**
     * Класс для хранения ребер графа.
     */
    public class Element_graph_way {
        int from;
        int to;
        int l;
    }

    /**
     * Класс для хранения координат узлов графа.
     */
    public class Ways_Point {
        int x;
        int y;
        int Name;
    }

    final int inf = 1000000000;
    int x = 130;
    int y = 105;
    int vertexX = 120;
    int vertexY = 105;
    final Random random = new Random();
    Vector<Element_graph_way> list = new Vector<Element_graph_way>();
    Vector<Integer> ways = new Vector<Integer>();
    Vector<Integer> road = new Vector<Integer>();
    Vector<Ways_Point> vicual = new Vector<Ways_Point>();
    int n;//количество узлов
    int m = 0;//количество ветвей
    int v;// узел из которого нужно считать пути;
    int V = -1;
    int negativeCircle = 0;

    /**
     * Функция считывания графа из файла.
     */
    public void input_file() {
        negativeCircle = 0;
        list.clear();
        vicual.clear();
        Scanner sc;
        try {
            sc = new Scanner(new File("D://in.txt"));
            try {
                if (sc.hasNextInt()) { // возвращает истинну если с потока ввода можно считать целое число
                    n = sc.nextInt();// считывает целое число с потока ввода и сохраняем в переменную
                } else System.out.println("В файле недостаточно данных");
                if (sc.hasNextInt()) {
                    m = sc.nextInt();
                } else System.out.println("В файле недостаточно данных");
                X_Y();
                for (int j = 0; j < m; j++) {
                    Element_graph_way Q = new Element_graph_way();
                    if (sc.hasNextInt()) {
                        Q.from = sc.nextInt();
                        Q.from--;
                    }
                    if (sc.hasNextInt()) {
                        Q.to = sc.nextInt();
                        Q.to--;
                    }
                    if (sc.hasNextInt()) {
                        Q.l = sc.nextInt();
                    }
                    list.add(Q);

                }
            } catch (Exception ex) {
                System.out.println("Файл пуст!");
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Файл не существует!");
        }

    }

    /**
     * Функция генерирования рандомного графа.
     */
    public void input_generation() {

    }

    /**
     * Функция поиска кратчайших путей из заданной вершины в графе.
     *
     * @param P контроллер, в который будут выведены результаты.
     */
    public void search_algorithm(Controller P) {
        for (int i = 0; i < n; ++i) {
            ways.add(inf);
            road.add(-1);
        }
        v--;
        ways.set(v, 0);
        P.graphAlg.clear();
        P.graphAlg.appendText((v + 1) + " |");
        for (int k = 1; k <= n; k++) {
            if (k > 9) {
                P.graphAlg.appendText(" " + k + " |");
            } else P.graphAlg.appendText(" " + k + " |");
        }
        P.graphAlg.appendText("\n");
        P.graphAlg.appendText("  ");
        for (int k = 0; k < n; k++) {
            if (ways.elementAt(k) == inf)
                P.graphAlg.appendText(" ∞  ");
            else {
                if (ways.elementAt(k) > 9)
                    P.graphAlg.appendText(" " + ways.elementAt(k) + " ");
                else P.graphAlg.appendText(" " + ways.elementAt(k) + " ");
            }
        }
        P.graphAlg.appendText("\n");
        for (int i = 1; i <= n; ++i) {
            for (int j = 0; j < m; ++j) {
                if ((ways.elementAt(list.elementAt(j).from) < inf) & ((ways.elementAt(list.elementAt(j).from) + list.elementAt(j).l) < ways.elementAt(list.elementAt(j).to)))
                    if (i == n) {
                        negativeCircle = 1;
                        P.graphAlg.clear();
                        P.result.clear();
                        P.graphAlg.appendText("Граф имеет отрицательные циклы");
                        P.result.appendText("Граф имеет отрицательные циклы");
                        return;
                    } else {
                        ways.set(list.elementAt(j).to, (ways.elementAt(list.elementAt(j).from) + list.elementAt(j).l));
                        road.set(list.elementAt(j).to, list.elementAt(j).from);
                        P.graphAlg.appendText("  ");
                        for (int k = 0; k < n; k++) {
                            if (ways.elementAt(k) == inf)
                                P.graphAlg.appendText(" ∞  ");
                            else {
                                if (ways.elementAt(k) > 99)
                                    P.graphAlg.appendText(" " + ways.elementAt(k) + "");
                                else P.graphAlg.appendText(" " + ways.elementAt(k) + " ");
                            }
                        }
                        P.graphAlg.appendText("\n");

                    }
            }

        }
    }

    /**
     * Функция вывода кратчайших путей из заданной вершины в графе.
     *
     * @param P контроллер, в который будут выведены результаты.
     */
    public void output_ways(Controller P) {
        if (negativeCircle == 0) {
            P.result.clear();
            Vector<Integer> path = new Vector<Integer>();
            for (int j = 0; j < n; j++)
                if (j != (v)) {
                    if (ways.elementAt(j) == 1000000000) {
                        P.result.appendText("Путь из вершины " + (v + 1) + " в вершину " + (j + 1) + ": NO\n");
                    } else {
                        path.clear();
                        for (int cur = j; cur != -1; cur = road.elementAt(cur))
                            path.add(cur);
                        P.result.appendText("Путь из вершины " + (v + 1) + " в вершину " + (j + 1) + ": " + ways.elementAt(j) + "\nКратчайший путь: ");
                        for (int i = path.size() - 1; i >= 1; i--) {
                            int l = (path.elementAt(i) + 1);
                            P.result.appendText(l + "->");
                        }
                        P.result.appendText((path.elementAt(0) + 1) + "\n");
                        path.clear();
                    }
                }
        }
    }

    /**
     * Функция присвоения координат узлам графа.
     */
    public void X_Y() {
        double fi = 360 / n;
        for (int i = 0; i < n; i++) {
            Ways_Point q = new Ways_Point();
            q.x = x + (int) (long) (vertexX * Math.cos(i * fi * Math.PI / 180));
            q.y = y + (int) (long) (vertexY * Math.sin(i * fi * Math.PI / 180));
            q.Name = i + 1;
            vicual.add(q);
        }
    }

}