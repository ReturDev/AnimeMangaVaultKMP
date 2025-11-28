# **AnimeMangaVaultKMP**

A cross-platform application (Android/iOS) built with **Kotlin Multiplatform**, designed to provide a complete experience for browsing, exploring, and organizing anime and manga titles.
Shared business logic is implemented using **Clean Architecture**, with REST API integration, local database storage, and a structure focused on scalability and maintainability.

[![Kotlin Multiplatform](https://img.shields.io/badge/Kotlin-Multiplatform-7F52FF?logo=kotlin)]()
[![Clean Architecture](https://img.shields.io/badge/Architecture-Clean-brightgreen)]()
[![Work in Progress](https://img.shields.io/badge/Status-WIP-orange)]()

> ⚠️ *Demo, screenshots, and UI previews coming soon. The design is currently in an early stage while core data and architectural layers are being completed.*

## 📚 Table of Contents

* [Motivation](#-motivation)
* [Main Features](#-main-features)
* [Technologies Used]([#%EF%B8%8F-technologies-used)
* [Project Architecture](#-project-architecture)
* [Installation & Running the Project](#-installation--running-the-project)
* [Screenshots / Demo](#-screenshots--demo)
* [Roadmap](#-roadmap)
* [Comparison with Previous Project](#-comparison-with-previous-project)
* [Technical Decisions](#-technical-decisions)
* [Challenges Encountered](#-challenges-encountered)
* [Next Steps & Contributions](#-next-steps--contributions)

---

## 🎯 Motivation

AnimeMangaVaultKMP was created as a personal learning project to deepen my experience with **Kotlin Multiplatform** and modern mobile architecture.

### What problem does it solve?

There is no unified, clean, cross-platform solution for browsing, exploring and organizing anime/manga content. This app provides:

* A consolidated place to discover trending and seasonal titles.
* Search capabilities for both anime and manga.
* A customizable personal library where users can categorize titles (watching, reading, dropped, etc.).

### What did I want to learn and demonstrate?

* Applying **Clean Architecture** with shared business logic across platforms.
* Structuring a scalable, testable codebase using KMP best practices.
* Integrating a **real external API** and handling domain models, mapping, pagination, errors, and data flow.
* Combining remote and local data sources using a **local database**.
* Understanding how to structure shared code for Android and iOS inside a single KMP project.

### Why is this relevant for companies?

Even without formal work experience, this project shows:

* Ability to design and maintain a clean architecture.
* Experience building production-like features (API consumption, persistence, loading states).
* Familiarity with modern Kotlin tools and patterns.
* Understanding of cross-platform development and code sharing.
* Habit of documenting, planning, and maintaining a technical project.

---

## ✨ Main Features

* **Kotlin Multiplatform (Android + iOS)**
  Shared logic for domain, data, networking, and caching.
* **Clean Architecture + MVVM + Use Cases**
  Clear separation of responsibilities and scalable structure.
* **REST API integration**
  Fetching trending, seasonal, and search results for anime & manga.
* **Local database storage**
  Used to store the user library and cached content.
* **User Library system**
  Organize titles with states such as watching/reading, paused, dropped, etc.
* **Dependency Injection**
  Using Koin.
* **Error, loading, and empty state handling**
  Consistent state-driven UI.
* **Work in Progress**
  UI is under active development; the data layer is mostly implemented.

---

## 🛠️ Technologies Used

### **Core**

* **Kotlin**
* **Kotlin Multiplatform (KMP)**
* **Coroutines**
* **StateFlow / Flow**
* **Kotlinx Datetime**

---

### **UI**

* **Jetpack Compose Multiplatform**

  * compose-runtime
  * compose-foundation
  * compose-material3
  * compose-ui
  * ui-tooling-preview
  * components-resources

* **Navigation**

  * navigation-compose

* **Media**

  * compose-multiplatform-media-player (Chaintech)

---

### **Networking**

* **Ktor Client**

  * ktor-client-core
  * ktor-client-content-negotiation
  * JSON serialization
  * ktor-client-okhttp (Android)
  * ktor-client-darwin (iOS)

* **Serialization**

  * Kotlinx Serialization

---

### **Data Layer**

* **Local Database**

  * Room (KMP)
  * androidx-sqlite-bundled
  * KSP room-compiler (Android & iOS)

* **Paging**

  * paging-common
  * paging-compose-common

* **Image Loading**

  * Coil KMP
  * coil-compose
  * coil-network-ktor

---

### **Architecture**

* **Clean Architecture**
* **MVVM**
* **Repository Pattern**

---

### **Dependency Injection**

* **Koin**

  * koin-core
  * koin-compose
  * koin-compose-viewmodel
  * koin-android (Android only)

---

### **Testing (planned)**

* Kotlin Test
* Unit tests
* Integration tests

---

### **Build / Tools**

* Gradle (KMP setup)
* Kotlinx Serialization plugin
* Coroutines
* KSP
* GitHub Actions (planned CI/CD)

## 🟪 Project Architecture

### **📘 Design Philosophy**

The project follows a clean, scalable and production-oriented structure based on:

* **Clean Architecture** for clear separation of concerns.
* **MVVM** for UI state management.
* **Use Cases** to encapsulate business logic independently of UI layers.
* **Repository pattern** to unify API + local database + caching.
* **Kotlin Multiplatform** to share 100% of business logic across Android and iOS.

This architecture ensures testability, modularity, and long-term maintainability.

---

### **📁 Code Structure (Real Project Layout)**

```
commonMain
└── com.returdev.animemanga
    ├── core
    ├── data
    │   ├── cache
    │   ├── library
    │   ├── model
    │   ├── paging
    │   ├── remote
    │   └── repository
    ├── di
    ├── domain
    │   └── model
    ├── ui
    │   ├── core
    │   ├── model
    │   ├── screen
    │   └── theme
```

Android and iOS modules contain only their platform-specific launchers and integrations.

---

### **🔄 Data Flow**

```
UI (Compose Multiplatform)
        ↓
ViewModel (StateFlow)
        ↓
Repository
        ↓
Local DB (Room) ↔ Remote API (Ktor)
```

* The UI observes `StateFlow` from ViewModels.
* The Repository merges API, paging and Room database sources.
* Results propagate back to the UI reactively.

---

## 🟫 Installation & Running the Project

### **🔧 Requirements**

* Android Studio **Koala or newer**
* JDK **11**
* Xcode **15+** (only if running the iOS app)
* Kotlin Multiplatform tooling (included in Android Studio)

---

### **▶️ Run on Android**

```bash
git clone https://github.com/your-user/AnimeMangaVaultKMP.git
open Android Studio
Run → 'androidApp'
```

---

### **🍎 Run on iOS**

```bash
cd iosApp
open iosApp.xcworkspace
```

Select a simulator → Run.

---

### **📦 APK / Binaries**

⚠️ Binaries will be published later through **GitHub Releases**

---

## ⬛ Screenshots / Demo

⚠️ **UI is still in early development.**
Currently implemented:

* Full API integration
* Repository
* Paging
* Room KMP database
* Basic navigation
* Initial screens structure

📸 *Screenshots and GIFs will be added as UI development progresses.*

---

## ⬜ Roadmap

### **🔹 Core & Data Layer**

* [x] Shared module setup
* [x] Ktor client + endpoints
* [x] Serialization, error handling
* [x] Repository integration
* [x] Paging integration
* [ ] Search filters (implemented in data layer, pending UI integration)
* [ ] Recent search cache

### **🔹 Database**

* [x] Initial Room KMP setup
* [x] Base tables

### **🔹 UI / Navigation**

* [x] Compose Multiplatform setup
* [ ] Home screen
* [ ] Detail screens
* [ ] User library
* [ ] Advanced search UI
* [ ] Connection error screens
* [ ] Empty states and loading skeletons

### **🔹 iOS**

* [ ] Real device testing
* [ ] Platform-specific adjustments & UI fixes

### **🔹 Testing**

* [ ] Unit tests for UseCases
* [ ] Integration tests (Repository + Ktor + Room)

---

## 🔷 Comparison with Previous Project

This project is an evolution of a previous Android-only version of the app built in pure Kotlin.
The main improvements include:

* **Multiplatform support (Android + iOS)** thanks to KMP
* **A much cleaner and more scalable architecture** (Clean + MVVM + UseCases)
* **Shared networking, database and business logic**
* **Better folder organization and code separation**
* **Compose Multiplatform UI instead of Android-only Compose**
* **More complete documentation and maintainability**

> The goal of this new version is to demonstrate real-world, modern development skills while being fully cross-platform.

---

## 🔶 Technical Decisions

### **Why Kotlin Multiplatform?**

* To share business logic across Android and iOS.
* Reduce duplicated code: API, models, database, repositories, rules.
* Learn a highly in-demand technology that companies increasingly adopt.
* Future-proof architecture that can scale to Desktop/Web if needed.

### **Why Clean Architecture + MVVM + UseCases?**

* Clear separation of responsibilities.
* Easier to test and maintain.
* Scales better as feature complexity increases.
* Makes the shared module highly reusable and platform-agnostic.

### **Why Ktor + Room KMP + Koin?**

* **Ktor** is the standard for multiplatform networking.
* **Room KMP** allows sharing database logic across devices.
* **Koin** is lightweight and works well with KMP + Compose.
* Good ecosystem support and modern tooling.

---

## 🔷 Challenges Encountered

Even though the development went smoothly, a few meaningful challenges appeared:

### **1. Limited documentation for some KMP libraries**

Some ecosystems (Room KMP, paging KMP, certain Compose MPP components) still have evolving documentation.
**How I solved it:**

* Reading GitHub issues, samples, and source code directly.
* Testing small isolated samples before integrating them.

### **2. Finding a multiplatform-compatible media player**

iOS and Android have completely different media stacks, so finding a KMP-ready solution for trailers was not straightforward.
**How I solved it:**

* Evaluated multiple libraries.
* Chose `compose-multiplatform-media-player` for its simplest integration.

### **3. Adapting my Android architecture mindset to a shared codebase**

Moving logic that traditionally lived in Android-only layers into `commonMain` required rethinking some patterns.
**How I solved it:**

* Redefined what belongs to shared vs platform-specific.
* Improved decoupling by enforcing interface-based boundaries.

### **4. Balancing development time with scope**

With limited time available, maintaining a consistent pace while learning KMP concepts was challenging.
**How I managed it:**

* Defined a feature-first roadmap.
* Prioritized core architecture before UI.

These challenges, while not blocking, helped reinforce my ability to learn, adapt, and ship clean solutions.

---

## 🔶 Next Steps & Contributions

This project is **active and evolving**. Upcoming improvements include:

* Completing the UI for all core screens
* Implementing search filters in the UI
* Recent search cache
* Error handling screens and empty states
* Refining iOS-specific UI adjustments
* Adding unit and integration tests
* Adding screenshots, GIFs and a demo APK/IPA
* Potential Desktop/Web version in the long term

---

