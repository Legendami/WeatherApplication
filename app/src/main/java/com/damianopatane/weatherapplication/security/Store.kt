package com.damianopatane.weatherapplication.security

import android.content.Context
import android.os.Build
import android.security.KeyPairGeneratorSpec
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import io.realm.RealmConfiguration
import com.damianopatane.weatherapplication.app.Constants.ENCRYPTION_ALIAS_WEATHER_APPLICATION
import com.damianopatane.weatherapplication.security.SharedPrefUtils.load
import java.math.BigInteger
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.security.*
import java.util.*
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.IvParameterSpec
import javax.security.auth.x500.X500Principal

class Store(appContext: Context) {
    private val KEYSTORE_PROVIDER_NAME = "AndroidKeyStore"
    private val KEY_ALIAS = ENCRYPTION_ALIAS_WEATHER_APPLICATION
    private val TRANSFORMATION = (KeyProperties.KEY_ALGORITHM_AES
            + "/" + KeyProperties.BLOCK_MODE_CBC
            + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7)
    private val AES_MODE_LESS_THAN_M = "RSA/ECB/PKCS1Padding"
    private val RSA_ALGORITHM_NAME = "RSA"
    private val ORDER_FOR_ENCRYPTED_DATA: ByteOrder = ByteOrder.BIG_ENDIAN
    private val AUTH_VALID_DURATION_IN_SECOND = 30

    //private var context: Activity? = context
    private var appContext : Context? = appContext
    private val rng: SecureRandom = SecureRandom()
    private val keyStore: KeyStore? = prepareKeyStore()

    fun containsEncryptionKey(): Boolean {
        return try {
            keyStore!!.containsAlias(KEY_ALIAS)
        } catch (e: KeyStoreException) {
            throw java.lang.RuntimeException(e)
        }
    }

    fun generateKeyForRealm(): ByteArray? {
        val keyForRealm = ByteArray(RealmConfiguration.KEY_LENGTH)
        rng.nextBytes(keyForRealm)
        return keyForRealm
    }

    fun generateKeyInKeystore() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            generateKeysForAPIMOrGreater()
        } else {
            generateKeysForAPILessThanM()
        }
    }

    private fun generateKeysForAPIMOrGreater() {
        val keyGenerator: KeyGenerator
        keyGenerator = try {
            KeyGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_AES,
                KEYSTORE_PROVIDER_NAME
            )
        } catch (e: Exception) {
            throw java.lang.RuntimeException(e)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val keySpec = KeyGenParameterSpec.Builder(
                KEY_ALIAS,
                KeyProperties.PURPOSE_ENCRYPT or  KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                .setUserAuthenticationRequired(false) //since i was using emulators without locked screens, i set this to false for now
                .setUserAuthenticationValidityDurationSeconds(
                    AUTH_VALID_DURATION_IN_SECOND
                )
                .build()
            try {
                keyGenerator.init(keySpec)
            } catch (e: InvalidAlgorithmParameterException) {
                throw java.lang.RuntimeException(e)
            }
            keyGenerator.generateKey()
        }
    }

    private fun generateKeysForAPILessThanM() { // Generate a key pair for encryption
        val start: Calendar = Calendar.getInstance()
        val end: Calendar = Calendar.getInstance()
        end.add(Calendar.YEAR, 30)
        val spec = KeyPairGeneratorSpec.Builder(appContext!!)
            .setAlias(KEY_ALIAS)
            .setSubject(X500Principal("CN=$KEY_ALIAS"))
            .setSerialNumber(BigInteger.TEN)
            .setStartDate(start.time)
            .setEndDate(end.time)
            .build()
        val kpg: KeyPairGenerator =
            KeyPairGenerator.getInstance(RSA_ALGORITHM_NAME, KEYSTORE_PROVIDER_NAME)
        kpg.initialize(spec)
        kpg.generateKeyPair()
    }

    fun getEncryptedRealmKey(): ByteArray? {
        return load(appContext!!)
    }

    fun encryptAndSaveKeyForRealm(keyForRealm : ByteArray) : ByteArray {
        val ks : KeyStore = prepareKeyStore()
        val cipher : Cipher = prepareCipher()!!

        val iv : ByteArray
        val encryptedKeyForRealm : ByteArray
        try {
            val key = ks.getEntry(KEY_ALIAS, null) as KeyStore.SecretKeyEntry
            val secretkey = key!!.secretKey
            cipher.init(Cipher.ENCRYPT_MODE, secretkey)
            encryptedKeyForRealm = cipher.doFinal(keyForRealm)
            iv = cipher.iv
        } catch (e: Exception) {
            throw java.lang.RuntimeException("key for encryption is invalid", e)
        }
        val ivAndEncryptedKey = ByteArray(Int.SIZE_BYTES + iv.size + encryptedKeyForRealm.size)

        val buffer : ByteBuffer = ByteBuffer.wrap(ivAndEncryptedKey)
        buffer.order(ORDER_FOR_ENCRYPTED_DATA)
        buffer.putInt(iv.size)
        buffer.put(iv)
        buffer.put(encryptedKeyForRealm)

        SharedPrefUtils.save(appContext!!, ivAndEncryptedKey)

        return ivAndEncryptedKey
    }

    fun decryptKeyForRealm(ivAndEncryptedKey : ByteArray) : ByteArray {
        val ks: KeyStore = prepareKeyStore()
        val cipher: Cipher = prepareCipher()!!

        val buffer : ByteBuffer = ByteBuffer.wrap(ivAndEncryptedKey)
        buffer.order(ORDER_FOR_ENCRYPTED_DATA)

        val ivLength = buffer.int
        val iv = ByteArray(ivLength)
        val encryptedKey = ByteArray((ivAndEncryptedKey.size - Integer.SIZE - ivLength))

        buffer.get(iv)
        buffer.get(encryptedKey)
        try {
            val key = ks.getEntry(KEY_ALIAS, null) as KeyStore.SecretKeyEntry
            val secretkey = key.secretKey
            val ivSpec = IvParameterSpec(iv)

            cipher.init(Cipher.DECRYPT_MODE, secretkey, ivSpec)

            return cipher.doFinal(encryptedKey)
        } catch (e : InvalidKeyException) {
            throw RuntimeException("key is invalid.");
        } catch (e : Exception ) {
            throw RuntimeException(e)
        }
    }

    private fun prepareKeyStore(): KeyStore {
        return try {
            val ks =
                KeyStore.getInstance(KEYSTORE_PROVIDER_NAME)
            ks.load(null)
            ks
        } catch (e: KeyStoreException) {
            throw RuntimeException(e)
        }
    }

    private fun prepareCipher(): Cipher? {
        val cipher: Cipher
        cipher = try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Cipher.getInstance(TRANSFORMATION)
            } else {
                Cipher.getInstance(AES_MODE_LESS_THAN_M)
            }
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
        return cipher
    }

//    private fun getKeyguardManager(): KeyguardManager? {
//        return context!!.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
//    }
}