package org.meshtastic.backend.service

import org.meshtastic.common.model.byteArrayOfInts
import java.security.Security
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * AES encode and decode as described by meshtastic-device/crypto.md
 */

/// Generate our 16 byte nonce per crypto.msd
fun genNonce(from: Int, id: Int) =
    IvParameterSpec(
        byteArrayOfInts(
            (id shr 0) and 0xff,
            (id shr 8) and 0xff,
            (id shr 16) and 0xff,
            (id shr 24) and 0xff,
            0,
            0,
            0,
            0,
            (from shr 0) and 0xff,
            (from shr 8) and 0xff,
            (from shr 16) and 0xff,
            (from shr 24) and 0xff,
            0,
            0,
            0,
            0
        )
    )

fun printProviders() {
    for (provider in Security.getProviders()) {
        println(provider.name)
        for (key in provider.stringPropertyNames()) println("\t" + key + "\t" + provider.getProperty(key))
    }
}

fun decrypt(keyBytes: ByteArray, from: Int, id: Int, inBytes: ByteArray): ByteArray {
    val ivSpec = genNonce(from, id)
    val cipher = Cipher.getInstance("AES/CTR/NoPadding")
    val mode = Cipher.DECRYPT_MODE
    val key = SecretKeySpec(keyBytes, "AES")
    cipher.init(mode, key, ivSpec)
    return cipher.doFinal(inBytes)
}

