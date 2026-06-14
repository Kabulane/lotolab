# Development Roadmap

## Guiding Principle

LotoLab is first and foremost a statistical analysis platform.

The project is built around the comparison between:

```txt
Theoretical Probability Distributions
vs
Observed Historical Distributions
```

Grid generation is a consequence of these analyses, not the primary objective.

The application must never claim to predict future draws or increase winning probabilities.

Its purpose is to:

* study draw structures;
* compare theory and reality;
* identify statistical patterns;
* provide educational visualizations;
* generate statistically natural grids.

---

# Phase 1 — Technical Foundations

## Step 0 — Repository Initialization

### Goal

Prepare the project workspace and Git repository.

### Deliverables

```txt
README.md
docs/
.gitignore
```

### Validation

* Git repository initialized
* GitHub remote configured
* Initial commit pushed

---

## Step 1 — Spring Boot Backend Initialization

### Goal

Create the Java backend foundation.

### Deliverables

```txt
backend/
├── pom.xml
├── src/main/java
├── src/main/resources
├── src/test/java
└── README.md
```

### Features

* Java 25
* Spring Boot
* Maven
* OpenAPI / Swagger
* Health endpoint

### Validation

* mvn test succeeds
* application starts
* Swagger UI accessible
* `/api/health` responds correctly

---

## Step 2 — MongoDB Setup

### Goal

Prepare local persistence.

### Deliverables

```txt
docker-compose.yml
application.yml
```

### Features

* MongoDB
* Spring Data MongoDB
* Local Docker environment

### Validation

* MongoDB starts successfully
* Spring connects correctly

---

# Phase 2 — Historical Data Model

## Step 3 — Draw Model

### Goal

Represent a historical FDJ draw.

### Deliverables

```txt
Draw
DrawRepository
DrawController
```

### Stored Data

* draw date
* five main numbers
* chance number
* metrics

---

## Step 4 — Metrics Engine

### Goal

Calculate draw metrics.

### Metrics

* sum
* range
* even count
* odd count
* represented decades
* low/high numbers
* gaps
* consecutive numbers

### Deliverables

```txt
DrawMetrics
DrawMetricsCalculator
```

### Validation

Unit tests covering all metrics.

---

## Step 5 — FDJ Historical Import

### Goal

Import historical draw archives.

### Features

* upload
* parsing
* normalization
* metrics calculation
* persistence

### Validation

Historical draws successfully stored.

---

# Phase 3 — Historical Analysis

## Step 6 — Historical Distributions

### Goal

Build observed distributions.

### Examples

* sums
* ranges
* parity
* decades
* gaps

### Deliverables

```txt
HistoricalDistributionService
DistributionBucket
```

---

## Step 7 — Historical Analysis API

### Goal

Expose historical distributions.

### Endpoints

```txt
GET /api/conjectures
GET /api/conjectures/{id}
```

### Validation

Each conjecture exposes:

* definition
* calculation method
* historical distribution
* interpretation

---

# Phase 4 — Theoretical Engine

## Step 8 — Theoretical Distribution Engine

### Goal

Calculate expected distributions from combinatorics.

### Examples

* parity distribution
* decade distribution
* low/high distribution
* consecutive number distribution

### Deliverables

```txt
TheoreticalDistributionService
```

### Validation

Theoretical distributions generated independently from historical data.

---

## Step 9 — Theory API

### Goal

Expose theoretical distributions.

### Endpoints

```txt
GET /api/theory
GET /api/theory/{id}
```

---

# Phase 5 — Theory vs Historical Comparison

## Step 10 — Comparison Engine

### Goal

Compare:

```txt
Observed Distribution
-
Expected Distribution
=
Deviation
```

### Deliverables

```txt
ComparisonService
```

### Metrics

For each conjecture:

* theoretical value
* observed value
* absolute deviation
* relative deviation

---

## Step 11 — Comparison API

### Goal

Expose comparison results.

### Endpoints

```txt
GET /api/comparison
GET /api/comparison/{id}
```

### Validation

Every conjecture can display:

* theory
* historical data
* deviation
* interpretation

---

# Phase 6 — Frontend

## Step 12 — Vue 3 + Vuetify Initialization

### Goal

Create frontend foundation.

### Pages

```txt
Dashboard
Theory
History
Comparison
Generator
```

---

## Step 13 — Dashboard

### Goal

Provide a global project overview.

### Content

* draw count
* import statistics
* available conjectures
* quick navigation

---

## Step 14 — Theory View

### Goal

Visualize theoretical distributions.

### Content

* charts
* tables
* explanations

---

## Step 15 — Historical View

### Goal

Visualize observed distributions.

### Content

* charts
* tables
* examples

---

## Step 16 — Comparison View

### Goal

Compare theory and reality.

### Content

For each conjecture:

* theoretical chart
* historical chart
* deviation chart
* interpretation

### Importance

This is one of the core pages of the application.

---

# Phase 7 — Naturality Score (LNI)

## Step 17 — LNI Calculation

### Goal

Create a naturality score.

### LNI

```txt
LNI = Loto Naturalness Index
```

The score must be based on:

* historical distributions
* theoretical distributions
* observed deviations

### Validation

Explainable score from 0 to 100.

---

## Step 18 — Grid Analysis

### Goal

Allow users to analyze their own grids.

### Inputs

```txt
5 numbers
+
chance number
```

### Outputs

* LNI score
* metric breakdown
* comparison to theory
* comparison to history

---

# Phase 8 — Grid Generation

## Step 19 — Natural Grid Generator

### Goal

Generate grids matching a target LNI range.

### Modes

```txt
Natural
Atypical
Controlled Chaos
```

### Validation

Generated grids include explanations.

---

## Step 20 — Assisted Grid Completion

### Goal

Allow users to provide fixed numbers.

### Example

```txt
User numbers:
7, 22

Target LNI:
80-90
```

The system completes the remaining numbers.

### Constraints

* historical distributions
* theoretical distributions
* target LNI

---

# Phase 9 — Documentation & Deployment

## Step 21 — Documentation

### Goal

Produce complete project documentation.

### Sections

* installation
* architecture
* methodology
* API
* limitations

---

## Step 22 — Deployment Preparation

### Goal

Prepare a production-ready version.

### Features

* Dockerization
* configuration management
* monitoring basics
* deployment documentation

---

# Definition of Done — MVP V1

The MVP is complete when:

* backend starts successfully;
* MongoDB runs through Docker Compose;
* FDJ historical data can be imported;
* metrics are calculated;
* historical distributions are available;
* theoretical distributions are available;
* comparison engine is available;
* frontend displays theory and historical data;
* comparison page works;
* users can analyze a grid;
* users can generate grids based on a target LNI;
* documentation is complete;
* code is versioned on GitHub.
