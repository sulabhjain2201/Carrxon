package in.globalsoft.urncr;

import java.lang.Thread.UncaughtExceptionHandler;

import android.app.Application;

public class MyApplication extends Application {
    // uncaught exception handler variable
    private UncaughtExceptionHandler defaultUEH;

    // handler listener
    private UncaughtExceptionHandler _unCaughtExceptionHandler =
        new UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {

                // here I do logging of exception to a db
//                PendingIntent myActivity = PendingIntent.getActivity(getContext(),
//                    192837, new Intent(getContext(), HomeScreen.class),
//                    PendingIntent.FLAG_ONE_SHOT);
//
//                AlarmManager alarmManager;
//                alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//                alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, 
//                    15000, myActivity );
                System.exit(2);

                // re-throw critical exception further to the os (important)
                defaultUEH.uncaughtException(thread, ex);
            }
        };

    public MyApplication() {
       // defaultUEH = Thread.getDefaultUncaughtExceptionHandler();

        // setup handler for uncaught exception 
       // Thread.setDefaultUncaughtExceptionHandler(_unCaughtExceptionHandler);
    }
}