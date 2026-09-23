# SysMon Server 🟢

A lightweight, multi-threaded Java TCP server designed to receive and process real-time system telemetry via JSON payloads.

This repository is the backend component of the **SysMon Architecture**. It works in tandem with the [SysMon-CPP Agent](https://github.com/rafaafdeez/SysMon-CPP) (the C++ client that collects hardware metrics).

## 🏗️ Architecture

The system follows a decoupled, distributed architecture:

1. **Agent (C++)**: Reads hardware metrics (CPU, RAM) from the Linux kernel and sends them over TCP.
2. **Server (Java)**: Listens on port 8080, ingests the JSON payload, and processes the telemetry.

## 🚀 Requirements

- Java 17 or higher
- Maven 3.6+

## 🛠️ Build and Run

1. **Clone the repository:**

```bash
git clone [https://github.com/rafaafdeez/sysmon-server.git](https://github.com/rafaafdeez/SysMon-Server.git)
cd sysmon-server
```

2. **Build the project using Maven:**

```bash
mvn clean package
```

3. **Run the server:**

```bash
java -cp target/sysmon-server-1.0-SNAPSHOT.jar com.telemetry.App
```

_The server will start listening for incoming TCP connections on port 8080._

## 📄 Data Contract (JSON Payload)

The server expects incoming TCP packets formatted as JSON:

```json
{
  "sequence_num": 1,
  "timestamp": 1715423890,
  "cpu_percent": 45.2,
  "ram_used_mb": 4096
}
```

## 📜 License

Distributed under the MIT License. See `LICENSE` for more information.
