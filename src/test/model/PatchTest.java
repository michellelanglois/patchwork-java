package model;

import model.patches.*;

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
        squarePatchA = new Square("A");
        squarePatchB = new Square("B");
        halfSquarePatch = new HalfSquare(90);
        halfSquareTrianglePatch = new HalfSquareTriangle(270);
    }

    @Test
    public void testSquarePatchAConstructor() {
        assertEquals(Patch.SQUARE, squarePatchA.getType());
        assertEquals(0, squarePatchA.getRotation());
        assertEquals("A", squarePatchA.getFabrics().get(0));
        assertNull(squarePatchA.getFabrics().get(1));
    }

    @Test
    public void testSquarePatchBConstructor() {
        assertEquals(Patch.SQUARE, squarePatchB.getType());
        assertEquals(0, squarePatchB.getRotation());
        assertNull(squarePatchB.getFabrics().get(0));
        assertEquals("B", squarePatchB.getFabrics().get(1));
    }

    @Test
    public void testHalfSquarePatchConstructor() {
        assertEquals(Patch.HALF_SQUARE, halfSquarePatch.getType());
        assertEquals(90, halfSquarePatch.getRotation());
        assertEquals("A", halfSquarePatch.getFabrics().get(0));
        assertEquals("B", halfSquarePatch.getFabrics().get(1));
    }

    @Test
    public void testHalfSquareTrianglePatchConstructor() {
        assertEquals(Patch.HALF_TRIANGLE, halfSquareTrianglePatch.getType());
        assertEquals(270, halfSquareTrianglePatch.getRotation());
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
        assertEquals(0, squarePatchA.calculateFabric("B", 2.5));
    }

    @Test
    public void testSquarePatchCalculateFabricContainsFabric() {
        // test side length of 2.5
        assertEquals(9.0, squarePatchA.calculateFabric("A", 2.5));
        // test side length of 2.0
        assertEquals(6.25, squarePatchA.calculateFabric("A", 2.0));
    }

    @Test
    public void testHalfSquarePatchCalculateFabricDoesNotContainFabric() {
        assertEquals(0, halfSquarePatch.calculateFabric("C", 2.5));
    }

    @Test
    public void testHalfSquarePatchCalculateFabricContainsFabric() {
        // test side length of 2.5
        assertEquals(5.25, halfSquarePatch.calculateFabric("A", 2.5));
        assertEquals(5.25, halfSquarePatch.calculateFabric("B", 2.5));

        // test side length of 3.0
        assertEquals(7.0, halfSquarePatch.calculateFabric("A", 3.0));
        assertEquals(7.0, halfSquarePatch.calculateFabric("B", 3.0));
    }

    @Test
    public void testHalfSquareTrianglePatchCalculateFabricDoesNotContainFabric() {
        assertEquals(0.0, halfSquareTrianglePatch.calculateFabric("C", 2.5));
    }

    @Test
    public void testHalfSquareTrianglePatchCalculateFabricContainsFabric() {
        // test side length of 2.5
        assertEquals(8.0, halfSquareTrianglePatch.calculateFabric("A", 2.5));
        assertEquals(8.0, halfSquareTrianglePatch.calculateFabric("B", 2.5));

        // test side length of 3.0
        assertEquals(10.125, halfSquareTrianglePatch.calculateFabric("A", 3.0));
        assertEquals(10.125, halfSquareTrianglePatch.calculateFabric("B", 3.0));

        // test side length of 2.0
        assertEquals(6.125, halfSquareTrianglePatch.calculateFabric("A", 2.0));
        assertEquals(6.125, halfSquareTrianglePatch.calculateFabric("B", 2.0));
    }

}
