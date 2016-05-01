/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_fx;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author malaka
 */
public class FrmAddProcessController implements Initializable {

    @FXML
    private TextField txtProcessID;
    @FXML
    private TextField txtProcessName;
    @FXML
    private TextField txtCPUBTime;
    @FXML
    private TextField txtStartingDeadline;
    @FXML
    private TextField txtFinishingDeadline;
    @FXML
    private TextField txtArrivalTime;
    @FXML
    private ColorPicker cmbColor;
    @FXML
    private TableView<TableRowData> tblProcess;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnRemove;
    @FXML
    private Button btnOK;
    @FXML
    private Button btnCancle;
    @FXML
    private TableColumn clmID;
    @FXML
    private TableColumn clmName;

    private static ArrayList<ArrayList<Object>> processDetails = new ArrayList();
    private static ObservableList<TableRowData> data = FXCollections.observableArrayList();
    private static Stage stage;

    // 0.id, 
    // 1.name, 
    // 2.bstTime, 
    // 3.StartingTime, 
    // 4.FinishingTime, 
    // 5.ArrivalTime, 
    // 6.Color.
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clmID.setCellValueFactory(new PropertyValueFactory<TableRowData,String>("id"));
        clmName.setCellValueFactory(new PropertyValueFactory<TableRowData,String>("Name"));
        txtProcessID.setText((processDetails.size()+1) + "p");
        txtProcessID.setEditable(false);
        txtProcessName.requestFocus();
    }

    public static ArrayList<ArrayList<Object>> show() throws IOException {
        stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(FrmAddProcessController.class.getResource("FrmAddProcess.fxml"))));
        stage.setTitle("Add Process");
        stage.showAndWait();
        
        return processDetails;
    }

    public void btnAdd_Click() {
        ArrayList<Object> arr = new ArrayList<>();
        arr.add(txtProcessID.getText());
        arr.add(txtProcessName.getText());
        arr.add(Integer.parseInt(txtCPUBTime.getText()));
        arr.add(Integer.parseInt(txtStartingDeadline.getText()));
        arr.add(Integer.parseInt(txtFinishingDeadline.getText()));
        arr.add(Integer.parseInt(txtArrivalTime.getText()));
        arr.add(cmbColor.getValue());

        processDetails.add(arr);
        System.out.println(Arrays.toString(arr.toArray()));
        data.add(new TableRowData((String) arr.get(0), (String) arr.get(1),
                ((Color) arr.get(6)).getRed(),
                ((Color) arr.get(6)).getGreen(),
                ((Color) arr.get(6)).getBlue(),
                ((Color) arr.get(6)).getOpacity())
        );
        
        tblProcess.setItems(data);
        txtProcessID.setText((processDetails.size()+1) + "p");
        txtArrivalTime.setText("");
        txtCPUBTime.setText("");
        txtFinishingDeadline.setText("");
        txtProcessName.setText("");
        txtStartingDeadline.setText("");
    }

    public void btnCancle_Click() {
        stage.close();
    }

    public void btnRemove_Click() {
         
    }

    public void btnOK_Click() {
        stage.close();
    }
    
   
}