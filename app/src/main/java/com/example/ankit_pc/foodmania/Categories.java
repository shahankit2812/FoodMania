package com.example.ankit_pc.foodmania;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class Categories extends ActionBarActivity {


    DBHelper db;
    ArrayList<Category> array_list;
    ListView li;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        Intent i = getIntent();
        int res_id = i.getIntExtra("restaurant_id", 0);
        String res_name = i.getStringExtra("restaurant_name");
        System.out.println(res_name);
        TextView restName = (TextView)findViewById(R.id.textView9);
        restName.setText(res_name);
        db = new DBHelper(this, null, null, 1);
        array_list = new ArrayList();
        Cursor res = db.getReadableDatabase().rawQuery("select * from categories where resid =" + res_id + "", null);
        //System.out.println(res);
        if (res != null) {
            res.moveToFirst();
            while (res.isAfterLast() == false) {
                array_list.add(new Category(Integer.parseInt(res.getString(0)), res.getString(1)));
                res.moveToNext();
            }
        }
        ArrayAdapter<Category> adapter = new CustomAdaptor();
        li = (ListView) findViewById(R.id.listView2);
        li.setAdapter(adapter);
        li.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                Intent i = new Intent(Categories.this, FoodItem.class);
                //If you wanna send any data to nextActicity.class you can use
//                        System.out.println(position);
                Category c=array_list.get(position);
                //System.out.println(r.get_id());
                i.putExtra("category_id",c.getId());
                //i.putExtra("restaurant_name",r.getName());
                startActivity(i);
            }
        });
        db.close();
    }

    private class CustomAdaptor extends ArrayAdapter {
        public CustomAdaptor() {
            super(Categories.this, R.layout.activity_custom_adapter, array_list);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater li = LayoutInflater.from(getContext());
            View customView = li.inflate(R.layout.activity_custom_adapter, parent, false);
            Category c = array_list.get(position);
            TextView cat = (TextView) customView.findViewById(R.id.textView6);
            cat.setText(c.getCat());
            return customView;
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_categories, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
