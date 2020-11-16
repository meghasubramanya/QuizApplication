package com.example.quizapp.noti;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.quizapp.data.States;
import com.example.quizapp.database.StateRepository;

public class NotificationWorker extends Worker {

    private StateRepository repository;

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        repository = StateRepository.getStateRepository((Application) context.getApplicationContext());
    }

    @NonNull
    @Override
    public Result doWork() {
        States state = repository.getRandomState();
        Notifications.getDailyNotifications(getApplicationContext(),state);
        return Result.success();
    }
}
