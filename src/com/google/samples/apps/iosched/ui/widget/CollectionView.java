package com.google.samples.apps.iosched.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.google.samples.apps.iosched.R;
import com.google.samples.apps.iosched.util.Lists;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import static com.google.samples.apps.iosched.util.LogUtils.LOGD;
import static com.google.samples.apps.iosched.util.LogUtils.LOGW;

public class CollectionView extends ListView {

    private static final String     TAG                     = "CollectionView";

    private static final int        BUILTIN_VIEWTYPE_HEADER = 0;
    private static final int        BUILTIN_VIEWTYPE_COUNT  = 1;

    private Inventory               mInventory              = new Inventory();
    private CollectionViewCallbacks mCallbacks              = null;
    private int                     mContentTopClearance    = 0;
    private int                     mInternalPadding;
    private MultiScrollListener     mMultiScrollListener;

    // 构造方法
    public CollectionView(Context context) {
        this(context, null);
    }

    // 构造方法
    public CollectionView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    // 构造方法
    public CollectionView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        // 设置ListView属性
        setAdapter(new MyListAdapter());
        setDivider(null);
        setDividerHeight(0);
        setItemsCanFocus(false);
        setChoiceMode(ListView.CHOICE_MODE_NONE);
        setSelector(android.R.color.transparent);

        // 获得自定义xml值
        if (attrs != null) {
            final TypedArray xmlArgs = context.obtainStyledAttributes(attrs, R.styleable.CollectionView, defStyle, 0);
            mInternalPadding = xmlArgs.getDimensionPixelSize(R.styleable.CollectionView_internalPadding, 0);
            mContentTopClearance = xmlArgs.getDimensionPixelSize(R.styleable.CollectionView_contentTopClearance, 0);
        }
    }

    // 更新listView,默认带动画
    public void updateInventory(final Inventory inv) {
        updateInventory(inv, true);
    }

    // 更新ListView
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void updateInventory(final Inventory inv, boolean animate) {
        if (animate == true) {
            setAlpha(0);
            updateInventoryImmediate(inv, animate);
            doFadeInAnimation();
        } else {
            updateInventoryImmediate(inv, animate);
        }
    }

    private void updateInventoryImmediate(Inventory inv, boolean animate) {
        mInventory = new Inventory(inv);
        notifyAdapterDataSetChanged();
        if (animate) {
            startLayoutAnimation();
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    private void doFadeInAnimation() {
        setAlpha(0);
        animate().setDuration(250).alpha(1);
    }

    /** 设置回调事件 **/
    public void setCollectionAdapter(CollectionViewCallbacks adapter) {
        mCallbacks = adapter;
    }

    /** 刷新适配器 **/
    private void notifyAdapterDataSetChanged() {
        // We have to set up a new adapter (as opposed to just calling notifyDataSetChanged()
        // because we might need MORE view types than before, and ListView isn't prepared to
        // handle the case where its existing adapter suddenly needs to increase the number of
        // view types it needs.
        // 必须是设置适配器而不是刷新适配器的方式
        setAdapter(new MyListAdapter());
    }

    public void setContentTopClearance(int clearance) {
        if (mContentTopClearance != clearance) {
            mContentTopClearance = clearance;
            setPadding(getPaddingLeft(), mContentTopClearance, getPaddingRight(), getPaddingBottom());
            notifyAdapterDataSetChanged();
        }
    }

    private class RowComputeResult {
        int            row;        // 行号
        boolean        isHeader;   // 是否头部
        int            groupId;    // 组id
        InventoryGroup group;      // 组
        int            groupOffset; // 组偏移量
    }

    private boolean computeRowContent(int row, RowComputeResult result) {
        int curRow = 0;
        int posInGroup = 0;
        for (InventoryGroup group : mInventory.mGroups) {
            if (group.mShowHeader) {
                if (curRow == row) {
                    // row is a group header!
                    result.row = row;
                    result.isHeader = true;
                    result.groupId = group.mGroupId;
                    result.group = group;
                    result.groupOffset = -1;
                    return true;
                }
                curRow++;
            }
            posInGroup = 0;
            while (posInGroup < group.mItemCount) {
                if (curRow == row) {
                    // this is the row we are looking for
                    result.row = row;
                    result.isHeader = false;
                    result.groupId = group.mGroupId;
                    result.group = group;
                    result.groupOffset = posInGroup;
                    return true;
                }
                // advance to next row
                posInGroup += group.mDisplayCols;
                curRow++;
            }
        }
        return false;
    }

    // 适配器
    protected class MyListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            int rowCount = 0;
            for (InventoryGroup group : mInventory.mGroups) {
                int thisGroupRowCount = group.getRowCount();
                rowCount += thisGroupRowCount;
            }
            return rowCount;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int row, View convertView, ViewGroup parent) {
            return getRowView(row, convertView, parent);
        }

        @Override
        public int getItemViewType(int row) {
            return getRowViewType(row);
        }

        @Override
        public int getViewTypeCount() {
            return BUILTIN_VIEWTYPE_COUNT + mInventory.mGroups.size();
        }

    }

    private View getRowView(int row, View convertView, ViewGroup parent) {
        if (computeRowContent(row, mRowComputeResult)) {
            return makeRow(convertView, mRowComputeResult, parent);
        } else {
            Log.e(TAG, "Invalid row passed to getView: " + row);
            return convertView != null ? convertView : new View(getContext());
        }
    }

    RowComputeResult mRowComputeResult = new RowComputeResult();

    private int getRowViewType(int row) {
        if (computeRowContent(row, mRowComputeResult)) {
            int type;
            if (mRowComputeResult.isHeader) {
                type = BUILTIN_VIEWTYPE_HEADER;
            } else {
                type = BUILTIN_VIEWTYPE_COUNT + mInventory.getGroupIndex(mRowComputeResult.groupId);
            }
            return type;
        } else {
            Log.e(TAG, "Invalid row passed to getItemViewType: " + row);
            return 0;
        }
    }

    @Override
    public void setOnScrollListener(OnScrollListener listener) {
        if (mMultiScrollListener == null) {
            mMultiScrollListener = new MultiScrollListener();
            super.setOnScrollListener(mMultiScrollListener);
        }
        mMultiScrollListener.addOnScrollListener(listener);
    }

    private View makeRow(View view, RowComputeResult rowInfo, ViewGroup parent) {
        if (mCallbacks == null) {
            Log.e(TAG, "Call to makeRow without an adapter installed");
            return view != null ? view : new View(getContext());
        }

        // Notice that view types are tied to a specific instance of mInventory by hashcode,
        // so when mInventory is updated, we don't attempt to reuse views that were used for
        // a previous incarnation of mInventory (the views may be incompatible).
        String desiredViewType = mInventory.hashCode() + "." + getRowViewType(rowInfo.row);
        String actualViewType = (view != null && view.getTag() != null) ? view.getTag().toString() : "";
        if (!desiredViewType.equals(actualViewType)) {
            // We can't recycle this view. We have to make a new one.
            view = null;
        }

        if (rowInfo.isHeader) {
            if (view == null) {
                view = mCallbacks.newCollectionHeaderView(getContext(), parent);
            }
            mCallbacks.bindCollectionHeaderView(getContext(), view, rowInfo.groupId, rowInfo.group.mHeaderLabel);
        } else {
            view = makeItemRow(view, rowInfo);
        }

        view.setTag(desiredViewType);
        return view;
    }

    private View makeItemRow(View convertView, RowComputeResult rowInfo) {
        return (convertView == null) ? makeNewItemRow(rowInfo) : recycleItemRow(convertView, rowInfo);
    }

    private static class EmptyView extends View {
        private EmptyView(Context ctx) {
            super(ctx);
        }
    }

    private View getItemView(RowComputeResult rowInfo, int column, View view, ViewGroup parent) {
        int indexInGroup = rowInfo.groupOffset + column;
        if (indexInGroup >= rowInfo.group.mItemCount) {
            // out of bounds, so use an empty view
            if (view != null && view instanceof EmptyView) {
                return view;
            }
            view = new EmptyView(getContext());
            view.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            return view;
        }

        if (view == null || view instanceof EmptyView) {
            view = mCallbacks.newCollectionItemView(getContext(), rowInfo.groupId, parent);
        }

        mCallbacks.bindCollectionItemView(getContext(), view, rowInfo.groupId, indexInGroup,
                rowInfo.group.getDataIndex(indexInGroup), rowInfo.group.getItemTag(rowInfo.groupOffset + column));
        return view;
    }

    private LinearLayout.LayoutParams setupLayoutParams(View view) {
        LinearLayout.LayoutParams viewLayoutParams;
        if (view.getLayoutParams() instanceof LinearLayout.LayoutParams) {
            viewLayoutParams = (LinearLayout.LayoutParams)view.getLayoutParams();
        } else {
            // This shouldn't happen... but if it does, let's work around it as well as we can.
            LOGW(TAG, "Unexpected class for collection view item's layout params: "
                    + view.getLayoutParams().getClass().getName());
            viewLayoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        }
        viewLayoutParams.leftMargin = mInternalPadding / 2;
        viewLayoutParams.rightMargin = mInternalPadding / 2;
        viewLayoutParams.bottomMargin = mInternalPadding;
        viewLayoutParams.width = LayoutParams.MATCH_PARENT;
        viewLayoutParams.weight = 1.0f;
        view.setLayoutParams(viewLayoutParams);
        return viewLayoutParams;
    }

    private View makeNewItemRow(RowComputeResult rowInfo) {
        LinearLayout ll = new LinearLayout(getContext());
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        ll.setLayoutParams(params);

        int i;
        for (i = 0; i < rowInfo.group.mDisplayCols; i++) {
            View view = getItemView(rowInfo, i, null, ll);
            LinearLayout.LayoutParams viewLayoutParams = setupLayoutParams(view);
            ll.addView(view, viewLayoutParams);
        }

        return ll;
    }

    private View recycleItemRow(View convertView, RowComputeResult rowInfo) {
        int i;
        LinearLayout ll = (LinearLayout)convertView;
        for (i = 0; i < rowInfo.group.mDisplayCols; i++) {
            View view = ll.getChildAt(i);
            View newView = getItemView(rowInfo, i, view, ll);
            if (view != newView) {
                LinearLayout.LayoutParams thisViewParams = setupLayoutParams(newView);
                ll.removeViewAt(i);
                ll.addView(newView, i, thisViewParams);
            }
        }
        return ll;
    }

    // 收藏
    public static class Inventory {

        // 集合
        private ArrayList<InventoryGroup> mGroups = new ArrayList<InventoryGroup>();

        // 构造方法
        public Inventory() {}

        // 构造方法
        public Inventory(Inventory copyFrom) {
            for (InventoryGroup group : copyFrom.mGroups) {
                mGroups.add(group);
            }
        }

        public void addGroup(InventoryGroup group) {
            if (group.mItemCount > 0) {
                mGroups.add(new InventoryGroup(group));
            }
        }

        public int getTotalItemCount() {
            int total = 0;
            for (InventoryGroup group : mGroups) {
                total += group.mItemCount;
            }
            return total;
        }

        public int getGroupCount() {
            return mGroups.size();
        }

        public int getGroupIndex(int groupId) {
            for (int i = 0; i < mGroups.size(); i++) {
                if (mGroups.get(i).mGroupId == groupId) {
                    return i;
                }
            }
            return -1;
        }
    }

    // 循环遍历执行滚动事件onScrollListener -->实现滚动接口，多个滚动接口实例，依次执行
    private static class MultiScrollListener implements OnScrollListener {

        // 集合
        private final Set<OnScrollListener> children = new HashSet<OnScrollListener>();

        // 添加监听器实例
        public void addOnScrollListener(OnScrollListener listener) {
            children.add(listener);
        }

        @Override
        public void onScrollStateChanged(AbsListView absListView, int i) {
            for (OnScrollListener listener : children) {
                listener.onScrollStateChanged(absListView, i);
            }
        }

        @Override
        public void onScroll(AbsListView absListView, int i, int i2, int i3) {
            for (OnScrollListener listener : children) {
                listener.onScroll(absListView, i, i2, i3);
            }
        }
    }

    // 实体类 收藏组
    public static class InventoryGroup implements Cloneable {

        private int                  mGroupId               = 0;
        private boolean              mShowHeader            = false;
        private String               mHeaderLabel           = "";
        private int                  mDataIndexStart        = 0;
        private int                  mDisplayCols           = 1;
        private int                  mItemCount             = 0;
        private SparseArray<Object>  mItemTag               = new SparseArray<Object>();
        private SparseArray<Integer> mItemCustomDataIndices = new SparseArray<Integer>();

        // 构造方法
        public InventoryGroup(int groupId) {
            mGroupId = groupId;
        }

        // 构造方法
        public InventoryGroup(InventoryGroup copyFrom) {
            mGroupId = copyFrom.mGroupId;
            mShowHeader = copyFrom.mShowHeader;
            mDataIndexStart = copyFrom.mDataIndexStart;
            mDisplayCols = copyFrom.mDisplayCols;
            mItemCount = copyFrom.mItemCount;
            mHeaderLabel = copyFrom.mHeaderLabel;
            mItemTag = Lists.cloneSparseArray(copyFrom.mItemTag); // 拷贝
            mItemCustomDataIndices = Lists.cloneSparseArray(copyFrom.mItemCustomDataIndices);// 拷贝
        }

        public InventoryGroup setShowHeader(boolean showHeader) {
            mShowHeader = showHeader;
            return this;
        }

        public InventoryGroup setHeaderLabel(String label) {
            mHeaderLabel = label;
            return this;
        }

        public String getHeaderLabel() {
            return mHeaderLabel;
        }

        public InventoryGroup setDataIndexStart(int dataIndexStart) {
            mDataIndexStart = dataIndexStart;
            return this;
        }

        public InventoryGroup setCustomDataIndex(int groupIndex, int customDataIndex) {
            mItemCustomDataIndices.put(groupIndex, customDataIndex);
            return this;
        }

        public int getDataIndex(int indexInGroup) {
            return mItemCustomDataIndices.get(indexInGroup, mDataIndexStart + indexInGroup);
        }

        public InventoryGroup setDisplayCols(int cols) {
            mDisplayCols = cols > 1 ? cols : 1;
            return this;
        }

        public InventoryGroup setItemCount(int count) {
            mItemCount = count;
            return this;
        }

        public InventoryGroup incrementItemCount() {
            mItemCount++;
            return this;
        }

        public InventoryGroup setItemTag(int index, Object tag) {
            mItemTag.put(index, tag);
            return this;
        }

        public InventoryGroup addItemWithTag(Object tag) {
            mItemCount++;
            setItemTag(mItemCount - 1, tag);
            return this;
        }

        public InventoryGroup addItemWithCustomDataIndex(int customDataIndex) {
            mItemCount++;
            setCustomDataIndex(mItemCount - 1, customDataIndex);
            return this;
        }

        public int getRowCount() {
            return (mShowHeader ? 1 : 0) + (mItemCount / mDisplayCols) + ((mItemCount % mDisplayCols > 0) ? 1 : 0);
        }

        public Object getItemTag(int i) {
            return mItemTag.get(i, null);
        }
    }
}
