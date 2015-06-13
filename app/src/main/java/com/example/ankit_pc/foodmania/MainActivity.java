package com.example.ankit_pc.foodmania;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    DBHelper db;
    EditText zip;
    Button search;
    List<Restaurant> array_list;
    ListView li;
    ImageView imageView;
    int []imageArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHelper(this, null, null, 1);
        db.dropTable();
        db.insertRestaurants();

        zip = (EditText) findViewById(R.id.editText3);
        imageArray = new int[]{R.drawable.r1,R.drawable.r2,R.drawable.r3};


        imageView = (ImageView)findViewById(R.id.imageView3);
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int i=0;
            public void run() {
                imageView.setImageResource(imageArray[i]);
                i++;
                if(i>imageArray.length-1)
                {
                    i=0;
                }
                handler.postDelayed(this, 3000);  //for interval...
            }
        };
        handler.postDelayed(runnable, 2000); //for initial delay..



        search=(Button)findViewById(R.id.button);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                array_list = new ArrayList<Restaurant>();
                //System.out.println(zip.getText().toString());
                Cursor res = db.getReadableDatabase().rawQuery("select * from listofrestaurants where zipcode =" + zip.getText(), null);
                res.moveToFirst();
                while (res.isAfterLast() == false) {
                    array_list.add(new Restaurant(Integer.parseInt(res.getString(0)),res.getString(1),res.getString(2),Integer.parseInt(res.getString(3)),Integer.parseInt(res.getString(4)),Integer.parseInt(res.getString(5))));
                    //array_list.add(new Restaurant(Integer.parseInt(res.getString(0)),res.getString(1),res.getString(2),Integer.parseInt(res.getString(3)),Integer.parseInt(res.getString(4)),Integer.parseInt(res.getString(5)),res.getString(6)));
                    res.moveToNext();
                }
                ArrayAdapter<Restaurant> adapter=new CustomAdaptor();
                li=(ListView)findViewById(R.id.listView);
                li.setAdapter(adapter);
                li.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

                        Intent i = new Intent(MainActivity.this, MenuActivity.class);
                        //If you wanna send any data to nextActicity.class you can use
//                        System.out.println(position);
                        Restaurant r=array_list.get(position);
                        //System.out.println(r.get_id());
                        i.putExtra("restaurant_id",r.get_id());
                        i.putExtra("restaurant_name",r.getName());
                        startActivity(i);
                    }
                });
            }
        });
        db.close();
    }
    private class CustomAdaptor extends ArrayAdapter {
        TextView t;
        int count=0;
        public CustomAdaptor() {
            super(MainActivity.this,R.layout.activity_layout_for_list, array_list);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater li=LayoutInflater.from(getContext());
            View customView=li.inflate(R.layout.activity_layout_for_list, parent, false);
            Restaurant r=array_list.get(position);
            TextView name=(TextView)customView.findViewById(R.id.textView1);
            name.setText(r.getName());


            TextView area=(TextView)customView.findViewById(R.id.textView2);
            area.setText(r.getArea());

            TextView min=(TextView)customView.findViewById(R.id.textView3);
            min.setText(""+r.getMin());

            TextView dt=(TextView)customView.findViewById(R.id.textView4);
            dt.setText("" + r.getDt());



//            t = (TextView)customView.findViewById(R.id.textView5);
//            Button plus=(Button)customView.findViewById(R.id.button2);
//            plus.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    // TODO Auto-generated method stub
//                    if(Integer.parseInt((String) t.getText())>0)
//                    {
//                        count=Integer.parseInt((String) t.getText());
//                        ++count;
//                    }
//                    if(count==0)
//                    {
//                        ++count;
//                    }
//                    t.setText(count);
//                }
//            });

//            Button minus=(Button)customView.findViewById(R.id.button3);
//            minus.setOnClickListener();
//            ImageView img=(ImageView)customView.findViewById(R.id.image);
//            img.setImageDrawable(getResources().getDrawable(Integer.parseInt(r.getImg())));

            return customView;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
