package com.amy.companyinfomation.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.amy.companyinfomation.BaseConstants;
import com.amy.companyinfomation.entity.IniEntity;
import com.thoughtworks.xstream.XStream;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/9/20.
 */

public class XmlUtil {

    private static final String TAG = "XmlUtil";

    public static void initXML(Context context) {
        try {
            File file = new File(BaseConstants.INI_PATH);
            if (!file.exists()) {
                Toast.makeText(context, "未找到配置文件，请确认配置文件是否存在", Toast.LENGTH_LONG).show();
                Activity activity = (Activity) context;
                activity.finish();
                return;
            }
            XStream xStream = new XStream();
            xStream.processAnnotations(IniEntity.class);
            IniEntity.setmIniEntity((IniEntity) xStream.fromXML(file));
            Log.e(TAG, "HanziToPinyin begin");
            for (int i = 0; i < IniEntity.getInstance().getClass_entity().getClassX().size(); i++) {
                List<IniEntity.ClassEntity.ClassBean.DataBean.ItemBean> mList = IniEntity.getInstance().getClass_entity().getClassX().get(i).getData().getItem();
                List<String> itemToPinyin = new ArrayList<>();
                for (IniEntity.ClassEntity.ClassBean.DataBean.ItemBean itemBean : mList) {
                    String result = "";
                    String source = "";
                    List<HanziToPinyin.Token> results = HanziToPinyin.getInstance().get(itemBean.getItem_name());
                    for (HanziToPinyin.Token token : results) {
                        result = result + token.target;
                        source = source + token.source;
                    }
                    itemToPinyin.add(result);
                }
                IniEntity.getInstance().getClass_entity().getClassX().get(i).getData().setItemToPinyin(itemToPinyin);
                List<String> temp = IniEntity.getInstance().getClass_entity().getClassX().get(i).getData().getItemToPinyin();
                for (String s : temp) {
                    Log.e(TAG, "s = " + s);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
