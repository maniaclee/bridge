#!/usr/bin/env bash

mvn clean deploy -Dgpg.skip   -Dmaven.test.skip=true
