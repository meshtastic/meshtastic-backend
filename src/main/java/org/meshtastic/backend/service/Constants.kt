package org.meshtastic.backend.service

object Constants {
    /**
     * The root for all our topics
     */
    private val topicRoot = "msh/"

    /**
     * The root for all encrypted messages
     */
    val cryptRoot = topicRoot + "1/c/"

    /**
     * The topic root for our JSON republishes
     */
    val cleartextRoot = topicRoot + "1/clear/"

    /**
     * The topic root for our JSON republishes
     */
    val jsonRoot = topicRoot + "1/json/"

}