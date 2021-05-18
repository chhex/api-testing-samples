#!/usr/bin/env bash
curl -vs -H "Content-Type: application/json" -X PUT  http://localhost:8080/api/testdata/$1/$2
