package szalaimihaly.hu.worshipsearcher;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Mihaly on 2015.10.03..
 */
public class WebDbLoader {
    private Context ctx;
    private DbLoader dbl;
    private Connection connection;

    public WebDbLoader(Context ctx) {
        this.ctx = ctx;
        dbl = new DbLoader(ctx);
        dbl.open();
        Log.d("sh", "webloader");
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e1) {
            Log.d("sh", e1.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean setConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://oziris3.inf.nyme.hu:3306/churchadmin", "szalaim", "GA5371evIN");
            Log.d("sh", "csatalkoztatva");
            return true;
        } catch (SQLException e) {
            Log.d("sh", e.getMessage());
            return false;
        } catch (Exception e) {
            Log.d("sh", e.getMessage());
            return false;
        }
    }

    public DbLoader getDbl() {
        return this.dbl;
    }

    public boolean isdDataInDb() {
        boolean isDataInClient = true;
        if (dbl.getAllChurch().size() == 0) {
            isDataInClient = false;
        }
        if (dbl.getAllWeek().size() == 0) {
            isDataInClient = false;
        }
        if (dbl.getAllWroship().size() == 0) {
            isDataInClient = false;
        }
        if (!isDataInClient) {
            getDbl().deleteAllDataFromDb();
        }
        return isDataInClient;
    }

    public void getAllChurches() {
        if(connection!=null) {
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select * from churches");
                while (resultSet.next()) {
                    //Log.d("sh",resultSet.getInt("churchid") + " " + resultSet.getInt("conid"));
                    Church church = new Church(resultSet.getInt("churchid"), resultSet.getInt("conid"), resultSet.getString("city"), resultSet.getString("address"), resultSet.getDouble("lat"), resultSet.getDouble("lon"), resultSet.getString("comment"));
                    //Log.d("sh",church.toString());
                    dbl.createChurch(church);
                }
            } catch (SQLException e) {
                Log.d("sh", e.getMessage());
            } catch (NullPointerException e) {
                Log.d("sh", "Rossz hálózat");
            }
        }
    }

    public void getAllWorships() {
        if (connection!=null) {
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select * from worships");
                while (resultSet.next()) {
                    Worship worship = new Worship(resultSet.getInt("worshipid"), resultSet.getInt("churchid"), resultSet.getString("termin"), resultSet.getInt("weekid"), resultSet.getString("comment"));
                    //Log.d("sh", worship.toString());
                    worship.setTermin(worship.getTermin().substring(0, worship.getTermin().length() - 3));
                    dbl.createWorship(worship);
                }
            } catch (SQLException e) {
                Log.d("sh", e.getMessage());
            }
        }
    }

    public void getAllWeeks() {
        if (connection!=null) {
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select * from weeks");
                while (resultSet.next()) {
                    Week week = new Week(resultSet.getInt("weekid"), resultSet.getString("type"));
                    dbl.createWeek(week);
                }
            } catch (SQLException e) {
                Log.d("sh", e.getMessage());
            }
        }
    }

    public void closeConnection() {
        try {
            dbl.setUpdatedTime();
            Log.d("sh","closed connection");
            this.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            Log.d("sh", "Rossz hálózat");
        }
    }


    public void deleteOldWorships() {
        if(connection!=null) {
            Log.d("sh", "törlés");
            String updated = this.dbl.getUpdatedTime();
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select * from deletedworship where deleted > \'" + updated + "\'");
                while (resultSet.next()) {
                    Log.d("sh", "törlõ ciklus");
                    //Log.d("sh", ((Integer) resultSet.getInt(0)).toString() + " " + resultSet.getString(resultSet.getString(1)));
                    dbl.deleteWorshipById(resultSet.getInt("worshipid"));
                }
            } catch (SQLException e) {
                Log.d("sh", e.getMessage());
            }
        }
    }


    public void getNewWorships() {
        if(connection!=null) {
            Log.d("sh", "új beszúrás");
            String updated = this.dbl.getUpdatedTime();
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select * from worships where updated > \'" + updated + "\'");
                Log.d("sh", ((Integer) resultSet.getFetchSize()).toString());
                while (resultSet.next()) {
                    Log.d("sh", "beszúró ciklus");
                    if (!dbl.isWorshipExists(resultSet.getInt("worshipid"))) {
                        Worship worship = new Worship(resultSet.getInt("worshipid"), resultSet.getInt("churchid"), resultSet.getString("termin"), resultSet.getInt("weekid"), resultSet.getString("comment"));
                        if (worship.getComment().equals("undefined")) {
                            worship.setComment("");
                        }
                        worship.setTermin(worship.getTermin().substring(0, worship.getTermin().length() - 3));
                        dbl.createWorship(worship);
                    } else {
                        Worship worship = new Worship(resultSet.getInt("worshipid"), resultSet.getInt("churchid"), resultSet.getString("termin"), resultSet.getInt("weekid"), resultSet.getString("comment"));
                        if (worship.getComment().equals("undefined")) {
                            worship.setComment("");
                        }
                        worship.setTermin(worship.getTermin().substring(0, worship.getTermin().length() - 3));
                        dbl.modifieWorshipById(worship);
                    }
                }
            } catch (SQLException e) {
                Log.d("sh", e.getMessage());
            }
        }
    }



    public void getNewChurches() {
        if (connection != null) {
            String updated = this.dbl.getUpdatedTime();
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select * from churches where updated > \'" + updated + "\'");
                Log.d("sh", ((Integer) resultSet.getFetchSize()).toString());
                while (resultSet.next()){
                    Log.d("sh", resultSet.getString("updated"));
                    Church church = new Church(resultSet.getInt("churchid"),resultSet.getInt("conid"),resultSet.getString("city"),
                            resultSet.getString("address"), resultSet.getDouble("lon"),resultSet.getDouble("lat"),resultSet.getString("comment"));
                    dbl.createChurch(church);
                    Log.d("sh",church.toString());
                }
            } catch (SQLException e) {
                Log.d("sh", e.getMessage());
            }
        }
    }




}
