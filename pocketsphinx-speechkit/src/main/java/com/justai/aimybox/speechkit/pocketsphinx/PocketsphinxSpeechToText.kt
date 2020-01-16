package com.justai.aimybox.speechkit.pocketsphinx

import androidx.annotation.RequiresPermission
import com.justai.aimybox.core.SpeechToTextException
import com.justai.aimybox.speechtotext.SpeechToText
import kotlinx.coroutines.channels.ReceiveChannel

import edu.cmu.pocketsphinx.Hypothesis
import edu.cmu.pocketsphinx.RecognitionListener
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import java.io.File
import java.lang.Exception

class PocketsphinxSpeechToText(
    recognizerProvider: PocketsphinxRecognizerProvider,
    grammarFilePath: String,
    private val timeout: Long = 5000
): SpeechToText(), CoroutineScope {

    companion object {
        private const val GRAMMAR_SEARCH = "grammar"
    }

    override val coroutineContext = Dispatchers.IO + Job()

    private val recognizer = recognizerProvider.recognizer

    private lateinit var channel: Channel<Result>
    private lateinit var timeoutTask: Job

    private val listener = object : RecognitionListener {
        override fun onResult(hyp: Hypothesis?) {
            launch {
                channel.send(Result.Final(hyp?.hypstr))
                finish()
            }
        }

        override fun onPartialResult(hyp: Hypothesis?) {
            launch {
                channel.send(Result.Partial(hyp?.hypstr))
            }
        }

        override fun onTimeout() {}

        override fun onBeginningOfSpeech() = onSpeechStart()

        override fun onEndOfSpeech() {
            L.d("onEndOfSpeech")
            onSpeechEnd()
            timeoutTask.cancel()
            recognizer.stop()
        }

        override fun onError(e: Exception?) {
            launch {
                channel.send(Result.Exception(SpeechToTextException(e?.message)))
                finish()
            }
        }

        fun finish() {
            channel.close()
            recognizer.removeListener(this)
        }
    }

    init {
        recognizer.addGrammarSearch(GRAMMAR_SEARCH, File(grammarFilePath))
    }

    override suspend fun cancelRecognition() {
        timeoutTask.cancel()
        recognizer.cancel()
        recognizer.removeListener(listener)
    }

    @RequiresPermission("android.permission.RECORD_AUDIO")
    override fun startRecognition(): ReceiveChannel<Result> {
        channel = Channel()
        recognizer.addListener(listener)
        recognizer.startListening(GRAMMAR_SEARCH)
        timeoutTask = launch {
            delay(timeout)
            recognizer.stop()
        }
        return channel
    }

    override suspend fun stopRecognition() {
        timeoutTask.cancel()
        recognizer.stop()
    }

    override fun destroy() {
        timeoutTask.cancel()
        recognizer.removeListener(listener)
        recognizer.shutdown()
    }
}