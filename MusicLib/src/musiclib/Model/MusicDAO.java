package musiclib.Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;

public class MusicDAO {
    static String DATABASE_URL;
    static String DATABASE_NAME;
    static String USERNAME;
    static String PASSWORD;

    public MusicDAO(String db_url, String db_name, String db_user, String db_pass){
        DATABASE_URL = db_url;
        DATABASE_NAME = db_name;
        USERNAME = db_user;
        PASSWORD = db_pass;
    }

    private static Connect conexao = new Connect();

    public void createTable() throws SQLException {
        if(conexao.connect() != null) try {
            conexao.connect();
            Statement stmt = conexao.createStmt();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Music ("
                    + "ID       int(11) Primary Key AUTO_INCREMENT, "
                    + "Nome     varchar(20)     Not Null, "
                    + "Artista  varchar(20)     Not Null, "
                    + "ano      int(4)          Not Null, "
                    + "Midia    varchar(20)     Not Null, "
                    + "Genero     varchar(20)   Not Null)");
            conexao.disconnect();
        } catch (SQLException e2) {
            System.out.println(e2.getLocalizedMessage());
            System.out.println("criaTabelaSeNaoExiste");
        }
    }

    public List<Music> selectAll() throws SQLException {
        List<Music> musics = new ArrayList<>();
        if(conexao.connect() != null) try {
            conexao.connect();
            Statement stmt = conexao.createStmt();
            ResultSet rs = stmt.executeQuery("Select * From Music");
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String artista = rs.getString("artista");
                int ano = rs.getInt("ano");
                String midia = rs.getString("midia");
                String genero = rs.getString("genero");
                musics.add(new Music(id, nome, artista, ano, midia, genero));
            }
            rs.close();
            conexao.disconnect();
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
            System.out.println("selectAll");
        }
        return musics;
    }


    public List<Music> search(String name) throws SQLException {
        List<Music> music = new ArrayList<>();
        if(conexao.connect() != null) try {
            conexao.connect();
            Statement stmt = conexao.createStmt();
            ResultSet rs = stmt.executeQuery(String.format("SELECT * From Music WHERE Nome like '%%%s%%'", name));
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String artista = rs.getString("artista");
                int ano = rs.getInt("ano");
                String midia = rs.getString("midia");
                String genero = rs.getString("genero");
                music.add(new Music(id, nome, artista, ano, midia, genero));
            }
            rs.close();
            conexao.disconnect();
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
            System.out.println("search");
        }
        return music;
    }

    public boolean save(String nome, String artista, int ano, String midia, String genero) throws SQLException {
        boolean retorno = false;
        if(conexao.connect() != null) try {
            conexao.connect();
            Statement stmt = conexao.createStmt();
            stmt.executeUpdate(String.format(
                    "Insert into Music (nome, artista, ano, midia, genero) "
                            + "values ('%s', '%s', '%d', '%s', '%s')", nome, artista, ano, midia, genero));

            stmt.close();
            conexao.disconnect();
            retorno = true;
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
            System.out.println("save");
        }
        return retorno;
    }

    public boolean delete(int id) throws SQLException {
        boolean retorno = false;
        if(conexao.connect() != null) try {
            conexao.connect();
            Statement stmt = conexao.createStmt();
            stmt.executeUpdate(String.format(
                    "Delete From Music Where id = %d", id));

            stmt.close();
            conexao.disconnect();
            retorno = true;
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
            System.out.println("delete");
        }
        return retorno;
    }

    public boolean update(int id, String nome, String artista, int ano, String midia, String genero) throws SQLException {
        boolean retorno = false;
        if(conexao.connect() != null) {
            try {
                conexao.connect();
                Statement stmt = conexao.createStmt();
                stmt.executeUpdate(String.format("Update Music "
                        + "set nome  = '%s', artista = '%s', ano = '%d', midia = '%s', genero = '%s' "
                        + "where id = %d", nome, artista, ano, midia, genero, id));

                stmt.close();
                conexao.disconnect();
                retorno = true;
            } catch (SQLException e) {
                System.out.println(e.getLocalizedMessage());
                System.out.println("save");
            }
        }
        return retorno;
    }

}