package com.example.activityresultbugsample

import android.Manifest
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

object NonActivityClass : LifecycleObserver {

    private var registerActivityResultLauncher: ActivityResultLauncher<String>? = null

    fun setLifeCycleOwner(lifecycleOwner: LifecycleOwner, registry: ActivityResultRegistry) {

        lifecycleOwner.lifecycle.addObserver(this)

        registerActivityResultLauncher = registry.registerActivityResultCallback(
            "my_key",
            lifecycleOwner, ActivityResultContracts.RequestPermission(),
            ActivityResultCallback { granted ->
                //Callback not invoked
                println("---> granted $granted")
                if (granted) {
                    //Action
                }
            }
        )
    }

    fun requestPermissions() {
        registerActivityResultLauncher?.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun cleanUp() {
        println("----> Cleaning up")
    }
}
