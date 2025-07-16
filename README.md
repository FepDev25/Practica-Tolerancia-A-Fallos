# Proyecto de Tolerancia a Fallos con Kubernetes

Este proyecto es una demostración práctica de cómo construir un sistema tolerante a fallos utilizando Kubernetes. Se despliega una aplicación web bancaria (UPS-Bank) con un frontend en Angular y un backend en Spring Boot, respaldada por una base de datos PostgreSQL de alta disponibilidad gestionada por el **Zalando Postgres Operator**.

El objetivo principal es mostrar cómo Kubernetes, junto con un operador especializado, puede mantener la aplicación y su base de datos operativas incluso cuando ocurren fallos en los nodos o pods del clúster.

## Tecnologías Utilizadas

- **Contenerización y Orquestación:**
  - Docker
  - Kubernetes (desplegado a través de Minikube, Kind, o similar)
- **Base de Datos y Alta Disponibilidad:**
  - PostgreSQL
  - **Zalando Postgres Operator**: Para la gestión y conmutación por error automática de clústeres de PostgreSQL.
- **Backend:**
  - Java 17
  - Spring Boot 3
  - Maven
- **Frontend:**
  - Angular 19
  - TypeScript
  - Tailwind CSS
- **Exposición y Enrutamiento:**
  - NGINX (como Ingress Controller y para servir el frontend)

## Arquitectura

El sistema sigue una arquitectura de 3 capas desplegada en Kubernetes:

1.  **Frontend (`frontend-upsbank`):** Una aplicación de una sola página (SPA) desarrollada en Angular. Se sirve como un sitio estático a través de un NGINX dentro de su propio Pod. Se despliega como un `Deployment` con múltiples réplicas para garantizar la disponibilidad.
2.  **Backend (`backend-upsbank`):** Una API RESTful desarrollada con Spring Boot. Se comunica con la base de datos para realizar las operaciones bancarias. También se despliega como un `Deployment` con réplicas para distribuir la carga y resistir fallos.
3.  **Base de Datos (`db` y `postgres-operator`):** Un clúster de PostgreSQL gestionado por el **Zalando Postgres Operator**. El operador se encarga de:
    - Crear un clúster con una instancia maestra y una o más réplicas.
    - Monitorear la salud del clúster.
    - Realizar una conmutación por error (failover) automática, promoviendo una réplica a maestro si el maestro actual falla.
    - Gestionar la configuración, usuarios y bases de datos a través de manifiestos de Kubernetes (CRDs).

El tráfico externo es gestionado por un `Ingress` que dirige las peticiones a los servicios del frontend o del backend según la ruta.

## Despliegue

Sigue estos pasos para desplegar la aplicación completa en tu clúster de Kubernetes.

### Prerrequisitos

- Un clúster de Kubernetes en ejecución (Minikube, Kind, Docker Desktop, etc.).
- `kubectl` configurado para interactuar con tu clúster.
- Un Ingress Controller (como NGINX Ingress) instalado en el clúster.

### Pasos de Despliegue

Los siguientes comandos se basan en el archivo `pasos.md` y los manifiestos en la carpeta `manifiestos/`.

1.  **Desplegar los CRDs y recursos del Operador de Zalando:**
    El operador necesita sus propias definiciones de recursos (CRDs) para entender los manifiestos de `postgresql`.

    ```bash
    kubectl apply -f postgres-operator/manifests/postgresql.crd.yaml
    kubectl apply -f postgres-operator/manifests/operatorconfiguration.crd.yaml
    kubectl apply -f postgres-operator/manifests/postgresteam.crd.yaml
    kubectl apply -f postgres-operator/manifests/operator-service-account-rbac.yaml
    kubectl apply -f postgres-operator/manifests/postgresql-operator-default-configuration.yaml
    kubectl apply -f postgres-operator/manifests/configmap.yaml
    ```

2.  **Desplegar el Postgres Operator:**
    Este comando inicia el pod del operador, que comenzará a observar los recursos `postgresql`.

    ```bash
    kubectl apply -f postgres-operator/manifests/postgres-operator.yaml
    ```

3.  **Crear el Clúster de PostgreSQL con Alta Disponibilidad (HA):**
    Este manifiesto (`manifiestos/postgresql.yaml`) le indica al operador que cree un clúster de PostgreSQL con 2 instancias (1 maestro, 1 réplica).

    ```bash
    kubectl apply -f manifiestos/postgresql.yaml
    ```
    Puedes verificar el estado con `kubectl get pods -l application=spilo` y `kubectl get postgresql`.

4.  **Desplegar la Aplicación (Backend y Frontend):**
    Estos comandos crean los `Deployments` y `Services` para las aplicaciones de frontend y backend.

    ```bash
    kubectl apply -f manifiestos/backend-deployment.yaml
    kubectl apply -f manifiestos/backend-service.yaml
    kubectl apply -f manifiestos/frontend-deployment.yaml
    kubectl apply -f manifiestos/frontend-service.yaml
    ```

5.  **Aplicar el Ingress:**
    Este recurso expone la aplicación al exterior, enrutando el tráfico a `/api/` hacia el backend y el resto hacia el frontend.

    ```bash
    kubectl apply -f manifiestos/ingress.yaml
    ```

### Acceder a la Aplicación

Una vez que todos los pods estén en estado `Running`, busca la dirección IP o el hostname de tu Ingress Controller y accede a él desde tu navegador.

```bash
kubectl get ingress
```

## Estructura del Proyecto

-   `backend-upsbank/`: Contiene el código fuente de la API REST en Spring Boot.
-   `frontend-upsbank/`: Contiene el código fuente de la aplicación web en Angular.
-   `manifiestos/`: Contiene todos los manifiestos de Kubernetes para desplegar la base de datos, el backend, el frontend y el ingress.
-   `pasos.md`: Documenta la secuencia de comandos para el despliegue.
-   `postgres-operator/`: Es un clon del repositorio del [Postgres Operator de Zalando](https://github.com/zalando/postgres-operator), que es el núcleo de la estrategia de tolerancia a fallos de la base de datos.
