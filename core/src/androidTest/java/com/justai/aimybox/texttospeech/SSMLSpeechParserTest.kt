package com.justai.aimybox.texttospeech

import android.util.Log
import androidx.test.filters.SmallTest
import com.justai.aimybox.model.AudioSpeech
import com.justai.aimybox.model.TextSpeech
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals

@SmallTest
class SSMLSPeechParserTest {
    val parser = SSMLSpeechParser()



    @Test
    fun testSimpleText() = runBlocking {
        val text = "Hello world!"
        val expected = listOf(TextSpeech(text))
        assertEquals(expected, parser.extractSSML(text).toList(), "Simple text extracted correctly")
    }

    @Test
    fun testAudioExtraction() = runBlocking {
        val text = "<audio src=\"https://testurl.com/audio.mp3\" />Hello!"
        val expected = listOf(AudioSpeech.Uri("https://testurl.com/audio.mp3"), TextSpeech("Hello!"))
        assertEquals(expected, parser.extractSSML(text).toList(), "Audio extracted correctly")
    }

    @Test
    fun testParagraphExtraction() = runBlocking {
        val text = "<p>Hello world!</p>Hello again!<p></p>"
        val expected = listOf(TextSpeech("Hello world!"), TextSpeech("Hello again!"))
        assertEquals(expected, parser.extractSSML(text).toList(), "Paragraph extracted correctly")
    }

    @Test
    fun testVoiceTagsExtraction(): Unit = runBlocking {
        val text =  "<p><voice name=\"en-US-JennyNeural\">" +
                "<audio src=\"https://contoso.com/opinionprompt.wav\"/>" +
                " Thanks for offering your opinion. Please begin speaking after 2 the beep." +
                "</voice>" +
                "</p>"
        val speechParts =  parser.extractSSML(text).toList()
        Log.d("SSMLSPeechParserTest", "Extracted tags: $speechParts")
    }

}
