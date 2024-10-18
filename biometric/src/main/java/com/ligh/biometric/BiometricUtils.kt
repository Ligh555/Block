package com.ligh.biometric

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

interface BiometricUtils {
    /**
     * 是否支持生物认证
     */
    fun isSupportBiometric(context: Context): Boolean

    /**
     * 生物认证是否发生变化
     */
    fun isChangeBiometric(): Boolean

    /**
     * 删除keyStore
     */
    fun deleteKeyStore()

    /**
     * 开启生物认证
     */
    fun startBiometricAuth(
        fragmentActivity: FragmentActivity,
        info: BiometricPrompt.PromptInfo,
        callBack: BiometricPrompt.AuthenticationCallback
    )
}

val biometricUtils: BiometricUtils = BiometricUtilsImpl()

class BiometricUtilsImpl : BiometricUtils {

    companion object{
        private const val KEY_NAME = "key_default"

        private const val KEY_SIZE = 256
        private const val ANDROID_KEYSTORE = "AndroidKeyStore"
        private const val ENCRYPTION_BLOCK_MODE = KeyProperties.BLOCK_MODE_GCM
        private const val ENCRYPTION_PADDING = KeyProperties.ENCRYPTION_PADDING_NONE
    }


    /**
     * 是否支持生物认证
     */
    override fun isSupportBiometric(context: Context) = BiometricManager.from(context)
        .canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK) == BiometricManager.BIOMETRIC_SUCCESS


    /**
     * 生物认证是否发生变化
     */
    override fun isChangeBiometric(): Boolean {
        val cipherString =
            "${KeyProperties.KEY_ALGORITHM_AES}/${KeyProperties.BLOCK_MODE_GCM}/${KeyProperties.ENCRYPTION_PADDING_NONE}"
        val defaultCipher = Cipher.getInstance(cipherString)
        return !initCipher(defaultCipher, KEY_NAME)
    }

    override fun deleteKeyStore() {
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE)
        keyStore.load(null)
        keyStore.deleteEntry(KEY_NAME) // 删除指定的密钥
    }

    override fun startBiometricAuth(
        fragmentActivity: FragmentActivity,
        info: BiometricPrompt.PromptInfo,
        callBack: BiometricPrompt.AuthenticationCallback
    ) {
        val executor = ContextCompat.getMainExecutor(fragmentActivity)
        BiometricPrompt(fragmentActivity, executor, callBack).authenticate(info)
    }


    /**
     * Initialize the [Cipher] instance with the created key in the [createKey] method.
     *
     * @param keyName the key name to init the cipher
     * @return `true` if initialization succeeded, `false` if the lock screen has been disabled or
     * reset after key generation, or if a fingerprint was enrolled after key generation.
     */
    private fun initCipher(cipher: Cipher, keyName: String): Boolean {
        return try {
            cipher.init(Cipher.ENCRYPT_MODE, getOrCreateSecretKey(keyName))
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun getOrCreateSecretKey(keyName: String): SecretKey {
        // If Secretkey was previously created for that keyName, then grab and return it.
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE)
        keyStore.load(null) // Keystore must be loaded before it can be accessed
        keyStore.getKey(keyName, null)?.let { return it as SecretKey }

        // if you reach here, then a new SecretKey must be generated for that keyName
        val paramsBuilder = KeyGenParameterSpec.Builder(
            keyName,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
        paramsBuilder.apply {
            setBlockModes(ENCRYPTION_BLOCK_MODE)
            setEncryptionPaddings(ENCRYPTION_PADDING)
            setKeySize(KEY_SIZE)
            setUserAuthenticationRequired(true)
        }

        val keyGenParams = paramsBuilder.build()
        val keyGenerator = KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES,
            ANDROID_KEYSTORE
        )
        keyGenerator.init(keyGenParams)
        return keyGenerator.generateKey()
    }
}