#!/usr/bin/env bash
curl -H "Content-Type: application/json" -X POST -d "{
    \"text\": \"$1\"
}"  http://localhost:8080/api/testdata
