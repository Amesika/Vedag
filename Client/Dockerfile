# Stage 2: Serve app with nginx server

# Use official nginx image as the base image
FROM nginx:latest

# Copy the build output to replace the default nginx contents.
#COPY --from=build /usr/local/app/dist/vedag /usr/share/nginx/html
COPY ./dist/vedag /usr/share/nginx/html

# Expose port 80
EXPOSE 80