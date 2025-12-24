# 🌬️ Wind – News App

Wind is an Android news application that provides real-time news using REST APIs.  
The app is built with **Java**, **Retrofit**, and modern Android UI components to deliver a smooth and simple news-reading experience.

---

## 🚀 Features

- 📰 Latest news headlines
- 🔍 Search news by keyword
- 🗂 Browse news by categories (Top, Business, Sports, Technology, Health)
- 🔄 Swipe-to-refresh functionality
- 🌐 Read full news articles inside the app using WebView
- 🖼 Image loading with Picasso
- ⚡ Fast, lightweight, and user-friendly UI

---

## 🛠️ Tech Stack

- **Language:** Java
- **Networking:** Retrofit
- **UI Components:** RecyclerView, CardView, Material Components
- **Image Loading:** Picasso
- **API:** REST-based News API
- **Build Tool:** Gradle

---

## 📂 Project Structure

app/
├── java/
│ └── com.projectinteract.wind
│ ├── activities
│ │ ├── MainActivity.java
│ │ ├── SplashActivity.java
│ │ └── NewsFullActivity.java
│ ├── adapters
│ │ └── NewsRecyclerAdapter.java
│ ├── models
│ │ └── Article.java
│ └── network
│ ├── RetrofitClient.java
│ └── NewsApiService.java
└── res/
├── layout/
│ ├── activity_main.xml
│ ├── activity_splash.xml
│ ├── activity_news_full.xml
│ └── news_recycler_row.xml
├── drawable/
└── values/








