# Android-Rovolut

This project is a **"production-ready"** and **"industry-standard" banking application** (aka Revolut clone) built in Android.
<br>
#### ⚠️ DISCLAIMER! This project is NOT a real banking app. It's purpouse is to showcase my software enginering skills and to help me learn along the way!
##### 📘 Also Important to note: Since this project serves the purpose of learning also, sometimes I might not stick with one way of doing things, for example for viewmodel-view communication I used multiple variants, while only using RxKotlin would have sufficed!

☑️ What this project includes / will include:
<ul>
<li> A fraction of the features of a real banking app (login, passcode, edit profile, create payment account, make (fake) transactions, etc...)
<li> Dev, Stage and Prod Flavours + communication with a <b>REST API</b> using <b>Retrofit</b>
<li> Coroutines and lazy init for the best performence
<li> A <b>MVVM architecture</b> respecting the SOLID principles
<li> Views built with XML layout
<li> <b>RxKotlin, RxJava and LiveData</b> for the communication between layers
<li> Authentication using <b>JWT tokens</b> and a <b>refresh token mechanism</b>
<li> <b>Encrypted local storage</b>
<li> <b>Detection of rooted users</b>, and not allowing them to access the app.
<li> Restriction on screenshot / screen recording on screens with sensitive data
<li> Certificate pinning
<li> Code obfuscation
<li> <b>Unit testes</b> and <b>UI testes</b> for some parts of the app
<li> Changing the app theme (dark or light, the user can switch between them in the settings)
<li> Beautiful animations, custom views AND edge to edge supported on any API level
<li> etc..
</ul>

##### 🔨 TODO next:

<ul>
<li> create base fragment
<li> create generic dialog
<li> create allow biometric authentication dialog
<li> create generic viw with titleBar with back navigation and animation inside scollview
<li> organize/refactor
<li> some UI tests
<li> custom in-app force update mechanism (this will display when there is an update available for the app. Mandatory updates do not let the user access the app unless he updates)
</ul>
