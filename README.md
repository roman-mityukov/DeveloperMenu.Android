# DeveloperMenu
DeveloperMenu - отладочное меню для вашего Android приложения

# Использование
Есть два способа открытия меню из вашего приложения.
- Слегка потрясти устройство. Для этого нужно в метод ```onCreate``` вашего приложения добавить следующий код
    ```
    if (BuildConfig.DEBUG) {
        DevMenuLauncher.startShakeDetection(applicationContext = this, devMenu = this.devMenu)
    }
    ```
    где this.devMenu - ссылка на экземпляр класса DevMenu (объект меню, состоящее из модулей), который вы создаете самостоятельно из нужных вам модулей. Например, так:
    ```
    //Предположим, что используется Dagger
    @Provides
    @Singleton
    fun provideDevMenu(
        applicationContext: Context
    ): DevMenu {
        return DevMenu.Builder()
            .add(BuildInfoItem(applicationContext))
            .add(DeviceInfoItem(applicationContext))
            .add(SettingsItem())
            .build()
    }
    ```
- Или по любому другому событию (например тап по кнопке) с помощью

    ```
    DevMenuLauncher.start(applicationContext = this.applicationContext, devMenu = this.devMenu)
    ```

В ```build.gradle.kts``` в корне проекта нужно добавить репозиторий с библиотекой в следующем порядке
```
allprojects {
    repositories {
        maven {
            setUrl("https://dl.bintray.com/actonica/android")
        }
        google()
        jcenter()
    }
}
```

И в ```app/build.gradle.kts``` добавить зависимости меню и нужных модулей
```
dependencies {
    ...
    implementation("com.actonica.devmenu:devmenu:0.9.2") //меню
    //зависимости модулей меню, которые вы выберите. См. ниже
}
```

# Модули меню

![](/images/devmenu_share_button.png)

Модули меню могут возвращать данные, которыми можно поделиться. Если нажать на кнопку "Поделиться", то для отправки с помощью Intent.ACTION_SEND будет доступен текст примерно следующего содержания (в зависимости от состава меню, который вы определяете сами):
```
Developer Menu
    ********************
    BuildInfo
    versionCode = 11253
    versionName = 1.0.11253
    packageName = com.actonica.devmenusample
    requestedPermissions = INTERNET
    ACCESS_COARSE_LOCATION
    ACCESS_FINE_LOCATION
    ACCESS_NETWORK_STATE
    grantedPermissions = INTERNET
    ACCESS_COARSE_LOCATION
    ACCESS_FINE_LOCATION
    ACCESS_NETWORK_STATE
    ********************
    DeviceInfo
    manufacturer = Google
    model = Android SDK built for x86
    apiVersion = 25
    androidVersion = 7.1.1
    screenResolution = 1794x1080
    screenDensity = 2.625 null dpi 420
    ********************
    BaseUrl
    baseUrlManual = null
    baseUrlPreset = https://yandex.com/
    ********************
    Logger Log4j2
    logLevel = ALL
    isLogInFileEnabled = false
    ********************
    Location
    latitude = 53.7676983
    longitude = 87.1103983
    time = 00:05:52 25.01.2019
    provider = fused
    accuracy = 20.0m
```

## BuildInfo

![](/images/devmenu_buildinfo.png)

Основная информация о билде
 - versionCode - код версии
 - versionName - имя версии
 - packageName - имя пакета
 - Requested Permissions - разрешения запрошенные приложением
 - Granted Permissions - разрешения, которые приложение получило

```
implementation("com.actonica.devmenu:devmenu_buildinfo:0.9.2")
```
 
## DeviceInfo
 
![](/images/devmenu_deviceinfo.png)
 
Основная информация об устройстве
 - Manufacturer - производитель устройства
 - Model - модель устройства
 - Android - версия ос Android
 - API - версия API
 - Resolution - разрешение экрана в пикселях (например 1920x1080)
 - Density - плотность экрана в формате displayMetrics.density + буквенное обозначение + displayMetrics.densityDpi (например 3.0 xxhdpi 420)

 ```
 implementation("com.actonica.devmenu:devmenu_deviceinfo:0.9.2")
 ```

## BaseUrl
 
![](/images/devmenu_baseurl.png)
 
Изменение значений ```baseUrl```, которые могут использоваться в http-клиентах. Можно ввести значения вручную или выбрать из значений, которые вы можете передать при создании модуля. Введенные значения сохраняются в SharedPreferences и доступны по ключам ```BaseUrlContract.KEY_MANUAL_BASE_URL``` и ```BaseUrlContract.KEY_PRESET_BASE_URL```

```
implementation("com.actonica.devmenu:devmenu_baseurl:0.9.2")
```
 
## HTTP
 
![](/images/devmenu_http.png)
 
Просмотр истории запросов OkHttp клиента приложения. В модуле используется библиотека [Chuck](https://github.com/jgilfelt/chuck)

```
implementation("com.actonica.devmenu:devmenu_http:0.9.2")
```

В OkHttp клиент нужно добавить ChuckInterceptor
```
val client = OkHttpClient.Builder()
                    .addInterceptor(ChuckInterceptor(context))
                    .build()
```

![](/images/devmenu_http_chuck.gif)
 
## Logcat
 
![](/images/devmenu_logcat.png)
 
Просмотр Logcat. В модуле используется библиотека [Lynx](https://github.com/pedrovgs/Lynx)

```
implementation("com.actonica.devmenu:devmenu_logcat:0.9.2")
```
 
![](/images/devmenu_logcat_lynx.gif)
 
## Logger
 
![](/images/devmenu_logger.png)
 
Подключает к приложению логгер log4j2. Позволяет
- включать/выключать логирование в файл
- выбирать уровень логирования
- просматривать последние логи
- делиться файлами с логами
- удалять логи

```
implementation("com.actonica.devmenu:devmenu_logger:0.9.2")
```

Для инициализации логгера в методе ```onCreate``` вашего приложения нужно добавить следующий код
```
if (BuildConfig.DEBUG) {
            DevMenuLogger.init(
                applicationContext = this.applicationContext,
                configXmlResource = R.raw.log4j2_config_debug
            )
        } else {
            DevMenuLogger.init(
                applicationContext = this.applicationContext,
                configXmlResource = R.raw.log4j2_config_release
            )
        }
```
где ```R.raw.log4j2_config_debug``` и ```R.raw.log4j2_config_release``` - xml с конфигами логгера log4j2 для отладочной и релизной версии
```
<?xml version="1.0" encoding="UTF-8"?>

<Configuration status="debug">
    <Appenders>
        <Logcat name="Logcat">
            <ThresholdFilter
                level="ALL"
                onMatch="ACCEPT"
                onMismatch="DENY" />
            <PatternLayout pattern="%m" />
        </Logcat>
        <RollingFile
            name="RollingFile"
            fileName="${logsdirpathlookup:externaldir}/com.actonica.devmenusample.log"
            filePattern="${logsdirpathlookup:externaldir}/com.actonica.devmenusample-%i.log.zip">
            <ThresholdFilter
                level="ALL"
                onMatch="DENY"
                onMismatch="DENY" />
            <PatternLayout>
                <Pattern>%d %p %c{1.} [%t] %m%n</Pattern>
            </PatternLayout>
            <Policies>
                <SizeBasedTriggeringPolicy size="1 MB" />
            </Policies>
            <DefaultRolloverStrategy max="5" />
        </RollingFile>
    </Appenders>

    <Loggers>
        <Root level="ALL">
            <AppenderRef ref="Logcat" />
            <AppenderRef ref="RollingFile" />
        </Root>
    </Loggers>
</Configuration>
```

С помощью ```${logsdirpathlookup:externaldir}``` можно указать путь ```applicationContext.getExternalFilesDir()``` или с помощью ```${logsdirpathlookup:internaldir}``` путь ```applicationContext.filesDir```

Подробная информация о log4j2 и его конфигурации может быть найдена [здесь](https://logging.apache.org/log4j/2.x/)

Можно использовать логирование через фасад slf4j. Для этого нужно добавить следующие зависимости
```
implementation("org.slf4j:slf4j-api:1.7.25") //simple logging facade for java
implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.3") //bridge from log4j2 to slf4j
implementation("io.rm.log4j2.android:log4j2-android:1.0.0") //this library
```
И в коде создать логгер с помощью slf4j api
```
import org.slf4j.Logger
import org.slf4j.LoggerFactory

val logger: Logger = LoggerFactory.getLogger("MyLoggerName")
```

Или можно использовать api log4j2 напрямую
```
dependencies {
  implementation("org.apache.logging.log4j:log4j-api:2.3")
  implementation("io.rm.log4j2.android:log4j2-android:1.0.0")
}
```
Создать логгер
```
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

val logger: Logger = LogManager.getLogger("MyLoggerName")
```

Также может понадобиться следующее в ```app/build.gradle.kts```
```
android {
  defaultConfig {
    ...
    javaCompileOptions.annotationProcessorOptions.includeCompileClasspath = false
  }
}
packagingOptions {
        exclude 'META-INF/LICENSE'
        exclude 'META-INF/NOTICE'
        exclude 'META-INF/DEPENDENCIES'
    }
```

## Location
![](/images/devmenu_location.png)

Отображение данных геолокации устройства в меню и просмотр на карте.

```
implementation("com.actonica.devmenu:devmenu_location:0.9.2")
```

## Settings
![](/images/devmenu_settings.png)

Навигация в следующие настройки
- Developer Options
- App info
- Location
- Settings

```
implementation("com.actonica.devmenu:devmenu_settings:0.9.2")
```

## Credits
- [Seismic](https://github.com/square/seismic) - Android device shake detection
- [log4j2](https://github.com/apache/logging-log4j2) - Apache Log4j 2 is an upgrade to Log4j that provides significant improvements over its predecessor, Log4j 1.x, and provides many of the improvements available in Logback while fixing some inherent problems in Logback's architecture.
- [lynx](https://github.com/pedrovgs/Lynx) - Lynx is an Android library created to show a custom view with all the information Android logcat is printing, different traces of different levels will be rendered to show from log messages to your application exceptions.
- [chuck](https://github.com/jgilfelt/chuck) - An in-app HTTP inspector for Android OkHttp clients
- [DebugDrawer](https://github.com/palaima/DebugDrawer) - Android Debug Drawer for faster development