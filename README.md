# Vk echo bot

Vk echo bot using Callback API that utilizes

- [SpringBoot](https://spring.io/projects/spring-boot)
- [Docker](https://docker.com)
- [Prometheus](https://prometheus.io)
- [Grafana](https://grafana.com)

# How to use

Create your [vk community](https://dev.vk.com/ru/api/callback/getting-started) and set up callback API, with next params:
* API version - 5.199
* Address - your public https address on application port 8080 + /vkbot path.
  Example: https://95ae-37-5-22-33.ngrok-free.app/vkbot
* Go to event type tab and set only incoming messages checkbox

For starting application you need to get 5 variables:

* [vk api key](https://dev.vk.com/ru/api/access-token/getting-started#Ключ%20доступа%20сообщества) - for call vk api
  method. the key must have access rights to community messages. Env VK_API_KEY
* [vk group id](https://dev.vk.com/ru/api/bots/getting-started) - create your community and copy its ID.
  Env VK_GROUP_ID
* vk confirmation string. Env VK_CONFIRMATION_STRING ![confirmationString.png](image/confirmationString.png)
* vk api version. 5.199 Env VK_API_VERSION
* vk secret. Env VK_SECRET ![secretKey.png](image/secretKey.png)

# Development

Run `VkechobotApplication.kt` file in your IDE while developing the project and set dev profile for spring boot app

# Docker

## Configuration

Pass the 5 variables you received earlier to the `.env` file

## Build and run

1. Build project with `stage` gradle task
2. Run `docker-compose up`

This build script runs:

- `http://localhost:3000` Grafana monitoring dashboards
- `http://localhost:8080` Your VK echo bot endpoint

>This builds a callback API version of your VK  echo bot.
Thus, you have to propagate your local 8080 port to the global web (through some gateway like [ngrok](https://ngrok.com)) and configure callback API VK with direct public URL of your machine.

3. Define your public vk bot webhook in the webhook settings of your  `/vkbot` (e.g. `https://domain.com/vkbot`)

## Options

You can override default running options for every service in the `docker` folder.

