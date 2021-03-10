package com.example.movierecommender;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AladdinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aladdin_activity);

        TextView more = (TextView)this.findViewById(R.id.moreLink);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://movie.naver.com/movie/bi/mi/basic.nhn?code=163788"));
                startActivity(intent);
            }
        });

        final TextView starPoint = (TextView)findViewById(R.id.starPoint);
        RatingBar ratingBar = (RatingBar)findViewById(R.id.star);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                starPoint.setText("별점 | " + rating);
            }
        });
    }
}
