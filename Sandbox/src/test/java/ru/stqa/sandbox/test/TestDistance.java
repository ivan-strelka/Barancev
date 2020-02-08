package ru.stqa.sandbox.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.sandbox.Point;

public class TestDistance {

    @Test
    public void testPoints() {
        Point point = new Point(2, 3);
        Point point2 = new Point(5, 5);
        Assert.assertEquals(point2.distance(point), 3.605551275463989);
    }

    @Test
    public void testPoints2() {
        Point point = new Point(0, 0);
        Point point2 = new Point(5, 5);
        Assert.assertEquals(point2.distance(point), 7.0710678118654755);
    }

    @Test
    public void testPoint3() {
        Point point = new Point(0, 0);
        Point point2 = new Point(5, 5);
        Assert.assertNotEquals(point2.distance(point), 7.07);

    }

    @Test
    public void testPoint4() {
        Point point = new Point(0, 0);
        Point point2 = new Point(0, 0);
        Assert.assertEquals(point2.distance(point), 0);
    }
}
