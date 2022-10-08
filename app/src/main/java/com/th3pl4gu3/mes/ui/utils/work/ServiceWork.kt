package com.th3pl4gu3.mes.ui.utils.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.th3pl4gu3.mes.database.ServiceRepository
import com.th3pl4gu3.mes.ui.MesApplication
import retrofit2.HttpException

class ServiceWork(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    companion object {
        const val WORKER_NAME = "REFRESH_SERVICES"
    }

    override suspend fun doWork(): Result {

        try {
            // Refresh the services
            val message = ServiceRepository.getInstance(MesApplication.getInstance()).refresh()
            return if (message == null) {
                Result.success()
            } else {
                Result.failure()
            }
        } catch (exception: HttpException) {
            Result.retry()
        }

        return Result.success()
    }

}