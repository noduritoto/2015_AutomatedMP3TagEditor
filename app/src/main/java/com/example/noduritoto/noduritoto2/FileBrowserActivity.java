package com.example.noduritoto.noduritoto2;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class FileBrowserActivity extends ListActivity {

    /* item은 탐색기에 표시될 내용, path는 item 클릭시 이동할 경로이다. */
    private List<String> item = null;
    private List<String> path = null;
    /* 루트 디렉토리 설정 */
    private String root = "/sdcard/";
    /* 현재 경로를 저장해주는 변수 myPath */
    private TextView myPath;
    private boolean isRoot = true;

    private String selectedPath;
    Button.OnClickListener mClickListener = new View.OnClickListener() {

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.okayInBrowser:
                    Log.d("OnClickListener", "click okayInBrowser button");
                    Intent intent = new Intent();
                    Log.d("intent", " browser intent" + intent);

                    intent.putExtra("pathname", selectedPath);
                    Log.d("OnClickListener", "selected path name" + selectedPath);
                    setResult(RESULT_OK, intent);
                    finish();


                    // 액티비티 실행

                    break;
                case R.id.cancleInBrowser:
                    Log.d("OnClickListener", "click cancleInBrowser button");
                    // 액티비티 실행
                    finish();

                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_browser);
        selectedPath = new String();

        myPath = (TextView) findViewById(R.id.path);
        getDir(root);

        findViewById(R.id.okayInBrowser).setOnClickListener(mClickListener);
        findViewById(R.id.cancleInBrowser).setOnClickListener(mClickListener);

    }

    /**
     * 주어진 argument(파일 경로)에 대한 탐색기 뷰 생성
     *
     * @param dirPath
     */
    private void getDir(String dirPath) {
        isRoot = dirPath.compareTo("/sdcard") == 0 || dirPath.compareTo("/sdcard/") == 0;
  /* myPath를  넘어온 매개변수로 설정*/
        myPath.setText("Location: " + dirPath);
        /*
        Log.d("dirPath", "dirPath :" + dirPath);
        Log.d("dirPath", "isRoot :" + isRoot);
        Log.d("dirPath", "/sdcard :" + dirPath.compareTo("/sdcard") );
        Log.d("dirPath", "/sdcard/ :" + dirPath.compareTo("/sdcard/") );
        */

        selectedPath = dirPath;
        Log.d("selectedPath", selectedPath);


        item = new ArrayList<String>();
        path = new ArrayList<String>();

  /* 주어진 주소값에 대한 File 객체 생성 및 하위 디렉토리/파일 행렬 생성 */
        File f = new File(dirPath);
        File[] files = f.listFiles();

  /* item, path 추가 */
        if (!dirPath.equals(root)) {
   /* root 폴더 아이템 및 경로 */
            item.add(root);
            path.add(root);
            Log.d("path", "index :" + path.size());

   /* 상위 디렉토리 및 경로 */
            item.add("../");
            path.add(f.getParent());
        }

  /* myPath의 하위 디렉토리/파일의 item, path 추가 */
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            path.add(file.getPath());

            if (file.isDirectory()) {
                item.add(file.getName() + "/");
                Log.d("path", "index :" + path.size());
            } else {
                item.add(file.getName());
                Log.d("path", "index :" + path.size());
            }
        }

     /*
      * ArrayAdapter를 통해 myPath에서의 파일 탐색기 뷰 생성
      * ArrayAdapter에 대한 자세한 내용은
      * android dev 페이지나 기타 블로그들 참고
      */
        ArrayAdapter<String> fileList = new ArrayAdapter<String>(this, R.layout.row, item);
        setListAdapter(fileList);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
  /*
   * 현재 뷰가 root 디렉토리가 아닌 경우,
   * path.get(0) = root 경로
   * path.get(1) = 상위 디렉토리 경로
   * path.get(2) = getDir에서 저장된 하위 디렉토리/파일 경로
   * ...
   */
        File file = new File(path.get(position));

  /* 만약 디렉토리를 클릭한 거라면 */
        if (file.isDirectory()) {
   /*
    * file.canRead()는 파일에 대한 접근 권한을 확인 하는 메소드
    * canRead() 대신에 canExecute(), canRead()를 사용할 수도 있다.
    */
            if (file.canRead())
    /* 디렉토리 클릭시 하위 디렉토리/파일들을 뷰로 구성해주어야 한다 */
                getDir(path.get(position));
            else {
                new AlertDialog.Builder(this)
                        .setIcon(R.drawable.icon_modifiedsize)
                        .setTitle("[" + file.getName() + "] folder can't be read!")
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        //  TODO Auto-generated method stub
                                    }
                                }).show();
            }
        }
  /* 만약 디렉토리가 아닌 파일을 클릭한 거라면 */
        else {
   /*
    * 본 예제에서는 파일 클릭시 다이얼로그를 띄우는 Action을 취한다.
    * 의도하는 Action이 있다면 아래의 코드를 지우고 원하는 코드로 대체해주면 된다.
    */
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.icon_modifiedsize)
                    .setTitle("This is not a Directory")
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                }
                            }).show();
        }
    }

    public void onBackPressed() {


        if (!isRoot) {
            getDir(path.get(1));
            return;

        } else {
            //super.onBackPressed();
            finish();
        }


    }


}
