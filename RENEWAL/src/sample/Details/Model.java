package sample.Details;

public class Model {

    /*private final StringProperty user = new SimpleStringProperty();
    private final IntegerProperty bidId = new SimpleIntegerProperty();
    private final IntegerProperty itemId = new SimpleIntegerProperty();
    private final DoubleProperty rating = new SimpleDoubleProperty();
    private final DoubleProperty currentHighestBid = new SimpleDoubleProperty();
    private final DoubleProperty userBid = new SimpleDoubleProperty();

    /*public Model(String user,int bidId, int itemId, double rating, double currentHighestBid,double userBid){
        this.user = user;
        this.bidId = bidId;
        this.itemId = itemId;
        this.userBid = userBid;
        this.rating = rating;
        this.currentHighestBid = currentHighestBid;
    }*/

    /*public int getBidId() {
        return bidId.get();
    }

    public void setBidId(int bidId) {
        this.bidId.set(bidId);
    }

    public int getItemId() {
        return itemId.get();
    }

    public void setItemId(int itemId) {
        this.itemId.set(itemId);
    }

    public String getUser() {
        return user.get();
    }

    public void setUser(String user) {
        this.user.set(user);
    }

    public double getUserBid() {
        return userBid.get();
    }

    public void setUserBid(double userBid) {
        this.userBid.set(userBid);
    }

    public double getRating() {
        return rating.get();
    }

    public void setRating(double rating) {
        this.rating.set(rating);
    }

    public double getCurrentHighestBid() {
        return currentHighestBid.get();
    }

    public void setCurrentHighestBid(double currentHighestBid) {
        this.currentHighestBid.set(currentHighestBid);
    }*/


             String user;
             int bidId;
             int itemId;
             double rating;
             double currentHighestBid;
             double userBid ;

             public Model( String user,int bidId,int itemId,double rating,double currentHighestBid,double userBid){
                 this.user = user;
                 this.bidId = bidId;
                 this.itemId = itemId;
                 this.rating = rating;
                 this.currentHighestBid = currentHighestBid;
                 this.userBid = userBid ;

             }

            public String getUser() {
                return user;
            }

            public void setUser(String user) {
                this.user = user;
            }

            public int getBidId() {
                return bidId;
            }

            public void setBidId(int bidId) {
                this.bidId = bidId;
            }

            public int getItemId() {
                return itemId;
            }

            public void setItemId(int itemId) {
                this.itemId = itemId;
            }

            public double getRating() {
                return rating;
            }

            public void setRating(double rating) {
                this.rating = rating;
            }

            public double getCurrentHighestBid() {
                return currentHighestBid;
            }

            public void setCurrentHighestBid(double currentHighestBid) {
                this.currentHighestBid = currentHighestBid;
            }

            public double getUserBid() {
                return userBid;
            }

            public void setUserBid(double userBid) {
                this.userBid = userBid;
            }
}
