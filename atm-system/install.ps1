cd account-service
.\mvnw clean install
cd ../atm-service
.\mvnw clean install
cd ../service-registry
.\mvnw clean install
cd ..
docker-compose up -d