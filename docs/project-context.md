# LotoLab — Project Context

## Project Vision

LotoLab is a statistical analysis platform dedicated to the French FDJ Lotto.

The primary goal is not prediction.

The project studies the structure of lottery draws through three perspectives:

1. Theoretical probability distributions.
2. Historical FDJ draw distributions.
3. Comparison between theory and observed reality.

The application must never claim to increase winning probabilities.

The application must never present generated grids as more likely to win.

Generated grids are only intended to match statistical patterns observed in theory and history.

---

## Core Principle

Every conjecture must expose:

* definition
* calculation method
* theoretical distribution
* historical distribution
* deviation
* interpretation

The comparison between theory and historical observations is one of the most important features of the project.

---

## Current Technology Stack

Backend

* Java 25
* Spring Boot
* Maven
* MongoDB
* Spring Data MongoDB
* OpenAPI / Swagger

Frontend

* Vue 3
* Vuetify

---

## Coding Philosophy

Prefer:

* simple code
* readability
* maintainability
* explicit naming
* unit testing

Avoid:

* premature optimization
* over-engineering
* unnecessary abstractions
* unnecessary layers

---

## Development Rules

Only implement the scope explicitly requested.

Do not implement future roadmap items.

Do not create speculative features.

Do not modify unrelated files.

If a design decision is unclear, ask for clarification instead of guessing.

---

## Main Domain Concepts

Draw

Represents a historical FDJ draw.

DrawMetrics

Represents calculated metrics associated with a draw.

Conjecture

Represents a statistical observation.

DistributionBucket

Represents a frequency bucket.

TheoreticalDistribution

Represents expected combinatorial distributions.

HistoricalDistribution

Represents observed distributions.

Comparison

Represents differences between theoretical and observed distributions.

LNI

Loto Naturalness Index.

Measures how closely a grid matches statistical patterns observed in theory and history.
