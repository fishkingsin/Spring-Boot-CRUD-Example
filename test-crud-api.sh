#!/bin/sh

echo "Creating a product..."
curl -s -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{"name":"Laptop","description":"A high-performance laptop.","price":1200.00}' | jq .

echo "Getting all products..."
curl -s http://localhost:8080/api/products | jq .

echo "Getting product by ID (1)..."
curl -s http://localhost:8080/api/products/1 | jq .

echo "Updating product by ID (1)..."
curl -s -X PUT http://localhost:8080/api/products/1 \
  -H "Content-Type: application/json" \
  -d '{"name":"Updated Laptop","description":"An updated high-performance laptop.","price":1300.00}' | jq .

echo "Deleting product by ID (1)..."
curl -s -X DELETE http://localhost:8080/api/products/1

echo "Getting all products after deletion..."
curl -s http://localhost:8080/api/products | jq .
