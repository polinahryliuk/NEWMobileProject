package com.frenchcards.newfinalproject.workers

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

class DailyReminderWorker(context: Context, params: WorkerParameters) :
    Worker(context, params) {
    override fun doWork(): Result {
        println("Daily Reminder: Time to practice French!")
        return Result.success()
    }
}
fun scheduleDailyReminder(context: Context) {
    val constraints = Constraints.Builder()
        .setRequiresBatteryNotLow(true)
        .build()

    val workRequest = PeriodicWorkRequestBuilder<DailyReminderWorker>(
        1, TimeUnit.DAYS
    )
        .setConstraints(constraints)
        .build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "daily_french_reminder",
        ExistingPeriodicWorkPolicy.KEEP,
        workRequest
    )
}