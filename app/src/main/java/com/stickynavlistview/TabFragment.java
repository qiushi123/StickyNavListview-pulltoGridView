package com.stickynavlistview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.stickynavlistview.listenter.AutoLoadListener;

import java.util.ArrayList;
import java.util.List;

public class TabFragment extends Fragment {
    public static final String TITLE = "title";
    private String mTitle = "Defaut Value";
    private GridView mListView;
    // private TextView mTextView;
    private List<String> mDatas = new ArrayList<String>();

    private int pageNum = -1;

    public TabFragment(int page) {
        pageNum = page;
    }

    //    @Override
    //    public void onCreate(Bundle savedInstanceState) {
    //        super.onCreate(savedInstanceState);
    //        if (getArguments() != null) {
    //            mTitle = getArguments().getString(TITLE);
    //        }
    //    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        mListView = (GridView) view
                .findViewById(R.id.id_stickynavlayout_innerscrollview);
        // mTextView = (TextView) view.findViewById(R.id.id_info);
        // mTextView.setText(mTitle);
        AutoLoadListener autoLoadListener = new AutoLoadListener(callBack);
        mListView.setOnScrollListener(autoLoadListener);
        initPage();
        return view;

    }

    AutoLoadListener.AutoLoadCallBack callBack = new AutoLoadListener.AutoLoadCallBack() {

        public void execute() {
            //            Utils.showToast("已经拖动至底部");
            loadSpareItems(currentPage + 1);
        }

    };

    private int currentPage = 1;

    private void initPage() {
        switch (pageNum) {
            case 0:
                mTitle = "GridView的上拉加载";
                mListView.setNumColumns(2);
                loadSpareItems(currentPage);
                break;
            case 1:
                mTitle = "GridView的上拉加载";
                mListView.setNumColumns(3);
                loadSpareItems(currentPage);
                break;
            case 2:
                mTitle = "ListView的上拉加载";
                loadSpareItems(currentPage);
                break;
            case 3:
                mTitle = "ListView的上拉加载";
                loadSpareItems(currentPage);
                break;
        }
    }

    private void loadSpareItems(final int page) {
        if (page == 1) {
            for (int i = 0; i < 10; i++) {
                mDatas.add(mTitle + " -> " + i);
            }
        } else if (page > 1 && page <= 10) {
            currentPage = page;
            for (int i = (page - 1) * 10; i < (page * 10); i++) {
                mDatas.add(mTitle + " -> " + i);
            }
            Toast.makeText(getActivity(), "加载更多成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "没有更多数据啦", Toast.LENGTH_SHORT).show();
        }

        mListView.setAdapter(new ArrayAdapter<String>(getActivity(),
                R.layout.item_mine, R.id.id_info, mDatas) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                //Log.e("tag", "convertView = " + convertView);
                return super.getView(position, convertView, parent);
            }
        });
    }

    //
    //    public static TabFragment newInstance(String title) {
    //        TabFragment tabFragment = new TabFragment();
    //        Bundle bundle = new Bundle();
    //        bundle.putString(TITLE, title);
    //        tabFragment.setArguments(bundle);
    //        return tabFragment;
    //    }

}
