#!/bin/bash
source ../.env

confluent kafka mirror create west-$NP_TOPIC --link west-north-clink --environment $ENV --source-topic $NP_TOPIC --cluster $CCLOUD_NORTH_CLUSTERID
