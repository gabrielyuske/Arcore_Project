package com.javahelps.arcore_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class MainActivity extends AppCompatActivity {
    private static final int CAMERA_PERMISSION_CODE = 0;
    private static final String CAMERA_PERMISSION = Manifest.permission.CAMERA;
    private ModelRenderable modelRenderable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ArFragment arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ar_fragment);

        #3Dオブジェクトの表示
        ModelRenderable.builder()
                .setSource(this, R.raw.ball)
                .build()
                .thenAccept(renderable -> modelRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "読み込み失敗", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });



        if(arFragment != null ) {

            arFragment.setOnTapArPlaneListener(
                    (HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {

                        if (modelRenderable == null) {

                            return;
                        }
                        Anchor anchor = hitResult.createAnchor();
                        AnchorNode anchorNode = new AnchorNode(anchor);
                        anchorNode.setParent(arFragment.getArSceneView().getScene());
                        TransformableNode model = new TransformableNode(arFragment.getTransformationSystem());
                        model.setRenderable(modelRenderable);

                        //大きさを指定します。
                        model.getScaleController().setMinScale(0.01f);
                        model.getScaleController().setMaxScale(2.0f);
                        //v:width v1:? v2:height
                        model.setLocalScale(new Vector3(0.5f,0f,0.5f));
                        //座標を指定します
                        model.setLocalPosition(new Vector3(0,0.0f,0));
                        //y軸　縦軸　を中心に１８０度回転
                        model.setLocalRotation(Quaternion.axisAngle(new Vector3(0,1,0),-180));
                        model.setParent(anchorNode);
                        model.select();



                    });
        }

    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        if (ContextCompat.checkSelfPermission(this, CAMERA_PERMISSION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[] {CAMERA_PERMISSION}, CAMERA_PERMISSION_CODE);
//        }
//    }
}
