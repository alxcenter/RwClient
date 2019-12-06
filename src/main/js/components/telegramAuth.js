import React from 'react';
import TelegramLoginButton from 'react-telegram-login';

const handleTelegramResponse = user => {
    console.log(user);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", '/auth', true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.onload = function (e) {
        window.location = xhr.responseText;
    };
    xhr.send(JSON.stringify(user));
};


export default function TelegramLogin(){
    return (
        <div className="telegramAuth">
            <TelegramLoginButton dataOnauth={handleTelegramResponse} botName="loclalhost_bot"/>
        </div>
    );
}