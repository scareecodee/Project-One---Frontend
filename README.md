📱 Project One – Frontend (YouTube Downloader App)

This is the **frontend** of the Project One app – a clean and responsive UI built with **Jetpack Compose** for downloading YouTube videos and audio using the backend FastAPI server.

---

## 🚀 Features

* 🔎 Enter a YouTube URL and fetch available video qualities
* 📥 Download video up to **4K resolution**
* 🎧 Download **best-quality audio (MP3)**
* 🎨 Modern UI using **Jetpack Compose**
* 🌐 Connects to backend running on **FastAPI**
* 📨 Sends and verifies email-based OTP

---

## 🧰 Tech Stack & Concepts Used

### 🧩 Jetpack Compose

* **Declarative UI** framework for Android
* UI is defined as composable functions
* Clean, state-driven design

### ⚙️ Architecture

* NavHost and NavController for navigation
  
* App screens are built using **dynamic common layout** with:

  * Title
  * Image
  * Description
  * Buttons
* Reusable components for scalable design

### 🌐 Backend Integration

* Communicates with the FastAPI backend via HTTP
* Sends JSON requests for:

  * Fetching available video qualities
  * Requesting video/audio downloads
  * Fetching downloaded files
  * Sending OTPs

### 📂 File Management

* Saves downloaded files directly to device storage
* Uses `FileProvider` to safely open media files

### ✉️ OTP Verification

* Email OTP sent from backend using Gmail SMTP
* App handles input, validation, and screen change

---

## 📦 Requirements

* Android Studio Flamingo or later
* Android SDK 33+
* Internet access (for API calls)

---

## ▶️ Run the App

1. Clone the repository:

   ```bash
   git clone https://github.com/scareecodee/Project-One---Frontend
   ```

2. Open in Android Studio

3. Make sure your **Android device and backend server are on the same Wi-Fi**

4. Update backend IP in your code:

   ```kotlin
   val BASE_URL = "http://192.168.x.x:8000"
   ```

5. Run the app on your emulator or physical device


## 👨‍💻 Developer

**Sundram Kumar**
IIIT Bhubaneswar, Batch of 2028
📧 [Mail](scareecodee@gmail.com)
🔗 [LinkedIn](https://in.linkedin.com/in/sundram-kumar-710710329)

