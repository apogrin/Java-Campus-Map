/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author orenj
 */
public class POITest {
    
    public POITest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getCoordinate method, of class POI.
     */
    @Test
    public void testGetCoordinate() {
        System.out.println("getCoordinate");
        int[] expResult = {1,2};
        POI instance = new POI("Bathroom", 1, "Bathroom1", expResult, 1, false);
        int[] result = instance.getCoordinate();
        assertArrayEquals(expResult, result);
    }
    
    /**
     * Test of setCoordinate method, of class POI.
     */
    @Test
    public void testSetCoordinate() {
        System.out.println("setCoordinate");
        POI instance = new POI("Bathroom", 1, "Bathroom1", new int[]{1,2}, 1, false);
        int[] expResult = {3,4}; // coordinates
        instance.setCoordinate(expResult);
        int[] result = instance.getCoordinate();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of setName method, of class POI.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        POI instance = new POI("Bathroom", 1, "Bathroom1", new int[]{1,2}, 1, false);
        String expResult = "Classroom";
        instance.setName(expResult);
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of setRoomNum method, of class POI.
     */
    @Test
    public void testSetRoomNum() {
        System.out.println("setRoomNum");
        int roomNum = 0;
        POI instance = new POI("Bathroom", 1, "Bathroom1", new int[]{1,2}, 1, false);
        int expResult = 1;
        instance.setRoomNum(expResult);
        int result = instance.getRoomNum();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDescription method, of class POI.
     */
    @Test
    public void testGetDescription() {
        System.out.println("getDescription");
        POI instance = new POI("Bathroom",1, "Bathroom1", new int[]{1,2}, 1, false);
        String expResult = "POI: Bathroom1, Room Number: 1, Coordinates: 1, 2";
        String result = instance.getDescription();
        assertEquals(expResult, result);
    }

    /**
     * Test of getName method, of class POI.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        POI instance = new POI("Bathroom",1, "Bathroom1", new int[]{1,2}, 1, false);
        String expResult = "Bathroom1";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getRoomNum method, of class POI.
     */
    @Test
    public void testGetRoomNum() {
        System.out.println("getRoomNum");
        POI instance = new POI("Bathroom",1, "Bathroom1", new int[]{1,2}, 1, false);
        int expResult = 1;
        int result = instance.getRoomNum();
        assertEquals(expResult, result);
    }

    /**
     * Test of setFloor method, of class POI.
     */
    @Test
    public void testSetFloor() {
        System.out.println("setFloor");
        POI instance = new POI("Bathroom",1, "Bathroom1", new int[]{1,2}, 1, false);
        int expResult = 2;
        instance.setFloor(2);
        int result = instance.getFloor();
        assertEquals(expResult, result);
    }

    /**
     * Test of setFav method, of class POI.
     */
    @Test
    public void testSetFav() {
        System.out.println("setFav");
        POI instance = new POI("Bathroom",1, "Bathroom1", new int[]{1,2}, 1, false);
        boolean expResult = true;
        instance.setFav(true);
        boolean result = instance.getFav();
        assertEquals(expResult, result);
    }

    /**
     * Test of getFav method, of class POI.
     */
    @Test
    public void testGetFav() {
        System.out.println("getFav");
        POI instance = new POI("Bathroom",1, "Bathroom1", new int[]{1,2}, 1, false);
        boolean expResult = false;
        boolean result = instance.getFav();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLayer method, of class POI.
     */
    @Test
    public void testGetLayer() {
        System.out.println("getLayer");
        POI instance = new POI("Bathroom",1, "Bathroom1", new int[]{1,2}, 1, false);
        String expResult = "Bathroom";
        String result = instance.getLayer();
        assertEquals(expResult, result);
    }

    /**
     * Test of getFloor method, of class POI.
     */
    @Test
    public void testGetFloor() {
        System.out.println("getFloor");
        POI instance = new POI("Bathroom",1, "Bathroom1", new int[]{1,2}, 1, false);
        int expResult = 1;
        int result = instance.getFloor();
        assertEquals(expResult, result);
    }

    /**
     * Test of getBuilding method, of class POI.
     */
    @Test
    public void testGetBuilding() {
        System.out.println("getBuilding");
        POI instance = new POI("Bathroom",1, "Bathroom1", new int[]{1,2}, 1, false, "Middlesex");
        String expResult = "Middlesex";
        String result = instance.getBuilding();
        assertEquals(expResult, result);
    }
    
}
