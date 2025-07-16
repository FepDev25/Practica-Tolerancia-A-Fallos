### ORDEN DE EJECUCIÓN FINAL

#### Paso 1: Desplegar CRDs y recursos del operador Zalando

```bash
kubectl apply -f postgres-operator/manifests/postgresql.crd.yaml
kubectl apply -f postgres-operator/manifests/operatorconfiguration.crd.yaml
kubectl apply -f postgres-operator/manifests/postgresteam.crd.yaml
kubectl apply -f postgres-operator/manifests/operator-service-account-rbac.yaml
kubectl apply -f postgres-operator/manifests/postgresql-operator-default-configuration.yaml
kubectl apply -f postgres-operator/manifests/configmap.yaml
```

#### Paso 2: Desplegar el Postgres Operator

```bash
kubectl apply -f postgres-operator/manifests/postgres-operator.yaml
```

#### Paso 3: Crear clúster PostgreSQL con HA

```bash
kubectl apply -f manifiestos/postgresql.yaml
```

#### Paso 4: Desplegar app

```bash
kubectl apply -f manifiestos/backend-deployment.yaml
kubectl apply -f manifiestos/backend-service.yaml
kubectl apply -f manifiestos/frontend-deployment.yaml
kubectl apply -f manifiestos/frontend-service.yaml
```

#### Paso 5: Aplicar Ingress

```bash
kubectl apply -f manifiestos/ingress.yaml
```

