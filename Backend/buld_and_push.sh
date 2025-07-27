#!/bin/bash

# GCR project ID
PROJECT_ID="attykart"

# Folder names (case-sensitive)
services=("ApiGateway" "CartService" "MailService" "OrderService" "ProductService" "TransactionService" "UserService" "WalletService")

# Tag to use
TAG="v1"

for service in "${services[@]}"
do
  echo "📦 Building $service..."
  cd $service || { echo "❌ Failed to enter $service directory"; exit 1; }

  # Convert service name to lowercase for image name
  image_name=$(echo "$service" | tr '[:upper:]' '[:lower:]')

  docker build -t gcr.io/$PROJECT_ID/$image_name:$TAG .

  echo "🚀 Pushing $image_name to GCR..."
  docker push gcr.io/$PROJECT_ID/$image_name:$TAG

  cd ..
done

echo "✅ All services built and pushed successfully!"
