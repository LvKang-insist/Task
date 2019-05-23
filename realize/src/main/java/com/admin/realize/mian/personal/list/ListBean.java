package com.admin.realize.mian.personal.list;

import com.admin.task_core.delegate.CoKeDelegate;
import com.chad.library.adapter.base.entity.MultiItemEntity;


/**
 * @description: ${DESCRIPTION}
 */
public class ListBean implements MultiItemEntity {

    private int mItemType;
    private int mId;
    private String mText;
    private String mImage;
    private CoKeDelegate mCokeDelegate;
    private String mValue;

    @Override
    public int getItemType() {
        return mItemType;
    }

    private ListBean(int mItemType, int mId, String mText,
                     String mImage, CoKeDelegate mCokeDelegate, String mValue) {
        this.mItemType = mItemType;
        this.mId = mId;
        this.mText = mText;
        this.mImage = mImage;
        this.mCokeDelegate = mCokeDelegate;
        this.mValue = mValue;
    }

    public int getId() {
        return mId;
    }

    public String getText() {
        if (mText == null) {
            return "";
        }
        return mText;
    }

    public String geImage() {
        return mImage;
    }

    public CoKeDelegate getCokeDelegate() {
        return mCokeDelegate;
    }

    public String getValue() {
        if (mValue == null) {
            return "";
        }
        return mValue;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public void setmText(String mText) {
        this.mText = mText;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }

    public void setmCokeDelegate(CoKeDelegate mCokeDelegate) {
        this.mCokeDelegate = mCokeDelegate;
    }

    public void setmValue(String mValue) {
        this.mValue = mValue;
    }

    public static final class Builder {
        private int mItemType;
        private int mId;
        private String mText;
        private String mImage;
        private CoKeDelegate mCokeDelegate;
        private String mValue;

        public Builder setmItemType(int mItemType) {
            this.mItemType = mItemType;
            return this;
        }

        public Builder setmId(int mId) {
            this.mId = mId;
            return this;
        }

        public Builder setmText(String mText) {
            this.mText = mText;
            return this;
        }

        public Builder setmImage(String mImage) {
            this.mImage = mImage;
            return this;
        }

        public Builder setmCokeDelegate(CoKeDelegate mCokeDelegate) {
            this.mCokeDelegate = mCokeDelegate;
            return this;
        }

        public Builder setmValue(String mValue) {
            this.mValue = mValue;
            return this;
        }

        public ListBean build() {
            return new ListBean(mItemType, mId, mText, mImage, mCokeDelegate, mValue);
        }
    }
}
