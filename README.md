# RAG (Retrieval Augmented Generation) System with Spring AI, Vertex AI, and Elasticsearch

이 프로젝트는 Spring AI 프레임워크를 활용하여 Vertex AI의 Gemini 모델과 임베딩 서비스를, 그리고 Elasticsearch를 벡터 스토어로 사용하는 RAG (Retrieval Augmented Generation) 시스템을 구축합니다. 사용자 질의에 대해 관련 문서를 검색하고, 이를 기반으로 LLM이 보다 정확하고 풍부한 답변을 생성하도록 돕는 것을 목표로 합니다.

## 🚀 기술 스택

*   **백엔드 프레임워크:** Spring Boot 3.x
*   **자바 버전:** Java 21
*   **LLM 및 임베딩:** Google Vertex AI (Gemini 모델, 임베딩 서비스)
*   **AI 프레임워크:** Spring AI (1.0.3)
*   **벡터 스토어:** Elasticsearch (9.1.4)
*   **모니터링/시각화:** Kibana (9.1.4)
*   **빌드 도구:** Gradle
*   **기타:** Lombok

## 🌟 주요 기능 (예상)

*   **문서 로딩 및 임베딩:** PDF 문서에서 텍스트를 추출하고, Vertex AI 임베딩 모델을 사용하여 벡터화합니다.
*   **벡터 스토어 관리:** Elasticsearch에 문서 임베딩 및 원본 텍스트를 저장하고 관리합니다.
*   **RAG 파이프라인:** 사용자 질의를 임베딩하고, Elasticsearch에서 관련 문서를 검색한 후, 검색된 문서를 기반으로 Vertex AI Gemini 모델이 답변을 생성합니다.
*   **REST API:** RAG 시스템과 상호작용하기 위한 RESTful API 엔드포인트를 제공합니다.

## 🛠️ 개발 환경 설정

이 프로젝트를 로컬에서 실행하려면 다음 도구들이 설치되어 있어야 합니다.

*   **Docker 및 Docker Compose:** Elasticsearch 및 Kibana 실행을 위해 필요합니다.
*   **Java Development Kit (JDK) 21**
*   **Gradle**
*   **Google Cloud SDK (gcloud CLI):** Vertex AI 서비스와의 인증 및 상호작용을 위해 필요할 수 있습니다. `gcloud auth application-default login` 명령어를 통해 인증을 설정하세요.

### 1. Elasticsearch 및 Kibana 실행

프로젝트 루트 디렉토리에서 다음 명령어를 실행하여 Elasticsearch 및 Kibana 컨테이너를 시작합니다.