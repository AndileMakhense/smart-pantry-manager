# Smart Pantry Manager

An Android app (Java) that helps reduce food waste. The user tracks the
ingredients they already have at home, and the app suggests recipes they can
cook using strictly those ingredients. A recipe is only suggested if every
ingredient is in the pantry in at least the required quantity.

**Author:** Andile Makhense | **Student No:** 402312724

**Module:** Mobile App Development 700, Richfield

## Database choice

SQLite via `SQLiteOpenHelper`. It runs locally on the device, needs no
account or backend, and matches the persistent-data approach taught in the
module.

## Planned features

- Pantry management (add, edit, delete items)
- 18 pre-loaded recipes
- Strict recipe matching with name normalization and safe unit conversion
- Recipe detail screen and settings screen

## Setup and run instructions

1. Install Android Studio.
2. Clone this repository: `git clone https://github.com/AndileMakhense/smart-pantry-manager.git`
3. Open the project folder in Android Studio and wait for Gradle sync.
4. Create or select an emulator (API 24 or higher) and press Run.

## Status

In development.