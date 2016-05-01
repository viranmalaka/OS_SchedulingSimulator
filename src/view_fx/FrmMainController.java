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
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
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
    @FXML
    private TableView<TableProcessDetails> tblProcess;
    @FXML
    private TableColumn clmID;
    @FXML
    private TableColumn clmName;
    @FXML
    private TableColumn clmBurst;
    @FXML
    private TableColumn clmSD;
    @FXML
    private TableColumn clmFD;
    @FXML 
    private TableColumn clmAT;

    private ArrayList<ArrayList<Object>> processData;
    private ObservableList<TableProcessDetails> tableData = FXCollections.observableArrayList();
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
        clmID.setCellFactory(new PropertyValueFactory<TableProcessDetails,String>("id"));
        clmName.setCellFactory(new PropertyValueFactory<TableProcessDetails,String>("name"));
        clmAT.setCellFactory(new PropertyValueFactory<TableProcessDetails,String>("at"));
//        clmBurst.setCellFactory(new PropertyValueFactory<TableProcessDetails,String>("bt"));
        clmSD.setCellFactory(new PropertyValueFactory<TableProcessDetails,String>("sd"));
        clmFD.setCellFactory(new PropertyValueFactory<TableProcessDetails,String>("fd"));
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

        tblProcess = new TableView();

        processData.add(a);
        processData.add(b);
        processData.add(c);
        tableData.add(new TableProcessDetails((String)a.get(0), (String)a.get(1), (int)a.get(2), (int)a.get(3), (int)a.get(4), (int)a.get(5)));
        tableData.add(new TableProcessDetails((String)b.get(0), (String)b.get(1), (int)b.get(2), (int)b.get(3), (int)b.get(4), (int)b.get(5)));
        tableData.add(new TableProcessDetails((String)c.get(0), (String)c.get(1), (int)c.get(2), (int)c.get(3), (int)c.get(4), (int)c.get(5)));
        
        tblProcess.setItems(tableData);
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
            int remain = getProcess(id).getRemainingTime();
            int finished = getProcess(id).getExecutingTime() - remain;
            gChart.add(new processDetails(id, ld,remain,finished));
            System.out.println(id + " " + ld);
        }
    }

    public void btnForward_Click() {
        if (gChart.size() > currentID) {
            processDetails get = gChart.get(currentID);

            //add the Rectangle
            final Rectangle r = new Rectangle(currentX, 25, get.getDuration() * 20 - 1, 40);
            Color c = processColors.get(get.getPid());
            String cs1 = "rgb(" + c.getRed() * 255 + "," + c.getGreen() * 255 + "," + c.getBlue() * 255 + ")";
            String cs2 = "rgb(" + c.getRed() * 100 + "," + c.getGreen() * 100 + "," + c.getBlue() * 100 + ")";
            r.setStyle("-fx-fill:linear-gradient(" + cs1 + ", " + cs2 + ");");
            r.setArcHeight(10);
            r.setArcWidth(10);

            Tooltip t = new Tooltip("Process ID : " + get.getPid()
                    + "\nProcess Name : " + processNames.get(get.getPid())
                    + "\nDuration : " + get.getDuration()
                    + "\nRemainig : " + get.getRemain()
                    + "\nFinished : " + get.getFinished());
            
                    
            Tooltip.install(r, t);

            lblPane.getChildren().add(r);

            currentID++;
            //set the Pane with and increase x-axis length
            currentX += get.getDuration() * 20;

            currentTime += get.getDuration();
            //add the lable to x axis
            final Label l = new Label(currentTime + "-");
            l.setTextAlignment(TextAlignment.RIGHT);
            l.setRotate(-90);
            l.setLayoutY(70);
            l.setLayoutX(currentX - (currentTime > 9 ? 12 : 8));
            lblPane.getChildren().add(l);

            if (currentX > 350) {
                lblPane.setPrefWidth(currentX + 20);
            }
            if (currentX > 260) {
                lineX.setEndX(lineX.getEndX() + get.getDuration() * 20);
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
    
    private model.Process getProcess(String id){
        for (model.Process process : simulator.getProcessList()) {
            if (process.getProcessId().equals(id)) {
                return process;
            }
        }
        return null;
    }
    
     public static class TableProcessDetails{
        private SimpleStringProperty id;
        private SimpleStringProperty name;
        private SimpleIntegerProperty bt;
        private SimpleIntegerProperty sd;
        private SimpleIntegerProperty fd;
        private SimpleIntegerProperty at;

        public TableProcessDetails(String id, String name, int bt, int sd, int fd, int at) {
            this.id = new SimpleStringProperty(id);
            this.name = new SimpleStringProperty(name);
            this.bt = new SimpleIntegerProperty(bt);
            this.sd = new SimpleIntegerProperty(sd);
            this.fd = new SimpleIntegerProperty(fd);
            this.at = new SimpleIntegerProperty(at);
        }

        /**
         * @return the id
         */
        public String getId() {
            return id.get();
        }

        /**
         * @param id the id to set
         */
        public void setId(String id) {
            this.id = new SimpleStringProperty(id);
        }

        /**
         * @return the name
         */
        public String getName() {
            return name.get();
        }

        /**
         * @param name the name to set
         */
        public void setName(String name) {
            this.name =new SimpleStringProperty (name);
        }

        /**
         * @return the bt
         */
        public int getBt() {
            return bt.get();
        }

        /**
         * @param bt the bt to set
         */
        public void setBt(int bt) {
            this.bt = new SimpleIntegerProperty(bt);
        }

        /**
         * @return the sd
         */
        public int getSd() {
            return sd.get();
        }

        /**
         * @param sd the sd to set
         */
        public void setSd(int sd) {
            this.sd = new SimpleIntegerProperty(sd);
        }

        /**
         * @return the fd
         */
        public int getFd() {
            return fd.get();
        }

        /**
         * @param fd the fd to set
         */
        public void setFd(int fd) {
            this.fd = new SimpleIntegerProperty(fd);
        }

        /**
         * @return the at
         */
        public int getAt() {
            return at.get();
        }

        /**
         * @param at the at to set
         */
        public void setAt(int at) {
            this.at = new SimpleIntegerProperty(at);
        }
        
        
    }
}

//<editor-fold defaultstate="collapsed" desc="class to save the temp process data for simulation data">
class processDetails {
    
    private String pid;
    private int duration;
    private int remain;
    private int finished;
    
    public processDetails(String pid, int duration,int remain, int finished) {
        this.pid = pid;
        this.duration = duration;
        this.remain = remain;
        this.finished = finished;
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

    public int getFinished() {
        return finished;
    }

    public int getRemain() {
        return remain;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    public void setRemain(int remain) {
        this.remain = remain;
    }
    
    
}
//</editor-fold>
