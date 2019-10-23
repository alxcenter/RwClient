# RwClient

**RwClient** - клиентская часть для создания мониторинга слежения ж/д билетов.

Для работы необходим Telegram Bot.
Инструкция по созданию бота https://core.telegram.org/bots#creating-a-new-bot

Закидываем имя бота и его токен в
`src/main/resources/telega.properties`

Для примера, бот уже есть в пропертях.


Для работы с DB используется MySql.

Конфиг для соединения в `src/main/resources/jetty.xml`

При использовании другой DB необходимо добавить депеденси на драйвер в pom.xml

запускаем при помощи maven
mvn `jetty:run`

Для работы с клиентом в телеграме необходимо написать боту `/start`

Пример работы:

![](src/main/resources/example.gif)