package Client.GUI;

import Client.Controller.PostsController;
import Client.Controller.UserController;
import Client.SocialNetwork;
import Common.domain.Post;
import Common.domain.PostDto;
import Common.domain.User;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by tudor on 15/01/16.
 */
public class SocialNetworkMainView extends HBox {


    public static final int SUTA = 100;
    public static final int SUTA5 = 150;
    public static final int HBoxSpacing = 25;
    private final Log logger = LogFactory.getLog(SocialNetworkMainView.class);

    private Button deleteArtistButton;
    private Button sortArtistButton;
    private Button updateArtistButton;
    private TextField nameTextField;


    private UserController userController;
    private PostsController postsController;
    private ObservableList<User> observableArtistsList;

    private ObservableList<Post> observableWorksList;
    private ObservableList<PostDto> observableWorksDtoList;
    private TableView<User> artistsTable;
    private TableView<Post> worksTable;
    private TableView<PostDto> worksDtoTable;

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

    }

}