package com.jim.robotos_v2;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jim.robotos_v2.RouteLogic.Obstacle;
import com.jim.robotos_v2.Utilities.CustomObstacleListAdapter;

import java.util.ArrayList;

public class RouteObstaclesListView extends AppCompatActivity {

    private ListView lvObstacleListView;
    ArrayList<Obstacle> detectedObstacles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_obstacles_list_view);

        lvObstacleListView = (ListView) findViewById(R.id.lvObstacleListView);

        detectedObstacles = (ArrayList<Obstacle>) getIntent().getSerializableExtra("mylist");

        String[] array = new String[detectedObstacles.size()];

        for (int i = 0; i < detectedObstacles.size(); i++) {
            array[i] = detectedObstacles.get(i).getName();
        }

       /* ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, array);*/

        Integer[] colors = new Integer[detectedObstacles.size()];

        for (int i = 0; i < detectedObstacles.size(); i++) {
            colors[i] = Color.argb(0, detectedObstacles.get(i).getColor()[0], detectedObstacles.get(i).getColor()[1], detectedObstacles.get(i).getColor()[2]);
        }

        final CustomObstacleListAdapter customObstacleListAdapter = new CustomObstacleListAdapter(this, array, colors);


        lvObstacleListView.setAdapter(customObstacleListAdapter);


        lvObstacleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;

                showSimpleMaterialDialog(detectedObstacles.get(itemPosition));

            }

        });
    }


    private void showSimpleMaterialDialog(Obstacle obstacle) {
        new MaterialDialog.Builder(this)
                .title(obstacle.getName())
                .titleColor(getResources().getColor(R.color.colorPrimary))
                .content(obstacle.toString())
                .positiveText("OK")
                .positiveColor(getResources().getColor(R.color.colorPrimaryDark))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        materialDialog.dismiss();
                    }
                })

                .show();
    }
}

