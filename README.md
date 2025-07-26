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


## 📬 Using Postman for API Testing

If you prefer Postman over Swagger UI, follow these steps:

### 🔗 Base URL

```
http://localhost:8080
```

### 🔹 POST /promotion

- **URL**: `http://localhost:8080/promotion`
- **Method**: POST
- **Body** (raw → JSON):
```json
{
  "level": 8,
  "country": "IN",
  "spendTier": "LOW",
  "daysSinceLastPurchase": 3,
  "abBucket": "A"
}
```

### 🔹 GET /rules

- **URL**: `http://localhost:8080/rules`
- **Method**: GET

Optionally, add query param: `country=IN`

---

### 🔹 GET /metrics

- **URL**: `http://localhost:8080/metrics`
- **Method**: GET

---

### 🔹 POST /reloadRules

- **URL**: `http://localhost:8080/reloadRules`
- **Method**: POST

Use this if you make changes to `rules.yaml` and want to reload without restarting.

---

You can also import the Swagger OpenAPI into Postman:
- Visit: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)
- Export JSON
- Import into Postman as a collection



## 🧪 Testing Note

Unit and integration tests were **not implemented** in this version of the project.

### Why?

- The assignment did not explicitly require tests.
- I prioritized building clean, modular, and well-documented business logic first.
- I also wanted to ensure that the core APIs, rule parsing, and edge-case handling were fully functional end-to-end.

### Future Plan

I am eager to add:
- ✅ JUnit 5 + Mockito test coverage for `RuleEngineService`
- ✅ Validation tests for edge cases and exceptions
- ✅ Integration test cases for controller endpoints using `MockMvc`

Testing is an area I have actively started exploring and excited to improve upon.

---

## 🧠 Reflection and Rationale

### 🔧 a. Design Choices

#### i. Why This Design?

- **Rule Matching**: I used a `List<PromotionRule>` and iterated it using traditional loops. This ensured clarity, control, and performance for an in-memory system.
- **DTO Usage**: DTOs (`PlayerRequest`, `PromotionPayload`, `MetricsResponse`) were used to separate internal logic from API contracts, ensuring clean and maintainable code.
- **Modular Services**: Interfaces and implementations were separated (e.g., `RuleEngineServiceImpl`) to follow the SOLID principles and allow future extensibility.
- **YAML for Rules**: YAML was chosen as it’s easy to read/write for non-developers like product managers.
- **Enum Usage**: The `SpendTier` enum was added to prevent typos and standardize input.

#### ii. Alternatives Considered

- Considered using Java Stream APIs for filtering rules but avoided it to maintain clarity and because I’m still gaining fluency with streams.
- Considered using a database for rules instead of YAML, but the assignment required in-memory usage.

---

### ⚖️ b. Trade-offs

- **Simplicity vs Performance**: Opted for simple loops over more optimized data structures (like Maps or Trees) to keep the logic accessible and easy to debug.
- **Stateless vs Stateful**: Metrics are kept in memory using atomic counters—lightweight but not persistent.
- **No DB Persistence**: Faster startup and easier testing, but not ideal if you needed auditability or long-term storage.

---

### ❓ c. Areas of Uncertainty

- **Multiple Matches**: Unsure if all matching rules should be returned or just the first. I stuck to first-match logic but introduced a `priority` field for extensibility.
- **Suggestion Granularity**: I kept suggestion logic simple and rule-based rather than scoring or ML-based due to time and clarity constraints.
- **Validation Handling**: Chose to return structured error responses for validation but left out full i18n or field-specific messaging for brevity.

---

### 🤖 d. AI Assistance Disclosure

During the development of this project, AI tools such as **ChatGPT** were used for the following purposes:
- Drafting README sections and markdown formatting
- Generating sample `rules.yaml` data and mock player input requests
- Suggesting architectural improvements
- Explaining trade-offs (e.g., between time window checking vs performance)
- Clarifying backend concepts like A/B testing, time windows, and enum deserialization
- Suggesting best practices for error handling and code modularity
- Providing suggestions for improvement and extensibility
All business logic, service wiring, exception handling, and debugging were implemented manually in IntelliJ. 
AI was used as a learning and productivity assistant, not a code generator.

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
