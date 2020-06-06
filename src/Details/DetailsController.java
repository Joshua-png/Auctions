package Details;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.DbConnection.DbConnection;
import sample.Details.Model;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DetailsController implements Initializable {
    @FXML
    private TableView<Model> table;

    @FXML
    private TableColumn<Model, String> col_user;

    @FXML
    private TableColumn<Model, Integer> col_bid_id;

    @FXML
    private TableColumn<Model, Integer> col_item_id;

    @FXML
    private TableColumn<Model, Double> col_user_bid;

    @FXML
    private TableColumn<Model, Double> col_rating;

    @FXML
    private TableColumn<Model, Double> col_current_highest;

    private DbConnection connect;
    private Connection connection;
    private ObservableList<Model> List = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connect = new DbConnection();
        col_user.setCellValueFactory(new PropertyValueFactory<Model,String>("user"));
        col_bid_id.setCellValueFactory(new PropertyValueFactory<>("bidId"));
        col_item_id.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        col_rating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        col_user_bid.setCellValueFactory(new PropertyValueFactory<>("userBid"));
        col_current_highest.setCellValueFactory(new PropertyValueFactory<>("currentHighestBid"));

        String SQL = "SELECT * FROM bid,item,user WHERE bid.user_id = user.user_id AND bid.item_id = item.item_id";

        try {
            connection = connect.getConnection();
            PreparedStatement stmt = connection.prepareStatement(SQL);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String username = rs.getString("username");
                int bidId = rs.getInt("bid_id");
                int itemId = rs.getInt("item_id");
                double rating = rs.getDouble("rating");
                double currentHighestBid = rs.getDouble("highest_bid");
                double userBid = rs.getDouble("price");

                List.add(new Model(username,bidId,itemId,rating,currentHighestBid,userBid));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        table.setItems(List);


    }

   /* public void populateTableView() throws SQLException {
        List = FXCollections.observableArrayList();
        String SQL = "SELECT * FROM bid,item,user WHERE bid.user_id = user.user_id AND bid.item_id = item.item_id";

        connection = connect.getConnection();
        PreparedStatement stmt = connection.prepareStatement(SQL);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()){
            String username = rs.getString("username");
            int bidId = rs.getInt("bid_id");
            int itemId = rs.getInt("item_id");
            double rating = rs.getDouble("rating");
            double currentHighestBid = rs.getDouble("highest_bid");
            double userBid = rs.getDouble("price");

            //List.add(new Model(username,bidId,itemId,rating,currentHighestBid,userBid));

            Model one = new Model();
            one.setUser(username);
            one.setBidId(bidId);
            one.setItemId(itemId);
            one.setRating(rating);
            one.setCurrentHighestBid(currentHighestBid);
            one.setUserBid(userBid);
            List.add(one);

        }
        col_user.setCellValueFactory(new PropertyValueFactory<>("user"));
        col_bid_id.setCellValueFactory(new PropertyValueFactory<>("bidId"));
        col_item_id.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        col_rating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        col_user_bid.setCellValueFactory(new PropertyValueFactory<>("userBid"));
        col_current_highest.setCellValueFactory(new PropertyValueFactory<>("currentHighestBid"));

        Callback<TableColumn<Model,String>, TableCell<Model,String>> cellFactory = (params)->{
            final TableCell<Model,String> cell=new TableCell<Model,String>()(

                    );

            return null;
        };
    }*/
}
