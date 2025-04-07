package com.tdavidc.dev.utility.encryption

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec


class CryptoData private constructor() {
    private val cipher = Cipher.getInstance(TRANSFORMATION)
    private val keyStore = KeyStore.getInstance("AndroidKeyStore").apply {
        load(null)
    }

    companion object {
        private const val ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
        private const val BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC
        private const val PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7
        private const val TRANSFORMATION = "$ALGORITHM/$BLOCK_MODE/$PADDING"

        private var instance: CryptoData? = null

        fun getInstance(): CryptoData {
            if (instance == null) instance = CryptoData()
            return instance!!
        }
    }

    private fun currentKey(): SecretKey? =
        (keyStore.getEntry("secret", null) as? KeyStore.SecretKeyEntry)?.secretKey

    private fun createKey(): SecretKey = KeyGenerator.getInstance(ALGORITHM).apply {
        init(
            KeyGenParameterSpec.Builder(
                "secret",
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            ).setBlockModes(BLOCK_MODE)
                .setEncryptionPaddings(PADDING)
                .build()
        )
    }.generateKey()

    fun encrypt(bytes: ByteArray): ByteArray {
        cipher.init(Cipher.ENCRYPT_MODE, currentKey() ?: createKey())
        val iv = cipher.iv
        val encrypted = cipher.doFinal(bytes)
        return iv + encrypted
    }

    fun decrypt(bytes: ByteArray): ByteArray? = currentKey()?.let {
        val iv = bytes.copyOfRange(0, cipher.blockSize)
        val data = bytes.copyOfRange(cipher.blockSize, bytes.size)
        cipher.init(Cipher.DECRYPT_MODE, it, IvParameterSpec(iv))
        cipher.doFinal(data)
    }
}