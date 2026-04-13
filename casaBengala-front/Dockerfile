FROM node:alpine AS builder

WORKDIR /app

# Mejor caché
COPY package*.json ./
RUN npm install

# Copiar proyecto
COPY . .

# Build producción
RUN npm run build -- --configuration production

# =========================
# Nginx
# =========================
FROM nginx:alpine

# Angular 17+ / 18+ / 19+ / 20+ / 21 suele generar browser/
COPY --from=builder /app/dist/*/browser/ /usr/share/nginx/html/

# Config nginx
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 8080

CMD ["nginx", "-g", "daemon off;"]