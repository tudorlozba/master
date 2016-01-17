package Client.GUI;

import Client.Controller.PostsController;
import Client.Controller.UserController;
import Client.SocialNetwork;
import Common.domain.Post;
import Common.domain.PostDto;
import Common.domain.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;

/**
 * Created by tudor on 15/01/16.
 */
public class SocialNetworkMainView extends GridPane {


    public static final int SUTA = 100;
    public static final int SUTA5 = 150;
    public static final int HBoxSpacing = 25;
    private final Log logger = LogFactory.getLog(SocialNetworkMainView.class);

    private Button deleteArtistButton;
    private Button sortArtistButton;
    private Button updateArtistButton;
    private TextField idUser;
    private TextField nameTextField;

    private TextField postOwner;
    private TextField postContent;


    private UserController userController;
    private PostsController postsController;
    private ObservableList<User> observableUsersList;

    private ObservableList<Post> observablePostsList;
    private ObservableList<PostDto> observableWorksDtoList;
    private TableView<User> usersTable;
    private TableView<Post> postsTable;
    private TableView<PostDto> postsDtoTable;

    private RadioButton radioButtonByName;
    private RadioButton radioButtonByBirth;
    private RadioButton radioButtonByDeath;

    private VBox vBoxForOperas;
    private RadioButton radioButtonByTitle;
    private RadioButton radioButtonByTip;
    private Button sortWorksButton;
    private TextField titleTextField;

    private ComboBox<String> typeComboBox;
    private Button deleteWorkButton;
    private Button updateWorkButton;
    private RadioButton radioButtonUnsorted;
    private RadioButton radioButtonArtistsUnsorted;


    public SocialNetworkMainView(SocialNetwork socialNetwork, UserController userController, PostsController postsController) {
        this.userController = userController;
        this.postsController = postsController;

        if (socialNetwork == null) logger.info("null");

        setWidth(700);

        VBox vBoxForUsers = new VBox();
        VBox vBoxForPosts = new VBox();

        ObservableList<Node> vBoxForUsersChildren = vBoxForUsers.getChildren();
        ObservableList<Node> vBoxForPostsChildren = vBoxForPosts.getChildren();

        setUsersTable();

        Label labelUsers = new Label("Users");
        labelUsers.setFont(new Font("Arial", 20));
        labelUsers.setAlignment(Pos.CENTER);
        vBoxForUsersChildren.add(labelUsers);
        vBoxForUsersChildren.add(usersTable);
        vBoxForUsers.setMaxWidth(150);


        setPostsTable();

        Label labelPosts = new Label("News feed");
        labelPosts.setFont(new Font("Arial", 20));
        labelPosts.setAlignment(Pos.CENTER);
        vBoxForPostsChildren.add(labelPosts);
        vBoxForPostsChildren.add(postsTable);
        vBoxForPosts.setMinWidth(550);

        add(vBoxForUsers,0,0);
        add(vBoxForPosts,1,0);


        fetchPosts();
        fetchUsers();
    }

    private void fetchUsers() {

        logger.info("fetching works...");
        Service<ArrayList<User>> fetchService = UserController.fetchUsersService();
        Dialog<Boolean> loading = AlertUtils.cancellableDialog("Loading", "Please wait ...");
        loading.setOnCloseRequest(event -> fetchService.cancel());
        loading.show();

        fetchService.setOnSucceeded(e -> { // prepare what to do when the call succeeds
            logger.info("fetch works service succeeded"); // executed on app thread
            //ObservableList<Node> children = vBoxForOperas.getChildren();
            //if (children.contains(worksDtoTable)) children.set(children.indexOf(worksDtoTable), worksTable);
            observableUsersList.clear();
            observableUsersList.addAll(fetchService.getValue());
            loading.close();
        });
        fetchService.setOnFailed(e -> { // prepare what to do when the call fails
            Throwable exception = e.getSource().getException();
            logger.warn("fetch works service failed", exception);
            loading.close();
            AlertUtils.showError(exception);
        });
        fetchService.setOnCancelled(e -> { // prepare what to do when the call was cancelled
            logger.info("fetch works service cancelled");
            loading.close();
        });
        logger.info("starting fetch works service");
        fetchService.start(); // start the async call/task (from app thread)
        // the task is executed on background threads
        
        
    }

    private void fetchPosts() {
        logger.info("fetching works...");
        Service<ArrayList<Post>> fetchService = PostsController.fetchPostsService();
        Dialog<Boolean> loading = AlertUtils.cancellableDialog("Loading", "Please wait ...");
        loading.setOnCloseRequest(event -> fetchService.cancel());
        loading.show();
        fetchService.setOnSucceeded(e -> { // prepare what to do when the call succeeds
            logger.info("fetch works service succeeded"); // executed on app thread
            observablePostsList.clear();
            observablePostsList.addAll(fetchService.getValue());
            loading.close();
        });
        fetchService.setOnFailed(e -> { // prepare what to do when the call fails
            Throwable exception = e.getSource().getException();
            logger.warn("fetch works service failed", exception);
            loading.close();
            AlertUtils.showError(exception);
        });
        fetchService.setOnCancelled(e -> { // prepare what to do when the call was cancelled
            logger.info("fetch works service cancelled");
            loading.close();
        });
        logger.info("starting fetch works service");
        fetchService.start(); // start the async call/task (from app thread)
        // the task is executed on background threads
    }

    private void setPostsTable() {
        postsTable = new TableView<>();
        observablePostsList = FXCollections.observableArrayList(new ArrayList<>());
        postsTable.getSelectionModel().selectedItemProperty().addListener(
                (lv, oldValue, newValue) -> {
                    logger.info(String.format("list view, old sel %s, new sel %s", oldValue, newValue));

                    if (newValue != null) {

                        postOwner.setText(String.valueOf(newValue.getUser()));
                        postContent.setText(String.valueOf(newValue.getContent()));
                    } else {
                        postOwner.setText(String.valueOf(newValue.getUser()));
                        postContent.setText(String.valueOf(newValue.getContent()));
                    }
                }
        );
        postsTable.setItems(observablePostsList);
        postsTable.setEditable(false);
        setColumnsForPostsTable();


    }

    private void setUsersTable() {

            usersTable = new TableView<>();
            observableUsersList = FXCollections.observableArrayList(new ArrayList<>());
            usersTable.getSelectionModel().selectedItemProperty().addListener(
                    (lv, oldValue, newValue) -> {
                        logger.info(String.format("list view, old sel %s, new sel %s", oldValue, newValue));
                        //enableButton(deleteArtistButton);
                        //enableButton(updateArtistButton);
                        if (newValue != null) {
                            nameTextField.setText(String.valueOf(newValue.getName()));
                        } else {
                            idUser.setText(String.valueOf(newValue.getId()));
                            nameTextField.setText("");
                        }
                    }
            );
            usersTable.setItems(observableUsersList);
            usersTable.setEditable(false);
            setColumnsForUsersTable();

    }

    private void setColumnsForPostsTable() {
        TableColumn userCol = new TableColumn("User");
        TableColumn contentCol = new TableColumn("Content");
        userCol.setMinWidth(50);
        userCol.setCellValueFactory(new PropertyValueFactory<User, Integer>("user"));
        userCol.setPrefWidth(50);
        contentCol.setCellValueFactory(new PropertyValueFactory<User, String>("content"));
        contentCol.setPrefWidth(500);
        postsTable.getColumns().addAll(userCol, contentCol);
    }

    private void setColumnsForUsersTable() {
        TableColumn nameCol = new TableColumn("Name");
        nameCol.setMinWidth(150);
        nameCol.setCellValueFactory(new PropertyValueFactory<User, String>("name"));

        usersTable.getColumns().addAll(nameCol);
    }

}