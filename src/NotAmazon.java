import javafx.application.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.*;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.*;
import javafx.scene.layout.GridPane;
import javafx.scene.*;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.paint.*;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.effect.DropShadow;
import java.io.File;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NotAmazon extends Application{
    
    private Stage window;
    private GUMainPage guMainScene;
    private LoginPage loginScene;
    private SignupPage signupScene;
    private OUMainPage ouMainScene;
    private TransactionPage transScene;
    private MyProfilePage myProfileScene;
    private SUMainPage suMainScene;
    private PendAppPage pendAppScene;
    private PendItemPage pendItemScene;
    private ReportAppPage reportAppScene;
    private ReportPage pendReportScene;
    private BlackListPage bListScene;
    private SellItemAppPage sellItemAppScene;
    private ViewItemPage viewItemScene;
    private RateUserPage rateUserScene;
    private MyAccountPage myAccountScene;
    private EditAddressPage editAddrScene;
    private EditNamePage editNameScene;
    private EditPasswordPage editPWScene;
    private EditPhoneNum editPhoneScene;
    private EditCCNum editCCScene;
    
    private String thisUser;
    private String thisAdmin;
    private String thisItem;
    private String currentApp;
    private String currentReport;
    
    public static void main(String[]args){
        launch(args);
    }
    
    @Override
    public void start(Stage stage){
        window = stage;
        
        initialize();
        window.setScene(guMainScene);
        window.show();
    }
    
    public void initialize(){
        //sets up values for variables
        thisUser = "";
        thisAdmin = "";
        guMainScene = new GUMainPage();
        loginScene = new LoginPage();
        signupScene = new SignupPage();
        ouMainScene = new OUMainPage();
        transScene = new TransactionPage();
        myProfileScene = new MyProfilePage();
        suMainScene = new SUMainPage();
        pendAppScene = new PendAppPage();
        pendItemScene = new PendItemPage();
        reportAppScene = new ReportAppPage();
        pendReportScene = new ReportPage();
        bListScene = new BlackListPage();
        viewItemScene = new ViewItemPage();
        rateUserScene = new RateUserPage();
        myAccountScene = new MyAccountPage();
        editAddrScene = new EditAddressPage();
        editNameScene = new EditNamePage();
        editPWScene = new EditPasswordPage();
        editPhoneScene = new EditPhoneNum();
        editCCScene = new EditCCNum();
    }
    
    @Override
    public void stop() {
        DataManager.shutdown();
    }
    
    class GUMainPage extends Scene{
        GridPane layout;
        Text sceneTitle;
        Text popItemTitle;
        TextField searchBar;
        Button searchBtn;
        Button loginBtn;
        Button signUpBtn;
        Image temp1;
        Image temp2;
        Image temp3;
        Image temp4;
        ImageView temp1View;
        ImageView temp2View;
        ImageView temp3View;
        ImageView temp4View;
        ObservableList searchResultList;
        ListView searchResultListView;
        
        
        public GUMainPage() {
            super(new GridPane(),1000,800);
            
            layout = (GridPane)this.getRoot();
            window.setTitle("Not Amazon");
            sceneTitle = new Text("<banner>This is the main page of Not Amazon<banner>");
            popItemTitle = new Text("Popular");
            searchBar = new TextField();
            
            popItemTitle.setFont(Font.font("Segoe UI Bold",25));
            
            searchBtn = new Button("Search");
            loginBtn = new Button("Login");
            signUpBtn = new Button("Sign Up");
            
            searchBtn.setOnAction(event -> {
                if(DataManager.checkValidBListWord(searchBar.getText()) && !searchBar.getText().contentEquals("")) {
                    searchResultList = FXCollections.observableArrayList(DataManager.getListofSearchItems(searchBar.getText()));
                    searchResultListView = new ListView<>(searchResultList);
                    
                    GridPane ouSearchResults = new GridPane();
                    Scene ouSearchResultScene = new Scene(ouSearchResults, 700, 700);
                    
                    sceneTitle = new Text("<banner>NotAmazon logo<banner>");
                    
                    sceneTitle.setOnMouseClicked(new EventHandler<MouseEvent>(){
                        @Override
                        public void handle(MouseEvent click) {
                            if (click.getClickCount()==2) {
                                guMainScene = new GUMainPage();
                                window.setScene(guMainScene);
                            }
                        }
                    });
                    
                    searchResultListView.setOnMouseClicked(new EventHandler<MouseEvent>(){
                        @Override
                        public void handle(MouseEvent click) {
                            if (click.getClickCount()==2) {
                                thisItem = searchResultListView.getSelectionModel().getSelectedItem().toString();
                                viewItemScene = new ViewItemPage();
                                window.setScene(viewItemScene);
                            }
                        }
                    });
                    
                    Button backBtn = new Button("Back");
                    
                    backBtn.setOnAction(e -> {
                        guMainScene = new GUMainPage();
                        window.setScene(guMainScene);
                    });
                    
                    searchResultListView.setPrefWidth(600);
                    searchResultListView.setPrefHeight(600);
                    searchResultListView.setOrientation(Orientation.VERTICAL);
                    ouSearchResults.setAlignment(Pos.BASELINE_CENTER);
                    
                    // New window (Stage)
                    Stage resultsWindow = new Stage();
                    resultsWindow.setScene(ouSearchResultScene);
                    
                    ouSearchResults.setHgap(10);
                    ouSearchResults.setVgap(10);
                    ouSearchResults.setPadding(new Insets(25, 25, 25, 25));
                    ouSearchResults.add(searchResultListView, 0, 1, 2, 1);
                    ouSearchResults.add(sceneTitle,0,0,2,1);
                    ouSearchResults.add(backBtn,0,2);
                    
                    resultsWindow.show();
                }
                else {
                    Alert fail = new Alert(AlertType.ERROR);
                    fail.setTitle("Error");
                    fail.setHeaderText(null);
                    fail.setContentText("Invalid input. Please try again.");
                    fail.showAndWait();
                }
            });
            
            loginBtn.setOnAction(e -> {
                loginScene = new LoginPage();
                window.setScene(loginScene);
            });
            
            signUpBtn.setOnAction(e -> {
                signupScene = new SignupPage();
                window.setScene(signupScene);
            });
            
            layout.setAlignment(Pos.BASELINE_CENTER);
            layout.setHgap(10);
            layout.setVgap(10);
            layout.setPadding(new Insets(25, 25, 25, 25));
            //placing objects into scene
            layout.add(sceneTitle, 0, 0, 2, 1);
            layout.add(searchBar, 0, 1, 2, 1);
            layout.add(searchBtn, 2, 1, 2, 1);
            layout.add(loginBtn,4,1,2,1);
            layout.add(signUpBtn,6,1,2,1);
            layout.add(popItemTitle, 0, 3, 2, 1);
        }
    }
    
    class LoginPage extends Scene{
        GridPane layout;
        Label login;
        Label user;
        Label pass;
        TextField usr_TextField;
        PasswordField pass_TextField;
        Button loginBtn;
        Button cancelBtn;
        HBox aBtn;
        HBox cBtn;
        
        private boolean validateFields(){
            if(usr_TextField.getText().isEmpty() | pass_TextField.getText().isEmpty()){
                
                Alert warnUsr = new Alert(AlertType.WARNING);
                warnUsr.setTitle("Invalid Credentials");
                warnUsr.setHeaderText(null);
                warnUsr.setContentText("An empty field has been detected. Please try again.");
                warnUsr.showAndWait();
                
                return false;
            }
            return true;
        }
        
        public LoginPage() {
            super(new GridPane(),300,220);
            layout = (GridPane)this.getRoot();
            
            login = new Label("Login");
            user = new Label("Username:");
            pass = new Label("Password:");
            
            usr_TextField = new TextField();
            pass_TextField = new PasswordField();
            
            loginBtn = new Button("Login");
            cancelBtn = new Button("Cancel");
            aBtn = new HBox(5);
            cBtn = new HBox(5);
            
            login.setFont(Font.font("Segoe UI Bold",25));
            
            aBtn.setAlignment(Pos.BOTTOM_RIGHT);
            aBtn.getChildren().add(loginBtn);
            
            loginBtn.setOnAction(event ->{
                if(validateFields()) {
                    String tempUsername = usr_TextField.getText();
                    String tempPassword = pass_TextField.getText();
                    if (DataManager.isValidAdmin(tempUsername, tempPassword)) {
                        thisAdmin = tempUsername;
                        usr_TextField.setText("");
                        pass_TextField.setText("");
                        suMainScene = new SUMainPage();
                        window.setScene(suMainScene);
                    }
                    else if (DataManager.isValidUser(tempUsername, tempPassword)) {
                        thisUser = tempUsername;
                        usr_TextField.setText("");
                        pass_TextField.setText("");
                        ouMainScene = new OUMainPage();
                        window.setScene(ouMainScene);
                    }else {
                        pass_TextField.setText("");
                        Alert alert = new Alert(AlertType.WARNING);
                        alert.setTitle("Invalid Credentials");
                        alert.setHeaderText(null);
                        alert.setContentText("Invalid username and/or password. Please try again.");
                        alert.showAndWait();
                    }
                }
            });
            
            cBtn.setAlignment(Pos.BOTTOM_LEFT);
            cBtn.getChildren().add(cancelBtn);
            cancelBtn.setOnAction(e -> window.setScene(guMainScene));
            
            
            // action event
            EventHandler<ActionEvent> pressEnter = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    if (validateFields()) {
                        String tempUsername = usr_TextField.getText();
                        String tempPassword = pass_TextField.getText();
                        if (DataManager.isValidAdmin(tempUsername, tempPassword)) {
                            thisAdmin = tempUsername;
                            usr_TextField.setText("");
                            pass_TextField.setText("");
                            suMainScene = new SUMainPage();
                            window.setScene(suMainScene);
                        }
                        else if (DataManager.isValidUser(tempUsername, tempPassword)) {
                            thisUser = tempUsername;
                            usr_TextField.setText("");
                            pass_TextField.setText("");
                            //suMainScene = new SUMainPage();
                            //REMOVED, NOT INITIALIZING CORRECT SCENE
                            ouMainScene = new OUMainPage();
                            window.setScene(ouMainScene);
                        }else {
                            pass_TextField.setText("");
                            Alert alert = new Alert(AlertType.WARNING);
                            alert.setTitle("Invalid Credentials");
                            alert.setHeaderText(null);
                            alert.setContentText("Invalid username and/or password. Please try again.");
                            alert.showAndWait();
                        }
                    }
                }
            };
            
            // when enter is pressed
            pass_TextField.setOnAction(pressEnter);
            
            
            cancelBtn.setOnAction(e -> window.setScene(guMainScene));
            
            layout.setAlignment(Pos.CENTER);
            layout.setHgap(10);
            layout.setVgap(10);
            layout.setPadding(new Insets(25, 25, 25, 25));
            
            //placing objects into scene
            layout.add(login, 0, 0, 2, 1);
            layout.add(user, 0, 1);
            layout.add(pass, 0, 2);
            
            layout.add(usr_TextField, 1, 1);
            layout.add(pass_TextField, 1, 2);
            layout.add(aBtn, 1, 3);
            layout.add(cBtn, 0, 3);
            
        }
    }
    
    class SignupPage extends Scene{
        GridPane layout;
        Label signup;
        Label user;
        Label first_name;
        Label last_name;
        Label address;
        Label phonenum;
        Label ccnum;
        TextField usr_TextField;
        TextField first_TextField;
        TextField last_TextField;
        TextField addr_TextField;
        TextField phone_TextField;
        TextField cc_TextField;
        Button applyBtn;
        Button cancelBtn;
        HBox aBtn;
        HBox cBtn;
        Tooltip t1,t2,t3;
        
        private boolean validateFields(){
            if(usr_TextField.getText().isEmpty() | first_TextField.getText().isEmpty() | last_TextField.getText().isEmpty() |
               addr_TextField.getText().isEmpty() | phone_TextField.getText().isEmpty() | cc_TextField.getText().isEmpty()){
                
                Alert warnUsr = new Alert(AlertType.WARNING);
                warnUsr.setTitle("Warning");
                warnUsr.setHeaderText("An empty field has been detected.");
                warnUsr.setContentText("Please complete the application and submit again.");
                warnUsr.showAndWait();
                
                return false;
            }
            return true;
        }
        
        public SignupPage() {
            super(new GridPane(),400,400);
            layout = (GridPane)this.getRoot();
            
            signup = new Label("Sign Up");
            user = new Label("Username:");
            first_name = new Label("First Name:");
            last_name = new Label("Last Name:");
            address = new Label("Address:");
            phonenum = new Label("Phone Number:");
            ccnum = new Label("Credit Card Number:");
            
            usr_TextField = new TextField();
            first_TextField = new TextField();
            last_TextField = new TextField();
            addr_TextField = new TextField();
            phone_TextField = new TextField();
            cc_TextField = new TextField();
            
            t1 = new Tooltip("Exclude country code and dashes.");
            t2 = new Tooltip("Input your 16-digit card while excluding space.");
            
            phone_TextField.setTooltip(t1);
            cc_TextField.setTooltip(t2);
            
            t1.setFont(Font.font("Segoe UI",12));
            t2.setFont(Font.font("Segoe UI",12));
            
            applyBtn = new Button("Submit");
            cancelBtn = new Button("Cancel");
            aBtn = new HBox(5);
            cBtn = new HBox(5);
            
            
            
            applyBtn.setOnAction(e -> {
                if(validateFields()){
                    String tempUserName = usr_TextField.getText();
                    String tempFName = first_TextField.getText();
                    String tempLName = last_TextField.getText();
                    String tempAddress = addr_TextField.getText();
                    String tempPhone = phone_TextField.getText();
                    String tempCard = cc_TextField.getText();
                    if(!tempUserName.equals("") && !DataManager.isValidUsername(tempUserName)){
                        DataManager.createNewUser(tempUserName, tempFName, tempLName, tempAddress,
                                                  tempPhone, tempCard);
                        usr_TextField.setText("");
                        first_TextField.setText("");
                        last_TextField.setText("");
                        addr_TextField.setText("");
                        cc_TextField.setText("");
                        window.setScene(loginScene);
                    }
                }
            });
            
            cancelBtn.setOnAction(e -> {
                usr_TextField.setText("");
                first_TextField.setText("");
                last_TextField.setText("");
                addr_TextField.setText("");
                phone_TextField.setText("");
                cc_TextField.setText("");
                window.setScene(guMainScene);
            });
            
            signup.setFont(Font.font("Segoe UI Bold",25));
            
            aBtn.setAlignment(Pos.BOTTOM_RIGHT);
            aBtn.getChildren().add(applyBtn);
            
            cBtn.setAlignment(Pos.BOTTOM_LEFT);
            cBtn.getChildren().add(cancelBtn);
            cancelBtn.setOnAction(e -> window.setScene(guMainScene));
            
            layout.setAlignment(Pos.CENTER);
            layout.setHgap(10);
            layout.setVgap(8);
            layout.setPadding(new Insets(25, 25, 25, 25));
            
            //placing objects into scene
            layout.add(signup, 0, 0, 2, 1);
            layout.add(user, 0, 1);
            layout.add(first_name, 0, 2);
            layout.add(last_name, 0, 3);
            layout.add(address, 0, 4);
            layout.add(phonenum, 0, 5);
            layout.add(ccnum, 0, 6);
            
            layout.add(usr_TextField, 1, 1);
            layout.add(first_TextField, 1, 2);
            layout.add(last_TextField, 1, 3);
            layout.add(addr_TextField, 1, 4);
            layout.add(phone_TextField, 1, 5);
            layout.add(cc_TextField, 1, 6);
            layout.add(aBtn, 1, 7);
            layout.add(cBtn, 0, 7);
            
        }
    }
    
    class OUMainPage extends Scene{
        GridPane layout;
        Text sceneTitle;
        Text recItemTitle;
        Text popItemTitle;
        Text sellTitle;
        Text bidTitle;
        Text friendTitle;
        Text addFriendTitle;
        Text friendName;
        MenuButton menu;
        MenuItem profile;
        MenuItem myAcc;
        MenuItem myTranHist;
        MenuItem signOut;
        TextField searchBar;
        TextField friendTextField;
        Button searchBtn;
        Button sellBtn;
        Button friendBtn;
        Button addFriendBtn;
        ObservableList<String> sellingList;
        ListView<String> sellListView;
        ObservableList<String> biddingList;
        ListView<String> bidListView;
        ObservableList<String> friendList;
        ListView<String> friendListView;
        ObservableList<String> searchResultList;
        ListView<String> searchResultListView;
        String friend;
        
        public OUMainPage() {
            super(new GridPane(),900,800);
            layout = (GridPane)this.getRoot();
            window.setTitle("Not Amazon");
            
            sellingList = FXCollections.observableArrayList(DataManager.getListOfItems(thisUser));
            sellListView = new ListView<>(sellingList);
            biddingList = FXCollections.observableArrayList(DataManager.getListOfBids(thisUser,thisItem));
            bidListView = new ListView<>(biddingList);
            friendList = FXCollections.observableArrayList(DataManager.getListOfFriends(thisUser));
            friendListView = new ListView<>(friendList);
            
            sceneTitle = new Text("<banner>This is the main page of Not Amazon<banner>");
            recItemTitle = new Text("Recommended");
            popItemTitle = new Text("Popular");
            sellTitle = new Text("Sell");
            bidTitle = new Text("Bid");
            friendTitle = new Text("Friend");
            addFriendTitle = new Text("Add Friend");
            friendName = new Text("Username:");
            
            searchBar = new TextField();
            friendTextField = new TextField();
            
            recItemTitle.setFont(Font.font("Segoe UI Bold",25));
            popItemTitle.setFont(Font.font("Segoe UI Bold",25));
            sellTitle.setFont(Font.font("Segoe UI Bold",25));
            bidTitle.setFont(Font.font("Segoe UI Bold",25));
            friendTitle.setFont(Font.font("Segoe UI Bold",25));
            addFriendTitle.setFont(Font.font("Segoe UI Bold",25));
            
            sellListView.setOnMouseClicked(new EventHandler<MouseEvent>(){
                @Override
                public void handle(MouseEvent click) {
                    if (click.getClickCount()==2) {
                        thisItem = sellListView.getSelectionModel().getSelectedItem().toString();
                        viewItemScene = new ViewItemPage();
                        window.setScene(viewItemScene);
                    }
                }
            });
            
            bidListView.setOnMouseClicked(new EventHandler<MouseEvent>(){
                @Override
                public void handle(MouseEvent click) {
                    if (click.getClickCount()==2) {
                        thisItem = bidListView.getSelectionModel().getSelectedItem().toString();
                        viewItemScene = new ViewItemPage();
                        window.setScene(viewItemScene);
                    }
                }
            });
            
            searchBtn = new Button("Search");
            sellBtn = new Button("+");
            friendBtn = new Button("+");
            addFriendBtn = new Button("Add");
            
            searchBtn.setOnAction(event -> {
                if (DataManager.checkValidBListWord(searchBar.getText()) && !searchBar.getText().contentEquals("")) {
                    searchResultList = FXCollections.observableArrayList(DataManager.getListofSearchItems(searchBar.getText()));
                    searchResultListView = new ListView<>(searchResultList);
                    
                    GridPane ouSearchResults = new GridPane();
                    Scene ouSearchResultScene = new Scene(ouSearchResults, 700, 700);
                    
                    sceneTitle = new Text("<banner>NotAmazon logo<banner>");
                    
                    sceneTitle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent click) {
                            if (click.getClickCount() == 2) {
                                guMainScene = new GUMainPage();
                                window.setScene(guMainScene);
                            }
                        }
                    });
                    
                    searchResultListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent click) {
                            if (click.getClickCount() == 2) {
                                thisItem = searchResultListView.getSelectionModel().getSelectedItem().toString();
                                viewItemScene = new ViewItemPage();
                                window.setScene(viewItemScene);
                            }
                        }
                    });
                    
                    Button backBtn = new Button("Back");
                    
                    backBtn.setOnAction(e -> {
                        guMainScene = new GUMainPage();
                        window.setScene(guMainScene);
                    });
                    
                    searchResultListView.setPrefWidth(600);
                    searchResultListView.setPrefHeight(600);
                    searchResultListView.setOrientation(Orientation.VERTICAL);
                    ouSearchResults.setAlignment(Pos.BASELINE_CENTER);
                    
                    // New window (Stage)
                    Stage resultsWindow = new Stage();
                    resultsWindow.setScene(ouSearchResultScene);
                    
                    ouSearchResults.setHgap(10);
                    ouSearchResults.setVgap(10);
                    ouSearchResults.setPadding(new Insets(25, 25, 25, 25));
                    ouSearchResults.add(searchResultListView, 0, 1, 2, 1);
                    ouSearchResults.add(sceneTitle, 0, 0, 2, 1);
                    ouSearchResults.add(backBtn, 0, 2);
                    
                    resultsWindow.show();
                } else {
                    Alert fail = new Alert(AlertType.ERROR);
                    fail.setTitle("Error");
                    fail.setHeaderText(null);
                    fail.setContentText("Invalid input. Please try again.");
                    fail.showAndWait();
                }
            });
            
            sellBtn.setOnAction(event -> {
                sellItemAppScene = new SellItemAppPage();
                window.setScene(sellItemAppScene);
            });
            
            addFriendBtn.setOnAction(e->{
                friend = friendTextField.getText();
                if(DataManager.isValidUsername(friend) && !(friend.equals(thisUser)) && DataManager.checkValidFriend(thisUser,friend)) {
                    DataManager.addNewFriend(thisUser, friend);
                    Alert success = new Alert(AlertType.INFORMATION);
                    success.setTitle("Valid Username");
                    success.setHeaderText(null);
                    success.setContentText("Valid username. The user has been added.");
                    success.showAndWait();
                    friendTextField = new TextField();
                }
                else {
                    Alert warning = new Alert(AlertType.WARNING);
                    warning.setTitle("Invalid Username");
                    warning.setHeaderText(null);
                    warning.setContentText("Invalid username. Please try again.");
                    warning.showAndWait();
                }
            });
            
            friendBtn.setOnAction(event -> {
                GridPane friendLayout = new GridPane();
                Scene friendScene = new Scene(friendLayout, 400, 200);
                
                // New window (Stage)
                Stage friendWindow = new Stage();
                friendWindow.setTitle("Add Friend");
                friendWindow.setScene(friendScene);
                
                friendLayout.setAlignment(Pos.BASELINE_CENTER);
                friendLayout.setHgap(10);
                friendLayout.setVgap(10);
                friendLayout.setPadding(new Insets(25, 25, 25, 25));
                friendLayout.add(addFriendTitle, 2, 0, 2, 1);
                friendLayout.add(friendName, 0, 1, 2, 1);
                friendLayout.add(friendTextField, 2, 1, 2, 1);
                friendLayout.add(addFriendBtn, 0, 3, 1, 2);
                
                friendWindow.show();
            });
            
            //dropdown menu
            menu = new MenuButton("My NotAmazon");
            profile = new MenuItem("Profile");
            myAcc = new MenuItem("My Account");
            myTranHist = new MenuItem("My Transaction History");
            signOut = new MenuItem("Sign Out");
            menu.getItems().addAll(profile, myAcc, myTranHist, signOut);
            
            
            profile.setOnAction(event -> {
                myProfileScene = new MyProfilePage();
                window.setScene(myProfileScene);
            });
            
            myAcc.setOnAction(event -> {
                myAccountScene = new MyAccountPage();
                window.setScene(myAccountScene);
            });
            
            myTranHist.setOnAction(event -> {
                transScene = new TransactionPage();
                window.setScene(transScene);
            });
            
            signOut.setOnAction(event -> {
                guMainScene = new GUMainPage();
                window.setScene(guMainScene);
            });
            //dropdown menu
            
            sellListView.setPrefWidth(300);
            sellListView.setPrefHeight(400);
            sellListView.setOrientation(Orientation.VERTICAL);
            bidListView.setPrefWidth(300);
            bidListView.setPrefHeight(400);
            bidListView.setOrientation(Orientation.VERTICAL);
            friendListView.setPrefWidth(300);
            friendListView.setPrefHeight(400);
            friendListView.setOrientation(Orientation.VERTICAL);
            
            
            layout.setAlignment(Pos.BASELINE_CENTER);
            layout.setHgap(10);
            layout.setVgap(10);
            layout.setPadding(new Insets(25, 25, 25, 25));
            //placing objects into scene
            layout.add(sceneTitle, 0, 0, 2, 1);
            layout.add(searchBar, 0, 1, 2, 1);
            layout.add(recItemTitle, 0, 3, 2, 1);
            layout.add(popItemTitle, 0, 6, 2, 1);
            layout.add(searchBtn, 2, 1, 2, 1);
            layout.add(sellTitle, 2, 3, 1, 1);
            layout.add(sellListView, 2, 4, 2, 1);
            layout.add(bidTitle, 2, 5, 1, 1);
            layout.add(bidListView, 2, 6, 2, 1);
            layout.add(friendTitle, 2, 7, 1, 1);
            layout.add(friendListView, 2, 8, 2, 1);
            layout.add(menu, 3, 1, 2, 1);
            layout.add(sellBtn, 3, 3, 2, 1);
            layout.add(friendBtn, 3, 7, 2, 1);
        }
    }
    
    class TransactionPage extends Scene{
        GridPane layout;
        Text transTitle;
        Button backBtn;
        
        public TransactionPage() {
            super(new GridPane(),700,700);
            layout = (GridPane)this.getRoot();
            transTitle = new Text("Transaction History");
            
            backBtn = new Button("Back");
            
            transTitle.setFont(Font.font("Segoe UI Bold",25));
            
            backBtn.setOnAction(event -> {
                ouMainScene = new OUMainPage();
                window.setScene(ouMainScene);
            });
            
            layout.setAlignment(Pos.BASELINE_CENTER);
            layout.setHgap(10);
            layout.setVgap(10);
            layout.setPadding(new Insets(25, 25, 25, 25));
            
            layout.add(transTitle, 0, 0, 2, 1);
            layout.add(backBtn, 9, 26, 2, 1);
        }
    }
    
    class MyProfilePage extends Scene{
        GridPane layout;
        Text sceneTitle;
        Text itemsSale;
        Text ratings;
        Label username;
        Label name;
        TextField searchBar;
        Button searchBtn;
        Button backBtn;
        MenuButton menu;
        MenuItem profile;
        MenuItem myAcc;
        MenuItem myTranHist;
        MenuItem signOut;
        String [] personalInfo;
        ObservableList searchResultList;
        ListView searchResultListView;
        ObservableList sellingList;
        ListView sellListView;
        
        
        public MyProfilePage() {
            super(new GridPane(), 700, 700);
            layout = (GridPane) this.getRoot();
            sceneTitle = new Text("<banner>NotAmazon logo<banner>");
            itemsSale = new Text("Items for sale");
            ratings = new Text("Ratings");
            searchBar = new TextField();
            
            personalInfo = DataManager.getPersonalInfo(thisUser);
            sellingList = FXCollections.observableArrayList(DataManager.getListOfItems(thisUser));
            sellListView = new ListView<>(sellingList);
            
            searchBtn = new Button("Search");
            backBtn = new Button("Back");
            
            sceneTitle.setFont(Font.font("Segoe UI Bold", 25));
            itemsSale.setFont(Font.font("Segoe UI Bold",25));
            ratings.setFont(Font.font("Segoe UI Bold",25));
            
            sceneTitle.setOnMouseClicked(new EventHandler<MouseEvent>(){
                @Override
                public void handle(MouseEvent click) {
                    if (click.getClickCount()==2) {
                        ouMainScene = new OUMainPage();
                        window.setScene(ouMainScene);
                    }
                }
            });

            sellListView.setOnMouseClicked(new EventHandler<MouseEvent>(){
                @Override
                public void handle(MouseEvent click) {
                    if (click.getClickCount()==2) {
                        thisItem = sellListView.getSelectionModel().getSelectedItem().toString();
                        viewItemScene = new ViewItemPage();
                        window.setScene(viewItemScene);
                    }
                }
            });
            
            DropShadow dropShadow = new DropShadow();
            dropShadow.setOffsetX(2);
            dropShadow.setOffsetY(2);
            
            //dropdown menu
            menu = new MenuButton("My NotAmazon");
            profile = new MenuItem("Profile");
            myAcc = new MenuItem("My Account");
            myTranHist = new MenuItem("My Transaction History");
            signOut = new MenuItem("Sign Out");
            menu.getItems().addAll(profile, myAcc, myTranHist, signOut);
            
            profile.setOnAction(event -> {
                myProfileScene = new MyProfilePage();
                window.setScene(myProfileScene);
            });
            
            myAcc.setOnAction(event -> {
                myAccountScene = new MyAccountPage();
                window.setScene(myAccountScene);
            });
            
            myTranHist.setOnAction(event -> {
                transScene = new TransactionPage();
                window.setScene(transScene);
            });
            
            signOut.setOnAction(event -> {
                guMainScene = new GUMainPage();
                window.setScene(guMainScene);
            });
            
            searchBtn.setOnAction(event -> {
                if (DataManager.checkValidBListWord(searchBar.getText()) && !searchBar.getText().contentEquals("")) {
                    searchResultList = FXCollections.observableArrayList(DataManager.getListofSearchItems(searchBar.getText()));
                    searchResultListView = new ListView<>(searchResultList);
                    
                    GridPane ouSearchResults = new GridPane();
                    Scene ouSearchResultScene = new Scene(ouSearchResults, 700, 700);
                    
                    sceneTitle = new Text("<banner>NotAmazon logo<banner>");
                    
                    sceneTitle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent click) {
                            if (click.getClickCount() == 2) {
                                ouMainScene = new OUMainPage();
                                window.setScene(ouMainScene);
                            }
                        }
                    });
                    
                    searchResultListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent click) {
                            if (click.getClickCount() == 2) {
                                thisItem = searchResultListView.getSelectionModel().getSelectedItem().toString();
                                viewItemScene = new ViewItemPage();
                                window.setScene(viewItemScene);
                            }
                        }
                    });
                    
                    Button backBtn = new Button("Back");
                    
                    backBtn.setOnAction(e -> {
                        guMainScene = new GUMainPage();
                        window.setScene(guMainScene);
                    });
                    
                    searchResultListView.setPrefWidth(600);
                    searchResultListView.setPrefHeight(600);
                    searchResultListView.setOrientation(Orientation.VERTICAL);
                    ouSearchResults.setAlignment(Pos.BASELINE_CENTER);
                    
                    // New window (Stage)
                    Stage resultsWindow = new Stage();
                    resultsWindow.setScene(ouSearchResultScene);
                    
                    ouSearchResults.setHgap(10);
                    ouSearchResults.setVgap(10);
                    ouSearchResults.setPadding(new Insets(25, 25, 25, 25));
                    ouSearchResults.add(searchResultListView, 0, 1, 2, 1);
                    ouSearchResults.add(sceneTitle, 0, 0, 2, 1);
                    ouSearchResults.add(backBtn, 0, 2);
                    
                    resultsWindow.show();
                }else {
                    Alert fail = new Alert(AlertType.ERROR);
                    fail.setTitle("Error");
                    fail.setHeaderText(null);
                    fail.setContentText("Invalid input. Please try again.");
                    fail.showAndWait();
                }
            });
            
            backBtn.setOnAction(event -> {
                ouMainScene = new OUMainPage();
                window.setScene(ouMainScene);
            });
            
            //rectangle for profile info
            username = new Label("  " + personalInfo[0]);
            name = new Label("  " + personalInfo[1] + " " + personalInfo[2]);
            Rectangle rectangle = new Rectangle(100, 100, 660, 160);
            rectangle.setFill(Color.LIGHTGRAY);
            rectangle.setArcHeight(10.0d);
            rectangle.setArcWidth(10.0d);
            rectangle.setEffect(dropShadow);
            StackPane stack_pane = new StackPane(rectangle, username, name);
            StackPane.setAlignment(username, Pos.TOP_LEFT);
            StackPane.setAlignment(name,Pos.CENTER_LEFT);

            sellListView.setPrefSize(600,200);
            sellListView.setOrientation(Orientation.VERTICAL);
            
            username.setFont(Font.font("Segoe UI Bold", 15));
            name.setFont(Font.font("Segoe UI Bold", 15));
            
            layout.setAlignment(Pos.BASELINE_CENTER);
            layout.setHgap(10);
            layout.setVgap(5);
            layout.setPadding(new Insets(25, 25, 25, 25));
            
            layout.add(sceneTitle, 0, 0, 2, 1);
            layout.add(searchBar, 0, 1, 2, 1);
            layout.add(searchBtn, 2, 1, 2, 1);
            layout.add(stack_pane, 0, 4, 2, 1);
            layout.add(menu, 4, 1);
            layout.add(itemsSale,0,6,2,1);
            layout.add(sellListView,0,7,4,1);
            layout.add(ratings,0,8,2,1);
            
        }
    }
    
    class MyAccountPage extends Scene{
        GridPane layout;
        Text sceneTitle;
        Label username;
        Label name;
        Label address;
        Label phonenum;
        TextField searchBar;
        Button searchBtn;
        Button backBtn;
        Button editNameBtn;
        Button editAddrBtn;
        Button editPhoneBtn;
        Button editCCBtn;
        Button editPWBtn;
        MenuButton menu;
        MenuItem profile;
        MenuItem myAcc;
        MenuItem myTranHist;
        MenuItem signOut;
        String [] personalInfo;
        ObservableList searchResultList;
        ListView searchResultListView;
        
        public MyAccountPage(){
            super(new GridPane(), 700, 350);
            layout = (GridPane) this.getRoot();
            sceneTitle = new Text("<banner>NotAmazon logo<banner>");
            personalInfo = DataManager.getPersonalInfo(thisUser);
            
            searchBar = new TextField();
            searchBtn = new Button("Search");
            backBtn = new Button("Back");
            editNameBtn = new Button("Change");
            editAddrBtn = new Button("Change");
            editPhoneBtn = new Button("Change");
            editCCBtn = new Button("Change credit card");
            editPWBtn = new Button("Change password");
            
            sceneTitle.setFont(Font.font("Segoe UI Bold", 20));
            
            username = new Label("Username: " + personalInfo[0]);
            name = new Label("Name: " + personalInfo[1] + " " + personalInfo[2]);
            address = new Label("Address: " + personalInfo[3]);
            phonenum = new Label("Phone Number: " + personalInfo[4]);
            
            username.setFont(Font.font("Segoe UI Bold", 13));
            name.setFont(Font.font("Segoe UI Bold",13));
            address.setFont(Font.font("Segoe UI Bold",13));
            phonenum.setFont(Font.font("Segoe UI Bold",13));
            
            sceneTitle.setOnMouseClicked(new EventHandler<MouseEvent>(){
                @Override
                public void handle(MouseEvent click) {
                    if (click.getClickCount()==2) {
                        ouMainScene = new OUMainPage();
                        window.setScene(ouMainScene);
                    }
                }
            });
            
            //dropdown menu
            menu = new MenuButton("My NotAmazon");
            profile = new MenuItem("Profile");
            myAcc = new MenuItem("My Account");
            myTranHist = new MenuItem("My Transaction History");
            signOut = new MenuItem("Sign Out");
            menu.getItems().addAll(profile, myAcc, myTranHist, signOut);
            
            profile.setOnAction(event -> {
                myProfileScene = new MyProfilePage();
                window.setScene(myProfileScene);
            });
            
            myAcc.setOnAction(event -> {
                myAccountScene = new MyAccountPage();
                window.setScene(myAccountScene);
            });
            
            myTranHist.setOnAction(event -> {
                transScene = new TransactionPage();
                window.setScene(transScene);
            });
            
            signOut.setOnAction(event -> {
                guMainScene = new GUMainPage();
                window.setScene(guMainScene);
            });
            
            searchBtn.setOnAction(event -> {
                if (DataManager.checkValidBListWord(searchBar.getText()) && !searchBar.getText().contentEquals("")) {
                    searchResultList = FXCollections.observableArrayList(DataManager.getListofSearchItems(searchBar.getText()));
                    searchResultListView = new ListView<>(searchResultList);
                    
                    GridPane ouSearchResults = new GridPane();
                    Scene ouSearchResultScene = new Scene(ouSearchResults, 700, 700);
                    
                    sceneTitle = new Text("<banner>NotAmazon logo<banner>");
                    
                    sceneTitle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent click) {
                            if (click.getClickCount() == 2) {
                                guMainScene = new GUMainPage();
                                window.setScene(guMainScene);
                            }
                        }
                    });
                    
                    searchResultListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent click) {
                            if (click.getClickCount() == 2) {
                                thisItem = searchResultListView.getSelectionModel().getSelectedItem().toString();
                                viewItemScene = new ViewItemPage();
                                window.setScene(viewItemScene);
                            }
                        }
                    });
                    
                    Button backBtn = new Button("Back");
                    
                    backBtn.setOnAction(e -> {
                        guMainScene = new GUMainPage();
                        window.setScene(guMainScene);
                    });
                    
                    searchResultListView.setPrefWidth(600);
                    searchResultListView.setPrefHeight(600);
                    searchResultListView.setOrientation(Orientation.VERTICAL);
                    ouSearchResults.setAlignment(Pos.BASELINE_CENTER);
                    
                    // New window (Stage)
                    Stage resultsWindow = new Stage();
                    resultsWindow.setScene(ouSearchResultScene);
                    
                    ouSearchResults.setHgap(10);
                    ouSearchResults.setVgap(10);
                    ouSearchResults.setPadding(new Insets(25, 25, 25, 25));
                    ouSearchResults.add(searchResultListView, 0, 1, 2, 1);
                    ouSearchResults.add(sceneTitle, 0, 0, 2, 1);
                    ouSearchResults.add(backBtn, 0, 2);
                    
                    resultsWindow.show();
                }else {
                    Alert fail = new Alert(AlertType.ERROR);
                    fail.setTitle("Error");
                    fail.setHeaderText(null);
                    fail.setContentText("Invalid input. Please try again.");
                    fail.showAndWait();
                }
            });
            
            backBtn.setOnAction(event -> {
                ouMainScene = new OUMainPage();
                window.setScene(ouMainScene);
            });
            
            editNameBtn.setOnAction(event -> {
                editNameScene = new EditNamePage();
                window.setScene(editNameScene);
            });
            
            editAddrBtn.setOnAction(event -> {
                editAddrScene = new EditAddressPage();
                window.setScene(editAddrScene);
            });
            
            editPhoneBtn.setOnAction(event -> {
                editPhoneScene = new EditPhoneNum();
                window.setScene(editPhoneScene);
            });
            
            editCCBtn.setOnAction(event -> {
                editCCScene = new EditCCNum();
                window.setScene(editCCScene);
            });
            
            editPWBtn.setOnAction(event -> {
                editPWScene = new EditPasswordPage();
                window.setScene(editPWScene);
            });
            
            layout.setAlignment(Pos.BASELINE_CENTER);
            layout.setHgap(10);
            layout.setVgap(5);
            layout.setPadding(new Insets(25, 25, 25, 25));
            
            layout.add(sceneTitle, 0, 0, 2, 1);
            layout.add(searchBar, 0, 1, 2, 1);
            layout.add(searchBtn, 2, 1, 2, 1);
            layout.add(menu, 4, 1);
            layout.add(username, 0,5,2,1);
            layout.add(name,0,6,2,1);
            layout.add(address,0,7,2,1);
            layout.add(phonenum,0,8,2,1);
            layout.add(editNameBtn,2,6);
            layout.add(editAddrBtn,2,7);
            layout.add(editPhoneBtn,2,8);
            layout.add(editPWBtn,0,9);
            layout.add(editCCBtn,0,10);
        }
    }
    
    class EditNamePage extends Scene{
        GridPane layout;
        Text sceneTitle;
        Text firstname;
        Text lastname;
        TextField firstname_field;
        TextField lastname_field;
        Button updateBtn;
        Button cancelBtn;
        
        private boolean validateFields(){
            if(firstname_field.getText().isEmpty() | lastname_field.getText().isEmpty()){
                
                Alert warnUsr = new Alert(AlertType.WARNING);
                warnUsr.setTitle("Warning");
                warnUsr.setHeaderText("An empty field has been detected.");
                warnUsr.setContentText("Input into the field (you do not wish to change) with the currently existing " +
                                       "information (i.e. if you want to change just your first name, input \"Jane Doe\" if you want " +
                                       "to change it from \"John Doe\").");
                warnUsr.showAndWait();
                
                return false;
            }
            return true;
        }
        
        public EditNamePage(){
            super(new GridPane(), 300, 200);
            layout = (GridPane) this.getRoot();
            
            sceneTitle = new Text("Update Information: Name");
            firstname = new Text("First name ");
            lastname = new Text("Last name ");
            
            firstname_field = new TextField();
            firstname_field.setPromptText("Enter your first name.");
            lastname_field = new TextField();
            lastname_field.setPromptText("Enter your last name.");
            
            updateBtn = new Button("Update");
            cancelBtn = new Button("Cancel");
            
            updateBtn.setOnAction(event -> {
                if(validateFields()){
                    DataManager.updateUserName(thisUser,firstname_field.getText(),lastname_field.getText());
                    myAccountScene = new MyAccountPage();
                    window.setScene(myAccountScene);
                }
            });
            
            cancelBtn.setOnAction(event-> {
                myAccountScene = new MyAccountPage();
                window.setScene(myAccountScene);
            });
            
            updateBtn.setAlignment(Pos.BOTTOM_LEFT);
            cancelBtn.setAlignment(Pos.BOTTOM_LEFT);
            
            layout.setAlignment(Pos.CENTER);
            layout.setHgap(10);
            layout.setVgap(5);
            layout.setPadding(new Insets(25, 25, 25, 25));
            
            layout.add(sceneTitle, 0, 0, 3, 1);
            layout.add(firstname,0,3);
            layout.add(lastname,0,4);
            layout.add(firstname_field,1,3,3,1);
            layout.add(lastname_field,1,4,3,1);
            layout.add(updateBtn,1,5);
            layout.add(cancelBtn,0,5);
        }
    }
    
    class EditAddressPage extends Scene{
        GridPane layout;
        Text sceneTitle;
        Text addr;
        TextField addr_field;
        Button updateBtn;
        Button cancelBtn;
        
        private boolean validateFields(){
            if(addr_field.getText().isEmpty()){
                
                Alert warnUsr = new Alert(AlertType.WARNING);
                warnUsr.setTitle("Warning");
                warnUsr.setHeaderText("An empty field has been detected.");
                warnUsr.setContentText("Please fill in the empty field and try again.");
                warnUsr.showAndWait();
                
                return false;
            }
            return true;
        }
        
        public EditAddressPage(){
            super(new GridPane(), 250, 200);
            layout = (GridPane) this.getRoot();
            
            sceneTitle = new Text("Update Information: Address");
            addr = new Text("Address ");
            
            addr_field = new TextField();
            addr_field.setPromptText("Enter your street address.");
            
            updateBtn = new Button("Update");
            cancelBtn = new Button("Cancel");
            
            updateBtn.setOnAction(event -> {
                if(validateFields()){
                    DataManager.updateUserAddr(thisUser,addr_field.getText());
                    myAccountScene = new MyAccountPage();
                    window.setScene(myAccountScene);
                }
            });
            
            cancelBtn.setOnAction(event-> {
                myAccountScene = new MyAccountPage();
                window.setScene(myAccountScene);
            });
            
            updateBtn.setAlignment(Pos.BOTTOM_LEFT);
            cancelBtn.setAlignment(Pos.BOTTOM_LEFT);
            
            layout.setAlignment(Pos.CENTER);
            layout.setHgap(10);
            layout.setVgap(5);
            layout.setPadding(new Insets(25, 25, 25, 25));
            
            layout.add(sceneTitle, 0, 0, 2, 1);
            layout.add(addr,0,3);
            layout.add(addr_field,1,3,2,1);
            layout.add(updateBtn,1,4);
            layout.add(cancelBtn,0,4);
        }
    }
    
    class EditPasswordPage extends Scene {
        GridPane layout;
        Text sceneTitle;
        Text newPass;
        Text confirmPass;
        TextField newPass_field;
        TextField confirmPass_field;
        Button updateBtn;
        Button cancelBtn;
        Tooltip t1;
        String [] personalInfo;
        
        private boolean validatePassword() {
            Pattern p = Pattern.compile("((?=.*\\d).{6,15})");
            Matcher m = p.matcher(confirmPass_field.getText());
            
            if(m.matches()){
                return true;
            }else {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Validate Password");
                alert.setHeaderText(null);
                alert.setContentText("Password must contain at least one digit with a length of 6-15 characters.");
                alert.showAndWait();
                
                return false;
            }
        }
        
        private boolean validateFields() {
            if (newPass_field.getText().isEmpty() | confirmPass_field.getText().isEmpty()){
                
                Alert warnUsr = new Alert(AlertType.WARNING);
                warnUsr.setTitle("Warning");
                warnUsr.setHeaderText("An empty field has been detected.");
                warnUsr.setContentText("Please fill in the empty field and try again.");
                warnUsr.showAndWait();
                
                return false;
            }
            return true;
        }
        
        public EditPasswordPage() {
            super(new GridPane(), 400, 230);
            layout = (GridPane) this.getRoot();
            personalInfo = DataManager.getPersonalInfo(thisUser);
            
            sceneTitle = new Text("Update Information: Password");
            newPass = new Text("New password ");
            confirmPass = new Text("Confirm new password ");
            
            newPass_field = new TextField();
            newPass_field.setPromptText("Enter new password.");
            confirmPass_field = new TextField();
            confirmPass_field.setPromptText("Enter again.");
            
            t1 = new Tooltip("Must contain at least 1 digit and have a length of 6-15 characters.");
            t1.setFont(Font.font("Segoe UI Bold",12));
            newPass_field.setTooltip(t1);
            
            updateBtn = new Button("Update");
            cancelBtn = new Button("Cancel");
            
            updateBtn.setOnAction(event -> {
                if (validateFields() && validatePassword()) {
                    String newPass1 = newPass_field.getText();
                    String newPass2 = confirmPass_field.getText();
                    if (newPass1 == newPass2) {
                        Alert warnUsr = new Alert(AlertType.WARNING);
                        warnUsr.setTitle("Error");
                        warnUsr.setHeaderText("An incorrect field has been detected.");
                        warnUsr.setContentText("Password must contain at least 1 digit and have a length of 6-15 " +
                                               "characters. Please fill in the field correctly and try again.");
                        warnUsr.showAndWait();
                    }else {
                        DataManager.updateUserPass(thisUser, newPass_field.getText());
                        myAccountScene = new MyAccountPage();
                        window.setScene(myAccountScene);
                    }
                }
            });
            
            cancelBtn.setOnAction(event -> {
                myAccountScene = new MyAccountPage();
                window.setScene(myAccountScene);
            });
            
            updateBtn.setAlignment(Pos.BOTTOM_RIGHT);
            cancelBtn.setAlignment(Pos.BOTTOM_RIGHT);
            
            layout.setAlignment(Pos.CENTER);
            layout.setHgap(10);
            layout.setVgap(5);
            layout.setPadding(new Insets(25, 25, 25, 25));
            
            layout.add(sceneTitle, 0, 0, 2, 1);
            layout.add(newPass, 0, 3);
            layout.add(confirmPass, 0, 4);
            layout.add(newPass_field, 1, 3, 2, 1);
            layout.add(confirmPass_field, 1, 4, 2, 1);
            layout.add(updateBtn, 1, 5);
            layout.add(cancelBtn, 0, 5);
        }
    }
    
    class EditPhoneNum extends Scene{
        GridPane layout;
        Text sceneTitle;
        Text newPhone;
        TextField newPhone_field;
        Button updateBtn;
        Button cancelBtn;
        Tooltip t1;
        
        private boolean validatePhoneNum() {
            Pattern p = Pattern.compile("((?=.*\\d).{10})");
            Matcher m = p.matcher(newPhone_field.getText());
            
            if(m.matches()){
                return true;
            }else {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Validate Phone Number");
                alert.setHeaderText(null);
                alert.setContentText("Invalid input of phone number. Please try again.");
                alert.showAndWait();
                
                return false;
            }
        }
        
        private boolean validateFields() {
            if (newPhone_field.getText().isEmpty()){
                
                Alert warnUsr = new Alert(AlertType.WARNING);
                warnUsr.setTitle("Warning");
                warnUsr.setHeaderText("An empty field has been detected.");
                warnUsr.setContentText("Please fill in the empty field and try again.");
                warnUsr.showAndWait();
                
                return false;
            }
            return true;
        }
        
        public EditPhoneNum() {
            super(new GridPane(), 400, 230);
            layout = (GridPane) this.getRoot();
            
            sceneTitle = new Text("Update Information: Phone Number");
            newPhone = new Text("New phone number ");
            
            newPhone_field = new TextField();
            newPhone_field.setPromptText("e.g. 1112223333");
            
            t1 = new Tooltip("Input your 10-digit phone number without the country, spaces, or dashes.");
            t1.setFont(Font.font("Segoe UI Bold",12));
            newPhone_field.setTooltip(t1);
            
            updateBtn = new Button("Update");
            cancelBtn = new Button("Cancel");
            
            updateBtn.setOnAction(event -> {
                if (validateFields() && validatePhoneNum()) {
                    DataManager.updateUserPhoneNum(thisUser, newPhone_field.getText());
                    myAccountScene = new MyAccountPage();
                    window.setScene(myAccountScene);
                }
            });
            
            cancelBtn.setOnAction(event -> {
                myAccountScene = new MyAccountPage();
                window.setScene(myAccountScene);
            });
            
            updateBtn.setAlignment(Pos.BOTTOM_RIGHT);
            cancelBtn.setAlignment(Pos.BOTTOM_RIGHT);
            
            layout.setAlignment(Pos.CENTER);
            layout.setHgap(10);
            layout.setVgap(5);
            layout.setPadding(new Insets(25, 25, 25, 25));
            
            layout.add(sceneTitle, 0, 0, 2, 1);
            layout.add(newPhone,0,3);
            layout.add(newPhone_field,1,3,2,1);
            layout.add(updateBtn,1,4);
            layout.add(cancelBtn,0,4);
        }
    }
    
    class EditCCNum extends Scene{
        GridPane layout;
        Text sceneTitle;
        Text newCC;
        TextField newCC_field;
        Button updateBtn;
        Button cancelBtn;
        Tooltip t1;
        
        private boolean validateCCNum() {
            Pattern p = Pattern.compile("((?=.*\\d).{16})");
            Matcher m = p.matcher(newCC_field.getText());
            
            if(m.matches()){
                return true;
            }else {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Validate Credit Card Number");
                alert.setHeaderText(null);
                alert.setContentText("Invalid input of credit card number. Please try again.");
                alert.showAndWait();
                
                return false;
            }
        }
        
        private boolean validateFields() {
            if (newCC_field.getText().isEmpty()){
                
                Alert warnUsr = new Alert(AlertType.WARNING);
                warnUsr.setTitle("Warning");
                warnUsr.setHeaderText("An empty field has been detected.");
                warnUsr.setContentText("Please fill in the empty field and try again.");
                warnUsr.showAndWait();
                
                return false;
            }
            return true;
        }
        
        public EditCCNum() {
            super(new GridPane(), 400, 230);
            layout = (GridPane) this.getRoot();
            
            sceneTitle = new Text("Update Information: Credt Card");
            newCC = new Text("New phone number ");
            
            newCC_field = new TextField();
            newCC_field.setPromptText("e.g. 1111222233334444");
            
            t1 = new Tooltip("Input your 16-digit credit card number without spaces or dashes.");
            t1.setFont(Font.font("Segoe UI Bold",12));
            newCC_field.setTooltip(t1);
            
            updateBtn = new Button("Update");
            cancelBtn = new Button("Cancel");
            
            updateBtn.setOnAction(event -> {
                if (validateFields() && validateCCNum()) {
                    DataManager.updateUserCCNum(thisUser, newCC_field.getText());
                    myAccountScene = new MyAccountPage();
                    window.setScene(myAccountScene);
                }
            });
            
            cancelBtn.setOnAction(event -> {
                myAccountScene = new MyAccountPage();
                window.setScene(myAccountScene);
            });
            
            updateBtn.setAlignment(Pos.BOTTOM_RIGHT);
            cancelBtn.setAlignment(Pos.BOTTOM_RIGHT);
            
            layout.setAlignment(Pos.CENTER);
            layout.setHgap(10);
            layout.setVgap(5);
            layout.setPadding(new Insets(25, 25, 25, 25));
            
            layout.add(sceneTitle, 0, 0, 2, 1);
            layout.add(newCC,0,3);
            layout.add(newCC_field,1,3,2,1);
            layout.add(updateBtn,1,4);
            layout.add(cancelBtn,0,4);
        }
    }
    
    class ReportAppPage extends Scene{
        GridPane layout;
        Text sceneTitle;
        Text reportUser;
        Text reasonForReport;
        Text reasonDetails;
        TextField reportUser_TextField;
        TextField mainReason_TextField;
        TextField reasonTextField;
        Button submitBtn;
        Button cancelBtn;
        
        private boolean validateFields() {
            if (reportUser_TextField.getText().isEmpty()){
                
                Alert warnUsr = new Alert(AlertType.WARNING);
                warnUsr.setTitle("Warning");
                warnUsr.setHeaderText("No user being reported.");
                warnUsr.setContentText("Please fill in the empty field and try again.");
                warnUsr.showAndWait();
                
                return false;
            }
            else if (mainReason_TextField.getText().isEmpty()){
                
                Alert warnUsr = new Alert(AlertType.WARNING);
                warnUsr.setTitle("Warning");
                warnUsr.setHeaderText("A reason for reporting the user has not been selected.");
                warnUsr.setContentText("Please select a reason and try again.");
                warnUsr.showAndWait();
                
                return false;
            }
            return true;
        }
        
        public ReportAppPage() {
            super(new GridPane(),500,500);
            layout = (GridPane)this.getRoot();
            
            sceneTitle = new Text("Report User");
            
            reportUser = new Text("User to report: ");
            reasonForReport = new Text("Reason: ");
            reasonDetails = new Text("If possible, provide an explanation as to your reason. More details in your " +
                                     "explanation\nmay help aid the administrators in their decision.");
            
            mainReason_TextField = new TextField();
            mainReason_TextField.setPromptText("e.g. User misconduct");
            reasonTextField = new TextField();
            reasonTextField.setPrefSize(450,200);
            reportUser_TextField = new TextField();
            
            
            submitBtn = new Button("Submit");
            cancelBtn = new Button("Cancel");
            
            submitBtn.setOnAction(event -> {
                if(validateFields()) {
                    String reportThisUser = reportUser_TextField.getText();
                    String whatReason = mainReason_TextField.getText();
                    String reasonText = reasonTextField.getText();
                    DataManager.addReport(thisUser, reportThisUser, whatReason, reasonText);
                    window.setScene(ouMainScene);
                }
            });
            
            cancelBtn.setOnAction(event -> {
                ouMainScene = new OUMainPage();
                window.setScene(ouMainScene);
            });
            
            submitBtn.setAlignment(Pos.BOTTOM_RIGHT);
            cancelBtn.setAlignment(Pos.BOTTOM_RIGHT);
            
            layout.setAlignment(Pos.BASELINE_LEFT);
            layout.setHgap(10);
            layout.setVgap(5);
            layout.setPadding(new Insets(25, 25, 25, 25));
            
            layout.add(sceneTitle, 0, 0, 2, 1);
            layout.add(reportUser,0,1);
            layout.add(reportUser_TextField,1,1);
            layout.add(reasonForReport,0,2);
            layout.add(mainReason_TextField,1,2);
            layout.add(reasonDetails,0,5,3,1);
            layout.add(reasonTextField,0,6,3,3);
            layout.add(submitBtn,1,10);
            layout.add(cancelBtn,0,10);
        }
    }
    
    class RateUserPage extends Scene{
        GridPane layout;
        Text rateUser;
        Text commnt;
        TextField rateUser_TextField;
        TextField commnt_TextField;
        Button submitBtn;
        Button cancelBtn;
        Slider ratingsBar;
        
        private boolean validateFields() {
            if (commnt_TextField.getText().isEmpty()){
                
                Alert warnUsr = new Alert(AlertType.WARNING);
                warnUsr.setTitle("Warning");
                warnUsr.setHeaderText("No user being reported.");
                warnUsr.setContentText("Please fill in the empty field and try again.");
                warnUsr.showAndWait();
                
                return false;
            }
            return true;
        }
        
        public RateUserPage() {
            super(new GridPane(),500,350);
            layout = (GridPane)this.getRoot();
            
            rateUser = new Text("User being rated: ");
            commnt = new Text("Comment");
            rateUser_TextField = new TextField();
            commnt_TextField = new TextField();
            
            commnt_TextField.setPrefSize(525,150);
            
            ratingsBar = new Slider(1, 5, 3);
            ratingsBar.setShowTickMarks(true);
            ratingsBar.setShowTickLabels(true);
            ratingsBar.setMajorTickUnit(1);
            ratingsBar.setMinorTickCount(0);
            ratingsBar.setBlockIncrement(0.5);
            ratingsBar.setSnapToTicks(true);
            
            submitBtn = new Button("Submit");
            cancelBtn = new Button("Cancel");
            
            submitBtn.setOnAction(event -> {
                if(validateFields()){
                    String whichUser = rateUser_TextField.getText();
                    String comment = commnt_TextField.getText();
                    double userRating = ratingsBar.getValue();
                    DataManager.addRating(whichUser,thisUser,userRating,comment);
                    rateUser_TextField.setText("");
                    ratingsBar.setValue(3);
                    commnt_TextField.setText("");
                }
            });
            
            cancelBtn.setOnAction(event -> {
                ouMainScene = new OUMainPage();
                window.setScene(ouMainScene);
            });
            
            submitBtn.setAlignment(Pos.BOTTOM_RIGHT);
            
            layout.setAlignment(Pos.BASELINE_LEFT);
            layout.setHgap(10);
            layout.setVgap(5);
            layout.setPadding(new Insets(25, 25, 25, 25));
            
            layout.add(rateUser,0,1);
            layout.add(rateUser_TextField,1,1);
            layout.add(ratingsBar,1,4);
            layout.add(commnt,0,6);
            layout.add(commnt_TextField,0,7,3,3);
            layout.add(submitBtn,1,14);
            layout.add(cancelBtn,0,14);
        }
    }
    
    class ViewItemPage extends Scene{
        GridPane layout;
        Text itemLabel;
        Text sceneTitle;
        Text seller;
        Text itemCondition;
        Text timeLeft;
        Text currentBid;
        Text displayCondition;
        Text displayTime;
        Text itemPrice;
        TextField myBid;
        TextField searchBar;
        Button placeBidBtn;
        Button searchBtn;
        Button backBtn;
        Button reportBtn;
        Button rateBtn;
        Button purchaseBtn;
        MenuButton menu;
        MenuItem profile;
        MenuItem myAcc;
        MenuItem myTranHist;
        MenuItem signOut;
        Object [] itemInfo;
        String [] userInfo;
        double placedBidPrice;
        double currentBidPrice;
        ObservableList searchResultList;
        ListView searchResultListView;

        public ViewItemPage(){
            super(new GridPane(),500,500);
            layout = (GridPane)this.getRoot();
            
            sceneTitle = new Text("<NotAmazon logo>");
            sceneTitle.setFont(Font.font("Segoe UI Bold",20));
            
            searchBar = new TextField();
            
            searchBtn = new Button("Search");
            backBtn = new Button("Back");
            rateBtn = new Button("Rate");
            
            sceneTitle.setOnMouseClicked(new EventHandler<MouseEvent>(){
                @Override
                public void handle(MouseEvent click) {
                    if (click.getClickCount()==2) {
                        ouMainScene = new OUMainPage();
                        window.setScene(ouMainScene);
                    }
                }
            });
            
            //dropdown menu
            menu = new MenuButton("My NotAmazon");
            profile = new MenuItem("Profile");
            myAcc = new MenuItem("My Account");
            myTranHist = new MenuItem("My Transaction History");
            signOut = new MenuItem("Sign Out");
            menu.getItems().addAll(profile, myAcc, myTranHist, signOut);
            
            profile.setOnAction(event -> {
                myProfileScene = new MyProfilePage();
                window.setScene(myProfileScene);
            });
            
            myAcc.setOnAction((event -> {
                myAccountScene = new MyAccountPage();
                window.setScene(myAccountScene);
            }));
            
            myTranHist.setOnAction(event -> {
                transScene = new TransactionPage();
                window.setScene(transScene);
            });
            
            signOut.setOnAction(event -> {
                guMainScene = new GUMainPage();
                window.setScene(guMainScene);
            });
            
            searchBtn.setOnAction(event -> {
                if (DataManager.checkValidBListWord(searchBar.getText()) && !searchBar.getText().contentEquals("")) {
                    searchResultList = FXCollections.observableArrayList(DataManager.getListofSearchItems(searchBar.getText()));
                    searchResultListView = new ListView<>(searchResultList);
                    
                    GridPane ouSearchResults = new GridPane();
                    Scene ouSearchResultScene = new Scene(ouSearchResults, 700, 700);
                    
                    sceneTitle = new Text("<banner>NotAmazon logo<banner>");
                    
                    sceneTitle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent click) {
                            if (click.getClickCount() == 2) {
                                ouMainScene = new OUMainPage();
                                window.setScene(guMainScene);
                            }
                        }
                    });

                    searchResultListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent click) {
                            if (click.getClickCount() == 2) {
                                thisItem = searchResultListView.getSelectionModel().getSelectedItem().toString();
                                viewItemScene = new ViewItemPage();
                                window.setScene(viewItemScene);
                            }
                        }
                    });
                    
                    Button backBtn = new Button("Back");
                    
                    backBtn.setOnAction(e -> {
                        guMainScene = new GUMainPage();
                        window.setScene(guMainScene);
                    });

                    searchResultListView.setPrefWidth(600);
                    searchResultListView.setPrefHeight(600);
                    searchResultListView.setOrientation(Orientation.VERTICAL);
                    ouSearchResults.setAlignment(Pos.BASELINE_CENTER);

                    // New window (Stage)
                    Stage resultsWindow = new Stage();
                    resultsWindow.setScene(ouSearchResultScene);

                    ouSearchResults.setHgap(10);
                    ouSearchResults.setVgap(10);
                    ouSearchResults.setPadding(new Insets(25, 25, 25, 25));
                    ouSearchResults.add(searchResultListView, 0, 1, 2, 1);
                    ouSearchResults.add(sceneTitle, 0, 0, 2, 1);
                    ouSearchResults.add(backBtn, 0, 2);

                    resultsWindow.show();
                }else {
                    Alert fail = new Alert(AlertType.ERROR);
                    fail.setTitle("Error");
                    fail.setHeaderText(null);
                    fail.setContentText("Invalid input. Please try again.");
                    fail.showAndWait();
                }
            });
            
            backBtn.setOnAction(event -> {
                ouMainScene = new OUMainPage();
                window.setScene(ouMainScene);
            });
            
            rateBtn.setOnAction(event -> {
                rateUserScene = new RateUserPage();
                window.setScene(rateUserScene);
            });
            
            reportBtn = new Button("Report?");
            reportBtn.setOnAction(event -> {
                reportAppScene = new ReportAppPage();
                window.setScene(reportAppScene);
            });
            
            itemInfo = DataManager.getItemInfo(thisItem);
            userInfo = DataManager.getPersonalInfo(thisUser);
            
            itemLabel = new Text((String) itemInfo[0]);
            seller = new Text("Seller: " + itemInfo[1]);
            displayCondition = new Text((String) itemInfo[4]);
            itemCondition = new Text("Condition:  "); //itemInfo[4]
            itemPrice = new Text("Original Price: " + itemInfo[2]);
            
            if(itemInfo[3].equals("1") && (userInfo[0] != itemInfo[1])) {
                displayTime = new Text((String) itemInfo[5]);
                timeLeft = new Text("Time left (minutes):  "); //itemInfo[5]
                currentBid = new Text("Current bid:  "+ itemInfo[6]); //itemInfo[2]
                myBid = new TextField();
                
                Tooltip t1 = new Tooltip("Input a price higher than the current bid.");
                t1.setFont(Font.font("Segoe UI Bold",10));
                myBid.setTooltip(t1);

                myBid.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        if (!newValue.matches("\\d{0,8}([\\.]\\d{0,2})?")) {
                            myBid.setText(oldValue);
                        }
                    }
                });
                
                placeBidBtn = new Button("Place bid");
                
                placeBidBtn.setOnAction(event -> {
                    placedBidPrice = Double.parseDouble(myBid.getText());
                    currentBidPrice = Double.parseDouble(String.valueOf(itemInfo[6]));
                    if(placedBidPrice <= currentBidPrice) {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Invalid Input");
                        alert.setHeaderText(null);
                        alert.setContentText("ERROR: Invalid input. Please input a price that is higher than the " +
                                "current bidding price.");
                        alert.showAndWait();
                    }else{
                        DataManager.updateItemBid(thisUser, thisItem, placedBidPrice, String.valueOf(itemInfo[1]));
                        viewItemScene = new ViewItemPage();
                        window.setScene(viewItemScene);
                    }
                });
                
                timeLeft.setFont(Font.font("Segoe UI",13));
                currentBid.setFont(Font.font("Segoe UI",13));
                
                layout.add(timeLeft,0,9);
                layout.add(displayTime, 1, 9);
                layout.add(currentBid,0,10);
                layout.add(myBid,0,11,2,1);
                layout.add(placeBidBtn,0,12);
                
                itemLabel.setFont(Font.font("Segoe UI Bold",15));
                seller.setFont(Font.font("Segoe UI Bold",15));
                itemCondition.setFont(Font.font("Segoe UI Bold",13));
                itemPrice.setFont(Font.font("Segoe UI Bold", 13));
                
                layout.setVgap(5);
                layout.setHgap(10);
                layout.setPadding(new Insets(25, 25, 25, 25));
                
                layout.add(sceneTitle,0,0,2,1);
                layout.add(searchBar, 0, 2, 2, 1);
                layout.add(searchBtn, 2, 2, 2, 1);
                layout.add(menu, 4, 2);
                layout.add(itemLabel,0,5,2,1);
                layout.add(seller,0,6,2,1);
                layout.add(itemCondition,0,7);
                layout.add(displayCondition, 1, 7);
                layout.add(itemPrice,0,8,2,1);
                layout.add(reportBtn,1,13);
                layout.add(rateBtn,0,13);
                
            }else if(itemInfo[3].equals("1") && userInfo[0].equals(itemInfo[1])){
                displayTime = new Text((String) itemInfo[5]);
                timeLeft = new Text("Time left (minutes):  "); //itemInfo[5]
                currentBid = new Text("Current bid:  " + itemInfo[6]); //itemInfo[2]
                
                timeLeft.setFont(Font.font("Segoe UI",13));
                currentBid.setFont(Font.font("Segoe UI",13));
                
                layout.add(timeLeft,0,9);
                layout.add(displayTime, 1, 9);
                layout.add(currentBid,0,10);
                
                itemLabel.setFont(Font.font("Segoe UI Bold",15));
                seller.setFont(Font.font("Segoe UI Bold",15));
                itemCondition.setFont(Font.font("Segoe UI Bold",13));
                itemPrice.setFont(Font.font("Segoe UI Bold", 13));
                
                layout.setVgap(5);
                layout.setHgap(10);
                layout.setPadding(new Insets(25, 25, 25, 25));
                
                layout.add(sceneTitle,0,0,2,1);
                layout.add(searchBar, 0, 2, 2, 1);
                layout.add(searchBtn, 2, 2, 2, 1);
                layout.add(menu, 4, 2);
                layout.add(itemLabel,0,5,2,1);
                layout.add(seller,0,6,2,1);
                layout.add(itemCondition,0,7);
                layout.add(displayCondition, 1, 7);
                layout.add(itemPrice,0,8,2,1);
                layout.add(reportBtn,1,13);
                layout.add(rateBtn,0,13);
            }else if(itemInfo[3].equals("0") && (userInfo[0] != itemInfo[1])){
                purchaseBtn = new Button("Purchase");
                
                purchaseBtn.setOnAction(event -> {
                    Alert confirm = new Alert(AlertType.CONFIRMATION,"Are you sure you want to make this purchase?",
                                              ButtonType.YES,ButtonType.NO);
                    confirm.setTitle("Confirming Purchase");
                    confirm.showAndWait();
                    if(confirm.getResult() == ButtonType.YES){
                        Alert address = new Alert(AlertType.CONFIRMATION,"Please confirm your address: " +
                                                  userInfo[3],ButtonType.OK,ButtonType.CANCEL);
                        address.setTitle("Confirming Purchase");
                        address.showAndWait();
                        if(address.getResult() == ButtonType.OK){
                            DataManager.addTransaction(itemInfo[0],itemInfo[1],itemInfo[2],thisUser);
                            Text thankyou = new Text("Thank you for your purchase of " + itemInfo[0] + ", " + userInfo[1]
                                                     + "!");
                            Alert thankyouAlert = new Alert(AlertType.CONFIRMATION, thankyou.getText(), ButtonType.FINISH);
                            thankyouAlert.showAndWait();
                        }else if(address.getResult() == ButtonType.CANCEL){
                            ouMainScene = new OUMainPage();
                            window.setScene(ouMainScene);
                        }
                    }else if(confirm.getResult() == ButtonType.NO){
                        ouMainScene = new OUMainPage();
                        window.setScene(ouMainScene);
                    }
                });
                
                layout.add(purchaseBtn,0,9);
                
                itemLabel.setFont(Font.font("Segoe UI Bold",15));
                seller.setFont(Font.font("Segoe UI Bold",15));
                itemCondition.setFont(Font.font("Segoe UI Bold",13));
                itemPrice.setFont(Font.font("Segoe UI Bold", 13));
                
                layout.setVgap(5);
                layout.setHgap(10);
                layout.setPadding(new Insets(25, 25, 25, 25));
                
                layout.add(sceneTitle,0,0,2,1);
                layout.add(searchBar, 0, 2, 2, 1);
                layout.add(searchBtn, 2, 2, 2, 1);
                layout.add(menu, 4, 2);
                layout.add(itemLabel,0,5,2,1);
                layout.add(seller,0,6,2,1);
                layout.add(itemCondition,0,7);
                layout.add(displayCondition, 1, 7);
                layout.add(itemPrice,0,8,2,1);
                layout.add(reportBtn,1,13);
                layout.add(rateBtn,0,13);
            }else if(itemInfo[3].equals("0") && (userInfo[0].equals(itemInfo[1]))){
                
                itemLabel.setFont(Font.font("Segoe UI Bold",15));
                seller.setFont(Font.font("Segoe UI Bold",15));
                itemCondition.setFont(Font.font("Segoe UI Bold",13));
                itemPrice.setFont(Font.font("Segoe UI Bold", 13));
                
                layout.setVgap(5);
                layout.setHgap(10);
                layout.setPadding(new Insets(25, 25, 25, 25));
                
                layout.add(sceneTitle,0,0,2,1);
                layout.add(searchBar, 0, 2, 2, 1);
                layout.add(searchBtn, 2, 2, 2, 1);
                layout.add(menu, 4, 2);
                layout.add(itemLabel,0,5,2,1);
                layout.add(seller,0,6,2,1);
                layout.add(itemCondition,0,7);
                layout.add(displayCondition, 1, 7);
                layout.add(itemPrice,0,8,2,1);
            }
        }
    }
    
    class SUMainPage extends Scene{
        GridPane layout;
        Text sceneTitle;
        Text pendAppTitle;
        Text pendItemAppTitle;
        Text reportTitle;
        Text blackListTitle;
        TextField searchBar;
        Button signOutBtn;
        Button searchBtn;
        Image userAppImage;
        Image itemAppImage;
        Image reportImage;
        Image bListImage;
        ImageView userAppView;
        ImageView itemAppView;
        ImageView reportView;
        ImageView bListView;
        ObservableList searchResultList;
        ListView searchResultListView;
        
        public SUMainPage() {
            super(new GridPane(),700,700);
            layout = (GridPane)this.getRoot();
            sceneTitle = new Text("<banner>This is the main page of Not Amazon<banner>");
            pendAppTitle = new Text("Pending Apps");
            reportTitle = new Text("Report/Warnings");
            pendItemAppTitle = new Text("Pending Items Apps");
            blackListTitle = new Text("Blacklisted Item");
            
            searchBar = new TextField();
            
            pendAppTitle.setFont(Font.font("Segoe UI Bold",25));
            reportTitle.setFont(Font.font("Segoe UI Bold",25));
            pendItemAppTitle.setFont(Font.font("Segoe UI Bold",25));
            blackListTitle.setFont(Font.font("Segoe UI Bold",25));
            
            userAppImage = new Image("appImage.jpg");
            userAppView = new ImageView();
            userAppView.setImage(userAppImage);
            userAppView.setFitHeight(200);
            userAppView.setFitWidth(200);
            userAppView.setOnMouseClicked((MouseEvent e) -> {
                pendAppScene = new PendAppPage();
                window.setScene(pendAppScene);
            });
            
            itemAppImage = new Image("itemApp.png");
            itemAppView = new ImageView();
            itemAppView.setImage(itemAppImage);
            itemAppView.setFitHeight(200);
            itemAppView.setFitWidth(200);
            itemAppView.setPickOnBounds(true);
            itemAppView.setOnMouseClicked((MouseEvent) -> {
                pendItemScene = new PendItemPage();
                window.setScene(pendItemScene);
            });
            
            reportImage = new Image("reportImage.png");
            reportView = new ImageView();
            reportView.setImage(reportImage);
            reportView.setFitHeight(200);
            reportView.setFitWidth(200);
            reportView.setOnMouseClicked((MouseEvent) -> {
                pendReportScene = new ReportPage();
                window.setScene(pendReportScene);
            });
            
            bListImage = new Image("blackListImage.png");
            bListView = new ImageView();
            bListView.setImage(bListImage);
            bListView.setFitHeight(200);
            bListView.setFitWidth(200);
            bListView.setOnMouseClicked((MouseEvent) -> {
                bListScene = new BlackListPage();
                window.setScene(bListScene);
            });
            
            
            searchBtn = new Button("Search");
            searchBtn.setOnAction(event -> {
                if (DataManager.checkValidBListWord(searchBar.getText()) && !searchBar.getText().contentEquals("")) {
                    searchResultList = FXCollections.observableArrayList(DataManager.getListofSearchItems(searchBar.getText()));
                    searchResultListView = new ListView<>(searchResultList);
                    
                    GridPane ouSearchResults = new GridPane();
                    Scene ouSearchResultScene = new Scene(ouSearchResults, 700, 700);
                    
                    sceneTitle = new Text("<banner>NotAmazon logo<banner>");
                    
                    sceneTitle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent click) {
                            if (click.getClickCount() == 2) {
                                guMainScene = new GUMainPage();
                                window.setScene(guMainScene);
                            }
                        }
                    });
                    
                    searchResultListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent click) {
                            if (click.getClickCount() == 2) {
                                thisItem = searchResultListView.getSelectionModel().getSelectedItem().toString();
                                viewItemScene = new ViewItemPage();
                                window.setScene(viewItemScene);
                            }
                        }
                    });
                    
                    Button backBtn = new Button("Back");
                    
                    backBtn.setOnAction(e -> {
                        guMainScene = new GUMainPage();
                        window.setScene(guMainScene);
                    });
                    
                    searchResultListView.setPrefWidth(600);
                    searchResultListView.setPrefHeight(600);
                    searchResultListView.setOrientation(Orientation.VERTICAL);
                    ouSearchResults.setAlignment(Pos.BASELINE_CENTER);
                    
                    // New window (Stage)
                    Stage resultsWindow = new Stage();
                    resultsWindow.setScene(ouSearchResultScene);
                    
                    ouSearchResults.setHgap(10);
                    ouSearchResults.setVgap(10);
                    ouSearchResults.setPadding(new Insets(25, 25, 25, 25));
                    ouSearchResults.add(searchResultListView, 0, 1, 2, 1);
                    ouSearchResults.add(sceneTitle, 0, 0, 2, 1);
                    ouSearchResults.add(backBtn, 0, 2);
                    
                    resultsWindow.show();
                }
            });
            
            signOutBtn = new Button("Sign Out");
            signOutBtn.setOnAction(event -> {
                guMainScene = new GUMainPage();
                window.setScene(guMainScene);
            });
            
            
            
            layout.setAlignment(Pos.BASELINE_CENTER);
            layout.setHgap(10);
            layout.setVgap(10);
            layout.setPadding(new Insets(25, 25, 25, 25));
            //placing objects into scene
            layout.add(sceneTitle, 0, 0, 2, 1);
            layout.add(signOutBtn, 3, 1);
            layout.add(searchBar, 0, 1, 2, 1);
            layout.add(pendAppTitle, 0, 3, 2, 1);
            layout.add(userAppView, 0, 6, 2, 1);
            layout.add(reportTitle, 0, 10, 2, 1);
            layout.add(reportView, 0, 13, 2, 1);
            layout.add(searchBtn, 2, 1, 1, 1);
            layout.add(pendItemAppTitle, 2, 3, 2, 1);
            layout.add(itemAppView, 2, 6, 2, 1);
            layout.add(blackListTitle, 2, 10, 2, 1);
            layout.add(bListView, 2, 13, 2, 1);
        }
    }
    
    class PendAppPage extends Scene{
        GridPane layout;
        Text sceneTitle;
        Button backBtn;
        Button viewBtn;
        ObservableList<String> listOfApp;
        ListView<String> appListView;
        Alert confirm;
        String [] userInfo;
        String user;
        String first;
        String last;
        String addr;
        String phone;
        String cc;
        
        public PendAppPage() {
            super(new GridPane(),700,700);
            layout = (GridPane)this.getRoot();
            listOfApp = FXCollections.observableArrayList(DataManager.getListOfApp());
            appListView = new ListView<>(listOfApp);
            sceneTitle = new Text("Pending User Applications");
            
            sceneTitle.setFont(Font.font("Segoe UI Bold",25));
            
            viewBtn = new Button("View");
            viewBtn.setOnAction(e -> {
                currentApp = appListView.getSelectionModel().getSelectedItem().toString();
                userInfo = DataManager.getUserApp(currentApp);
                user = userInfo[0];
                first = userInfo[1];
                last = userInfo[2];
                addr = userInfo[3];
                phone = userInfo[4];
                cc = userInfo[5];
                
                confirm = new Alert(AlertType.CONFIRMATION,
                                    "Username: "+user
                                    +"\nFirst Name: "+first
                                    +"\nLast Name: "+last
                                    +"\nAddress: "+addr
                                    +"\nPhone Number: "+phone
                                    +"\nCredit Card Number: "+cc
                                    +"\nApprove application?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
                
                if(appListView.getSelectionModel().getSelectedItem() != null){
                    confirm.showAndWait();
                    if(confirm.getResult() == ButtonType.YES) {
                        DataManager.addNewUser(currentApp);
                        DataManager.defaultUserPass(currentApp);
                        DataManager.deleteUserApp(currentApp);
                        pendAppScene = new PendAppPage();
                        window.setScene(pendAppScene);
                    }
                    else if(confirm.getResult() == ButtonType.NO) {
                        DataManager.deleteUserApp(currentApp);
                        pendAppScene = new PendAppPage();
                        window.setScene(pendAppScene);
                    }
                }
            });
            
            backBtn = new Button("Back");
            backBtn.setOnAction(event -> {
                suMainScene = new SUMainPage();
                window.setScene(suMainScene);
            });
            
            appListView.setPrefWidth(300);
            appListView.setPrefHeight(400);
            appListView.setOrientation(Orientation.VERTICAL);
            
            layout.setAlignment(Pos.BASELINE_CENTER);
            layout.setHgap(10);
            layout.setVgap(10);
            layout.setPadding(new Insets(25, 25, 25, 25));
            
            layout.add(sceneTitle, 0, 0, 2, 1);
            layout.add(appListView, 0, 1, 2, 1);
            layout.add(backBtn,0,2,2,1);
            layout.add(viewBtn, 2, 2, 2, 1);
        }
    }
    
    class PendItemPage extends Scene{
        GridPane layout;
        Text sceneTitle;
        Button backBtn;
        Button viewBtn;
        ObservableList<String> listOfItem;
        ListView<String> itemListView;
        Alert confirm;
        String [] itemInfo;
        String seller;
        String itemName;
        String price;
        String itemCondition;
        
        public PendItemPage() {
            super(new GridPane(),700,700);
            layout = (GridPane)this.getRoot();
            listOfItem = FXCollections.observableArrayList(DataManager.getListOfItemApp());
            itemListView = new ListView<>(listOfItem);
            sceneTitle = new Text("Pending Item Applications");
            
            sceneTitle.setFont(Font.font("Segoe UI Bold",25));
            
            viewBtn = new Button("View");
            viewBtn.setOnAction(e -> {
                currentApp = itemListView.getSelectionModel().getSelectedItem().toString();
                itemInfo = DataManager.getItemAppInfo(currentApp);
                itemName = itemInfo[0];
                price = itemInfo[1];
                itemCondition = itemInfo[2];
                seller = itemInfo[3];
                
                confirm = new Alert(AlertType.CONFIRMATION,
                                    "Item Name: "+itemName
                                    +"\nSeller: "+seller
                                    +"\nPrice: $"+price
                                    +"\nItem Condition: "+itemCondition
                                    +"\nApprove application?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
                if(itemListView.getSelectionModel().getSelectedItem() != null){
                    confirm.showAndWait();
                    if(confirm.getResult() == ButtonType.YES) {
                        DataManager.addNewItem(itemName);
                        DataManager.defaultBidPrice(itemName);
                        DataManager.deleteItemApp(itemName);
                        pendItemScene = new PendItemPage();
                        window.setScene(pendItemScene);
                    }
                    else if(confirm.getResult() == ButtonType.NO) {
                        DataManager.deleteItemApp(currentApp);
                        pendItemScene = new PendItemPage();
                        window.setScene(pendItemScene);
                    }
                }
            });
            
            backBtn = new Button("Back");
            backBtn.setOnAction(event -> {
                suMainScene = new SUMainPage();
                window.setScene(suMainScene);
            });
            
            layout.setAlignment(Pos.BASELINE_CENTER);
            layout.setHgap(10);
            layout.setVgap(10);
            layout.setPadding(new Insets(25, 25, 25, 25));
            
            layout.add(sceneTitle, 0, 0, 2, 1);
            layout.add(itemListView, 0, 1, 2, 1);
            layout.add(backBtn, 0, 2, 2, 1);
            layout.add(viewBtn, 2, 2, 2, 1);
            
        }
    }
    
    class ReportPage extends Scene{
        GridPane layout;
        Text sceneTitle;
        Button backBtn;
        ObservableList<String> listOfReport;
        ListView<String> reportListView;
        
        public ReportPage() {
            super(new GridPane(),700,700);
            layout = (GridPane)this.getRoot();
            listOfReport = FXCollections.observableArrayList();
            reportListView = new ListView<>(listOfReport);
            sceneTitle = new Text("Report Page");
            
            sceneTitle.setFont(Font.font("Segoe UI Bold",25));
            
            backBtn = new Button("Back");
            backBtn.setOnAction(event -> {
                suMainScene = new SUMainPage();
                window.setScene(suMainScene);
            });
            
            layout.setAlignment(Pos.BASELINE_CENTER);
            layout.setHgap(10);
            layout.setVgap(10);
            layout.setPadding(new Insets(25, 25, 25, 25));
            
            layout.add(sceneTitle, 0, 0, 2, 1);
            layout.add(reportListView, 0, 1, 2, 1);
            layout.add(backBtn, 0, 2, 2, 1);
            
        }
    }
    
    class BlackListPage extends Scene{
        GridPane layout;
        Text sceneTitle;
        Text addTabooTitle;
        Text bListWord;
        TextField bListField;
        Button backBtn;
        Button addBtn;
        Button addWordBtn;
        Button removeBtn;
        ObservableList<String> blackList;
        ListView<String> blackListView;
        String currentWord;
        
        public BlackListPage() {
            super(new GridPane(),700,700);
            layout = (GridPane)this.getRoot();
            blackList = FXCollections.observableArrayList(DataManager.getListOfBListWords());
            blackListView = new ListView<>(blackList);
            sceneTitle = new Text("Black List Page");
            addTabooTitle = new Text("Add to List");
            bListWord = new Text("Blacklist Word: ");
            
            sceneTitle.setFont(Font.font("Segoe UI Bold",25));
            addTabooTitle.setFont(Font.font("Segoe UI Bold",25));
            
            bListField = new TextField();
            
            backBtn = new Button("Back");
            addBtn = new Button("Add");
            removeBtn = new Button("Remove");
            addWordBtn = new Button("Add");
            
            backBtn.setOnAction(event -> {
                suMainScene = new SUMainPage();
                window.setScene(suMainScene);
            });
            
            addBtn.setOnAction(event -> {
                GridPane tabooLayout = new GridPane();
                Scene tabooScene = new Scene(tabooLayout, 400, 200);
                
                Stage tabooWindow = new Stage();
                tabooWindow.setTitle("Blacklisting Word");
                tabooWindow.setScene(tabooScene);
                
                tabooLayout.setAlignment(Pos.BASELINE_CENTER);
                tabooLayout.setHgap(10);
                tabooLayout.setVgap(10);
                tabooLayout.setPadding(new Insets(25, 25, 25, 25));
                tabooLayout.add(addTabooTitle, 2, 0, 2, 1);
                tabooLayout.add(bListWord, 0, 1, 2, 1);
                tabooLayout.add(bListField, 2, 1, 2, 1);
                tabooLayout.add(addWordBtn, 0, 3, 1, 2);
                
                tabooWindow.show();
            });
            
            removeBtn.setOnAction(event -> {
                currentWord = blackListView.getSelectionModel().getSelectedItem().toString();
                DataManager.deleteBListWord(currentWord);
                bListScene = new BlackListPage();
                window.setScene(bListScene);
            });
            
            addWordBtn.setOnAction(event -> {
                if(DataManager.checkValidBListWord(bListField.getText())) {
                    DataManager.addBListWord(bListField.getText());
                    Alert success = new Alert(AlertType.INFORMATION);
                    success.setTitle("Valid Word");
                    success.setHeaderText(null);
                    success.setContentText("Valid word.");
                    success.showAndWait();
                }
                else {
                    Alert fail = new Alert(AlertType.ERROR);
                    fail.setTitle("Error");
                    fail.setHeaderText(null);
                    fail.setContentText("Error! Word not added.");
                    fail.showAndWait();
                }
            });
            
            addBtn.setAlignment(Pos.BOTTOM_RIGHT);
            
            layout.setAlignment(Pos.BASELINE_CENTER);
            layout.setHgap(10);
            layout.setVgap(10);
            layout.setPadding(new Insets(25, 25, 25, 25));
            
            layout.add(sceneTitle, 0, 0, 2, 1);
            layout.add(blackListView, 0, 1, 2, 1);
            layout.add(backBtn, 0, 3, 2, 1);
            layout.add(removeBtn, 0, 2, 2, 1);
            layout.add(addBtn, 3, 2, 2, 1);
        }
    }
    
    class SellItemAppPage extends Scene{
        GridPane layout;
        Text sellTitle;
        Button browseBtn;
        Button backBtn;
        Button submitBtn;
        FileChooser fileChooser;
        File upload;
        Image item;
        ImageView itemupload;
        Label itemLabel;
        Label item_typeLabel;
        Label priceLabel;
        Label item_conditionLabel;
        Label timeLabel;
        Label attachImageLabel;
        TextField itemTF;
        TextField item_typeTF;
        TextField priceTF;
        TextField item_conditionTF;
        TextField timeTF;
        DecimalFormat money;
        DecimalFormat time;
        Alert confirm;
        Alert error;
        String imageAddr;
        
        public SellItemAppPage() {
            super(new GridPane(),700,700);
            layout = (GridPane)this.getRoot();
            sellTitle = new Text("Sell/Auction");
            sellTitle.setFont(Font.font("Segoe UI Bold",25));
            browseBtn = new Button("Upload");
            backBtn = new Button("Back");
            submitBtn = new Button("Submit");
            
            attachImageLabel = new Label("Attach image of item:");
            itemLabel = new Label("Item Name:");
            item_typeLabel = new Label("Sell/Auction?");
            priceLabel = new Label("Price:");
            item_conditionLabel = new Label("Item Condition:");
            timeLabel = new Label("Time (in minutes):");
            
            itemTF = new TextField();
            item_typeTF = new TextField();
            item_typeTF.setPromptText("Type \"Sell\" or \"Auction\"");
            priceTF = new TextField();
            item_conditionTF = new TextField();
            timeTF = new TextField();
            
            money = new DecimalFormat("0.00");
            money.setMinimumFractionDigits(2);
            money.setMaximumFractionDigits(2);
            money.setRoundingMode(RoundingMode.DOWN);
            
            time = new DecimalFormat("#0");
            
            //fileChooser
            fileChooser = new FileChooser();
            fileChooser.setTitle("Uploading image...");
            fileChooser.getExtensionFilters().addAll(
                                                     new FileChooser.ExtensionFilter("JPG Files", "*.jpg"),
                                                     new FileChooser.ExtensionFilter("PNG Files", "*.png")
                                                     );
            //fileChooser
            timeTF.setTextFormatter(new TextFormatter<>(c ->{
                if ( c.getControlNewText().isEmpty() )
                {
                    return c;
                }
                
                ParsePosition parsePosition = new ParsePosition( 0 );
                Object object = time.parse( c.getControlNewText(), parsePosition );
                
                if ( object == null || parsePosition.getIndex() < c.getControlNewText().length() )
                {
                    return null;
                }
                else
                {
                    return c;
                }
            }));
            
            priceTF.setTextFormatter(new TextFormatter<>(c ->{
                if ( c.getControlNewText().isEmpty() )
                {
                    return c;
                }
                
                ParsePosition parsePosition = new ParsePosition( 0 );
                Object object = money.parse( c.getControlNewText(), parsePosition );
                
                if ( object == null || parsePosition.getIndex() < c.getControlNewText().length() )
                {
                    return null;
                }
                else
                {
                    return c;
                }
            }));
            
            browseBtn.setOnAction(e->{
                upload = fileChooser.showOpenDialog(browseBtn.getScene().getWindow());
                if(upload != null) {
                    imageAddr = upload.toURI().toString();
                    item = new Image(imageAddr);
                    itemupload = new ImageView();
                    itemupload.setImage(item);
                    itemupload.setFitHeight(200);
                    itemupload.setFitWidth(200);
                    layout.add(itemupload, 0, 2, 2, 1);
                }
            });
            
            backBtn.setOnAction(e->{
                window.setScene(ouMainScene);
            });
            
            submitBtn.setOnAction(e->{
                if(upload == null) {
                    error = new Alert(AlertType.WARNING,"Please upload an image of your item.",ButtonType.OK);
                    error.showAndWait();
                }
                else if(itemTF.getText().isEmpty() || item_typeTF.getText().isEmpty() ||
                        priceTF.getText().isEmpty() || item_conditionTF.getText().isEmpty() ||
                        timeTF.getText().isEmpty()) {
                    error = new Alert(AlertType.WARNING,"An empty field has been detected. Please try again.",ButtonType.OK);
                    error.setTitle("Empty Field Error");
                    error.showAndWait();
                }
                else if(!item_typeTF.getText().toLowerCase().equals("sell")
                        && !item_typeTF.getText().toLowerCase().equals("auction")) {
                    
                    error = new Alert(AlertType.WARNING,"Invalid input, must be either \"Sell\" or \"Auction\"",ButtonType.OK);
                    error.setTitle("Invalid Input");
                    error.showAndWait();
                }
                else {
                    double priceDouble=0;
                    int timeInt=0;
                    try {
                        priceDouble = Double.parseDouble(priceTF.getText());
                        priceDouble = Double.parseDouble(money.format(priceDouble));
                        timeInt= Integer.parseInt(timeTF.getText());
                    }catch(NumberFormatException x){
                        x.printStackTrace();
                    }
                    confirm = new Alert(AlertType.CONFIRMATION,
                                        "Please confirm your item application."
                                        +"\nItem Name: "+itemTF.getText()
                                        +"\nSeller: "+thisUser
                                        +"\nItem Type: "+item_typeTF.getText()
                                        +"\nPrice: $"+money.format(priceDouble)
                                        +"\nItem Condition: "+item_conditionTF.getText()
                                        +"\nTime: "+timeTF.getText()
                                        +"\nItem Image Location: "+imageAddr
                                        +"\nAre you sure?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
                    confirm.showAndWait();
                    if(confirm.getResult() == ButtonType.YES){
                        if(item_typeTF.getText().toLowerCase().equals("sell")) {
                            DataManager.itemApplication(thisUser, itemTF.getText()
                                                        ,0,priceDouble,item_conditionTF.getText(),timeInt,imageAddr);
                        }
                        if(item_typeTF.getText().toLowerCase().equals("auction")) {
                            DataManager.itemApplication(thisUser, itemTF.getText()
                                                        ,1,priceDouble,item_conditionTF.getText(),timeInt,imageAddr);
                        }
                        ouMainScene = new OUMainPage();
                        window.setScene(ouMainScene);
                    }
                }
            });
            
            submitBtn.setAlignment(Pos.BOTTOM_RIGHT);
            
            layout.setAlignment(Pos.BASELINE_CENTER);
            layout.setHgap(10);
            layout.setVgap(10);
            layout.setPadding(new Insets(25, 25, 25, 25));
            
            layout.add(sellTitle, 0, 0, 2, 1);
            layout.add(attachImageLabel,0,1);
            layout.add(browseBtn, 1, 1, 2, 1);
            layout.add(itemLabel, 0, 3);
            layout.add(itemTF, 1, 3);
            layout.add(item_typeLabel, 0, 4);
            layout.add(item_typeTF, 1, 4);
            layout.add(priceLabel, 0, 5);
            layout.add(priceTF, 1, 5);
            layout.add(item_conditionLabel, 0, 6);
            layout.add(item_conditionTF, 1, 6);
            layout.add(timeLabel, 0, 7);
            layout.add(timeTF, 1, 7);
            layout.add(submitBtn, 1, 8);
            layout.add(backBtn, 0, 8);
        }
    }
}
