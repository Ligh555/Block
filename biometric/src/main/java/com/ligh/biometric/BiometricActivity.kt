package com.ligh.biometric

import android.widget.Toast
import androidx.biometric.BiometricPrompt
import com.ligh.base.activity.BaseActivity
import com.ligh.base.activity.binding
import com.ligh.biometric.databinding.ActivityBiometricBinding

class BiometricActivity : BaseActivity() {
    override val viewBinding: ActivityBiometricBinding by binding()

    override fun initViewBinding() {

        with(viewBinding) {
            support.setOnClickListener {
                val message = if (biometricUtils.isSupportBiometric(this@BiometricActivity)) {
                    "支持生物验证"
                } else {
                    "不支持生物验证"
                }
                Toast.makeText(this@BiometricActivity, message, Toast.LENGTH_SHORT).show()
            }
            change.setOnClickListener {
                val message = if (biometricUtils.isChangeBiometric()) {
                    "指纹验证发生变化,请重新设置"
                } else {
                    "指纹验证未发生变化"
                }
                Toast.makeText(this@BiometricActivity, message, Toast.LENGTH_SHORT).show()
            }

            delete.setOnClickListener {
                biometricUtils.deleteKeyStore()
            }

            start.setOnClickListener {

                val callback = object : BiometricPrompt.AuthenticationCallback() {

                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        Toast.makeText(this@BiometricActivity, "success", Toast.LENGTH_SHORT).show()
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        Toast.makeText(this@BiometricActivity, "failed", Toast.LENGTH_SHORT).show()

                    }

                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        Toast.makeText(this@BiometricActivity, "error", Toast.LENGTH_SHORT).show()
                    }

                }
                biometricUtils.startBiometricAuth(
                    this@BiometricActivity, BiometricPrompt.PromptInfo.Builder()
                        .setTitle("验证")
                        .setSubtitle("请进行生物验证")
                        .setDescription("请进行生物验证")
                        .setConfirmationRequired(false)
                        .setNegativeButtonText("取消")
                        .build(), callback
                )
            }
        }

    }

}