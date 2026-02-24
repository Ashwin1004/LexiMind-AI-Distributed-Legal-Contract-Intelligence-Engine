# ⚖️ LexiMind AI  
## Distributed Legal Contract Intelligence Engine (Kafka + pgvector + Groq RAG)

LexiMind AI is a distributed, microservices-based Legal AI system that enables:

- 📄 Contract PDF upload
- ✂️ Clause extraction
- 🔄 Kafka-based distributed processing
- 🧠 Embedding generation using SentenceTransformers
- 🗄️ Vector storage using PostgreSQL + pgvector
- 🔍 Semantic similarity search
- 🤖 Retrieval-Augmented Generation (RAG) using Groq LLM

This project demonstrates a production-style AI-native legal intelligence architecture.

---

# 🏗 System Architecture

User Uploads PDF  
⬇  
Document Service (Spring Boot)  
⬇  
Text Extraction  
⬇  
Kafka → clause-topic  
⬇  
Embedding Service  
⬇  
SentenceTransformer → Vector  
⬇  
PostgreSQL + pgvector  
⬇  
Semantic Retrieval  
⬇  
Groq LLM  
⬇  
AI Answer (RAG)

---

# 🚀 Tech Stack

Backend: Spring Boot  
Messaging: Apache Kafka (Docker)  
Vector Database: PostgreSQL 17 + pgvector  
Embedding Model: sentence-transformers/all-MiniLM-L6-v2  
LLM: Groq API (Llama 3)  
Containerization: Docker  

---

# 📦 Microservices

## 1️⃣ Document Service (Port 8085)

Responsibilities:
- Upload PDF
- Extract text
- Split into clauses
- Send clauses to Kafka topic

### Upload API

POST  
http://localhost:8085/api/documents/upload  

Body → form-data  
Key: file  
Type: File  

---

## 2️⃣ Embedding Service (Port 8086)

Responsibilities:
- Consume clauses from Kafka
- Generate embeddings via Python
- Store vectors in PostgreSQL (pgvector)
- Perform semantic search
- Execute RAG using Groq

### RAG API

POST  
http://localhost:8086/rag  

Body:

{
  "question": "What are the termination conditions?"
}

Response:
AI-generated answer based only on stored contract clauses.

---

# 🐳 Running Dependencies

## Start Kafka

docker run -d -p 9092:9092 apache/kafka

---

## Start PostgreSQL with pgvector

docker run -d \
  --name legalai-postgres \
  -e POSTGRES_DB=legal_db \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=your_password \
  -p 5434:5432 \
  pgvector/pgvector:pg17

---

## Enable pgvector Extension

docker exec -it legalai-postgres psql -U postgres -d legal_db

Inside psql:

CREATE EXTENSION vector;

---

# 🗄 Database Schema

CREATE TABLE clause_embeddings (
    id SERIAL PRIMARY KEY,
    document_id INT,
    clause_number INT,
    clause_text TEXT,
    embedding vector(384)
);

CREATE INDEX clause_embedding_idx
ON clause_embeddings
USING ivfflat (embedding vector_cosine_ops)
WITH (lists = 100);

---

# 🔐 Environment Variables

DO NOT hardcode secrets.

Create a `.env` file or set system environment variables:

GROQ_API_KEY=your_groq_api_key_here
DB_HOST=localhost
DB_PORT=5434
DB_NAME=legal_db
DB_USER=postgres
DB_PASSWORD=your_password
KAFKA_BOOTSTRAP=localhost:9092

---

# 📝 application.properties Example

groq.api.key=${GROQ_API_KEY}

spring.datasource.url=jdbc:postgresql://${DB_HOST}:${DB_PORT}/${DB_NAME}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}

spring.kafka.bootstrap-servers=${KAFKA_BOOTSTRAP}

---

# 🧠 Features

- Multi-document ingestion
- Distributed event-driven processing
- Clause-level vector embeddings
- Cosine similarity search
- Retrieval-Augmented Generation
- Microservices architecture
- pgvector-based semantic intelligence

---

# 🔥 What Makes This Production-Ready

- Uses Kafka for decoupled processing
- Stores embeddings in database (not memory)
- Supports multi-document search
- Clean separation of ingestion and AI layers
- RAG pipeline using real LLM (Groq)

---

# 📈 Future Improvements

- FastAPI embedding microservice (persistent model)
- Async streaming Groq responses
- Clause risk scoring
- Contract comparison engine
- UI dashboard
- Multi-tenant support

---

# 📜 License

MIT License

---

# 👨‍💻 Author

Built as part of an AI-native Legal Intelligence system exploration.
