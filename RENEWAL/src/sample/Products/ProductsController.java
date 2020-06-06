package sample.Products;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;
import sample.DbConnection.DbConnection;
import sample.Login.LoginController;
import sample.MainPage.MainPageController;

import javax.swing.*;
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class ProductsController {

    @FXML
    private Label productName;

    @FXML
    private Rating productRating;

    @FXML
    private Label productAvailabilty;

    @FXML
    private Label productDescription;

    @FXML
    private Label timeLeft;

    @FXML
    private Label bidHistory;

    @FXML
    private Label startingBid;

    @FXML
    private TextField userBid;

    @FXML
    private JFXButton placeBid;

    @FXML
    private Label lowestBid;

    @FXML
    private ImageView productImage;

    @FXML
    private Button details;

    @FXML
    private Button home;

    @FXML
    private Button unBid;

    //Database connection and structured query language statements
    private DbConnection connection = new DbConnection();
    private String SQL = "INSERT INTO bid(user_id,item_id,price,bid_status,rating) VALUES (?,?,?,?,?)";
    private String SQL1 = "SELECT * FROM item WHERE item_id = ?";
    private String SQL3 = "UPDATE item set highest_bid = ? WHERE item_id = ? AND highest_bid < ?";
    private String SQL4 = "SELECT length_of_time FROM item WHERE item_id = ?";
    private String SQL5 = "SELECT * FROM item WHERE availability_status = ? and display_status = ?";
    private String SQL6 = "UPDATE item SET availability_status = ? ,display_status = ? WHERE length_of_time = ?";
    private String SQL7 = "UPDATE item SET length_of_time =  ? WHERE item_id = ? ";
    private String SQL12 = "SELECT * FROM item WHERE item_id > ? AND display_status = ? LIMIT 1";
    private static int bidId;
    private Image image1;
    private ImageView view;

    @FXML
    void takeAction(ActionEvent event) throws IOException, SQLException {
        System.out.println("clicked");
        try {
            //Getting the price from the price textfield
            String p = userBid.getText();
            //converting the string price to double
            double price = Double.parseDouble(p);

            System.out.println(productRating.getRating());

            FXMLLoader loader_item = new FXMLLoader(getClass().getResource("../MainPage/MainPage.fxml"));
            Parent root_item = loader_item.load();
            MainPageController i = loader_item.getController();


            try (Connection connect = connection.getConnection();
                 PreparedStatement stmt_get = connect.prepareStatement(SQL1);
                 PreparedStatement stmt_insert = connect.prepareStatement(SQL);
                 PreparedStatement stmt_update = connect.prepareStatement(SQL3);){

                int itemId = i.getItem_id();
                stmt_get.setInt(1, itemId);
                ResultSet rs = stmt_get.executeQuery();

                if (rs.next()) {
                    //String availability = rs.getString("availability_status");
                    Double starting = rs.getDouble("lowest_bid");
                    Double lowest = rs.getDouble("lowest_bid");

                    if (price < starting) {
                        JOptionPane.showMessageDialog(null, "Less than the starting bid $" + starting + ".");
                    } else {
                        String bidStatus = "Placed";

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Login/Login.fxml"));
                        Parent root = loader.load();
                        LoginController c = loader.getController();

                        //getting the user_id from the Login controller
                        int userId = c.getUserId();

                        //inserting data into the bid table
                        stmt_insert.setInt(1, userId);
                        stmt_insert.setInt(2, itemId);
                        stmt_insert.setDouble(3, price);
                        stmt_insert.setString(4, bidStatus);
                        stmt_insert.setDouble(5,productRating.getRating());

                        boolean result = stmt_insert.execute();
                        JOptionPane.showMessageDialog(null, "Bid Placed");
                        userBid.setText("");

                        //updating data in the item table
                        stmt_update.setDouble(1,price);
                        stmt_update.setInt(2,itemId);
                        stmt_update.setDouble(3,price);

                        int update_results = stmt_update.executeUpdate();
                    }
                }
            }

        }catch (NumberFormatException ex) {
            System.err.println(ex);
            JOptionPane.showMessageDialog(null, "Your input should be a number");
        }
    }









    public void showInformation(String pName,String productAvailability,String pDescription,double sBid,double lBid,Image image,int count){
        productName.setText(pName);
        productAvailabilty.setText(productAvailability);
        productDescription.setText(pDescription);
        startingBid.setText(Double.toString(sBid));
        lowestBid.setText(Double.toString(lBid));
        productImage.setImage(image);
        bidHistory.setText(Integer.toString(count));

    }

    private int time;
    public int getTime() throws IOException, SQLException {
        FXMLLoader loader_item = new FXMLLoader(getClass().getResource("../MainPage/MainPage.fxml"));
        Parent root_item = loader_item.load();
        MainPageController i = loader_item.getController();

        int id  = i.getItem_id();

       try (Connection connect = connection.getConnection();
             PreparedStatement stmt_time = connect.prepareStatement(SQL4);){

             stmt_time.setInt(1,id);
             ResultSet rs = stmt_time.executeQuery();

            if (rs.next()) {
                time = rs.getInt("length_of_time");
            }

        }

        return time;
    }


    Thread timerThread;
    long t = getTime();
    private String availabilty;

    public ProductsController() throws IOException, SQLException {


            //ResultSet rs = stmt_update_avail.executeQuery();


            timerThread = new Thread(() -> {
                SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

                while (t > 0) {
                    try {
                        t--;
                        //c = t;
                        Thread.sleep(1000);
                        Thread.activeCount();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //final String time = timeFormat.format(new Date());
                    //Platform.setImplicitExit(false);
                    Platform.runLater(() -> {
                        timeLeft.setText(Long.toString(t));

                    });
         try (Connection connect = connection.getConnection();
              PreparedStatement stmt_update_avail = connect.prepareStatement(SQL6);
              PreparedStatement stmt_update_zero = connect.prepareStatement(SQL7);
              PreparedStatement stmt_image = connect.prepareStatement(SQL1);
              PreparedStatement stmt_secImage = connect.prepareStatement(SQL12)) {

             FXMLLoader loader_item = new FXMLLoader(getClass().getResource("../MainPage/MainPage.fxml"));
             Parent root_item = loader_item.load();
             MainPageController i = loader_item.getController();

             int itemId  = i.getItem_id();
             //System.out.println(i.get_ImageView());
// private String SQL12 = "SELECT * FROM item WHERE item_id > ? AND display_status = ? LIMIT 1";
                    if (t == 0) {
                        //private String SQL6 = "UPDATE item SET availability_status = ? WHERE length_of_time = 0";
                        System.out.println("Time out");
                        placeBid.setDisable(true);
                        availabilty = "Out of stock";

                        stmt_update_zero.setInt(1,0);
                        stmt_update_zero.setInt(2,itemId);
                        stmt_update_avail.setString(1, availabilty);
                        stmt_update_avail.setInt(3,0);
                        stmt_update_avail.setString(2,"No");
                        //stmt_image.setInt(1,116);
                        stmt_secImage.setInt(1,116);
                        stmt_secImage.setString(2,"No");
                        int rs_zero = stmt_update_zero.executeUpdate();
                        int rs = stmt_update_avail.executeUpdate();
                        ResultSet rs_sec = stmt_secImage.executeQuery();
                        //ResultSet rs_image = stmt_image.executeQuery();

                        ImageView view = i.get_ImageView();
                        //Image im = createNewImage(117);

                        if(rs_sec.next()) {

                                Blob blob = rs_sec.getBlob("image");
                                InputStream inputStream = blob.getBinaryStream();
                                image1 = new Image(inputStream);

                                 view.setImage(image1);
                        }
                    }

         } catch (SQLException | IOException e) {
             e.printStackTrace();
         }
     }

                System.out.println(t);
                System.out.println(timerThread.isAlive());
            });
            timerThread.start();
        }


    /*public void displaytableView(ActionEvent actionEvent) {
    }*/

    @FXML
    public void displaytableView(ActionEvent actionEvent) throws IOException {
        //Stage stage = (Stage) details.getScene().getWindow();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../Details/Details.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void clickedHome(ActionEvent actionEvent) throws IOException {
       /* Stage stage = (Stage) details.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../MainPage/MainPage.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();*/

        Stage stage = (Stage) details.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../MainPage/MainPage.fxml"));
        Parent root = loader.load();
        MainPageController c = loader.getController();

        System.out.println(c.get_ImageView());
        c.update(c.get_ImageView());

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    public void clickedUnBid(ActionEvent actionEvent) throws IOException, SQLException {
        String bid_status = "Cancelled";


        FXMLLoader loader = new FXMLLoader(getClass().getResource("../MainPage/MainPage.fxml"));
        Parent root = loader.load();
        MainPageController c = loader.getController();

        //getting the user_id from the Login controller
        int itemId = c.getItem_id();



        String SQL8 = "UPDATE bid set bid_status = ? WHERE bid_id = (SELECT MAX(bid_id) from bid)";
        String SQL9 = "SELECT price,highest_bid FROM bid,item WHERE bid_id = (SELECT MAX(bid_id) from bid) && item.item_id = ?";
        String SQL10 = "SELECT price FROM bid WHERE bid_status != ? ";
        String SQL11 = "UPDATE item SET highest_bid = ? WHERE item_id = ?";
        String SQL12 = "SELECT MAX(price) AS second FROM bid WHERE price < (SELECT MAX(price) FROM bid)";
        try (Connection connect = connection.getConnection();
             PreparedStatement stmt_cancelled = connect.prepareStatement(SQL8);
             PreparedStatement stmt_reshaf = connect.prepareStatement(SQL9);
             PreparedStatement stmt_placed = connect.prepareStatement(SQL10);
             PreparedStatement stmt_update = connect.prepareStatement(SQL11);
             PreparedStatement stmt_second = connect.prepareStatement(SQL12)){

            stmt_cancelled.setString(1,bid_status);
            int rs_cancel = stmt_cancelled.executeUpdate();

            stmt_reshaf.setInt(1,itemId);
            stmt_placed.setString(1,bid_status);

            ResultSet rs = stmt_reshaf.executeQuery();
            ResultSet rs_placed = stmt_placed.executeQuery();
            ResultSet rs_second = stmt_second.executeQuery();

            double placeholder = 0;
            if(rs.next()){
                double phBid = rs.getDouble("price"); // that particular item having the highest bid
                if(rs_second.next()){
                    placeholder = rs_second.getDouble("second");
                }


                System.out.println(phBid);
                System.out.println(placeholder);
                stmt_update.setDouble(1,placeholder);
                stmt_update.setInt(2,itemId);
                int result_update = stmt_update.executeUpdate();
                JOptionPane.showMessageDialog(null,"Unbid successfully");
            }else{
                System.out.println("nothing");
            }
          }

    }

    public Image createNewImage(int item_id) throws SQLException, IOException {
        String SQL8 = "SELECT * FROM item WHERE item_id = ?";
        Image image1 = null;
        try(Connection con = connection.getConnection();
            PreparedStatement stmt = con.prepareStatement(SQL8)){

            stmt.setInt(1,item_id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                Blob blob = rs.getBlob("image");
                InputStream inputStream = blob.getBinaryStream();
                image1 = new Image(inputStream);
                System.out.println(image1.getUrl());

                /*String name = rs.getString("name");
                String availability = rs.getString("availability_status");
                String description = rs.getString("description");
                Double starting = rs.getDouble("lowest_bid");
                Double lowest = rs.getDouble("lowest_bid");

                //Retrieving the image from the database
                Blob blob = rs.getBlob("image");
                InputStream inputStream = blob.getBinaryStream();
                Image image1 = new Image(inputStream);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("../Products/Products.fxml"));
                Parent root = loader.load();
                ProductsController c = loader.getController();

                MainPageController a = new MainPageController();
                c.showInformation(name, availability, description, starting, lowest,image1,a.getCount(item_id));*/
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../MainPage/MainPage.fxml"));
            Parent root = loader.load();
            MainPageController c = loader.getController();

            c.setItem_id(item_id);
            return image1;
        }

    }

    public int getBidId(){
        return bidId;
    }

    public void setBidId(int id){
        bidId = id;
    }
}
