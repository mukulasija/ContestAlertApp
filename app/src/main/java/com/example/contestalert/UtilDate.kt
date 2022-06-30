package com.example.contestalert

import android.annotation.SuppressLint
import android.text.TextUtils
import java.text.SimpleDateFormat
import java.util.*

fun String.toDate(dateFormat: String = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timeZone: TimeZone = TimeZone.getTimeZone("UTC")): Date {
    val parser = SimpleDateFormat(dateFormat, Locale.getDefault())
    parser.timeZone = timeZone
    return parser.parse(this)
}
fun Date.formatTo(dateFormat: String, timeZone: TimeZone = TimeZone.getDefault()): String {
    val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
    formatter.timeZone = timeZone
    return formatter.format(this)
}
@SuppressLint("NewApi")
fun String.getDateWithServerTimeStamp(site : String): String {
    val string = this
//        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
    var nn : String? = null
    if(site=="code_chef")
    {
        nn = string.toDate("yyyy-MM-dd HH:mm:ss 'UTC'").formatTo("dd-MM-yyyy | hh:mm a")
    }
    else
    {
        nn = string.toDate().formatTo("dd-MM-yyyy | hh:mm a")
    }
    return nn
//        Log.d("nn", nn.toString());
//        if(site=="code_chef")
//        {
//            formatter= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss 'UTC'",Locale.ENGLISH)
//        }
//        val datetime = LocalDateTime.parse(string,formatter)
//        Log.d("date ",datetime.toString());
//        Log.d("zone" , datetime.atZone(ZoneId.systemDefault()).toString())
//        val newdate = datetime.atZone(ZoneId.systemDefault())
////        datetime.atZone(ZoneId.systemDefault())
//        val format = DateTimeFormatter.ofPattern("dd-MM-yyyy | hh:mm a")
////        val cal = Calendar.getInstance()
////        val tz = cal.timeZone
////        val ind = ZoneId.of("Asia/Kolkata")
////        datetime.atZone(ind)
//        Log.d("result",newdate!!.format(format))
//        return datetime.format(format)

}