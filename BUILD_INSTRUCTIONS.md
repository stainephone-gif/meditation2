# Инструкция по сборке APK для MindfulMoments

## Предварительные требования

### 1. Установка Java Development Kit (JDK)

Требуется JDK 17 или выше.

**На Ubuntu/Debian:**
```bash
sudo apt update
sudo apt install openjdk-17-jdk
java -version  # Проверка установки
```

**На macOS:**
```bash
brew install openjdk@17
```

**На Windows:**
Скачайте JDK с официального сайта Oracle или используйте [Adoptium](https://adoptium.net/)

### 2. Установка Android SDK (опционально)

Android SDK загрузится автоматически при первой сборке через Gradle. Но если хотите установить вручную:

**Через Android Studio:**
1. Скачайте [Android Studio](https://developer.android.com/studio)
2. Запустите и следуйте инструкциям установки
3. SDK Manager автоматически установит необходимые компоненты

**Через командную строку:**
```bash
# Скачайте command line tools с официального сайта
# https://developer.android.com/studio#command-tools
```

## Сборка APK

### Метод 1: Через Gradle Wrapper (рекомендуется)

#### Debug версия (для тестирования)
```bash
# Перейдите в директорию проекта
cd meditation2

# Сделайте gradlew исполняемым (Linux/Mac)
chmod +x gradlew

# Соберите debug APK
./gradlew assembleDebug

# На Windows используйте:
gradlew.bat assembleDebug
```

#### Release версия (для продакшена)
```bash
# Соберите release APK
./gradlew assembleRelease

# APK будет оптимизирован с ProGuard
```

### Метод 2: Через Android Studio

1. Откройте проект в Android Studio
2. Меню: Build → Build Bundle(s) / APK(s) → Build APK(s)
3. Дождитесь завершения сборки
4. Нажмите "locate" в уведомлении для открытия папки с APK

## Местоположение собранных APK

После успешной сборки найдите APK файлы:

**Debug версия:**
```
app/build/outputs/apk/debug/app-debug.apk
```

**Release версия:**
```
app/build/outputs/apk/release/app-release.apk
```

## Установка APK на устройство

### Метод 1: Через ADB (Android Debug Bridge)

```bash
# Убедитесь, что устройство подключено и ADB установлен
adb devices

# Установите APK
adb install app/build/outputs/apk/debug/app-debug.apk

# Для переустановки (сохраняя данные)
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

### Метод 2: Ручная установка

1. Скопируйте APK файл на Android устройство
2. Откройте файловый менеджер на устройстве
3. Найдите скопированный APK файл
4. Нажмите на файл для установки
5. Разрешите установку из неизвестных источников (если требуется)

### Метод 3: Через облачные сервисы

1. Загрузите APK в облако (Google Drive, Dropbox, etc.)
2. Откройте ссылку на Android устройстве
3. Скачайте и установите APK

## Подписание Release APK

Для публикации в Google Play нужно подписать APK:

### 1. Создайте keystore
```bash
keytool -genkey -v -keystore my-release-key.jks -keyalg RSA -keysize 2048 -validity 10000 -alias my-key-alias
```

### 2. Добавьте в app/build.gradle.kts
```kotlin
android {
    signingConfigs {
        create("release") {
            storeFile = file("my-release-key.jks")
            storePassword = "your-password"
            keyAlias = "my-key-alias"
            keyPassword = "your-password"
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
        }
    }
}
```

### 3. Соберите подписанный APK
```bash
./gradlew assembleRelease
```

## Оптимизация размера APK

Текущие оптимизации включены:

- ✅ ProGuard минификация (в release сборке)
- ✅ Resource shrinking
- ✅ Vector Drawables вместо PNG
- ✅ Удаление неиспользуемого кода

Дополнительные опции:

### Включить R8 (вместо ProGuard)
Уже включен по умолчанию в Android Gradle Plugin 3.4+

### Создать App Bundle вместо APK
```bash
./gradlew bundleRelease
```
App Bundle автоматически оптимизируется для каждого устройства.

## Решение проблем

### Ошибка: "SDK location not found"
Создайте файл `local.properties` в корне проекта:
```properties
sdk.dir=/path/to/your/Android/sdk
```

### Ошибка: "Java heap space"
Увеличьте память для Gradle в `gradle.properties`:
```properties
org.gradle.jvmargs=-Xmx4096m
```

### Ошибка: "License not accepted"
Установите Android SDK и примите лицензии:
```bash
yes | sdkmanager --licenses
```

### Slow build times
Включите Gradle daemon и parallel execution в `gradle.properties`:
```properties
org.gradle.daemon=true
org.gradle.parallel=true
org.gradle.caching=true
```

## Проверка APK

### Информация об APK
```bash
# Размер APK
ls -lh app/build/outputs/apk/debug/app-debug.apk

# Детальная информация
aapt dump badging app/build/outputs/apk/debug/app-debug.apk

# Содержимое APK
unzip -l app/build/outputs/apk/debug/app-debug.apk
```

### Анализ APK в Android Studio
1. Build → Analyze APK
2. Выберите собранный APK
3. Изучите размеры компонентов, методы, ресурсы

## Целевые размеры

- **Debug APK**: ~10-15 MB
- **Release APK**: ~8-12 MB (с ProGuard)
- **App Bundle**: ~6-8 MB (для одного устройства)

## Дополнительные команды Gradle

```bash
# Очистить проект
./gradlew clean

# Очистить и собрать
./gradlew clean assembleDebug

# Запустить тесты
./gradlew test

# Проверить зависимости
./gradlew dependencies

# Список всех задач
./gradlew tasks
```

## Continuous Integration (CI)

Пример GitHub Actions для автоматической сборки:

```yaml
name: Android CI

on:
  push:
    branches: [ main ]
  pull_request:
    branches: [ main ]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v3
    - name: Set up JDK 17
      uses: actions/setup-java@v3
      with:
        java-version: '17'
        distribution: 'temurin'
    - name: Grant execute permission for gradlew
      run: chmod +x gradlew
    - name: Build with Gradle
      run: ./gradlew assembleDebug
    - name: Upload APK
      uses: actions/upload-artifact@v3
      with:
        name: app-debug
        path: app/build/outputs/apk/debug/app-debug.apk
```

---

Для получения дополнительной помощи обратитесь к [официальной документации Android](https://developer.android.com/studio/build).
