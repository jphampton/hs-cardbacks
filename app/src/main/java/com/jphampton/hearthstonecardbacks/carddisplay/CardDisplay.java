package com.jphampton.hearthstonecardbacks.carddisplay;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.jphampton.hearthstonecardbacks.R;
import com.jphampton.hearthstonecardbacks.models.Card;
import com.jphampton.hearthstonecardbacks.service.HearthstoneServiceImpl;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class CardDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_display);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TimeZone local = TimeZone.getDefault();
        Calendar cal = Calendar.getInstance(local);
        Pair<String, String> monthYear = getMonthAndYear(cal);
        String currentMonth = monthYear.first;
        String currentYear = monthYear.second;
        setTitle(getString(R.string.card_activity_title, currentMonth));

        new HearthstoneServiceImpl().getRankedCards((j)->{});


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private Pair<String,String> getMonthAndYear(Calendar cal){
        return Pair.create(getResources().getStringArray(R.array.months)[cal.get(Calendar.MONTH)], cal.get(Calendar.YEAR)+"");
    }

    private Card getCurrentCard(List<Card> cards, Calendar cal) {
        Pair<String, String> monthYear = getMonthAndYear(cal);
        String currentMonth = monthYear.first;
        String currentYear = monthYear.second;
        String toFind = currentMonth + " " + currentYear;
        for(int i = 0; i < cards.size();i++) {
            Card current = cards.get(i);
            String desc = current.description;
            if(desc.contains(toFind)) {
                return current;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_card_display, menu);
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
