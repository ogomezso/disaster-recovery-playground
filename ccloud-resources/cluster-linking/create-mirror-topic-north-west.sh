#!/bin/bash
source ../.env

confluent kafka mirror create north-chuck-java-topic --link north-west-clink --environment $ENV --source-topic chuck-java-topic --cluster $CCLOUD_WEST_CLUSTERID
