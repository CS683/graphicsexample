package edu.bu.metcs.graphicsexample;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static int textViewBg = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);


        FloatingActionButton fab = findViewById (R.id.fab);
        fab.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) { int bgImgId;
             TextView myText = findViewById(R.id.tvwithbg);

                if (textViewBg == 0) {
                    textViewBg = 1;
                    bgImgId = R.drawable.human;
                } else {
                    textViewBg = 0;
                    bgImgId = R.drawable.background;
                }
                myText.setBackgroundResource(bgImgId);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater ().inflate (R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId ();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.animation:
                startActivity (new Intent (this, AnimationActivity.class));
                break;
            case R.id.canvas:
                startActivity (new Intent (this, CanvasActivity.class));
            default:
                break;
        }
        return super.onOptionsItemSelected (item);
    }

}
