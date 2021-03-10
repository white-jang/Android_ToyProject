package com.example.mydraw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class SingleTouchView extends View {
    private Paint paint = new Paint();
    private Path path = new Path();
    private int paintColor = 0xFF000000;
    private Paint canvasPaint;
    private Canvas drawCanvas;
    private Bitmap canvasBitmap;
    private boolean rec_btn;    // 사각형 버튼을 눌렀는지 여부
    private boolean cir_btn;    // 원형 버튼을 눌렀는지 여부

    public void setRec_btn(boolean isBtnOn) { rec_btn = isBtnOn; }  // 사각형 버튼 활성화 (설정자)

    public void setCir_btn(boolean isBtnOn) { cir_btn = isBtnOn; }  // 원형 버튼 활성화 (설정자)

    public SingleTouchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(10f);
        paint.setColor(paintColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(touchX, touchY);

                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                if (rec_btn == false && cir_btn == false)
                    drawCanvas.drawPath(path, paint);
                else if (rec_btn == true) {
                    // 사각형 버튼이 눌렸을 때
                    drawCanvas.drawRect(touchX, touchY, touchX+200, touchY+200, paint);
                }
                else if (cir_btn == true) {
                    // 원형 버튼이 눌렸을 때
                    drawCanvas.drawCircle(touchX, touchY, 100, paint);
                }
                path.reset();
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }

    public void setColor(String newColor) {
        invalidate();
        paintColor = Color.parseColor(newColor);
        paint.setColor(paintColor);
    }

    public void clearCanvas() {
        // 캔버스 초기화
        this.canvasBitmap.eraseColor(Color.WHITE);
        invalidate();
    }

    public void changeStroke(int p) {
        // 선 굵기 바꾸기
        paint.setStrokeWidth(p);
    }

}
