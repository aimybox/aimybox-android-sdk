package com.justai.aimybox.model.reply

import com.google.gson.JsonObject

/**
 * Reply is a unit of a composite response. One response contains from 0 to infinite replies.
 * */
interface Reply {

    /**
     * The source JSON of the reply. It is useful to extract additional data, which is not provided by included replies.
     * */
    val sourceJson: JsonObject

    interface Parser<out T : Reply> {
        /**
         * Converts JsonObject to the current reply or returns null if the reply can't be parsed from the json.
         * */
        fun parse(json: JsonObject): T?
    }
}
