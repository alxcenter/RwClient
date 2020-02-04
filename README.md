# RwClient

**RwClient** - client part of railway tickets monitoring.

For running this application you should [create TelegramBot](https://core.telegram.org/bots#creating-a-new-bot)

Set bot name and token in application.yml

```
telega:
    name: ${telega_name}
    apiToken: ${telega_apiToken}
```

Client using Telegram Login Widget.
For using OAuth you should [link your domain to the bot.](https://core.telegram.org/widgets/login#linking-your-domain-to-the-bot)

Set MySQL or PostgreSQL connection in application.yml

Run `mvn clean install` to build app

Then run `src/main/java/io/bot/Application.java`


For start conversation with the bot, you can send him a message `/start`

Or you can use the web version `http://localhost/`

Example:

![](src/main/resources/example.gif)