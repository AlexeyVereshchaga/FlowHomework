package otus.homework.flowcats

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {

    fun listenForCatFacts() = flow {
        while (true) {
            runCatching { catsService.getCatFact() }
                .onSuccess {
                    emit(Result.Success(it))
                    delay(refreshIntervalMs)
                }
                .onFailure {
                    println(it.toString())
                    Log.e("CatsTag", "Error", it)
                    emit(Result.Error(it.message ?: "Error"))
                    delay(refreshIntervalMs)
                }
        }
    }.flowOn(Dispatchers.IO)
}