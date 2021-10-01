#
# Deploy the k8s services
#
#kubectl apply -f ../eks/demo-postgres-service.yaml, ../eks/demo-postgres-deployment.yaml
kubectl apply -f ../eks/demo-eureka-service.yaml,../eks/demo-eureka-deployment.yaml
kubectl apply -f ../eks/demo-gateway-service.yaml,../eks/demo-gateway-deployment.yaml
#kubectl apply -f ../eks/demo-activemq-service.yaml, ../eks/demo-activemq-deployment.yaml
#kubectl apply -f ../eks/demo-api-service.yaml, ../eks/demo-api-deployment.yaml
#kubectl apply -f ../eks/demo-job-service.yaml, ../eks/demo-job-deployment.yaml
#kubectl apply -f ../eks/adminer-service.yaml, ../eks/adminer-deployment.yaml
#
# Get the security group ID associated to our EKS cluster EC2 node
#
GROUP_ID=$(aws ec2 describe-security-groups --filters Name=group-name,Values="*demo-dsirc*" --query "SecurityGroups[*].{Name:GroupName,ID:GroupID}")
#
# Create a new rule for the associated EC2 security group to allow the incoming traffic from the node ports
#
aws ec2 authorize-security-group-ingress --protocol tcp --port 31000 --group-ID $GROUP_ID --cidr 0.0.0.0/0