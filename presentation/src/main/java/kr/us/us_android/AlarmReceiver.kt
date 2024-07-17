package kr.us.us_android

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.app.AlarmManager
import android.app.PendingIntent
import android.util.Log
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // 이곳에서 알림을 보내는 작업을 수행합니다.
        Log.d("AlarmReceiver", "알림이 수신되었습니다.")

        // 예시: 알림을 보내는 함수 호출
        sendNotification(context, "매일 정오에 알림을 보냅니다.")
    }

    private fun sendNotification(context: Context, message: String) {
        // 알림을 보내는 코드를 작성합니다.
        // 예시: NotificationCompat을 사용하여 알림 생성 및 표시
        // 실제로는 원하는 형태의 알림을 생성하여 사용합니다.
        // NotificationManager 사용법은 알림 생성 및 관리에 필요합니다.
    }

    companion object {
        // 정각에 알림을 보내기 위한 시간 설정
        fun setExactAlarm(context: Context) {
            val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val alarmIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
                PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
            }

            // 정각에 알림 설정
            val calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, 12) // 12시 설정
                set(Calendar.MINUTE, 0) // 0분 설정
                set(Calendar.SECOND, 0) // 0초 설정
            }

            // 지정된 시간에 알림을 보냅니다.
            alarmMgr.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY, // 매일 반복
                alarmIntent
            )

            Log.d("AlarmReceiver", "매일 12시에 알림이 설정되었습니다.")
        }

        // 알람을 취소하는 경우 사용
        fun cancelAlarm(context: Context) {
            val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val alarmIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
                PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
            }
            alarmMgr.cancel(alarmIntent)

            Log.d("AlarmReceiver", "알림이 취소되었습니다.")
        }
    }
}
