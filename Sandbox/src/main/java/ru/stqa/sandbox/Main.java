package ru.stqa.sandbox;

public class Main {

    public static void main(String[] args) {
        Point A = new Point(2, 4);
        Point B = new Point(5, 7);
        System.out.println("Посчитаем расстояние между точками " + A.x +
                " и " + A.y + " + " + B.x + " и " + B.y + " оно равно " + +distance(A, B));


        System.out.println("Посчитаем расстояние между точками вторым способом " + A.x +
                " и " + A.y + " + " + B.x + " и " + B.y + " оно равно " +A.distance(B));
    }

    public static double distance(Point point, Point point1) {
        double f = Math.sqrt((point1.x - point.x) * (point1.x - point.x) + (point1.y - point.y) * (point1.y - point.y));
        return f;

    }


}
