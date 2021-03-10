package com.example.mydraw;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    private SingleTouchView drawView;
    private ImageButton currPaint;
    private SeekBar strokeBar;
    private ImageButton save;   // 저장 버튼
    private static int count = 0;   // 저장될 그림 파일에 이용되는 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawView = (SingleTouchView) findViewById(R.id.drawing);
        LinearLayout paintLayout = (LinearLayout) findViewById((R.id.paint_colors));
        currPaint = (ImageButton) paintLayout.getChildAt(0);

        save = findViewById(R.id.save_btn); // 저장 버튼 찾기
        strokeBar = (SeekBar) findViewById(R.id.stroke_bar);    // 브러쉬 굵기 조절 바 변수

        strokeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                drawView.changeStroke(progress);    // 굵기 조절 바에 변화가 생기면...
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // 저장 버튼에 이벤트 처리 메소드 등록
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePicture();
                count++;
            }
        });

    }

    public void clicked(View view) {
        if (view != currPaint) {
            String color = view.getTag().toString();
            drawView.setColor(color);
            currPaint = (ImageButton) view;
        }
    }

    public void clear(View view) {
        drawView.clearCanvas();
    }

    public void brush(View view) {
       if (strokeBar.getVisibility() == view.VISIBLE)
           strokeBar.setVisibility(view.INVISIBLE);
       else
           strokeBar.setVisibility(view.VISIBLE);
       drawView.setCir_btn(false);
       drawView.setRec_btn(false);
    }

    public void savePicture() {
        drawView.setDrawingCacheEnabled(true);  // 캐쉬 허용
        Bitmap screenshot = Bitmap.createBitmap(drawView.getDrawingCache());    // 캐쉬에서 가져온 비트맵을 복사해서 새로운 비트맵 생성
        drawView.setDrawingCacheEnabled(false); // 캐쉬 닫기
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);   // 외부 저장소 디렉토리 얻기

        if (!dir.exists())  // 만약 dir가 없으면
            dir.mkdirs();   // 디렉토리 생성

        FileOutputStream fos;   // 파일 출력 스트림 객체 생성
        try {
            fos = new FileOutputStream(new File(dir, "my_" + count + ".png" )); // 파일 이름이 겹치지 않도록 변수 사용
            screenshot.compress(Bitmap.CompressFormat.PNG, 100, fos);   // Bitmap 압축
            fos.close();    // 출력 스트림 닫기
            Toast.makeText(this, "저장 성공", Toast.LENGTH_SHORT).show();  // 저장을 성공했다는 토스트 메시지
        } catch (Exception e) {
            Log.e("photo", "그림저장오류", e);
            Toast.makeText(this, "저장 실패", Toast.LENGTH_SHORT).show();   // 저장을 실패했다는 토스트 메시지
        }
    }

    public void drawRec(View view) {
        // 사각형 버튼 클릭시 호출되는 메소드
        drawView.setRec_btn(true);
        drawView.setCir_btn(false);
        // drawview의 Rec_btn과 Cir_btn의 설정자 이용하여 설정 변경 => 사각형 활성화
    }

    public void drawCircle(View view) {
        // 원형 버튼 클릭시 호출되는 메소드
        drawView.setCir_btn(true);
        drawView.setRec_btn(false);
        // drawview의 Rec_btn과 Cir_btn의 설정자 이용하여 설정 변경 => 원형 활성화
    }
}
