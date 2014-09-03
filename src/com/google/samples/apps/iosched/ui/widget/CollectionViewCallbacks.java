package com.google.samples.apps.iosched.ui.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

// CollectionView�ص��ӿ�
public interface CollectionViewCallbacks {

    // ʵ����header
    View newCollectionHeaderView(Context context, ViewGroup parent);

    // ��header
    void bindCollectionHeaderView(Context context, View view, int groupId, String headerLabel);

    // ʵ����item
    View newCollectionItemView(Context context, int groupId, ViewGroup parent);

    // ��item
    void bindCollectionItemView(Context context, View view, int groupId, int indexInGroup, int dataIndex, Object tag);

}
