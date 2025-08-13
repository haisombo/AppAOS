# LOLC Customer Management Android App 📱

[![Platform](https://img.shields.io/badge/platform-Android-green.svg)](https://www.android.com)
[![API](https://img.shields.io/badge/API-29%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=29)
[![Language](https://img.shields.io/badge/language-Kotlin-orange.svg)](https://kotlinlang.org)
[![Architecture](https://img.shields.io/badge/architecture-MVVM-blue.svg)](https://developer.android.com/jetpack/guide)

A modern Android application built with Kotlin that displays and manages customer information from LOLC's API. The app supports customer listing, detailed views, and tracking of visited customers with local persistence.

## 📋 Table of Contents

- [Features](#features)
- [Screenshots](#screenshots)
- [Architecture](#architecture)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [API Documentation](#api-documentation)
- [Testing](#testing)
- [Building & Deployment](#building--deployment)
- [Contributing](#contributing)
  
## ✨ Features

### Core Functionality
- **Customer List View**: Display customers with name, phone, and address
- **Customer Detail View**: View complete customer information
- **Visited Status Tracking**: Mark and persist visited customers locally
- **Pull-to-Refresh**: Refresh customer list with swipe gesture
- **Offline Support**: View previously loaded data when offline
- **Error Handling**: Graceful error handling with retry options

### Technical Features
- **MVVM Architecture**: Clean separation of concerns
- **Dependency Injection**: Using Hilt for DI
- **Coroutines**: Asynchronous programming with Kotlin Coroutines
- **Navigation Component**: Type-safe navigation between screens
- **DataStore**: Modern data persistence for preferences
- **Material Design 3**: Following latest Material Design guidelines

## 📸 Screenshots

| Customer List | Customer Detail | Visited Status |
|--------------|-----------------|----------------|
| ![List](screenshots/list.png) | ![Detail](screenshots/detail.png) | ![Visited](screenshots/visited.png) |

## 🏗 Architecture

The app follows **Clean Architecture** principles with **MVVM** pattern:

```
┌─────────────────────────────────────────────┐
│                Presentation                 │
│  ┌─────────────────────────────────────┐   │
│  │          Views (Activities,         │   │
│  │           Fragments, Adapters)      │   │
│  └─────────────────────────────────────┘   │
│  ┌─────────────────────────────────────┐   │
│  │          ViewModels                 │   │
│  └─────────────────────────────────────┘   │
└─────────────────────────────────────────────┘
                      ↕
┌─────────────────────────────────────────────┐
│                  Domain                      │
│  ┌─────────────────────────────────────┐   │
│  │          Use Cases                  │   │
│  └─────────────────────────────────────┘   │
│  ┌─────────────────────────────────────┐   │
│  │          Repository Interfaces      │   │
│  └─────────────────────────────────────┘   │
└─────────────────────────────────────────────┘
                      ↕
┌─────────────────────────────────────────────┐
│                   Data                       │
│  ┌─────────────────────────────────────┐   │
│  │          Repository Impl            │   │
│  └─────────────────────────────────────┘   │
│  ┌──────────────┐  ┌──────────────────┐   │
│  │   Remote     │  │     Local        │   │
│  │   (API)      │  │  (DataStore)     │   │
│  └──────────────┘  └──────────────────┘   │
└─────────────────────────────────────────────┘
```

### Architecture Benefits
- **Separation of Concerns**: Each layer has its specific responsibility
- **Testability**: Easy to write unit tests for each component
- **Scalability**: Easy to add new features without affecting existing code
- **Maintainability**: Clean and organized code structure

## 🛠 Tech Stack

### Core
- **Language**: [Kotlin](https://kotlinlang.org/) - Modern, concise, and safe
- **Minimum SDK**: API 29 (Android 10)
- **Target SDK**: API 34 (Android 14)

### Architecture Components
- **ViewModel**: Manage UI-related data lifecycle-aware
- **LiveData**: Observable data holder
- **Navigation**: Handle in-app navigation
- **DataStore**: Data persistence solution
- **Hilt**: Dependency injection

### Networking
- **Retrofit**: Type-safe HTTP client
- **OkHttp**: HTTP & HTTP/2 client
- **Gson**: JSON serialization/deserialization

### UI
- **Material Components**: Material Design UI components
- **RecyclerView**: Efficient list display
- **SwipeRefreshLayout**: Pull-to-refresh functionality
- **ConstraintLayout**: Flexible layout system

### Async
- **Coroutines**: Asynchronous programming
- **Flow**: Reactive streams

## 📁 Project Structure

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/lolc/customerapp/
│   │   │   ├── data/                    # Data layer
│   │   │   │   ├── api/                 # API service & interceptors
│   │   │   │   ├── local/               # Local storage (DataStore)
│   │   │   │   ├── model/               # Data models
│   │   │   │   └── repository/          # Repository implementations
│   │   │   │
│   │   │   ├── domain/                  # Domain layer
│   │   │   │   ├── usecase/             # Business logic
│   │   │   │   └── repository/          # Repository interfaces
│   │   │   │
│   │   │   ├── presentation/            # Presentation layer
│   │   │   │   └── ui/
│   │   │   │       ├── customerlist/    # List screen
│   │   │   │       └── customerdetail/  # Detail screen
│   │   │   │
│   │   │   ├── di/                      # Dependency injection
│   │   │   └── CustomerApplication.kt   # Application class
│   │   │
│   │   └── res/                          # Resources
│   │       ├── layout/                   # XML layouts
│   │       ├── navigation/               # Navigation graphs
│   │       ├── values/                   # Strings, colors, themes
│   │       └── drawable/                 # Images and icons
│   │
│   └── test/                             # Unit tests
│       └── java/com/lolc/customerapp/
│
└── build.gradle.kts                      # Module build configuration
```

## 🚀 Getting Started

### Prerequisites

- Android Studio Hedgehog (2023.1.1) or later
- JDK 17 or later
- Android SDK with API Level 34
- Kotlin 1.9.0 or later

### Installation

1. **Clone the repository**
```bash
[git clone https://github.com/haisombo/lAppAOS.git]
cd lolc-customer-app
```

2. **Open in Android Studio**
```bash
studio .
```

3. **Sync Project**
- Click "Sync Project with Gradle Files" or
- File → Sync Project with Gradle Files

4. **Run the app**
- Select a device/emulator
- Click "Run" button or press `Shift + F10`

### Configuration

The app uses the following base configuration:

```kotlin
// API Configuration
const val BASE_URL = "https://sdo-test-api.lolc.com.kh:4433/"
const val CLIENT_ID = "test_client"
const val CLIENT_SECRET = "super_secret"
```

To modify for different environments, update `NetworkModule.kt`:

```kotlin
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "YOUR_API_URL"
    // ...
}
```

## 📡 API Documentation

### Base URL
```
https://sdo-test-api.lolc.com.kh:443
```

### Endpoints

#### 1. Authentication - Get Token
```http
POST /api/token
Content-Type: application/json

{
    "clientId": "test_client",
    "clientSecret": "super_secret"
}
```

**Response:**
```json
{
    "accessToken": "mBw7q3qLc0GL3uiegsH1Ag==",
    "expiration": "2025-08-07T14:33:19.3411696+07:00"
}
```

#### 2. Get Customers
```http
GET /api/users
Authorization: Bearer {token}
```

**Response:**
```json
[
    {
        "id": 1,
        "name": "Chan Samnang",
        "phone": "0881234567",
        "address": "Kampot Province",
        "company": "LOLC",
        "email": "sokdata.test@lolc.com.kh",
        "website": "lolc.com.kh"
    }
]
```

#### 3. Update Customer
```http
PUT /api/users/{id}
Authorization: Bearer {token}
Content-Type: application/json

{
    "name": "Chan Samnang",
    "phone": "0881234567",
    "address": "Kampot Province"
}
```

**Response:**
```json
{
    "message": "Customer updated successfully."
}
```

## 🧪 Testing

### Running Tests

**Unit Tests:**
```bash
./gradlew test
```

**Instrumented Tests:**
```bash
./gradlew connectedAndroidTest
```

**All Tests:**
```bash
./gradlew testDebugUnitTest connectedDebugAndroidTest
```

### Test Coverage

Generate test coverage report:
```bash
./gradlew createDebugCoverageReport
```

Report location: `app/build/reports/coverage/debug/index.html`

## 📦 Building & Deployment

### Debug Build
```bash
./gradlew assembleDebug
```
Output: `app/build/outputs/apk/debug/app-debug.apk`

### Release Build

1. **Create signing configuration** in `app/build.gradle.kts`:
```kotlin
signingConfigs {
    create("release") {
        keyAlias = "your-key-alias"
        keyPassword = "your-key-password"
        storeFile = file("path/to/keystore")
        storePassword = "your-store-password"
    }
}
```

2. **Build release APK:**
```bash
./gradlew assembleRelease
```
Output: `app/build/outputs/apk/release/app-release.apk`

### Generate AAB (App Bundle)
```bash
./gradlew bundleRelease
```
Output: `app/build/outputs/bundle/release/app-release.aab`

## 🔍 Code Quality

### Lint Check
```bash
./gradlew lint
```
Report: `app/build/reports/lint-results-debug.html`

### Detekt (Static Analysis)
```bash
./gradlew detekt
```

### Format Code
```bash
./gradlew ktlintFormat
```

## 📊 Performance Optimization

- **RecyclerView**: Using DiffUtil for efficient list updates
- **Image Loading**: Placeholder images for better UX
- **Network Caching**: OkHttp cache for offline support
- **Coroutines**: Efficient async operations
- **ViewBinding**: Null-safe view references


### Coding Standards

- Follow [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Use meaningful variable and function names
- Write unit tests for new features
- Update documentation as needed

## 📝 TODO / Future Enhancements

- [ ] Add search functionality
- [ ] Implement sorting options
- [ ] Add customer filtering
- [ ] Implement offline-first architecture
- [ ] Add customer photo support
- [ ] Implement push notifications
- [ ] Add analytics tracking
- [ ] Support dark theme
- [ ] Add customer notes feature
- [ ] Export customer data

## 🐛 Known Issues

- None at the moment


## 👥 Authors

- **Hai Sombo** - *Initial work* - [GitHub]([https://github.com/haisombo])

## 🙏 Acknowledgments

- LOLC Cambodia for the assignment opportunity
- Android Documentation for guidelines
- Stack Overflow community for solutions

## 📞 Contact

For any queries regarding this project, please contact:
- Email: haisombo20@gmail.com

---

⭐ If you found this project helpful, please consider giving it a star!
