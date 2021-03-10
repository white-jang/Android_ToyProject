package com.example.movierecommender;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.time.MonthDay;
import java.util.Date;

public class ClockActivity extends AppCompatActivity {

    private WebView myWebView;  // 개봉 예정 영화 페이지를 띄울 웹뷰 변수
    private WebSettings myWebSetting;   // 웹뷰 세팅 변수
    private TextView myDate; // 날짜를 표시할 텍스트뷰 변수
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy/MM/dd");  // 날짜를 표시할 포맷
    long now = System.currentTimeMillis();  // 현재 시스템의 날짜

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clock_activity);

        myWebView = (WebView) findViewById(R.id.releaseFilm);   // 웹뷰 찾기
        myDate = (TextView) findViewById(R.id.dateText); // 날짜 텍스트뷰 찾기

        myWebView.setWebViewClient(new WebViewClient());    // 웹뷰 클릭시 새창이 뜨지 않게 설정
        myWebSetting = myWebView.getSettings(); // 웹뷰 세팅 등록
        myWebSetting.setJavaScriptEnabled(true); // 웹페이지 자바스클비트 허용 여부
        myWebSetting.setSupportMultipleWindows(false); // 새창 띄우기 허용 여부
        myWebSetting.setJavaScriptCanOpenWindowsAutomatically(false); // 자바스크립트 새창 띄우기(멀티뷰) 허용 여부
        myWebSetting.setLoadWithOverviewMode(true); // 메타태그 허용 여부
        myWebSetting.setUseWideViewPort(true); // 화면 사이즈 맞추기 허용 여부
        myWebSetting.setSupportZoom(false); // 화면 줌 허용 여부
        myWebSetting.setBuiltInZoomControls(false); // 화면 확대 축소 허용 여부
        myWebSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL); // 컨텐츠 사이즈 맞추기
        myWebSetting.setCacheMode(WebSettings.LOAD_NO_CACHE); // 브라우저 캐시 허용 여부
        myWebSetting.setDomStorageEnabled(true); // 로컬저장소 허용 여부

        myWebView.loadUrl("http://m.cgv.co.kr/WebAPP/MovieV4/movieList.aspx?mtype=plan&iPage=1"); // 웹뷰에 표시할 웹사이트 주소

        Date date = new Date(now);  // 시스템의 날짜 받아오기
        String time = mFormat.format(date); // 날짜를 받아 포맷에 따라 문자열로 설정
        myDate.setText(time);
    }
}
