#
# Check whether an ECR repository having the name equal with the one passed as the input parameter exists.
# If it doesn't, then create it, tag the image and push it
# Otherwise, do nothing
#
function createRepo()
{
  # The repository name is the 1st input argument
  PARAM=$1
  REPO_NAME=${PARAM%:*}
  REPO_VERSION=${PARAM#*:}
  #Check whether the repository already exits
  OUTPUT=$(aws ecr describe-repositories --repository-names ${REPO_NAME} 2>&1)
  if [ $? -ne 0 ]
  then
    if echo ${OUTPUT} | grep -q RepositoryNotFoundException
    #It doesn't.
    then
      #Create it
      REPO_URI=$(aws ecr create-repository --repository-name ${REPO_NAME} --output text --query "repository.repositoryUri")
      #Tag it
      docker tag $REPO_NAME:$REPO_VERSION $REPO_URI:$REPO_VERSION
      #Push it
      docker push $REPO_URI:$REPO_VERSION
    #An error has been thrown (no internet connection, no permission, wrong repo name, etc.)
    else
      >&2 echo ${OUTPUT}
    fi
  fi
}

#
# Get a temporary access password to the AWS ECR (Elastic Container Registry) service
#
aws ecr get-login-password > login-password.txt
#
# Get the current IAM user ECR account number
#
ACCOUNT=$(aws sts get-caller-identity --output text --query "Account")
#
# Login to the AWS ECR service as generic AWS user
#
docker login -u AWS --password-stdin <login-password.txt https://${ACCOUNT}.dkr.ecr.eu-west-3.amazonaws.com
#
# Create the ECR docker repositories if they don't exist
#
for repo in postgres:latest demo-eureka:0.0.1-SNAPSHOT demo-gateway:0.0.1-SNAPSHOT rmohr/activemq:latest demo-api:0.0.1-SNAPSHOT demo-job:0.0.1-SNAPSHOT demo-xa:0.0.1-SNAPSHOT adminer:latest
do
  createRepo $repo
done
