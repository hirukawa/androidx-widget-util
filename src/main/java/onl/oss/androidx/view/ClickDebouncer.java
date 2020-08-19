package onl.oss.androidx.view;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/** クリック・デバウンサー
 *
 * すばやい連続タップによってクリックイベントが複数発生するのを抑制します。
 * 最後のタップ・ダウンから次のタップ・ダウンまでの時間がダブルタップ検出時間（通常は300ミリ秒）未満の場合、
 * タップ・ダウンを消費してクリックイベントが発生しないようにします。
 *
 */
public class ClickDebouncer implements View.OnTouchListener {
    private static final long doubleTapTimeout = ViewConfiguration.getDoubleTapTimeout();

    private long lastDownTime;

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        long eventTime = motionEvent.getEventTime();

        if(action == MotionEvent.ACTION_DOWN) {
            if(eventTime - lastDownTime < doubleTapTimeout) {
                lastDownTime = eventTime;
                return true;
            }
            lastDownTime = eventTime;
        }
        return false;
    }
}
