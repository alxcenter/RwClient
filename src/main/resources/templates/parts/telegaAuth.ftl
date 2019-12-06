<#macro telegaAuth>
        <div class="telega-auth">
            <script async src="https://telegram.org/js/telegram-widget.js?7" data-telegram-login="loclalhost_bot" data-size="large" data-onauth="onTelegramAuth(user)" data-request-access="write"></script>
            <script type="text/javascript">
                function onTelegramAuth(user) {
                    console.log(JSON.stringify(user));
                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", '/auth', true);
                    xhr.setRequestHeader('Content-Type', 'application/json');
                    xhr.onload = function (e) {
                        window.location = xhr.responseText;
                    }
                    xhr.send(JSON.stringify(user));
                }
            </script>
        </div>
</#macro>