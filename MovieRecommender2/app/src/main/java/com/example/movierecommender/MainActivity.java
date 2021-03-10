package com.example.movierecommender;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // 메인 액티비티 설정
    }

    public void parasiteListener(View target) {
        Intent intent = new Intent(getApplicationContext(), ParasiteActivity.class);    // 인텐트 선언
        startActivity(intent);  // '기생충' 액티비티 시작
    }

    public void aladdinListener(View target) {
        Intent intent = new Intent(getApplicationContext(), AladdinActivity.class);     // 인텐트 선언
        startActivity(intent);  // '알라딘' 액티비티 시작
    }

    public void avatarListener(View target) {
        Intent intent = new Intent(getApplicationContext(), AvatarActivity.class);      // 인텐트 선언
        startActivity(intent);  // '아바타' 액티비티 시작
    }

    public void titanicListener(View target) {
        Intent intent = new Intent(getApplicationContext(), TitanicActivity.class);     // 인텐트 선언
        startActivity(intent);  // '타이타닉' 액티비티 시작
    }

    public void clockListener(View target) {
        Intent intent = new Intent(getApplicationContext(), ClockActivity.class);       // 인텐트 선언
        startActivity(intent);  // '개봉 예정 영화' 액티비티 시작
    }

}
