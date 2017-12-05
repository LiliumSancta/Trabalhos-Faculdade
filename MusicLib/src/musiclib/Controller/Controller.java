package musiclib.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import musiclib.Model.Connect;
import musiclib.Model.Music;
import musiclib.Model.MusicDAO;
import musiclib.Model.Utils;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.IntStream;


public class Controller {

    @FXML private TextField musica;
    @FXML private TextField artista;
    @FXML private TextField ano;
    @FXML private ChoiceBox<String> genero;
    @FXML private ChoiceBox<String> midia;
    @FXML private TextField search;
    @FXML private Button btn_del;
    @FXML private Button btn_search;
    @FXML private Label labelindexsearch;
    @FXML private Label labelindexsearch2;
    @FXML private Button btn_search_next;
    @FXML private Button btn_search_before;
    @FXML private Button btn_save;
    @FXML private Button btn_edit;
    @FXML private Button btn_first;
    @FXML private Button btn_next;
    @FXML private Button btn_before;
    @FXML private Button btn_last;
    @FXML private TextField musica_add;
    @FXML private TextField artista_add;
    @FXML private TextField ano_add;
    @FXML private ChoiceBox<String> genero_add;
    @FXML private ChoiceBox<String> midia_add;
    @FXML private ColorPicker colorpickertext;
    @FXML private ColorPicker colorpickericon;
    @FXML private ColorPicker colorpickerbg;
    @FXML private AnchorPane anchorpane;
    @FXML private TabPane tabPane;
    @FXML private TextField user;
    @FXML private TextField pass;
    @FXML private TextField database;
    @FXML private TextField host;

    private List<Music> musicas;
    private int index = 0;
    private int[] index_search;
    private int index_s = 0;

    @FXML
    private void initialize() throws SQLException {
        user.setText("root");
        pass.setText("vertrigo");
        database.setText("test");
        host.setText("localhost");
        colorpickertext.setValue((Color.valueOf("#000000")));
        colorpickericon.setValue((Color.valueOf("#ff0000")));
        colorpickerbg.setValue((Color.valueOf("#f2f2f2")));

        createMenus();
        getMusics();
        updateInterface();

        if(!conectionTest()){
            SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
            selectionModel.select(2);

        }else if(musicas.isEmpty()){
            SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
            selectionModel.select(1);
        }
    }

    private void updateInterface(){

        musica.setDisable(true);
        artista.setDisable(true);
        ano.setDisable(true);
        btn_save.setDisable(true);

        musica_add.setText("");
        artista_add.setText("");
        ano_add.setText(null);
        genero_add.setValue("");
        midia_add.setValue("");

        labelindexsearch.setText("");

        if(!musicas.isEmpty()) {
            musica.setText(musicas.get(index).getNome());
            artista.setText(musicas.get(index).getArtista());
            ano.setText(String.valueOf(musicas.get(index).getAno()));
            genero.setValue(musicas.get(index).getGenero());
            midia.setValue(musicas.get(index).getMidia());

            search.setDisable(false);

            labelindexsearch.setText(index_search != null && index_search.length > 0 ? String.valueOf(index_s+1) : "");
            btn_search_next.setDisable(index_search == null || index_search.length <= 0);
            btn_search_before.setDisable(index_search == null || index_search.length <= 0);

            btn_del.setDisable(false);
            btn_search.setDisable(false);
            btn_edit.setDisable(false);

            btn_first.setDisable(false);
            btn_next.setDisable(false);
            btn_before.setDisable(false);
            btn_last.setDisable(false);

        }else{
            musica.setText("");
            artista.setText("");
            ano.setText(null);
            genero.setValue("");
            midia.setValue("");

            search.setDisable(true);

            btn_save.setDisable(true);
            btn_del.setDisable(true);
            btn_search.setDisable(true);
            btn_search_next.setDisable(true);
            btn_search_before.setDisable(true);
            btn_edit.setDisable(true);

            btn_first.setDisable(true);
            btn_next.setDisable(true);
            btn_before.setDisable(true);
            btn_last.setDisable(true);

        }

        if(!conectionTest()){
            Utils.printMsg("Não foi possivel conectar ao banco de dados!", "Conexão", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void musicDelete() throws SQLException {
        if(!musicas.isEmpty()) {
            MusicDAO rs = new MusicDAO(host.getText(), database.getText(), user.getText(), pass.getText());
            rs.delete(musicas.get(index).getId());
        }

        getMusics();
        firtMusic();
        updateInterface();
    }

    @FXML
    private void musicSearch(){
        String find = search.getText();
        index_search = IntStream.range(0, musicas.size())
                .filter(i -> musicas.get(i).getNome().matches(".*"+find+".*"))
                .toArray();

        if(index_search.length > 0) {
            index = index_search[index_s];
        }
        labelindexsearch2.setText(String.valueOf(index_search.length) + " Resultados.....");
        updateInterface();
    }

    @FXML
    private void searchNext(){
        if (index_s < index_search.length - 1) {
            index_s++;
            index = index_search[index_s];
        }else{
            index = index_search[index_s];
        }
        updateInterface();
    }

    @FXML
    private void searchBefore(){
        if (index_s > 0) {
            index_s--;
            index = index_search[index_s];
        }else{
            index = index_search[index_s];
        }
        updateInterface();
    }

    @FXML
    private void musicEdit(){
        musica.setDisable(false);
        artista.setDisable(false);
        ano.setDisable(false);
        btn_save.setDisable(false);
    }

    @FXML
    private void musicSave() throws SQLException {
        MusicDAO rs = new MusicDAO(host.getText(), database.getText(), user.getText(), pass.getText());
        rs.update(musicas.get(index).getId(), musica.getText(),artista.getText(), Integer.valueOf(ano.getText()), String.valueOf(midia.getValue()), String.valueOf(genero.getValue()));
        getMusics();
        firtMusic();
        updateInterface();
    }


    @FXML
    private void musicAdd() throws SQLException {
        MusicDAO rs = new MusicDAO(host.getText(), database.getText(), user.getText(), pass.getText());
        rs.save(musica_add.getText(),artista_add.getText(), Integer.valueOf(ano_add.getText()), String.valueOf(midia_add.getValue()), String.valueOf(genero_add.getValue()));
        getMusics();
        updateInterface();
    }

    @FXML
    private void firtMusic() {
        this.index = 0;
        updateInterface();
    }

    @FXML
    private void lastMusic() {
        this.index = musicas.size() - 1;
        updateInterface();
    }

    @FXML
    private void nextMusic() {
        if (this.index < musicas.size() - 1) {
            this.index += 1;
        }
        updateInterface();
    }

    @FXML
    private void beforeMusic() {
        if (this.index > 0) {
            this.index -= 1;
        }
        updateInterface();
    }

    @FXML
    private void saveSetings() throws SQLException {

        Color textfill = colorpickertext.getValue();
        Color iconfill = colorpickericon.getValue();
        Color backfill = colorpickerbg.getValue();

        getMusics();
        updateInterface();
        if(!conectionTest()){
            Utils.printMsg("Não foi possivel conectar ao banco de dados!", "Conexão", Alert.AlertType.INFORMATION);
        }
        tabPane.setStyle("-fx-background-color: "+ Utils.toRgbString(backfill) +";");
        anchorpane.setStyle("colorbuttons: "+ Utils.toRgbString(iconfill) +"; coloricons: "+ Utils.toRgbString(iconfill) +"; fontcolor: "+ Utils.toRgbString(textfill) +";");

    }

    private void getMusics() {
        try {
            MusicDAO rs = new MusicDAO(host.getText(), database.getText(), user.getText(), pass.getText());
            rs.createTable();
            this.musicas = rs.selectAll();
        }catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
                System.out.println("getMusics");
            }
    }


    private void createMenus() {
        ObservableList<String> genderoptions = FXCollections.observableArrayList("Folk", "Rock", "Pop", "Gospel", "Funk", "Sertanejo", "Samba", "Pagode", "Jazz", "Rap");
        genero.setItems(genderoptions);
        genero.setValue("Rock");
        genero_add.setItems(genderoptions);
        //genero_add.setValue("Rock");

        ObservableList<String> mediaoptions = FXCollections.observableArrayList("Itunes", "Spotfy", "CD/DVD", "Google Play Music", "Youtube");
        midia.setItems(mediaoptions);
        midia.setValue("Itunes");
        midia_add.setItems(mediaoptions);
        //midia_add.setValue("Itunes");

    }

    private boolean conectionTest(){
        Connect conexao = new Connect();
        return conexao.connect() != null;
    }

}
