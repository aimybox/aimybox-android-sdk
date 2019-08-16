<h1 align="center">
    <br>
    <a href="https://aimybox.com"><img src="https://app.aimybox.com/assets/images/aimybox.png"
                                                                    height="200"></a>
    <br><br>
    Aimybox Android SDK
</h1>

<h4 align="center">Open source voice assistant SDK written in Kotlin</h4>

<p align="center">
    <a href="https://gitter.im/aimybox/community"><img src="https://badges.gitter.im/amitmerchant1990/electron-markdownify.svg"></a>
    <a href="https://twitter.com/intent/follow?screen_name=aimybox"><img alt="Twitter Follow" src="https://img.shields.io/twitter/follow/aimybox.svg?label=Follow%20on%20Twitter&style=popout"></a>
    <a href="https://travis-ci.org/just-ai/aimybox-android-sdk/"><img alt="Travis CI Build" src="https://travis-ci.org/just-ai/aimybox-android-sdk.svg?branch=master"></a>
    <a href="https://bintray.com/aimybox/aimybox-android-sdk/"><img alt="Bintray artifact" src="https://api.bintray.com/packages/aimybox/aimybox-android-sdk/core/images/download.svg"></a>
</p>

The only solution if you need to embed your own intelligent voice assistant into your existing application or device.

# Key Features

* Modular and independent from ASR and TTS vendors
* Open source under Apache 2.0, written in pure Kotlin
* Embeddable into any application or device under Android
* Voice skills logic is not limited by any restrictions
* Works with any NLU providers like Aimylogic or Dialogflow
* Can interact with any local device services and local networks
* Fully customizable and extendable

# How to start using

```kotlin
    repositories {
        maven("https://dl.bintray.com/aimybox/aimybox-android-sdk/")
    }
    
    dependencies {
        /* Core Aimybox package */
        implementation("com.justai.aimybox:core:${version}")
        
        /* Optional modules */
        implementation("com.justai.aimybox:google-platform-speechkit:${version}")
        implementation("com.justai.aimybox:yandex-speechkit:${version}")
        implementation("com.justai.aimybox:snowboy-speechkit:${version}")
        implementation("com.justai.aimybox:houndify-speechkit:${version}")
    }
```

Please refer to the [demo voice assistant](https://github.com/aimybox/aimybox-android-assistant) to see how to use Aimybox library in your own project.

# Documentation

There is a full Aimybox documentation available [here](https://help.aimybox.com)
