package com.jphampton.hearthstonecardbacks.carddisplay;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.google.common.base.Optional;
import com.jphampton.hearthstonecardbacks.R;
import com.jphampton.hearthstonecardbacks.glide.GlideApp;
import com.jphampton.hearthstonecardbacks.models.Card;
import com.jphampton.hearthstonecardbacks.service.HearthstoneServiceImpl;

import java.time.Month;
import java.util.Calendar;
import java.util.TimeZone;

import io.reactivex.disposables.CompositeDisposable;

public class CardDisplay extends AppCompatActivity {

    private CompositeDisposable disposables = new CompositeDisposable();

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

        disposables.add(new HearthstoneServiceImpl()
            .getCardByDate(month, year, this::displayCardback));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposables.clear();
    }

    private void displayCardback(Optional<Card> cardbackOptional) {
        if (!cardbackOptional.isPresent()) {
            Log.w("card display", "No card for this month");
            displayError(R.string.no_card_message);
            return;
        }
        Card currentCard = cardbackOptional.get();
        if (currentCard.imgURL == null || currentCard.imgURL.isEmpty()) {
            Log.w("card display", "No image url for this card");
            displayError(R.string.no_image_message);
            return;
        }
        ImageView cardView = findViewById(R.id.cardback_image);
        GlideUrl glideUrl = new GlideUrl(currentCard.imgURL);
        GlideApp
            .with(cardView.getContext())
            .load(glideUrl)
            .transforms(new FitCenter(), new RoundedCorners(getCornerRadius(cardView)))
            .into(cardView);
    }


    private void displayError(int messageId) {
        Toast.makeText(this, messageId, Toast.LENGTH_LONG).show();
    }

    private int getCornerRadius(View view) {
      int height = view.getHeight();
      int width = view.getWidth();
      return height < 1.5*width ? height/30 : width/20;
    }
}
