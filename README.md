[//]: # (# 🎰 Promotion Rule Engine – Scopely Backend Intern Assignment)

[//]: # ()
[//]: # (A lightweight, scalable backend service to evaluate and apply promotion rules based on dynamic player attributes.)

[//]: # ()
[//]: # (## 📌 Problem Statement)

[//]: # ()
[//]: # (Design and implement a Promotion Rule Engine that:)

[//]: # (- Loads rules from an in-memory YAML file at startup)

[//]: # (- Evaluates player attributes against these rules)

[//]: # (- Returns matching promotions, if any)

[//]: # (- Supports hot-reloading of rules at runtime)

[//]: # ()
[//]: # (## ✅ Features Implemented)

[//]: # ()
[//]: # (| Feature | Description |)

[//]: # (|--------|-------------|)

[//]: # (| 🔍 Rule Evaluation Engine | Matches player request against all rules |)

[//]: # (| 🧠 Smart Suggestions | Suggests what to change if no rule matches |)

[//]: # (| 📄 YAML Rules | Rules are defined in `rules.yaml` |)

[//]: # (| ♻️ Hot Reload | Reload rules without restarting via API |)

[//]: # (| 📊 Metrics API | Total requests, hits, misses, latency |)

[//]: # (| 🌍 Country + Bucket Filters | Optimized lookup for performance |)

[//]: # (| ⏰ Time Windows | Rules valid only in specific date/time range |)

[//]: # (| 🧪 A/B Testing | Rules apply based on A/B bucket |)

[//]: # (| ⚠️ Edge Case Handling | Invalid input, unknown fields, large rules |)

[//]: # (| 📘 Swagger UI | Auto-generated OpenAPI docs |)

[//]: # (| 🧱 Enum Validation | Enum-safe with case-insensitive config |)

[//]: # (| 🛑 Global Error Handling | Graceful 400 errors for invalid requests |)

[//]: # ()
[//]: # (---)

[//]: # ()
[//]: # (## 🧾 Tech Stack)

[//]: # ()
[//]: # (- Java 17)

[//]: # (- Spring Boot 3.5)

[//]: # (- Jackson YAML)

[//]: # (- Swagger)

[//]: # (- Lombok)

[//]: # ()
[//]: # ()
[//]: # ()
[//]: # (## 📂 Project Structure)

[//]: # ()
[//]: # (```text)

[//]: # (com.scopely.assignment.PromotionRuleEngineMicroservice)

[//]: # (│)

[//]: # (├── config/                 )

[//]: # (│   └── SwaggerConfig.java)

[//]: # (│)

[//]: # (├── controller/             )

[//]: # (│   └── PromotionController.java)

[//]: # (│)

[//]: # (├── dto/                    )

[//]: # (│   ├── MetricsResponse.java)

[//]: # (│   ├── PlayerRequest.java)

[//]: # (│   ├── PlayerPromotionResponse.java)

[//]: # (│   └── ValidationErrorResponse.java)

[//]: # (│)

[//]: # (├── exception/              )

[//]: # (│   └── GlobalExceptionHandler.java)

[//]: # (│)

[//]: # (├── metrics/                )

[//]: # (│   ├── MetricsService.java)

[//]: # (│   └── MetricsServiceImpl.java)

[//]: # (│)

[//]: # (├── model/                  )

[//]: # (│   ├── Condition.java)

[//]: # (│   ├── PromotionPayload.java)

[//]: # (│   ├── PromotionRule.java)

[//]: # (│   └── SpendTier.java)

[//]: # (│)

[//]: # (├── service/                )

[//]: # (│   ├── RuleEngineService.java)

[//]: # (│   ├── RuleEngineServiceImpl.java)

[//]: # (│   ├── RulesLoaderService.java)

[//]: # (│   └── RuleLoaderServiceImpl.java)

[//]: # (│)

[//]: # (└── PromotionRuleEngineMicroserviceApplication.java)

[//]: # ()
[//]: # ()
[//]: # (```)

[//]: # (## API Endpoints)

[//]: # ()
[//]: # (### Reload Rules)

[//]: # ()
[//]: # (`POST /reloadRules`)

[//]: # ()
[//]: # (Reloads the `rules.yaml` file at runtime without requiring a service restart.)

[//]: # ()
[//]: # (### Get Promotion for Player)

[//]: # ()
[//]: # (`POST /promotion`)

[//]: # ()
[//]: # (Evaluates player attributes against defined promotion rules and returns a matching promotion if found.)

[//]: # ()
[//]: # (#### Request Body)

[//]: # ()
[//]: # (The `PlayerRequest` object should contain the following player attributes:)

[//]: # ()
[//]: # (| Field                 | Type    | Description                               | Required | Example |)

[//]: # (| :-------------------- | :------ | :---------------------------------------- | :------- | :------ |)

[//]: # (| `level`               | integer | Player level &#40;minimum 1&#41;                  | Yes      | `6`     |)

[//]: # (| `country`             | string  | Player country code                       | Yes      | `"IN"`  |)

[//]: # (| `spendTier`           | string  | Player spend tier &#40;allowed: LOW, MEDIUM, HIGH&#41;. Case-insensitive. | No       | `"low"` |)

[//]: # (| `daysSinceLastPurchase` | integer | Days since player's last purchase         | No       | `3`     |)

[//]: # (| `abBucket`            | string  | A/B testing bucket assigned to the player | No       | `"A"`   |)

[//]: # ()
[//]: # (#### Responses)

[//]: # ()
[//]: # (* **200 OK**: Promotion matched and returned.)

[//]: # (* **204 No Content**: No matching promotion found.)

[//]: # (* **400 Bad Request**: Invalid request.)

[//]: # ()
[//]: # (### Get All Loaded Promotion Rules)

[//]: # ()
[//]: # (`GET /rules`)

[//]: # ()
[//]: # (Retrieves all currently loaded promotion rules. You can optionally filter rules by country using a query parameter.)

[//]: # ()
[//]: # (#### Query Parameters)

[//]: # ()
[//]: # (| Field     | Type   | Description                | Required |)

[//]: # (| :-------- | :----- | :------------------------- | :------- |)

[//]: # (| `country` | string | Country code to filter rules | No       |)

[//]: # ()
[//]: # (### Get Evaluation Metrics)

[//]: # ()
[//]: # (`GET /metrics`)

[//]: # ()
[//]: # (Returns performance metrics for the promotion rule engine, including total evaluations, hits, misses, and average latency.)



# 🎰 Promotion Rule Engine – Scopely Backend Intern Assignment

A lightweight, extensible Spring Boot microservice to evaluate promotion rules against player attributes, with A/B testing, time windows, smart suggestions, and metrics.

---

## 📌 Problem Statement

Design and implement a Promotion Rule Engine that:
- Loads rules from an in-memory YAML file
- Evaluates player inputs against these rules
- Returns matching promotions (if any)
- Supports A/B buckets, time-window rules, and suggestion logic
- Exposes REST APIs and metrics
- Supports hot-reloading of rules at runtime

---

## ✅ Features Implemented

| Feature | Description |
|--------|-------------|
| 🔍 Rule Evaluation Engine | Matches player request against all rules |
| 🧠 Smart Suggestions | Suggests what to change if no rule matches |
| 📄 YAML Rules | Rules are defined in `rules.yaml` |
| ♻️ Hot Reload | Reload rules without restarting via API |
| 📊 Metrics API | Total requests, hits, misses, latency |
| 🌍 Country + Bucket Filters | Optimized lookup for performance |
| ⏰ Time Windows | Rules valid only in specific date/time range |
| 🧪 A/B Testing | Rules apply based on A/B bucket |
| ⚠️ Edge Case Handling | Invalid input, unknown fields, large rules |
| 📘 Swagger UI | Auto-generated OpenAPI docs |
| 🧱 Enum Validation | Enum-safe with case-insensitive config |
| 🛑 Global Error Handling | Graceful 400 errors for invalid requests |

---

## 📂 Project Structure

```
com.scopely.assignment.PromotionRuleEngineMicroservice
│
├── config/                 
│   └── SwaggerConfig.java
│
├── controller/             
│   └── PromotionController.java
│
├── dto/                    
│   ├── MetricsResponse.java
│   ├── PlayerRequest.java
│   ├── PlayerPromotionResponse.java
│   └── ValidationErrorResponse.java
│
├── exception/              
│   └── GlobalExceptionHandler.java
│
├── metrics/                
│   ├── MetricsService.java
│   └── MetricsServiceImpl.java
│
├── model/                  
│   ├── Condition.java
│   ├── PromotionPayload.java
│   ├── PromotionRule.java
│   └── SpendTier.java
│
├── service/                
│   ├── RuleEngineService.java
│   ├── RuleEngineServiceImpl.java
│   ├── RulesLoaderService.java
│   └── RuleLoaderServiceImpl.java
│
└── PromotionRuleEngineMicroserviceApplication.java
```

---

## 🛠️ API Endpoints

> Base URL: `http://localhost:8080`

### 🎯 POST `/promotion`

Evaluates a player's attributes and returns a matching promotion or a helpful suggestion.

**Request Body**
```json
{
  "level": 6,
  "country": "IN",
  "spendTier": "LOW",
  "daysSinceLastPurchase": 3,
  "abBucket": "A"
}
```

**Success Response `200 OK`**
```json
{
  "promotion": {
    "title": "100 Coins for A",
    "reward": "100 coins"
  },
  "suggestion": null
}
```

**No Match Response `200 OK`**
```json
{
  "promotion": null,
  "suggestion": "Level up to 10+ to unlock Loyalty Reward"
}
```

**Validation Error `400`**
```json
{
  "error": "Validation failed",
  "details": {
    "spendTier": "must not be null"
  }
}
```

---

### ♻️ POST `/reloadRules`

Reloads rules from the YAML file at runtime without restarting the app.

**Response**
```
200 OK
"Rules reloaded successfully."
```

---

### 📊 GET `/metrics`

Returns statistics on rule evaluations.

```json
{
  "totalEvaluations": 12,
  "hits": 7,
  "misses": 5,
  "averageLatencyMs": 2.1
}
```

---

### 📘 GET `/rules`

Returns the list of loaded promotion rules. You can optionally filter by `country`.

**Example Request**
```
GET /rules?country=IN
```

**Example Response**
```json
[
  {
    "id": "promoA1",
    "conditions": {
      "minLevel": 5,
      "country": "IN",
      "abBucket": "A"
    },
    "promotion": {
      "title": "100 Coins for A",
      "reward": "100 coins"
    },
    "priority": 10
  }
]
```

---

### 🔗 Swagger UI

Access Swagger docs:  
`http://localhost:8080/swagger-ui/index.html`  
Raw OpenAPI JSON:  
`http://localhost:8080/v3/api-docs`

---

## ⚙️ How to Set Up Locally

Follow these steps to run the Promotion Rule Engine microservice on your machine:

### 1️⃣ Prerequisites

- Java 17+ installed
- Maven installed
- IDE (IntelliJ / VS Code)
- (Optional) Postman or Swagger for API testing

### 2️⃣ Clone the Repository

```bash
  git clone https://github.com/tyagishubhangam/PromotionRuleEngine.git
  cd PromotionRuleEngineMicroservice
```

### 3️⃣ Build the Project

```bash
  mvn clean install
```

### 4️⃣ Run the Application

```bash
  mvn spring-boot:run
```

or run `PromotionRuleEngineMicroserviceApplication.java` directly from your IDE.

### 5️⃣ Access the API

- Swagger UI: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- Base URL: `http://localhost:8080`

### 6️⃣ Reload Rules (Optional)

Use this endpoint after editing `rules.yaml`:

```
POST http://localhost:8080/reloadRules
```

---



## 🚀 Future Enhancements

- 🔝 Rule prioritization logic (e.g., highest priority wins)
- ➕ Add new rule via admin POST endpoint
- 🧪 Add basic test coverage (JUnit + Mockito)
- 🐳 Dockerize for container-based deployment
- 📷 Add Swagger screenshots in README
- 📊 Promotion analytics/logging
- ⚡ Caching and lookup optimization for large rule sets

---



## 🙌 Final Note

Built for Scopely Backend Internship to demonstrate real-world backend architecture with best practices.
