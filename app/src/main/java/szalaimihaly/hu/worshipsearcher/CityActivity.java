package szalaimihaly.hu.worshipsearcher;


import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.Collections;

public class CityActivity extends Activity{
    private ArrayList<Church> churchList = new ArrayList<>();
    private String[] menuItems = new String []{"Legközelbbi alkalom"};
    public void onCreate(Bundle savedinstanceState) {
        super.onCreate(savedinstanceState);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        double latitude = bundle.getDouble("latitude");
        double longitue = bundle.getDouble("longitude");
        final Location location = new Location("");
        location.setLatitude(latitude);
        location.setLongitude(longitue);
        setContentView(R.layout.city);
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
        ListView listViewChurhes = (ListView) findViewById(R.id.churchList);
        Log.d("sh", "Listahoszz:" + ((Integer) churchList.size()).toString());
        final ChurchAdapter churchAdapter = new ChurchAdapter(getApplicationContext(),R.layout.churchrow,churchList);
        listViewChurhes.setAdapter(churchAdapter);
        listViewChurhes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Church seletctedChurch = (Church) churchAdapter.getItem(position);
                Intent intent = new Intent();
                intent.setClass(CityActivity.this, WorshipListActivity.class);
                intent.putExtra("churchid", seletctedChurch.getChurchid());
                startActivity(intent);
            }

            ;
        });
        registerForContextMenu(listViewChurhes);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if(v.getId()==R.id.churchList){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            menu.setHeaderTitle(churchList.get(info.position).getCity());
            for (int i=0; i<menuItems.length; i++){
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        String menuItemName = menuItems[menuItemIndex];
        Church selectedChurch = churchList.get(info.position);
        if(item.getTitle().equals(menuItems[0])){
            Intent intent = getIntent();
            intent.putExtra("churchid",selectedChurch.getChurchid());
            intent.setClass(CityActivity.this, SimpleWorshipActivity.class);
            startActivity(intent);
        }
       return true;
    }
}
