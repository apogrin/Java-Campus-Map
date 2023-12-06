/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
import javax.swing.*;
import java.io.FileReader;
import java.io.FileWriter;
import org.json.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

/**
 * This class represents Middlesex3 frame
 * @author louie
 */
//Components
public class Middlesex3 extends javax.swing.JFrame {
    
    /**
     * This default constructor creates a linked list with all layers and then
     * call the createButtonsFromJSON method with the created linked list
     */
    public Middlesex3() {
        initComponents();
        LinkedList<String> ll = new LinkedList<String>();
        ll.add("Bathroom");
        ll.add("Food");
        ll.add("Other");
        ll.add("Navigation");
        ll.add("Lab");
        ll.add("Classroom");
        ll.add("User Defined");
        this.createButtonsFromJSON("dataFiles/POI.json", ll);
    }
    
    /**
     * This constructor takes in a linked list and calls the createButtonsFromJSON
     * with said linked list
     * @param ll this linked list contains the strings of all the layers
     * to be shown on the map
     */
    public Middlesex3(LinkedList<String> ll) {
        initComponents();
        this.createButtonsFromJSON("dataFiles/POI.json", ll);
    }
    
    /**
     * method returns the JLabel that our map png is placed on
     * @return returns the Jlabel that our map png is placed on
     */
    public JLabel getMapLabel() {
        return map;
    }
    /**
     * This method is used to add the POI's to the map from the JSON file
     * See comments within code for more detail to individual parts of method
     * @param jsonFilePath this string is the path to the POI.Json file
     * @param ll this linked list contains the strings of all the layers
     * to be shown on the map
     */
     public void createButtonsFromJSON(String jsonFilePath, LinkedList<String> ll) {
        // Read in the JSON file
        try (FileReader reader = new FileReader(jsonFilePath)) {
            JSONObject json = new JSONObject(new JSONTokener(reader));
            JSONArray buildings = json.getJSONArray("buildings");
            JSONObject middlesex = buildings.getJSONObject(0);

            JSONArray pointsOfInterest = middlesex.getJSONArray("points_of_interest");

            // Loop through the points of interest and create a button for each one
            for (int i = 0; i < pointsOfInterest.length(); i++) {
                JSONObject poiJson = pointsOfInterest.getJSONObject(i);
                int floor = poiJson.getInt("floor");
                String layer = poiJson.getString("layer");
                if ((floor == 3) && (ll.contains(layer))) {
                    // ensures that the POI is on this floor and the layer is in the linked list layer
                    int roomNum = poiJson.getInt("room_number");
                    String name = poiJson.getString("name");
                    int[] coordinate = new int[2];
                    int fav = poiJson.getInt("favourite");
                    Boolean bool = false;
                    if (fav == 1){
                        bool = true;
                    }
                    coordinate[0] = poiJson.getJSONObject("coordinates").getInt("latitude");
                    coordinate[1] = poiJson.getJSONObject("coordinates").getInt("longitude");
                    POI poi = new POI(layer, roomNum, name, coordinate, floor, bool);

                        // Create a button for the POI using its coordinates
                    JButton button = new JButton(name);
                    if (layer.equals("Navigation")) {
                        button.setBackground(new java.awt.Color(0, 0, 0));
                    } else if (layer.equals("Food")){
                        button.setBackground(new java.awt.Color(0, 255, 0));
                    } else if (layer.equals("Bathroom")){
                        button.setBackground(new java.awt.Color(0, 255, 255));
                    } else if (layer.equals("Classroom")){
                        button.setBackground(new java.awt.Color(0, 0, 255));
                    } else if (layer.equals("Lab")){
                        button.setBackground(new java.awt.Color(255, 0, 0));
                    } else if (layer.equals("User Defined")){
                        button.setBackground(new java.awt.Color(255, 0, 255));
                    } else {
                        button.setBackground(new java.awt.Color(128, 128, 128));
                    }
                    //button.setBackground(new java.awt.Color(255, 0, 0));
                    button.setText("");
                    int x = poi.getCoordinate()[0];
                    int y = poi.getCoordinate()[1];
                    button.setBounds(x, y, 15, 15); // Set the button position and size
                    if (bool == false) {
                        button.setBorder(BorderFactory.createLineBorder(new java.awt.Color(0,0,0), 2));
                    } else {
                        button.setBorder(BorderFactory.createLineBorder(new java.awt.Color(255,215,0), 2));
                    }
                    // Add ActionListener to the button
                    button.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            if (LoginFrame.isDev) {
                                Object[] options = {"Set Favourite", "Unfavourite", "Close", "Edit", "Remove"}; 
                                int result = JOptionPane.showOptionDialog(null, poi.getDescription(), "POI Description", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                                if (result == 0) {
                                    POI selectedPOI = poi;
                                    selectedPOI.setFav(true);
                                    button.setBorder(BorderFactory.createLineBorder(new java.awt.Color(255,215,0), 2));
                                    poiJson.put("favourite", 1);
                                    try {
                                        FileWriter fileWriter = new FileWriter("dataFiles/POI.json");
                                        fileWriter.write(json.toString());
                                        fileWriter.flush();
                                        fileWriter.close();
                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                    }
                                } else if (result == 1) {
                                    POI selectedPOI = poi;
                                    selectedPOI.setFav(false);
                                    button.setBorder(BorderFactory.createLineBorder(new java.awt.Color(0,0,0), 2));
                                    poiJson.put("favourite", 0);
                                    try {
                                        FileWriter fileWriter = new FileWriter("dataFiles/POI.json");
                                        fileWriter.write(json.toString());
                                        fileWriter.flush();
                                        fileWriter.close();
                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                    }
                                } else if (result == 2) {
                                    return;
                                } else if (result == 3) {
                                    POI selectedPOI = poi;
                                    JPanel panel = new JPanel(new GridLayout(0, 1));
                                    JTextField nameField = new JTextField();
                                    panel.add(new JLabel("Name:"));
                                    panel.add(nameField);

                                    JTextField floorField = new JTextField();
                                    panel.add(new JLabel("Floor:"));
                                    panel.add(floorField);

                                    JTextField roomNumField = new JTextField();
                                    panel.add(new JLabel("Room Number:"));
                                    panel.add(roomNumField);


                                    String[] layerOptions = {"Navigation", "Food", "Bathroom", "Classroom", "Lab", "Other"};
                                    JComboBox<String> layerComboBox = new JComboBox<>(layerOptions);
                                    panel.add(new JLabel("Layer:"));
                                    panel.add(layerComboBox);

                                    // Show the input dialog to the user
                                    int res = JOptionPane.showConfirmDialog(null, panel, "Add POI", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                                    // Process the user input if they clicked OK
                                    if (res == JOptionPane.OK_OPTION) {
                                        String name = nameField.getText();
                                        int roomNum = Integer.parseInt(roomNumField.getText());
                                        int floor = Integer.parseInt(floorField.getText());
                                        String layer = (String) layerComboBox.getSelectedItem();
                                        int[] coordinate = new int[2];
                                        coordinate[0] = 0;
                                        coordinate[1] = 0;
                                        poi.setLayer(layer);
                                        poi.setFloor(floor);
                                        poi.setName(name);
                                        poi.setRoomNum(roomNum);
                                        JOptionPane.showMessageDialog(null, poi.getDescription(), "POI Description", JOptionPane.INFORMATION_MESSAGE);
                                        button.setBorder(BorderFactory.createLineBorder(new java.awt.Color(255,215,0), 2));
                                        poiJson.put("room_number", roomNum);
                                        poiJson.put("name", name);
                                        poiJson.put("floor", floor);
                                        poiJson.put("layer", layer);
                                        hideFrame();
                                        try {
                                            FileWriter fileWriter = new FileWriter("dataFiles/POI.json");
                                            fileWriter.write(json.toString());
                                            fileWriter.flush();
                                            fileWriter.close();
                                        } catch (IOException ex) {
                                            ex.printStackTrace();
                                        }
                                        new Middlesex3(ll).setVisible(true);
                                    }
                                } else if (result == 4) {
                                    int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the selected POI?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                                    if (confirm == JOptionPane.YES_OPTION) {
                                        int indexToRemove = -1;
                                        for (int i = 0; i < pointsOfInterest.length(); i++) {
                                            JSONObject currentPoi = (JSONObject) pointsOfInterest.get(i);
                                            if (currentPoi.get("name").equals(poi.getName())) {
                                                indexToRemove = i;
                                                break;
                                            }
                                        }
                                        if (indexToRemove != -1) {
                                            pointsOfInterest.remove(indexToRemove);
                                            try {
                                                FileWriter fileWriter = new FileWriter("dataFiles/POI.json");
                                                fileWriter.write(json.toString());
                                                fileWriter.flush();
                                                fileWriter.close();
                                            } catch (IOException ex) {
                                                ex.printStackTrace();
                                            }
                                            JOptionPane.showMessageDialog(null, "POI deleted successfully", "POI Deleted", JOptionPane.INFORMATION_MESSAGE);
                                            hideFrame();
                                            new Middlesex3(ll).setVisible(true);
                                        } else {
                                            JOptionPane.showMessageDialog(null, "Unable to find POI in JSON file", "Error", JOptionPane.ERROR_MESSAGE);
                                        }
                                    }
                                }
                            } else {
                                if (poi.getLayer().equals("User Defined")) {
                                    removePOI(poi,button, poiJson, json, pointsOfInterest, ll);
                                    hideFrame();
                                    new Middlesex3(ll).setVisible(true);
                                } else {
                                    Object[] options = {"Set Favourite", "Unfavourite", "Close"}; // additional options
                                    int result = JOptionPane.showOptionDialog(null, poi.getDescription(), "POI Description", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                                    if (result == 0) {
                                        POI selectedPOI = poi;
                                        selectedPOI.setFav(true);
                                        button.setBorder(BorderFactory.createLineBorder(new java.awt.Color(255,215,0), 2));
                                        poiJson.put("favourite", 1);
                                        try {
                                            FileWriter fileWriter = new FileWriter("dataFiles/POI.json");
                                            fileWriter.write(json.toString());
                                            fileWriter.flush();
                                            fileWriter.close();
                                        } catch (IOException ex) {
                                            ex.printStackTrace();
                                        }
                                    } else if (result == 1) {
                                        POI selectedPOI = poi;
                                        selectedPOI.setFav(false);
                                        button.setBorder(BorderFactory.createLineBorder(new java.awt.Color(0,0,0), 2));
                                        poiJson.put("favourite", 0);
                                        try {
                                            FileWriter fileWriter = new FileWriter("dataFiles/POI.json");
                                            fileWriter.write(json.toString());
                                            fileWriter.flush();
                                            fileWriter.close();
                                        } catch (IOException ex) {
                                            ex.printStackTrace();
                                        }
                                    } else if (result == 2) {
                                        return;
                                    }
                                }
                            } 
                            
                        }
                    });

                    map.add(button); // Add the button to the map
                    //System.out.println(poi.getDescription());
                }
                
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
//
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        HeaderBG = new javax.swing.JPanel();
        HeaderTitle = new javax.swing.JLabel();
        OtherBG1 = new javax.swing.JPanel();
        helpButton = new javax.swing.JButton();
        logOutButton = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        SelectFloorBox1 = new javax.swing.JComboBox<>();
        searchBarTextField = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        buildingSelectionButton = new javax.swing.JButton();
        mapScrollPane = new javax.swing.JScrollPane();
        floorMap = new javax.swing.JLayeredPane();
        map = new javax.swing.JLabel();
        poiLegendScrollPane = new javax.swing.JScrollPane();
        floorMap1 = new javax.swing.JLayeredPane();
        classroom = new javax.swing.JCheckBox();
        food = new javax.swing.JCheckBox();
        bath = new javax.swing.JCheckBox();
        labs = new javax.swing.JCheckBox();
        nav = new javax.swing.JCheckBox();
        other = new javax.swing.JCheckBox();
        userdef = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        poiLegendScrollPane1 = new javax.swing.JScrollPane();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        jLabel1 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        goButton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        OtherBG2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        HeaderBG.setBackground(new java.awt.Color(153, 0, 255));

        HeaderTitle.setBackground(new java.awt.Color(255, 255, 255));
        HeaderTitle.setFont(new java.awt.Font("Helvetica Neue", 1, 36)); // NOI18N
        HeaderTitle.setForeground(new java.awt.Color(255, 255, 255));
        HeaderTitle.setText("WesternU Building Navigation System");

        OtherBG1.setBackground(new java.awt.Color(255, 255, 255));

        helpButton.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        helpButton.setText("Help");
        helpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpButtonActionPerformed(evt);
            }
        });

        logOutButton.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        logOutButton.setText("Log Out");
        logOutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logOutButtonActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Helvetica Neue", 3, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(102, 0, 204));
        jButton1.setText("Exit Program");
        jButton1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 0, 204), 3, true));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout OtherBG1Layout = new javax.swing.GroupLayout(OtherBG1);
        OtherBG1.setLayout(OtherBG1Layout);
        OtherBG1Layout.setHorizontalGroup(
            OtherBG1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OtherBG1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(logOutButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(helpButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        OtherBG1Layout.setVerticalGroup(
            OtherBG1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OtherBG1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(logOutButton)
                .addComponent(helpButton)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel3.setBackground(new java.awt.Color(153, 51, 255));

        jLabel4.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Middlesex College Floor 3");

        jLabel2.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Change Floor: ");

        SelectFloorBox1.setFont(new java.awt.Font("Helvetica Neue", 1, 10)); // NOI18N
        SelectFloorBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Floor 1", "Floor 2", "Floor 3", "Floor 4", "Floor 5" }));
        SelectFloorBox1.setSelectedIndex(2);
        SelectFloorBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SelectFloorBox1ActionPerformed(evt);
            }
        });

        searchBarTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBarTextFieldActionPerformed(evt);
            }
        });

        searchButton.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        searchButton.setText("Search");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        buildingSelectionButton.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        buildingSelectionButton.setText("Building Selection");
        buildingSelectionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buildingSelectionButtonActionPerformed(evt);
            }
        });

        floorMap.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        map.setIcon(new javax.swing.ImageIcon("images/Middlesex3.png"));
        map.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mapMouseClicked(evt);
            }
        });

        floorMap.setLayer(map, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout floorMapLayout = new javax.swing.GroupLayout(floorMap);
        floorMap.setLayout(floorMapLayout);
        floorMapLayout.setHorizontalGroup(
            floorMapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(floorMapLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(map)
                .addContainerGap(917, Short.MAX_VALUE))
        );
        floorMapLayout.setVerticalGroup(
            floorMapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(floorMapLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(map)
                .addContainerGap(475, Short.MAX_VALUE))
        );

        mapScrollPane.setViewportView(floorMap);

        floorMap1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        classroom.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        classroom.setText("Classrooms");

        food.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        food.setText("Food");
        food.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                foodActionPerformed(evt);
            }
        });

        bath.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        bath.setText("Bathrooms");
        bath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bathActionPerformed(evt);
            }
        });

        labs.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        labs.setText("Labs");

        nav.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        nav.setText("Navigation");

        other.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        other.setText("Other");
        other.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherActionPerformed(evt);
            }
        });

        userdef.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        userdef.setText("User Defined");

        floorMap1.setLayer(classroom, javax.swing.JLayeredPane.DEFAULT_LAYER);
        floorMap1.setLayer(food, javax.swing.JLayeredPane.DEFAULT_LAYER);
        floorMap1.setLayer(bath, javax.swing.JLayeredPane.DEFAULT_LAYER);
        floorMap1.setLayer(labs, javax.swing.JLayeredPane.DEFAULT_LAYER);
        floorMap1.setLayer(nav, javax.swing.JLayeredPane.DEFAULT_LAYER);
        floorMap1.setLayer(other, javax.swing.JLayeredPane.DEFAULT_LAYER);
        floorMap1.setLayer(userdef, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout floorMap1Layout = new javax.swing.GroupLayout(floorMap1);
        floorMap1.setLayout(floorMap1Layout);
        floorMap1Layout.setHorizontalGroup(
            floorMap1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(floorMap1Layout.createSequentialGroup()
                .addGroup(floorMap1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(food, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bath, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labs, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nav, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(other, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(classroom, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                    .addComponent(userdef, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 301, Short.MAX_VALUE))
        );
        floorMap1Layout.setVerticalGroup(
            floorMap1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(floorMap1Layout.createSequentialGroup()
                .addComponent(bath, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(classroom, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(food, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labs, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nav, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(userdef, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(other, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        poiLegendScrollPane.setViewportView(floorMap1);

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jLabel3.setText("Layer Legend:");
        jLabel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel3.setOpaque(true);

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jLabel5.setText("Select Layers:");
        jLabel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel5.setOpaque(true);

        jLayeredPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Navigation");
        jLabel1.setOpaque(true);

        jLabel8.setBackground(new java.awt.Color(0, 255, 0));
        jLabel8.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jLabel8.setText("Food");
        jLabel8.setOpaque(true);

        jLabel11.setBackground(new java.awt.Color(255, 0, 0));
        jLabel11.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Labs");
        jLabel11.setOpaque(true);

        jLabel9.setBackground(new java.awt.Color(0, 0, 255));
        jLabel9.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Classrooms");
        jLabel9.setOpaque(true);

        jLabel10.setBackground(new java.awt.Color(0, 255, 255));
        jLabel10.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jLabel10.setText("Bathrooms");
        jLabel10.setOpaque(true);

        jLabel13.setBackground(new java.awt.Color(128, 128, 128));
        jLabel13.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jLabel13.setText("Other");
        jLabel13.setOpaque(true);

        jLabel14.setBackground(new java.awt.Color(255, 0, 255));
        jLabel14.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jLabel14.setText("User Defined");
        jLabel14.setOpaque(true);

        jLayeredPane2.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jLabel8, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jLabel11, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jLabel9, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jLabel10, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jLabel13, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jLabel14, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane2Layout = new javax.swing.GroupLayout(jLayeredPane2);
        jLayeredPane2.setLayout(jLayeredPane2Layout);
        jLayeredPane2Layout.setHorizontalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel13)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel8)
                    .addComponent(jLabel11)
                    .addComponent(jLabel14))
                .addContainerGap(82, Short.MAX_VALUE))
        );
        jLayeredPane2Layout.setVerticalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addComponent(jLabel13)
                .addContainerGap())
        );

        poiLegendScrollPane1.setViewportView(jLayeredPane2);

        jButton2.setFont(new java.awt.Font("Helvetica Neue", 1, 10)); // NOI18N
        jButton2.setText("SUBMIT");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        goButton.setFont(new java.awt.Font("Helvetica Neue", 1, 10)); // NOI18N
        goButton.setText("Go");
        goButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goButtonActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Helvetica Neue", 3, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 0, 204));
        jLabel6.setText("ADD POI: click on the map where you want to insert");
        jLabel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel6.setOpaque(true);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(mapScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 821, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(buildingSelectionButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SelectFloorBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(goButton)
                        .addGap(57, 57, 57)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGap(1, 1, 1)
                                    .addComponent(jLabel5)
                                    .addGap(74, 74, 74))
                                .addComponent(poiLegendScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(searchBarTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addComponent(poiLegendScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(searchBarTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(searchButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(poiLegendScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(poiLegendScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(mapScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(goButton)
                    .addComponent(SelectFloorBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(buildingSelectionButton)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        OtherBG2.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout OtherBG2Layout = new javax.swing.GroupLayout(OtherBG2);
        OtherBG2.setLayout(OtherBG2Layout);
        OtherBG2Layout.setHorizontalGroup(
            OtherBG2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        OtherBG2Layout.setVerticalGroup(
            OtherBG2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 7, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout HeaderBGLayout = new javax.swing.GroupLayout(HeaderBG);
        HeaderBG.setLayout(HeaderBGLayout);
        HeaderBGLayout.setHorizontalGroup(
            HeaderBGLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeaderBGLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(HeaderTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 675, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(321, Short.MAX_VALUE))
            .addComponent(OtherBG2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(HeaderBGLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(HeaderBGLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(OtherBG1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        HeaderBGLayout.setVerticalGroup(
            HeaderBGLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeaderBGLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(HeaderTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(OtherBG2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(OtherBG1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(HeaderBG, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(HeaderBG, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SelectFloorBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SelectFloorBox1ActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_SelectFloorBox1ActionPerformed

    /**
     * this method opens our help pdf when the help button is clicked
     * @param evt not used
     */
    private void helpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpButtonActionPerformed
        File pdfFile = new File("dataFiles/2212help.pdf");

        // Open the PDF file using the default PDF viewer
        try {
            Desktop.getDesktop().open(pdfFile);
        } catch (IOException ex) {
            // Handle any errors opening the PDF file
            ex.printStackTrace();
        }
    }//GEN-LAST:event_helpButtonActionPerformed

    /**
     * this method returns users to the create account frame when the lot out button is clicked
     * @param evt not used
     */
    private void logOutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logOutButtonActionPerformed
        new CreateAccountFrame().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_logOutButtonActionPerformed

    /**
     * this method returns the user back to the building selection frame
     * @param evt not used
     */
    private void buildingSelectionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buildingSelectionButtonActionPerformed
        new welcomeScreenFrame().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_buildingSelectionButtonActionPerformed

    /**
     * This method allows users to switch floors within the building by taking the text from the
     * selectFloorBox and switching the user to that frame
     * @param evt not used
     */
    private void goButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goButtonActionPerformed
        if (SelectFloorBox1.getSelectedItem().toString().equals("Floor 1")) {
            new Middlesex1().setVisible(true);
            this.dispose();
        }
        if (SelectFloorBox1.getSelectedItem().toString().equals("Floor 2")) {
            new Middlesex2().setVisible(true);
            this.dispose();
        }
        if (SelectFloorBox1.getSelectedItem().toString().equals("Floor 4")) {
            new Middlesex4().setVisible(true);
            this.dispose();
        }
        if (SelectFloorBox1.getSelectedItem().toString().equals("Floor 5")) {
            new Middlesex5().setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_goButtonActionPerformed
    /**
     * performs the same function as the search button action but does so when the user
     * clicks the enter key rather than clicking the search button
     * @param evt passed to satisfy the parameters for the searchButton action function but not used
     */
    private void searchBarTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBarTextFieldActionPerformed
        searchButtonActionPerformed(evt);
    }//GEN-LAST:event_searchBarTextFieldActionPerformed
    /**
     * this method gets the string from the search bar text field, disregards case sensitivity, 
     * and performs the search functionality via the searchFunc class
     * @param evt not used
     */
    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        // TODO add your handling code here:
        String searchTerm = searchBarTextField.getText();
        searchTerm = searchTerm.toLowerCase();
        SearchFunc search = new SearchFunc(searchTerm, "dataFiles/POI.json", 0, map, searchBarTextField, this);   
    }//GEN-LAST:event_searchButtonActionPerformed
    /**
     * this function gets the coordinates of where the user clicked on the map and calls the addPoiPopUp method
     * @param evt allows the method to get the x and y coordinates of where the user clicked
     */
    private void mapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mapMouseClicked

        int x = evt.getX();
        int y = evt.getY();
        int[] coordinates = new int[]{x,y};
        addPoiPopUp(coordinates);
    }//GEN-LAST:event_mapMouseClicked
    /**
     * this method exits the program 
     * @param evt not used
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void foodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_foodActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_foodActionPerformed

    private void bathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bathActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bathActionPerformed

    /**
     * this method determines which layers have been selected and calls the alt
     * constructor to display the POI's that fit in the desired layers
     * @param evt not used
     */
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        LinkedList<String> selected = new LinkedList<>();
        if (bath.isSelected()) {
            selected.add("Bathroom");
        }
        if (food.isSelected()){
            selected.add("Food");
        }
        if (nav.isSelected()){
            selected.add("Navigation");
        }
        if (other.isSelected()){
            selected.add("Other");
        }
        if (labs.isSelected()){
            selected.add("Lab");
        }
        if (classroom.isSelected()){
            selected.add("Classroom");
        }
        if (userdef.isSelected()){
            selected.add("User Defined");
        }

        new Middlesex3(selected).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void otherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_otherActionPerformed
    
    /**
     * This method adds a POI to the map and ensures its added to the JSON file
     * @param coordinates represents the pixels of where the user wants the POI to be
     */
    public void addPoiPopUp(int[] coordinates) {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        JTextField nameField = new JTextField();
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        
        JTextField roomNumField = new JTextField();
        panel.add(new JLabel("Room Number:"));
        panel.add(roomNumField);
        
        JTextField layerField = new JTextField();
        if (LoginFrame.isDev) {
            panel.add(new JLabel("Layer:"));
            panel.add(layerField);
        }
        
        int result = JOptionPane.showConfirmDialog(null, panel, "Add POI", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            int roomNum = Integer.parseInt(roomNumField.getText());
            try (FileReader reader = new FileReader("dataFiles/POI.json")) {
                JSONObject json = new JSONObject(new JSONTokener(reader));
                JSONArray buildings = json.getJSONArray("buildings");
                JSONObject middlesex = buildings.getJSONObject(0);
                JSONArray pois = middlesex.getJSONArray("points_of_interest");
                String layer = "";
                if (LoginFrame.isDev) {
                    layer = layerField.getText();
                } else {
                    layer = "User Defined";
                }
                // Create a new POI instance and add it to the "Middlesex" section
                POI addPoi = new POI(layer, roomNum, name, coordinates, 3, false);  //HEREEEE
                JSONObject poiJson = new JSONObject();
                poiJson.put("layer", layer);
                poiJson.put("room_number", roomNum);
                poiJson.put("name", name);
                poiJson.put("coordinates", new JSONObject().put("latitude", coordinates[0]).put("longitude", coordinates[1]));
                poiJson.put("floor", addPoi.getFloor());
                poiJson.put("favourite", 0);
                pois.put(poiJson);

                // Write the updated JSON back to the file
                FileWriter fileWriter = new FileWriter("dataFiles/POI.json");
                fileWriter.write(json.toString());
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException | JSONException ex) {
                ex.printStackTrace();
            }
        }
        new Middlesex3().setVisible(true);
        this.dispose();
    }
    /**
     * allows us to dispose this frame when we dont have access to an instance of this frame
     */
    public void hideFrame() {
        this.dispose();
    }
    /**
     * This method allows the user and/or developers to remove a POI from the map and the POI.Json file
     * @param poi The POI to be removed from the map
     * @param button the button representing the POI on the map
     * @param poiJson the JSONObject for putting
     * @param json JSONObject for writing to the json file
     * @param pointsOfInterest the JSONArray for the POI's for the given building
     * @param ll the linked list denoting which layers to be shown on the updated floor map
     */
    public void removePOI(POI poi, JButton button, JSONObject poiJson, JSONObject json, JSONArray pointsOfInterest, LinkedList<String> ll) {
        Object[] options = {"Set Favourite", "Unfavourite", "Edit","Remove", "Close"}; // additional options
        int result = JOptionPane.showOptionDialog(null, poi.getDescription(), "POI Description", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        if (result == 0) {
            POI selectedPOI = poi;
            selectedPOI.setFav(true);
            button.setBorder(BorderFactory.createLineBorder(new java.awt.Color(255,215,0), 2));
            poiJson.put("favourite", 1);
            try {
                FileWriter fileWriter = new FileWriter("dataFiles/POI.json");
                fileWriter.write(json.toString());
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (result == 1) {
            POI selectedPOI = poi;
            selectedPOI.setFav(false);
            button.setBorder(BorderFactory.createLineBorder(new java.awt.Color(0,0,0), 2));
            poiJson.put("favourite", 0);
            try {
                FileWriter fileWriter = new FileWriter("dataFiles/POI.json");
                fileWriter.write(json.toString());
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (result == 4) {
            return;
        } else if (result == 3) {
            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the selected POI?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                int indexToRemove = -1;
                for (int i = 0; i < pointsOfInterest.length(); i++) {
                    JSONObject currentPoi = (JSONObject) pointsOfInterest.get(i);
                    if (currentPoi.get("name").equals(poi.getName())) {
                        indexToRemove = i;
                        break;
                    }
                }
                if (indexToRemove != -1) {
                    pointsOfInterest.remove(indexToRemove);
                    try {
                        FileWriter fileWriter = new FileWriter("dataFiles/POI.json");
                        fileWriter.write(json.toString());
                        fileWriter.flush();
                        fileWriter.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(null, "POI deleted successfully", "POI Deleted", JOptionPane.INFORMATION_MESSAGE);
                    hideFrame();
              
                } else {
                    JOptionPane.showMessageDialog(null, "Unable to find POI in JSON file", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (result == 2) {
            POI selectedPOI = poi;
            JPanel panel = new JPanel(new GridLayout(0, 1));
            JTextField nameField = new JTextField();
            panel.add(new JLabel("Name:"));
            panel.add(nameField);


            JTextField roomNumField = new JTextField();
            panel.add(new JLabel("Room Number:"));
            panel.add(roomNumField);

            // Show the input dialog to the user
            int res = JOptionPane.showConfirmDialog(null, panel, "Add POI", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            // Process the user input if they clicked OK
            if (res == JOptionPane.OK_OPTION) {
                String name = nameField.getText();
                int roomNum = Integer.parseInt(roomNumField.getText());
                int floor = poi.getFloor();
                String layer = "User Defined";
                int[] coordinate = new int[2];
                coordinate[0] = 0;
                coordinate[1] = 0;
                poi.setLayer(layer);
                poi.setFloor(floor);
                poi.setName(name);
                poi.setRoomNum(roomNum);
                JOptionPane.showMessageDialog(null, poi.getDescription(), "POI Description", JOptionPane.INFORMATION_MESSAGE);
                button.setBorder(BorderFactory.createLineBorder(new java.awt.Color(255,215,0), 2));
                poiJson.put("room_number", roomNum);
                poiJson.put("name", name);
                poiJson.put("floor", floor);
                poiJson.put("layer", layer);
                hideFrame();
                new Middlesex3(ll).setVisible(true);
                try {
                    FileWriter fileWriter = new FileWriter("dataFiles/POI.json");
                    fileWriter.write(json.toString());
                    fileWriter.flush();
                    fileWriter.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    /**
     * test
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Middlesex3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Middlesex3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Middlesex3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Middlesex3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Middlesex3().setVisible(true);
            }
        });
    }
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel HeaderBG;
    private javax.swing.JLabel HeaderTitle;
    private javax.swing.JPanel OtherBG1;
    private javax.swing.JPanel OtherBG2;
    private javax.swing.JComboBox<String> SelectFloorBox1;
    private javax.swing.JCheckBox bath;
    private javax.swing.JButton buildingSelectionButton;
    private javax.swing.JCheckBox classroom;
    private javax.swing.JLayeredPane floorMap;
    private javax.swing.JLayeredPane floorMap1;
    private javax.swing.JCheckBox food;
    private javax.swing.JButton goButton;
    private javax.swing.JButton helpButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JCheckBox labs;
    private javax.swing.JButton logOutButton;
    private javax.swing.JLabel map;
    private javax.swing.JScrollPane mapScrollPane;
    private javax.swing.JCheckBox nav;
    private javax.swing.JCheckBox other;
    private javax.swing.JScrollPane poiLegendScrollPane;
    private javax.swing.JScrollPane poiLegendScrollPane1;
    private javax.swing.JTextField searchBarTextField;
    private javax.swing.JButton searchButton;
    private javax.swing.JCheckBox userdef;
    // End of variables declaration//GEN-END:variables
}
