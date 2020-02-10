package model;

import model.patches.HalfSquare;
import model.patches.HalfSquareTriangle;
import model.patches.Square;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PatchTest {

    private Patch squarePatchA;
    private Patch squarePatchB;
    private Patch halfSquarePatch;
    private Patch halfSquareTrianglePatch;

    @BeforeEach
    public void runBefore() {
        squarePatchA = new Square(2.5, "A");
        squarePatchB = new Square(2.5, "B");
        halfSquarePatch = new HalfSquare(2.5, 90);
        halfSquareTrianglePatch = new HalfSquareTriangle(2.5, 270);
    }

    @Test
    public void testSquarePatchAConstructor() {
        assertEquals(Patch.SQUARE, squarePatchA.getType());
        assertEquals(0, squarePatchA.getRotation());
        assertEquals(2.5, squarePatchA.getFinishedSideLength());
        assertEquals("A", squarePatchA.getFabrics().get(0));
        assertEquals(null, squarePatchA.getFabrics().get(1));
    }

    @Test
    public void testSquarePatchBConstructor() {
        assertEquals(Patch.SQUARE, squarePatchB.getType());
        assertEquals(0, squarePatchB.getRotation());
        assertEquals(2.5, squarePatchB.getFinishedSideLength());
        assertEquals(null, squarePatchB.getFabrics().get(0));
        assertEquals("B", squarePatchB.getFabrics().get(1));
    }

    @Test
    public void testHalfSquarePatchConstructor() {
        assertEquals(Patch.HALF_SQUARE, halfSquarePatch.getType());
        assertEquals(90, halfSquarePatch.getRotation());
        assertEquals(2.5, halfSquarePatch.getFinishedSideLength());
        assertEquals("A", halfSquarePatch.getFabrics().get(0));
        assertEquals("B", halfSquarePatch.getFabrics().get(1));
    }

    @Test
    public void testHalfSquareTrianglePatchConstructor() {
        assertEquals(Patch.HALF_TRIANGLE, halfSquareTrianglePatch.getType());
        assertEquals(270, halfSquareTrianglePatch.getRotation());
        assertEquals(2.5, halfSquareTrianglePatch.getFinishedSideLength());
        assertEquals(2, halfSquareTrianglePatch.getFabrics().size());
        assertEquals("A", halfSquareTrianglePatch.getFabrics().get(0));
        assertEquals("B", halfSquareTrianglePatch.getFabrics().get(1));
    }

    @Test
    public void testContainsFabricDoesContain() {
        assertTrue(squarePatchA.containsFabric("A"));
        assertTrue(squarePatchB.containsFabric("B"));
        assertTrue(halfSquareTrianglePatch.containsFabric("A"));
        assertTrue(halfSquarePatch.containsFabric("A"));
    }

    @Test
    public void testContainsFabricDoesNotContain() {
        assertFalse(halfSquarePatch.containsFabric("C"));
        assertFalse(squarePatchA.containsFabric("B"));
        assertFalse(squarePatchB.containsFabric("A"));
    }

    @Test
    public void testSquarePatchCalculateFabricDoesNotContainFabric() {
        assertEquals(0, squarePatchA.calculateFabric("B"));
    }

    @Test
    public void testSquarePatchCalculateFabricContainsFabric() {
        assertEquals(9.0, squarePatchA.calculateFabric("A"));
        squarePatchA.setFinishedSideLength(2);
        assertEquals(6.25, squarePatchA.calculateFabric("A"));
    }

    @Test
    public void testHalfSquarePatchCalculateFabricDoesNotContainFabric() {
        assertEquals(0, halfSquarePatch.calculateFabric("C"));
    }

    @Test
    public void testHalfSquarePatchCalculateFabricContainsFabric() {
        assertEquals(5.25, halfSquarePatch.calculateFabric("A"));
        assertEquals(5.25, halfSquarePatch.calculateFabric("B"));

        halfSquarePatch.setFinishedSideLength(3.0);
        assertEquals(7.0, halfSquarePatch.calculateFabric("A"));
        assertEquals(7.0, halfSquarePatch.calculateFabric("B"));
    }

    @Test
    public void testHalfSquareTrianglePatchCalculateFabricDoesNotContainFabric() {
        assertEquals(0.0, halfSquareTrianglePatch.calculateFabric("C"));
    }

    @Test
    public void testHalfSquareTrianglePatchCalculateFabricContainsFabric() {
        assertEquals(8.0, halfSquareTrianglePatch.calculateFabric("A"));
        assertEquals(8.0, halfSquareTrianglePatch.calculateFabric("B"));

        halfSquareTrianglePatch.setFinishedSideLength(3.0);
        assertEquals(10.125, halfSquareTrianglePatch.calculateFabric("A"));
        assertEquals(10.125, halfSquareTrianglePatch.calculateFabric("B"));

        halfSquareTrianglePatch.setFinishedSideLength(2.0);
        assertEquals(6.125, halfSquareTrianglePatch.calculateFabric("A"));
        assertEquals(6.125, halfSquareTrianglePatch.calculateFabric("B"));
    }

}
