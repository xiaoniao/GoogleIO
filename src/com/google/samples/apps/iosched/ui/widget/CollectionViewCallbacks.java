package com.google.samples.apps.iosched.ui.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

// CollectionView回调接口
public interface CollectionViewCallbacks {

    // 实例化header
    View newCollectionHeaderView(Context context, ViewGroup parent);

    // 绑定header
    void bindCollectionHeaderView(Context context, View view, int groupId, String headerLabel);

    // 实例化item
    View newCollectionItemView(Context context, int groupId, ViewGroup parent);

    // 绑定item
    void bindCollectionItemView(Context context, View view, int groupId, int indexInGroup, int dataIndex, Object tag);

}
