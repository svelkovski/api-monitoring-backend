# API Monitoring Tool - Backend

This is the backend service for a developer-focused API monitoring tool.  
It handles user authentication, API monitoring, storing check results, and exposing REST endpoints for the frontend dashboard.

## Technologies Used
- Java 17 / Spring Boot
- Postgres (via Supabase)
- JWT for authentication
- Docker for containerization
- Maven for build

## Features
- User registration and login
- CRUD operations for monitored APIs
- Background worker to check API uptime
- Stores response status and latency
- REST API endpoints for frontend

## Related Repos
Frontend: https://github.com/yourorg/api-monitor-frontend