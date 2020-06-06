package sample.MainPage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.DbConnection.DbConnection;
import sample.Login.LoginController;
import sample.Products.ProductsController;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

public class MainPageController {
    @FXML
    private Button click;

    @FXML
    private Label user;

    @FXML
    private ImageView flight;

    @FXML
    private ImageView martin;

    @FXML
    private ImageView camaro;

    @FXML
    private ImageView ferrari;

    @FXML
    private ImageView nissan;

    @FXML
    private ImageView bentley;

    @FXML
    private ImageView bmw;

    @FXML
    private ImageView lambo;

    @FXML
    private ImageView car11;

    @FXML
    private ImageView car15;

    @FXML
    private ImageView car51;

    @FXML
    private ImageView car58;

    @FXML
    private ImageView mucle;

    @FXML
    private ImageView neon;

    @FXML
    private ImageView ford;

    @FXML
    private ImageView rover;

    public int item_id;
    DbConnection connection = new DbConnection();
    private String SQL2 = "SELECT item_id FROM item";
    private String SQL1 = "SELECT * FROM item WHERE item_id = ?";
    private String SQL7 = "SELECT * FROM bid WHERE item_id = ?";
    private static int itemId;
    private static ImageView view;
    private static Image image3;
    private int count;

    public static void setImage3(Image image3) {
        MainPageController.image3 = image3;
    }

    public static Image getImage3() {
        return image3;
    }

    @FXML
    void onPictureClicked(MouseEvent event) throws IOException, SQLException {
        System.out.println("clicked");
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        item_id = getId(event);
        view = getImageView(event);
        setImage3(view.getImage());


        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Products/Products.fxml"));
        Parent root = loader.load();
        ProductsController c = loader.getController();

            try (Connection connect = connection.getConnection();
                PreparedStatement stmt = connect.prepareStatement(SQL1);
                PreparedStatement stmt_count = connect.prepareStatement(SQL7)) {

                stmt_count.setInt(1,item_id);
                stmt.setInt(1, item_id);
                ResultSet rs = stmt.executeQuery();
                ResultSet rs_count = stmt_count.executeQuery();


                while (rs.next()) {
                   String name = rs.getString("name");
                   String availability = rs.getString("availability_status");
                   String description = rs.getString("description");
                   Double starting = rs.getDouble("lowest_bid");
                   Double lowest = rs.getDouble("lowest_bid");

                   //Retrieving the image from the database
                   Blob blob = rs.getBlob("image");
                   InputStream inputStream = blob.getBinaryStream();
                   Image image = new Image(inputStream);

                   c.showInformation(name, availability, description, starting, lowest,image,getCount(item_id));
                   }
                }

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public int getCount(int id) throws SQLException {
        try (Connection connect = connection.getConnection();
             PreparedStatement stmt_count = connect.prepareStatement(SQL7)) {

             stmt_count.setInt(1,id);
             ResultSet rs_count = stmt_count.executeQuery();

             count = 0;
             while(rs_count.next()){
                 count++;
             }
            System.out.println(count);
        }
        return count;
    }


     public  int getId(MouseEvent event) {
        int id = 0;
        if(event.getSource() == flight){
            id =  101;
        }else if(event.getSource() == martin){
            id = 102;
        }else if(event.getSource() == camaro){
            id = 103;
        }else if(event.getSource() == ferrari){
            id = 104;
        }else if(event.getSource() == nissan){
            id = 105;
        }else if(event.getSource() == bentley){
            id = 106;
        }else if(event.getSource() == bmw){
            id = 107;
        }else if(event.getSource() == lambo){
            id = 108;
        }else if(event.getSource() == car11){
            id = 109;
        }else if(event.getSource() == car15){
            id = 110;
        }else if(event.getSource() == car51){
            id = 111;
        }else if(event.getSource() == car58){
            id = 112;
        }else if(event.getSource() == mucle){
            id = 113;
        }else if(event.getSource() == neon){
            id = 114;
        } else if(event.getSource() == ford){
            id = 115;
        }else if(event.getSource() == rover){
            id = 116;
        }

        setItem_id(id);
        return id;
    }

    public int getItem_id() {
        return itemId;
    }

    public void setItem_id(int item_id) {
        this.itemId = item_id;
    }

    public void getUser(String username) {
        user.setText(username);
    }

    public ImageView get_ImageView(){
        return view;
    }

    public void set_Image(Image image1){
        view.setImage(image1);
    }


    public  ImageView getImageView(MouseEvent event) {
        ImageView image = null;
        if(event.getSource() == flight){
            image = flight;
        }else if(event.getSource() == martin){
            image = martin;
        }else if(event.getSource() == camaro){
            image = camaro;
        }else if(event.getSource() == ferrari){
            image = ferrari;
        }else if(event.getSource() == nissan){
            image = nissan;
        }else if(event.getSource() == bentley){
            image = bentley;
        }else if(event.getSource() == bmw){
            image = bmw;
        }else if(event.getSource() == lambo){
            image = lambo;
        }else if(event.getSource() == car11){
            image = car11;
        }else if(event.getSource() == car15){
            image = car15;
        }else if(event.getSource() == car51){
            image = car51;
        }else if(event.getSource() == car58){
            image = car58;
        }else if(event.getSource() == mucle){
            image = mucle;
        }else if(event.getSource() == neon){
            image = neon;
        } else if(event.getSource() == ford){
            image = ford;
        }else if(event.getSource() == rover){
            image = rover;
        }

        //setItem_id(id);
        return image;
    }

    public void click(ActionEvent actionEvent) throws SQLException {
        try(Connection connect = connection.getConnection();
        PreparedStatement stmt = connect.prepareStatement(SQL1)){
            stmt.setInt(1,117);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                Blob blob = rs.getBlob("image");
                InputStream inputStream = blob.getBinaryStream();
                Image image = new Image(inputStream);
                flight.setImage(image);
            }
        }

    }


   /* public ImageView getImageView(String names) {
        ImageView image;
        switch (names) {
            case "flight":
                image = flight;
            case "martin":
                image = martin;
            case "camaro":
                image = camaro;
            case "ferrari":
                image = ferrari;
            case "nissan":
                image = nissan;
            case "bentley":
                image = bentley;
            case "bmw":
                image = bmw;
            case "lambo":
                image = lambo;
            case "car11":
                image =  car11;
            case "car15":
                image = car15;
            case "car51":
                image = car51;
            case "car58":
                image =  car58;
            case "mucle":
                image =  mucle;
            case "neon":
                image = neon;
            case "ford":
                image = ford;
            case "rover":
                image = rover;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + names);
        }
        return image;
    }*/
   public void original(String word,Image flight,Image martin,Image camaro,Image ferrari,Image nissan,
                        Image bentley,Image bmw, Image lambo,Image car11,Image car15,Image car51,
                        Image car58,Image mucle,Image neon,Image ford,Image rover){

       this.user.setText(word); this.flight.setImage(flight); this.martin.setImage(martin);
       this.camaro.setImage(camaro); this.ferrari.setImage(ferrari); this.nissan.setImage(nissan);
       this.bentley.setImage(bentley); this.bmw.setImage(bmw); this.lambo.setImage(lambo);
       this.car11.setImage(car11); this.car15.setImage(car15); this.car51.setImage(car51);
       this.car58.setImage(car58); this.mucle.setImage(mucle); this.neon .setImage(neon);
       this.ford.setImage(ford); this.rover.setImage( rover);

   }

   public void update(ImageView view) throws IOException {
       FXMLLoader loader = new FXMLLoader(getClass().getResource("../Login/Login.fxml"));
       Parent root = loader.load();
       LoginController c = loader.getController();
       user.setText(c.getUsername());
       System.out.println(c.getUsername());

       if(!(view.getImage().equals(getImage3()))){
           System.out.println(getImage3());
           System.out.println(view.getImage().getWidth()+" "+ view.getImage().getHeight()+" "+ view.getImage().getProgress()+" "+ view.getImage());
           //set_Image(view.getImage());

           //this.view.setImage(view.getImage());
           Image image = view.getImage();
           System.out.println(image);
           view.setImage(image);
       }
   }
}

