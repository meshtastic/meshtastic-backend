package org.meshtastic.backend.service

object Constants {
    /**
     * The root for all our topics
     */
    private val topicRoot = "msh/"

    private val protoVersion = "1"

    /**
     * The root for all encrypted messages
     */
    val cryptRoot = "$topicRoot$protoVersion/c/"

    /**
     * The topic root for our JSON republishes
     */
    val cleartextRoot = "$topicRoot$protoVersion/clear/"

    /**
     * The topic root for our JSON republishes
     */
    val jsonRoot = "$topicRoot$protoVersion/json/"

}