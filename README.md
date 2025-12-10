# TP5 - Sistema Integrado (Entrega Final)

![Build](https://github.com/MarlonPasseri/TP5-AMADEU/actions/workflows/build.yml/badge.svg)
![Deploy](https://github.com/MarlonPasseri/TP5-AMADEU/actions/workflows/deploy.yml/badge.svg)
![Security](https://github.com/MarlonPasseri/TP5-AMADEU/actions/workflows/security.yml/badge.svg)

## Visão Geral

Este repositório representa a **entrega final do TP5**, contendo:

- Sistema integrado de **Catálogo de Produtos** e **Pedidos**
- Código refatorado com:
  - Value Objects imutáveis (`Money`, `StockQuantity`)
  - Separação de responsabilidades (camadas `domain`, `application`, `web`)
  - API de domínio imutável (sem uso de setters na lógica de negócio)
  - Tratamento de erros com `BusinessException` + `GlobalExceptionHandler`
- Workflows completos de **CI/CD** com GitHub Actions:
  - `build.yml` → build, testes, Jacoco, SAST (CodeQL), resumo em Markdown
  - `deploy.yml` → deploy automatizado, uso de OIDC para autenticação na nuvem
  - `post-deploy-tests.yml` → testes Selenium pós-deploy usando tag `@Tag("selenium")`
  - `security.yml` → DAST agendado com OWASP ZAP

## Como rodar localmente

```bash
./gradlew bootRun
```

Acesse:

- Dashboard: http://localhost:8080/
- Produtos: http://localhost:8080/produtos
- Pedidos: http://localhost:8080/pedidos

## Testes

```bash
./gradlew test           # testes unitários
./gradlew seleniumTest   # testes Selenium (requer ChromeDriver)
```

## CI/CD

1. Faça push deste projeto para um repositório no GitHub.
2. Ajuste:
   - `SEU_USUARIO/SEU_REPO` nas URLs de badges
   - URL real de produção nos arquivos `deploy.yml` e `security.yml`
   - Segredo `AWS_DEPLOY_ROLE` (ou equivalente, dependendo da nuvem escolhida)

Os workflows serão disparados automaticamente em:

- `push` / `pull_request` → `build.yml`
- `push` na `main` ou `workflow_dispatch` → `deploy.yml`
- Conclusão de deploy → `post-deploy-tests.yml`
- Agendado diariamente → `security.yml`

## Logs e Depuração

- Cada workflow utiliza:
  - `group logs` (`::group:: ... ::endgroup::`) para agrupar etapas
  - `$GITHUB_STEP_SUMMARY` para gerar um **resumo em Markdown**
  - Upload de artefatos (relatório Jacoco, JAR) para facilitar inspeção posterior

Isso atende aos requisitos de **monitoramento, rastreabilidade e documentação da pipeline** exigidos no TP5.
