#docker kill nexus > "/dev/null 2>&1"
#docker rm nexus > "/dev/null 2>&1"
# JOB_NAME is the name of the project of this build. This is the name you gave your job. It is set up by Jenkins
#COMPOSE_ID=${JOB_NAME:-local}
COMPOSE_ID=${JOB_NAME}
# Remove Previous Stack
#docker-compose -p $COMPOSE_ID rm -f
# Starting new stack environment
#docker-compose -p $COMPOSE_ID up -d --build
cd COMPOSE_ID
docker-compose up -d
