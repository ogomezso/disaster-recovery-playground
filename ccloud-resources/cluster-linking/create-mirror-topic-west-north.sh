#!/bin/bash
source ../.env

confluent kafka mirror create west-$TOPIC --link west-north-clink --environment $ENV --source-topic $TOPIC --cluster $CCLOUD_NORTH_CLUSTERID
