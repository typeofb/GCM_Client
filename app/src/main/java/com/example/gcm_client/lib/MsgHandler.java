package com.example.gcm_client.lib;

import android.os.Handler;
import android.os.Message;
import androidx.fragment.app.FragmentActivity;

public abstract class MsgHandler extends FragmentActivity {
    protected Handler MsgHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {  // ③ 메시지 수신
            if (msg.what == 0) {
//                sendActivityUpdate(msg.arg1, msg.arg2);
            }
        }
    };

    public void sendActivityUpdate(int arg1, int arg2) {
        Message m = Message.obtain(this.MsgHandler, 0, arg1, arg2);  // ① 메시지 대상 설정 to this.MsgHandler
        this.MsgHandler.sendMessage(m);  // ② 메시지 송신

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
