package sample.data;


import sample.models.User;

public final class Store {

     private User user;
     private final static Store INSTANCE = new Store();

     private Store() {}

     public static Store getInstance() {
         return INSTANCE;
     }

     public void setUser(User user) {
         this.user = user;
     }

     public User getUser() {
         return this.user;
     }

}
