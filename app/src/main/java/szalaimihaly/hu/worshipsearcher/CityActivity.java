package szalaimihaly.hu.worshipsearcher;


import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class CityActivity extends Activity{
    private ArrayList<Church> churchList = new ArrayList<>();
    private String[] menuItems = new String []{"Legközelbbi alkalom"};
    private String[] comp_array;
    private ArrayAdapter<String> comp_adapter;
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
                int i=0;
                comp_array=new String[churchList.size()];
                for(Church church: churchList) {
                    church.setDistance(location);
                    comp_array[i] = church.getCity() + " " + church.getAddress();
                    Log.d("sh", comp_array[i].toString());
                    i++;
                    Log.d("sh", "distance: " + church.getDistance());

                }
                Collections.sort(churchList);
                MainActivity.getDbl().close();
            }
        };
        Log.d("sh", location.toString());
        t.run();
        final AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autocomplete);
        TextView textView = (TextView) findViewById(R.id.suggestionList);
        comp_adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.autocompletetextview,R.id.suggestionList,comp_array);
        ListView listViewChurhes = (ListView) findViewById(R.id.churchList);
        Log.d("sh", "Listahoszz:" + ((Integer) churchList.size()).toString());
        final ChurchAdapter churchAdapter = new ChurchAdapter(getApplicationContext(),R.layout.churchrow,churchList);
        listViewChurhes.setAdapter(churchAdapter);
        autoCompleteTextView.setAdapter(comp_adapter);
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
        autoCompleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //autoCompleteTextView.showDropDown();
            }
        });
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String) parent.getItemAtPosition(position);
                int pos = Arrays.asList(comp_array).indexOf(selected);
                int space = selected.indexOf(" ");
                String city = selected.substring(0, space);
                String address = selected.substring(space + 1, selected.length());
                MainActivity.getDbl().open();
                int churchid  = MainActivity.getDbl().getChurchIdFromCityAddress(city, address);
                MainActivity.getDbl().close();
                Toast.makeText(getApplication(), selected, Toast.LENGTH_LONG).show();
                Intent intent = getIntent();
                intent.putExtra("churchid",churchid);
                intent.setClass(CityActivity.this,WorshipListActivity.class);
                startActivity(intent);
            }
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
