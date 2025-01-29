package otus.homework.flowcats

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 2000
) {

    fun listenForCatFacts() = flow {
        while (true) {
            runCatching { catsService.getCatFact() }
                .onSuccess {
                    emit(it)
                    delay(refreshIntervalMs)
                }
                .onFailure {
                    println(it.toString())
                    Log.e("CatsTag", "Error", it)
                    delay(refreshIntervalMs)
                }
        }
    }.flowOn(Dispatchers.IO)
}