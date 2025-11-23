# Moon Phases MCP Server 🌙

MCP Server для получения информации о фазах Луны через APIVerve Moon Phases API.

## Возможности

- 🌑 Получение текущей фазы Луны
- 📅 Получение фазы Луны на конкретную дату
- 🌕 Информация о следующем/предыдущем полнолунии
- 📊 Детальная лунная информация (возраст, расстояние, лунация)

## Технологии

- **Kotlin** 1.9.22
- **Ktor** 2.3.7 (Server + Client)
- **Kotlinx Serialization**
- **Logback** для логирования

## Запуск

### Через Gradle

```bash
./gradlew run
```

Сервер запустится на порту **8081** (http://127.0.0.1:8081)

### Сборка Fat JAR

```bash
./gradlew fatJar
```

Запуск JAR:

```bash
java -jar build/libs/moonphases-mcp-server-all.jar
```

## API Endpoints

### Health Check

```
GET /health
```

Ответ:
```json
{
  "status": "ok",
  "service": "moon-phases-mcp",
  "timestamp": 1234567890
}
```

### Получить доступные инструменты

```
GET /tools
```

Ответ:
```json
{
  "tools": [
    {
      "id": "current_moon_phase",
      "title": "Current Moon Phase",
      "description": "Get the current moon phase information",
      "sampleQuery": "What is the current moon phase?"
    }
  ]
}
```

### Получить текущую фазу Луны

```
GET /moonphase/current
```

Ответ:
```json
{
  "phase": "Last Quarter",
  "phaseEmoji": "🌗",
  "waxing": false,
  "waning": true,
  "lunarAge": 22.91781121430745,
  "lunarAgePercent": 0.7760702713626415,
  "lunationNumber": 1264,
  "lunarDistance": 62.68375671610132,
  "nextFullMoon": "2025-03-23T00:00:00Z",
  "lastFullMoon": "2025-01-22T00:00:00Z"
}
```

### Получить фазу Луны на дату (GET)

```
GET /moonphase?date=11-19-2025
```

### Получить фазу Луны на дату (POST)

```
POST /moonphase
Content-Type: application/json

{
  "date": "11-19-2025"
}
```

**Формат даты:** MM-DD-YYYY

## Конфигурация

- **API Key:** `04b8d5a8-96b6-4926-a2ed-17ba06cd33af` (хардкод в `MoonPhasesService.kt`)
- **Port:** `8081` (можно изменить через переменную окружения `PORT`)
- **Host:** `127.0.0.1`

## Структура проекта

```
MoonPhasesMCPServer/
├── src/main/kotlin/dev/kamikaze/moonphases/mcp/
│   ├── Application.kt          # Точка входа
│   ├── models/
│   │   ├── Request.kt          # Модели запросов
│   │   └── Response.kt         # Модели ответов
│   ├── services/
│   │   └── MoonPhasesService.kt # Сервис для работы с API
│   ├── routes/
│   │   ├── MoonPhaseRoute.kt   # Роуты для фаз Луны
│   │   └── ToolsRoute.kt       # Роут для инструментов
│   └── plugins/
│       ├── Serialization.kt    # JSON сериализация
│       ├── HTTP.kt             # CORS, StatusPages
│       ├── Monitoring.kt       # Логирование
│       └── Routing.kt          # Настройка роутов
└── src/main/resources/
    └── logback.xml             # Конфигурация логирования
```

## Примеры использования

### cURL

```bash
# Текущая фаза
curl http://127.0.0.1:8081/moonphase/current

# Фаза на конкретную дату
curl "http://127.0.0.1:8081/moonphase?date=11-19-2025"

# POST запрос
curl -X POST http://127.0.0.1:8081/moonphase \
  -H "Content-Type: application/json" \
  -d '{"date": "11-19-2025"}'
```

### Kotlin/Ktor Client

```kotlin
val client = HttpClient(CIO) {
    install(ContentNegotiation) {
        json()
    }
}

val response: MoonPhaseResponse = client.get("http://127.0.0.1:8081/moonphase/current").body()
println("Current phase: ${response.phase} ${response.phaseEmoji}")
```

## Лицензия

MIT

## Автор

kamikaze.dev
