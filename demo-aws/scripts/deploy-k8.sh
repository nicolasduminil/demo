#
# Deploy the k8s services
#
kubectl apply -f ../eks/demo-postgres-service.yaml,../eks/demo-postgres-deployment.yaml
kubectl apply -f ../eks/demo-eureka-service.yaml,../eks/demo-eureka-deployment.yaml
kubectl apply -f ../eks/demo-gateway-service.yaml,../eks/demo-gateway-deployment.yaml
kubectl apply -f ../eks/demo-activemq-service.yaml,../eks/demo-activemq-deployment.yaml
kubectl apply -f ../eks/demo-api-service.yaml,../eks/demo-api-deployment.yaml
kubectl apply -f ../eks/demo-job-service.yaml,../eks/demo-job-deployment.yaml
kubectl apply -f ../eks/demo-adminer-service.yaml,../eks/demo-adminer-deployment.yaml
