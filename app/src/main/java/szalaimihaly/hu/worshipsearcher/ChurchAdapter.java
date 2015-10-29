package szalaimihaly.hu.worshipsearcher;

import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by Mihaly on 2015.10.28..
 */
public class ChurchAdapter extends BaseAdapter {


        private final ArrayList<Church> churcList;

        public ChurchAdapter(ArrayList<Church> churcList){
            this.churcList=churcList;
        }

        public void addTemplom(Church church){
            churcList.add(church);
        }


        @Override
        public int getCount() {
            return churcList.size();
        }

        @Override
        public Object getItem(int position) {
            return churcList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final Church church = churcList.get(position);
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View viewItem = inflater.inflate(R.layout.churchrow, null);
            TextView textViewChurchCity = (TextView) viewItem.findViewById(R.id.churchCity);
            textViewChurchCity.setText(church.getCity());
            TextView textViewChurchAddress = (TextView) viewItem.findViewById(R.id.churchAddress);
            textViewChurchAddress.setText(church.getAddress());
            TextView textViewChurchComment = (TextView) viewItem.findViewById(R.id.churchComment);
            textViewChurchComment.setText(church.getComment());
            TextView textViewChurchDistance = (TextView) viewItem.findViewById(R.id.churchDistance);
            textViewChurchDistance.setText(church.getDistanceString());
            return viewItem;
        }



    }

