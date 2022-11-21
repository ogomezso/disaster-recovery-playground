#!/bin/bash
source .env

confluent kafka mirror create west-chuck-java-topic --link west-north-clink --environment $ENV --source-topic chuck-java-topic --cluster $CCLOUD_NORTH_CLUSTERID
