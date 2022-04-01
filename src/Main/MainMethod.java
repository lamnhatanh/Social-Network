package Main;

import javax.swing.DefaultListModel;

public class MainMethod {

    public static void main(String[] args) {
        //Create name for Profile
        Profile aaron = new Profile("Aaron");
        Profile bella = new Profile("Bella");
        Profile edward = new Profile("Edward");
        Profile jacob = new Profile("Jacob");
        
        //Create network
        Connection network = new Connection();
        network.join(aaron);
        network.join(bella);
        network.join(edward);
        network.join(jacob);

        //Add connection between friends
        //I made their connection between each other like a square
        network.addFriendship(jacob, bella);
        network.addFriendship(bella, edward);
        network.addFriendship(edward, aaron);
        network.addFriendship(aaron, jacob);
        
        //Create a login window with the network as a constructor
        TheSocialNetwork login = new TheSocialNetwork(network);
        login.setVisible(true);
        

    }
}
