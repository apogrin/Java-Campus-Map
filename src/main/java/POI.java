/**
 * This class represents a POI on the map. POIs are an important aspect of the GUI and allow for users to access various points on the map.
 * @author stephenkinsey
 */
public class POI {
    /** layer of the POI */
    private String layer;
    /** room number of the POI */
    private int roomNum;
    /** name of the POI */
    private String name;
    /** coordinate vector of the POI (x,y) */
    private int[] coordinate; // should always be length 2
    /** floor of the POI */
    private int floor;
    /** favourite attributes (true if favourite) */
    private Boolean fav;
    /** building of the POI */
    private String building = "";
    
    /**
     * Normal constructor that is used
     * @param layer layer of the POI
     * @param roomNum room number of the POI
     * @param name name of the POI
     * @param coordinate coordinate vector of the POI (x,y)
     * @param floor floor of the POI
     * @param fav favourite attributes (true if favourite)
     */
    public POI(String layer, int roomNum, String name, int[] coordinate, int floor, Boolean fav) {
        this.layer = layer;
        this.roomNum = roomNum;
        this.name = name;
        this.coordinate = coordinate;
        this.floor = floor;
        this.fav = false;
    }
    
    /**
     * Secondary constructor for adding POIs
     * @param name name of the POI
     * @param coordinate coordinate vector of the POI (x,y)
     * @param roomNum the room number of this POI
     */
    public POI(String name, int[] coordinate, int roomNum) {
        this.roomNum = roomNum;
        this.name = name;
        this.coordinate = coordinate;
        this.fav = false;
    }
    
    /**
     * Constructor used when searching a POI
     * Normal constructor that is used
     * @param layer layer of the POI
     * @param roomNum room number of the POI
     * @param name name of the POI
     * @param coordinate coordinate vector of the POI (x,y)
     * @param floor floor of the POI
     * @param fav favourite attributes (true if favourite)
     * @param building building of the POI
     */
    public POI(String layer, int roomNum, String name, int[] coordinate, int floor, Boolean fav, String building) {
        this.layer = layer;
        this.roomNum = roomNum;
        this.name = name;
        this.coordinate = coordinate;
        this.floor = floor;
        this.fav = false;
        this.building = building;
    }
    
    /**
     * Setter for layer:
     * @param layer layer of the POI
     */
    public void setLayer(String layer) {
        this.layer = layer;
    }
    
    /**
     * Setter for coordinates:
     * @param coordinate coordinate vector of the POIs location
     */
    public void setCoordinate(int[] coordinate) {
        this.coordinate = coordinate;
    }
    
    /**
     * Getter for coordinates:
     * @return coordinate vector of POIs location
     */
    public int[] getCoordinate() {
        return this.coordinate;
    }
    
    /**
     * Setter for name variable:
     * @param name of POI
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Getter for name variable:
     * @return name name of POI
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Setter for room number of POI:
     * @param roomNum room number of POI
     */
    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
    }
    
    /**
     * Getter for room number of POI
     * @return this POI's room number
     */
    public int getRoomNum() {
        return this.roomNum;
    }
    
    /**
     * Setter for floor variable
     * @param floor floor of the POI
     */
    public void setFloor(int floor) {
        this.floor = floor;
    }
    
    /**
     * Retrieves a string description of the POI used for pop-up messages
     * @return String representation of POI
     */
    public String getDescription() {
        return "POI: " + name + ", Room Number: " + roomNum + ", "
                + "Coordinates: " + coordinate[0] + ", " + coordinate[1];
    }
    
    /**
     * Setter for is Favourite boolean
     * @param bool boolean variable, true if POI is a favourite
     */
    public void setFav(boolean bool) {
        this.fav = bool;
    }
    
    /**
     * Getter for the favourite boolean
     * @return favourite boolean
     */
    public boolean getFav() {
        return this.fav;
    }
    
    /**
     * Getter for Layer Variable:
     * @return layer of POI
     */
    public String getLayer() {
        return this.layer;
    }
    
    /**
     * Getter for floor of POI:
     * @return floor of POI
     */
    public int getFloor() {
        return this.floor;
    }
    
    /**
     * getter for the building name
     * @return building of the POI object
     */
    public String getBuilding() {
        return this.building;
    }
    
    
 }

