package com.lanou.xiao.facedetectorapplication;

import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements Camera.FaceDetectionListener, OpenVerCallback {
    private SurfaceHolder mHolder;
    private Camera mCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    public void init() {
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.surfaceView);

        mHolder = surfaceView.getHolder();

        mHolder.setFormat(PixelFormat.TRANSPARENT);//translucent半透明 transparent透明
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_HARDWARE);
        mHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                startPreview();
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                releaseCamera();
            }
        });
        openCamera(this);
    }

    public void openCamera(final OpenVerCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mCamera = Camera.open();
                if (mCamera != null) {
                    callback.hasOpenCamera();
                }

            }
        }).start();
    }


    public void startPreview() {
        if (mCamera != null) {
            try {
                mCamera.setPreviewDisplay(mHolder);
                mCamera.setDisplayOrientation(90);

            } catch (IOException e) {
                e.printStackTrace();
            }
            mCamera.startPreview();
        }
    }

    public void releaseCamera() {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void onFaceDetection(Camera.Face[] faces, Camera camera) {

    }

    @Override
    public void hasOpenCamera() {
//        startPreview();
    }
}
