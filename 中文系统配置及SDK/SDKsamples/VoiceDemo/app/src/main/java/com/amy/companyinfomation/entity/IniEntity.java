package com.amy.companyinfomation.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/9/19.
 */
@XStreamAlias("ini_entity")
public class IniEntity {

    private static IniEntity mIniEntity = null;
    private static Object lock = new Object();

    private IniEntity() {
    }

    public static IniEntity getInstance() {
        synchronized (lock) {
            if (mIniEntity == null) {
                mIniEntity = new IniEntity();
            }
        }

        return mIniEntity;
    }

    public static void setmIniEntity(IniEntity mIniEntity) {
        IniEntity.mIniEntity = mIniEntity;
    }

    private BannerEntity banner_entity;
    private ClassEntity class_entity;

    public BannerEntity getBanner_entity() {
        return banner_entity;
    }

    public void setBanner_entity(BannerEntity banner_entity) {
        this.banner_entity = banner_entity;
    }

    public ClassEntity getClass_entity() {
        return class_entity;
    }

    public void setClass_entity(ClassEntity class_entity) {
        this.class_entity = class_entity;
    }

    @XStreamAlias("banner_entity")
    public static class BannerEntity {
        /**
         * banner_src : banner1.png
         */

        @XStreamImplicit(itemFieldName = "banner")
        private List<Banner> banner;

        public List<Banner> getBanner() {
            return banner;
        }

        public void setBanner(List<Banner> banner) {
            this.banner = banner;
        }

        @XStreamAlias("banner")
        public static class Banner {

            private String banner_src;

            public String getBanner_src() {
                return banner_src;
            }

            public void setBanner_src(String banner_src) {
                this.banner_src = banner_src;
            }
        }

        @Override
        public String toString() {
            return banner.toString();
        }
    }

    @XStreamAlias("class_entity")
    public static class ClassEntity {
        /**
         * data : {"item":[{"item_info":"item_info1","item_detail":"item_detail1","item_name":"item_name1","item_title":"item_title1","item_icon":"item_icon1","item_num":"item_num1"},{"item_info":"item_info2","item_detail":"item_detail2","item_name":"item_name2","item_title":"item_title2","item_icon":"item_icon2","item_num":"item_num2"},{"item_info":"item_info3","item_detail":"item_detail3","item_name":"item_name3","item_title":"item_title3","item_icon":"item_icon3","item_num":"item_num3"}]}
         * class_info : class_info1
         * class_icon : class_icon1.png
         * class_title : class_title1
         */

        @XStreamImplicit(itemFieldName = "class")
        private List<ClassBean> classX;

        public List<ClassBean> getClassX() {
            return classX;
        }

        public void setClassX(List<ClassBean> classX) {
            this.classX = classX;
        }

        @XStreamAlias("class")
        public static class ClassBean {
            @XStreamAlias("data")
            private DataBean data;
            private String class_info;
            private String class_icon;
            private String class_title;
            private int class_type;

            public int getClass_type() {
                return class_type;
            }

            public void setClass_type(int class_type) {
                this.class_type = class_type;
            }

            public DataBean getData() {
                return data;
            }

            public void setData(DataBean data) {
                this.data = data;
            }

            public String getClass_info() {
                return class_info;
            }

            public void setClass_info(String class_info) {
                this.class_info = class_info;
            }

            public String getClass_icon() {
                return class_icon;
            }

            public void setClass_icon(String class_icon) {
                this.class_icon = class_icon;
            }

            public String getClass_title() {
                return class_title;
            }

            public void setClass_title(String class_title) {
                this.class_title = class_title;
            }

            @XStreamAlias("data")
            public static class DataBean {
                /**
                 * item_info : item_info1
                 * item_detail : item_detail1
                 * item_name : item_name1
                 * item_title : item_title1
                 * item_icon : item_icon1
                 * item_num : item_num1
                 */

                @XStreamImplicit(itemFieldName = "item")
                private List<ItemBean> item;

                private List<String> itemToPinyin = new ArrayList<>();

                public List<ItemBean> getItem() {
                    return item;
                }

                public void setItem(List<ItemBean> item) {
                    this.item = item;
                }

                public List<String> getItemToPinyin() {
                    return itemToPinyin;
                }

                public void setItemToPinyin(List<String> itemToPinyin) {
                    this.itemToPinyin = itemToPinyin;
                }

                public static class ItemBean {
                    private String item_info;
                    private String item_detail;
                    private String item_name;
                    private String item_title;
                    private String item_icon;
                    private String item_num;

                    public String getItem_info() {
                        return item_info;
                    }

                    public void setItem_info(String item_info) {
                        this.item_info = item_info;
                    }

                    public String getItem_detail() {
                        return item_detail;
                    }

                    public void setItem_detail(String item_detail) {
                        this.item_detail = item_detail;
                    }

                    public String getItem_name() {
                        return item_name;
                    }

                    public void setItem_name(String item_name) {
                        this.item_name = item_name;
                    }

                    public String getItem_title() {
                        return item_title;
                    }

                    public void setItem_title(String item_title) {
                        this.item_title = item_title;
                    }

                    public String getItem_icon() {
                        return item_icon;
                    }

                    public void setItem_icon(String item_icon) {
                        this.item_icon = item_icon;
                    }

                    public String getItem_num() {
                        return item_num;
                    }

                    public void setItem_num(String item_num) {
                        this.item_num = item_num;
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return banner_entity.toString() + "\n"
                + class_entity.toString();
    }
}
