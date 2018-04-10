package com.stockai.craftdata.stockbook;

public class Stock {
    private String stock_id, stock_name;
    private String today_open, yesterday_end, now, high, low;
    private String buy1_s, sell1_s;
    private String total_volume, total_money;
    private String b_volume1, buy1;
    private String b_volume2, buy2;
    private String b_volume3, buy3;
    private String b_volume4, buy4;
    private String b_volume5, buy5;
    private String s_volume1, sell1;
    private String s_volume2, sell2;
    private String s_volume3, sell3;
    private String s_volume4, sell4;
    private String s_volume5, sell5;
    private String transac_date, transac_time;
    private String is_closed;

    public Stock(String stock_id, String stock_name, String today_open, String yesterday_end, String now, String high, String low, String buy1_s, String sell1_s, String total_volume, String total_money, String b_volume1, String buy1, String b_volume2, String buy2, String b_volume3, String buy3, String b_volume4, String buy4, String b_volume5, String buy5, String s_volume1, String sell1, String s_volume2, String sell2, String s_volume3, String sell3, String s_volume4, String sell4, String s_volume5, String sell5, String transac_date, String transac_time, String is_closed) {
        this.stock_id = stock_id;
        this.stock_name = stock_name;
        this.today_open = today_open;
        this.yesterday_end = yesterday_end;
        this.now = now;
        this.high = high;
        this.low = low;
        this.buy1_s = buy1_s;
        this.sell1_s = sell1_s;
        this.total_volume = total_volume;
        this.total_money = total_money;
        this.b_volume1 = b_volume1;
        this.buy1 = buy1;
        this.b_volume2 = b_volume2;
        this.buy2 = buy2;
        this.b_volume3 = b_volume3;
        this.buy3 = buy3;
        this.b_volume4 = b_volume4;
        this.buy4 = buy4;
        this.b_volume5 = b_volume5;
        this.buy5 = buy5;
        this.s_volume1 = s_volume1;
        this.sell1 = sell1;
        this.s_volume2 = s_volume2;
        this.sell2 = sell2;
        this.s_volume3 = s_volume3;
        this.sell3 = sell3;
        this.s_volume4 = s_volume4;
        this.sell4 = sell4;
        this.s_volume5 = s_volume5;
        this.sell5 = sell5;
        this.transac_date = transac_date;
        this.transac_time = transac_time;
        this.is_closed = is_closed;
    }

    public String getStock_id() {
        return stock_id;
    }

    public String getStock_name() {
        return stock_name;
    }

    public String getToday_open() {
        return today_open;
    }

    public String getYesterday_end() {
        return yesterday_end;
    }

    public String getNow() {
        return now;
    }

    public String getHigh() {
        return high;
    }

    public String getLow() {
        return low;
    }

    public String getBuy1_s() {
        return buy1_s;
    }

    public String getSell1_s() {
        return sell1_s;
    }

    public String getTotal_volume() {
        return total_volume;
    }

    public String getTotal_money() {
        return total_money;
    }

    public String getB_volume1() {
        return b_volume1;
    }

    public String getBuy1() {
        return buy1;
    }

    public String getB_volume2() {
        return b_volume2;
    }

    public String getBuy2() {
        return buy2;
    }

    public String getB_volume3() {
        return b_volume3;
    }

    public String getBuy3() {
        return buy3;
    }

    public String getB_volume4() {
        return b_volume4;
    }

    public String getBuy4() {
        return buy4;
    }

    public String getB_volume5() {
        return b_volume5;
    }

    public String getBuy5() {
        return buy5;
    }

    public String getS_volume1() {
        return s_volume1;
    }

    public String getSell1() {
        return sell1;
    }

    public String getS_volume2() {
        return s_volume2;
    }

    public String getSell2() {
        return sell2;
    }

    public String getS_volume3() {
        return s_volume3;
    }

    public String getSell3() {
        return sell3;
    }

    public String getS_volume4() {
        return s_volume4;
    }

    public String getSell4() {
        return sell4;
    }

    public String getS_volume5() {
        return s_volume5;
    }

    public String getSell5() {
        return sell5;
    }

    public String getTransac_date() {
        return transac_date;
    }

    public String getTransac_time() {
        return transac_time;
    }

    public String getIs_closed() {
        return is_closed;
    }

    public void setStock_id(String stock_id) {
        this.stock_id = stock_id;
    }

    public void setStock_name(String stock_name) {
        this.stock_name = stock_name;
    }

    public void setToday_open(String today_open) {
        this.today_open = today_open;
    }

    public void setYesterday_end(String yesterday_end) {
        this.yesterday_end = yesterday_end;
    }

    public void setNow(String now) {
        this.now = now;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public void setBuy1_s(String buy1_s) {
        this.buy1_s = buy1_s;
    }

    public void setSell1_s(String sell1_s) {
        this.sell1_s = sell1_s;
    }

    public void setTotal_volume(String total_volume) {
        this.total_volume = total_volume;
    }

    public void setTotal_money(String total_money) {
        this.total_money = total_money;
    }

    public void setB_volume1(String b_volume1) {
        this.b_volume1 = b_volume1;
    }

    public void setBuy1(String buy1) {
        this.buy1 = buy1;
    }

    public void setB_volume2(String b_volume2) {
        this.b_volume2 = b_volume2;
    }

    public void setBuy2(String buy2) {
        this.buy2 = buy2;
    }

    public void setB_volume3(String b_volume3) {
        this.b_volume3 = b_volume3;
    }

    public void setBuy3(String buy3) {
        this.buy3 = buy3;
    }

    public void setB_volume4(String b_volume4) {
        this.b_volume4 = b_volume4;
    }

    public void setBuy4(String buy4) {
        this.buy4 = buy4;
    }

    public void setB_volume5(String b_volume5) {
        this.b_volume5 = b_volume5;
    }

    public void setBuy5(String buy5) {
        this.buy5 = buy5;
    }

    public void setS_volume1(String s_volume1) {
        this.s_volume1 = s_volume1;
    }

    public void setSell1(String sell1) {
        this.sell1 = sell1;
    }

    public void setS_volume2(String s_volume2) {
        this.s_volume2 = s_volume2;
    }

    public void setSell2(String sell2) {
        this.sell2 = sell2;
    }

    public void setS_volume3(String s_volume3) {
        this.s_volume3 = s_volume3;
    }

    public void setSell3(String sell3) {
        this.sell3 = sell3;
    }

    public void setS_volume4(String s_volume4) {
        this.s_volume4 = s_volume4;
    }

    public void setSell4(String sell4) {
        this.sell4 = sell4;
    }

    public void setS_volume5(String s_volume5) {
        this.s_volume5 = s_volume5;
    }

    public void setSell5(String sell5) {
        this.sell5 = sell5;
    }

    public void setTransac_date(String transac_date) {
        this.transac_date = transac_date;
    }

    public void setTransac_time(String transac_time) {
        this.transac_time = transac_time;
    }

    public void setIs_closed(String is_closed) {
        this.is_closed = is_closed;
    }
}
