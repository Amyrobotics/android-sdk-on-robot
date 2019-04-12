package com.amy.companyinfomation.activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.amy.companyinfomation.BaseConstants;
import com.amy.companyinfomation.CompanyInfoApp;
import com.amy.companyinfomation.R;
import com.amy.companyinfomation.adapter.MainMenuDividerDecoration;
import com.amy.companyinfomation.adapter.MainMenuRecycleAdapter;
import com.amy.companyinfomation.adapter.SortListDividerDecoration;
import com.amy.companyinfomation.entity.IniEntity;
import com.amy.companyinfomation.manager.VoiceManager;
import com.amy.companyinfomation.utils.GlideRoundTransform;
import com.amy.companyinfomation.utils.XmlUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    private static final int CODE_CHECK_PERMISSION = 99;
    private ImageView mBannerIv;
    private RecyclerView mMenuRv;
    private IniEntity mIniEntity;
    private MainMenuRecycleAdapter mAdapter;

    private int mBannerNum = 1;
    private ObjectAnimator animator;
    private CountDownTimer timer;
    private boolean isAction = false;
    private View mFlashV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();
    }

    private void initView() {
        mFlashV = findViewById(R.id.v_flash);
        mBannerIv = findViewById(R.id.iv_banner);
        mMenuRv = findViewById(R.id.rv_main_menu);

        mIniEntity = IniEntity.getInstance();

//        LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
//        manager.setOrientation(LinearLayoutManager.VERTICAL);
        GridLayoutManager manager;
        manager = new GridLayoutManager(this, 2);
        mMenuRv.addItemDecoration(new SortListDividerDecoration(3));
        manager.setOrientation(LinearLayoutManager.VERTICAL);
//        mMenuRv.addItemDecoration(new MainMenuDividerDecoration());
        mMenuRv.setLayoutManager(manager);
        mAdapter = new MainMenuRecycleAdapter(this);
        mMenuRv.setAdapter(mAdapter);
        mAdapter.setDataList(mIniEntity.getClass_entity().getClassX());
        mAdapter.notifyDataSetChanged();

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .transform(new GlideRoundTransform(50));

        Glide.with(this)
                .load(new File(BaseConstants.IMG_PATH + mIniEntity.getBanner_entity().getBanner().get(0).getBanner_src()))
                .apply(options)
                .into(mBannerIv);

        if (mIniEntity.getBanner_entity().getBanner().size() != 1) {
            Glide.with(this).load(new File(BaseConstants.IMG_PATH + mIniEntity.getBanner_entity().getBanner().get(0).getBanner_src())).into(mBannerIv);
            //定时器
            timer = new CountDownTimer(3000, 1000) {
                @Override
                public void onTick(long l) {
                    //每次间隔回调，什么都不做
                }

                @Override
                public void onFinish() {
                    //计时结束回调
                    startAnim();
                }
            };
            timer.start();
        }
    }

    private void startAnim() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mFlashV, "alpha", 0f, 1f, 0f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentValue = animation.getAnimatedFraction();
                if (currentValue > 0.5f && currentValue < 0.6f) {
                    if (isAction) {
                        return;
                    }
                    try {
                        Glide.with(MainActivity.this).load(new File(BaseConstants.IMG_PATH + new File(mIniEntity.getBanner_entity().getBanner().get(mBannerNum).getBanner_src()))).into(mBannerIv);
                        if (mBannerNum == mIniEntity.getBanner_entity().getBanner().size() - 1) {
                            mBannerNum = 0;
                        } else {
                            mBannerNum++;
                        }
                        isAction = true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAction = false;
                timer.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.setDuration(1000);
        animator.start();
    }

    private void checkPermission() {
        int writeStoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readStoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        List<String> permissList = new ArrayList<String>();
        if (writeStoragePermission != PackageManager.PERMISSION_GRANTED) {
            permissList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (readStoragePermission != PackageManager.PERMISSION_GRANTED) {
            permissList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (permissList.size() > 0) {
            String[] arr = new String[permissList.size()];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = permissList.get(i);
            }
            ActivityCompat.requestPermissions(this, arr, CODE_CHECK_PERMISSION);
        } else {
            XmlUtil.initXML(this);
            initView();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //通过requestCode来识别是否同一个请求
        if (requestCode == CODE_CHECK_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //用户同意，执行操作
                XmlUtil.initXML(this);
                initView();
            } else {
                checkPermission();
            }
        }
    }

    @Override
    public synchronized void disposeVoice(String voiceFont) {
        switch (voiceFont) {
            case "后退":
            case "返回":
//                VoiceManager.getInstance().stopVoiceRecognition();
                this.finish();
                break;
            default:
                for (int i = 0; i < mIniEntity.getClass_entity().getClassX().size(); i++) {
                    if (mIniEntity.getClass_entity().getClassX().get(i).getClass_title().equals(voiceFont)) {
                        Intent intent = new Intent(this, SortListActivity.class);
                        intent.putExtra(SortListActivity.CODE_CLASS, i);
                        startActivity(intent);
                    }
                }
        }
    }

    @Override
    protected void onDestroy() {
        if (mFlashV != null) {
            mFlashV.clearAnimation();
        }
        if (timer != null) {
            timer.cancel();
        }
        if (animator != null) {
            animator.cancel();
        }
        VoiceManager.getInstance().speechRecognitionStop();
        super.onDestroy();
    }
}
