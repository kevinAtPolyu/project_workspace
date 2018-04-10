package com.stockai.craftdata.stockbook;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private static Set<String> stockIds_ = new HashSet();
    private final static String stockIdsKey_ = "StockIds";
    private static String selectedStockItem = "";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_news);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_person);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try{
            fetchIndexData();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        loadWatchList();
        refreshStocks();
        mTextMessage = (TextView) findViewById(R.id.message);
        Button button = (Button) findViewById(R.id.btn_add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(addStock(v)) {
                    ((EditText)findViewById(R.id.editText_stockId)).setText("");
                    Toast.makeText(getBaseContext(), "加入成功", Toast.LENGTH_SHORT).show();
                }
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    protected void loadWatchList(){
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String idsStr = sharedPref.getString(stockIdsKey_, "sz000001,sh600750,sh600570,sz300033");

        String[] ids = idsStr.split(",");
        stockIds_.clear();
        for (String id : ids) {
            stockIds_.add(id);
        }
    }

    protected void fetchIndexData() throws IOException{
        String url = "http://hq.sinajs.cn/list=s_sh000001,s_sz399001,s_sz399006";
        try{
            URL oburl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)oburl.openConnection();
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(in,"gbk"));
            String str = br.readLine();
            while(str!=null){
                Log.v("index***********get: ",str);
                String[] strs = str.split(",|\"");
                updateView(strs);
                str = br.readLine();
            }
        }
        catch(MalformedURLException e){
            e.printStackTrace();
        }
    }

    protected void updateView(String[] strs){
        int indexId=-1,changeId=-1;
        Resources resources = getResources();
        if(strs[1].equals(resources.getString(R.string.stock_sh_name))){
            indexId=R.id.stock_sh_index;
            changeId=R.id.stock_sh_change;
        }
        if(strs[1].equals(resources.getString(R.string.stock_sz_name))){
            indexId=R.id.stock_sz_index;
            changeId=R.id.stock_sz_change;
        }
        if(strs[1].equals(resources.getString(R.string.stock_chuang_name))){
            indexId=R.id.stock_chuang_index;
            changeId=R.id.stock_chuang_change;
        }
        TextView textView = (TextView)findViewById(indexId);
        if(Double.parseDouble(strs[3])>=0){
            textView.setTextColor(Color.RED);
            strs[2] = strs[2]+"↑";
            strs[3] = "+"+strs[3];
            strs[4] = "+"+strs[4];
        }
        else{
            textView.setTextColor(Color.GREEN);
            strs[2] = strs[2]+"↓";
            strs[3] = "-"+strs[3];
            strs[4] = "-"+strs[4];
        }
        textView.setText(strs[2]);
        textView.setTextSize(18);
        textView = (TextView)findViewById(changeId);
        textView.setText(strs[3]+"  "+strs[4]+"%");
    }

    private boolean refreshStocks(){
        String ids = "";
        Boolean res = false;
        for (String id : stockIds_){
            ids += id;
            ids += ",";
        }
        try {
            res = querySinaStocks(ids);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return res;
    }

    public boolean addStock(View view) {
        EditText editText = (EditText) findViewById(R.id.editText_stockId);
        String stockId = editText.getText().toString();
        if(stockId.length() != 6) {
            Toast.makeText(getBaseContext(), "输入代码长度不是6位数", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (stockId.startsWith("6")) {
            stockId = "sh" + stockId;
        } else if (stockId.startsWith("0") || stockId.startsWith("3")) {
            stockId = "sz" + stockId;
        } else {
            Toast.makeText(getBaseContext(), "非A股代码", Toast.LENGTH_SHORT).show();
            return false;
        }

        stockIds_.add(stockId);
        return refreshStocks();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();  // Always call the superclass

        saveStocksToPreferences();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        saveStocksToPreferences();

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    //save the watch list
    private void saveStocksToPreferences(){
        String ids = "";
        for (String id : stockIds_){
            ids += id;
            ids += ",";
        }
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(stockIdsKey_, ids);
        editor.commit();
    }

    public boolean querySinaStocks(String list)throws IOException{
        boolean res = true;
        String url ="http://hq.sinajs.cn/list=" + list;
        try{
            URL oburl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)oburl.openConnection();
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(in,"gbk"));

            //get table
            TableLayout table = (TableLayout)findViewById(R.id.stock_table);
            table.setStretchAllColumns(true);
            table.setShrinkAllColumns(true);
            table.removeAllViews();
            //construct title
            TableRow rowTitle = new TableRow(this);
            TextView nameTitle = new TextView(this);
            nameTitle.setText(getResources().getString(R.string.stock_name_title));
            rowTitle.addView(nameTitle);
            TextView nowTitle = new TextView(this);
            nowTitle.setGravity(Gravity.CENTER);
            nowTitle.setText(getResources().getString(R.string.stock_now_title));
            rowTitle.addView(nowTitle);
            TextView percentTitle = new TextView(this);
            percentTitle.setGravity(Gravity.CENTER);
            percentTitle.setText(getResources().getString(R.string.stock_increase_percent_title));
            rowTitle.addView(percentTitle);
            TextView increaseTitle = new TextView(this);
            increaseTitle.setGravity(Gravity.CENTER);
            increaseTitle.setText(getResources().getString(R.string.stock_increase_title));
            rowTitle.addView(increaseTitle);
            table.addView(rowTitle);


            String str = br.readLine();
            if(str==null){
                Toast.makeText(getBaseContext(), "No stocks in watch list", Toast.LENGTH_SHORT).show();
            }
            while(str!=null){
                Log.v("list***********get: ",str);
                String[] strs = str.split(",|\"");
//                updateView(strs);
                String id = strs[0].split("_|=")[2];
                String n = strs[1];
                if(n.isEmpty()){
                    Toast.makeText(getBaseContext(),"该代码不存在",Toast.LENGTH_SHORT).show();
                    stockIds_.remove(id);
                    res = false;
                    str = br.readLine();
                    continue;
                }
                Stock stock = new Stock(id,n,
                        strs[2],strs[3],strs[4],strs[5],strs[6],
                        strs[7],strs[8],
                        strs[9],strs[10],
                        strs[11],strs[12],//buy1
                        strs[13],strs[14],
                        strs[15],strs[16],
                        strs[17],strs[18],
                        strs[19],strs[20],
                        strs[21],strs[22],//sell1
                        strs[23],strs[24],
                        strs[25],strs[26],
                        strs[27],strs[28],
                        strs[29],strs[30],
                        strs[31],strs[32],//transaction date time
                        strs[33]);

                TableRow row = new TableRow(this);
                //display the name and id of one row stock
                LinearLayout nameId = new LinearLayout(this);
                nameId.setOrientation(LinearLayout.VERTICAL);
                TextView name_t = new TextView(this);
                name_t.setText(stock.getStock_name());
                nameId.addView(name_t);
                TextView id_t = new TextView(this);
                id_t.setTextSize(10);
                id_t.setText(stock.getStock_id());
                nameId.addView(id_t);
                row.addView(nameId);
                String i = stock.getStock_id().replaceAll("sh","");
                i = i.replaceAll("sz","");
                row.setId(Integer.parseInt(i));//set the row id as stock id
                //display the current price
                TextView now_t = new TextView(this);
                now_t.setGravity(Gravity.CENTER);
                now_t.setText(stock.getNow());
                row.addView(now_t);
                //display how much the stock grows
                TextView percent = new TextView(this);
                percent.setGravity(Gravity.CENTER);
                TextView increaseValue = new TextView(this);
                increaseValue.setGravity(Gravity.CENTER);
                Double dOpen = Double.parseDouble(stock.getToday_open());
                Double dB1 = Double.parseDouble(stock.getBuy1_s());
                Double dS1 = Double.parseDouble(stock.getSell1_s());
                if(dOpen == 0 && dB1 == 0 && dS1 == 0) {
                    percent.setText("--");
                    increaseValue.setText("--");
                }
                else{
                    Double dNow = Double.parseDouble(stock.getNow());
                    if(dNow == 0) {// before open
                        if(dS1 == 0) {
                            dNow = dB1;
                            now_t.setText(stock.getBuy1_s());
                        }
                        else {
                            dNow = dS1;
                            now_t.setText(stock.getSell1_s());
                        }
                    }
                    Double dYesterday = Double.parseDouble(stock.getYesterday_end());
                    Double dIncrease = dNow - dYesterday;
                    Double dPercent = dIncrease / dYesterday * 100;
                    percent.setText(String.format("%.2f", dPercent) + "%");
                    increaseValue.setText(String.format("%.2f", dIncrease));
                    int color = Color.BLACK;
                    if(dIncrease > 0) {
                        color = Color.RED;
                    }
                    else if(dIncrease < 0){
                        color = Color.GREEN;
                    }

                    now_t.setTextColor(color);
                    percent.setTextColor(color);
                    increaseValue.setTextColor(color);
                }
                row.addView(percent);
                row.addView(increaseValue);

                row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewGroup group = (ViewGroup) v;
                        ViewGroup nameId = (ViewGroup) group.getChildAt(0);
                        TextView idText = (TextView) nameId.getChildAt(1);
                        if (selectedStockItem.equals(idText.getText().toString())) {
                            v.setBackgroundColor(Color.WHITE);
                            selectedStockItem = "";
                        } else {
                            TableRow t=null;
                            if(selectedStockItem!="")
                                t= (TableRow)findViewById(Integer.parseInt(selectedStockItem.replaceAll("sh","").replaceAll("sz","")));
                            if(t!=null)
                                t.setBackgroundColor(Color.WHITE);
                            v.setBackgroundColor(Color.rgb(210, 233, 255));
                            selectedStockItem = idText.getText().toString();
                        }
                    }
                });

                //big order notification
                String text = "";
                String sBuy = getResources().getString(R.string.stock_buy);
                String sSell = getResources().getString(R.string.stock_sell);
                if(Double.parseDouble(stock.getBuy1() )>= 1000000) {
                    text += sBuy + "1:" + stock.getBuy1() + ",";
                }
                if(Double.parseDouble(stock.getBuy2() )>= 1000000) {
                    text += sBuy + "2:" + stock.getBuy2() + ",";
                }
                if(Double.parseDouble(stock.getBuy3() )>= 1000000) {
                    text += sBuy + "3:" + stock.getBuy3() + ",";
                }
                if(Double.parseDouble(stock.getBuy4() )>= 1000000) {
                    text += sBuy + "4:" + stock.getBuy4() + ",";
                }
                if(Double.parseDouble(stock.getBuy5() )>= 1000000) {
                    text += sBuy + "5:" + stock.getBuy5() + ",";
                }
                if(Double.parseDouble(stock.getSell1() )>= 1000000) {
                    text += sSell + "1:" + stock.getSell1() + ",";
                }
                if(Double.parseDouble(stock.getSell2() )>= 1000000) {
                    text += sSell + "2:" + stock.getSell2() + ",";
                }
                if(Double.parseDouble(stock.getSell3() )>= 1000000) {
                    text += sSell + "3:" + stock.getSell3() + ",";
                }
                if(Double.parseDouble(stock.getSell4() )>= 1000000) {
                    text += sSell + "4:" + stock.getSell4() + ",";
                }
                if(Double.parseDouble(stock.getSell5() )>= 1000000) {
                    text += sSell + "5:" + stock.getSell5() + ",";
                }
                if(text.length() > 0)
                    sendNotifation(Integer.parseInt(i), stock.getStock_name(), text);

                table.addView(row);
                str = br.readLine();
            }
        }
        catch(MalformedURLException e){
            e.printStackTrace();
        }
        return res;
    }

    public void sendNotifation(int id, String title, String text){
        NotificationCompat.Builder nBuilder =
                new NotificationCompat.Builder(this);
        nBuilder.setSmallIcon(R.drawable.ic_launcher_background);
        nBuilder.setContentTitle(title);
        nBuilder.setContentText(text);
        nBuilder.setVibrate(new long[]{100, 100, 100});
        nBuilder.setLights(Color.RED, 1000, 1000);

        NotificationManager notifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notifyMgr.notify(id, nBuilder.build());
    }
}
