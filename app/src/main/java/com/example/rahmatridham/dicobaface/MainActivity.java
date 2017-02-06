package com.example.rahmatridham.dicobaface;

import android.content.Intent;
import android.hardware.Camera;
import android.hardware.camera2.params.Face;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rahmatridham.dicobaface.Holder.RecyclerView_Adapter;
import com.example.rahmatridham.dicobaface.Model.StickerItem;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private int midScreenWidth;
    private int midScreenHeight;

    private Camera mCamera; //objek kamera
    private SurfaceView cameraSurface; // view layout yang menampilkan gambar kamera
    private SurfaceHolder cameraSurfaceHolder;
    private ImageView opt1, opt2, switchCam;
    private CustomView myCustomView; // objek stiker
    private RecyclerView stickerMenus;
    private TextView ket;


    private ArrayList<StickerItem> items;
    boolean isCaptured;
    static int numCamera = 1;
    public static int stickerCam = R.mipmap.ahoka;

    private SurfaceHolder.Callback cameraSurfaceHolderCallbacks = new SurfaceHolder.Callback() {

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            if (mCamera == null) return;
            mCamera.stopPreview();
            mCamera.stopFaceDetection();
            mCamera.release();
            mCamera = null;
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {

            try {
                mCamera = Camera.open(numCamera);
                mCamera.setDisplayOrientation(90);
                mCamera.setPreviewDisplay(holder);
            } catch (Exception exception) {
                android.util.Log.e("TrackingFlow", "Surface Created Exception", exception);
                if (mCamera == null) return;
                mCamera.release();
                mCamera = null;
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            mCamera.startPreview();
            mCamera.setFaceDetectionListener(faceDetectionListener);
            mCamera.startFaceDetection();
        }
    };

    private Camera.FaceDetectionListener faceDetectionListener = new Camera.FaceDetectionListener() {
        @Override
        public void onFaceDetection(Camera.Face[] faces, Camera camera) {
            if (faces.length >= 1) {
                myCustomView.setVisibility(View.VISIBLE);
                for (int i = 0; i < faces.length; i++) {
                /*
                plus minus pengaruhi atas bawah masking
                 */
                    int posX = midScreenWidth + faces[0].rect.centerX();
                    int posY = midScreenHeight - faces[0].rect.centerY();
                    myCustomView.setPoints(posX, posY, faces[0], stickerCam);
                    ket.setText("posX: " + posX + ", posY: " + posY);
                }
                myCustomView.invalidate();
            } else {
                myCustomView.setVisibility(View.INVISIBLE);
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);


        switchCam = (ImageView) findViewById(R.id.switchCamera);
        ket = (TextView) findViewById(R.id.keterangan);
        opt1 = (ImageView) findViewById(R.id.option1);
        Picasso.with(this.getApplicationContext()).load(R.mipmap.camera).into(opt1);
        opt2 = (ImageView) findViewById(R.id.option2);
        Picasso.with(this.getApplicationContext()).load(R.mipmap.video).into(opt2);
        opt1.setOnClickListener(this);
        opt2.setOnClickListener(this);
        switchCam.setOnClickListener(this);

        stickerMenus = (RecyclerView) findViewById(R.id.stickerList);
        isCaptured = true;
        stickerMenus.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));

        items = new ArrayList<>();
        generateList();

        myCustomView = (CustomView) findViewById(R.id.myCustomView);
        cameraSurface = (SurfaceView) findViewById(R.id.cameraSurface);
        cameraSurfaceHolder = cameraSurface.getHolder();
        cameraSurfaceHolder.addCallback(cameraSurfaceHolderCallbacks);

        //Screen sizes...
        Display display = getWindowManager().getDefaultDisplay();
        midScreenHeight = display.getHeight() / 2;
        midScreenWidth = display.getWidth() / 2;
    }

    /*
    ubah mode kamera
     */
    private void switchCamera() {

        if (mCamera != null) {
            System.out.println("flipcamera");
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }

        if (numCamera == 1) {
            mCamera = Camera.open(0);
            numCamera = 0;
        } else {
            mCamera = Camera.open(1);
            numCamera = 1;
        }
        mCamera.setDisplayOrientation(90);

        if (mCamera != null) {
            try {
                mCamera.setPreviewDisplay(cameraSurfaceHolder);
                mCamera.startPreview();
                mCamera.setFaceDetectionListener(faceDetectionListener);
                mCamera.startFaceDetection();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        if (mCamera != null) {
            mCamera.release();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.option1:
                if (isCaptured) {
                    /*
                    melakukan proses capturing foto
                     */
//                    mCamera.takePicture(null,null,mPicture);
                    Toast.makeText(this, "Capture Foto", Toast.LENGTH_SHORT).show();
                } else {
                    /*
                    melakukan proses recording video
                     */
                    Toast.makeText(this, "Record Video", Toast.LENGTH_SHORT).show();

                }

                break;

            case R.id.option2:
                if (isCaptured) {
                    isCaptured = false;
//                    opt1.setImageResource(R.mipmap.ic_record);
//                    opt2.setImageResource(R.drawable.capture);
                    Picasso.with(this.getApplicationContext()).load(R.mipmap.video).into(opt1);
                    Picasso.with(this.getApplicationContext()).load(R.mipmap.camera).into(opt2);

                } else {
                    isCaptured = true;
//                    opt1.setImageResource(R.drawable.capture);
//                    opt2.setImageResource(R.mipmap.ic_record);

                    Picasso.with(this.getApplicationContext()).load(R.mipmap.camera).into(opt1);
                    Picasso.with(this.getApplicationContext()).load(R.mipmap.video).into(opt2);
                }

                break;

            case R.id.switchCamera:
                switchCamera();
                break;

            default:
                break;
        }
    }

    Camera.PictureCallback mPicture = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            File pictureFile = getOutputMediaFile();
            if (pictureFile == null) {
                return;
            }
            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException e) {

            } catch (IOException e) {
            }
        }
    };

    private static File getOutputMediaFile() {
        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "MyCameraApp");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "IMG_" + timeStamp + ".jpg");

        return mediaFile;
    }

    void generateList() {
        items.add(new StickerItem("smiley", R.drawable.forbid));
        items.add(new StickerItem("smiley", R.mipmap.ahoka));
        items.add(new StickerItem("smiley", R.mipmap.ahokb));
        items.add(new StickerItem("smiley", R.mipmap.ahokc));
        items.add(new StickerItem("smiley", R.mipmap.ahokd));
        items.add(new StickerItem("smiley", R.mipmap.ahoke));
        items.add(new StickerItem("smiley", R.mipmap.ahokf));
        items.add(new StickerItem("smiley", R.mipmap.ahokg));
        items.add(new StickerItem("smiley", R.mipmap.ahokh));
        items.add(new StickerItem("smiley", R.mipmap.ahoki));

        RecyclerView_Adapter adapter = new RecyclerView_Adapter(MainActivity.this, items);
        stickerMenus.setAdapter(adapter);// set adapter on recyclerview
        adapter.notifyDataSetChanged();// Notify the adapter
    }
}
