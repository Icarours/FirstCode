package com.syl.firstcode.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.syl.firstcode.R;
import com.syl.firstcode.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bright on 2017/5/21.
 *
 * @Describe ContentProvider
 * Android5.0以后要添加动态权限,否则不能正常运行
 * @Called
 */

public class CPFragment extends BaseFragment {
    private static final int MY_CPFRAGMENT_PERMISSION_CODE = 4;
    List<String> mContacts = new ArrayList<>();
    private View mRootView;
    private ListView mLvContacts;

    @Override
    public View initView() {
        mRootView = View.inflate(getContext(), R.layout.fragment_cp, null);
        mLvContacts = (ListView) mRootView.findViewById(R.id.lv_contacts);
        mLvContacts.setAdapter(new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, mContacts));
        //如果没有动态权限,需要手动获取
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED ||
                (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_CONTACTS)) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS}, MY_CPFRAGMENT_PERMISSION_CODE);
        }
        getContacts();
        return mRootView;
    }

    /**
     * 获取联系人
     */
    private void getContacts() {
        Cursor cursor = getActivity().getContentResolver()
                .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            int number = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER));
            mContacts.add(displayName + "--" + number);
        }
    }

    /**
     * 判断回调的权限
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CPFRAGMENT_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                getContacts();
            } else {
                Toast.makeText(getContext(), "permission was denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
