# quarkus-otel-cdi-or-not

Demonstrates that `@WithSpan` (OpenTelemetry) on plain Java classes only works in Quarkus when the CDI producer uses [`InterceptionProxy`](https://quarkus.io/guides/cdi#interceptionproxy).

## The problem

In Quarkus, non-CDI-bean classes instantiated with `new` in a `@Produces` method are **not** intercepted by CDI. Annotations like `@WithSpan` are silently ignored — no child spans appear in traces.

## What this project proves

| Producer style | Service class | Endpoint | `@WithSpan` span created? |
|---|---|---|---|
| `InterceptionProxy<T>.create(new T())` | `GreetingService` | `GET /v1/hello` | Yes |
| Plain `new T()` | `PlainGreetingService` | `GET /v1/plain-hello` | No |

Both services are identical plain Java classes with `@WithSpan` on their `greet` method. The only difference is how they are produced as CDI beans.

## Running

```bash
./gradlew test
```

The two `@QuarkusTest` classes verify the behavior:

- **`InterceptionProxySpanTest`** — asserts `GreetingService.greet` span **is** present
- **`PlainProducerSpanTest`** — asserts `PlainGreetingService.greet` span is **not** present

## Requirements

- Java 25
- Quarkus 3.31+
