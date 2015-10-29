package szalaimihaly.hu.worshipsearcher;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.sql.Time;
import java.util.TimeZone;

/**
 * Created by Mihaly on 2015.05.01..
 */
public class MainActivity extends Activity {
    public static WebDbLoader webDbLoader;
    private Button button;
    private GPSListener gpsListener;
    private Location location;
    private double latitude;
    private  double longitude;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TimeZone timeZone = TimeZone.getDefault();
        Log.d("sh", timeZone.toString());
        button = (Button) findViewById(R.id.button);
        gpsListener = new GPSListener(getApplicationContext());
        if(gpsListener.canGetLocation()) {
            location = gpsListener.getLocation();
            if(location != null) {
                longitude = location.getLongitude();
                latitude = location.getLatitude();
            }
        } else {
            longitude = 0.0;
            latitude = 0.0;
        }
        Log.d("sh", ((Double) longitude).toString() + " "+ ((Double)latitude).toString());
        Location loc2 = new Location("");
        loadData();
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageResource(R.mipmap.ic_launcher);
        imageView.setMaxWidth(android.R.attr.width);
        imageView.setMinimumWidth(android.R.attr.width);


    }

    public static DbLoader getDbl() {
        return webDbLoader.getDbl();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    private void loadData(){
        Thread t = new Thread() {
            public void run() {
                webDbLoader = new WebDbLoader(getApplicationContext());
                if (webDbLoader.isdDataInDb()) {
                    Log.d("sh", "db let�ltve");
                    if(webDbLoader.setConnection()) {
                        webDbLoader.deleteOldWorships();
                        webDbLoader.getNewWorships();
                        webDbLoader.getNewChurches();
                        webDbLoader.closeConnection();
                    }
                    button.setOnClickListener(new ButtonClickListener());
                    Log.d("sh", "action listener l�trehozva");
                } else {
                    if(webDbLoader.setConnection()) {

                        webDbLoader.getAllChurches();
                        webDbLoader.getAllWorships();
                        webDbLoader.getAllWeeks();
                        webDbLoader.closeConnection();
                        button.setOnClickListener(new ButtonClickListener());
                    } else {
                        Log.d("sh", "csin�lj netet");
                    }

                }
            }
        };
        Thread t2 = new Thread(){
            public void run(){
                webDbLoader = new WebDbLoader(getApplicationContext());
                if (webDbLoader.isdDataInDb()) {
                    Log.d("sh", "db let�ltve");
                    button.setOnClickListener(new ButtonClickListener());
                    Log.d("sh", "action listener l�trehozva");
                } else {

                    Log.d("sh", "Kapcsol�djon az intertre, hogy a program let�lthesse az adatb�zist");
                }
            }
        };
        if(isNetworkAvailable()){
            Log.d("sh", "Van net");
            t.start();
        } else {
            Log.d("sh", "Nincs net");
            t2.start();
        }
    }


    private void showInternetConnetionSetting(){
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(this.getApplicationContext());
        alertdialog.setTitle("Internetbe�ll�t�sok");
        alertdialog.setMessage("Bekapcsolja az internethozz�f�r�st?");
        alertdialog.setPositiveButton("Wifi be�ll�t�sok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                MainActivity.this.getApplicationContext().startActivity(intent);
            }
        });
        alertdialog.setNegativeButton("M�gse", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertdialog.show();
    }


     class ButtonClickListener implements View.OnClickListener {
        public void onClick(View v){
                Intent intent = new Intent();
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                intent.setClass(MainActivity.this, CityActivity.class);
                startActivity(intent);
            }
        }
    }


