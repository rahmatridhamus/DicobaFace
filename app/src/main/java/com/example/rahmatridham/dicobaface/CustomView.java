package com.example.rahmatridham.dicobaface;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.View;

public class CustomView extends View {
    private static int posX;
    private static int posY;
    private static Bitmap leftEyeBmp;
    private int leftEyeBmpWidth;
    private int leftEyeBmpHeight;
    private Paint paint = new Paint();
    static Camera.Face face;
    float f = (float) 0.9;
    int stickerAddress = 0;


    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (leftEyeBmp != null && !leftEyeBmp.isRecycled()) leftEyeBmp.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*
        float 2.65 itu mempengaruhi atas-bawah masking object
        2 - f itu mempengaruhi kiri-kanan masking object
         */
        canvas.drawBitmap(leftEyeBmp, posY - (leftEyeBmpWidth / (2 - f)), ((leftEyeBmpHeight * (float) 2.65) / (2 + f)) - posX, null);
        drawBox(canvas);

    }

    public static void drawBox(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

        double viewWidth = canvas.getWidth();
        double viewHeight = canvas.getHeight();
        double imageWidth = leftEyeBmp.getWidth();
        double imageHeight = leftEyeBmp.getHeight();
//        double scale = Math.min(viewWidth / imageWidth, viewHeight / imageHeight);
        double scale = 1;
        /*
        left-top mempengaruhi atas-bawah
         */
        float left = (float) (posY - ((face.rect.width())));
        float top = (float) (posX);
        float bottom = (float) (posX + (face.rect.height()));
        float right = (float) (posY - (face.rect.width()) / (5));
        RectF rect = new RectF(left, top, (float) scale * right, (float) scale * bottom);
//        Rect rect = new Rect(posY,posX,posY+500,posX+500);

//        canvas.drawRect(rect, paint);

//        canvas.drawRect(face.rect.left, face.rect.top, face.rect.right, face.rect.bottom, paint);
//        canvas.drawRect(face.rect.bottom, face.rect.left, face.rect.top, face.rect.right, paint);
//        canvas.drawRect(face.rect.right, face.rect.bottom, face.rect.left, face.rect.top, paint);
    }

    public void setPoints(int x, int y, Camera.Face face, int sticker) {
        this.face = face;
        this.posX = x;
        this.posY = y;
        this.stickerAddress = sticker;

//        Bitmap resource = BitmapFactory.decodeResource(context.getResources(), R.drawable.my_face_glasses);
        Bitmap bmp = BitmapFactory.decodeResource(this.getResources(), stickerAddress);
        leftEyeBmp = Bitmap.createScaledBitmap(bmp, 1000, 1000, true);
        if (leftEyeBmp != null) {
            leftEyeBmpWidth = leftEyeBmp.getWidth();
            leftEyeBmpHeight = leftEyeBmp.getHeight();
        }

    }

}
