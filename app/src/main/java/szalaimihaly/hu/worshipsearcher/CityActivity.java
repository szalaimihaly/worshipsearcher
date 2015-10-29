package szalaimihaly.hu.worshipsearcher;


import android.app.ListActivity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;


import java.security.Provider;
import java.util.ArrayList;
import java.util.Collections;

public class CityActivity extends ListActivity {
    private ArrayList<Church> churchList = new ArrayList<>();
    public void onCreate(Bundle savedinstanceState) {
        super.onCreate(savedinstanceState);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        double latitude = bundle.getDouble("latitude");
        double longitue = bundle.getDouble("longitude");
        final Location location = new Location("");
        location.setLatitude(latitude);
        location.setLongitude(longitue);

        Thread t = new Thread() {
            public void run() {
                MainActivity.getDbl().open();
                churchList = MainActivity.getDbl().getAllChurch();
                for(Church church: churchList){
                    church.setDistance(location);
                    Log.d("sh", "distance: " + church.getDistance());
                }
                //Log.d("sh", "Listahoszz:" + ((Integer) churchList.size()).toString());
                Collections.sort(churchList);
                MainActivity.getDbl().close();
            }
        };
        Log.d("sh",location.toString());
        t.run();
        Log.d("sh", "Listahoszz:" + ((Integer) churchList.size()).toString());
        ChurchAdapter churchAdapter = new ChurchAdapter(churchList);
        setListAdapter(churchAdapter);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Church seletctedChurch = (Church) getListAdapter().getItem(position);
                Intent intent = new Intent();
                intent.setClass(CityActivity.this, WorshipActivity.class);
                intent.putExtra("churchid", seletctedChurch.getChurchid());
                startActivity(intent);
            }

            ;
        });
    }
}
