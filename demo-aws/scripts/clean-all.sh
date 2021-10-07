#
# Remove ECR repositories
# #
for r in postgres demo-eureka demo-gateway rmohr/activemq demo-api demo-job demo-xa adminer
do
  aws ecr delete-repository --repository-name $r --force
done
./delete-k8s.sh
