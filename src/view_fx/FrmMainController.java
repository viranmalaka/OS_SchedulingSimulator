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
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.Simulator;

/**
 * FXML Controller class
 *
 * @author malaka
 */
public class FrmMainController extends Application implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Pane lblPane;
    @FXML
    private TextField txtSDP;
    @FXML
    private TextField txtFDP;
    @FXML
    private TextField txtTQ;
    @FXML
    private Line lineX;

    private ArrayList<ArrayList<Object>> processData;
    private Map<String, Color> processColors;
    private Map<String, String> processNames;
    private Simulator simulator;
    private ArrayList<processDetails> gChart = new ArrayList<>();

    int currentID;
    int currentX = 28;
    int currentTime = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        processColors = new HashMap<>();
        processNames = new HashMap<>();
    }

    @FXML
    public void showFrmAddProcess() throws IOException {
        // processData = FrmAddProcessController.show();
        processData = new ArrayList<>();
        //[1p, pro1, 20, 0, 40, 0, 0xff0000ff], [2p, pro2, 10, 0, 10, 1, 0x00ff00ff], 
        //[3p, pro3, 5, 0, 10, 3, 0x0004ffff]]
        ArrayList a = new ArrayList();
        a.add("1p");
        a.add("pro1");
        a.add(20);
        a.add(0);
        a.add(40);
        a.add(0);
        a.add(Color.RED);
        ArrayList b = new ArrayList();
        b.add("2p");
        b.add("pro2");
        b.add(10);
        b.add(0);
        b.add(10);
        b.add(1);
        b.add(Color.BLUE);
        ArrayList c = new ArrayList();
        c.add("3p");
        c.add("pro3");
        c.add(5);
        c.add(0);
        c.add(10);
        c.add(3);
        c.add(Color.GREEN);
        processData.add(a);
        processData.add(b);
        processData.add(c);

        System.out.println(Arrays.toString(processData.toArray()));
    }

    public void show() {
        Stage stage = new Stage();
        stage.setTitle("Process Simulator");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FrmMain.fxml"));
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FrmMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void btnRun_Click() {
        int sdp = Integer.parseInt(txtSDP.getText());
        int fdp = Integer.parseInt(txtFDP.getText());
        int tq = Integer.parseInt(txtTQ.getText());

        simulator = new Simulator(fdp, sdp, tq);

        for (ArrayList<Object> process : processData) {
            simulator.addProcess((String) process.get(1), (int) process.get(3), (int) process.get(4), (int) process.get(2), (int) process.get(5));
            processColors.put((String) process.get(0), (Color) process.get(6));
            processNames.put((String) process.get(0), (String) process.get(1));
        }
        while (simulator.executeNextProcess()) {
            String id = simulator.getCurrentlyExecutingPID();
            int ld = simulator.getActiveProcessList().get(0).getLastExecutedDuration();
            gChart.add(new processDetails(id, ld));
            System.out.println(id + " " + ld);
        }
    }

    public void btnForward_Click() {
        if (gChart.size() > currentID) {
            processDetails get = gChart.get(currentID);

            
            //add the Rectangle
            final Rectangle r = new Rectangle(currentX, 25, get.getDuration() * 19, 40);
            Color c = processColors.get(get.getPid());
            String cs = "rgb(" + c.getRed() * 255 + "," + c.getGreen() * 255 + "," + c.getBlue() * 255 + ");";
            r.setStyle("-fx-fill:" + cs);
            r.setArcHeight(10);
            r.setArcWidth(10);
            lblPane.getChildren().add(r);
            
            
            currentID++;
            //set the Pane with and increase x-axis length
            currentX += get.getDuration() * 20;
            
            currentTime += get.getDuration();
            //add the lable to x axis
            final Label l = new Label(currentTime+"");
            l.setTextAlignment(TextAlignment.RIGHT);
            l.setRotate(-90);
            l.setLayoutY(70);
            l.setLayoutX(currentX - 5);
            lblPane.getChildren().add(l);
            
            if (currentX > 350) {
                lblPane.setPrefWidth(currentX + 20);
            }
            if (currentX > 260) {
                lineX.setEndX(lineX.getEndX() + get.getDuration()*20);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        show();
    }
}

class processDetails {

    private String pid;
    private int duration;

    public processDetails(String pid, int duration) {
        this.pid = pid;
        this.duration = duration;
    }

    /**
     * @return the pid
     */
    public String getPid() {
        return pid;
    }

    /**
     * @param pid the pid to set
     */
    public void setPid(String pid) {
        this.pid = pid;
    }

    /**
     * @return the duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }
}
