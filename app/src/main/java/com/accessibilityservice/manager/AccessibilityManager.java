package com.accessibilityservice.manager;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.os.Build;
import android.os.Message;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import com.accessibilityservice.service.MyAccessibilityService;
import com.accessibilityservice.util.Shell;

import java.util.ArrayList;
import java.util.List;

import static com.accessibilityservice.MainApplication.mHandler;

/**
 * Created by gongkai on 2018/11/7.
 */

public class AccessibilityManager {
    private static List<String> mList = new ArrayList<>();

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static boolean clickByViewIdForList(String viewId) {
        for (AccessibilityNodeInfo nodeInfo : MyAccessibilityService.getList()) {
            if (viewId.equals(nodeInfo.getViewIdResourceName())) {
                Rect rect2 = new Rect();
                nodeInfo.getBoundsInScreen(rect2);
                if (nodeInfo.getText() != null) {
                    final String text = nodeInfo.getText().toString();
                    if (!mList.toArray().toString().contains(text)) {
                        Shell.execute("input tap " + rect2.left + " " + rect2.top);
                        Log.i("----", "text == " + text);
                        mList.add(text);
                        sendMsg("阅读" + text);
                        break;
                    }
                } else {
                    Shell.execute("input tap " + rect2.left + " " + rect2.top);
                }
            }
        }
        return false;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static boolean clickByViewId(String viewId) {
        for (AccessibilityNodeInfo nodeInfo : MyAccessibilityService.getList()) {
            if (viewId.equals(nodeInfo.getViewIdResourceName())) {
                Rect rect2 = new Rect();
                nodeInfo.getBoundsInScreen(rect2);
                Shell.execute("input tap " + rect2.left + " " + rect2.top);
                Log.i("----", "点击viewId== " + viewId);
                if (nodeInfo.getText() != null) {
                    String text = nodeInfo.getText().toString();
                    Log.i("----", "text == " + text);
                    sendMsg("点击" + text);
                }
                break;
            }
        }
        return false;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static boolean clickByRect() {
        Shell.execute("input tap 340 440");
        return false;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static boolean clickByText(String text) {
        for (AccessibilityNodeInfo nodeInfo : MyAccessibilityService.getList()) {
            CharSequence text1 = nodeInfo.getText();
            CharSequence description = nodeInfo.getContentDescription();
            if ((text1 != null && text1.toString().contains(text))
                    || (description != null && description.toString().contains(text))) {
                Rect rect2 = new Rect();
                nodeInfo.getBoundsInScreen(rect2);
                Shell.execute("input tap " + rect2.left + " " + rect2.top);
                Log.i("----", "点击text== " + text);
                sendMsg("点击" + text);
                break;
            }
        }
        return false;
    }

    public static void sendMsg(String msg) {
        Message message = mHandler.obtainMessage();
        message.obj = msg;
        mHandler.sendMessage(message);
    }
}
