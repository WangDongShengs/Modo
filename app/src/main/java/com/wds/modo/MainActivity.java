package com.wds.modo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.wds.adapter.AppAdapter;
import com.wds.bean.InfoApp;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recycler_1;
    private RecyclerView recycler_2;
    private AppAdapter appAdapter1;
    private AppAdapter appAdapter2;
    private ArrayList<InfoApp> infoApp1;
    private ArrayList<InfoApp> infoApp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        getInstalledApplications(MainActivity.this);

    }

    private void initView() {
        //左
        recycler_1 = (RecyclerView) findViewById(R.id.recycler_1);
        recycler_1.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recycler_1.setLayoutManager(new LinearLayoutManager(this));
        infoApp1 = new ArrayList<>();
        appAdapter1 = new AppAdapter(this, infoApp1);
        recycler_1.setAdapter(appAdapter1);
        appAdapter1.setClickItem(new AppAdapter.ClickItem() {
            @Override
            public void item(int i) {
                //获取文件包名
                String packageName = infoApp1.get(i).getPackageName();
                startAPP(MainActivity.this,packageName);
            }
        });
        //右
        recycler_2 = (RecyclerView) findViewById(R.id.recycler_2);
        recycler_2.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recycler_2.setLayoutManager(new LinearLayoutManager(this));
        infoApp2 = new ArrayList<>();
        appAdapter2 = new AppAdapter(this, infoApp2);
        recycler_2.setAdapter(appAdapter2);
        appAdapter2.setClickItem(new AppAdapter.ClickItem() {
            @Override
            public void item(int i) {
                //获取文件包名
                String packageName = infoApp2.get(i).getPackageName();
                startAPP(MainActivity.this,packageName);
            }
        });
    }
    public void startAPP(Context context, String appPackageName) {
        try {
            //跳转到App应用
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(appPackageName);
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "无法打开该应用", Toast.LENGTH_SHORT).show();
        }
    }
    public void getInstalledApplications(Context context) {
        // 获取到包的管理者
        PackageManager packageManager = context.getPackageManager();
        // 获取手机所有的安装程序
        List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);
        // 遍历获取到每个应用程序的信息
        for (PackageInfo packageInfo : installedPackages) {
            // 获取到程序的包名
            String packageName = packageInfo.packageName;
           /* // 获取到版本号
            String versionName = packageInfo.versionName;*/
            //获取应用信息
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            // 获取程序名
            String appName = applicationInfo.loadLabel(packageManager).toString();
            // 获取到程序图标
            Drawable AppIcon = applicationInfo.loadIcon(packageManager);

            // 获取程序的所有标签 用来获取 以下信息
            int flags = applicationInfo.flags;
            // 判断是不是用户程序
            // 系统程序
            if ((flags & ApplicationInfo.FLAG_SYSTEM) == ApplicationInfo.FLAG_SYSTEM) {
                InfoApp app = new InfoApp(appName, AppIcon, packageName);
                infoApp1.add(app);
                appAdapter1.notifyDataSetChanged();

            }  // 用户程序
            else {
                InfoApp appBean = new InfoApp(appName, AppIcon, packageName);
                infoApp2.add(appBean);
                appAdapter2.notifyDataSetChanged();

            }
        }

    }
}
