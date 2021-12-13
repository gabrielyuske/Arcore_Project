//package com.javahelps.arcore_project;
//
//import android.Manifest;
//import android.os.Bundle;
//import android.view.Gravity;
//import android.view.MotionEvent;
//import android.widget.Toast;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.ar.core.Anchor;
//import com.google.ar.core.HitResult;
//import com.google.ar.core.Plane;
//import com.google.ar.sceneform.AnchorNode;
//import com.google.ar.sceneform.animation.ModelAnimator;
//import com.google.ar.sceneform.math.Quaternion;
//import com.google.ar.sceneform.math.Vector3;
//import com.google.ar.sceneform.rendering.ModelRenderable;
//import com.google.ar.sceneform.ux.ArFragment;
//import com.google.ar.sceneform.ux.TransformableNode;
//
//public class MainActivity extends AppCompatActivity {
//
//    private ModelAnimator modelAnimator;
//    private int i =0;
//    private static final int CAMERA_PERMISSION_CODE = 0;
//    private static final String CAMERA_PERMISSION = Manifest.permission.CAMERA;
//    private ModelRenderable modelRenderable;
//    private TransformableNode startAnimation;
//    private ArFragment fragment;
//    private PointerDrawable pointer = new PointerDrawable();
//    private boolean isTracking;
//    private boolean isHitting;
//
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        ArFragment arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ar_fragment);
//
////      #3Dオブジェクトの表示
//        ModelRenderable.builder()
//                .setSource(this, R.raw.cangrejo)
//                .build()
//                .thenAccept(renderable -> modelRenderable = renderable)
//                .exceptionally(
//                        throwable -> {
//                            Toast toast =
//                                    Toast.makeText(this, "読み込み失敗", Toast.LENGTH_LONG);
//                            toast.setGravity(Gravity.CENTER, 0, 0);
//                            toast.show();
//                            return null;
//                        });
//        if(arFragment != null ) {
//
//            arFragment.setOnTapArPlaneListener(
//                    (HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {
//
//                        if (modelRenderable == null) {
//
//                            return;
//                        }
//                        Anchor anchor = hitResult.createAnchor();
//                        AnchorNode anchorNode = new AnchorNode(anchor);
//                        anchorNode.setParent(arFragment.getArSceneView().getScene());
//                        TransformableNode model = new TransformableNode(arFragment.getTransformationSystem());
//                        model.setRenderable(modelRenderable);
//
//                        //大きさを指定します。
//                        model.getScaleController().setMinScale(0.01f);
//                        model.getScaleController().setMaxScale(2.0f);
//                        //v:width v1:? v2:height
//                        model.setLocalScale(new Vector3(0.5f,0f,0.5f));
//                        //座標を指定します
//                        model.setLocalPosition(new Vector3(0,0.0f,0));
//                        //y軸　縦軸　を中心に１８０度回転
//                        model.setLocalRotation(Quaternion.axisAngle(new Vector3(0,1,0),-180));
//                        model.setParent(anchorNode);
//                        model.select();
//
//
//
//                    });
//        }
//
//
// }
//
////    private void onUpdate() {
////        boolean trackingChanged = updateTracking();
////        View contentView = findViewById(android.R.id.content);
////        if (trackingChanged) {
////            if (isTracking) {
////                contentView.getOverlay().add(pointer);
////            } else {
////                contentView.getOverlay().remove(pointer);
////            }
////            contentView.invalidate();
////        }
////
////        if (isTracking) {
////            boolean hitTestChanged = updateHitTest();
////            if (hitTestChanged) {
////                pointer.setEnabled(isHitting);
////                contentView.invalidate();
////            }
////        }
////    }
//
////    private boolean updateHitTest() {
////        Frame frame = fragment.getArSceneView().getArFrame();
////        android.graphics.Point pt = getScreenCenter();
////        List<HitResult> hits;
////        boolean wasHitting = isHitting;
////        isHitting = false;
////        if (frame != null) {
////            hits = frame.hitTest(pt.x, pt.y);
////            for (HitResult hit : hits) {
////                Trackable trackable = hit.getTrackable();
////                if (trackable instanceof Plane &&
////                        ((Plane) trackable).isPoseInPolygon(hit.getHitPose())) {
////                    isHitting = true;
////                    break;
////                }
////            }
////        }
////        return wasHitting != isHitting;
////    }
//
////    private android.graphics.Point getScreenCenter() {
////        View vw = findViewById(android.R.id.content);
////        return new android.graphics.Point(vw.getWidth()/2, vw.getHeight()/2);
////    }
//
////    private boolean updateTracking() {
////        Frame frame = fragment.getArSceneView().getArFrame();
////        boolean wasTracking = isTracking;
////        isTracking = frame != null &&
////                frame.getCamera().getTrackingState() == TrackingState.TRACKING;
////        return isTracking != wasTracking;
////    }
//
//
////    public void startAnimation(TransformableNode node, ModelRenderable renderable){
////        if(renderable==null || renderable.getAnimationDataCount() == 0) {
////            return;
////        }
////        for(int i = 0;i < renderable.getAnimationDataCount();i++){
////            AnimationData animationData = renderable.getAnimationData(i);
////        }
////        ModelAnimator animator = new ModelAnimator(renderable.getAnimationData(0), renderable);
////        animator.start();
////        node.setOnTapListener(
////                (hitTestResult, motionEvent) -> {
////                    togglePauseAndResume(animator);
////                });
////    }
//
////    public void togglePauseAndResume(ModelAnimator animator) {
////        if (animator.isPaused()) {
////            animator.resume();
////        } else if (animator.isStarted()) {
////            animator.pause();
////        } else {
////            animator.start();
////        }
////    }
//
//
//}
////if (renderable instanceof ModelRenderable) {
////        startAnimation(node, (ModelRenderable)renderable);
//////        }