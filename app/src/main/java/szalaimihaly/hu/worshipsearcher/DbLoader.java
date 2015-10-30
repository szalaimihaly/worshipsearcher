package szalaimihaly.hu.worshipsearcher;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Mihaly on 2015.04.24..
 */
public class DbLoader{
    private Context ctx;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase mdb;



    public DbLoader(Context ctx) {
        this.ctx = ctx;

    }

    public void open() throws SQLiteException {
        dbHelper = new DatabaseHelper(ctx, DbConstants.DATABASE_NAME);
        mdb = dbHelper.getWritableDatabase();
        dbHelper.onCreate(mdb);
    }

    public void close() {
        dbHelper.close();
    }

    // INSERT

    public void createChurch(Church church){
        ContentValues values = new ContentValues();
        values.put(DbConstants.Churches.CHURCHID, church.getChurchid());
        values.put(DbConstants.Churches.CONID, church.getConid());
        values.put(DbConstants.Churches.CITY, church.getCity());
        values.put(DbConstants.Churches.ADDRESS, church.getAddress());
        values.put(DbConstants.Churches.LAT, church.getLatitude());
        values.put(DbConstants.Churches.LON, church.getLongitude());
        values.put(DbConstants.Churches.COMMENT, church.getComment());
        mdb.insert(DbConstants.Churches.DATABASE_TABLE, null, values);
    }

    public void createWorship(Worship worship) {
        ContentValues values = new ContentValues();
        values.put(DbConstants.Worships.WORSHIPID, worship.getWorshipid());
        values.put(DbConstants.Worships.CHURCHID, worship.getChurchid());
        values.put(DbConstants.Worships.TERMIN, worship.getTermin());
        values.put(DbConstants.Worships.COMMENT, worship.getComment());
        values.put(DbConstants.Worships.WEEKID, worship.getWeekid());
        mdb.insert(DbConstants.Worships.DATABASE_TABLE, null, values);
    }

    public void createWeek(Week week) {
        ContentValues values = new ContentValues();
        values.put(DbConstants.Weeks.WEEKID, week.getWeekid());
        values.put(DbConstants.Weeks.TYPE, week.getType());
        mdb.insert(DbConstants.Weeks.DATABASE_TABLE, null, values);
    }

    public boolean isWorshipExists(int worshipid){
        String selectquery = "SELECT churchid FROM worships WHERE worshipid=" + worshipid;
        Cursor c = mdb.rawQuery(selectquery,null);
        if(c.moveToFirst()){
            return true;
        } else {
            return false;
        }
    }





    public ArrayList<Church> getAllChurch(){
        ArrayList<Church> l = new ArrayList<Church>();
        String selectquery = "SELECT * FROM " + DbConstants.Churches.DATABASE_TABLE;
        Cursor c = mdb.rawQuery(selectquery, null);
        if(c.moveToFirst()){
            while(!c.isAfterLast()){
                Church church = new Church(c.getInt(0), c.getInt(1), c.getString(2),
                        c.getString(3), c.getDouble(4), c.getDouble(5), c.getString(6));
                l.add(church);
                c.moveToNext();
            }
        }
        c.close();
        return l;
    }


    public ArrayList<Worship> getAllWroship(){
        ArrayList<Worship> l = new ArrayList<Worship>();
        String selectquery = "SELECT * FROM " + DbConstants.Worships.DATABASE_TABLE;
        Cursor c = mdb.rawQuery(selectquery, null);
        if(c.moveToFirst()){
            while(!c.isAfterLast()){
                Worship worship = new Worship(c.getInt(0),c.getInt(1), c.getString(2), c.getInt(3), c.getString(4));
                l.add(worship);
                c.moveToNext();
            }
        }
        c.close();
        return l;
    }

    public ArrayList<Week> getAllWeek(){
        ArrayList<Week> l = new ArrayList<Week>();
        String selectquery = "SELECT * FROM " + DbConstants.Weeks.DATABASE_TABLE;
        Cursor c = mdb.rawQuery(selectquery, null);
        if(c.moveToFirst()){
            while(!c.isAfterLast()){
                Week h = new Week(c.getInt(0), c.getString(1));
                l.add(h);
                c.moveToNext();
            }
        }
        c.close();
        return l;
    }



    public ArrayList<Worship> getWorshipByChurch(int churchid){
        ArrayList<Worship> l = new ArrayList<Worship>();
        String selectquery = "SELECT * FROM " + DbConstants.Worships.DATABASE_TABLE + " WHERE " + DbConstants.Worships.CHURCHID + "=" + churchid;
        Cursor c = mdb.rawQuery(selectquery, null);
        if(c.moveToFirst()){
            while(!c.isAfterLast()){
                Worship i = new Worship(c.getInt(0), c.getInt(1), c.getString(2), c.getInt(3), c.getString(4));
                l.add(i);
                Log.d("sh", "worship kurzor " + i.toString());
                c.moveToNext();
            }
        }
        c.close();
        return l;
    }

    public String getWeekById(int weekid){
        String selectquery = "SELECT * FROM " + DbConstants.Weeks.DATABASE_TABLE + " WHERE " + DbConstants.Weeks.WEEKID + "=" + weekid;
        Cursor c = mdb.rawQuery(selectquery,null);
        if(c.moveToFirst()){
            return c.getString(1);
        }
        c.close();
        return "hiba";
    }

    public Church getChurchById(int churchid){
        String selectquery = "SELECT * FROM " + DbConstants.Churches.DATABASE_TABLE + " WHERE " + DbConstants.Churches.CHURCHID + "=" +churchid;
        Cursor c = mdb.rawQuery(selectquery,null);
        if(c.moveToFirst()){
            return new Church(c.getInt(0),c.getInt(1),c.getString(2),c.getString(3),c.getLong(4),c.getLong(5),c.getString(6));
        }
        c.close();
        return null;
    }

    public int getChurchIdFromCityAddress(String city, String address){
        String selectquery = "SELECT * FROM " + DbConstants.Churches.DATABASE_TABLE + " WHERE " + DbConstants.Churches.CITY + "=\"" + city + "\" AND " + DbConstants.Churches.ADDRESS + "=\"" + address+"\"";
        Cursor c = mdb.rawQuery(selectquery,null);
        if(c.moveToFirst()){
            return c.getInt(0);
        }
        return 0;
    }

    public void deleteAllDataFromDb(){
        mdb.execSQL(DbConstants.Churches.DATABASE_DROP);
        mdb.execSQL(DbConstants.Weeks.DATABASE_DROP);
        mdb.execSQL(DbConstants.Worships.DATABASE_DROP);
        mdb.execSQL(DbConstants.Churches.DATABASE_CREATE);
        mdb.execSQL(DbConstants.Weeks.DATABASE_CREATE);
        mdb.execSQL(DbConstants.Worships.DATABASE_CREATE);
    }

    public void setUpdatedTime(){
        mdb.delete(DbConstants.UpdatedTime.DATABASE_TABLE, DbConstants.UpdatedTime.ID + "=1", null);
        ContentValues values = new ContentValues();
        values.put(DbConstants.UpdatedTime.ID, 1);
        Date date = new Date(System.currentTimeMillis());
        TimeZone timeZone = TimeZone.getDefault();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(timeZone.inDaylightTime(date)) {
            date.setTime(date.getTime()-7200000);
            String currentDateandTime = sdf.format(date);
            Log.d("sh", "set udpated time: " + currentDateandTime);
            values.put(DbConstants.UpdatedTime.LAST_MODIFIED, currentDateandTime);
            mdb.insert(DbConstants.UpdatedTime.DATABASE_TABLE, null, values);
        } else {
            date.setTime(date.getTime()-3600000);
            String currentDateandTime = sdf.format(date);
            Log.d("sh", "set udpated time: " + currentDateandTime);
            values.put(DbConstants.UpdatedTime.LAST_MODIFIED, currentDateandTime);
            mdb.insert(DbConstants.UpdatedTime.DATABASE_TABLE, null, values);
        }
    }

    public String getUpdatedTime(){
        String selecquery = "SELECT * FROM " + DbConstants.UpdatedTime.DATABASE_TABLE + " WHERE " + DbConstants.UpdatedTime.ID + "=1";
        Cursor c = mdb.rawQuery(selecquery, null);
        if(c.moveToFirst()){
            Log.d("sh", "get updated time: " +c.getString(1));
            return c.getString(1);
        }

        return "hiba";
    }

    public void deleteWorshipById(int worshipid){
        mdb.delete(DbConstants.Worships.DATABASE_TABLE, DbConstants.Worships.WORSHIPID + "=" + worshipid,null);
    }

    public void modifieWorshipById(Worship worship){
        ContentValues values = new ContentValues();
        values.put(DbConstants.Worships.CHURCHID, worship.getChurchid());
        values.put(DbConstants.Worships.TERMIN, worship.getTermin());
        values.put(DbConstants.Worships.COMMENT, worship.getComment());
        values.put(DbConstants.Worships.WEEKID, worship.getWeekid());
        mdb.update(DbConstants.Worships.DATABASE_TABLE, values, DbConstants.Worships.WORSHIPID + "="+ worship.getWorshipid(),null);
    }
}
