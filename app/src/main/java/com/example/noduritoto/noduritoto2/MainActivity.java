package com.example.noduritoto.noduritoto2;

import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener {

    String selectedPath = "start";
    Intent intent;

    // 파일리스트 관
    String mRoot = "";
    String mPath = "";
    TextView mTextMsg;
    ListView mListFile;
    ArrayList<String> mArFile;
    // 버튼 이벤트 핸들러, http://fishpoint.tistory.com/707 3번 예제
    Button.OnClickListener mClickListener = new View.OnClickListener() {

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.addButton:
                    /*
                    Log.d("OnClickListener", "click add button");

                    Intent intent = getIntent();
                    Log.d("intent", " main2 intent" + getIntent());

                    intent = new Intent(MainActivity.this, FileBrowserActivity.class);
                    Log.d("intent", " main3 intent" + intent);
                    startActivityForResult(intent, 1);
                    // 액티비티 실행
                    */
                    intent = new Intent(MainActivity.this, Test.class);
                    startActivity(intent);


                    break;
                case R.id.deleteButton:
                    Log.d("OnClickListener", "click del button");
                    Log.d("intent", " main delete intent" + getIntent());
                    // 액티비티 실행

                    intent = new Intent(MainActivity.this, FileBrowserActivity.class);
                    startActivityForResult(intent, 1);


                    break;
                case R.id.editButton:
                    Log.d("OnClickListener", "click edit button");
                    //Log.d("sjenfl", "sjenfl " + intent.getStringExtra("pathname"));

                    intent = new Intent(MainActivity.this, TagInfoActivity.class);
                    Log.d("selectedPath", selectedPath);
                    intent.putExtra("selectedPath", selectedPath);
                    startActivity(intent);

                    //onActivityResult(RESULT_OK, 1, getIntent());
                    // 액티비티 실행

                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("intent", " main1 intent" + getIntent());

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // 이미지 버튼 온클릭리스너에 등
        setImageButtons();

        // for test

        /*
        Write testForWrite = new Write();
        testForWrite.write();
        */
        Read testRead = new Read();
        testRead.readTest("/storage/extSdCard/noduritoto/testV1.mp3");

        // 파일리스트

        mTextMsg = (TextView) findViewById(R.id.textMessage);
        mRoot = "/storage/extSdCard/noduritoto";
        String[] fileList = getFileList(mRoot);
        for (int i = 0; i < fileList.length; i++)
            Log.d("tag", fileList[i]);
        // ListView 초기화
        initListView();
        fileList2Array(fileList);


    }

    public void setImageButtons() {
        findViewById(R.id.addButton).setOnClickListener(mClickListener);
        findViewById(R.id.deleteButton).setOnClickListener(mClickListener);
        findViewById(R.id.editButton).setOnClickListener(mClickListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK)
        //noduritoto
// 액티비티가 정상적으로 종료되었을 경우
        {
            if (requestCode == 1)

            {

                Log.d("activity", data.getStringExtra("pathname"));
                mPath = data.getStringExtra("pathname");
                String[] fileList = getFileList(mPath);
                for (int i = 0; i < fileList.length; i++)
                    Log.d("tag", fileList[i]);
                // ListView 초기화
                initListView();
                fileList2Array(fileList);

            }
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logtest) {
            Log.d("path", " main path" + selectedPath);
            return true;
        } else if (id == R.id.action_exit) {
            moveTaskToBack(true);
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Icon icon;

        if (id == R.id.nav_Help) {
            Intent intent = new Intent(MainActivity.this, HelpActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_Exit) {
            moveTaskToBack(true);
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void initListView() {
        mArFile = new ArrayList<String>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mArFile);

        mListFile = (ListView) findViewById(R.id.listFile);
        mListFile.setAdapter(adapter);
        mListFile.setOnItemClickListener(this);
    }

    // ListView 항목 선택 이벤트 함수
    public void onItemClick(AdapterView parent, View view, int position, long id) {
        String strItem = mArFile.get(position);
        // 선택된 폴더의 전체 경로를 구한다
        String strPath = getAbsolutePath(strItem);
        selectedPath = strPath;
        // 선택된 폴더에 존재하는 파일 목록을 구한다
        String[] fileList = getFileList(strPath);
        // 파일 목록을 ListView 에 표시
        fileList2Array(fileList);
    }

    // 폴더명을 받아서 전체 경로를 반환하는 함수
    public String getAbsolutePath(String strFolder) {
        String strPath;
        // 이전 폴더일때
        if (strFolder == "..") {
            // 전체 경로에서 최하위 폴더를 제거
            int pos = mPath.lastIndexOf("/");
            strPath = mPath.substring(0, pos);
        } else
            strPath = mPath + "/" + strFolder;
        return strPath;
    }

    // SD 카드 장착 여부를 반환
    public boolean isSdCard() {
        String ext = Environment.getExternalStorageState();
        if (ext.equals(Environment.MEDIA_MOUNTED) == false) {
            Toast.makeText(this, "SD Card does not exist", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // 특정 폴더의 파일 목록을 구해서 반환
    public String[] getFileList(String strPath) {
        // 폴더 경로를 지정해서 File 객체 생성
        File fileRoot = new File(strPath);
        // 해당 경로가 폴더가 아니라면 함수 탈출
        if (fileRoot.isDirectory() == false)
            return null;
        mPath = strPath;
        mTextMsg.setText(mPath);
        // 파일 목록을 구한다
        String[] fileList = fileRoot.list();
        return fileList;
    }

    // 파일 목록을 ListView 에 표시
    public void fileList2Array(String[] fileList) {
        if (fileList == null)
            return;

        mArFile.clear();
        // 현재 선택된 폴더가 루트 폴더가 아니라면
        if (mRoot.length() < mPath.length())
            // 이전 폴더로 이동하기 위해서 ListView 에 ".." 항목을 추가
            mArFile.add("..");

        for (int i = 0; i < fileList.length; i++) {
            Log.d("tag", fileList[i]);
            mArFile.add(fileList[i]);
        }
        ArrayAdapter adapter = (ArrayAdapter) mListFile.getAdapter();
        adapter.notifyDataSetChanged();
    }

    /*

    // 파일리스트
    public void initListView() {
        mArFile = new ArrayList<String>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mArFile);

        mListFile = (ListView)findViewById(R.id.listFile);
        mListFile.setAdapter(adapter);
        mListFile.setOnItemClickListener(this);
    }

    // ListView 항목 선택 이벤트 함수
    public void onItemClick(AdapterView parent, View view, int position, long id) {
        String strItem = mArFile.get(position);
        // 선택된 폴더의 전체 경로를 구한다
        String strPath = getAbsolutePath(strItem);
        // 선택된 폴더에 존재하는 파일 목록을 구한다
        String[] fileList = getFileList(strPath);
        // 파일 목록을 ListView 에 표시
        fileList2Array(fileList);
    }

    // 폴더명을 받아서 전체 경로를 반환하는 함수
    public String getAbsolutePath(String strFolder) {
        String strPath;


        File fileRoot = new File(strFolder);
        if(fileRoot.isDirectory() == false){
            return strFolder;
        }


        // 이전 폴더일때
        if( strFolder == ".." ) {
            // 전체 경로에서 최하위 폴더를 제거
            int pos = mPath.lastIndexOf("/");
            strPath = mPath.substring(0, pos);
        }
        else
            strPath = mPath + "/" + strFolder;
        return strPath;
    }

    // SD 카드 장착 여부를 반환
    public boolean isSdCard() {
        String ext = Environment.getExternalStorageState();
        if (ext.equals(Environment.MEDIA_MOUNTED) == false) {
            Toast.makeText(this, "SD Card does not exist", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // 특정 폴더의 파일 목록을 구해서 반환
    public String[] getFileList(String strPath) {
        // 폴더 경로를 지정해서 File 객체 생성
        File fileRoot = new File(strPath);
        // 해당 경로가 폴더가 아니라면 함수 탈출
        if( fileRoot.isDirectory() == false ) {
            if(mPath.compareTo("start")==0){
                String[] fileList = {" ", " "," "," "," "," ", " ", " ", " "};
                return fileList;
            }
            //return null;
        }

        mPath = strPath;
        mTextMsg.setText(mPath);
        // 파일 목록을 구한다
        String[] fileList = fileRoot.list();
        return fileList;
    }

    // 파일 목록을 ListView 에 표시 :http://onycomict.com/board/bbs/board.php?bo_table=class&wr_id=139
    public void fileList2Array(String[] fileList) {
        if( fileList == null ){
            return;
        }
            //
        mArFile.clear();
        // 현재 선택된 폴더가 루트 폴더가 아니라면
        if( mRoot.length() < mPath.length() )
            // 이전 폴더로 이동하기 위해서 ListView 에 ".." 항목을 추가
            mArFile.add("..");

        for(int i=0; i < fileList.length; i++) {
            Log.d("tag", fileList[i]);
            mArFile.add(fileList[i]);
        }
        ArrayAdapter adapter = (ArrayAdapter)mListFile.getAdapter();
        adapter.notifyDataSetChanged();
    }

    */

    // ListView 초기화

}
