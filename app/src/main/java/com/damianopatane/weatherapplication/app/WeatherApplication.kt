package com.damianopatane.weatherapplication.app

import android.app.KeyguardManager
import android.content.Context
import com.damianopatane.weatherapplication.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import io.realm.Realm
import io.realm.RealmConfiguration
import com.damianopatane.weatherapplication.security.Store
import com.damianopatane.weatherapplication.utils.Util
import dagger.android.support.DaggerApplication
import java.util.*

class WeatherApplication : DaggerApplication() {

    private val tag = WeatherApplication::class.java.name
    lateinit var realm : Realm

    init {
        appInstance = this
    }

    companion object {
        private var appInstance: WeatherApplication? = null

        fun applicationContext() : Context {
            return appInstance!!.applicationContext
        }

        fun getRealm() : Realm {
            return appInstance!!.realm
        }

        fun closeRealm() {
            appInstance!!.realm.close()
        }

        //to be used in the future
        fun isLocked(context: Context): Boolean {
            val keyguardManager =
                context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
            return keyguardManager.isKeyguardSecure
        }
    }

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        realm = Realm.getDefaultInstance()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val component = DaggerAppComponent.builder().application(this).build()
        component.inject(this)
        return component
    }

    //to be used in the future
    private fun unlockKeyStore() {
        val store = Store(applicationContext())
        var encryptedRealmKey = store.getEncryptedRealmKey()
        if (encryptedRealmKey == null || !store.containsEncryptionKey()) {
            val realmKey: ByteArray = store.generateKeyForRealm()!!
            store.generateKeyInKeystore()
            encryptedRealmKey = store.encryptAndSaveKeyForRealm(realmKey)
            val realmConfig = RealmConfiguration.Builder()
                .encryptionKey(realmKey)
                .build()
            Arrays.fill(realmKey, 0.toByte())
            // create encrypted Realm
            Realm.deleteRealm(realmConfig)
            Realm.getInstance(realmConfig).close()
        }
        Util.showMessage(applicationContext(), "Unlocking ...")
        val realmKey = store.decryptKeyForRealm(encryptedRealmKey)
        val realmConfig =
            RealmConfiguration.Builder().encryptionKey(realmKey).build()
        Realm.setDefaultConfiguration(realmConfig)
    }
}