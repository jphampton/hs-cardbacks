package com.jphampton.hearthstonecardbacks.carddisplay;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.load.model.GlideUrl;
import com.google.common.base.Optional;
import com.jphampton.hearthstonecardbacks.R;
import com.jphampton.hearthstonecardbacks.glide.GlideApp;
import com.jphampton.hearthstonecardbacks.models.Card;
import com.jphampton.hearthstonecardbacks.service.HearthstoneServiceImpl;

import java.time.Month;
import java.util.Calendar;
import java.util.TimeZone;

public class CardDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_display);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TimeZone local = TimeZone.getDefault();
        Calendar cal = Calendar.getInstance(local);
        int monthIndex = cal.get(Calendar.MONTH);
        Month month = Month.values()[monthIndex];
        String monthName = getResources().getStringArray(R.array.months)[monthIndex];
        int year = cal.get(Calendar.YEAR);
        setTitle(getString(R.string.card_activity_title, monthName));

        new HearthstoneServiceImpl()
            .getCardByDate(month, year, cardback -> displayCardback(cardback));


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(
            view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
    }

    private void displayCardback(Optional<Card> cardbackOptional) {
        if (!cardbackOptional.isPresent()) {
            Log.w("card display", "No card for this month");
            return;
        }
        Card currentCard = cardbackOptional.get();
        if (currentCard.imgURL == null || currentCard.imgURL.isEmpty()) {
            Log.w("card display", "No image url for this card");
            return;
        }
        ImageView cardView = findViewById(R.id.cardback_image);
        GlideUrl glideUrl = new GlideUrl(currentCard.imgURL);
        GlideApp.with(cardView.getContext()).load(glideUrl).fitCenter().into(cardView);
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
