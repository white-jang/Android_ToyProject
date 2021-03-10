package com.example.movierecommender;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ParasiteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parasite_activity); // 액티비티 화면 설정

        TextView more = (TextView)this.findViewById(R.id.moreLink); // 더보기 텍스트 찾기
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW); // 인텐트 선언
                intent.setData(Uri.parse("https://movie.naver.com/movie/bi/mi/basic.nhn?code=161967")); // 링크된 웹으로 넘어가게 설정
                startActivity(intent);  // 인텐트의 액티비티 시작
            }
        });

        final TextView starPoint = (TextView)findViewById(R.id.starPoint);  // 별점을 표시할 텍스트 찾기
        RatingBar ratingBar = (RatingBar)findViewById(R.id.star);   // 레이팅바 (별점) 찾기

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                starPoint.setText("별점 | " + rating);    // 레이팅 바의 값이 바뀔 때마다 텍스트 수정
            }
        });
    }
}
