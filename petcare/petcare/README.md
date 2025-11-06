PetCare - Sistema de Monitoramento de Pets
 Índice
Sobre o Projeto

Equipe

Tecnologias

Arquitetura

Como Executar

Documentação da API

Diagramas

Vídeo de Apresentação

Endpoints

Testes

 Sobre o Projeto
O PetCare é um sistema inovador de monitoramento de pets que combina dispositivos IoT com uma plataforma web para oferecer cuidado completo e em tempo real para animais de estimação.

 Problemas Resolvidos
Monitoramento de saúde: Temperatura corporal, frequência cardíaca e nível de atividade

Alertas proativos: Notificações automáticas baseadas em condições críticas

Localização: Rastreamento GPS em tempo real

Integração veterinária: Compartilhamento de dados com profissionais

Gestão múltipla: Suporte a múltiplos pets por tutor

 Público-Alvo
Tutores de cães e gatos que consideram seus animais como membros da família

Clínicas veterinárias que desejam oferecer monitoramento remoto

Pet shops interessados em soluções tecnológicas inovadoras

 Equipe
Alessandro da Silva Lira - RM: 560512
Responsabilidades:

Desenvolvimento das entidades JPA

Implementação dos serviços de negócio

Configuração do banco de dados

Gestão do repositório GitHub

Leonardo Carvalho Jeronimo Santos - RM: 560380
Responsabilidades:

Modelagem do banco de dados (DER)

Desenvolvimento dos controllers REST

Configuração do Swagger/OpenAPI

Documentação do projeto

Como Executar
Pré-requisitos
Java 17 ou superior

Maven 3.6+

Docker e Docker Compose (opcional)

# Clone o repositório
git clone https://github.com/LeonardoCJS/petcare-java.git
cd petcare-sistema

# Execute com Docker Compose
docker-compose up -d

# Acesse a aplicação
http://localhost:8080

# Navegue para o diretório do backend
cd backend-java

# Execute a aplicação
./mvnw spring-boot:run

# Ou compile e execute
./mvnw clean package
java -jar target/petcare-api-1.0.0.jar

Configurações
Aplicação rodando em: http://localhost:8080

Banco H2 Console: http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:petcaredb

Username: sa

Password: (vazio)

